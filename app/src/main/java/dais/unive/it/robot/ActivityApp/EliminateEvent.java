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
import java.util.Calendar;

import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventData;
import dais.unive.it.robot.R;


public class EliminateEvent extends AppCompatActivity {
    private Button eliminateButton;
    // TODO Sebastian - sample data to eliminate
    EventData tempEventData = new EventData();
    ArrayList<Event> tempEventList = tempEventData.getEventList();

    /*
     eliminatedEventsList: elements checked by the user ready to eliminate
     checkBoxIdList: list of Ids used by listeners to find selected items
     checkBoxList: list of checkBoxes used to select all boxes on the first row Button listener
     */
    ArrayList<Event> eliminatedEventsList = new ArrayList<>();
    ArrayList<Integer> checkBoxIdList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminate_event);

        TableLayout eliminateTableLayout = findViewById(R.id.eliminate_event_table_layout);
        Context eliminateContext = getApplicationContext();

        eliminateButton = findViewById(R.id.eliminate_button);
        eliminateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View w){
                eliminate();
            }
        });
        this.drawEliminateTable(tempEventList, eliminateTableLayout, eliminateContext);
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

        for (int i = 0; i < 3; i++){
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
                eliminatedEventsList.clear();
                for (int i = 0; i < checkBoxList.size(); i++){
                    checkBoxList.get(i).setChecked(isChecked);
                    if (isChecked)
                        eliminatedEventsList.add(eventList.get(i));
                }
            }
        });

        // Add first row to the table layout
        inputTableLayout.addView(firstTableRow);

        // Create the dynamic table, each 'i' cycle is a row
        for (int i = 0; i < listSize; i++){
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
            //colorView.setBackgroundColor(getResources().);
            tableRow.addView(colorView);

            // Add CheckBox
            CheckBox tempCheckBox = new CheckBox(inputContext);
            tempCheckBox.setId(tempCheckBox.generateViewId());
            checkBoxIdList.add(tempCheckBox.getId());
            checkBoxList.add(tempCheckBox);
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
                    if (isChecked)
                        eliminatedEventsList.add(eventList.get(position));

                    else
                        eliminatedEventsList.remove(eventList.get(position));
                    //Log.i("drawtag", "elemento numero: " + Integer.toString(position));
                    //String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Check it Checkbox.";
                    //Toast.makeText(inputContext, msg, Toast.LENGTH_SHORT).show();
                }
            });
            tableRow.addView(tempCheckBox);

            // Add the ith row to the table layout
            inputTableLayout.addView(tableRow);
        }
    }

    public void eliminate(){
        //TODO Sebastian - da implementare

        // The cycle is used to see output in console, click on 6: Logcat and search by tag argument "eliminatedEventList"
        /*for (int i = 0; i < eliminatedEventsList.size(); i++) {
            String our = Integer.toString(eliminatedEventsList.get(i).getTime().get(Calendar.HOUR_OF_DAY)) +
                    ":" + Integer.toString(eliminatedEventsList.get(i).getTime().get(Calendar.MINUTE));
            String day =  getResources().getStringArray(R.array.days_array)[eliminatedEventsList.get(i).getDay() - 1];
            String color = Integer.toString(eliminatedEventsList.get(i).getColor());
            Log.i("eliminatedEventList", "eliminated hour " + our + ", day " + day
                    + ", color " + color);
        }*/
    }
}
