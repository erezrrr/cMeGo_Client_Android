package lab.cmego.com.cmegoclientandroid.model;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

// One per controller
public class BluetoothDevice {

    private String mMacAddress;
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
