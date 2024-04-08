import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Calendar {
    private HashMap<String, ArrayList<Event>> calendar;

    public Calendar() {
        this.calendar = new HashMap<>();
    }

    public void addEvent(String day, Event newEvent) {
        day = day.toUpperCase();
        if ( !calendar.containsKey(day) ) {
            calendar.put(day, new ArrayList<>());
        }
        calendar.get(day).add(newEvent);
    }

    public void removeEvent(String day, int index) {
        calendar.get(day).remove(index);
    }

    public ArrayList<Event> getEvents(String day, Predicate<Event> check) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : calendar.get(day)) {
            if (check.test(event)) events.add(event);
        }
        return events;
    }

    public ArrayList<Event> getEvents(String day) {
        return calendar.get(day);
    }

    public int getEventsAmount(String day) {
        try {
            return calendar.get(day).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
