package lab.cmego.com.cmegoclientandroid.connections;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public interface ConnectionStatusListener {
    void onConnected();
    void onDisconnected();
    void onError(Exception e);
}
