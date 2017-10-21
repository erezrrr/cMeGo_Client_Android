package lab.cmego.com.cmegoclientandroid.model;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

public class Profile {

    private String mId;
    private String mFirstName;
    private String mSurName;
    private String mProfilePicUrl;
    private String mMobileNumber;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Profile(String id, String firstName, String surName) {
        mId = id;
        mFirstName = firstName;
        mSurName = surName;
    }

    public Profile(String id, String firstName, String surName, String profilePicUrl, String mobileNumber) {
        mId = id;
        mFirstName = firstName;
        mSurName = surName;
        mProfilePicUrl = profilePicUrl;
        mMobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSurName() {
        return mSurName;
    }

    public void setSurName(String surName) {
        mSurName = surName;
    }

    public String getProfilePicUrl() {
        return mProfilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        mProfilePicUrl = profilePicUrl;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        mMobileNumber = mobileNumber;
    }

    public String getId() {
        return mId;
    }
}
