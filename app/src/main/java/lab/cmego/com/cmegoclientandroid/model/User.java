package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

public class User {

    @SerializedName("id")
    @PropertyName("id")
    private String mId;

    @SerializedName("profileId")
    @PropertyName("profileId")
    private String mProfileId;

    public User() {
    }

    public User(String id, String profileId) {
        mId = id;
        mProfileId = profileId;
    }

    public String getId() {
        return mId;
    }

    public String getProfileId() {
        return mProfileId;
    }
}
