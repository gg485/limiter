package algorithm;

import grpc.Admin;
import grpc.Api;

import java.util.List;

public interface Algo {
    double[]getQuota(long capacity, List<Admin.Client>clients, Api.GetCapacityReq req);
}
