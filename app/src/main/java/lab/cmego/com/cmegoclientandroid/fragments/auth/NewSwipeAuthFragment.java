package lab.cmego.com.cmegoclientandroid.fragments.auth;

import android.util.Log;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.network.NetworkClient;
import lab.cmego.com.cmegoclientandroid.views.SlideButton;

/**
 * Created by Owner on 04/11/2017.
 */

public class NewSwipeAuthFragment extends AuthFragment {

    @Override
    protected int getMainLayout() {
        return R.layout.new_swipe;
    }

    @Override
    protected void setupView() {

        SlideButton slideButton = (SlideButton) getView().findViewById(R.id.swipeControl);

        slideButton.setSlideButtonListener(new SlideButton.SlideButtonListener() {
            @Override
            public void onSlide() {
                authenticate();
            }
        });
//        Switch switche = (Switch) getView().findViewById(R.id.swipeControl);

//        switche.setswit
//        getView().findViewById(R.id.swipeControl).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                authenticate();
//            }
//        });
    }

    @Override
    protected UserAuthenticationMethod getAuthMethod() {
        return UserAuthenticationMethod.SWIPE;
    }

    private void authenticate() {
        NetworkClient.getInstance().authenticateSwipe(
                ContentProvider.getInstance().getUserId(),
                mGateId,
                new ResultListener<AuthenticationResult>() {
                    @Override
                    public void onResult(AuthenticationResult authenticationResult) {
                        Log.d("","");
                        notifyAuthSuccess(authenticationResult);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("","");
                        notifyAuthError(e);
                    }
                }

        );
    }
}