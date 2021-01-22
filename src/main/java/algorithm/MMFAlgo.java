package algorithm;

import grpc.Admin;
import grpc.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMFAlgo implements Algo {
    @Override
    public double[] getQuota(long capacity, List<Admin.Client> clients, Api.GetCapacityReq req) {
        Admin.Client old = AlgoUtils.get(clients, req.getClientId());
        double available = capacity - AlgoUtils.sumHas(clients);
        double clientsNum = clients.size();
        double wants = req.getWants();
        if (old != null) {
            available += old.getGets();
        } else {
            clientsNum++;
        }
        if (available < 0) {
            available = 0;
        }
        if (available > capacity) {
            available = capacity;
        }
        //第一轮分配
        double avg = capacity / clientsNum;
        double gets;
        if (wants <= avg) {
            gets = Math.min(wants, available);
            return new double[]{gets, available - gets};
        }
        //第二轮
        double extra = 0;
        Map<String, Admin.Client> wantExtra = new HashMap<>();
        for (Admin.Client client : clients) {
            if (req.getClientId().equals(client.getId())) {
                continue;
            }
            if (client.getWants() < avg) {
                extra += avg - client.getWants();
            } else if (client.getWants() > avg) {
                wantExtra.put(client.getId(), client);
            }
        }
        double avgExtra = extra / (wantExtra.size() + 1);
        if (extra == 0 || req.getWants() <= avg + avgExtra) {
            gets = Math.min(Math.min(available, avgExtra + avg), req.getWants());
            return new double[]{gets, available - gets};
        }
        //第三轮分配
        double extraExtra = 0;
        double extraExtraClientsNum = 0;
        for (Admin.Client client : clients) {
            if (client.getWants() < avgExtra + avg) {
                extraExtra += avgExtra + avg - client.getWants();
            } else if (client.getWants() > avgExtra + avg) {
                extraExtraClientsNum++;
            }
        }
        double avgExtraExtra = extraExtra / (extraExtraClientsNum + 1);
        gets = Math.min(Math.min(available, avg + avgExtra + avgExtraExtra), req.getWants());
        return new double[]{gets, available - gets};
    }
}
