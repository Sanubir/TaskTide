import java.time.LocalTime;

public final class Meeting extends Event {
    private String priority;

    Meeting(LocalTime timeStart, LocalTime timeEnd, String priority, String description) {
        super(timeStart, timeEnd, description);
        this.priority = priority;
    }

    @Override
    public String toString() {
        String string = "";
        String separation = ", ";
        string += "From: " + timeStart + separation;
        string += "To: " + timeEnd + separation;
        string += "Priority: " + priority + separation;
        string += "Desc: " + description;
        return string;
    }

    public String getPriority() {
        return this.priority;
    }
}
