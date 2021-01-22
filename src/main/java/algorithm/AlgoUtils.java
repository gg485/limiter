package algorithm;

import grpc.Admin;

import java.util.List;

public class AlgoUtils {
    public static double sumHas(List<Admin.Client> clients) {
        double ret = 0;
        for (Admin.Client client : clients) {
            ret += client.getGets();
        }
        return ret;
    }

    public static double sumWants(List<Admin.Client> clients) {
        double ret = 0;
        for (Admin.Client client : clients) {
            ret += client.getWants();
        }
        return ret;
    }

    public static Admin.Client get(List<Admin.Client> clients, String id) {
        for (Admin.Client client : clients) {
            if (id.equals(client.getId())) {
                return client;
            }
        }
        return null;
    }
}
