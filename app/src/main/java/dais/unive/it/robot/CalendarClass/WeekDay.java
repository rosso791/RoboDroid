package dais.unive.it.robot.CalendarClass;

public enum WeekDay {
    Mon(0),
    Tue(1),
    Wed(2),
    Thu(3),
    Fri(4),
    Sat(5),
    Sun(6);


    private Integer hierarchy;

    private WeekDay(final Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getHierarchy() {
        return hierarchy;
    }
}