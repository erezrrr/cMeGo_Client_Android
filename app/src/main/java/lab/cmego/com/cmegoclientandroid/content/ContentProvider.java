package lab.cmego.com.cmegoclientandroid.content;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;
import lab.cmego.com.cmegoclientandroid.model.Checkpoint;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.network.NetworkClient;
import lab.cmego.com.cmegoclientandroid.serialization.CustomGson;

/**
 * Created by Owner on 29/10/2017.
 */

public class ContentProvider {

    private static ContentProvider sInstance;

    private AllDataForUser mAllDataForUser;

    private List<ContentProviderInterface> mListeners = new ArrayList<>();

    private ContentProvider(){
    }

    public static ContentProvider getInstance(){
        if(sInstance == null){
            sInstance = new ContentProvider();
        }

        return sInstance;
    }

    public void addListener(ContentProviderInterface listener){
        if(!mListeners.contains(listener)){
            mListeners.add(listener);
        }
    }

    public void removeListener(ContentProviderInterface listener){
        if(mListeners.contains(listener)){
            mListeners.remove(listener);
        }
    }

    public void init(){
        loadDataFromCache();
        notifyContentLoaded();
        refreshData();
    }

    public void refreshData() {
        NetworkClient.getInstance().getAllMembershipsForProfile(new ResultListener<String>() {
            @Override
            public void onResult(String result) {
                Log.d("dfgdfg","refreshdata got this result: " + result);

                if(TextUtils.isEmpty(result)){
                    return;
                }

                Persistence.getSharedInstance().setAllData(result);
                loadDataFromCache();

                notifyContentLoaded();
            }

            @Override
            public void onError(Exception e) {
                Log.d("","");
            }
        });
    }

    private void notifyContentLoaded() {
        for(ContentProviderInterface listener : mListeners){
            listener.onContentRefreshed();
        }
    }

    private void loadDataFromCache() {
        String asJson = Persistence.getSharedInstance().getAllData();

        if(TextUtils.isEmpty(asJson)){
            return;
        }

        mAllDataForUser = CustomGson.getInstance().fromJson(asJson, AllDataForUser.class);

        Log.d("","");
    }

    public Checkpoint getCheckpointForGate(Gate gate) {
        return getCheckpointForGate(gate.getId());
    }

    public Checkpoint getCheckpointForGate(String gateId) {
        if(mAllDataForUser == null){
            return null;
        }

        for(Checkpoint checkpoint : mAllDataForUser.getCheckpoints()){

            for(String tmpGateId : checkpoint.getGateIds()){
                if(gateId.equals(tmpGateId)){
                    return checkpoint;
                }
            }

        }

        return null;
    }

    public List<Gate> getGates() {
        if(mAllDataForUser == null || mAllDataForUser.getGates() == null){
            return new ArrayList<>();
        }

        return mAllDataForUser.getGates();
    }

    public Gate getGateById(String gateId) {
        if(mAllDataForUser == null || mAllDataForUser.getGates() == null){
            return null;
        }

        for(Gate gate : mAllDataForUser.getGates()){
            if(gate.getId().equals(gateId)){
                return gate;
            }
        }

        return null;
    }

    public Controller getControllerForGate(String gateId) {
        if(mAllDataForUser == null){
            return null;
        }

        Checkpoint checkpoint = getCheckpointForGate(gateId);

        for(Controller controller : mAllDataForUser.getControllers()){

            if(checkpoint.getControllerId().equals(controller.getId())){
                return controller;
            }

        }

        return null;
    }

    public String getUserId() {
        if(mAllDataForUser == null || mAllDataForUser.getUser() == null){
            return null;
        }

        return mAllDataForUser.getUser().getId();
    }

    public WifiNetwork getWifiNetworkForGate(String gateId) {
        if(mAllDataForUser == null){
            return null;
        }

        Checkpoint checkpoint = getCheckpointForGate(gateId);

        for(WifiNetwork wifiNetwork : mAllDataForUser.getWifiNetworks()){

            if(checkpoint.getWifiNetworkId().equals(wifiNetwork.getId())){
                return wifiNetwork;
            }

        }

        return null;
    }

    public List<WifiNetwork> getAllWifiNetworks() {
        if(mAllDataForUser == null || mAllDataForUser.getWifiNetworks() == null){
            return new ArrayList<>();
        }

        return mAllDataForUser.getWifiNetworks();
    }

    public List<BluetoothDevice> getAllBles() {
        List<BluetoothDevice> retval = new ArrayList<>();

        if(mAllDataForUser == null || mAllDataForUser.getGates() == null){
            return retval;
        }

        for(Gate gate : mAllDataForUser.getGates()){
            if(gate.getBluetoothDevice() != null){
                retval.add(gate.getBluetoothDevice());
            }
        }

        return retval;
    }

    public Gate getGateForBle(String bleMac) {

        if(mAllDataForUser == null || mAllDataForUser.getGates() == null){
            return null;
        }

        for(Gate gate : mAllDataForUser.getGates()){
            if(gate.getBluetoothDevice() != null && gate.getBluetoothDevice().getMacAddress().equals(bleMac)){
                return gate;
            }
        }

        return null;
    }



    public interface ContentProviderInterface {
        void onContentRefreshed();
    }
}
