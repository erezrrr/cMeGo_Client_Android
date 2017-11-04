package lab.cmego.com.cmegoclientandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.fragments.auth.AuthFragment;
import lab.cmego.com.cmegoclientandroid.fragments.auth.SwipeAuthFragment;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.GateState;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.network.NetworkClient;

public class GateActivity extends AppCompatActivity implements AuthFragment.AuthResultInterface {

    public final static String EXTRA_GATE_ID = "gate_id";
    private Gate mGate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);

        String gateId = getIntent().getStringExtra(EXTRA_GATE_ID);

        if(TextUtils.isEmpty(gateId)){
            Toast.makeText(this, "GateId is empty. Exiting Activity.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mGate = ContentProvider.getInstance().getGateById(gateId);

        if(mGate == null){
            Toast.makeText(this, "GateId not found. Exiting Activity.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Controller controller = ContentProvider.getInstance().getControllerForGate(gateId);

        String userId = ContentProvider.getInstance().getUserId();

        if(TextUtils.isEmpty(userId)){
            Toast.makeText(this, "UserId not found. Exiting Activity.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        NetworkClient.getInstance().initControllerInterface(controller.getBaseUrl());

        NetworkClient.getInstance().checkIn(userId, gateId,  new ResultListener<GateState>() {
            @Override
            public void onResult(GateState state) {
//                Toast.makeText(GateActivity.this, "Got this state:\n" + CustomGson.getInstance().toJson(state), Toast.LENGTH_LONG).show();

                onGotGateState(state);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(GateActivity.this, "Got this ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onGotGateState(GateState state) {
        SwipeAuthFragment placeholder = new SwipeAuthFragment();

        Bundle bundle = new Bundle();
        bundle.putString(AuthFragment.EXTRA_GATE_ID, mGate.getId());
        placeholder.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.authContainer, placeholder).commit();
    }

    @Override
    public void onSuccess(String gateId, UserAuthenticationMethod method, AuthenticationResult authenticationResult) {
        Toast.makeText(GateActivity.this, "Auth result: " + authenticationResult, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onError(String gateId, UserAuthenticationMethod method, Exception e){
        Toast.makeText(GateActivity.this, "Auth ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
