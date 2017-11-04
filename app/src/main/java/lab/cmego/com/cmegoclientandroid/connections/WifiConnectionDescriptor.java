package lab.cmego.com.cmegoclientandroid.connections;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 1/15/2017.
 */

public class WifiConnectionDescriptor extends ConnectionDescriptor {

    @SerializedName("ssid")
    private String mSsid;

    @SerializedName("password")
    private String mPassword;

    public WifiConnectionDescriptor() {
        super(ConnectionType.WIFI);
    }

    public String getSsid() {
        return String.format("\"%s\"", mSsid);
    }

    public void setSsid(String ssid) {
        mSsid = ssid;
    }

    public String getPassword() {
        return String.format("\"%s\"", mPassword);
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String describe() {
        return "Wifi: SSID- " + mSsid;
    }
}
