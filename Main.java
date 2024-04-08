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

    enum Statuses {
        PLANNED, CONFIRMED, PURSUED, DONE
    }

    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerFunctionInput = new Scanner(System.in);
        boolean programExit = false;

        Calendar calendar = new Calendar();
        addExampleEvents(calendar);

        while(!programExit) {
            showProgramInfo();
            switch (scannerMenuChoice.nextLine().strip()) {
                case "1" -> handleAddNewMeeting(scannerFunctionInput, calendar);
                case "2" -> handleAddNewTask(scannerFunctionInput, calendar);
                case "3" -> handleRemoveMeeting(scannerFunctionInput, calendar);
                case "4" -> handleRemoveTask(scannerFunctionInput, calendar);
                case "5" -> handleShowEventsInDay(scannerFunctionInput, calendar, "meetings");
                case "6" -> handleShowEventsInDay(scannerFunctionInput, calendar, "tasks");
                case "7" -> handleShowMeetingsInDayWithPriority(scannerFunctionInput, calendar);
                case "8" -> handleShowTasksInDayWithStatus(scannerFunctionInput, calendar);
                case "9" -> handleShowMeetingsInDayWithPriorityAndNotEarlierThan(scannerFunctionInput,calendar);
                case "10" -> handleShowTasksInDayWithStatusAndNotLaterThan(scannerFunctionInput, calendar);
                case "0" -> programExit = true;
                default -> System.out.println("\nWrong option!");
            }
        }
    }

    static void showProgramInfo() {
        System.out.println("\nThis program offers these functions:");
        System.out.println("[1] Add a meeting for a given day");
        System.out.println("[2] Add a task for a given day");
        System.out.println("[3] Remove a meeting from a given day");
        System.out.println("[4] Remove a task from a given day");
        System.out.println("[5] Show meetings in a given day");
        System.out.println("[6] Show tasks in a given day");
        System.out.println("[7] Show meetings in a given day, with a specific priority");
        System.out.println("[8] Show tasks in a given day, with a specific status");
        System.out.println("[9] Show meetings in a given day, with a specific priority, "+
                           "starting not earlier than a given hour");
        System.out.println("[10] Show tasks in a given day, with a specific status, "+
                           "ending not later than a given hour");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,5,6,7,8,9,10 or 0]: ");
    }

    static void addExampleEvents(Calendar calendar) {
        calendar.addEvent("friday", new Meeting(LocalTime.parse("10:15"),
                LocalTime.parse("12:45"), "medium", "Event 1"));
        calendar.addEvent("friday", new Meeting(LocalTime.parse("17:00"),
                LocalTime.parse("18:30"), "low", "Event 2"));
        calendar.addEvent("friday", new Meeting(LocalTime.parse("20:30"),
                LocalTime.parse("22:30"), "low", "Event 3"));

        calendar.addEvent("friday", new Task(LocalTime.parse("11:30"),
                LocalTime.parse("14:30"), "done", "Task 1"));
        calendar.addEvent("friday", new Task(LocalTime.parse("17:15"),
                LocalTime.parse("18:45"), "planned", "Task 2"));
        calendar.addEvent("friday", new Task(LocalTime.parse("21:30"),
                LocalTime.parse("23:30"), "planned", "Task 3"));
    }

    static void handleAddNewMeeting(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);

        String msg = "Input a start time (e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeStart = inputGetTimeMsg(scanner, msg);

        msg = "Input an  end time (e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeEnd = inputGetTimeMsg(scanner, msg);

        String priority = inputGetPriority(scanner);
        String description = inputGetDescription(scanner);

        Meeting newMeeting = new Meeting(timeStart, timeEnd, priority, description);
        calendar.addEvent(day, newMeeting);
        System.out.println("\nNew meeting added.");
    }

    static void handleAddNewTask(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);

        String msg = "Input a start time (e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeStart = inputGetTimeMsg(scanner, msg);

        msg = "Input an  end time (e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime timeEnd = inputGetTimeMsg(scanner, msg);

        String status = inputGetStatus(scanner);
        String description = inputGetDescription(scanner);

        Task newTask = new Task(timeStart, timeEnd, status, description);
        calendar.addEvent(day, newTask);
        System.out.println("\nNew task added.");
    }

    static void handleRemoveMeeting(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        printMeetingsInDay(calendar, day);

        int nthToRemove = inputGetNthToRemove(scanner, calendar.getEvents(day, a -> a instanceof Meeting).size());
        if ( nthToRemove == 0 ) return; // user inputs 0 to cancel

        removeNthMeeting(calendar, day, nthToRemove);
        System.out.println("\nMeeting removed.");
    }

    static void handleRemoveTask(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        printTasksInDay(calendar, day);

        int nthToRemove = inputGetNthToRemove(scanner, calendar.getEvents(day, a -> a instanceof Meeting).size());
        if ( nthToRemove == 0 ) return; // user inputs 0 to cancel

        removeNthTask(calendar, day, nthToRemove);
        System.out.println("\nTask removed.");
    }

    static void handleShowEventsInDay(Scanner scanner, Calendar calendar, String type) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        switch (type.toLowerCase()) {
            case "meetings" -> printMeetingsInDay(calendar, day);
            case "tasks" -> printTasksInDay(calendar, day);
        }
    }

    static void handleShowMeetingsInDayWithPriority(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String priority = inputGetPriority(scanner);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> (a instanceof Meeting)
                && ((Meeting) a).getPriority().toUpperCase().equals(priority));

        System.out.println("\nMeetings for " + day + " with priority " + priority + ":");
        printEvents(events);
    }

    static void handleShowTasksInDayWithStatus(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String status = inputGetStatus(scanner);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> (a instanceof Task)
                && ((Task) a).getStatus().toUpperCase().equals(status));

        System.out.println("\nTasks for " + day + " with status " + status + ":");
        printEvents(events);
    }

    static void handleShowMeetingsInDayWithPriorityAndNotEarlierThan(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String priority = inputGetPriority(scanner);

        String msg = "Input a time to show events starting not earlier than "+
                     "(e.g. 09:45[:59]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTime = inputGetTimeMsg(scanner, msg);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> a instanceof Meeting
                && ((Meeting) a).getPriority().toUpperCase().equals(priority)
                && !a.getTimeStart().isBefore(inputTime));

        System.out.println("\nMeetings for " + day + " with priority " + priority +
                            ", starting not earlier than " + inputTime + " :");
        printEvents(events);
    }

    static void handleShowTasksInDayWithStatusAndNotLaterThan(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        if (noEventsExist(calendar, day)) {
            msgNoEventsInDay(day);
            return;
        }
        String status = inputGetStatus(scanner);

        String msg = "Input a time to show events ending to "+
                    "(e.g. 12:15[:01]) (after: "+Event.getTimeEarliest()+"): ";
        LocalTime inputTime = inputGetTimeMsg(scanner, msg);

        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> a instanceof Task
                && ((Task) a).getStatus().toUpperCase().equals(status)
                && !a.getTimeEnd().isAfter(inputTime));

        System.out.println("\nTasks for " + day + " with status " + status +
                            ", ending not later than " + inputTime + " :");
        printEvents(events);
    }

    static boolean noEventsExist(Calendar calendar, String day) {
        return calendar.getEventsAmount(day) == 0;
    }

    static void msgNoEventsInDay(String day) {
        System.out.println("\nNo " + day + " events available. Add some events first");
    }

    static void printMeetingsInDay(Calendar calendar, String day) {
        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> a instanceof Meeting);

        System.out.println("\nMeetings for " + day + ":");
        printEvents(events);
    }

    static void printTasksInDay(Calendar calendar, String day) {
        ArrayList<Event> events;
        events = calendar.getEvents(day, a -> a instanceof Task);

        System.out.println("\nTasks for " + day + ":");
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

    static String inputGetStatus(Scanner scanner) {
        String inputStatus;
        do {
            System.out.print("Valid statuses: ");
            for (Statuses status : Statuses.values()) {
                System.out.print(status.name() + " ");
            }
            System.out.print("\nInput a valid status: ");
            inputStatus = scanner.nextLine().strip();
        } while ( !validStatus(inputStatus) );

        return inputStatus.toUpperCase();
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

    static boolean validStatus(String statusToCheck) {
        boolean isValid = false;
        for (Statuses status : Statuses.values()) {
            if (status.name().equalsIgnoreCase(statusToCheck)) {
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

    static int inputGetNthToRemove(Scanner scanner, int maxIndex) {
        int inputIndex = 0;
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
        return inputIndex;
    }

    static void removeNthMeeting(Calendar calendar, String day, int NthToRemove) {
        int index = 0;
        int meetingCounter = 0;

        for (Event event : calendar.getEvents(day)) {
            if (event instanceof Meeting) {
                meetingCounter++;
                if (meetingCounter == NthToRemove) {
                    calendar.removeEvent(day, index);
                    break;
                }
            }
            index++;
        }
    }

    static void removeNthTask(Calendar calendar, String day, int NthToRemove) {
        int index = 0;
        int meetingCounter = 0;

        for (Event event : calendar.getEvents(day)) {
            if (event instanceof Task) {
                meetingCounter++;
                if (meetingCounter == NthToRemove) {
                    calendar.removeEvent(day, index);
                    break;
                }
            }
            index++;
        }
    }
}
