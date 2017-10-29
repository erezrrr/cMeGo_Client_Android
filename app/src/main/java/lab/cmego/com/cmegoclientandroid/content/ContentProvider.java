package lab.cmego.com.cmegoclientandroid.content;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.model.CheckPoint;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;

/**
 * Created by Owner on 29/10/2017.
 */

public class ContentProvider {

    private static ContentProvider sInstance;

    private User mUser;
    private List<Membership> mMemberships;
    private List<Controller> mControllers;
    private List<CheckPoint> mCheckPoints;
    private List<WifiNetwork> mWifiNetworks;

    private ContentProvider(){
    }

    public void init(){
        loadDataFromCache();
        refreshData();
    }

    private void refreshData() {

    }

    private void loadDataFromCache() {

    }

    public static ContentProvider getInstance(){
        if(sInstance == null){
            sInstance = new ContentProvider();
        }

        return sInstance;
    }

}
