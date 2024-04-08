import java.util.ArrayList;
import java.util.HashMap;

public class Calendar {
    private HashMap<String, ArrayList<Event>> calendar;

    public Calendar() {
        this.calendar = new HashMap<>();
    }

    public void addEvent(String day, Event newEvent) {
        if ( !calendar.containsKey(day) ) {
            calendar.put(day, new ArrayList<>());
        }
        calendar.get(day).add(newEvent);
    }

    public void removeEvent(String day, int index) {
        calendar.get(day).remove(index);
    }

    public String getStringOfEventsInDay(String day) {
        StringBuilder eventsInDay = new StringBuilder();
        int i = 1;
        for (Event event : calendar.get(day)) {
            eventsInDay.append("[%d] %s".formatted(i, event.toString()));
            i++;
        }
        return eventsInDay.toString().strip();
    }

    public String getStringOfEventsInDayWithPriority(String day, String priority) {
        StringBuilder eventsInDayWithPriority = new StringBuilder();
        ArrayList<Event> events = getEventsInDayWithPriority(day, priority);
        int i = 1;
        for (Event event : events) {
            eventsInDayWithPriority.append("[%d] %s".formatted(i, event.toString()));
            i++;
        }
        return eventsInDayWithPriority.toString().strip();
    }

    public ArrayList<Event> getEventsInDayWithPriority(String day, String priority) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : calendar.get(day)) {
            if (event.getPriority().equals(priority)) {
                events.add(event);
            }
        }
        return events;
    }

    public int getEventsAmount(String day) {
        try {
            return calendar.get(day).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
