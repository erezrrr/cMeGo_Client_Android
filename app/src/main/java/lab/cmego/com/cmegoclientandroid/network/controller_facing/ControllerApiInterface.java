package lab.cmego.com.cmegoclientandroid.network.controller_facing;


import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.model.GateState;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amit Ishai on 1/23/2017.
 */

public interface ControllerApiInterface {

    @GET("/ff")
    Call<ResponseBody> helloWorld();

    @GET("/checkIn")
    Call<GateState> checkIn(@Query("gateId") String gateId, @Query("userId") String userId);

    @GET("/authenticatePlain")
    Call<AuthenticationResult> authenticatePlain(
            @Query("gateId") String gateId,
            @Query("userId") String userId,
            @Query("method") String method,
            @Query("value") String value);

}
