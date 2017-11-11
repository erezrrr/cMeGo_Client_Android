package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Amit Ishai on 9/24/2017.
 */

public class BleScanner {

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;

    private final static int REQUEST_ENABLE_BT = 1;

    private static BleScanner sInstance;

    private HashMap<String, TimedScanResult> mCurrentResults = new HashMap<>();

    private ArrayList<ScanBleInterface> mListeners = new ArrayList<>();
    private Handler mHandler;

    private static final long SCAN_RUNNABLE_INTERVAL = 1000;
    private static final long SCAN_LIFETIME = 22000;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            boolean removed = removeStaleScans();

            if(removed){
                notifyOnChange();
            }

            mHandler.postDelayed(this, SCAN_RUNNABLE_INTERVAL);
        }
    };

    private void notifyOnChange() {
        for(ScanBleInterface listener : mListeners){
            listener.onChange();
        }
    }

    private boolean removeStaleScans() {

        boolean removed = false;

        if(mCurrentResults == null){
            return removed;
        }

        Set<String> keys = new HashSet<>(mCurrentResults.keySet());

        for(String key : keys){
            TimedScanResult scanResult = mCurrentResults.get(key);

            if(isStale(scanResult)){
                mCurrentResults.remove(key);
                removed = true;
            }
        }

        return removed;
    }

    private boolean isStale(TimedScanResult scanResult) {
        Log.d("sdsdfsdf","sdsdf Time between scans: " + (System.currentTimeMillis() - scanResult.getTime()));
        return System.currentTimeMillis() - scanResult.getTime() > SCAN_LIFETIME;
    }

    public static BleScanner getInstance(){
        if(sInstance == null){
            sInstance = new BleScanner();
        }

        return sInstance;
    }

    public void addListener(ScanBleInterface scanBleInterface){
        if(!mListeners.contains(scanBleInterface)){
            mListeners.add(scanBleInterface);
        }
    }

    public void removeListener(ScanBleInterface scanBleInterface){
        if(mListeners.contains(scanBleInterface)){
            mListeners.remove(scanBleInterface);
        }
    }

    public void init(Context context){
        if(btManager != null){
            return;
        }

        btManager = (BluetoothManager)context.getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();

        mHandler = new Handler();
    }

    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            Log.d("dfgdfgdfg","dfgdfgdfgdfgdfgdf: " + result.getRssi());
            mCurrentResults.put(result.getDevice().getAddress(), new TimedScanResult(result));

            for (ScanBleInterface listener: mListeners){
                listener.onScan(callbackType, result);
            }

            notifyOnChange();

        }
    };

    public HashMap<String, TimedScanResult> getCurrentResults() {
        return mCurrentResults;
    }

    public void startScanning() {
        System.out.println("start scanning");
        btScanner.flushPendingScanResults(leScanCallback);
        btScanner.stopScan(leScanCallback);

        ArrayList<ScanFilter> scanFilters = new ArrayList<>();

        ScanSettings scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        btScanner.startScan(scanFilters, scanSettings, leScanCallback);

        mHandler.postDelayed(mRunnable, SCAN_RUNNABLE_INTERVAL);
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
        btScanner.stopScan(leScanCallback);
        mHandler.removeCallbacks(mRunnable);
    }

    public interface ScanBleInterface{
        void onScan(int callbackType, ScanResult result);
        void onChange();
    }

}
