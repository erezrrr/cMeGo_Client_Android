package lab.cmego.com.cmegoclientandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import lab.cmego.com.cmegoclientandroid.create.CreateVehicleActivity;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Account;
import lab.cmego.com.cmegoclientandroid.model.Billing.BillingDetails;
import lab.cmego.com.cmegoclientandroid.model.Billing.CashPaymentMethod;
import lab.cmego.com.cmegoclientandroid.model.Billing.PaymentMethod;
import lab.cmego.com.cmegoclientandroid.model.BluetoothDevice;
import lab.cmego.com.cmegoclientandroid.model.CheckPoint;
import lab.cmego.com.cmegoclientandroid.model.Client;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Constants.VehicleAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.Location;
import lab.cmego.com.cmegoclientandroid.model.Membership;
import lab.cmego.com.cmegoclientandroid.model.Profile;
import lab.cmego.com.cmegoclientandroid.model.User;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.PlateIdentifier;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.Vehicle;
import lab.cmego.com.cmegoclientandroid.model.Vehicle.VehicleIdentifier;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.network.server_facing.NetworkClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSignOutButton;
    private TextView mUserDetails;
    private Button mShowAllMembershipsButton;
    private Button mShowMyMembershipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //        fetchAllMemberships();

        mSignOutButton = (Button)findViewById(R.id.signOutButton);
        mUserDetails = (TextView)findViewById(R.id.userDetails);
        mShowAllMembershipsButton = (Button)findViewById(R.id.showAllMembershipsButton);
        mShowMyMembershipsButton = (Button)findViewById(R.id.showMyMembershipsButton);

        mShowMyMembershipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllMemberships();
                //                showMyMemberships();
            }
        });

        findViewById(R.id.showAllVehicles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllVehicles();
            }
        });

        findViewById(R.id.createVehicleButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVehicle();
            }
        });

        mShowAllMembershipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMemberships();
            }
        });

//        addUserData();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    showUI();

                    //                    addUserData();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(MainActivity.this, InitialActivity.class));
                    finish();
                }
                // ...
            }
        };
    }

    private void fetchAllMemberships() {

        NetworkClient.getInstance().getAllMembershipsForProfile(new ResultListener<Map<String, Membership>>() {
            @Override
            public void onResult(Map<String, Membership> stringMembershipMap) {
                Log.d("","");
            }

            @Override
            public void onError(Exception e) {
                Log.d("","");
            }
        });

    }

    private void createVehicle() {
        startActivity(new Intent(MainActivity.this, CreateVehicleActivity.class));
    }

    private void showAllVehicles() {
        startActivity(new Intent(MainActivity.this, ShowAllVehiclesActivity.class));
    }

    private void showMyMemberships() {
        startActivity(new Intent(MainActivity.this, ShowMyMembershipsActivity.class));
    }

    private void showMemberships() {
        startActivity(new Intent(MainActivity.this, ShowMembershipsActivity.class));
    }

    private void addUserData() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String accId = mDatabase.child("accounts").push().getKey();

        String profileId = currentUser.getUid();
        String payerProfileId = profileId;

        Profile profile = new Profile(profileId, "Amit", "Ishai", "www.google.com", "0506987741");
//        Profile payerProfile = new Profile(payerProfileId, "Erez", "Rosenwaks", "www.pornhub.com", "0522732052");
        Profile payerProfile = profile;

        mDatabase.child("profiles").child(profileId).setValue(profile);
        mDatabase.child("profiles").child(payerProfileId).setValue(payerProfile);


        String userId = mDatabase.child("users").push().getKey();
        String checkpointId = mDatabase.child("checkpoints").push().getKey();

        User user = new User(userId, profileId);
        mDatabase.child("users").child(userId).setValue(user);

        VehicleIdentifier vehicleIdentifier = new PlateIdentifier("987356","IL");
        ArrayList<VehicleIdentifier> vehicleIdentifiers = new ArrayList<>();
        vehicleIdentifiers.add(vehicleIdentifier);

        DatabaseReference i = mDatabase.child("vehicles").push();
        String vehicleId = i.getKey();

        Vehicle vehicle = new Vehicle(Vehicle.Type.SPORTS_CAR, vehicleId, vehicleIdentifiers);

        mDatabase.child("vehicles").child(i.getKey()).setValue(vehicle);

        String membId = mDatabase.child("memberships").push().getKey();

        Membership membership = new Membership()
                .setActivationDate(new Date(System.currentTimeMillis()))
                .setExpirationDate(new Date(System.currentTimeMillis()))
                .setInternalAddress("Apt 2")
                .setUserId(userId)
                .setVehicleId(vehicleId)
                .setType(Membership.Type.SYSTEM_ADMIN)
                .setAccountId(accId)
                .setCheckpointId(checkpointId)
                .setId(membId);

        mDatabase.child("memberships").child(membId).setValue(membership);

        String locId = mDatabase.child("locations").push().getKey();

        Location location = new Location(locId, "99 Beverly Hills Rd.", Location.Type.PRIVATE);

        mDatabase.child("locations").child(locId).setValue(location);

        String clientId = mDatabase.child("clients").push().getKey();
        Client client = new Client(Client.Type.PRIVATE, clientId, payerProfileId);
        mDatabase.child("clients").child(clientId).setValue(client);

        ArrayList<String> memberShipIds = new ArrayList<>();
        memberShipIds.add(membId);

        ArrayList<UserAuthenticationMethod> userAuthenticationMethods = new ArrayList<>();
        userAuthenticationMethods.add(UserAuthenticationMethod.SWIPE);

        ArrayList<VehicleAuthenticationMethod> vehicleAuthenticationMethods = new ArrayList<>();
        vehicleAuthenticationMethods.add(VehicleAuthenticationMethod.PLATE_RECOGNITION);

        BluetoothDevice bluetoothDevice = new BluetoothDevice("AE:23:97:0C:39:B1", "BuddyBeacon#2");

        String wifiNetworkId = mDatabase.child("wifi_networks").push().getKey();

        WifiNetwork wifiNetwork = new WifiNetwork(wifiNetworkId, "73:5E:20:F2:68:01", "Great Wifi", "secretPassword!");

        String controllerId = mDatabase.child("controllers").push().getKey();

        Controller controller = new Controller(controllerId, "0.0.1", bluetoothDevice);

        mDatabase.child("controllers").child(controllerId).setValue(controller);
        mDatabase.child("wifi_networks").child(wifiNetworkId).setValue(wifiNetwork);


        Gate gate = new Gate(Gate.Type.DOUBLE_DOOR);
        gate.setThumbnailUrl("http://cornucopia3d.e-oncontent.com/storeItems/Objects/Exterior/Fencing/Mansion_Gate_Kit_3_0_img.jpg?mod=1");

        ArrayList<Gate> gates = new ArrayList<>();
        gates.add(gate);

        CheckPoint checkPoint = new CheckPoint(checkpointId, accId, controllerId,
                wifiNetworkId, gates, memberShipIds, userAuthenticationMethods, vehicleAuthenticationMethods);

        mDatabase.child("checkpoints").child(checkpointId).setValue(checkPoint);

        PaymentMethod paymentMethod = new CashPaymentMethod();
        BillingDetails billingDetails = new BillingDetails(paymentMethod, 50000, BillingDetails
                .Frequency.MONTHLY, new Date(System.currentTimeMillis()));

        ArrayList<String> checkpointIds = new ArrayList<>();
        checkpointIds.add(checkpointId);

        Account account = new Account(accId, locId, clientId, Account.Type.RESIDENTIAL
                , new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), checkpointIds, billingDetails);

        mDatabase.child("accounts").child(accId).setValue(account);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        showUI();
    }

    private void showUI() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        mUserDetails.setVisibility(View.VISIBLE);

        setUserDetails(currentUser);
    }

    private void setUserDetails(FirebaseUser currentUser) {
        String details = "Email: " + currentUser.getEmail() + "\n" +
                "Display Name: " + currentUser.getDisplayName() + "\n" +
                "UserId: " + currentUser.getUid() + "\n";

        mUserDetails.setText(details);

    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
