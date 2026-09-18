package inheritance_polymorphism.assignment_problems;

class RaceEntry5 {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private static int bibCounter;
    private final String entryCode;

    public RaceEntry5(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.length() < 4)
            throw new IllegalArgumentException("Invalid bib number");
        if (entryFee <= 0)
            throw new IllegalArgumentException("Invalid entry fee");

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;

        bibCounter++;
        entryCode = "ENTRY-" + bibCounter;
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        pay(amount);
        System.out.println("Paying via " + mode);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5)
            return false;

        return code.charAt(0) == 'M'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && Character.isUpperCase(code.charAt(4));
    }
}

class RunnerEntry5 extends RaceEntry5 {
    protected String category;

    public RunnerEntry5(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry5 extends RunnerEntry5 {
    public EliteRunnerEntry5(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee, category);
    }
}

class RelayTeamEntry5 extends RaceEntry5 {
    private int teamSize;

    public RelayTeamEntry5(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        if (teamSize <= 0)
            throw new IllegalArgumentException("Invalid team size");
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class Problem5SettlementEngine {
    public static String settleNight(RaceEntry5[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry5 entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry5)
                relay++;
            else
                individual++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        EliteRunnerEntry5 elite =
                new EliteRunnerEntry5("BIB3001", 150, "Elite Full Marathon");
        RelayTeamEntry5 relay = new RelayTeamEntry5("BIB4001", 300, 4);

        elite.pay(10, "UPI");

        System.out.println(settleNight(new RaceEntry5[]{elite, null, relay}));
        System.out.println(RaceEntry5.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry5.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry5.isValidDiscountCode("X123A"));
        System.out.println(RaceEntry5.getBibCounter());
    }
}
