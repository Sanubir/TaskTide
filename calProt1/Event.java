import java.time.LocalTime;

public class Event {
    private static LocalTime timeEarliest;
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
        string += "Desc: " + description + "\n";
        return string;
    }

    public static void setTimeEarliest(LocalTime time) {
        timeEarliest = time;
    }

    public static LocalTime getTimeEarliest() {
        return timeEarliest;
    }

    public String getPriority() {
        return this.priority;
    }
}
