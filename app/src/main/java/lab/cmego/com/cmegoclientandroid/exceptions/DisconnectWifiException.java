package lab.cmego.com.cmegoclientandroid.exceptions;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class DisconnectWifiException extends Exception{
    public DisconnectWifiException(String ssid) {
        super("Couldn't disconnect from wifi. Ssid: " + ssid);
    }
}
