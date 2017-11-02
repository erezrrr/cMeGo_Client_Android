package lab.cmego.com.cmegoclientandroid.content;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.network.server_facing.NetworkClient;
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

    public interface ContentProviderInterface {
        void onContentRefreshed();
    }
}
