package lab.cmego.com.cmegoclientandroid.proximity;

import java.util.ArrayList;

import lab.cmego.com.cmegoclientandroid.ble.proximity.BleProximityResolver;
import lab.cmego.com.cmegoclientandroid.connections.ConnectionStateChangedListener;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.wifi.ConnectedNetworkResolver;

/**
 * Created by Owner on 04/11/2017.
 */

public class ProximityStateMachine implements ConnectionStateChangedListener, ContentProvider.ContentProviderInterface, BleProximityResolver.BleProximityListener, ConnectedNetworkResolver.ConnectedNetworkListener {

    public enum ProximityState { DEFAULT, ONLY_CLOSE, ONLY_CONNECTED, CONNECTED_AND_CLOSE };

    private ProximityState mState = ProximityState.DEFAULT;

    private static ProximityStateMachine sInstance;
    private ArrayList<ProximityStateListener> mListeners = new ArrayList<>();
    private BleProximityResolver mBleProximityResolver;
    private ConnectedNetworkResolver mConnectedNetworkResolver;

    private WifiNetwork mConnectedNetwork;
    private Gate mClosestGate;

    private ProximityStateMachine(){}

    public static ProximityStateMachine getInstance(){
        if(sInstance == null){
            sInstance = new ProximityStateMachine();
        }

        return sInstance;
    }

    public void init(){
        ContentProvider.getInstance().addListener(this);

        mConnectedNetworkResolver = new ConnectedNetworkResolver();
        mConnectedNetworkResolver.addListener(this);
        mConnectedNetworkResolver.start();

        mBleProximityResolver = new BleProximityResolver();
        mBleProximityResolver.addListener(this);
        mBleProximityResolver.start();

        resolveAndNotifyIfStateChanged();
    }

    private void resolveAndNotifyIfStateChanged(){
        resolveState();
    }

    private void resolveState() {

        WifiNetwork currentWifi = mConnectedNetwork;
        Gate currentGate = mClosestGate;

        WifiNetwork newConnectedNetwork = mConnectedNetworkResolver.getConnectedNetwork();
        BluetoothDevice newClosestBle = mBleProximityResolver.getClosestBle();
        Gate newGate = null;

        if(newClosestBle != null){
            newGate = ContentProvider.getInstance().getGateForBle(newClosestBle.getMacAddress());
        }

        if(gatesAreDifferent(currentGate, newGate) || wifiNetworkAreDifferent(currentWifi, newConnectedNetwork)){

            // There has been a change, we can do stuff now.
            mState = resolveProximityState(newGate, newConnectedNetwork);
            mConnectedNetwork = newConnectedNetwork;
            mClosestGate = newGate;

            notifyListeners();
        }
     }

    public ProximityState getState() {
        return mState;
    }

    // TODO fix this up
    private static boolean wifiNetworkAreDifferent(WifiNetwork a, WifiNetwork b) {
        if(a == null && b == null){
            return false;
        } else if(a == null || b == null){
            return true;
        }

        return !a.getBssid().equals(b.getBssid());
    }

    private boolean gatesAreDifferent(Gate a, Gate b) {
        if(a == null && b == null){
            return false;
        } else if(a == null || b == null){
            return true;
        }

        return !a.getId().equals(b.getId());
    }

    public static ProximityState resolveProximityState(Gate gate, WifiNetwork wifiNetwork) {

        if(wifiNetwork == null && gate == null){
            return ProximityState.DEFAULT;
        } else if(gate == null){
            return ProximityState.ONLY_CONNECTED;
        } else {

            WifiNetwork wifiNetworkForGate = ContentProvider.getInstance().getWifiNetworkForGate(gate.getId());

            if(wifiNetworkAreDifferent(wifiNetwork, wifiNetworkForGate)){
                return ProximityState.ONLY_CLOSE;
            }

            return ProximityState.CONNECTED_AND_CLOSE;
        }
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
        resolveAndNotifyIfStateChanged();
    }

    @Override
    public void onConnectionError(String errorMessage) {
        resolveAndNotifyIfStateChanged();
    }

    @Override
    public void onContentRefreshed() {
        resolveAndNotifyIfStateChanged();
    }

    @Override
    public void onBleProximityUpdate() {
        resolveAndNotifyIfStateChanged();
    }

    public Gate getClosestGate() {
        return mClosestGate;
    }

    @Override
    public void onConnectedNetworkUpdate() {
        resolveAndNotifyIfStateChanged();
    }

    public boolean isConnectedToNetwork(WifiNetwork wifiNetwork) {
        return mConnectedNetworkResolver.isConnectedToNetwork(wifiNetwork);
    }

    public WifiNetwork getConnectedNetwork() {
        return mConnectedNetwork;
    }

    public interface ProximityStateListener{
        void onProximityStateChanged();
    }
}
