package lab.cmego.com.cmegoclientandroid.proximity;

import android.bluetooth.le.ScanResult;
import android.net.wifi.WifiInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.ble.BleScanner;
import lab.cmego.com.cmegoclientandroid.connections.ConnectionManager;
import lab.cmego.com.cmegoclientandroid.connections.ConnectionStateChangedListener;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;

/**
 * Created by Owner on 04/11/2017.
 */

public class ProximityStateMachine implements ConnectionStateChangedListener, ContentProvider.ContentProviderInterface, BleScanner.ScanBleInterface {

    private static ProximityStateMachine sInstance;
    private WifiInfo mCurrentWifiNetwork;
    private String mConnectedWifiNetworkId;
    private ArrayList<ProximityStateListener> mListeners = new ArrayList<>();
    private List<WifiNetwork> mWifiNetworksToListenFor = new ArrayList<>();
    private List<BluetoothDevice> mBlesToListenFor;
    private HashMap<String, ScanResult> mCurrentBleResults;
    private BluetoothDevice mClosestBle;
    private Gate mClosestGate;

    private ProximityStateMachine(){}

    public static ProximityStateMachine getInstance(){
        if(sInstance == null){
            sInstance = new ProximityStateMachine();
        }

        return sInstance;
    }

    public void init(){
        ConnectionManager.getSharedInstance().addConnectionStateChangedListener(this);
        ContentProvider.getInstance().addListener(this);
        BleScanner.getInstance().addListener(this);
        refreshResolveNotify();
    }

    private void refreshResolveNotify(){
        refreshData();
        resolveState();
        notifyListeners();
    }

    private void resolveState() {
        setCurrentWifi();
        setCurrentBle();
    }

    private void setCurrentBle() {
        mCurrentBleResults = BleScanner.getInstance().getCurrentResults();

        if(mCurrentBleResults == null || mCurrentBleResults.size() == 0){
            mClosestBle = null;
            mClosestGate = null;
            return;
        }

        // find closest...

        int maxRssi = -1000;
        BluetoothDevice closestBle = null;

        for(ScanResult scanResult : mCurrentBleResults.values()){

            BluetoothDevice temp = getMatchingBle(scanResult);

            if(temp != null){

                if(scanResult.getRssi() > maxRssi){
                    maxRssi = scanResult.getRssi();
                    closestBle = temp;
                }

            }

        }

        if(closestBle == null){
            mClosestBle = null;
            mClosestGate = null;
            return;
        }

        mClosestBle = closestBle;
        mClosestGate = ContentProvider.getInstance().getGateForBle(mClosestBle.getMacAddress());
    }

    private BluetoothDevice getMatchingBle(ScanResult scanResult) {
        if(mCurrentBleResults == null || mCurrentBleResults.size() == 0){
            return null;
        }

        for(BluetoothDevice bluetoothDevice : mBlesToListenFor){
            if(bluetoothDevice.getMacAddress().equals(scanResult.getDevice().getAddress())){
                return bluetoothDevice;
            }
        }

        return null;
    }

    private void refreshData() {
        mWifiNetworksToListenFor = ContentProvider.getInstance().getAllWifiNetworks();
        mBlesToListenFor = ContentProvider.getInstance().getAllBles();
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

    private void setCurrentWifi(){
        mCurrentWifiNetwork = ConnectionManager.getSharedInstance().getCurrentlyConnectedWifiNetwork();

        if(mCurrentWifiNetwork == null){
            //Not connected to wifi
            mConnectedWifiNetworkId = null;
            return;
        }

        for(WifiNetwork wifiNetwork : mWifiNetworksToListenFor){
            if(wifiNetwork.getBssid().equals(mCurrentWifiNetwork.getBSSID())){
                mConnectedWifiNetworkId = wifiNetwork.getId();
            }
        }
    }


    public boolean isConnectedToNetwork(WifiNetwork wifiNetwork) {

        if(mCurrentWifiNetwork == null){
            return false;
        }

        String current = mCurrentWifiNetwork.getBSSID();
        String checked = wifiNetwork.getBssid();

        return current.equals(checked);
    }

    @Override
    public void onConnectionStateChanged() {
        refreshResolveNotify();
    }

    @Override
    public void onConnectionError(String errorMessage) {
        refreshResolveNotify();
    }

    @Override
    public void onContentRefreshed() {
        refreshResolveNotify();
    }

    @Override
    public void onScan(int callbackType, ScanResult result) {
        refreshResolveNotify();
    }

    @Override
    public void onChange() {
        refreshResolveNotify();
    }

    public Gate getClosestGate() {
        return mClosestGate;
    }

    public interface ProximityStateListener{
        void onProximityStateChanged();
    }
}
