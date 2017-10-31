package lab.cmego.com.cmegoclientandroid.content;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.CheckPoint;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;

/**
 * Created by Owner on 31/10/2017.
 */

public class AllDataForUser {

    @SerializedName("user")
    private User mUser;

    @SerializedName("memberships")
    private List<Membership> mMemberships;

    @SerializedName("controllers")
    private List<Controller> mControllers;

    @SerializedName("checkpoints")
    private List<CheckPoint> mCheckPoints;

    @SerializedName("wifi_networks")
    private List<WifiNetwork> mWifiNetworks;

    public User getUser() {
        return mUser;
    }

    public List<Membership> getMemberships() {
        return mMemberships;
    }

    public List<Controller> getControllers() {
        return mControllers;
    }

    public List<CheckPoint> getCheckPoints() {
        return mCheckPoints;
    }

    public List<WifiNetwork> getWifiNetworks() {
        return mWifiNetworks;
    }
}
