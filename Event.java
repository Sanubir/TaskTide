import java.time.LocalTime;

sealed abstract class Event permits Task, Meeting {
    private static final LocalTime TIME_EARLIEST = LocalTime.parse("04:00");
    protected LocalTime timeStart;
    protected LocalTime timeEnd;
    protected String description;

    Event(LocalTime timeStart, LocalTime timeEnd, String description) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
    }

    @Override
    public abstract String toString();

    public static LocalTime getTimeEarliest() {
        return TIME_EARLIEST;
    }


    public LocalTime getTimeStart() {
        return this.timeStart;
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }
}
