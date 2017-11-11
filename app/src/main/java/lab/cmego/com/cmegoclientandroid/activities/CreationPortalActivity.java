package lab.cmego.com.cmegoclientandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.create.CreateVehicleActivity;

/**
 * Created by Owner on 11/11/2017.
 */

public class CreationPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_portal);

        findViewById(R.id.createVehicleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVehicle();
            }
        });
    }

    private void createVehicle() {
        startActivity(new Intent(CreationPortalActivity.this, CreateVehicleActivity.class));
    }

}
