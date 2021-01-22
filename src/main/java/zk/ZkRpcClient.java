package zk;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ZkRpcClient {
    private final ZkGrpc.ZkBlockingStub blockingStub;
    public ZkRpcClient(String zkHost,int zkPort){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(zkHost, zkPort).usePlaintext().build();
        blockingStub=ZkGrpc.newBlockingStub(channel);
    }
    public String getMasterAddr(ZkOuterClass.GetMasterAddrReq request){
        ZkOuterClass.GetMasterAddrResp resp = blockingStub.getMasterAddr(request);
        return resp.getMasterAddr();
    }
}
