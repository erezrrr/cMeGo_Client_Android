package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

@IgnoreExtraProperties
public class Controller {

    @SerializedName("id")
    private String mId;
    @SerializedName("version")
    private String mVersion;
    @SerializedName("bluetoothDevice")
    private BluetoothDevice mBluetoothDevice;
    @SerializedName("unixUserName")
    private String mUnixUserName;
    @SerializedName("unixPassword")
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
