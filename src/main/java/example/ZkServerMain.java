package example;

import zk.ZkServer;

import java.io.IOException;

public class ZkServerMain {


    public static void main(String[] args) throws IOException, InterruptedException {
        ZkServer zkServer=new ZkServer(50001);
        zkServer.start();
        zkServer.blockUntilShutDown();
    }
}
