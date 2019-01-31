package dais.unive.it.robot.ActivityApp;


import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;

import android.support.v7.app.AlertDialog;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.AutomationTask;
import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventManager;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;
import it.unive.dais.legodroid.lib.util.Prelude;

public class BackgroundService extends Service {

    private static final String TAG = "MyActivity";
    public Context context = this;
    EV3 ev3;
    public static Service pa;

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        startForeground();

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }


    private void startForeground() {
        /*Intent notificationIntent = new Intent(this, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());*/

        Timer timer = new Timer();
        Timer notify = new Timer();
        NotificationHelper notificationHelper = new NotificationHelper(this);
        DataExchange.GetInstance();
        AutomationTask.GetInstance();
        try {
            // connect to EV3 via bluetooth
            ev3 = new EV3(new BluetoothConnection("EV3").connect());
            // replace with your own brick name
            Prelude.trap(() -> ev3.run(AutomationTask::StartTimer));


            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //Prelude.trap(() -> ev3.run(AutomationTask::StartTimer));
                    notificationHelper.checkNotification();
                    EventManager.GetInstance().checkEvent();
                    System.out.print("ciao");
                }
            }, 0, 60 * 1000);

        } catch (IOException e) {
            Intent dialogIntent = new Intent(this, AlertActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }


    }
}
