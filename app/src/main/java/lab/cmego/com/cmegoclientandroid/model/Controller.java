package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

@IgnoreExtraProperties
public class Controller {

    private String mId;
    private String mVersion;
    private BluetoothDevice mBluetoothDevice;
    private String mUnixUserName;
    private String mUnixPassword;


    public Controller() {
    }

    public Controller(String id, String version, BluetoothDevice bluetoothDevice) {
        mId = id;
        mVersion = version;
        mBluetoothDevice = bluetoothDevice;
    }

    public String getId() {
        return mId;
    }

    public String getVersion() {
        return mVersion;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }
}
