package rmi;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by tom on 2/11/2017.
 */
public class RMIInvocationHandler implements InvocationHandler, Serializable {
    private InetSocketAddress address;
    private Class RMIClass;
    public RMIInvocationHandler(InetSocketAddress _address, Class c){
        this.address = _address;
        this.RMIClass = c;
    }
    public InetSocketAddress getAddress(){
        return address;
    }
    public void setAddress(InetSocketAddress _address){
        address = _address;
    }
    public Class getRMIClass(){
        return RMIClass;
    }
    public void setRMIClass(Class _RMIClass){
        RMIClass = _RMIClass;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodname = method.getName();
        switch (methodname){
            case "hashCode":
                try {
                    this.RMIClass.getMethod("hashCode", method.getParameterTypes());

                } catch (Exception e){
                    return this.hashCode();
                }
                break;
            case "toString":
                try{
                    this.RMIClass.getMethod("toString",method.getParameterTypes());
                }catch (Exception e){
                    return this.toString();
                }
                break;
            case "equals":
                try{
                    this.RMIClass.getMethod("equals",method.getParameterTypes());
                }catch (Exception e){
                    if (!method.getReturnType().getName().equals("boolean") || !(method.getParameterTypes().length == 1)
                            || !method.getParameterTypes()[0].getName().equals("java.lang.Object"))
                        return false;
                    if (args[0] == null || !Proxy.isProxyClass(args[0].getClass()))
                        return false;
                    RMIInvocationHandler other = (RMIInvocationHandler) Proxy.getInvocationHandler(args[0]);
                    RMIInvocationHandler my = (RMIInvocationHandler) Proxy.getInvocationHandler(proxy);
                    if (other.getRMIClass().equals(my.getRMIClass()) && other.getAddress().equals(my.getAddress()))
                        return true;
                    return false;
                }
                break;
        }
        boolean isRMI = false;
        for (Class<?> c : method.getExceptionTypes()){
            if (c.getName().equals(RMIException.class.getName())){
                isRMI = true;
                break;
            }
        }
        if (!isRMI)
            throw new Exception("Invoked method doesn't throw a RMIException!!");
        ReturnObj returnObj = null;
        try{
            Socket conn = new Socket(address.getHostName(),address.getPort());
            ObjectInputStream input = new ObjectInputStream(conn.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(conn.getOutputStream());
            output.writeObject(method.getName());
            output.writeObject(method.getParameterTypes());
            output.writeObject(method.getReturnType().getName());
            output.writeObject(args);
            returnObj = (ReturnObj) input.readObject();
            conn.close();
        } catch (Exception e){
            throw new RMIException(e.getCause());
        }
        if (returnObj.hasException())
            throw (Exception) returnObj.getObject();
        return returnObj.getObject();
    }
    @Override
    public int hashCode(){
        //return (address.toString() + RMIClass.getName().toString()).hashCode();
        return this.address.hashCode();
    }
    @Override
    public String toString(){
        return  "RMIClass: " + RMIClass.getCanonicalName() + "\n" +
                "HostnName: " + address.getHostName() + "\n"        +
                "Port: " + String.valueOf(address.getPort())+"\n";

    }

}
