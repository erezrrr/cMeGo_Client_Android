package lab.cmego.com.cmegoclientandroid.ble.proximity;

import android.bluetooth.le.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.ble.BleScanner;
import lab.cmego.com.cmegoclientandroid.ble.TimedScanResult;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;

/**
 * Created by Owner on 08/11/2017.
 */

public class BleProximityResolver implements BleScanner.ScanBleInterface, ContentProvider.ContentProviderInterface {

    private BluetoothDevice mClosestBle;
    private ArrayList<BleProximityListener> mListeners = new ArrayList<>();

    public void addListener(BleProximityListener proximityListener){
        if(!mListeners.contains(proximityListener)){
            mListeners.add(proximityListener);
        }
    }

    public void removeListener(BleProximityListener proximityListener){
        if(mListeners.contains(proximityListener)){
            mListeners.remove(proximityListener);
        }
    }

    public BluetoothDevice getClosestBle() {
        return mClosestBle;
    }

    public void start(){
        BleScanner.getInstance().addListener(this);
        ContentProvider.getInstance().addListener(this);
        resolveStateAndNotify();
    }

    public void stop(){

    }

    @Override
    public void onScan(int callbackType, ScanResult result) {

    }

    @Override
    public void onChange() {
        resolveStateAndNotify();
    }

    private void resolveStateAndNotify() {
        BluetoothDevice temp = mClosestBle;
        setClosestBle();

        if(stateChanged(temp, mClosestBle)){
            notifyUpdate();
        }
    }

    private boolean stateChanged(BluetoothDevice current, BluetoothDevice previous) {
        if(current == null && previous == null){
            return false;
        } else if(current == null || previous == null){
            return true;
        }

        return !current.getMacAddress().equals(previous.getMacAddress());
    }

    private void notifyUpdate() {
        for(BleProximityListener listener : mListeners){
            listener.onBleProximityUpdate();
        }
    }

    private void setClosestBle() {
        HashMap<String, TimedScanResult> currentResults = BleScanner.getInstance().getCurrentResults();

        if(currentResults == null || currentResults.size() == 0){
            mClosestBle = null;
            return;
        }

        // find closest...

        int maxRssi = -1000;
        BluetoothDevice closestBle = null;

        for(TimedScanResult scanResult : currentResults.values()){

            BluetoothDevice temp = getMatchingBle(scanResult.getScanResult());

            if(temp != null){

                if(scanResult.getScanResult().getRssi() > maxRssi){
                    maxRssi = scanResult.getScanResult().getRssi();
                    closestBle = temp;
                }

            }

        }

        if(closestBle == null){
            mClosestBle = null;
            return;
        }

        mClosestBle = closestBle;
    }

    private BluetoothDevice getMatchingBle(ScanResult scanResult) {

        List<BluetoothDevice> allBles = ContentProvider.getInstance().getAllBles();

        if(allBles == null || allBles.size() == 0){
            return null;
        }

        for(BluetoothDevice bluetoothDevice : allBles){
            if(bluetoothDevice.getMacAddress().equals(scanResult.getDevice().getAddress())){
                return bluetoothDevice;
            }
        }

        return null;
    }

    @Override
    public void onContentRefreshed() {
        resolveStateAndNotify();
    }

    public interface BleProximityListener {
        void onBleProximityUpdate();
    }
}
