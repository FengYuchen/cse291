package rmi;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.*;

/**
 * Created by tom on 2/12/2017.
 */
public class Work<T> extends Thread {
    private Skeleton<?> skeleton;
    private Socket socket;
    private Class<T> c = null;
    public Work(Skeleton<?> _skeleton, Class<T> _c, Socket _socket ){
        this.skeleton = _skeleton;
        this.c = _c;
        this.socket = _socket;
    }
    public void run(){

        try {
            ReturnObj result = null;
            ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());
            output.flush();
            ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
            String methodname = (String)input.readObject();
            Class<?>[] parameters = (Class[]) input.readObject();
            Method method = null;
            try {
                method = c.getMethod(methodname, parameters);
            } catch (Exception e){
                result = new ReturnObj(new RMIException(e.getCause()), true);
            }
            String resultType = (String) input.readObject();
            if (method != null)
                try {
                    Object tmp = method.invoke(this.skeleton.getServer(), (Object[]) input.readObject());
                    result = new ReturnObj(tmp, false);
                } catch (Throwable e){
                    result = new ReturnObj(e.getCause(), true);
                }
            output.writeObject(result);
            socket.close();
        } catch (Throwable e) {
            this.skeleton.service_error(new RMIException(e.getCause()));
        }
    }
}
