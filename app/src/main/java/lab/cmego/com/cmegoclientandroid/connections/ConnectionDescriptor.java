package lab.cmego.com.cmegoclientandroid.connections;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 1/15/2017.
 */

public abstract class ConnectionDescriptor {

    @SerializedName("type")
    protected String mType;

    public ConnectionDescriptor(String type){
        mType = type;
    }

    public abstract String describe();
}
