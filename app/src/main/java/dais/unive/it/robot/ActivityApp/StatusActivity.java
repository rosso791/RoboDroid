package dais.unive.it.robot.ActivityApp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.R;

public class StatusActivity extends AppCompatActivity {
    private Button showNextActivity;
    private TextView textView;
    private ListView listView;
    private DataExchange ex = DataExchange.GetInstance();
    private NotificationManager notificationManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Show number of ball in the robot
        textView = findViewById(R.id.redNumber);
        textView.setText("" + DataExchange.GetColorQuantity(1));
        textView = findViewById(R.id.blueNumber);
        textView.setText("" + DataExchange.GetColorQuantity(2));
        textView = findViewById(R.id.greenNumber);
        textView.setText("" + DataExchange.GetColorQuantity(3));
        textView = findViewById(R.id.yellowNumber);
        textView.setText("" + DataExchange.GetColorQuantity(4));


        textView = findViewById(R.id.alarmText);
        if (DataExchange.GetNotificationCode() == 0) {
            textView.setText("Non ci sono problemi");
        }
        else{
            textView.setText(DataExchange.GetNotificationDescription());
        }

        Button alarmButton = findViewById(R.id.alarmButton);
        if (DataExchange.GetNotificationCode() != 0) {
            alarmButton.setEnabled(true);
        } else alarmButton.setEnabled(false);

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataExchange.SetNotificationCode(0);
                notificationManager.cancel(0);
                textView.setText("Non ci sono problemi");
                alarmButton.setEnabled(false);
            }
        });


        //Intent to go to the calendar activity
        showNextActivity = (Button) findViewById(R.id.calendarStatus);
        Intent intentCalendar = new Intent(StatusActivity.this, CalendarActivity.class);
        showNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCalendar);
            }
        });


        //Show notification
       /* Timer timer = new Timer();
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
