package lab.cmego.com.cmegoclientandroid.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.service.MainService;

/**
 * Created by Amit Ishai on 5/4/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    private SettingsItem mShowNotificationsItem;
    private SettingsItem mConnectToWifiAutomatically;
    private SettingsItem mStartServiceInForeground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupSwitches();
        renderStates();
    }

    private void setupSwitches() {
        mShowNotificationsItem = (SettingsItem) findViewById(R.id.showNotificationsItem);

        mShowNotificationsItem.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Persistence.getSharedInstance().setShowNotifications(checked);
                renderStates();
            }
        });

        mConnectToWifiAutomatically = (SettingsItem) findViewById(R.id.automaticConnectToWifi);

        mConnectToWifiAutomatically.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Persistence.getSharedInstance().setConnectToWifiAutomatically(checked);
                renderStates();
            }
        });

        mStartServiceInForeground = (SettingsItem) findViewById(R.id.serviceInForeground);

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
        stopService(new Intent(this, MainService.class));
        startService(new Intent(this, MainService.class));
    }


    private void renderStates(){
        mShowNotificationsItem.setSwitchState(Persistence.getSharedInstance().getShowNotifications());
        mConnectToWifiAutomatically.setSwitchState(Persistence.getSharedInstance().getConnectToWifiAutomatically());
        mStartServiceInForeground.setSwitchState(Persistence.getSharedInstance().getStartServiceInForeground());
    }
}
