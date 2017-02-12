import rmi.*;

import java.net.InetSocketAddress;

public class PingPongServer {
    protected static Skeleton<PingServerInterface> skeleton;
    protected static PingServer server = null;
    public static void main(String[] args){
        server = new PingServer();
        InetSocketAddress address = new InetSocketAddress(1234);
        skeleton = new Skeleton<>(PingServerInterface.class, server, address);
        try {
            skeleton.start();
        } catch (RMIException e){
            e.printStackTrace();
        }
        System.out.println("[Server] Server will be terminated in 10 seconds.");
        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        skeleton.stop();
        System.out.println("[Server] Server stop.");
    }
}