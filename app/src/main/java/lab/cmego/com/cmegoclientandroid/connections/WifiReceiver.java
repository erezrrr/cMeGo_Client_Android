package lab.cmego.com.cmegoclientandroid.connections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import static android.net.wifi.WifiManager.EXTRA_WIFI_STATE;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLING;
import static android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN;

public class WifiReceiver extends BroadcastReceiver {
    private static final String TAG = WifiReceiver.class.getSimpleName();

    public static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_WIFI_STATE_CHANGED = "android.net.wifi.WIFI_STATE_CHANGED";
    public static final String ACTION_STATE_CHANGE= "android.net.wifi.STATE_CHANGE";

    private static WifiReceiver sSharedInstance;

    private ArrayList<WifiConnectionListener> mListeners = new ArrayList<>();

    private static Object LOCK = new Object();

    private WifiReceiver(){}

    public static WifiReceiver getSharedInstance(){
        synchronized (LOCK){
            if(sSharedInstance == null){
                sSharedInstance = new WifiReceiver();
            }
        }

        return sSharedInstance;
    }

    public void addListener(WifiConnectionListener listener) {
        if(!mListeners.contains(listener)){
            mListeners.add(listener);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();

        if(intent == null || TextUtils.isEmpty(intent.getAction())){
            return;
        }

        switch (intent.getAction()){
            case ACTION_CONNECTIVITY_CHANGE:

                if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI){
                    Log.d("WifiReceiver", "Have Wifi Connection");

                    for(WifiConnectionListener listener : mListeners){
                        listener.onConnected(netInfo.getExtraInfo());
                    }

                } else {
                    Log.d("WifiReceiver", "Don't have Wifi Connection");

                    for(WifiConnectionListener listener : mListeners){
                        listener.onDisconnected();
                    }
                }

                break;
            case ACTION_WIFI_STATE_CHANGED:

                int state = intent.getIntExtra(EXTRA_WIFI_STATE, 0);

                switch (state){
                    case WIFI_STATE_ENABLED:
                        Log.d(TAG, "Wifi State: WIFI_STATE_ENABLED");

                        for(WifiConnectionListener listener : mListeners){
                            listener.onWifiEnabled();
                        }

                        break;
                    case WIFI_STATE_ENABLING:
                        Log.d(TAG, "Wifi State: WIFI_STATE_ENABLING");

                        break;
                    case WIFI_STATE_DISABLED:
                        Log.d(TAG, "Wifi State: WIFI_STATE_DISABLED");

                        for(WifiConnectionListener listener : mListeners){
                            listener.onWifiDisabled();
                        }

                        break;
                    case WIFI_STATE_DISABLING:
                        Log.d(TAG, "Wifi State: WIFI_STATE_DISABLING");

                        break;
                    case WIFI_STATE_UNKNOWN:
                        Log.d(TAG, "Wifi State: WIFI_STATE_UNKNOWN");

                        break;
                    default:
                        Log.d(TAG, "Wifi State: default");
                        break;
                }

                break;
            case ACTION_STATE_CHANGE:
                break;
        }
    }
}
