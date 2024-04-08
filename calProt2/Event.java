import java.time.LocalTime;

public class Event {
    private static final LocalTime timeEarliest = LocalTime.parse("04:00");
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String priority;
    private String description;

    Event(LocalTime timeStart, LocalTime timeEnd, String priority, String description) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priority = priority;
        this.description = description;
    }

    public String toString() {
        String string = "";
        String separation = ", ";
        string += "From: " + timeStart + separation;
        string += "To: " + timeEnd + separation;
        string += "Priority: " + priority + separation;
        string += "Desc: " + description;
        return string;
    }

    public static LocalTime getTimeEarliest() {
        return timeEarliest;
    }

    public String getPriority() {
        return this.priority;
    }

    public LocalTime getTimeStart() {
        return this.timeStart;
    }

    public LocalTime getTimeEnd() {
        return this.timeEnd;
    }
}
