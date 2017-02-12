import rmi.*;

import java.net.InetSocketAddress;

public class PingPongClient{
    protected static PingServerInterface stub = null;
    public static void main(String[] args){
        if (args.length != 3){
            System.err.println("Please follow: java PingPongClient <hostname> <port> <id>");
            System.exit(1);
        }
        String hostname = args[0];
        int port = Integer.valueOf(args[1]);
        int idnumber = Integer.valueOf(args[2]);
        InetSocketAddress address = new InetSocketAddress(hostname,port);
        stub = PingServerFactory.makePingServer(address);
        int count = 0;
        try {
            for (int i = 0; i < 4; i++){
                String res = stub.ping(idnumber);
                int num = Integer.valueOf(res.substring(4));
                if(num == idnumber) count++;
            }
            System.out.println("[Client] 4 Tests Completed, "+String.valueOf(4 - count) + " Tests Failed");
        } catch (RMIException e){
            e.printStackTrace();
        }

    }
}