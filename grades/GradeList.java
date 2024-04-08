import java.util.ArrayList;

public class GradeList {
    private ArrayList<Double> grades = new ArrayList<Double>();

    public void addGrade(double newGrade) {
        grades.add(newGrade);
    }

    public double getGradeAverage() {
        double sum = 0;
        for (int i = 0; i < grades.size(); i++) {
            sum += grades.get(i);
        }
        double average = sum / grades.size();
        return average;
    }

    public double getGradeHighest() {
        double highest = grades.getFirst();
        for (int i = 0; i < grades.size(); i++) {
            if (highest < grades.get(i)) {
                highest = grades.get(i);
            }
        }
        return highest;
    }

    public double getGradeLowest() {
        double lowest = grades.getFirst();
        for (int i = 0; i < grades.size(); i++) {
            if (lowest > grades.get(i)) {
                lowest = grades.get(i);
            }
        }
        return lowest;
    }

    public int getGradesAmount() {
        return grades.size();
    }
}
