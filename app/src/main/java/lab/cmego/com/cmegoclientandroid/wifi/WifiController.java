package lab.cmego.com.cmegoclientandroid.wifi;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import lab.cmego.com.cmegoclientandroid.connections.RetryWifiConnection;
import lab.cmego.com.cmegoclientandroid.connections.WifiConnectionDescriptor;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.proximity.ProximityStateMachine;

/**
 * Created by Owner on 11/11/2017.
 */

public class WifiController implements ProximityStateMachine.ProximityStateListener {

    private static WifiController sInstance;
    private Context mContext;

    private HashMap<String, RetryWifiConnection> mConnections = new HashMap<>();

    public static WifiController getInstance(){
        if(sInstance == null){
            sInstance = new WifiController();
        }

        return sInstance;
    }

    public void init(Context context){
        mContext = context;
    }

    private WifiController(){
        ProximityStateMachine.getInstance().addProximityStateListener(this);
    }

    @Override
    public void onProximityStateChanged() {
        Log.d("condivityAutomatically","WifiController onProximityStateChanged : " + ProximityStateMachine.getInstance().getState());

        ProximityStateMachine.ProximityState state = ProximityStateMachine.getInstance().getState();

        if(state == ProximityStateMachine.ProximityState.ONLY_CLOSE){
            Gate closestGate = ProximityStateMachine.getInstance().getClosestGate();
            WifiNetwork wifiNetwork = ContentProvider.getInstance().getWifiNetworkForGate(closestGate.getId());
            connectToNetwork(wifiNetwork);
        }
    }

    private void connectToNetwork(WifiNetwork wifiNetwork) {

        RetryWifiConnection connection = mConnections.get(wifiNetwork.getBssid());

        if(connection == null){
            connection = new RetryWifiConnection(mContext,
                new WifiConnectionDescriptor()
                        .setPassword(wifiNetwork.getPassword())
                        .setSsid(wifiNetwork.getSsid()));
        }

        mConnections.put(wifiNetwork.getBssid(), connection);

        connection.connect();
    }

}
