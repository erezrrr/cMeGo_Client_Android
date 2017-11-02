package lab.cmego.com.cmegoclientandroid.content;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.Checkpoint;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;

/**
 * Created by Owner on 31/10/2017.
 */

public class AllDataForUser {

    private User mUser;

    private List<Membership> mMemberships;

    private List<Controller> mControllers;

    private List<Checkpoint> mCheckpoints;

    private List<Gate> mGates;


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

    public List<Checkpoint> getCheckpoints() {
        return mCheckpoints;
    }

    public List<WifiNetwork> getWifiNetworks() {
        return mWifiNetworks;
    }

    public List<Gate> getGates() {
        return mGates;
    }
}
