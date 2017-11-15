package lab.cmego.com.cmegoclientandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.authentication.AuthenticationResult;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.fragments.auth.AuthFragment;
import lab.cmego.com.cmegoclientandroid.fragments.auth.NewSwipeAuthFragment;
import lab.cmego.com.cmegoclientandroid.interfaces.ResultListener;
import lab.cmego.com.cmegoclientandroid.model.Constants.UserAuthenticationMethod;
import lab.cmego.com.cmegoclientandroid.model.Controller;
import lab.cmego.com.cmegoclientandroid.model.GateState;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;
import lab.cmego.com.cmegoclientandroid.network.NetworkClient;
import lab.cmego.com.cmegoclientandroid.proximity.ProximityStateMachine;
import lab.cmego.com.cmegoclientandroid.proximity.ProximityWakerUpper;

public class GateFragment extends Fragment implements AuthFragment.AuthResultInterface, ProximityStateMachine.ProximityStateListener, ContentProvider.ContentProviderInterface {

    public final static String EXTRA_GATE_ID = "gate_id";
    private static boolean IN_FOREGROUND = false;

    private Gate mGate;
    private AuthFragment mCurrentAuthFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentProvider.getInstance().addListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_gate, container, false);

        // TODO set from last received here
        setAuthFragment(UserAuthenticationMethod.SWIPE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("condivityAutomatically","conditionalOpen onResume : " + ProximityWakerUpper.getInstance().isActivityConsumedState());

        conditionalOpenGateActivityAutomatically();

        ProximityStateMachine.getInstance().addProximityStateListener(this);

        IN_FOREGROUND = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        IN_FOREGROUND = false;
        ProximityStateMachine.getInstance().removeProximityStateListener(this);
    }

    @Override
    public void onContentRefreshed() {
//        setAdapterWithGates();
    }

    @Override
    public void onProximityStateChanged() {
//        mConsumedState = false;
//        mGateStateConsumed = false;

//        if(ProximityStateMachine.getInstance().getState() == ProximityStateMachine.ProximityState.CONNECTED_AND_CLOSE){
//            ProximityWakerUpper.getInstance().setActivityConsumedState(true);
//            Gate closestGate = ProximityStateMachine.getInstance().getClosestGate();
//            startGateActivity(closestGate);
//        }
        Log.d("condivityAutomatically","conditionalOpen onProximityStateChanged : " + ProximityWakerUpper.getInstance().isActivityConsumedState());

        conditionalOpenGateActivityAutomatically();
    }

    private void conditionalOpenGateActivityAutomatically() {

        Log.d("condivityAutomatically","conditionalOpen: " + ProximityWakerUpper.getInstance().isActivityConsumedState());

        if(ProximityStateMachine.getInstance().getState() == ProximityStateMachine.ProximityState.CONNECTED_AND_CLOSE){
            Log.d("condivityAutomatically","conditionalOpen: OPENING");
//            setActivityConsumedState(true);
            mGate = ProximityStateMachine.getInstance().getClosestGate();

            onChoseActiveGate();
        }

    }

    private void onChoseActiveGate() {
        if(mGate == null){
            Toast.makeText(getContext(), "GateId not found. Exiting Activity.", Toast.LENGTH_SHORT).show();
//            finish();
            return;
        }

        Controller controller = ContentProvider.getInstance().getControllerForGate(mGate.getId());

        String userId = ContentProvider.getInstance().getUserId();

        if(TextUtils.isEmpty(userId)){
            Toast.makeText(getContext(), "UserId not found. Exiting Activity.", Toast.LENGTH_SHORT).show();
//            finish();
            return;
        }

        NetworkClient.getInstance().initControllerInterface(controller.getBaseUrl());

        NetworkClient.getInstance().checkIn(userId, mGate.getId(),  new ResultListener<GateState>() {
            @Override
            public void onResult(GateState state) {
//                Toast.makeText(GateActivity.this, "Got this state:\n" + CustomGson.getInstance().toJson(state), Toast.LENGTH_LONG).show();

                onGotGateState(state);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Got this ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setAuthFragment(UserAuthenticationMethod method) {

        if(mCurrentAuthFragment != null &&
                mCurrentAuthFragment.getClass().equals(NewSwipeAuthFragment.class)){
            // Nothing yet

            if(mGate != null){
                mCurrentAuthFragment.setGateId(mGate.getId());
            }
        } else {
            mCurrentAuthFragment = new NewSwipeAuthFragment();

            if(mGate != null){
                Bundle bundle = new Bundle();
                bundle.putString(AuthFragment.EXTRA_GATE_ID, mGate.getId());
                mCurrentAuthFragment.setArguments(bundle);
            }

            // First remove any fragments
            List<Fragment> currentFragments = getChildFragmentManager().getFragments();

            if(currentFragments != null){

                for(Fragment fragment : currentFragments){
                    getChildFragmentManager().beginTransaction().remove(fragment).commit();
                }

            }

            getChildFragmentManager().beginTransaction()
                    .add(R.id.authContainer, mCurrentAuthFragment).commit();
        }


    }

    private void onGotGateState(GateState state) {
        setAuthFragment(state.getNextUserAuthMethod());
//        NewSwipeAuthFragment placeholder = new NewSwipeAuthFragment();
//
//        Bundle bundle = new Bundle();
////        bundle.putString(AuthFragment.EXTRA_GATE_ID, mGate.getId());
//        placeholder.setArguments(bundle);
//
//        getChildFragmentManager().beginTransaction()
//                .add(R.id.authContainer, placeholder).commit();
    }

    @Override
    public void onSuccess(String gateId, UserAuthenticationMethod method, AuthenticationResult authenticationResult) {
        Toast.makeText(getContext(), "Auth result: " + authenticationResult, Toast.LENGTH_LONG).show();
//        finish();
    }

    @Override
    public void onError(String gateId, UserAuthenticationMethod method, Exception e){
        Toast.makeText(getContext(), "Auth ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
