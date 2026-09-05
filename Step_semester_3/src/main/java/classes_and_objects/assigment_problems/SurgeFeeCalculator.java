public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge cannot be negative");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        int firstPart = Math.min(delayMinutes, 5);
        int secondPart = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int thirdPart = Math.max(delayMinutes - 15, 0);

        double firstFee = orderValue * 0.005 * firstPart;
        double secondFee = orderValue * 0.01 * secondPart;
        double thirdFee = orderValue * 0.02 * thirdPart;

        double tieredFee = firstFee + secondFee + thirdFee;
        double minimumFee = orderValue * minimumSurgePercent / 100.0;

        return Math.max(tieredFee, minimumFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);

        System.out.println("Rs " + calculator.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calculator.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calculator.calculateSurgeFee(500, 16));
    }
}
