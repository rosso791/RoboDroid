package dais.unive.it.robot.ActivityApp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.io.IOException;

import dais.unive.it.robot.Automation.AutomationTask;
import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.R;
import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.BluetoothConnection;
import it.unive.dais.legodroid.lib.util.Prelude;

public class MenuActivity extends AppCompatActivity {
    private Button showNextActivity;
    private static final String TAG = Prelude.ReTAG("MainActivity");
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        DataExchange.GetInstance();
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fa = this;
        startService(new Intent(this, BackgroundService.class));

        DataExchange.GetInstance();
        AutomationTask.GetInstance();

        /*try {
            // connect to EV3 via bluetooth
            EV3 ev3 = new EV3(new BluetoothConnection("EV3").connect());    // replace with your own brick name
            //Prelude.trap(() -> ev3.run(this::legomain));
            Prelude.trap(() -> ev3.run(AutomationTask::StartTimer));


        } catch (IOException e) {
            Log.e(TAG, "fatal error: cannot connect to EV3");
            e.printStackTrace();
        }*/


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


    }
}
