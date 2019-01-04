package dais.unive.it.robot.CalendarClass;


public enum PillColors {
    blue(0),
    red(1),
    yellow(2),
    green(3);

    private Integer hierarchy;

    private PillColors(final Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getHierarchy() {
        return hierarchy;
    }
}