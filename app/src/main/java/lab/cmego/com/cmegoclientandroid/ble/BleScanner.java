package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amit Ishai on 9/24/2017.
 */

public class BleScanner {

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;

    private final static int REQUEST_ENABLE_BT = 1;

    private static BleScanner sInstance;

    private HashMap<String, ScanResult> mCurrentResults = new HashMap<>();

    private ArrayList<ScanBleInterface> mListeners = new ArrayList<>();


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
    }

    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            if(!result.getDevice().getAddress().contains("1B:78:C8")){
                return;
            }

            Log.d("dfgdfgdfg","dfgdfgdfgdfgdfgdf: " + result.getRssi());
            mCurrentResults.put(result.getDevice().getAddress(), result);

            for (ScanBleInterface listener: mListeners){
                listener.onScan(callbackType, result);
            }

        }
    };

    public HashMap<String, ScanResult> getCurrentResults() {
        return mCurrentResults;
    }

    public void startScanning() {
        System.out.println("start scanning");
        btScanner.flushPendingScanResults(leScanCallback);
        btScanner.stopScan(leScanCallback);
        btScanner.startScan(leScanCallback);
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
        btScanner.stopScan(leScanCallback);
    }

    public interface ScanBleInterface{
        void onScan(int callbackType, ScanResult result);
    }

}
