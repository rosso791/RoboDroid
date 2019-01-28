package dais.unive.it.robot.ActivityApp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;



import dais.unive.it.robot.Automation.DataExchange;

import dais.unive.it.robot.R;

public class StatusActivity extends AppCompatActivity {
    private Button showNextActivity;
    private TextView textView;
    private ListView listView;
    NotificationManager notificationManager;
    Handler handler = new Handler();
    private static final long TIME_DELAY = 100;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataExchange.GetInstance();

        setContentView(R.layout.activity_status);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Intent to go to the calendar activity
        showNextActivity = (Button) findViewById(R.id.calendarStatus);
        Intent intentCalendar = new Intent(StatusActivity.this, CalendarActivity.class);
        showNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCalendar);
            }
        });


        handler.post(updateTextRunnable);


    }

    Runnable updateTextRunnable=new Runnable(){
        public void run() {
            TextView redValue = findViewById(R.id.redNumber);
            TextView blueValue = findViewById(R.id.blueNumber);
            TextView greenValue = findViewById(R.id.greenNumber);
            TextView yellowValue = findViewById(R.id.yellowNumber);
            TextView notify = findViewById(R.id.alarmText);
            Button alarmButton = findViewById(R.id.alarmButton);
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            //Show number of ball in the robot
            redValue.setText("" + DataExchange.GetColorQuantity(1));
            blueValue.setText("" + DataExchange.GetColorQuantity(2));
            greenValue.setText("" + DataExchange.GetColorQuantity(3));
            yellowValue.setText("" + DataExchange.GetColorQuantity(4));

            if (DataExchange.GetNotificationCode() == 0) {
                notify.setText("Non ci sono problemi");
            }
            else{
                notify.setText(DataExchange.GetNotificationDescription());
            }

            if (DataExchange.GetNotificationCode() != 0) {
                alarmButton.setEnabled(true);
            } else alarmButton.setEnabled(false);

            alarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataExchange.SetNotificationCode(1000);
                    notificationManager.cancel(0);
                    notify.setText("Non ci sono problemi");
                    alarmButton.setEnabled(false);
                }
            });

            handler.postDelayed(this, TIME_DELAY);
        }


    };
}
