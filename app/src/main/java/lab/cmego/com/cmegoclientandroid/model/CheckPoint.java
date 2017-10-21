package lab.cmego.com.cmegoclientandroid.model;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Constants.VehicleAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.model.gate.policy.MultipleGatePolicy;

/**
 * Created by Amit Ishai on 10/6/2017.
 */

public class CheckPoint {

    private String mId;

    private String mControllerId;
    private String mAccountId;

    private String mWifiNetworkId;

    // Physical gate description
    private List<Gate> mGates;

    private ArrayList<String> mMembershipIds;

    private MultipleGatePolicy mGatePolicy;

    // These are the base authentication methods decided upon by client.
    private List<UserAuthenticationMethod> mUserAuthenticationMethods;
    private List<VehicleAuthenticationMethod> mVehicleAuthenticationMethods;

    public CheckPoint() {
    }

    public CheckPoint(String id, String accountId, String controllerId, String wifiNetworkId, List <Gate> gates,
                      ArrayList<String> membershipIds, List<UserAuthenticationMethod>
                              userAuthenticationMethods, List<VehicleAuthenticationMethod>
                              vehicleAuthenticationMethods) {
        mId = id;
        mAccountId = accountId;
        mControllerId = controllerId;
        mWifiNetworkId = wifiNetworkId;
        mGates = gates;
        mMembershipIds = membershipIds;
        mUserAuthenticationMethods = userAuthenticationMethods;
        mVehicleAuthenticationMethods = vehicleAuthenticationMethods;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setControllerId(String controllerId) {
        mControllerId = controllerId;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public void setAccountId(String accountId) {
        mAccountId = accountId;
    }

    public void setWifiNetworkId(String wifiNetworkId) {
        mWifiNetworkId = wifiNetworkId;
    }

    public void setGates(List<Gate> gates) {
        mGates = gates;
    }

    public void setMembershipIds(ArrayList<String> membershipIds) {
        mMembershipIds = membershipIds;
    }

    public void setUserAuthenticationMethods(ArrayList<UserAuthenticationMethod>
                                                     userAuthenticationMethods) {
        mUserAuthenticationMethods = userAuthenticationMethods;
    }

    public void setVehicleAuthenticationMethods(ArrayList<VehicleAuthenticationMethod> vehicleAuthenticationMethods) {
        mVehicleAuthenticationMethods = vehicleAuthenticationMethods;
    }

    public List<UserAuthenticationMethod> getUserAuthenticationMethods() {
        return mUserAuthenticationMethods;
    }

    public List<VehicleAuthenticationMethod> getVehicleAuthenticationMethods() {
        return mVehicleAuthenticationMethods;
    }

    public String getId() {
        return mId;
    }

    public String getControllerId() {
        return mControllerId;
    }

    public String getWifiNetworkId() {
        return mWifiNetworkId;
    }

    public List<Gate> getGates() {
        return mGates;
    }

    public ArrayList<String> getMembershipIds() {
        return mMembershipIds;
    }

    public MultipleGatePolicy getGatePolicy() {
        return mGatePolicy;
    }

    public void setGatePolicy(MultipleGatePolicy gatePolicy) {
        mGatePolicy = gatePolicy;
    }
}
