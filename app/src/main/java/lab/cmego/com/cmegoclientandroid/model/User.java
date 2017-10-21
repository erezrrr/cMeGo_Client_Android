package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.PropertyName;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

public class User {

    @PropertyName("id")
    private String mId;

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
