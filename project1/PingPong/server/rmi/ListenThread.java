package rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tom on 2/12/2017.
 */
public class ListenThread<T> extends Thread {
    private Skeleton<?> skeleton = null;
    private ServerSocket listener = null;
    private Exception caughtException = null;
    private Class<T> c = null;
    public ListenThread(Skeleton<?> skeleton, Class<T> c, ServerSocket serverSocket){
        this.skeleton = skeleton;
        this.c = c;
        this.listener = serverSocket;
    }
    public void run(){
        while(this.skeleton.getStatus() && !this.isInterrupted()){
            try {
                Socket connection = listener.accept();
                Work worker = new Work(this.skeleton, c, connection);
                if (skeleton.getStatus())
                    worker.start();
            } catch (IOException e) {
                if (!this.skeleton.getStatus() || !this.skeleton.listen_error(e) )
                {
                    //this.skeleton.setStatus(false);
                    this.interrupt();
                    this.caughtException = e;
                }
            }

        }
        this.skeleton.stopped(this.caughtException);
    }
}
