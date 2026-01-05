/**
 * Grade calculation utility class
 * All calculation operations are performed here
 */
public class GradeCalculator {

    /**
     * Calculates weighted average
     * 
     * @param grades  Grade list
     * @param weights Weight list (as percentage, e.g., 15 = 15%)
     * @return Calculated weighted average
     */
    public static double calculateWeightedAverage(double[] grades, double[] weights) {
        if (grades.length != weights.length) {
            throw new IllegalArgumentException("Number of grades and weights must be equal!");
        }

        double total = 0;
        for (int i = 0; i < grades.length; i++) {
            total += grades[i] * (weights[i] / 100);
        }
        return total;
    }

    /**
     * Calculates weighted average for the classic system
     * Weights: Assignment 15%, Quiz 9%, Participation 15%, Midterm 21%, Final 40%
     */
    public static double calculateClassicSystem(double assignment, double quiz, double participation, double midterm,
            double finalGrade) {
        return (assignment * 0.15) + (quiz * 0.09) + (participation * 0.15) + (midterm * 0.21) + (finalGrade * 0.40);
    }

    /**
     * Determines letter grade based on total score
     * 
     * @param total Calculated total score
     * @return Letter grade and status information
     */
    public static String determineLetterGrade(double total) {
        if (total < 30) {
            return "Fail FF";
        } else if (total >= 30 && total < 35) {
            return "DC-DD";
        } else if (total >= 35 && total < 40) {
            return "CC";
        } else if (total >= 40 && total < 50) {
            return "CB";
        } else if (total >= 50 && total < 60) {
            return "BB";
        } else if (total >= 60 && total < 70) {
            return "BA";
        } else if (total >= 70 && total < 80) {
            return "AA-";
        } else {
            return "AA";
        }
    }

    /**
     * Checks if grade is valid (between 0-100)
     */
    public static boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }

    /**
     * Checks if weight is valid (between 0-100)
     */
    public static boolean isValidWeight(double weight) {
        return weight >= 0 && weight <= 100;
    }

    // Keep old method names for backward compatibility
    @Deprecated
    public static double agirlikliOrtalamaHesapla(double[] notlar, double[] agirliklar) {
        return calculateWeightedAverage(notlar, agirliklar);
    }

    @Deprecated
    public static double klasikSistemHesapla(double odev, double quiz, double katilim, double vize, double finalNot) {
        return calculateClassicSystem(odev, quiz, katilim, vize, finalNot);
    }

    @Deprecated
    public static String harfNotuBelirle(double toplam) {
        return determineLetterGrade(toplam);
    }

    @Deprecated
    public static boolean notGecerliMi(double not) {
        return isValidGrade(not);
    }

    @Deprecated
    public static boolean agirlikGecerliMi(double agirlik) {
        return isValidWeight(agirlik);
    }
}
