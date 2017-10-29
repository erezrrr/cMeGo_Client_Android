package lab.cmego.com.cmegoclientandroid.ble;

import android.bluetooth.le.ScanResult;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amit Ishai on 10/5/2017.
 */

public class BleProximityProvider implements BleScanAggregator.ScanResultAggregationInterface {

    private static BleProximityProvider sInstance;

    private ArrayList<BleProximityInterface> mListeners = new ArrayList<>();

    public static BleProximityProvider getInstance(){
        if(sInstance == null){
            sInstance = new BleProximityProvider();
        }

        return sInstance;
    }

    public void start(){
        BleScanAggregator.getInstance().addListener(this);
    }

    public void stop(){
        BleScanAggregator.getInstance().removeListener(this);
    }

    public void addListener(BleProximityInterface proximityInterface){
        if(!mListeners.contains(proximityInterface)){
            mListeners.add(proximityInterface);
        }
    }

    public void removeListener(BleProximityInterface proximityInterface){
        if(mListeners.contains(proximityInterface)){
            mListeners.remove(proximityInterface);
        }
    }

    @Override
    public void newAggregation() {

        HashMap<String, Double> proximities = new HashMap<>();

        for (Map.Entry<String, MaxElementSlidingWindow<ScanResult>> entry : BleScanAggregator.getInstance().getLastResults().entrySet()){

            double proximity = ProximityCalculator.calculate(entry.getValue());

            Log.d("ghfhf","dfgdfg name: FINAL Cal: " + proximity);


            proximities.put(entry.getKey(), proximity);

        }

        for(BleProximityInterface listener : mListeners){
            listener.onNewProximities(proximities);
        }

    }

    public interface BleProximityInterface {
        void onNewProximities(HashMap<String, Double> proximities);
    }
}
