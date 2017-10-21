package lab.cmego.com.cmegoclientandroid.network.server_facing;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Amit Ishai on 1/23/2017.
 */

public interface CloudFunctionApiInterface {

    @GET("/getAllData")
    Call<ResponseBody> getAllData();

    @GET("/helloWorld")
    Call<ResponseBody> helloWorld();


    //
    //    @GET("/recognizePic")
    //    Call<ResponseBody> recognizePic();
    //
    //    @POST("/recognizePic")
    //    Call<ResponseBody> recognizePic2(@Body RequestBody photo);

}
