package lab.cmego.com.cmegoclientandroid.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

import lab.cmego.com.cmegoclientandroid.model.Billing.BillingDetails;

/**
 * Created by Amit Ishai on 9/26/2017.
 */

@IgnoreExtraProperties
public class Account {

    public enum Type { RESIDENTIAL, COMMERCIAL }

    private String mId;

    private String mLocationId;

    // The paying entity
    private String mClientId;

    private Type mType;

    private long mActivationDate;
    private long mExpirationDate;

    private ArrayList<String> mCheckpointIds;

    private BillingDetails mBillingDetails;

    public Account() {
    }

    public Account(String id, String locationId, String clientId, Type type, long activationDate, long
            expirationDate, ArrayList<String> checkpointIds, BillingDetails billingDetails) {
        mId = id;
        mLocationId = locationId;
        mClientId = clientId;
        mType = type;
        mActivationDate = activationDate;
        mExpirationDate = expirationDate;
        mCheckpointIds = checkpointIds;
        mBillingDetails = billingDetails;
    }

    public String getId() {
        return mId;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public String getClientId() {
        return mClientId;
    }

    public Type getType() {
        return mType;
    }

    public long getActivationDate() {
        return mActivationDate;
    }

    public long getExpirationDate() {
        return mExpirationDate;
    }

    public BillingDetails getBillingDetails() {
        return mBillingDetails;
    }

    public ArrayList<String> getCheckpointIds() {
        return mCheckpointIds;
    }
}
