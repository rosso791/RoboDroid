package dais.unive.it.robot.ActivityApp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.EventManager;
import dais.unive.it.robot.CalendarClass.NotificationHelper;
import dais.unive.it.robot.CalendarClass.PillColors;
import dais.unive.it.robot.CalendarClass.WeekDay;
import dais.unive.it.robot.R;

public class AddEvent extends AppCompatActivity {
    private int occurrency = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        DataExchange.GetInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //Cancellato perché gestisco con Landscape
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Occurrency Spinner
        Spinner occurrencySpinner = findViewById(R.id.occurrencySpinner);
        ArrayAdapter<CharSequence> occurrencyAdapter = ArrayAdapter.createFromResource(this,
                //Spinner layout can be modified using the 3rd parameter
                R.array.occurrency_array, android.R.layout.simple_spinner_item);
        //Dropdown item layout can be also modified here
        occurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occurrencySpinner.setAdapter(occurrencyAdapter);

        //Day Spinner, 2 adapters: one for all days, one void to use when "Settimanale" is choosed
        Spinner daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> voidDayAdapter = ArrayAdapter.createFromResource(this,
                R.array.void_days_array, android.R.layout.simple_spinner_item);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        //Occurrency Spinner Listener: needed to manage "Settimanale" occurrency, if Settimanale
        //is choosed then no day can be selected from the Day Spinner
        occurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                switch (selectedItemText) {
                    case "Singola":
                        occurrency = 0;
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case "Giornaliera":
                        occurrency = 1;
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case "Settimanale":
                        occurrency = 2;
                        daySpinner.setAdapter(voidDayAdapter);
                        break;
                }
                /*
                // Notify the selected item texe ToDo eliminare toast
                Toast.makeText
                        (getApplicationContext(), "Selected : " + selectedItemText,
                                Toast.LENGTH_SHORT).show();
                Toast.makeText
                        (getApplicationContext(), "number : " + Integer.toString(occurrency),
                                Toast.LENGTH_SHORT).show();
                 */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                daySpinner.setAdapter(dayAdapter);
            }
        });


        // 2018-12-29 Pagnin
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void save(View v) {
                PillColors color;
                Calendar c = Calendar.getInstance();
                c.set(0, 0, 0, ((TimePicker) findViewById(R.id.timePicker)).getHour(), ((TimePicker) findViewById(R.id.timePicker)).getMinute());
                WeekDay wd;
                switch (daySpinner.getSelectedItem().toString()) {
                    case "Lunedì":
                        wd = WeekDay.Mon;
                        break;
                    case "Martedì":
                        wd = WeekDay.Tue;
                        break;
                    case "Mercoledì":
                        wd = WeekDay.Wed;
                        break;
                    case "Giovedì":
                        wd = WeekDay.Thu;
                        break;
                    case "Venerdì":
                        wd = WeekDay.Fri;
                        break;
                    case "Sabato":
                        wd = WeekDay.Sat;
                        break;
                    case "Domenica":
                        wd = WeekDay.Sun;
                        break;
                    default:
                        wd = null;
                }
                switch (((Spinner) findViewById(R.id.colorSpinner)).getSelectedItemPosition()) {
                    case 2:
                        color = PillColors.blue;
                        break;
                    case 1:
                        color = PillColors.red;
                        break;
                    case 4:
                        color = PillColors.yellow;
                        break;
                    case 3:
                        color = PillColors.green;
                        break;
                    default:
                        color = PillColors.red;
                }
                try {
                    switch (occurrencySpinner.getSelectedItemPosition()) {
                        case 0:
                            EventManager.GetInstance().AddEvent(color, Event.OccurrencyType.onetime, c, wd);
                            break;
                        case 1:
                            EventManager.GetInstance().AddEvent(color, Event.OccurrencyType.weekly, c, wd);
                            break;
                        case 2:
                            EventManager.GetInstance().AddEvent(color, Event.OccurrencyType.daily, c, wd);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                save(v);
                Intent i = new Intent(AddEvent.this, CalendarActivity.class);
                startActivity(i);
            }
        });


        //Time Picker
        TimePicker timePicker = findViewById(R.id.timePicker);

        //Color Spinner
        class SpinnerAdapter extends BaseAdapter {
            private ArrayList<Integer> colors;
            private Context context;

            private SpinnerAdapter(Context context) {
                this.context = context;
                colors = new ArrayList<>();
                int retrieve[] = context.getResources().getIntArray(R.array.items_colors);
                for (int i = 1; i < retrieve.length; i++)
                    colors.add(retrieve[i]);

                //old cycle
                /*
                for (int re : retrieve)
                    colors.add(re);
                */
            }

            @Override
            public int getCount() {
                return colors.size();
            }

            @Override
            public Object getItem(int arg0) {
                return colors.get(arg0);
            }

            @Override
            public long getItemId(int arg0) {
                return arg0;
            }

            @Override
            public View getView(int pos, View view, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
                TextView txv = view.findViewById(android.R.id.text1);
                txv.setBackgroundColor(colors.get(pos));
                txv.setTextSize(24f);
                //txv.setText("Text  "+pos);
                return view;
            }
        }

        Spinner colorSpinner = findViewById(R.id.colorSpinner);
        colorSpinner.setAdapter(new SpinnerAdapter(this));



    }
}