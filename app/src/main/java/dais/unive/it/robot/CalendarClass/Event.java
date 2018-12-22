package dais.unive.it.robot.CalendarClass;

import java.sql.Time;
import java.util.Calendar;

public class Event {
    public int day;
    public Calendar dateTime;
    public int color;
    public int occourrency;


    public Event(int day, Calendar dateTime, int color, int occourrency){
        this.day = day;
        this.dateTime =dateTime;
        this.color = color;
        this.occourrency = occourrency;
    }

    public int getDay(){
        return this.day;
    }

    public Calendar getTime(){
        return this.dateTime;
    }

    public int getColor(){
        return this.color;
    }

    public int getOccourrency(){
        return this.occourrency;
    }

    public void setDay(int day){
        this.day = day;
    }


    public void setColor(int color){
        this.color = color;
    }

    public void setOccourrency(int occourrency){
        this.occourrency = occourrency;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Event)) return false;
        if(((Event)o).dateTime == this.dateTime && ((Event)o).color == this.color && ((Event)o).day == this.day) return true;
        return false;
    }
}
