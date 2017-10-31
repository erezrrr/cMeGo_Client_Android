package lab.cmego.com.cmegoclientandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

// One per controller
public class BluetoothDevice {

    @SerializedName("macAddress")
    private String mMacAddress;
    @SerializedName("name")
    private String mName;

    public BluetoothDevice() {
    }

    public BluetoothDevice(String macAddress, String name) {
        mMacAddress = macAddress;
        mName = name;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public String getName() {
        return mName;
    }
}
