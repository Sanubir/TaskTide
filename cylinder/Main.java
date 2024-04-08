import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerFunctionInput = new Scanner(System.in);
        boolean programExit = false;

        Cylinder myCylinder = new Cylinder();

        while(!programExit) {
            showProgramInfo();

            switch (scannerMenuChoice.nextLine().trim()) {
                case "1" -> handleShowCylinderVariables(myCylinder);
                case "2" -> handleSetNewValues(scannerFunctionInput, myCylinder);
                case "3" -> handleShowSurfacesAreas(myCylinder);
                case "4" -> handleShowVolume(myCylinder);
                case "0" -> {
                    System.out.println("\nExiting the program...");
                    programExit = true;
                    continue;
                }
                default -> {
                    System.out.println("\nWrong option!");
                    continue;
                }
            }
            System.out.print("\nClick enter to continue");
            scannerMenuChoice.nextLine();
        }
    }

    static void showProgramInfo() {
        System.out.println("\nThis program offers these functions for working with a cylinder:");
        System.out.println("[1] Show values of cylinder radius and its height");
        System.out.println("[2] Set new values for radius and height");
        System.out.println("[3] Show areas of the cylinder");
        System.out.println("[4] Show volume of the cylinder");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,0]: ");
    }

    static void handleShowCylinderVariables(Cylinder cylinder) {
        System.out.println("\nCylinder's radius: " + cylinder.getRadius());
        System.out.println("Cylinder's height: " + cylinder.getHeight());
    }

    static void handleSetNewValues(Scanner scannerFunctionInput, Cylinder cylinder) {
        double newRadius = 0;
        double newHeight = 0;
        System.out.println();
        // handle and check new radius input
        do {
            System.out.print("Input the new value for the cylinder radius (>0): ");
            try {
                newRadius = Double.parseDouble(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while (newRadius <= 0);
        cylinder.setRadius(newRadius);

        // handle and check new height input
        do {
            System.out.print("Input the new value for the cylinder height (>0): ");
            try {
                newHeight = Double.parseDouble(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while (newHeight <= 0);
        cylinder.setHeight(newHeight);

        System.out.println("New radius and height values set.");
    }

    static void handleShowSurfacesAreas(Cylinder cylinder) {
        System.out.println("\nArea of base: \t" + cylinder.getAreaOfBase());
        System.out.println("Area of sides: \t" + cylinder.getAreaOfSides());
        System.out.println("Area of whole cylinder: " + cylinder.getArea());
    }

    static void handleShowVolume(Cylinder cylinder) {
        System.out.println("\nVolume of the cylinder: " + cylinder.getVolume());
    }
}
