package lab.cmego.com.cmegoclientandroid.connections;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public interface WifiConnectionListener {
    void onConnected(String ssid);
    void onDisconnected();
    void onWifiEnabled();
    void onWifiDisabled();
}
