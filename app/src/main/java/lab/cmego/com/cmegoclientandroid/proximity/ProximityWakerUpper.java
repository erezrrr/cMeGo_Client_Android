
package lab.cmego.com.cmegoclientandroid.proximity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.activities.MainActivity;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.model.Checkpoint;
import lab.cmego.com.cmegoclientandroid.model.WifiNetwork;
import lab.cmego.com.cmegoclientandroid.model.gate.Gate;

/**
 * Created by Owner on 08/11/2017.
 */

public class ProximityWakerUpper implements ProximityStateMachine.ProximityStateListener {

    private static ProximityWakerUpper sInstance;
    private Context mContext;

    private boolean mActivityConsumedState = false;

    private ProximityWakerUpper(){}

    public static ProximityWakerUpper getInstance(){
        if(sInstance == null){
            sInstance = new ProximityWakerUpper();
        }

        return sInstance;
    }

    public void init(Context context){
        mContext = context;
        ProximityStateMachine.getInstance().addProximityStateListener(this);
    }

    @Override
    public void onProximityStateChanged() {
        Log.d("condivityAutomatically","conditionalOpen onProximityStateChanged FFFSTATE : " + ProximityStateMachine.getInstance().getState());

        mActivityConsumedState = false;

        ProximityStateMachine.ProximityState state = ProximityStateMachine.getInstance().getState();

        Gate closestGate = ProximityStateMachine.getInstance().getClosestGate();

        switch (state){
            case DEFAULT:

                break;
            case ONLY_CLOSE:

                connectToNetworkForGate(closestGate);

                break;

            case ONLY_CONNECTED:

                break;

            case CONNECTED_AND_CLOSE:

                onEnteringGate(closestGate);

                break;
        }
    }

    public boolean isActivityConsumedState() {
        return mActivityConsumedState;
    }

    public void setActivityConsumedState(boolean activityConsumedState) {
        mActivityConsumedState = activityConsumedState;
    }

    private void connectToNetworkForGate(Gate gate) {
        WifiNetwork wifiNetwork = ContentProvider.getInstance().getWifiNetworkForGate(gate.getId());

    }

    private void onEnteringGate(Gate closestGate) {
        if(MainActivity.IN_FOREGROUND){
            // Do nothing, MainActivity will handle
        } else {

            if(Persistence.getSharedInstance().getShowNotifications()){
                showNotificationForEnteringGate(closestGate);
            }

        }
    }

    private void showNotificationForEnteringGate(Gate closestGate) {

        Checkpoint checkpoint = ContentProvider.getInstance().getCheckpointForGate(closestGate);

        Intent myIntent = new Intent(mContext, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification myNotification = new NotificationCompat.Builder(mContext)
                .setContentTitle("You are near " + checkpoint.getName() + ": " + closestGate.getName())
                .setContentText("Click to open")
                .setTicker("Near Gate!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon)
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(67, myNotification);
    }
}
