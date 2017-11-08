package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.le.ScanResult;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import lab.cmego.com.cmegoclientandroid.slidingwindows.MaxElementSlidingWindow;

/**
 * Created by Amit Ishai on 10/5/2017.
 */

public class BleScanAggregator implements BleScanner.ScanBleInterface {
    private static final long BLE_SCAN_VALIDITY_MILLIES = TimeUnit.SECONDS.toMillis(60);
    private static BleScanAggregator sInstance;

    private int mWindowSize = 1;

    private HashMap<String, MaxElementSlidingWindow<ScanResult>> mLastResults;

    private ArrayList<ScanResultAggregationInterface> mListeners = new ArrayList<>();

    public static BleScanAggregator getInstance(){
        if(sInstance == null){
            sInstance = new BleScanAggregator();
        }

        return sInstance;
    }

    public void start(){
        mLastResults = new HashMap<>();
        BleScanner.getInstance().addListener(this);
    }

    public void stop(){
        BleScanner.getInstance().removeListener(this);
    }

    public void addListener(ScanResultAggregationInterface aggregationInterface){
        if(!mListeners.contains(aggregationInterface)){
            mListeners.add(aggregationInterface);
        }
    }

    public void removeListener(ScanResultAggregationInterface aggregationInterface){
        if(mListeners.contains(aggregationInterface)){
            mListeners.remove(aggregationInterface);
        }
    }

    public void setWindowSize(int windowSize){
        mWindowSize = windowSize;
        mLastResults.clear();
    }

    public HashMap<String, MaxElementSlidingWindow<ScanResult>> getLastResults() {
        return mLastResults;
    }

    @Override
    public void onScan(int callbackType, ScanResult result) {
        MaxElementSlidingWindow<ScanResult> scanResults = mLastResults.get(result.getDevice().getAddress());

        if(scanResults == null){
            scanResults = new MaxElementSlidingWindow<>(mWindowSize);
        }

        scanResults.add(result);

        mLastResults.put(result.getDevice().getAddress(), scanResults);

        removeStale();

        for(ScanResultAggregationInterface listener : mListeners){
            listener.newAggregation();
        }
    }

    @Override
    public void onChange() {

    }

    private void removeStale() {

        HashSet<String> keys = new HashSet<>(mLastResults.keySet());

        for(String key : keys){

            MaxElementSlidingWindow<ScanResult> slidingWindow = mLastResults.get(key);

            if(slidingWindow == null || slidingWindow.size() == 0){
                mLastResults.remove(key);
                continue;
            }

            ScanResult latestResult = slidingWindow.get(0);

            if(latestResult == null){
                mLastResults.remove(key);
                continue;
            }

            long rxTimestampMillis = System.currentTimeMillis() -
                    SystemClock.elapsedRealtime() +
                    latestResult.getTimestampNanos() / 1000000;

            Log.d("sdfsdfs","sdfsdfsdf Current " + System.currentTimeMillis());

            Log.d("sdfsdfs","sdfsdfsdf time stamp for " + key + ": " + rxTimestampMillis);

            Log.d("sdfsdfs","sdfsdfsdf time passed for " + key + ": " + (System.currentTimeMillis() - rxTimestampMillis));

            if(System.currentTimeMillis() - rxTimestampMillis > BLE_SCAN_VALIDITY_MILLIES){
//                mLastResults.remove(key);
            }

        }
    }


    public interface ScanResultAggregationInterface {
        void newAggregation();
    }
}
