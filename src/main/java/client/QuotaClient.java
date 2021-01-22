package client;

import grpc.Api;
import grpc.QuotaGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class QuotaClient {
    private final ManagedChannel channel;
    private final QuotaGrpc.QuotaBlockingStub blockingStub;
    private final Logger logger=Logger.getLogger(QuotaClient.class.getName());

    public QuotaClient(String addr){
        channel= ManagedChannelBuilder.forTarget(addr).usePlaintext().build();
        blockingStub=QuotaGrpc.newBlockingStub(channel);
    }
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public Api.GetCapacityResp getCapacity(Api.GetCapacityReq req){
        try {
            return blockingStub.getCapacity(req);
        }catch (StatusRuntimeException e){
            logger.error("rpc failed",e);
            return null;
        }
    }
    public void releaseCapacity(Api.ReleaseCapacityReq req){
        try {
             blockingStub.releaseCapacity(req);
        }catch (StatusRuntimeException e){
            logger.error("rpc failed",e);
        }
    }
}
