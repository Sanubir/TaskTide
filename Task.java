import java.time.LocalTime;

public final class Task extends Event {
    private String status;

    Task(LocalTime timeStart, LocalTime timeEnd, String status, String description) {
        super(timeStart, timeEnd, description);
        this.status = status;
    }

    @Override
    public String toString() {
        String string = "";
        String separation = ", ";
        string += "From: " + timeStart + separation;
        string += "To: " + timeEnd + separation;
        string += "Status: " + status + separation;
        string += "Desc: " + description;
        return string;
    }

    public String getStatus() {
        return this.status;
    }
}
