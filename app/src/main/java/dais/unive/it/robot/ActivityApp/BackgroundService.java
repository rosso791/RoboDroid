package dais.unive.it.robot.ActivityApp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.AutomationTask;
import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventManager;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.R;
import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;
import it.unive.dais.legodroid.lib.util.Prelude;

public class BackgroundService extends Service {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    private static final String TAG = "MyActivity";
    public Context context = this;
    private Timer timer;
    Handler handler=new Handler();
    EV3 ev3;
    NotificationHelper notificationHelper = new NotificationHelper(this);

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
        NotificationHelper notificationHelper = new NotificationHelper(this);
        DataExchange.GetInstance();
        AutomationTask.GetInstance();
        try {
            // connect to EV3 via bluetooth
            ev3 = new EV3(new BluetoothConnection("EV3").connect());    // replace with your own brick name
            Prelude.trap(() -> ev3.run(AutomationTask::StartTimer));
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //Prelude.trap(() -> ev3.run(AutomationTask::StartTimer));
                    notificationHelper.checkNotification();
                    EventManager.GetInstance();
                }
            }, 0, 10*1000);

        } catch (IOException e) {
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }

        //AutomationTask.StartTimer();
        //handler.post(updateTextRunnable);



    }
}
