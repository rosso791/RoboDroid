package dais.unive.it.robot.ActivityApp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.R;
import dais.unive.it.robot.CalendarClass.EventData;


public class CalendarActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // TODO Sebastian - sample data to eliminate
        EventData tempData = new EventData();
        ArrayList<Event> tempEventList = tempData.getEventList();


        TableLayout tableLayout = findViewById(R.id.calendar_table_layout);
        Context context = getApplicationContext();

        this.drawCalendarHead(tableLayout, context);
        // TODO Sebastian -  modify tempEventList parameter with real eventList
        this.drawCalendar(tempEventList, tableLayout, context);

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

    private void drawCalendarHead(TableLayout inputTableLayout, Context inputContext) {
        TableRow firstTableRow = new TableRow(inputContext);
        TableRow.LayoutParams firstLayoutParams = new TableRow.LayoutParams();
        firstTableRow.setLayoutParams(firstLayoutParams);

        Button firstHourView = new Button(inputContext);
        firstHourView.setBackgroundColor(0);
        firstTableRow.addView(firstHourView);

        String[] tempArray = getResources().getStringArray(R.array.short_days_array);

        for (int i = 0; i < 7; i++) {
            Button tempView = new Button(inputContext);
            tempView.setText(tempArray[i]);
            tempView.setTextColor(Color.WHITE);
            tempView.setBackgroundColor(Color.DKGRAY);
            firstTableRow.addView(tempView);
        }
        inputTableLayout.addView(firstTableRow);
    }


    // Precondition: eventList orderd by hour,min,day,colour (follow the order)
    private void drawCalendar(ArrayList<Event> eventList, TableLayout inputTableLayout, Context inputContext) {
        int[] resourceColours = getResources().getIntArray(R.array.items_colors);
        int listSize = eventList.size();
        BitSet bitMap = new BitSet(listSize);

        int i, j;
        i = 0;
        j = 1;

        // Now create the dynamic table, each i cycle is a row
        while (i < listSize) {
            // Now we add thisRow events (only add those Events not added until now)
            if (!bitMap.get(i)) {
                Event tempEvent = eventList.get(i);

                // Create a new table row
                TableRow tableRow = new TableRow(inputContext);

                // Set new table row layout parameters
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();//TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(layoutParams);

                // Add the hour TextView in the first column.
                Button hourView = new Button(inputContext);
                int thisHour = tempEvent.getWhen().get(Calendar.HOUR_OF_DAY);   //getTime().get(Calendar.HOUR_OF_DAY);
                int thisMinute = tempEvent.getWhen().get(Calendar.MINUTE);      //getTime().get(Calendar.MINUTE);
                String tempTime = String.format("%02d:%02d", thisHour, thisMinute);

                hourView.setText(tempTime);
                hourView.setTextColor(Color.WHITE);
                hourView.setBackgroundColor(Color.DKGRAY);

                tableRow.addView(hourView, 0);

                // Create a temp list using actual delivery time. First element is the ith event
                ArrayList<Event> thisRowList = new ArrayList<>();
                thisRowList.add(tempEvent);
                bitMap.set(i);

                while (j < listSize && eventList.get(j).getWhen().get(Calendar.HOUR_OF_DAY) == thisHour &&
                        eventList.get(j).getWhen().get(Calendar.MINUTE) == thisMinute) {
                    // Only 1 colour per slot so compare jth day with the last tempList element day
                    if (!bitMap.get(j) && thisRowList.get(thisRowList.size() - 1).getDay() !=
                            eventList.get(j).getDay()) {
                        thisRowList.add(eventList.get(j));
                        // jth element is set on true
                        bitMap.set(j);
                    }
                    j++;
                }

                // Draw this tableRow
                int k, r;
                k = r = 0;

                while (k < 7) {
                    Button tempView = new Button(inputContext);
                    if (r < thisRowList.size() && thisRowList.get(r).getDay().getHierarchy() == k) {
                        tempView.setBackgroundColor(resourceColours[thisRowList.get(r).getColor().getHierarchy()]);
                        r++;
                    } else
                        tempView.setBackgroundColor(Color.parseColor("#969696"));
                    tableRow.addView(tempView);
                    k++;
                }

                inputTableLayout.addView(tableRow);
            }
            i++;
            j = i + 1;
        }
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.add_event_menu);
        popup.show();
    }

    public void goState(View v) {
        Intent i = new Intent(this, StatusActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                //Toast.makeText(this, "add click", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(CalendarActivity.this, AddEvent.class);
                startActivity(intent2);
                //TODO Sebastian - modifica 01a: aggiunto break
                break;
            case R.id.delete:
                //TODO Sebastian - modifica 01a: aggiunto questa partea, break incluso
                Intent intent3 = new Intent(CalendarActivity.this, EliminateEvent.class);
                startActivity(intent3);
                //Toast.makeText(this, "delete click", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}
