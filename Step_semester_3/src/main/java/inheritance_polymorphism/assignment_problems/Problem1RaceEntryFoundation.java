package inheritance_polymorphism.assignment_problems;

class RaceEntry1 {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry1(String bibNumber, double entryFee) {
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
}

class RunnerEntry1 extends RaceEntry1 {
    private String category;

    public RunnerEntry1(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}

public class Problem1RaceEntryFoundation {
    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bibNumber : bibNumbers) {
            try {
                new RaceEntry1(bibNumber, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        RunnerEntry1 r = new RunnerEntry1("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());
        System.out.println(registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
    }
}
