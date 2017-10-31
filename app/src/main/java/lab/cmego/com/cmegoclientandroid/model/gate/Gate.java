package lab.cmego.com.cmegoclientandroid.model.gate;

import com.google.gson.annotations.SerializedName;

import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

public class Gate {
    public enum Type{SINGLE_DOOR, DOUBLE_DOOR}

    @SerializedName("type")
    private Type mType;

    @SerializedName("thumbnailUrl")
    private String mThumbnailUrl;

    @SerializedName("bluetoothDevice")
    private BluetoothDevice mBluetoothDevice;

    public Gate() {
    }

    public Gate(Type type) {
        mType = type;
    }

    public Type getType() {
        return mType;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        mBluetoothDevice = bluetoothDevice;
    }
}
