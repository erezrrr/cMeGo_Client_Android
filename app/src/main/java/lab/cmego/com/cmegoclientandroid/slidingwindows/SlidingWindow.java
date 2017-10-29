package lab.cmego.com.cmegoclientandroid.slidingwindows;

import java.util.ArrayList;

/**
 * Created by Amit Ishai on 10/5/2017.
 */

public abstract class SlidingWindow<T> extends ArrayList<T>{

    public boolean add(T item){
        super.add(item);
        trim();
        return true;
    }

    protected abstract void trim();
}
