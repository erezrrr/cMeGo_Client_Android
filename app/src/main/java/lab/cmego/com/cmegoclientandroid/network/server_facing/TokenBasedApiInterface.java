package lab.cmego.com.cmegoclientandroid.network.server_facing;


import java.util.Map;

import lab.cmego.com.cmegoclientandroid.model.Membership;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amit Ishai on 1/23/2017.
 */

public interface TokenBasedApiInterface {

    @GET("/memberships.json")
    Call<Map<String, Membership>> getAllMemberships(@Query("auth") String authToken);

    @GET("/getAllData.json")
    Call<Map<String, Object>> getAllData(@Query("auth") String authToken);

    //
    //    @GET("/recognizePic")
    //    Call<ResponseBody> recognizePic();
    //
    //    @POST("/recognizePic")
    //    Call<ResponseBody> recognizePic2(@Body RequestBody photo);

}
