package lab.cmego.com.cmegoclientandroid.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Amit Ishai on 5/4/2017.
 */

public class SettingsActivity extends Activity {

    private SettingsItem mDebugItem;
    private SettingsItem mAddRideIdPrefixItem;
    private SettingsItem mSendStartRideItem;
    private SettingsItem mDeleteLogsItem;
    private SettingsItem mUpdateDevicesItem;
    private SettingsItem mRequestUpdatesItem;
    private SettingsItem mAddDeviceNameToLogsItem;
    private SettingsItem mRestartFrozenDevicesItem;
    private SettingsItem mShowSimulatorToggleItem;
    private SettingsItem mShowDeviceInBackgroundItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);

        setupSwitches();

        renderStates();
    }

    private void setupSwitches() {
//        mDebugItem = (SettingsItem) findViewById(R.id.debugItem);
//
//        mDebugItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setInDebugMode(checked);
//                renderStates();
//            }
//        });
//
//        mAddRideIdPrefixItem = (SettingsItem) findViewById(R.id.addRideIdPrefixItem);
//
//        mAddRideIdPrefixItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setAddRideIdPrefix(checked);
//            }
//        });
//
//        mSendStartRideItem = (SettingsItem) findViewById(R.id.sendStartRideItem);
//
//        mSendStartRideItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setSendStartRideToDevices(checked);
//            }
//        });
//
//        mDeleteLogsItem = (SettingsItem) findViewById(R.id.deleteLogsItem);
//
//        mDeleteLogsItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setDeleteLogs(checked);
//            }
//        });
//
//        mUpdateDevicesItem = (SettingsItem) findViewById(R.id.updateDevicesItem);
//
//        mUpdateDevicesItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setUpdateDevices(checked);
//            }
//        });
//
//        mRequestUpdatesItem = (SettingsItem) findViewById(R.id.requestUpdatesItem);
//
//        mRequestUpdatesItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setRequestUpdatesFromServerDuringSync(checked);
//            }
//        });
//
//        mAddDeviceNameToLogsItem = (SettingsItem) findViewById(R.id.addDeviceNameToLogsItem);
//
//        mAddDeviceNameToLogsItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setAddDeviceNamesToLogFiles(checked);
//            }
//        });
//
//        mRestartFrozenDevicesItem = (SettingsItem) findViewById(R.id.restartFrozenDevicesItem);
//
//        mRestartFrozenDevicesItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setRestartFrozenDevicesAutomatically(checked);
//            }
//        });
//
//        mShowSimulatorToggleItem = (SettingsItem) findViewById(R.id.showSimulatorToggleItem);
//
//        mShowSimulatorToggleItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setAllowSimulatorUsageToggle(checked);
//            }
//        });
//
//        mShowDeviceInBackgroundItem = (SettingsItem) findViewById(R.id.showDeviceInBackgroundItem);
//
//        mShowDeviceInBackgroundItem.setListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Global.setShowDeviceInBackgroundIcon(checked);
//            }
//        });
//
//        final EditText delayET = (EditText)findViewById(R.id.delay);
//        delayET.setText("" + Persistence.getSharedInstance().getRideStartDelay());
//        delayET.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
//
//                    Editable editable = delayET.getEditableText();
//
//                    if(editable != null && !TextUtils.isEmpty(editable.toString())){
//
//                        String text = editable.toString();
//
//                        try {
//                            int value = Integer.valueOf(text);
//
//                            Persistence.getSharedInstance().setRideStartDelay(value);
//
//                            Toast.makeText(SettingsActivity.this, "Ride Delay set to " + value + " second" + (value == 1 ? "" : "s"), Toast.LENGTH_LONG).show();
//
//                            return true;
//
//                        } catch (NumberFormatException e){
//
//                        }
//                    }
//
//                    return true;
//                }
//                return false;
//            }
//        });
    }


    private void renderStates(){
//        mDebugItem.setSwitchState(Global.inDebugMode());
//        mAddRideIdPrefixItem.setSwitchState(Global.addRideIdPrefix());
//        mSendStartRideItem.setSwitchState(Global.sendStartRideToDevices());
//        mDeleteLogsItem.setSwitchState(Global.deleteLogs());
//        mUpdateDevicesItem.setSwitchState(Global.updateDevices());
//        mRequestUpdatesItem.setSwitchState(Global.requestUpdatesFromServerDuringSync());
//        mAddDeviceNameToLogsItem.setSwitchState(Global.addDeviceNamesToLogFiles());
//        mRestartFrozenDevicesItem.setSwitchState(Global.restartFrozenDevicesAutomatically());
//        mShowSimulatorToggleItem.setSwitchState(Global.allowSimulatorUsageToggle());
//        mShowDeviceInBackgroundItem.setSwitchState(Global.showDeviceInBackgroundIcon());
//
//        mAddRideIdPrefixItem.setSwitchEnabled(Global.inDebugMode());
//        mSendStartRideItem.setSwitchEnabled(Global.inDebugMode());
//        mDeleteLogsItem.setSwitchEnabled(Global.inDebugMode());
//        mUpdateDevicesItem.setSwitchEnabled(Global.inDebugMode());
//        mRequestUpdatesItem.setSwitchEnabled(Global.inDebugMode());
//        mAddDeviceNameToLogsItem.setSwitchEnabled(Global.inDebugMode());
//        mRestartFrozenDevicesItem.setSwitchEnabled(Global.inDebugMode());
//        mShowSimulatorToggleItem.setSwitchEnabled(Global.inDebugMode());
//        mShowDeviceInBackgroundItem.setSwitchEnabled(Global.inDebugMode());
    }
}
