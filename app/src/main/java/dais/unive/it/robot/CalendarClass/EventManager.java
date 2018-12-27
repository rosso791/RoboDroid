package dais.unive.it.robot.CalendarClass;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static EventManager instance;

    private List<Event> events;

    protected EventManager() {
        events = new ArrayList<Event>();
        // ToDo...
    }

    public static EventManager getInstance() {
        if(instance == null) {
            instance = new EventManager();
            // ToDo deserialize object
        }
        return instance;
    }

    private static List<Event> deserialize() {
        List<Event> events = new ArrayList<Event>();
        // deserialize
        return events;
    }
    private static void serialize() {
        // serialize events
    }

    public void addEvent(Event e) {
        events.add(e);
        serialize();
    }

    public void deleteEvent(Event e) {
        events.remove(e);
        serialize();
    }
}
