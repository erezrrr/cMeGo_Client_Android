package lab.cmego.com.cmegoclientandroid.model.gate;

import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

public class Gate {

    public enum Type{SINGLE_DOOR, DOUBLE_DOOR, SINGLE_ARM, SLIDING}
    private String mId;
    private Type mType;
    private String mThumbnailUrl;
    private BluetoothDevice mBluetoothDevice;
    private String mName;

    public Gate() {
    }

    public Gate(Type type) {
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Type getType() {
        return mType;
    }

    public String getThumbnailUrl() {
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
