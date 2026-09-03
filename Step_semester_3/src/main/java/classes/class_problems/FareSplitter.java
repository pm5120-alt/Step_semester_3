package classes.class_problems;

public class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Invalid fare or passenger count");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        double[] result = new double[passengerCount];
        double share = Math.floor((totalFare / passengerCount) * 100.0) / 100.0;
        double used = 0.0;

        for (int i = 0; i < passengerCount - 1; i++) {
            result[i] = share;
            used += share;
        }

        result[passengerCount - 1] = Math.round((totalFare - used) * 100.0) / 100.0;
        return result;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}
