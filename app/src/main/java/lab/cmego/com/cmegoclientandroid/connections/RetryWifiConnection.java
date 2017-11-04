package lab.cmego.com.cmegoclientandroid.connections;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import lab.cmego.com.cmegoclientandroid.exceptions.WifiConnectionTimeoutException;

/**
 * Created by Amit Ishai on 1/15/2017.
 */

public class RetryWifiConnection extends WifiConnection {
    private static final String TAG = RetryWifiConnection.class.getSimpleName();

    private static final long WIFI_CONNECTION_TIMEOUT_MILLISECONDS = 10000;

    private static final int NUM_OF_CONNECTION_RETRIES = 3;
    private int mRetries = 0;

    private final Handler mHandler;

    private Runnable mTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "mTimeoutRunnable. mSsid: " + mConnectionDescriptor.getSsid() + ", State: " + mState);

            if(mState != ConnectionState.CONNECTED){
                onReceivedConnectionError(new WifiConnectionTimeoutException(mConnectionDescriptor.getSsid()));
            }
        }
    };

    public RetryWifiConnection(Context context, WifiConnectionDescriptor descriptor) {
        super(context, descriptor);
        mHandler = new Handler();
    }

    @Override
    public void connect() {
        mRetries = 0;
        tryToConnectToWifi();
    }

    private void tryToConnectToWifi() {
        startTimeOut();
        super.connect();
    }

    @Override
    public void disconnect() {
        resetTimeout();
        super.disconnect();
    }

    @Override
    public void onConnected(String ssid) {
        resetTimeout();
        super.onConnected(ssid);
    }

    private void startTimeOut() {
        resetTimeout();
        Log.d(TAG, "startTimeOut. mSsid: " + mConnectionDescriptor.getSsid());
        mHandler.postDelayed(mTimeoutRunnable, WIFI_CONNECTION_TIMEOUT_MILLISECONDS);
    }

    private void resetTimeout() {
        Log.d(TAG, " resetTimeout. mSsid: " + mConnectionDescriptor.getSsid());
        mHandler.removeCallbacks(mTimeoutRunnable);
    }

    protected void onReceivedConnectionError(Exception e) {

        // Dismiss other errors
        resetTimeout();

        if(mRetries < NUM_OF_CONNECTION_RETRIES){
            mRetries++;
            tryToConnectToWifi();
        } else {
            super.onReceivedConnectionError(e);
        }
    }

}
