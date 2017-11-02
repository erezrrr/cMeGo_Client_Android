package lab.cmego.com.cmegoclientandroid.network.server_facing;

import lab.cmego.com.cmegoclientandroid.network.ServiceGenerator;
import lab.cmego.com.cmegoclientandroid.network.server_facing.authentication.AuthenticationInterceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Amit Ishai on 2/14/2017.
 */

public class TokenAuthenticatedServiceGenerator extends ServiceGenerator {

    @Override
    protected OkHttpClient.Builder customizeHttpClient(OkHttpClient.Builder httpClient) {
        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor();

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
        }

        return httpClient;
    }

}
