package zk;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import zk.hashalgo.MurmurHashService;

import java.io.IOException;
import java.util.List;

public class ZkServer {
    public static final String zkServers = "127.0.0.1:2181";
    private static ZkClient zkClient;
    private static ConsistentHash<String> consistentHash;
    private final static String QUOTA = "/quota";
    private static final Object lock = new Object();
    private static Server rpcServer;
    final int port;
    private static final QuotaServerListener listener = new QuotaServerListener();

    public ZkServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        zkClient = new ZkClient(zkServers, 3000);
        if (zkClient.exists(QUOTA)) zkClient.delete(QUOTA);
        zkClient.createPersistent(QUOTA);
        zkClient.subscribeChildChanges(QUOTA, listener);

        rpcServer = ServerBuilder.forPort(50001).addService(new RpcServerImpl()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread(ZkServer.this::stop));
    }

    public void stop() {
        zkClient.unsubscribeChildChanges(QUOTA, listener);
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }

    public void blockUntilShutDown() throws InterruptedException {
        if (rpcServer != null) {
            rpcServer.awaitTermination();
        }
    }

    private static class QuotaServerListener implements IZkChildListener {
        @Override
        public void handleChildChange(String parentPath, List<String> currentChildren) {
            synchronized (lock) {
                System.out.println(currentChildren);
                consistentHash = new ConsistentHash<>(new MurmurHashService(), 3, currentChildren);
            }
        }
    }

    private static class RpcServerImpl extends ZkGrpc.ZkImplBase {
        @Override
        public void getMasterAddr(ZkOuterClass.GetMasterAddrReq request, StreamObserver<ZkOuterClass.GetMasterAddrResp> responseObserver) {
            synchronized (lock) {
                String resourceId = request.getResourceId();
                ZkOuterClass.GetMasterAddrResp resp = ZkOuterClass.GetMasterAddrResp.newBuilder()
                        .setMasterAddr(consistentHash.get(resourceId)).build();
                responseObserver.onNext(resp);
                responseObserver.onCompleted();
            }
        }
    }
}
