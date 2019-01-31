package dais.unive.it.robot.CalendarClass;

import java.util.Calendar;

public class Event {
    private WeekDay day;
    private PillColors color;
    private Calendar when;
    private boolean repeat;

    public enum OccurrencyType {weekly, daily, onetime}

    Event(WeekDay day, PillColors color, Calendar when, boolean repeat) {
        this.day = day;
        this.color = color;
        this.when = when;
        this.repeat = repeat;
    }

    public WeekDay getDay() {
        return this.day;
    }

    public PillColors getColor() {
        return this.color;
    }

    public Calendar getWhen() {
        return this.when;
    }

    public boolean getRepeat() {
        return this.repeat;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) return false;
        return ((Event) o).color == this.color &&
                ((Event) o).day == this.day &&
                ((Event) o).when == this.when &&
                ((Event) o).repeat == this.repeat;
    }
}