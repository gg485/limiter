package algorithm;

import grpc.Admin;
import grpc.Api;

import java.util.List;

public class BalanceAlgo implements Algo{

    @Override
    public double[] getQuota(long capacity, List<Admin.Client> clients, Api.GetCapacityReq req) {
        Admin.Client old = AlgoUtils.get(clients, req.getClientId());
        double available=capacity-AlgoUtils.sumHas(clients);
        double clientsNum=clients.size();
        double wants=req.getWants();
        if(old!=null){
            available+=old.getGets();
        } else {
            clientsNum++;
        }
        if(available<0){
            available=0;
        }
        if(available>capacity){
            available=capacity;
        }
        double avg=capacity/clientsNum;
        if(wants<=avg){
            return new double[]{wants,(available-wants)/clientsNum};
        }
        double gets=Math.max(Math.min(available-avg,wants-avg),0)+avg;
        double reserve=Math.max(available-gets,0)/clientsNum;
        return new double[]{gets,reserve};
    }

}
