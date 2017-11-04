package lab.cmego.com.cmegoclientandroid.network;

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

import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.GateState;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.User;
import lab.cmego.com.cmegoclientandroid.network.controller_facing.ControllerApiInterface;
import lab.cmego.com.cmegoclientandroid.network.server_facing.CloudFunctionApiInterface;
import lab.cmego.com.cmegoclientandroid.network.server_facing.RemoteServerCallback;
import lab.cmego.com.cmegoclientandroid.network.server_facing.TokenBasedApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Amit Ishai on 10/12/2017.
 */

public class NetworkClient {

    private static NetworkClient sInstance;
    private CloudFunctionApiInterface mCloudFunctionService;
    private TokenBasedApiInterface mTokenAuthenticatedService;
    private ControllerApiInterface mControllerApiInterface;

    private NetworkClient(){
        mTokenAuthenticatedService = new ServiceGenerator().createService("https://cmego-373dd.firebaseio.com/", TokenBasedApiInterface.class);
        mCloudFunctionService = new ServiceGenerator().createService("https://us-central1-cmego-373dd.cloudfunctions.net/", CloudFunctionApiInterface.class);
    }

    public void initControllerInterface(String baseUrl){
        mControllerApiInterface = new ServiceGenerator().createService(baseUrl, ControllerApiInterface.class);
    }

    public static NetworkClient getInstance(){
        if(sInstance == null){
            sInstance = new NetworkClient();
        }

        return sInstance;
    }

    public void helloWorld(final ResultListener<String> listener){
        Call<ResponseBody> call = mControllerApiInterface.helloWorld();
        call.enqueue(new RemoteServerCallback<ResponseBody>() {
            @Override
            public void onServerResponse(Call<ResponseBody> call,
                                         Response<ResponseBody> response) {

                Log.d("","");

                if(listener != null){
                    try {
                        listener.onResult(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //                                    Logger.log("Upload Success in " + (System.currentTimeMillis() - startTime) + " millies. Received in server: " + response.body().getData().getDescription());
                //
                //                                    if(listener != null){
                //                                        listener.onComplete();
                //                                    }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("","");

                if(listener != null){
                    listener.onError(new Exception(t.getMessage()));
                }
            }
        });
    }

    public void checkIn(String userId, String gateId, final ResultListener<GateState> listener){
        Call<GateState> call = mControllerApiInterface.checkIn(gateId, userId);
        call.enqueue(new RemoteServerCallback<GateState>() {
            @Override
            public void onServerResponse(Call<GateState> call,
                                         Response<GateState> response) {

                Log.d("","");

                if(listener != null){
                    listener.onResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<GateState> call, Throwable t) {
                //                                    Logger.log("Upload error: " + t.getMessage());
                Log.d("","");

                if(listener != null){
                    listener.onError(new Exception(t.getMessage()));
                }
                //                                    if(listener != null){
                //                                        listener.onError(new Exception(t == null ? "No message" : t.getMessage()));
                //                                    }
            }
        });
    }

    public void authenticateSwipe(String userId, String gateId, final ResultListener<AuthenticationResult> listener){
        authenticatePlain(userId, gateId, UserAuthenticationMethod.SWIPE, "", listener);
    }

    public void authenticatePlain(
            String userId, String gateId, UserAuthenticationMethod method, String value, final ResultListener<AuthenticationResult> listener){

        Call<AuthenticationResult> call = mControllerApiInterface.authenticatePlain(
                gateId, userId, String.valueOf(method), value);

        call.enqueue(new RemoteServerCallback<AuthenticationResult>() {
            @Override
            public void onServerResponse(Call<AuthenticationResult> call,
                                         Response<AuthenticationResult> response) {

                Log.d("","");

                if(listener != null){
                    listener.onResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResult> call, Throwable t) {
                Log.d("","");

                if(listener != null){
                    listener.onError(new Exception(t.getMessage()));
                }
            }
        });

    }


    public void getAllData(){



        Call<ResponseBody> call = mCloudFunctionService.getAllData();
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

        Call<ResponseBody> call = mCloudFunctionService.getMembershipsForProfileAllData(profileId);
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

        Call<ResponseBody> call = mCloudFunctionService.getMembershipsForUserAllData(userId);
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
