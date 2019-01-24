package dais.unive.it.robot.ActivityApp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.R;

public class MenuActivity extends AppCompatActivity {
    private Button showNextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startService(new Intent(this, BackgroundService.class));


        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Calendar button that leads to CalendarActivity
        showNextActivity = (Button) findViewById(R.id.calendarButton);
        Intent intentCalendar = new Intent(MenuActivity.this, CalendarActivity.class);
        showNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCalendar);
            }
        });


        //Status button that leads to StatusActivity
        showNextActivity = (Button) findViewById(R.id.statusButton);
        Intent intentStatus = new Intent(MenuActivity.this, StatusActivity.class);
        showNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentStatus);
            }
        });


        //DataExchange.SetNotificationCode(-4);

        //Show notification
        /*Timer timer = new Timer();
        NotificationHelper notificationHelper = new NotificationHelper(this);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                    if (DataExchange.GetNotificationCode() !=0 ){
                        notificationHelper.createNotification("Android", DataExchange.GetNotificationDescription());
                    }
            }
        }, 0, 10*1000);*/
    }
}
