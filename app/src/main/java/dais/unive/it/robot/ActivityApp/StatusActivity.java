package dais.unive.it.robot.ActivityApp;

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

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.R;

public class StatusActivity extends AppCompatActivity {
    private Button showNextActivity;
    private TextView textView;
    private ListView listView;
    private DataExchange ex = DataExchange.GetInstance();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Show number of ball in the robot
        textView =  findViewById(R.id.redNumber);
        textView.setText("" + DataExchange.GetColorQuantity(1));
        textView = findViewById(R.id.blueNumber);
        textView.setText("" + DataExchange.GetColorQuantity(2));
        textView = findViewById(R.id.greenNumber);
        textView.setText("" + DataExchange.GetColorQuantity(3));
        textView = findViewById(R.id.yellowNumber);
        textView.setText("" + DataExchange.GetColorQuantity(4));



        //Intent to go to the calendar activity
        showNextActivity = (Button) findViewById(R.id.calendarStatus);
        Intent intentCalendar = new Intent(StatusActivity.this, CalendarActivity.class);
        showNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCalendar);
            }
        });


    }


}
