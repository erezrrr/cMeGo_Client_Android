package lab.cmego.com.cmegoclientandroid.interfaces;

/**
 * Created by Amit Ishai on 2/1/2017.
 */

public interface ResultListener<T>{
    void onResult(T t);
    void onError(Exception e);
}
