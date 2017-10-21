package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

// This is the paying entity

@IgnoreExtraProperties
public class Client {

    public enum Type { PRIVATE, BUISINESS, HOUSE_COMMITTEE }

    private String mId;

    private Type mType;

    private String mProfileId;

    public Client() {}

    public Client(Type type, String id, String profileId) {
        mType = type;
        mId = id;
        mProfileId = profileId;
    }

    public Type getType() {
        return mType;
    }

    public String getProfileId() {
        return mProfileId;
    }

    public String getId() {
        return mId;
    }
}
