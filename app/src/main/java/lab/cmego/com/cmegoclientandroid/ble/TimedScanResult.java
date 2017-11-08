package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.le.ScanResult;

/**
 * Created by Owner on 08/11/2017.
 */

public class TimedScanResult {

    private ScanResult mScanResult;
    private long mTime;

    public TimedScanResult(ScanResult scanResult){
        mScanResult = scanResult;
        mTime = System.currentTimeMillis();
    }

    public ScanResult getScanResult() {
        return mScanResult;
    }

    public long getTime() {
        return mTime;
    }
}
