package lab.cmego.com.cmegoclientandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lab.cmego.com.cmegoclientandroid.DataModel;
import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.adapters.DrawerItemCustomAdapter;
import lab.cmego.com.cmegoclientandroid.adapters.GatesRecyclerViewAdapter;
import lab.cmego.com.cmegoclientandroid.fragments.GateFragment;
import lab.cmego.com.cmegoclientandroid.proximity.ProximityWakerUpper;
import lab.cmego.com.cmegoclientandroid.service.MainService;
import lab.cmego.com.cmegoclientandroid.settings.SettingsFragment;

public class NavMainActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button mSignOutButton;
    private TextView mUserDetails;
    private GatesRecyclerViewAdapter mAdapter;

//    public static boolean IN_FOREGROUND = false;
//    private boolean mConsumedState= false;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_ac);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[3];

        drawerItem[0] = new DataModel(R.drawable.icon, "Open Gate");
        drawerItem[1] = new DataModel(R.drawable.icon, "Settings");
        drawerItem[2] = new DataModel(R.drawable.icon, "Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();






        startService(new Intent(this, MainService.class));

        mAuth = FirebaseAuth.getInstance();

//        ContentProvider.getInstance().addListener(this);
//        setupRecyclerView();

        mSignOutButton = (Button)findViewById(R.id.signOutButton);
        mUserDetails = (TextView)findViewById(R.id.userDetails);

//        findViewById(R.id.creationPortalActivityButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openCreationPortalActivity();
//            }
//        });
//
//        findViewById(R.id.showPortalButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openShowPortalActivity();
//            }
//        });
//
//        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSettingsActivity();
//            }
//        });
//
//        findViewById(R.id.newUIButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNewUIActivity();
//            }
//        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

//                    showUI();

                    //                    addUserData();
                } else {
                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(NavMainActivity.this, InitialActivity.class));
                    finish();
                }
                // ...
            }
        };










        selectItem(0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("condivityAutomatically","conditionalOpen onResume : " + ProximityWakerUpper.getInstance().isActivityConsumedState());

//        conditionalOpenGateActivityAutomatically();

//        ProximityStateMachine.getInstance().addProximityStateListener(this);

//        IN_FOREGROUND = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        IN_FOREGROUND = false;
//        ProximityStateMachine.getInstance().removeProximityStateListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
//        showUI();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

//    @Override
//    public void onContentRefreshed() {
////        setAdapterWithGates();
//    }
//
//    @Override
//    public void onProximityStateChanged() {
//        mConsumedState = false;
////        mGateStateConsumed = false;
//
////        if(ProximityStateMachine.getInstance().getState() == ProximityStateMachine.ProximityState.CONNECTED_AND_CLOSE){
////            ProximityWakerUpper.getInstance().setActivityConsumedState(true);
////            Gate closestGate = ProximityStateMachine.getInstance().getClosestGate();
////            startGateActivity(closestGate);
////        }
//        Log.d("condivityAutomatically","conditionalOpen onProximityStateChanged : " + ProximityWakerUpper.getInstance().isActivityConsumedState());
//
//        conditionalOpenGateActivityAutomatically();
//    }
//
//    private void conditionalOpenGateActivityAutomatically() {
//
//        Log.d("condivityAutomatically","conditionalOpen: " + ProximityWakerUpper.getInstance().isActivityConsumedState());
//
//        if(!isActivityConsumedState() && ProximityStateMachine.getInstance().getState() == ProximityStateMachine.ProximityState.CONNECTED_AND_CLOSE){
//            Log.d("condivityAutomatically","conditionalOpen: OPENING");
//            setActivityConsumedState(true);
//            Gate closestGate = ProximityStateMachine.getInstance().getClosestGate();
////            startGateActivity(closestGate);
//        }
//
//    }
//
//    private void setActivityConsumedState(boolean b) {
//        mConsumedState = b;
//    }
//
//    private boolean isActivityConsumedState() {
//        return mConsumedState;
//    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

//        Fragment fragment = null;

        switch (position) {
            case 0:
                mFragment = new GateFragment();
                break;
            case 1:
                mFragment = new SettingsFragment();
                break;
            case 2:
//                fragment = new TableFragment();
                break;

            default:
                break;
        }

        if (mFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, mFragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}