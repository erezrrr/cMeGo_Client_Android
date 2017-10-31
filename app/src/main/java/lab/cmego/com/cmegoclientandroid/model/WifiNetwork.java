package lab.cmego.com.cmegoclientandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

// May be used across multiple accounts/checkpoints
public class WifiNetwork {

    @SerializedName("id")
    private String mId;
    @SerializedName("bssid")
    private String mBssid;
    @SerializedName("ssid")
    private String mSsid;
    @SerializedName("password")
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
