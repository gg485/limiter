package client;

import java.net.UnknownHostException;

//立即返回能否获得n个令牌
public class Allower {
    private static final long DEFAULT_INTERVAL = 10;
    private final Resource resource;

    public Allower(String zkHost, int zkPort, String id) throws UnknownHostException {
        resource = new Resource(zkHost, zkPort);
        resource.getLimiter().setRate(1);
        resource.setId(id);
        resource.setRefreshInterval(DEFAULT_INTERVAL);
        resource.syncer.start();
        resource.refreshResource(0);
    }

    public boolean isAllow() {
        return isAllowN(1);
    }

    private boolean isAllowN(int n) {
        resource.requests[resource.idx()].getAndAdd(n);
        boolean ret = resource.getLimiter().tryAcquire(n);
        if (ret) {
            resource.used[resource.idx()].getAndAdd(n);
        }
        return ret;
    }
    public void close() throws UnknownHostException, InterruptedException {
        resource.close();
    }
}

