package lab.cmego.com.cmegoclientandroid.proximity;

import java.util.ArrayList;

import lab.cmego.com.cmegoclientandroid.connections.ConnectionManager;
import lab.cmego.com.cmegoclientandroid.connections.ConnectionStateChangedListener;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;

/**
 * Created by Owner on 04/11/2017.
 */

public class ProximityStateMachine implements ConnectionStateChangedListener {

    private static ProximityStateMachine sInstance;
    private String mCurrentWifi;
    private String mCurrentBle;
    private ArrayList<ProximityStateListener> mListeners = new ArrayList<>();

    private ProximityStateMachine(){}

    public static ProximityStateMachine getInstance(){
        if(sInstance == null){
            sInstance = new ProximityStateMachine();
        }

        return sInstance;
    }

    public void init(){
        ConnectionManager.getSharedInstance().addConnectionStateChangedListener(this);
        setCurrentWifi();
    }
    public void addProximityStateListener(ProximityStateListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    private void notifyListeners() {
        for (ProximityStateListener listener : mListeners) {
            listener.onProximityStateChanged();
        }
    }

    @Override
    public void onConnectionStateChanged() {
        setCurrentWifi();
        notifyListeners();
    }

    private void setCurrentWifi(){
        mCurrentWifi = ConnectionManager.getSharedInstance().getCurrentSsid();
    }


    @Override
    public void onConnectionError(String errorMessage) {
        notifyListeners();
    }

    public boolean isConnectedToNetwork(WifiNetwork wifiNetwork) {
        return wifiNetwork.getSsid().equals(mCurrentWifi);
    }

    public interface ProximityStateListener{
        void onProximityStateChanged();
    }
}
