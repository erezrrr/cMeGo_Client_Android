package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.le.ScanResult;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Amit Ishai on 10/5/2017.
 */

public class ProximityCalculator {

    public static double calculate(ArrayList<ScanResult> results){

        if(results == null || results.size() == 0){
            return 9999;
        }

        // TODO rethink. Currently plain average

        double sum = 0;

        for(ScanResult scanResult : results){
            //            sum += calculate(scanResult);
            sum += calculate(scanResult.getScanRecord().getTxPowerLevel(), scanResult.getRssi());
        }

        return sum / (double)results.size();
    }

    // According to https://iotandelectronics.wordpress.com/2016/10/07/how-to-calculate-distance-from-the-rssi-value-of-the-ble-beacon/
    private static double calculate(float tx, double rssi) {

        // Distance = 10 ^ ((Measured Power – RSSI)/(10 * N))

//        Log.d("ghfhf","dfgdfg name: " + scanResult.getDevice().getName());


//        double tx = txPower;
//        double rssi = rssi;

        //        tx = 0;
        //        rssi = 9;

        Log.d("ghfhf","dfgdfg TX: " + tx);
        Log.d("ghfhf","dfgdfg RSSI: " + rssi);

        double prox = Math.pow(10, (tx - rssi) / (10 * 2));

        Log.d("ghfhf","dfgdfg name: Cal: " + prox);

        return prox;
    }

    //https://stackoverflow.com/questions/36399927/distance-calculation-from-rssi-ble-android
    public static double calculateDistance(float txPower, double rssi) {

        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi * 1.0 / txPower;

        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }
}
