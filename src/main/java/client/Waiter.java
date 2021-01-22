package client;

import java.net.UnknownHostException;

//阻塞直到获得n个令牌
public class Waiter {
    private static final long DEFAULT_INTERVAL = 10;
    private final Resource resource;

    public Waiter(String zkHost, int zkPort, String id) throws UnknownHostException {
        resource = new Resource(zkHost, zkPort);
        resource.setId(id);
        resource.setRefreshInterval(DEFAULT_INTERVAL);
        resource.setRipeTime(System.currentTimeMillis());
        resource.refreshResource(0);
        resource.syncer.start();
    }

    public void acquire() {
        acquireN(1);
    }

    public void acquireN(int n) {
        resource.requests[resource.idx()].getAndAdd(n);
        resource.getLimiter().acquire(n);
        resource.used[resource.idx()].getAndAdd(n);
    }
    public void close() throws UnknownHostException, InterruptedException {
        resource.close();
    }
}
