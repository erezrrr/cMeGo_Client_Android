package lab.cmego.com.cmegoclientandroid.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import lab.cmego.com.cmegoclientandroid.MainActivity;
import lab.cmego.com.cmegoclientandroid.R;
import lab.cmego.com.cmegoclientandroid.ble.BleProximityProvider;
import lab.cmego.com.cmegoclientandroid.ble.BleScanAggregator;
import lab.cmego.com.cmegoclientandroid.ble.BleScanner;
import lab.cmego.com.cmegoclientandroid.content.ContentProvider;
import lab.cmego.com.cmegoclientandroid.log.ScanLogger;

/**
 * Created by Amit Ishai on 9/24/2017.
 */

public class MainService extends Service implements BleScanner.ScanBleInterface {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CMeGo Ble Scanning")
                .setContentText("Scanning...")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BleScanner.getInstance().addListener(this);
        BleScanner.getInstance().init(this);
        BleScanner.getInstance().startScanning();

        BleScanAggregator.getInstance().start();
        BleProximityProvider.getInstance().start();

        ScanLogger.getInstance().start();

        ContentProvider.getInstance().init();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BleScanner.getInstance().removeListener(this);
        BleScanner.getInstance().stopScanning();
        ScanLogger.getInstance().stop();
        BleScanAggregator.getInstance().stop();
        BleProximityProvider.getInstance().stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onScan(int callbackType, ScanResult result) {

    }
}
