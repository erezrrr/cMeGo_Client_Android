package lab.cmego.com.cmegoclientandroid.network.server_facing;

import java.util.concurrent.TimeUnit;

import lab.cmego.com.cmegoclientandroid.serialization.GsonConverterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Amit Ishai on 2/14/2017.
 */

public class ServiceGenerator {

    public <S> S createService(String baseUrl, Class<S> serviceClass) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(4, TimeUnit.MINUTES);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create());

        httpClient = customizeHttpClient(httpClient);

        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    protected OkHttpClient.Builder customizeHttpClient(OkHttpClient.Builder httpClient) {
        return httpClient;
    }
}
