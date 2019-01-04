package dais.unive.it.robot.ActivityApp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventManager;
import dais.unive.it.robot.CalendarClass.WeekDay;
import dais.unive.it.robot.R;

import static dais.unive.it.robot.CalendarClass.EventManager.GetInstance;


public class CalendarActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String res = "";
        final Map<Integer, Map<WeekDay, Event>> allEvents = EventManager.GetInstance().getAllEvents();
        res = allEvents.toString();

        Toast toast = Toast.makeText(getApplicationContext(),res
                ,
                Toast.LENGTH_SHORT);

        toast.show();
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.add_event_menu);
        popup.show();

    }

    public void goState (View v){
        Intent i = new Intent(this, StatusActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                //Toast.makeText(this, "add click", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(CalendarActivity.this, AddEvent.class);
                startActivity(intent2);
            case R.id.delete:
                Intent intent3 = new Intent(CalendarActivity.this, EliminateEvent.class);
                startActivity(intent3);
                //Toast.makeText(this, "delete click", Toast.LENGTH_SHORT).show();

        }
        return true;
    }


}
