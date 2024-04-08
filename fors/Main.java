import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerFunctionInput = new Scanner(System.in);
        boolean programExit = false;

        while(!programExit) {
            showProgramInfo();

            switch (scannerMenuChoice.nextLine().trim()) {
                case "1":
                    System.out.println("Chosen option: 1");
                    handlePow2N(scannerFunctionInput);
                    break;
                case "2":
                    System.out.println("Chosen option: 2");
                    handleSumFromRange(scannerFunctionInput);
                    break;
                case "0":
                    System.out.println("\nExiting program...");
                    programExit = true;
                    continue;
                default:
                    System.out.println("\nWrong option!\n");
                    break;
            }
        }
    }

    static void showProgramInfo() {
        System.out.println("\nThis program offers two functions:");
        System.out.println("[1] Return a value of 2^n (where 'n' is a natural number given by the user");
        System.out.println("[2] Return a sum of natural numbers from a given range <numberFrom; numberTo>");
        System.out.print("Choose option [1,2,0]: ");
    }

    static void handlePow2N(Scanner scannerFunctionInput) {
        int n = 0;

        // handle and check input
        do {
            System.out.print("Input a NATURAL number for the equation 2^n: ");
            try {
                n = Integer.parseInt(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
                n = -1;
            }
        } while (n < 0);

        // print result
        System.out.println("Result: 2^" + n + " = " + Functions.pow2N(n));
    }

    static void handleSumFromRange(Scanner scannerFunctionInput) {
        int numberFrom = 0;
        int numberTo = 0;

        // handle and check first number
        do {
            System.out.print("Input the first NATURAL number to count the sum from: ");
            try {
                numberFrom = Integer.parseInt(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
                numberFrom = -1;
            }
        } while (numberFrom < 0);

        // handle and check second number
        do {
            System.out.print("Input the second NATURAL number to count the sum to BIGGER than the first number: ");
            try {
                numberTo = Integer.parseInt(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
                numberTo = -1;
            }
        } while (numberTo < numberFrom);

        // print result
        System.out.println("Sum of numbers in < " + numberFrom + " ; " + numberTo + " > is: " +
                Functions.sumFromRange(numberFrom, numberTo));
    }
}