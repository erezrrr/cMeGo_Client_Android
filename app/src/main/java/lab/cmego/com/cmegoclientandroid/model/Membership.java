package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Constants.VehicleAuthenticationMethod;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

@IgnoreExtraProperties
public class Membership {

    public enum Type { SYSTEM_ADMIN, MANAGER, TENANT, GUEST }

    @PropertyName("id")
    private String mId;

    @PropertyName("type")
    private Type mType;

    @PropertyName("userId")
    private String mUserId;

    @PropertyName("vehicleId")
    private String mVehicleId;

    @PropertyName("checkpointId")
    private String mCheckpointId;

    @PropertyName("accountId")
    private String mAccountId;

    @PropertyName("activationDate")
    private long mActivationDate;

    @PropertyName("expirationDate")
    private long mExpirationDate;

    @PropertyName("internalAddress")
    private String mInternalAddress; // What .apt?

    // Additional authentication methods decided upon by user
    @PropertyName("userAuthenticationMethods")
    private List<UserAuthenticationMethod> mUserAuthenticationMethods = new ArrayList<>();

    @PropertyName("vehicleAuthenticationMethods")
    private List<VehicleAuthenticationMethod> mVehicleAuthenticationMethods = new ArrayList<>();

    public Membership() {
    }

    public String getId() {
        return mId;
    }

    public Membership setId(String id) {
        mId = id;
        return this;
    }

    public Membership setType(Type type) {
        mType = type;
        return this;
    }

    public Type getType() {
        return mType;
    }

    public String getUserId() {
        return mUserId;
    }

    public Membership setUserId(String userId) {
        mUserId = userId;
        return this;
    }



    public String getVehicleId() {
        return mVehicleId;
    }

    public Membership setVehicleId(String vehicleId) {
        mVehicleId = vehicleId;
        return this;
    }

    public long getActivationDate() {
        return mActivationDate;
    }

    public Membership setActivationDate(long activationDate) {
        mActivationDate = activationDate;
        return this;
    }

    public Membership setCheckpointId(String checkpointId) {
        mCheckpointId = checkpointId;
        return this;
    }

    public Membership setAccountId(String accountId) {
        mAccountId = accountId;
        return this;
    }

    public long getExpirationDate() {
        return mExpirationDate;
    }

    public Membership setExpirationDate(long expirationDate) {
        mExpirationDate = expirationDate;
        return this;
    }

    public String getInternalAddress() {
        return mInternalAddress;
    }

    public Membership setInternalAddress(String internalAddress) {
        mInternalAddress = internalAddress;
        return this;
    }

    public String getCheckpointId() {
        return mCheckpointId;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public List<UserAuthenticationMethod> getUserAuthenticationMethods() {
        return mUserAuthenticationMethods;
    }

    public Membership setUserAuthenticationMethods(ArrayList<UserAuthenticationMethod> userAuthenticationMethods) {
        mUserAuthenticationMethods = userAuthenticationMethods;
        return this;
    }

    public List<VehicleAuthenticationMethod> getVehicleAuthenticationMethods() {
        return mVehicleAuthenticationMethods;
    }

    public Membership setVehicleAuthenticationMethods(ArrayList<VehicleAuthenticationMethod> vehicleAuthenticationMethods) {
        mVehicleAuthenticationMethods = vehicleAuthenticationMethods;
        return this;
    }
}
