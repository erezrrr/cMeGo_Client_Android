package lab.cmego.com.cmegoclientandroid.network.server_facing;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amit Ishai on 2/5/2017.
 */

public abstract class RemoteServerCallback<T> implements Callback<T> {

    private static final int AUTHENTICATION_ERROR_RETRIES = 1;
    private static final int AUTHENTICATION_ERROR_CODE = 403;
    private int mRetries = 0;

    @Override
    public final void onResponse(final Call<T> call, Response<T> response) {

        if(response.errorBody() != null){
            String errorString = "Server error";

            try {
                errorString = response.errorBody().string();

//                NetworkRequestError networkRequestError = new Gson().fromJson(errorString, NetworkRequestError.class);
//
//                if(networkRequestError != null){
//
//                    // If error is an authentication error, first try to login before giving up.
//                    if(networkRequestError.getCode() == AUTHENTICATION_ERROR_CODE && mRetries < AUTHENTICATION_ERROR_RETRIES){
//
//                        mRetries++;
//
//                        GlobalServerClient.getSharedInstance().login(Persistence
//                                .getSharedInstance().getPassword(), new CompletionListener() {
//                            @Override
//                            public void onComplete() {
//                                call.clone().enqueue(RemoteServerCallback.this);
//                            }
//
//                            @Override
//                            public void onError(Exception e) {
//                                onFailure(call, e);
//                            }
//                        });
//
//                        return;
//                    }

//                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            onFailure(call, new Exception(errorString));
            return;
        }

        onServerResponse(call, response);
    }

    protected abstract void onServerResponse(Call<T> call, Response<T> response);
}
