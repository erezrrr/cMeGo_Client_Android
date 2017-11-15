package lab.cmego.com.cmegoclientandroid.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import lab.cmego.com.cmegoclientandroid.R;

public class InitialActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        mAuth = FirebaseAuth.getInstance();


        BluetoothManager btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = btManager.getAdapter();

        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            startService(new Intent(MainActivity.this, ScanBleService.class));
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }

        String[] per = getPermissionRequest();

        if(per == null || per.length == 0){
            init();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    per,
                    PERMISSION_REQUEST_COARSE_LOCATION
            );
        }

    }

    private String[] getPermissionRequest(){
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        ArrayList<String> retval = new ArrayList<>();

        if(permission != PackageManager.PERMISSION_GRANTED){
            retval.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(permission2 != PackageManager.PERMISSION_GRANTED){
            retval.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if(permission3 != PackageManager.PERMISSION_GRANTED){
            retval.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        return retval.toArray(new String[0]);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            String[] per = getPermissionRequest();

            if (per == null || per.length == 0) {
                init();
            } else {
                ActivityCompat.requestPermissions(this, per, PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    private void init(){
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(this, SignInSignUpActivity.class));
        } else {
            startActivity(new Intent(this, NavMainActivity.class));
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(mAuth.getCurrentUser() == null){
//            startActivity(new Intent(this, SignInSignUpActivity.class));
//        } else {
//            startActivity(new Intent(this, MainActivity.class));
//        }
//
//        finish();
    }
}
