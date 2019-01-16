package dais.unive.it.robot.CalendarClass;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dais.unive.it.robot.Automation.DataExchange;

public class EventManager extends AppCompatActivity {

    private static EventManager instance;

    private static final String FILENAME = "/calendar.json";

    Timer timer = new Timer();

    private List<Event> events;

    private EventManager() {
        events = EventManager.deserialize(Environment.getExternalStorageDirectory() + FILENAME);
        events
                .stream()
                .filter(e -> e.getWhen().get(Calendar.HOUR) == Calendar.getInstance().get(Calendar.HOUR)
                        && e.getWhen().get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)
                        && e.getDay().ordinal() == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) % 7)
                .forEach(e -> {
                    DataExchange.AddColorToDischargeQueue(e.getColor().ordinal());
                    if(!e.getRepeat()) events.remove(e);
                });
/*        timer.schedule( new TimerTask() {
            public void run() {
                events
                        .stream()
                        .filter(e -> e.getWhen().get(Calendar.HOUR) == Calendar.getInstance().get(Calendar.HOUR)
                                && e.getWhen().get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)
                                && e.getDay().ordinal() == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) % 7)
                        .forEach(e -> {
                            DataExchange.SetColorDischargeRequest(e.getColor().ordinal());
                            if(!e.getRepeat()) events.remove(e);
                        });
            }
        }, 0, 10*1000);*/
    }

    public static EventManager GetInstance() {
        if(instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    private static List<Event> deserialize(String path) {
        List<Event> events = new ArrayList<>();
        Gson unserializer = new Gson();
        try {
            File file = new File(path);
            if(file.exists()) {
                FileReader r = new FileReader(path);
                java.lang.reflect.Type t = new TypeToken<List<Event>>(){}.getType();
                events = (ArrayList<Event>)unserializer.fromJson(r, t);
            }
        } catch (IOException e) {
            Toast.makeText(instance.getApplicationContext(), "Error reading a backup of the calendar", Toast.LENGTH_LONG).show();
        }
        return events;
    }

    private static void serialize(String path) {
        Gson serializer = new Gson();
        try {
            File file = new File(path);
            FileWriter w = new FileWriter(file);
            serializer.toJson(instance.events, w);
            w.flush();
            w.close();
        } catch (IOException e) {
            Toast.makeText(instance.getApplicationContext(), "Error writing a backup of the calendar", Toast.LENGTH_LONG).show();
        }
    }

    public void AddEvent(PillColors color, Event.OccurrencyType occurrencyType, Calendar when, WeekDay day) throws Exception {
        switch(occurrencyType) {
            case daily:
                for (WeekDay wday: WeekDay.values()) {
                    this.events.add(new Event(wday, color, when, true));
                }
                break;
            case weekly:
                if(day == null) throw new Exception("The day isn't specified for a weekly event");
                this.events.add(new Event(day, color, when, true));
                break;
            case onetime:
                if(day == null) throw new Exception("The day isn't specified for a onetime event");
                this.events.add(new Event(day, color, when, false));
                break;
        }
        serialize(Environment.getExternalStorageDirectory() + FILENAME);
    }

    public void DeleteEvent(Event e) {
        this.events.remove(e);
        serialize(Environment.getExternalStorageDirectory() + FILENAME);
    }

/*    public Map<Integer, Map<WeekDay, List<Event>>> getAllEvents() {
        Map<Integer, Map<WeekDay, List<Event>>> eventsList = new HashMap<>();
        events
                .forEach(e -> {
                    int when = e.getWhen().get(Calendar.HOUR) * 60 + e.getWhen().get(Calendar.MINUTE);
                    if(!eventsList.containsKey(when)) eventsList.put(when, new HashMap<>());
                    if(!eventsList.get(when).containsKey(e.getDay())) eventsList.get(when).put(e.getDay(), new ArrayList<>());
                    eventsList.get(when).get(e.getDay()).add(e);
                });
        return eventsList;
    }

    public int[][] getAllEventsArray() {
        // ordered by hour, day, color
        // exposed as [day0-6, hour, color, 0]
        int[][] result = new int[events.size()][4];
        events.sort(Comparator.comparingInt(p -> p.getColor().ordinal()));
        events.sort(Comparator.comparingInt(p -> p.getDay().ordinal()));
        events.sort(Comparator.comparingInt(p -> p.getWhen().get(Calendar.MINUTE)));
        events.sort(Comparator.comparingInt(p -> p.getWhen().get(Calendar.HOUR_OF_DAY)));
        int i = 0;
        events.forEach(e -> {
            result[i][0] = e.getDay().ordinal();
            result[i][1] = e.getWhen().get(Calendar.HOUR_OF_DAY) * 60 + e.getWhen().get(Calendar.MINUTE);
            result[i][2] = e.getColor().ordinal();
            result[i][3] = 0;
        });
        return result;
    }*/

    public List<Event> getAllEvents() {
        events.sort(Comparator.comparingInt(p -> p.getColor().ordinal()));
        events.sort(Comparator.comparingInt(p -> p.getDay().ordinal()));
        events.sort(Comparator.comparingInt(p -> p.getWhen().get(Calendar.MINUTE)));
        events.sort(Comparator.comparingInt(p -> p.getWhen().get(Calendar.HOUR_OF_DAY)));
        return events;
    }
}