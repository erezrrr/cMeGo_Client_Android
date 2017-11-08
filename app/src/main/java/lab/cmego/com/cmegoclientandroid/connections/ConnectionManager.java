package lab.cmego.com.cmegoclientandroid.connections;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Amit Ishai on 1/2/2017.
 */

public class ConnectionManager implements WifiConnectionListener {
    private static final String TAG = ConnectionManager.class.getSimpleName();

    private static ConnectionManager mSharedInstance;

    private ArrayList<ConnectionStateChangedListener> mListeners = new ArrayList<>();
    private WifiManager mWifiManager;
    private Context mContext;
    private WifiInfo mCurrentWifi;

    private boolean mConnected = false;
    private boolean mEnabled = false;

    public static ConnectionManager getSharedInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new ConnectionManager();
        }

        return mSharedInstance;
    }

    public void init(Context context) throws NullPointerException {
        mContext = context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);

        mEnabled = mWifiManager.isWifiEnabled();


        IntentFilter wifiIntentFilter = new IntentFilter();
        wifiIntentFilter.addAction(WifiReceiver.ACTION_CONNECTIVITY_CHANGE);
        wifiIntentFilter.addAction(WifiReceiver.ACTION_STATE_CHANGE);
        wifiIntentFilter.addAction(WifiReceiver.ACTION_WIFI_STATE_CHANGED);

        WifiReceiver.getSharedInstance().addListener(this);
        context.registerReceiver(WifiReceiver.getSharedInstance(), wifiIntentFilter);

        resolveStateAndNotifyListeners();
    }

    private void onConnectionError(Exception e, ConnectionDescriptor connectionDescriptor) {
        for(ConnectionStateChangedListener listener : mListeners){
            listener.onConnectionError("Error connecting to " + connectionDescriptor.describe() + ".\n\n" + e.getMessage());
        }
    }

    private void resolveStateAndNotifyListeners() {
        resolveState();
        notifyListeners();
    }

    public void resolveState() {
        mCurrentWifi = getCurrentlyConnectedWifiNetwork();
        Log.d(TAG, "resolveState. State: " + mCurrentWifi);
    }

    public void addConnectionStateChangedListener(ConnectionStateChangedListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    private void notifyListeners() {
        for (ConnectionStateChangedListener listener : mListeners) {
            listener.onConnectionStateChanged();
        }
    }

    @Override
    public void onConnected(String ssid) {
        resolveStateAndNotifyListeners();
    }

    @Override
    public void onDisconnected() {
        resolveStateAndNotifyListeners();
    }

    @Override
    public void onWifiEnabled() {
        resolveStateAndNotifyListeners();
    }

    @Override
    public void onWifiDisabled() {
        resolveStateAndNotifyListeners();
    }

    public WifiInfo getCurrentlyConnectedWifiNetwork() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            return wifiInfo;
        }

        return null;
    }

    public void destroy() {
        if(mContext != null){
            mContext.unregisterReceiver(WifiReceiver.getSharedInstance());
        }
    }
}
