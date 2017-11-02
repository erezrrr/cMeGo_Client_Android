package lab.cmego.com.cmegoclientandroid.model;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

// May be used across multiple accounts/checkpoints
public class WifiNetwork {

    private String mId;
    private String mBssid;
    private String mSsid;
    private String mPassword;

    public WifiNetwork(String id, String bssid, String ssid, String password) {
        mId = id;
        mBssid = bssid;
        mSsid = ssid;
        mPassword = password;
    }

    public String getId() {
        return mId;
    }

    public String getBssid() {
        return mBssid;
    }

    public String getSsid() {
        return mSsid;
    }

    public String getPassword() {
        return mPassword;
    }
}
