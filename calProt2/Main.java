import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    enum Days {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    enum Priorities {
        LOW, MEDIUM, HIGH
    }

    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerFunctionInput = new Scanner(System.in);
        boolean programExit = false;

        Calendar calendar = new Calendar();

        calendar.addEvent("wednesday", new Event(LocalTime.parse("12:15"),
                LocalTime.parse("15:45"), "high", "Event"));
        calendar.addEvent("wednesday", new Event(LocalTime.parse("19:00"),
                LocalTime.parse("20:00"), "medium", "Event 0"));
        calendar.addEvent("friday", new Event(LocalTime.parse("10:15"),
                LocalTime.parse("12:45"), "medium", "Event 1"));
        calendar.addEvent("friday", new Event(LocalTime.parse("17:00"),
                LocalTime.parse("18:30"), "low", "Event 2"));
        calendar.addEvent("friday", new Event(LocalTime.parse("20:30"),
                LocalTime.parse("22:30"), "low", "Event 3"));

        while(!programExit) {
            showProgramInfo();
            switch (scannerMenuChoice.nextLine().strip()) {
                case "1" -> handleAddNewEvent(scannerFunctionInput, calendar);
                case "2" -> handleRemoveEvent(scannerFunctionInput, calendar);
                case "3" -> handleShowEventsInDay(scannerFunctionInput, calendar);
                case "4" -> handleShowEventsInDayWithPriority(scannerFunctionInput, calendar);
                case "5" -> handleShowEventsInDayNotEarlierThan(scannerFunctionInput,calendar);
                case "6" -> handleShowEventsInDayBetweenHours(scannerFunctionInput, calendar);
                case "7" -> handleShowEventsInDayWithPriorityNotLaterThan(scannerFunctionInput, calendar);
                case "0" -> programExit = true;
                default -> System.out.println("\nWrong option!");
            }
        }
    }

    static void showProgramInfo() {
        System.out.println("\nThis program offers these functions:");
        System.out.println("[1] Add an event for a given day");
        System.out.println("[2] Remove an event from a given day");
        System.out.println("[3] Show events in a given day");
        System.out.println("[4] Show events in a given day, with a specific priority");
        System.out.println("[5] Show events in a given day, starting not earlier than a given hour");
        System.out.println("[6] Show events in a given day, happening between given hours");
        System.out.println("[7] Show events in a given day, with a specific priority, "+
                           "ending not later than a given hour");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,0]: ");
    }

    static void handleAddNewEvent(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);

        String msg = "Input a start time (e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeStart = inputGetTimeMsg(scanner, msg);

        msg = "Input an  end time (e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeEnd = inputGetTimeMsg(scanner, msg);

        String priority = inputGetPriority(scanner);
        String description = inputGetDescription(scanner);

        Event newEvent = new Event(timeStart, timeEnd, priority, description);
        calendar.addEvent(day, newEvent);
        System.out.println("\nNew event added.");
    }

    static void handleRemoveEvent(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        printEventsInDay(calendar, day);

        int indexToRemove = inputGetIndexToRemove(scanner, calendar.getEventsAmount(day));
        if ( indexToRemove == -1 ) return; // user inputs 0 to cancel

        calendar.removeEvent(day, indexToRemove);
        System.out.println("\nEvent removed.");
    }

    static void handleShowEventsInDay(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        printEventsInDay(calendar, day);
    }

    static void handleShowEventsInDayWithPriority(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String priority = inputGetPriority(scanner);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> a.getPriority().toUpperCase().equals(priority));

        System.out.println("\nEvents for " + day + " with priority " + priority + ":");
        printEvents(events);
    }

    static void handleShowEventsInDayNotEarlierThan(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String msg = "Input a time to show events starting not earlier than "+
                     "(e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTime = inputGetTimeMsg(scanner, msg);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> !a.getTimeStart().isBefore(inputTime));

        System.out.println("\nEvents for " + day + " starting not earlier than " + inputTime + " :");
        printEvents(events);
    }

    static void handleShowEventsInDayBetweenHours(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String msg = "Input a time to show events starting from "+
                     "(e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTimeStart = inputGetTimeMsg(scanner, msg);

        msg = "Input a time to show events ending to "+
                "(e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTimeEnd = inputGetTimeMsg(scanner, msg);

        ArrayList<Event> events;
        events = calendar.getEvents(day,
                a -> !a.getTimeStart().isBefore(inputTimeStart) && !a.getTimeEnd().isAfter(inputTimeEnd));

        System.out.println("\nEvents for " + day + " between " + inputTimeStart + " and " + inputTimeEnd + " :");
        printEvents(events);
    }

    static void handleShowEventsInDayWithPriorityNotLaterThan(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String priority = inputGetPriority(scanner);

        String msg = "Input a time to show events ending to "+
                    "(e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTime = inputGetTimeMsg(scanner, msg);

        ArrayList<Event> events;
        events = calendar.getEvents(day,
                a -> a.getPriority().toUpperCase().equals(priority) && !a.getTimeEnd().isAfter(inputTime));

        System.out.println("\nEvents for " + day + " with priority " + priority +
                            ", ending not later than " + inputTime + " :");
        printEvents(events);
    }

    static boolean noEventsExist(Calendar calendar, String day) {
        return calendar.getEventsAmount(day) == 0;
    }

    static void msgNoEventsInDay(String day) {
        System.out.println("\nNo " + day + " events available. Add some events first");
    }

    static void printEventsInDay(Calendar calendar, String day) {
        ArrayList<Event> events;
        events = calendar.getEvents(day);

        System.out.println("\nEvents for " + day + ":");
        printEvents(events);
    }

    static void printEvents(ArrayList<Event> events) {
        int i = 1;
        for (Event event : events) {
            System.out.printf("[%d] %s\n", i, event);
            i++;
        }
    }

    static String inputGetDay(Scanner scanner) {
        String inputDay;
        System.out.println();
        do {
            System.out.print("Valid days: ");
            for (Days day : Days.values()) {
                System.out.print(day.name() + " ");
            }
            System.out.print("\nInput a valid day: ");
            inputDay = scanner.nextLine().strip().toUpperCase();
        } while ( !validDay(inputDay) );

        return inputDay;
    }

    static boolean validDay(String dayToCheck) {
        boolean isValid = false;
        for (Days day : Days.values()) {
            if (day.name().equalsIgnoreCase(dayToCheck)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    static String inputGetPriority(Scanner scanner) {
        String inputPriority;
        do {
            System.out.print("Valid priorities: ");
            for (Priorities priority : Priorities.values()) {
                System.out.print(priority.name() + " ");
            }
            System.out.print("\nInput a valid priority: ");
            inputPriority = scanner.nextLine().strip();
        } while ( !validPriority(inputPriority) );

        return inputPriority.toUpperCase();
    }

    static boolean validPriority(String priorityToCheck) {
        boolean isValid = false;
        for (Priorities priority : Priorities.values()) {
            if (priority.name().equalsIgnoreCase(priorityToCheck)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    static String inputGetDescription(Scanner scanner) {
        String inputDesc;
        do {
            System.out.print("Input a description: ");
            inputDesc = scanner.nextLine().strip();
        } while (inputDesc.isEmpty());
        return inputDesc;
    }

    static LocalTime inputGetTimeMsg(Scanner scanner, String msg) {
        LocalTime inputTime;
        do {
            System.out.print(msg);
            inputTime = inputGetTime(scanner);
        } while (inputTime == null || inputTime.isBefore(Event.getTimeEarliest()));

        return inputTime;
    }

    static LocalTime inputGetTime(Scanner scanner) {
        LocalTime inputTime = null;
        try {
            inputTime = LocalTime.parse(scanner.nextLine().strip());
        } catch (Exception e) {
            System.out.println("Wrong input!");
        }
        return inputTime;
    }

    static int inputGetIndexToRemove(Scanner scanner, int maxIndex) {
        int inputIndex = -1;
        System.out.println();
        do {
            System.out.print("Choose an event to remove [index from 1 to "
                            +maxIndex+"] (or 0 to cancel): ");
            try {
                inputIndex = Integer.parseInt(scanner.nextLine().strip());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while ((inputIndex < 0) || (inputIndex > maxIndex));
        return inputIndex - 1;
    }
}
