package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

@IgnoreExtraProperties
public class Location {

    public enum Type { PRIVATE, APT_BUILDING, HOTEL, OFFICE_BUILDING}

    private String mId;

    private String mAddress;
    private Type mType;

    public Location() {
    }

    public Location(String id, String address, Type type) {
        mId = id;
        mAddress = address;
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public String getAddress() {
        return mAddress;
    }

    public Type getType() {
        return mType;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setType(Type type) {
        mType = type;
    }
}
