package server;

import algorithm.Algo;
import algorithm.BalanceAlgo;
import algorithm.MMFAlgo;
import dao.QuotaDao;
import exceptions.InvalidAlgoException;
import exceptions.QuotaResourceException;
import exceptions.WrongMasterException;
import grpc.Admin;
import grpc.Api;
import grpc.QuotaGrpc;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import lane.Lane;
import model.Resource;
import model.ResourceCommon;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import zk.ZkOuterClass;
import zk.ZkRpcClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QuotaServer {
    private final String host;
    private final int port;
    private static Server rpcServer;
    private static final String zkServers = "127.0.0.1:2181";

    private static final Map<String, Resource> resources = new HashMap<>();
    private static final long nullcacheExpire = 10000;//10s
    private static final long resourceExpire = 10000;
    private static final String QUOTA = "/quota";
    private static ZkRpcClient client = null;
    private static final Map<Long, Algo> algos = new HashMap<>();
    private static final BlockingQueue<Pair> queue = new ArrayBlockingQueue<>(1024, true);
    private static volatile boolean terminated = false;
    private final QuotaDao dao = new QuotaDao();
    private final Logger logger = Logger.getLogger(QuotaServer.class.getName());
    private final Lane<ResourceCommon> lane = new Lane<>();

    public QuotaServer(String host, int port, String zkHost, int zkPort) throws SQLException {
        this.host = host;
        this.port = port;
        client = new ZkRpcClient(zkHost, zkPort);
        algos.put(0L, new BalanceAlgo());
        algos.put(1L, new MMFAlgo());
    }

    public void start() throws IOException {
        if (!terminated) return;
        ZkClient zkClient = new ZkClient(zkServers, 3000);

        zkClient.createEphemeral(QUOTA + "/" + host + ":" + port);
        new Thread(() -> {
            while (!terminated) {
                try {
                    Pair pair = queue.take();
                    Resource res = pair.resource;
                    String key = pair.key;
                    if (System.currentTimeMillis() - res.getResource().getUpdateTime() < resourceExpire) {
                        continue;
                    }
                    ResourceCommon r = dao.DBResource(key);
                    if (r == null) continue;
                    r.setUpdateTime(System.currentTimeMillis());
                    pair.resource.setResource(r);

                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        rpcServer = ServerBuilder.forPort(port).addService(new QuotaService()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread(QuotaServer.this::stop));
    }

    public void stop() {
        if (rpcServer != null) {
            rpcServer.shutdown();
        }
    }

    public void blockUntilShutDown() throws InterruptedException {
        if (rpcServer != null) {
            rpcServer.awaitTermination();
        }
        terminated = true;
    }

    class QuotaService extends QuotaGrpc.QuotaImplBase {
        @Override
        public void getCapacity(Api.GetCapacityReq req, StreamObserver<Api.GetCapacityResp> responseObserver) {
            try {
                checkMaster(req.getClientId(), req.getResourceId());
                Resource r = resource(req.getResourceId());
                r.heartbeat();

                List<Admin.Client> clients = r.cleanClients();
                long algoId = r.getResource().getAlgorithm();
                Algo algo = algos.get(algoId);
                if (algo == null) {
                    logger.error("invalid algo: " + r.getResource().getAlgorithm());
                    throw new InvalidAlgoException();
                }
                double[] res = algo.getQuota(r.getResource().getCapacity(), clients, req);
                double gets = res[0], reserve = res[1];
                if (gets < 0) gets = 0;
                if (reserve < 0) reserve = 0;
                Admin.Client client = Admin.Client.newBuilder().setId(req.getClientId()).setWants(req.getWants()).setGets(gets)
                        .setReserve(reserve).setExpireTime(System.currentTimeMillis() + r.getResource().getRefreshInterval() + 2)
                        .setRefreshInterval(r.getResource().getRefreshInterval()).build();
                r.setClient(client);
                Api.GetCapacityResp reply = Api.GetCapacityResp.newBuilder().setResourceId(r.getResource().getId())
                        .setRefreshInterval(client.getRefreshInterval()).setGets(gets).setReserve(reserve).build();
                if (reply.getGets() + reply.getReserve() < req.getWants()) {
                    logger.warn("trigger limit");
                } else {
                    logger.info("not trigger limit");
                }
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            } catch (WrongMasterException | InvalidAlgoException | QuotaResourceException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void releaseCapacity(Api.ReleaseCapacityReq req, StreamObserver<Api.ReleaseCapacityResp> responseObserver) {
            Api.ReleaseCapacityResp resp = Api.ReleaseCapacityResp.newBuilder().build();
            try {
                checkMaster(req.getClientId(), req.getResourceId());
                Resource r = resource(req.getResourceId());
                r.removeClient(req.getClientId());
            } catch (WrongMasterException | InterruptedException | QuotaResourceException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
    }

    static class Pair {
        volatile Resource resource;
        String key;

        public Pair(Resource res, String key) {
            this.resource = res;
            this.key = key;
        }
    }

    public void checkMaster(String clientId, String resourceId) throws WrongMasterException {
        ZkOuterClass.GetMasterAddrReq addrReq = ZkOuterClass.GetMasterAddrReq.newBuilder()
                .setResourceId(resourceId).build();
        String masterAddr = client.getMasterAddr(addrReq);
        if (!masterAddr.equals(host + ":" + port)) {
            logger.error("current client " + clientId + ", host expect: " + masterAddr + ", get: " + host);
            throw new WrongMasterException();
        }
    }

    private Resource resource(String key) throws QuotaResourceException, InterruptedException {
        Resource res = getResource(key);
        if (res.isNullCache()) {
            if (System.currentTimeMillis() - res.getResource().getUpdateTime() > nullcacheExpire) {
                setResource(key, null);
                res = null;
            } else {
                throw new QuotaResourceException();
            }
        }
        if (res != null) {//存在则异步更新
            if (System.currentTimeMillis() - res.getResource().getUpdateTime() > resourceExpire) {
                Pair pair = new Pair(res, key);
                queue.put(pair);
            }
            return res;
        }
        //不存在则同步获取，注意缓存击穿
        ResourceCommon r = lane.exec(key, () -> {
            try {
                return dao.DBResource(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        if (r == null) {
            setResource(key, newNullCache());
            throw new QuotaResourceException();
        }
        r.setUpdateTime(System.currentTimeMillis());
        res = new Resource();
        res.setResource(r);
        res.setClients(new HashMap<>());
        return res;
    }

    private synchronized Resource getResource(String key) {
        return resources.get(key);
    }

    private synchronized void setResource(String key, Resource r) {
        System.out.println("new resource: " + key + " " + r);
        resources.put(key, r);
    }

    public Resource newNullCache() {
        Resource resource = new Resource();
        resource.getResource().setId("nullcache");
        resource.getResource().setUpdateTime(System.currentTimeMillis());
        return resource;
    }

}
