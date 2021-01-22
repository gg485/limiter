package example;

import client.Waiter;

import java.net.UnknownHostException;

public class MockClient {
    static Waiter waiter;

    static {
        try {
            waiter = new Waiter("127.0.0.1",2181,"mock client");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            for(int i=0;i<1000;i++){
                waiter.acquire();
                System.out.println(System.currentTimeMillis()+"时得到锁！");
            }
            waiter.close();
        } catch (UnknownHostException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
