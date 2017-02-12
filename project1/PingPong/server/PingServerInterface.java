import rmi.*;

public interface PingServerInterface{
    String ping(int idNumber) throws RMIException;
}