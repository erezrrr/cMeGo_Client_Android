package lab.cmego.com.cmegoclientandroid.fragments.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;

/**
 * Created by Owner on 04/11/2017.
 */

public abstract class AuthFragment extends Fragment {
    public static final String EXTRA_GATE_ID = "extra_gate_id";

    protected String mGateId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            mGateId = bundle.getString(EXTRA_GATE_ID);
        }

        if(TextUtils.isEmpty(mGateId)){
//            notifyNoGateId();
//            removeFromStack();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setupView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(getMainLayout(), container, false);
    }

    public void setGateId(String gateId) {
        mGateId = gateId;
    }

    protected void notifyAuthSuccess(AuthenticationResult authenticationResult) {
        try {
            ((AuthResultInterface)getActivity()).onSuccess(mGateId, getAuthMethod(), authenticationResult);
        } catch(ClassCastException e){

        }
    }

    protected void notifyAuthError(Exception e) {
        try {
            ((AuthResultInterface)getActivity()).onError(mGateId, getAuthMethod(), e);
        } catch(ClassCastException e1){

        }
    }

    protected abstract int getMainLayout();
    protected abstract void setupView();
    protected abstract UserAuthenticationMethod getAuthMethod();

    public interface AuthResultInterface {
        void onSuccess(String gateId, UserAuthenticationMethod method, AuthenticationResult authenticationResult);
        void onError(String gateId, UserAuthenticationMethod method, Exception e);
    }
}
