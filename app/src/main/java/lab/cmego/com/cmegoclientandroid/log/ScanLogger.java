package lab.cmego.com.cmegoclientandroid.log;

import android.bluetooth.le.ScanResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import lab.cmego.com.cmegoclientandroid.ble.BleScanner;
import lab.cmego.com.cmegoclientandroid.files.FileManager;

/**
 * Created by Amit Ishai on 9/24/2017.
 */

public class ScanLogger implements BleScanner.ScanBleInterface {
    private static ScanLogger sInstance;

    public static ScanLogger getInstance(){
        if(sInstance == null){
            sInstance = new ScanLogger();
        }

        return sInstance;
    }

    public void start(){
        BleScanner.getInstance().addListener(this);
    }

    public void stop(){
        BleScanner.getInstance().removeListener(this);
    }

    @Override
    public void onScan(int callbackType, ScanResult result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        FileManager.appendToFileAsync(FileManager.DIRECTORY_CEME, FileManager.FILE_BLE_LOG,
                strDate + " Device: " + result.getDevice().getName() + "(" +  result.getDevice().getAddress() + ") rssi:" + result.getRssi() + "\n");
    }
}
