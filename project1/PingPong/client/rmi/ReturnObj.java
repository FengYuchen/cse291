package rmi;

import java.io.Serializable;

/**
 * Created by tom on 2/11/2017.
 */
public class ReturnObj implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object returnobj = null;
    private boolean hasEx = false;
    public ReturnObj(Object obj, boolean ex){
        this.returnobj = obj;
        this.hasEx = ex;
    }
    public Object getObject(){
        return this.returnobj;
    }
    public boolean hasException(){
        return this.hasEx;
    }
}
