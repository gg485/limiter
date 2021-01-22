package client;

import com.google.common.util.concurrent.RateLimiter;
import grpc.Api;
import zk.ZkOuterClass;
import zk.ZkRpcClient;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Resource {
    private String id;
    private volatile long refreshInterval;
    private long lastInterval;
    private final RateLimiter limiter;
    private String masterAddr;
    private final QuotaClient conn;
    private final ZkRpcClient zkClient;
    private final ScheduledExecutorService service;
    private ScheduledFuture<?> future;
    private final Runnable cron;
    private final BlockingQueue<Double>queue;
    private long ripeTime;
    final AtomicLong[]requests;
    final AtomicLong[]used;
    Thread syncer;
    private volatile boolean terminated=false;
    private volatile double want;

    public Resource(String zkHost,int zkPort){
        limiter=RateLimiter.create(Double.MAX_VALUE);
        zkClient=new ZkRpcClient(zkHost,zkPort);
        ZkOuterClass.GetMasterAddrReq req = ZkOuterClass.GetMasterAddrReq.newBuilder().setResourceId(id).build();
        masterAddr=zkClient.getMasterAddr(req);
        conn=new QuotaClient(masterAddr);

        service= Executors.newSingleThreadScheduledExecutor();
        cron=new Runnable() {
            @Override
            public void run() {
                try {
                    if(lastInterval!=refreshInterval){
                        if(future!=null){
                            future.cancel(true);
                        }
                        future=service.scheduleAtFixedRate(cron, 0L, refreshInterval, TimeUnit.SECONDS);
                        lastInterval=refreshInterval;
                    }
                    queue.put(getWants());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        future = service.scheduleAtFixedRate(cron, 1L, refreshInterval, TimeUnit.SECONDS);
        queue=new ArrayBlockingQueue<>(1);
        requests=new AtomicLong[5];
        used=new AtomicLong[5];
        for(int i=0;i<5;i++){
            requests[i]=new AtomicLong(0);
            used[i]=new AtomicLong(0);
        }
        syncer=new Thread(() -> {
            while(!terminated){
                try {
                    want = queue.take();
                    refreshResource(want);
                } catch (InterruptedException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void refreshResource(double want) throws UnknownHostException {
        ZkOuterClass.GetMasterAddrReq req = ZkOuterClass.GetMasterAddrReq.newBuilder().setResourceId(id).build();
        masterAddr=zkClient.getMasterAddr(req);
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String jvmName = bean.getName();
        String pid = jvmName.split("@")[0];
        Api.GetCapacityReq getCapacityReq = Api.GetCapacityReq.newBuilder()
                .setClientId(InetAddress.getLocalHost().getHostName()+":"+pid).setResourceId(id).setWants(want).build();
        Api.GetCapacityResp resp = conn.getCapacity(getCapacityReq);
        if(want!=0&&ripeTime==0){
            ripeTime=System.currentTimeMillis()+(refreshInterval+5)*1000;
        }
        double gets = resp.getGets();
        double reserve = resp.getReserve();
        refreshInterval=resp.getRefreshInterval();
        if(refreshInterval<=0)refreshInterval=1;
        if(ripeTime!=0&&System.currentTimeMillis()>ripeTime){
            double limit= gets + reserve;
            limiter.setRate(limit);
        }
    }

    private double getWants(){
        int idx=idx();
        int next=nextIdx(idx);
        requests[next].set(0);
        used[next].set(0);
        double last=requests[lastIdx(idx)].get();
        return last/refreshInterval;
    }

    private int lastIdx(int idx) {
        idx--;
        if(idx<0)idx+=5;
        return idx;
    }

    private int nextIdx(int idx) {
        idx++;
        return idx%5;
    }

    int idx() {
        return (int) ((System.currentTimeMillis()/refreshInterval)%5);
    }

    public void close() throws UnknownHostException, InterruptedException {
        if(terminated)return;
        terminated=true;
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String jvmName = bean.getName();
        String pid = jvmName.split("@")[0];
        Api.ReleaseCapacityReq req = Api.ReleaseCapacityReq.newBuilder()
                .setClientId(InetAddress.getLocalHost().getHostName() + ":" + pid).setResourceId(id).build();
        conn.releaseCapacity(req);
        conn.shutdown();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getMasterAddr() {
        return masterAddr;
    }

    public void setMasterAddr(String masterAddr) {
        this.masterAddr = masterAddr;
    }

    public void setRipeTime(long ripeTime) {
        this.ripeTime = ripeTime;
    }

    public RateLimiter getLimiter() {
        return limiter;
    }
}
