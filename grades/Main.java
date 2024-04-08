import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scannerMenuChoice = new Scanner(System.in);
        Scanner scannerFunctionInput = new Scanner(System.in);
        boolean programExit = false;

        GradeList gradeList= new GradeList();

        while(!programExit) {
            showProgramInfo();

            switch (scannerMenuChoice.nextLine().trim()) {
                case "1" -> handleAddNewGrade(scannerFunctionInput, gradeList);
                case "2" -> handleShowAverageGrade(gradeList);
                case "3" -> handleShowHighestGrade(gradeList);
                case "4" -> handleShowLowestGrade(gradeList);
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
        System.out.println("\nThis program offers these functions:");
        System.out.println("[1] Add new grade");
        System.out.println("[2] Show average grade");
        System.out.println("[3] Show highest grade");
        System.out.println("[4] Show lowest grade");
        System.out.println("[0] Exit the program");
        System.out.print("Choose option [1,2,3,4,0]: ");
    }

    static void handleAddNewGrade(Scanner scannerFunctionInput, GradeList gradeList) {
        double newGrade = 0;
        System.out.println();
        // check new grade input
        do {
            System.out.print("Input the value for the new grade (1<=x<=6): ");
            try {
                newGrade = Double.parseDouble(scannerFunctionInput.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Wrong input!");
            }
        } while ((newGrade < 1) || (newGrade > 6));
        gradeList.addGrade(newGrade);
        System.out.println("New grade value added.");
    }

    static void handleShowAverageGrade(GradeList gradeList) {
        if ( !gradesExist(gradeList) ) return;
        System.out.println("\nAverage grade: " + gradeList.getGradeAverage());
    }

    static void handleShowHighestGrade(GradeList gradeList) {
        if ( !gradesExist(gradeList) ) return;
        System.out.println("\nHighest grade: " + gradeList.getGradeHighest());
    }

    static void handleShowLowestGrade(GradeList gradeList) {
        if ( !gradesExist(gradeList) ) return;
        System.out.println("\nLowest grade: " + gradeList.getGradeLowest());
    }

    static boolean gradesExist(GradeList gradeList) {
        if ( gradeList.getGradesAmount() == 0 ) {
            System.out.println("\nNo grades available. Add some grades first");
            return false;
        } else {
            return true;
        }
    }
}
