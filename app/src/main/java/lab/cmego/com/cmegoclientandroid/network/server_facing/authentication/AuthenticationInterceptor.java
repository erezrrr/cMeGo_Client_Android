package lab.cmego.com.cmegoclientandroid.network.server_facing.authentication;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Amit Ishai on 2/14/2017.
 */

public class AuthenticationInterceptor implements Interceptor {

    private FirebaseAuth mAuth;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        mAuth = FirebaseAuth.getInstance();

        Request.Builder builder = original.newBuilder();

//        String authenticationToken = mAuth.getCurrentUser().getToken(true);

//        if(!TextUtils.isEmpty(authenticationToken)){
//            builder.header("Authorization", authenticationToken);
//        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
