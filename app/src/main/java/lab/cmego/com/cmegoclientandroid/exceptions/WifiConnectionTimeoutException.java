package lab.cmego.com.cmegoclientandroid.exceptions;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class WifiConnectionTimeoutException extends Exception {

    public WifiConnectionTimeoutException(String ssid) {
        super("Timeout while trying to connect to: " + ssid);
    }
}
