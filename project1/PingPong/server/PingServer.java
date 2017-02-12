import rmi.*;
public class PingServer implements PingServerInterface{
    public String ping(int idNumber) throws RMIException{
        return "Pong" + idNumber;
    }

}