package example;

import server.QuotaServer;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

public class QuotaServerMain {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        String host = InetAddress.getLocalHost().getHostAddress();
        QuotaServer quotaServer=new QuotaServer(host,50002,host,50001);
        quotaServer.start();
        quotaServer.blockUntilShutDown();
    }
}
