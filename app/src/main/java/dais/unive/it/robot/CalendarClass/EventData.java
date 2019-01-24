package dais.unive.it.robot.CalendarClass;

import java.util.ArrayList;
import java.util.Calendar;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.PillColors;
import dais.unive.it.robot.CalendarClass.WeekDay;

public class EventData {
    ArrayList<Event> eventList = new ArrayList<>();

    public EventData(){
        eventList = (ArrayList)EventManager.GetInstance().getAllEvents();
    }

    public ArrayList<Event> getEventList(){
        return eventList;
    }

    public int getNumberOfEvents(){
        return eventList.size();
    }

    private WeekDay getDay(int inputInt) {
        switch (inputInt) {
            case 0:
                return WeekDay.Mon;
            case 1:
                return WeekDay.Tue;
            case 2:
                return WeekDay.Wed;
            case 3:
                return WeekDay.Thu;
            case 4:
                return WeekDay.Fri;
            case 5:
                return WeekDay.Sat;
            case 6:
                return WeekDay.Sun;
        }
        return WeekDay.Sun;
    }

    private PillColors getColor(int inputInt){
        switch (inputInt){
            case 0: return PillColors.blue;
            case 1: return PillColors.red;
            case 2: return PillColors.yellow;
            case 3: return PillColors.green;
        }
        return PillColors.green;
    }
}
