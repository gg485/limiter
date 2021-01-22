package model;

import grpc.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resource {
    ResourceCommon resource;
    Map<String, Admin.Client> clients;
    long lastTime;

    public Resource() {
        clients = new HashMap<>();
        resource = new ResourceCommon();
    }

    public ResourceCommon getResource() {
        return resource;
    }

    public Map<String, Admin.Client> getClients() {
        return clients;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setResource(ResourceCommon resource) {
        this.resource = resource;
    }

    public void setClients(Map<String, Admin.Client> clients) {
        this.clients = clients;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public synchronized List<Admin.Client> cleanClients() {
        List<Admin.Client> ret = new ArrayList<>();
        clients.forEach((k, v) -> {
            if (v.getExpireTime() - System.currentTimeMillis() < 0) {
                clients.remove(k);
            } else {
                ret.add(v);
            }
        });
        return ret;
    }

    public synchronized List<Admin.Client> allClients() {
        return new ArrayList<>(clients.values());
    }

    public synchronized boolean expired() {
        return System.currentTimeMillis() - lastTime > 1000 * resource.refreshInterval * 2;
    }

    public synchronized void heartbeat() {
        lastTime = System.currentTimeMillis();
    }

    public synchronized void setClient(Admin.Client client) {
        if (client != null) {
            clients.put(client.getId(), client);
        }
    }

    public synchronized void removeClient(String id) {
        clients.remove(id);
    }

    public synchronized int clientsCount() {
        return clients.size();
    }

    public boolean isNullCache() {
        return resource.id.equals("nullcache");
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resource=" + resource +
                ", clients=" + clients +
                ", lastTime=" + lastTime +
                '}';
    }
}
