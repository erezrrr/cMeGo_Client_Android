package lab.cmego.com.cmegoclientandroid.network.controller_facing;


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
    Call<ResponseBody> getMembershipsForUserAllData(@Query("userId") String userId);

    @GET("/getMembershipsForUserAllDataFake")
    Call<ResponseBody> getMembershipsForUserAllDataFake(@Query("userId") String userId);

    @GET("/getMembershipsForProfileAllData")
    Call<ResponseBody> getMembershipsForProfileAllData(@Query("profileId") String profileId);

    //
    //    @GET("/recognizePic")
    //    Call<ResponseBody> recognizePic();
    //
    //    @POST("/recognizePic")
    //    Call<ResponseBody> recognizePic2(@Body RequestBody photo);

}
