package dais.unive.it.robot.CalendarClass;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EventManager extends AppCompatActivity {

    private static EventManager instance;

    Timer timer = new Timer();

    private List<Event> events;

    private EventManager() {
        events = EventManager.deserialize();
//        timer.schedule( new TimerTask() {
//            public void run() {
//
//                // ToDo aggiungere il metodo per rilasciare le pillole dentro il foreach
////                events
////                        .stream()
////                        .filter(e -> e.getWhen().get(Calendar.HOUR) == Calendar.getInstance().get(Calendar.HOUR)
////                            && e.getWhen().get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)
////                            && e.getDay().ordinal() == Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
////                        .forEach(e -> e.);
//
//            }
//        }, 0, 10*1000);
    }

    public static EventManager GetInstance() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private static List<Event> deserialize() {
        List<Event> events = new ArrayList<>();
        Gson unserializer = new Gson();
        try {
            File file = new File(Environment.getDataDirectory() + "/calendar.json");
            if(file.exists()) {
                FileReader r = new FileReader(Environment.getDataDirectory() + "/calendar.json");
                instance.events = unserializer.fromJson(r, List.class);
            }
        } catch (IOException e) {
            Toast.makeText(instance.getApplicationContext(), "Error reading a backup of the calendar", Toast.LENGTH_LONG).show();
        }
        return events;
    }
    private static void serialize() {
        Gson serializer = new Gson();
        try {
            File file = new File(Environment.getDataDirectory() + "/calendar.json");
            FileWriter w = new FileWriter(file);
            serializer.toJson(instance.events, w);
        } catch (IOException e) {
            Toast.makeText(instance.getApplicationContext(), "Error writing a backup of the calendar", Toast.LENGTH_LONG).show();
        }
    }

    public void AddEvent(PillColors color, Event.OccurrencyType occurrencyType, Calendar when, WeekDay day) throws Exception {
        switch(occurrencyType) {
            case daily:
                for (WeekDay wday: WeekDay.values()) {
                    events.add(new Event(wday, color, when, true));
                }
                break;
            case weekly:
                if(day == null) throw new Exception("The day isn't specified for a weekly event");
                events.add(new Event(day, color, when, true));
                break;
            case onetime:
                if(day == null) throw new Exception("The day isn't specified for a onetime event");
                events.add(new Event(day, color, when, false));
                break;
        }
        serialize();
    }

    public void DeleteEvent(Event e) {
        events.remove(e);
        serialize();
    }
}