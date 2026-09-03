package classes.class_problems;

public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Invalid minimum penalty");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        double percent = 0.0;

        if (minutesLate <= 5) {
            percent = minutesLate * 0.5;
        } else if (minutesLate <= 15) {
            percent = 5 * 0.5 + (minutesLate - 5) * 1.0;
        } else {
            percent = 5 * 0.5 + 10 * 1.0 + (minutesLate - 15) * 2.0;
        }

        if (percent < minimumPenaltyPercent) {
            percent = minimumPenaltyPercent;
        }

        return ticketFare * percent / 100.0;
    }
}
