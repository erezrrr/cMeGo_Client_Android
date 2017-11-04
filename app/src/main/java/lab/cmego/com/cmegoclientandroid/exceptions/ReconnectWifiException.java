package lab.cmego.com.cmegoclientandroid.exceptions;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class ReconnectWifiException extends Exception {

    public ReconnectWifiException(String ssid) {
        super("Couldn't reconnect to wifi: " + ssid);
    }
}
