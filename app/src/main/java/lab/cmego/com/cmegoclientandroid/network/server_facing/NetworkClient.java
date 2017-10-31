package lab.cmego.com.cmegoclientandroid.network.server_facing;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Map;

import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Amit Ishai on 10/12/2017.
 */

public class NetworkClient {

    private static NetworkClient sInstance;
    private final CloudFunctionApiInterface cloudFunctionService;
    private TokenBasedApiInterface tokenAuthenticatedService;

    private NetworkClient(){
        tokenAuthenticatedService = new ServiceGenerator().createService("https://cmego-373dd.firebaseio.com/", TokenBasedApiInterface.class);
        cloudFunctionService = new ServiceGenerator().createService("https://us-central1-cmego-373dd.cloudfunctions.net/", CloudFunctionApiInterface.class);
    }

    public static NetworkClient getInstance(){
        if(sInstance == null){
            sInstance = new NetworkClient();
        }

        return sInstance;
    }

    public void helloWorld(){
        Call<ResponseBody> call = cloudFunctionService.helloWorld();
        call.enqueue(new RemoteServerCallback<ResponseBody>() {
            @Override
            public void onServerResponse(Call<ResponseBody> call,
                                         Response<ResponseBody> response) {

                Log.d("","");

                //                                    Logger.log("Upload Success in " + (System.currentTimeMillis() - startTime) + " millies. Received in server: " + response.body().getData().getDescription());
                //
                //                                    if(listener != null){
                //                                        listener.onComplete();
                //                                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //                                    Logger.log("Upload error: " + t.getMessage());
                Log.d("","");

                //                                    if(listener != null){
                //                                        listener.onError(new Exception(t == null ? "No message" : t.getMessage()));
                //                                    }
            }
        });
    }

    public void getAllData(){



        Call<ResponseBody> call = cloudFunctionService.getAllData();
        call.enqueue(new RemoteServerCallback<ResponseBody>() {
            @Override
            public void onServerResponse(Call<ResponseBody> call,
                                         Response<ResponseBody> response) {

                Log.d("","");

                //                                    Logger.log("Upload Success in " + (System.currentTimeMillis() - startTime) + " millies. Received in server: " + response.body().getData().getDescription());
                //
                //                                    if(listener != null){
                //                                        listener.onComplete();
                //                                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //                                    Logger.log("Upload error: " + t.getMessage());
                Log.d("","");

                //                                    if(listener != null){
                //                                        listener.onError(new Exception(t == null ? "No message" : t.getMessage()));
                //                                    }
            }
        });
    }

    public void getAllMembershipsForProfile(final ResultListener<String> listener){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String profileId = user.getUid();

        Call<ResponseBody> call = cloudFunctionService.getMembershipsForProfileAllData(profileId);
        call.enqueue(new RemoteServerCallback<ResponseBody>() {
            @Override
            protected void onServerResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    String result = response.body().string();

                    Log.d("kjhkjhkjh","kjhkjhkj: From Profile: " + result);

                    if(listener != null){
                        listener.onResult(result);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    reportError(e);
                } catch (Exception e) {
                    e.printStackTrace();
                    reportError(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("","");
                reportError(t);
            }

            private void reportError(Throwable t) {
                if(listener != null){
                    listener.onError(new Exception(t.getMessage()));
                }
            }

        });
    }

    public void getAllMemberships(final ResultListener<Map<String, Membership>> listener){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String profileId = user.getUid();

        Query mUserReference = FirebaseDatabase.getInstance().getReference().child
                ("users").orderByChild("profileId").equalTo(profileId);

        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                getMembershipsForUser(listener, user.getId());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMembershipsForUser(ResultListener<Map<String, Membership>> listener, String userId){

        Call<ResponseBody> call = cloudFunctionService.getMembershipsForUserAllData(userId);
        call.enqueue(new RemoteServerCallback<ResponseBody>() {
            @Override
            protected void onServerResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("kjhkjhkjh","kjhkjhkj: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("","");
            }
        });
    }
}
