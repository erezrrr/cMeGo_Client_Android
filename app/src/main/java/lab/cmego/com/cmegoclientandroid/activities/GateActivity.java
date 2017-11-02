package lab.cmego.com.cmegoclientandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.network.NetworkClient;

public class GateActivity extends AppCompatActivity {

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

        NetworkClient.getInstance().initControllerInterface(controller.getBaseUrl());

        NetworkClient.getInstance().helloWorld(new ResultListener<String>() {
            @Override
            public void onResult(String s) {
                Toast.makeText(GateActivity.this, "Got this string: " + s, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(GateActivity.this, "Got this string: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
