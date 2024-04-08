import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerInput = new Scanner(System.in);
        boolean programExit = false;

        OffersList offersList = new OffersList();
        addExampleOffers(offersList);

        while(!programExit) {
            showProgramInfo();
            switch (scannerMenuChoice.nextLine().strip()) {
                case "1" -> handleAddOfferHouse(scannerInput, offersList);
                case "2" -> handleAddOfferAparment(scannerInput, offersList);
                case "3" -> handleShowValidOffers(offersList, "all");
                case "4" -> handleShowValidOffers(offersList, "houses");
                case "5" -> handleShowValidOffers(offersList, "apartments");
                case "6" -> handleShowValidHouseOffersWithConditions(scannerInput, offersList);
                case "7" -> handleShowValidApartmentOffersWithConditions(scannerInput, offersList);
                case "0" -> programExit = true;
                default -> System.out.println("\nWrong option!");
            }
        }
    }

    static void showProgramInfo() {
        System.out.println("\nThis program offers these functions:");
        System.out.println("[1] Add a house offer");
        System.out.println("[2] Add an apartment offer");
        System.out.println("[3] Show all valid offers");
        System.out.println("[4] Show all valid house offers");
        System.out.println("[5] Show all valid apartment offers");
        System.out.println("[6] Show all valid house offers, in specified town and area not smaller than given");
        System.out.println("[7] Show all valid apartment offers, in specified town, " +
                            "not more expensive than price given and from a specified floor number + up");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,5,6,7,8,9,10 or 0]: ");
    }

    static void addExampleOffers(OffersList offersList) {
        offersList.addOffer(new House("street old", 9, "Cracow", "80-120",
                160, 1_300_000.00, LocalDate.parse("2023-12-12"), 220));
        offersList.addOffer(new House("street 1", 1, "Warsaw", "81-231",
                200, 1_900_000.00, LocalDate.parse("2024-01-02"), 600));
        offersList.addOffer(new House("street 2", 2, "Gdansk", "82-342",
                180, 1_600_000.00, LocalDate.parse("2024-01-15"), 400));
        offersList.addOffer(new House("street 3", 3, "Cracow", "83-453",
                150, 1_200_000.00, LocalDate.parse("2024-02-21"), 200));
        offersList.addOffer(new House("street 8", 8, "Cracow", "84-562",
                120, 1_000_000.00, LocalDate.parse("2024-03-27"), 180));

        offersList.addOffer(new Apartment("street old", 9, "Cracow", "80-123",
                110, 1_100_000.00, LocalDate.parse("2023-12-11"), 140, 8));
        offersList.addOffer(new Apartment("street 4", 4, "Warsaw", "81-234",
                140, 1_500_000.00, LocalDate.parse("2024-01-03"), 140, 8));
        offersList.addOffer(new Apartment("street 5", 5, "Gdansk", "82-345",
                120, 1_200_000.00, LocalDate.parse("2024-01-16"), 21, 4));
        offersList.addOffer(new Apartment("street 6", 6, "Cracow", "83-456",
                100, 1_000_000.00, LocalDate.parse("2024-02-22"), 14, 2));
        offersList.addOffer(new Apartment("street 7", 7, "Cracow", "84-567",
                130, 1_300_000.00, LocalDate.parse("2024-04-02"), 23, 3));
    }

    static void handleAddOfferHouse(Scanner scanner, OffersList offersList) {
        String town = inputGetString(scanner, "Input the town name: ");
        String streetName = inputGetString(scanner, "Input a street name: ");
        int streetNumber = inputGetPositiveInt(scanner, "Input a street number: ");
        String postcode = inputGetString(scanner, "Input the postcode: ");
        double areaM2 = inputGetPositiveDouble(scanner, "Input an area of the property: ");
        double price = inputGetPositiveDouble(scanner, "Input the price for the offer: ");
        double lotSizeM2 = inputGetPositiveDouble(scanner, "Input the lot size: ");

        String msg = "Input the date the offer should be valid to (e.g. 2024-01-01) : ";
        LocalDate validTill = inputGetDate(scanner, msg);

        House newHouse = new House(streetName, streetNumber, town, postcode, areaM2, price, validTill, lotSizeM2);
        offersList.addOffer(newHouse);
        System.out.println("\nNew house offer added.");
    }

    static void handleAddOfferAparment(Scanner scanner, OffersList offersList) {
        String town = inputGetString(scanner, "Input the town name: ");
        String streetName = inputGetString(scanner, "Input a street name: ");
        int streetNumber = inputGetPositiveInt(scanner, "Input a street number: ");
        String postcode = inputGetString(scanner, "Input the postcode: ");
        double areaM2 = inputGetPositiveDouble(scanner, "Input an area of the property: ");
        double price = inputGetPositiveDouble(scanner, "Input the price for the offer: ");
        int apartmentNumber = inputGetPositiveInt(scanner, "Input the apartment number: ");
        int floorNumber = inputGetPositiveInt(scanner, "Input the floor number: ");

        String msg = "Input the date the offer should be valid to (e.g. 2024-01-01) : ";
        LocalDate validTill = inputGetDate(scanner, msg);

        Apartment newTask = new Apartment(streetName, streetNumber, town, postcode, areaM2, price,
                                            validTill, apartmentNumber, floorNumber);
        offersList.addOffer(newTask);
        System.out.println("\nNew apartment offer added.");
    }

    static void handleShowValidOffers(OffersList offersList, String type) {
        if (noOffersExist(offersList)) {
            msgNoOffers();
            return;
        }
        switch (type.toLowerCase()) {
            case "all" -> {
                System.out.println("\nAvailable offers:");
                printOffers(offersList.getOffers(a -> a.getValidTill().isAfter(LocalDate.now())));}
            case "houses" -> printOffersHouse(offersList);
            case "apartments" -> printOffersApartment(offersList);
        }
    }

    static void handleShowValidHouseOffersWithConditions(Scanner scanner, OffersList offersList) {
        String town = inputGetString(scanner, "Input the town name to show offers in: ").toLowerCase();
        double areaM2 = inputGetPositiveDouble(scanner, "Input a minimum area of the property: ");

        ArrayList<Property> offers;
        offers = offersList.getOffers(a -> a.getValidTill().isAfter(LocalDate.now()) && a instanceof House
                && a.getTown().toLowerCase().equals(town) && a.getAreaM2() >= areaM2);

        if (noOffersExist(offers)) {
            msgNoOffers();
            return;
        }
        System.out.println("\nAvailable house offers in " + town + " with area >= " + areaM2 + "m^2 :");
        printOffers(offers);
    }

    static void handleShowValidApartmentOffersWithConditions(Scanner scanner, OffersList offersList) {
        String town = inputGetString(scanner, "Input the town name to show offers in: ").toLowerCase();
        double price = inputGetPositiveDouble(scanner, "Input a maximum price for the offer: ");
        int floorNumber = inputGetPositiveInt(scanner, "Input the minimum floor number: ");

        ArrayList<Property> offers;
        offers = offersList.getOffers(a -> a.getValidTill().isAfter(LocalDate.now()) && a instanceof Apartment
                && a.getTown().toLowerCase().equals(town) && a.getPrice() <= price
                && ((Apartment) a).getFloorNumber() >= floorNumber);

        if (noOffersExist(offers)) {
            msgNoOffers();
            return;
        }
        System.out.println("\nAvailable apartment offers:");
        printOffers(offers);
    }

    static boolean noOffersExist(OffersList offersList) {
        return offersList.getOffersAmount() == 0;
    }

    static boolean noOffersExist(ArrayList<Property> offers) {
        return offers.isEmpty();
    }

    static void msgNoOffers() {
        System.out.println("\nNo wanted offers available.");
    }

    static void printOffersHouse(OffersList offersList) {
        ArrayList<Property> offers;
        offers = offersList.getOffers(a -> a instanceof House && a.getValidTill().isAfter(LocalDate.now()));

        System.out.println("\nAvailable house offers:");
        printOffers(offers);
    }

    static void printOffersApartment(OffersList offersList) {
        ArrayList<Property> offers;
        offers = offersList.getOffers(a -> a instanceof Apartment && a.getValidTill().isAfter(LocalDate.now()));

        System.out.println("\nAvailable apartment offers:");
        printOffers(offers);
    }

    static void printOffers(ArrayList<Property> offers) {
        int i = 1;
        for (Property offer : offers) {
            System.out.printf("[%d] %s\n", i, offer);
            i++;
        }
    }

    static String inputGetString(Scanner scanner, String msg) {
        String input;
        do {
            System.out.print(msg);
            input = scanner.nextLine().strip();
        } while (input.isEmpty());
        return input;
    }

    static int inputGetPositiveInt(Scanner scanner, String msg) {
        int input = -1;
        do {
            System.out.print(msg);
            try {
                input = Integer.parseInt(scanner.nextLine().strip());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while (input <= 0);
        return input;
    }

    static double inputGetPositiveDouble(Scanner scanner, String msg) {
        double input = -1;
        do {
            System.out.print(msg);
            try {
                input = Double.parseDouble(scanner.nextLine().strip());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while (input <= 0);
        return input;
    }

    static LocalDate inputGetDate(Scanner scanner, String msg) {
        LocalDate input = null;
        do {
            System.out.print(msg);
            try {
                input = LocalDate.parse(scanner.nextLine().strip());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while (input == null);

        return input;
    }
}
