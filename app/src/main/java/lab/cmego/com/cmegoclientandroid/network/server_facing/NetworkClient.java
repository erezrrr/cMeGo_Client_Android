package lab.cmego.com.cmegoclientandroid.network.server_facing;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Map;

import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Membership;
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
        tokenAuthenticatedService = new ServiceGenerator().createService("https://fir-fun-b5410.firebaseio.com/", TokenBasedApiInterface.class);
        cloudFunctionService = new ServiceGenerator().createService("https://us-central1-fir-fun-b5410.cloudfunctions.net/", CloudFunctionApiInterface.class);
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

    public void getAllMemberships(ResultListener<Map<String, Membership>> listener){

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        mUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();

                            Call<Map<String, Membership>> call = tokenAuthenticatedService.getAllMemberships(idToken);
                            call.enqueue(new RemoteServerCallback<Map<String, Membership>>() {
                                @Override
                                public void onServerResponse(Call<Map<String, Membership>> call,
                                                             Response<Map<String, Membership>> response) {

                                    Log.d("","");

                                    //                                    Logger.log("Upload Success in " + (System.currentTimeMillis() - startTime) + " millies. Received in server: " + response.body().getData().getDescription());
                                    //
                                    //                                    if(listener != null){
                                    //                                        listener.onComplete();
                                    //                                    }
                                }

                                @Override
                                public void onFailure(Call<Map<String, Membership>> call, Throwable t) {
                                    //                                    Logger.log("Upload error: " + t.getMessage());
                                    Log.d("","");

                                    //                                    if(listener != null){
                                    //                                        listener.onError(new Exception(t == null ? "No message" : t.getMessage()));
                                    //                                    }
                                }
                            });


                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                });

    }
}
