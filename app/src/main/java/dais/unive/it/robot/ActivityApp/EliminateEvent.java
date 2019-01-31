package dais.unive.it.robot.ActivityApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventData;
import dais.unive.it.robot.CalendarClass.EventManager;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.CalendarClass.PillColors;
import dais.unive.it.robot.R;


public class EliminateEvent extends AppCompatActivity {
    private Button eliminateButton;

    EventData tempEventData = new EventData();
    ArrayList<Event> tempEventList = tempEventData.getEventList();

    /**
     * eliminatedEventsList: elements checked by the user ready to eliminate
     * checkBoxIdList: list of Ids used by listeners to find selected items
     * checkBoxList: list of checkBoxes used to select all boxes on the first row Button listener
     */
    ArrayList<Event> eliminatedEventsList = new ArrayList<>();
    ArrayList<Integer> checkBoxIdList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxList = new ArrayList<>();
    ArrayList<Boolean> booleanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminate_event);

        TableLayout eliminateTableLayout = findViewById(R.id.eliminate_event_table_layout);
        Context eliminateContext = getApplicationContext();
        for (int i = 0; i < tempEventList.size(); i++)
            booleanList.add(false);
        eliminateButton = findViewById(R.id.eliminate_button);
        eliminateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View w) {
                eliminate();
            }
        });

        for (int i = 0; i < tempEventList.size(); i++) {
            Log.i("elements", "color: " + tempEventList.get(i).getColor());
        }

        this.drawEliminateTable(tempEventList, eliminateTableLayout, eliminateContext);

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

    private void drawEliminateTable(ArrayList<Event> eventList, TableLayout inputTableLayout, Context inputContext) {
        // Call some resources
        int[] resourceColours = getResources().getIntArray(R.array.items_colors);
        String[] resourceDays = getResources().getStringArray(R.array.days_array);
        int listSize = eventList.size();

        // Create first tableRow
        TableRow firstTableRow = new TableRow(inputContext);
        TableRow.LayoutParams firstLayoutParams = new TableRow.LayoutParams();
        firstTableRow.setLayoutParams(firstLayoutParams);

        String[] tempStrings = {"Ora", "Giorno", "Colore"};

        for (int i = 0; i < 3; i++) {
            Button tempButton = new Button(inputContext);
            tempButton.setTextColor(Color.WHITE);
            tempButton.setBackgroundColor(Color.DKGRAY);
            tempButton.setText(tempStrings[i]);
            firstTableRow.addView(tempButton);
        }
        CheckBox selectAllCheckBox = new CheckBox(inputContext);
        firstTableRow.addView(selectAllCheckBox);

        // slectAllCheckBox listener
        selectAllCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*
                eliminatedEventsList.clear();

                for (int i = 0; i < checkBoxList.size(); i++){
                    checkBoxList.get(i).setChecked(isChecked);
                    if (isChecked)
                        eliminatedEventsList.add(eventList.get(i));
                }
                */
                //eliminatedEventsList.clear();
                for (int i = 0; i < checkBoxList.size(); i++) {
                    checkBoxList.get(i).setChecked(isChecked);
                    booleanList.set(i, isChecked); //setto anche nella lista di boolean, in teoria dovrebbe bastare il settaggio nella checkBoxList
                    //Log.i("elements", "to cancel: " + Integer.toString(i) + " - " + booleanList.get(i));
                }
            }
        });

        // Add first row to the table layout
        inputTableLayout.addView(firstTableRow);

        // Create the dynamic table, each 'i' cycle is a row
        for (int i = 0; i < listSize; i++) {
            Event tempEvent = eventList.get(i);

            // Create a new table row
            TableRow tableRow = new TableRow(inputContext);

            // Set new table row layout parameters
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();//TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(layoutParams);

            // Add the hour TextView in the first column.
            Button hourView = new Button(inputContext);
            int thisHour = tempEvent.getWhen().get(Calendar.HOUR_OF_DAY);
            int thisMinute = tempEvent.getWhen().get(Calendar.MINUTE);
            String tempTime = String.format("%02d:%02d", thisHour, thisMinute);

            hourView.setText(tempTime);
            hourView.setTextColor(Color.WHITE);
            hourView.setBackgroundColor(Color.DKGRAY);

            tableRow.addView(hourView);

            // Add the day TextView in the second column.
            Button dayView = new Button(inputContext);
            dayView.setText(tempEvent.getDay().toString());
            dayView.setTextColor(Color.DKGRAY);
            dayView.setBackgroundColor(Color.WHITE);
            tableRow.addView(dayView);

            // Add the colour
            Button colorView = new Button(inputContext);
            colorView.setBackgroundColor(resourceColours[tempEvent.getColor().ordinal()]);
            tableRow.addView(colorView);

            // Add CheckBox
            CheckBox tempCheckBox = new CheckBox(inputContext);
            tempCheckBox.setId(tempCheckBox.generateViewId());
            checkBoxIdList.add(tempCheckBox.getId());
            checkBoxList.add(tempCheckBox);
            tempCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int thisId = buttonView.getId();

                    for (int i = 0; i < checkBoxIdList.size(); i++) {
                        if (thisId == checkBoxIdList.get(i)) {
                            booleanList.set(i, isChecked);
                            //Log.i("elements", "item: " + Integer.toString(i) + ", color " + tempEventList.get(i).getColor() +" set to " + booleanList.get(i));
                        }
                    }
                }
            });


            /*
            tempCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = 0;
                    int i = 0;
                    while (i < checkBoxIdList.size()) {
                        if (checkBoxIdList.get(i) == tempCheckBox.getId()){
                            position = i;
                            i = checkBoxIdList.size();
                        }
                        i++;
                    }

                    //if (isChecked)
                      //  eliminatedEventsList.add(eventList.get(position));

                    //else
                      //  eliminatedEventsList.remove(eventList.get(position));


                    //booleanList.set(position, isChecked);

                    //Log.i("drawtag", "elemento numero: " + Integer.toString(position));
                    //String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                    //Toast.makeText(inputContext, msg, Toast.LENGTH_SHORT).show();
                }
            });

            */
            tableRow.addView(tempCheckBox);

            // Add the ith row to the table layout
            inputTableLayout.addView(tableRow);
        }
    }

    public void eliminate() {
        /*
        for (int i = 0; i < eliminatedEventsList.size(); i++) {
            try {
                EventManager.GetInstance().DeleteEvent(eliminatedEventsList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
        //Log.i("elements", "tempEventList size: " + tempEventList.size());

        for (int i = booleanList.size() - 1; i >= 0; i--) {
            if (booleanList.get(i)) {
                //Log.i("elements", "cancelled: " + Integer.toString(i) + " - " + tempEventList.get(i).getColor());
                EventManager.GetInstance().DeleteEvent(tempEventList.get(i));
            }
        }

        startActivity(new Intent(EliminateEvent.this, CalendarActivity.class));
    }
}
