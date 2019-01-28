package dais.unive.it.robot.CalendarClass;


public enum PillColors {
    empty(0),
    red(1),
    blue(2),
    green(3),
    yellow(4);

    private Integer hierarchy;

    private PillColors(final Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getHierarchy() {
        return hierarchy;
    }
}