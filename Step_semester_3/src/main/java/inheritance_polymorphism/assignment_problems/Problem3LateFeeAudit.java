package inheritance_polymorphism.assignment_problems;

import java.util.Arrays;

class RaceEntry3 {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount;

    public RaceEntry3(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4)
            throw new IllegalArgumentException("Invalid bib number");
        if (entryFee <= 0)
            throw new IllegalArgumentException("Invalid entry fee");

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    protected void applyLateFee(double amount) {
        if (lateFeeCount >= lateFeeHistory.length)
            throw new IllegalStateException("Late fee history is full");

        amountPaid -= amount;
        lateFeeHistory[lateFeeCount++] = amount;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount);
    }
}

class RunnerEntry3 extends RaceEntry3 {
    private String category;

    public RunnerEntry3(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Problem3LateFeeAudit {
    public static void main(String[] args) {
        RunnerEntry3 r = new RunnerEntry3("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);

        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        history[0] = 999;

        System.out.println(Arrays.toString(r.getLateFeeHistory()));
    }
}
