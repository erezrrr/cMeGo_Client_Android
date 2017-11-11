package lab.cmego.com.cmegoclientandroid.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import lab.cmego.com.cmegoclientandroid.Persistence;
import lab.cmego.com.cmegoclientandroid.R;

/**
 * Created by Amit Ishai on 5/4/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    private SettingsItem mShowNotificationsItem;

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
    }


    private void renderStates(){
        mShowNotificationsItem.setSwitchState(Persistence.getSharedInstance().getShowNotifications());
    }
}
