package lab.cmego.com.cmegoclientandroid.exceptions;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class EnableWifiNetworkException extends Exception{

    public EnableWifiNetworkException(int netId, String ssid) {
        super("Couldn't enable wifi network. NetId:  "  + netId + ", ssid: " + ssid);
    }
}
