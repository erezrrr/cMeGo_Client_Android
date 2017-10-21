package lab.cmego.com.cmegoclientandroid.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.EmbeddedBeaconIdentifier;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.PlateIdentifier;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.Vehicle;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.VehicleIdentifier;

public class CreateVehicleActivity extends AppCompatActivity {

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        setupTypeSelector();

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToCreateVehicle();
            }
        });
    }

    private void setupTypeSelector() {
        mSpinner = (Spinner)findViewById(R.id.typeSelector);
        mSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Vehicle.Type.values()));
    }

    private void tryToCreateVehicle() {
        Vehicle.Type type = (Vehicle.Type) mSpinner.getSelectedItem();

        Editable plateNumberEditable = ((EditText)findViewById(R.id.plateNumberET)).getEditableText();

        List<VehicleIdentifier> identifiers = new ArrayList<>();

        ADD_PLATE_IDENTIFIER:
        if(plateNumberEditable != null){
            String plateNumber = plateNumberEditable.toString();

            if(TextUtils.isEmpty(plateNumber)){
                break ADD_PLATE_IDENTIFIER;
            }

            Editable plateLocaleEditable = ((EditText)findViewById(R.id.plateLocaleET)).getEditableText();

            String locale = "IL";

            if(plateLocaleEditable != null && !TextUtils.isEmpty(plateLocaleEditable)){
                locale = plateLocaleEditable.toString();
            }

            identifiers.add(new PlateIdentifier(plateNumber, locale));
        }

        Editable beaconMacEditable = ((EditText)findViewById(R.id.beaconMacET)).getEditableText();

        ADD_EMBEDDED_BEACON_IDENTIFIER:
        if(beaconMacEditable != null){
            String beaconMac = beaconMacEditable.toString();

            if(TextUtils.isEmpty(beaconMac)){
                break ADD_EMBEDDED_BEACON_IDENTIFIER;
            }

            Editable beaconNameEditable = ((EditText)findViewById(R.id.beaconNameET)).getEditableText();

            String beaconName = "DEFAULT";

            if(beaconNameEditable != null && !TextUtils.isEmpty(beaconNameEditable)){
                beaconName = beaconNameEditable.toString();
            }

            identifiers.add(new EmbeddedBeaconIdentifier(beaconMac, beaconName));
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String vehicleId = mDatabase.child("vehicles").push().getKey();

        createVehicle(new Vehicle(type, vehicleId, identifiers));
    }

    private void createVehicle(Vehicle vehicle) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("vehicles").child(vehicle.getId()).setValue(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreateVehicleActivity.this, "Vehicle create successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateVehicleActivity.this, "Error creating Vehicle", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
