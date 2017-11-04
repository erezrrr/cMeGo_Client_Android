package lab.cmego.com.cmegoclientandroid.connections;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 2/1/2017.
 */

public class NetConfig {

    @SerializedName("internal")
    private WifiConnectionDescriptor mInternal;

    @SerializedName("external")
    private WifiConnectionDescriptor mExternal;

    public NetConfig(WifiConnectionDescriptor internal, WifiConnectionDescriptor external) {
        mInternal = internal;
        mExternal = external;
    }

    public WifiConnectionDescriptor getInternal() {
        return mInternal;
    }

    public WifiConnectionDescriptor getExternal() {
        return mExternal;
    }
}
