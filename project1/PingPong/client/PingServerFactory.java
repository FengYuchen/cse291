import rmi.*;

import java.net.InetSocketAddress;

public class PingServerFactory{
    public static PingServerInterface makePingServer(InetSocketAddress address){
        return Stub.create(PingServerInterface.class, address);
    }
}