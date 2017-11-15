package lab.cmego.com.cmegoclientandroid.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.service.MainService;

/**
 * Created by Amit Ishai on 5/4/2017.
 */

public class SettingsFragment extends Fragment {

    private SettingsItem mShowNotificationsItem;
    private SettingsItem mConnectToWifiAutomatically;
    private SettingsItem mStartServiceInForeground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.activity_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setupSwitches();
        renderStates();
    }

    private void setupSwitches() {
        mShowNotificationsItem = (SettingsItem) getView().findViewById(R.id.showNotificationsItem);

        mShowNotificationsItem.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Persistence.getSharedInstance().setShowNotifications(checked);
                renderStates();
            }
        });

        mConnectToWifiAutomatically = (SettingsItem) getView().findViewById(R.id.automaticConnectToWifi);

        mConnectToWifiAutomatically.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Persistence.getSharedInstance().setConnectToWifiAutomatically(checked);
                renderStates();
            }
        });

        mStartServiceInForeground = (SettingsItem) getView().findViewById(R.id.serviceInForeground);

        mStartServiceInForeground.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Persistence.getSharedInstance().setStartServiceInForeground(checked);
                restartService();
                renderStates();
            }
        });

    }

    private void restartService() {
        getContext().stopService(new Intent(getContext(), MainService.class));
        getContext().startService(new Intent(getContext(), MainService.class));
    }


    private void renderStates(){
        mShowNotificationsItem.setSwitchState(Persistence.getSharedInstance().getShowNotifications());
        mConnectToWifiAutomatically.setSwitchState(Persistence.getSharedInstance().getConnectToWifiAutomatically());
        mStartServiceInForeground.setSwitchState(Persistence.getSharedInstance().getStartServiceInForeground());
    }
}
