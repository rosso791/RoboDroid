package dais.unive.it.robot.CalendarClass;

import java.util.ArrayList;
import java.util.Calendar;
import dais.unive.it.robot.CalendarClass.Event;
import dais.unive.it.robot.CalendarClass.PillColors;
import dais.unive.it.robot.CalendarClass.WeekDay;

public class EventData {
    int ints[][] = {{0,2,0,3},{1,2,0,3},{2,2,0,3},{3,2,0,3},{4,2,0,3},{5,2,0,3},{6,2,0,3},
                    {1,3,1,1},
                    {2,4,0,1},{4,4,0,1},
                    {4,5,1,1},
                    {5,18,3,1},
                    {5,19,2,1}

                    };
    int rowNumber = 0;
    ArrayList<Event> eventList = new ArrayList<>();

    public EventData(){
        rowNumber = ints.length;

        /*for (int i = 0; i < ints.length; i++){
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(Calendar.HOUR_OF_DAY, ints[i][1]);
            tempCalendar.set(Calendar.MINUTE, 0);
            Event tempEvent = new Event(ints[i][0], tempCalendar, ints[i][2], ints[i][3]);
            eventList.add(tempEvent);
        }*/
        //ints = EventManager.GetInstance().getAllEventsArray();
        for (int i = 0; i < rowNumber; i++){
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.set(Calendar.HOUR_OF_DAY, ints[i][1]);
            tempCalendar.set(Calendar.MINUTE, 0);
            Event thisEvent = new Event(getDay(ints[i][0]), getColor(ints[i][2]), tempCalendar, false);
            eventList.add(thisEvent);
        }

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
