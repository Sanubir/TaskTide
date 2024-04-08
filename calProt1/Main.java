import java.time.LocalTime;
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

        Event.setTimeEarliest(LocalTime.parse("04:00"));
        Calendar calendar = new Calendar();

        while(!programExit) {
            showProgramInfo();
            switch (scannerMenuChoice.nextLine().strip()) {
                case "1" -> handleAddNewEvent(scannerFunctionInput, calendar);
                case "2" -> handleRemoveEvent(scannerFunctionInput, calendar);
                case "3" -> handleShowEventsInDay(scannerFunctionInput, calendar);
                case "4" -> handleShowEventsInDayWithPriority(scannerFunctionInput, calendar);
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
        System.out.println("[4] Show events in a given day with a specific priority");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,0]: ");
    }

    static void handleAddNewEvent(Scanner scanner, Calendar calendar) {
        String day = inputGetDay(scanner);
        LocalTime timeStart = inputGetTimeStart(scanner);
        LocalTime timeEnd = inputGetTimeEnd(scanner);
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

        System.out.println("\nEvents for " + day + " with priority " + priority + ":");
        System.out.println(calendar.getStringOfEventsInDayWithPriority(day, priority));
    }

    static boolean noEventsExist(Calendar calendar, String day) {
        return calendar.getEventsAmount(day) == 0;
    }

    static void msgNoEventsInDay(String day) {
        System.out.println("\nNo " + day + " events available. Add some events first");
    }

    static void printEventsInDay(Calendar calendar, String day) {
        System.out.println("\nEvents for " + day + ":");
        System.out.println(calendar.getStringOfEventsInDay(day));
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

    static LocalTime inputGetTimeStart(Scanner scanner) {
        LocalTime inputTime;
        LocalTime timeEarliest = Event.getTimeEarliest();
        do {
            System.out.print("Input a start time (e.g. 09:45[:59]) (after: "+timeEarliest+"): ");
            inputTime = inputGetTime(scanner);
        } while (inputTime == null || inputTime.isBefore(timeEarliest));

        return inputTime;
    }

    static LocalTime inputGetTimeEnd(Scanner scanner) {
        LocalTime inputTime;
        LocalTime timeEarliest = Event.getTimeEarliest();
        do {
            System.out.print("Input an  end time (e.g. 12:15[:01]) (after: "+timeEarliest+"): ");
            inputTime = inputGetTime(scanner);
        } while (inputTime == null || inputTime.isBefore(timeEarliest));

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
