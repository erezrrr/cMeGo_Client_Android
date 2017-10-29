package lab.cmego.com.cmegoclientandroid.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

import lab.cmego.com.cmegoclientandroid.files.FileManager;
import lab.cmego.com.cmegoclientandroid.service.MainService;


/**
 * Needs for reboot. After reboot service will start automatically
 */
public class ServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);

        Intent i = new Intent(context, MainService.class);
        context.startService(i);

        FileManager.appendToFileAsync(FileManager.DIRECTORY_CEME, FileManager.FILE_BLE_LOG,
                "\n" + strDate + ": Service woken up after boot\n\n");
    }
}
