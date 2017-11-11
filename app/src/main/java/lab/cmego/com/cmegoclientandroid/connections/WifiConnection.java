package lab.cmego.com.cmegoclientandroid.connections;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.exceptions.DisconnectWifiException;
import lab.cmego.com.cmegoclientandroid.exceptions.EnableWifiNetworkException;
import lab.cmego.com.cmegoclientandroid.exceptions.ReconnectWifiException;
import lab.cmego.com.cmegoclientandroid.exceptions.SetWifiEnabledException;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class WifiConnection implements Connection, WifiConnectionListener {
    private static final String TAG = WifiConnection.class.getSimpleName();

    private final WifiManager mWifiManager;
    private final Context mContext;
    private int mNetId = -1;
    private final WifiConfiguration mWifiConfiguration;

    protected WifiConnectionDescriptor mConnectionDescriptor;

    protected ConnectionState mState = ConnectionState.INITIAL;
    private ConnectionStatusListener mListener;

    protected void onReceivedConnectionError(Exception e) {
        if(mListener != null){
            mListener.onError(e);
        }
    }

    public WifiConnection(Context context, WifiConnectionDescriptor descriptor) {
        mContext = context;

        mWifiConfiguration = new WifiConfiguration();

        mConnectionDescriptor = descriptor;

        mWifiConfiguration.SSID = mConnectionDescriptor.getSsid();
        mWifiConfiguration.preSharedKey = mConnectionDescriptor.getPassword();

        mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);

        WifiReceiver.getSharedInstance().addListener(this);

        resolveState();
    }

    public void refreshState(){
        resolveState();
    }
//    public void destroy(){
//        WifiReceiver.getSharedInstance().removeListener(this);
//    }

    @Override
    public void connect() {

        if(mState == ConnectionState.CONNECTED){
            if(mListener != null){
                mListener.onConnected();
            }

            return;
        }

        synchronized (this){

            mState = ConnectionState.AWAITING_CONNECTION;

            if(mWifiManager.isWifiEnabled()){
                // GREAT! continue...

                Log.d(TAG, "WIFI ENABLED!! WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid());
                connectToWifi();

            } else {
                enableWifi();
                return;
            }
        }
    }

    private void enableWifi() {
        boolean success = mWifiManager.setWifiEnabled(true);

        Log.d(TAG, "TRY TO ENABLE WIFI WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid() + ", Success? " + success);

        if(!success){
            onReceivedConnectionError(new SetWifiEnabledException());
        }
    }

    private void connectToWifi() {
        Log.d(TAG, "connectToWifi IN WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid());

        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();

        boolean needToAdd = true;

        if(configuredNetworks == null){
            needToAdd = true;
        } else {

            for(WifiConfiguration configuration : configuredNetworks){
                if(configuration.SSID.equals(mConnectionDescriptor.getSsid())){
                    needToAdd = false;
                    mNetId = configuration.networkId;
                }
            }
        }

        if(needToAdd){
            mNetId = mWifiManager.addNetwork(mWifiConfiguration);
        }

        boolean success = mWifiManager.enableNetwork(mNetId, true);

        if(!success){
            onReceivedConnectionError(new EnableWifiNetworkException(mNetId, mConnectionDescriptor.getSsid()));
            return;
        }

        success = mWifiManager.reconnect();

        if(!success){
            onReceivedConnectionError(new ReconnectWifiException(mConnectionDescriptor.getSsid()));
            return;
        }
    }

    @Override
    public void disconnect() {
        Log.d(TAG, "disconnect WIFI: " + mConnectionDescriptor.getSsid());

        synchronized (this){

            if (mWifiManager.isWifiEnabled()) { //---wifi is turned on---
                mState = ConnectionState.AWAITING_DISCONNECTION;
                //---disconnect it first---
                boolean success = mWifiManager.disconnect();

                if(!success && mListener != null){
                    mListener.onError(new DisconnectWifiException(mConnectionDescriptor.getSsid()));
                }
            } else {

                if(mListener != null){
                    mListener.onDisconnected();
                }
            }
        }
    }

    @Override
    public void resolveState() {
        String currentSsid = getCurrentSsid();

        // Simple approach for now, not taking intermediate states (awaiting...) into consideration.
        if(!TextUtils.isEmpty(currentSsid) && currentSsid.equals(mConnectionDescriptor.getSsid())){
            mState = ConnectionState.CONNECTED;
        } else {
            mState = ConnectionState.DISCONNECTED;
        }
    }

    @Override
    public boolean isConnected() {
        return mState == ConnectionState.CONNECTED;
    }

    @Override
    public void setListener(ConnectionStatusListener listener) {
        mListener = listener;
    }

    @Override
    public ConnectionDescriptor getConnectionDescriptor() {
        return mConnectionDescriptor;
    }

    @Override
    public void onConnected(String ssid) {
        if(ssid.equals(mConnectionDescriptor.getSsid())){

            Log.d(TAG, "onConnected IN WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid());

            mState = ConnectionState.CONNECTED;

            if(mListener != null){
                mListener.onConnected();
            }
        }
    }

    @Override
    public void onDisconnected() {
        // WifiReceiver sends this event when there is no active wifi connection,
        // therefore, in this case, all wifi connections are disconnected.
        mState = ConnectionState.DISCONNECTED;

        Log.d(TAG, "onDisconnected IN WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid());

        if(mListener != null){
            mListener.onDisconnected();
        }
    }

    @Override
    public void onWifiEnabled() {

        Log.d(TAG, "onWifiEnabled IN WIFICONNECTION. mSsid: " + mConnectionDescriptor.getSsid());


        // I was waiting for wifi to be enabled bra.
        if(mState == ConnectionState.AWAITING_CONNECTION){
            connectToWifi();
        }
    }

    @Override
    public void onWifiDisabled() {
        // TODO Amit - 11/1/2017 This a patch.
        // When externally disabling Wifi, the app would most times NOT re-connect on it's own.
        // It seems that it has to do with the particular order of wifi events received from the system
        // We would try to enable wifi, receive a report of success, and then the wifi state would change to disabled,
        // and then wouldn't be re-enabled.
        // This patch seems to fix the issue.
        // Leaving a todo here to look into the issue a bit more some time down the road

//        enableWifi();
    }

    private String getCurrentSsid() {
        WifiInfo wifiInfo = ConnectionManager.getSharedInstance().getCurrentlyConnectedWifiNetwork();

        if(wifiInfo != null){
            return wifiInfo.getSSID();
        }

        return null;
    }
}
