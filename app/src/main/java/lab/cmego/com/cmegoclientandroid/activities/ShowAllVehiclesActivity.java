package lab.cmego.com.cmegoclientandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.adapters.VehiclesRecyclerViewAdapter;
import lab.cmego.com.cmegoclientandroid.converters.VehicleConverter;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.Vehicle;
import lab.cmego.com.cmegoclientandroid.vehicle.VehicleContract;

public class ShowAllVehiclesActivity extends AppCompatActivity implements VehiclesRecyclerViewAdapter.ItemClickListener {

    private VehiclesRecyclerViewAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vehicles);

        mAuth = FirebaseAuth.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.membershipsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VehiclesRecyclerViewAdapter(this, new ArrayList<Vehicle>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference().child
                ("vehicles");

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Vehicle> items = new ArrayList<>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    VehicleContract vehicleContract = iterator.next().getValue(VehicleContract.class);

                    Vehicle vehicle = VehicleConverter.convert(vehicleContract);

                    items.add(vehicle);
                }


                adapter.setData(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
