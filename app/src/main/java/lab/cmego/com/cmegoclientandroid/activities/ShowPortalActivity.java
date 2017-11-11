package lab.cmego.com.cmegoclientandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import lab.cmego.com.cmegoclientandroid.R;

public class ShowPortalActivity extends AppCompatActivity {

    private static final String TAG = ShowPortalActivity.class.getSimpleName();
    private Button mShowAllMembershipsButton;
    private Button mShowMyMembershipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_portal);
        mShowAllMembershipsButton = (Button)findViewById(R.id.showAllMembershipsButton);
        mShowMyMembershipsButton = (Button)findViewById(R.id.showMyMembershipsButton);

        mShowMyMembershipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyMemberships();
            }
        });

        findViewById(R.id.showAllVehicles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllVehicles();
            }
        });

        mShowAllMembershipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemberships();
            }
        });
    }

    private void showAllVehicles() {
        startActivity(new Intent(ShowPortalActivity.this, ShowAllVehiclesActivity.class));
    }

    private void showMyMemberships() {
        startActivity(new Intent(ShowPortalActivity.this, ShowMyMembershipsActivity.class));
    }

    private void showMemberships() {
        startActivity(new Intent(ShowPortalActivity.this, ShowMembershipsActivity.class));
    }
}
