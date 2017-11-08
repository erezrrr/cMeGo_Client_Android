package lab.cmego.com.cmegoclientandroid.wifi;

import android.net.wifi.WifiInfo;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.connections.ConnectionManager;
import lab.cmego.com.cmegoclientandroid.connections.ConnectionStateChangedListener;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;

/**
 * Created by Owner on 08/11/2017.
 */

public class ConnectedNetworkResolver implements ConnectionStateChangedListener, ContentProvider.ContentProviderInterface {

    private ArrayList<ConnectedNetworkListener> mListeners = new ArrayList<>();
    private WifiNetwork mConnectedWifiNetwork;

    public void addListener(ConnectedNetworkListener listener){
        if(!mListeners.contains(listener)){
            mListeners.add(listener);
        }
    }

    public void removeListener(ConnectedNetworkListener listener){
        if(mListeners.contains(listener)){
            mListeners.remove(listener);
        }
    }

    public void start(){
        ConnectionManager.getSharedInstance().addConnectionStateChangedListener(this);
        ContentProvider.getInstance().addListener(this);
        resolveStateAndNotify();
    }

    public void stop(){}

    private void resolveStateAndNotify() {
        WifiNetwork temp = mConnectedWifiNetwork;

        setConnectedWifiNetwork();

        if(stateChanged(temp, mConnectedWifiNetwork)){
            notifyUpdate();
        }
    }

    private boolean stateChanged(WifiNetwork current, WifiNetwork previous) {
        if(current == null && previous == null){
            return false;
        } else if(current == null || previous == null){
            return true;
        }

        return !current.getBssid().equals(previous.getBssid());
    }

    private void notifyUpdate() {
        for(ConnectedNetworkListener listener : mListeners){
            listener.onConnectedNetworkUpdate();
        }
    }

    private void setConnectedWifiNetwork(){
        WifiInfo currentWifiNetwork = ConnectionManager.getSharedInstance().getCurrentlyConnectedWifiNetwork();
        List<WifiNetwork> allWifiNetworks = ContentProvider.getInstance().getAllWifiNetworks();

        if(currentWifiNetwork == null){
            //Not connected to wifi
            mConnectedWifiNetwork = null;
            return;
        }

        for(WifiNetwork wifiNetwork : allWifiNetworks){
            if(wifiNetwork.getBssid().equals(currentWifiNetwork.getBSSID())){
                mConnectedWifiNetwork = wifiNetwork;
            }
        }
    }


    public boolean isConnectedToNetwork(WifiNetwork wifiNetwork) {

        if(mConnectedWifiNetwork == null){
            return false;
        }

        String current = mConnectedWifiNetwork.getBssid();
        String checked = wifiNetwork.getBssid();

        return current.equals(checked);
    }


    @Override
    public void onConnectionStateChanged() {
        resolveStateAndNotify();
    }

    @Override
    public void onConnectionError(String errorMessage) {
        resolveStateAndNotify();
    }

    @Override
    public void onContentRefreshed() {
        resolveStateAndNotify();
    }

    public WifiNetwork getConnectedNetwork() {
        return mConnectedWifiNetwork;
    }

    public interface ConnectedNetworkListener {
        void onConnectedNetworkUpdate();
    }
}
