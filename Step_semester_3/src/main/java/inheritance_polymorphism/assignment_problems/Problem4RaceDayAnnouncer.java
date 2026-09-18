package inheritance_polymorphism.assignment_problems;

class RaceEntry4 {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry4(String bibNumber, double entryFee) {
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

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }
}

class RunnerEntry4 extends RaceEntry4 {
    private String category;

    public RunnerEntry4(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry4 extends RaceEntry4 {
    private int teamSize;

    public RelayTeamEntry4(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        if (teamSize <= 0)
            throw new IllegalArgumentException("Invalid team size");
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize
                + " | Balance: " + getBalanceDue();
    }
}

public class Problem4RaceDayAnnouncer {
    public static String announceAll(RaceEntry4[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry4 entry : entries) {
            report.append(entry.announce()).append(" ");

            if (entry instanceof RelayTeamEntry4) {
                RelayTeamEntry4 relay = (RelayTeamEntry4) entry;
                report.append("[Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("] ");
            }
        }

        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry4 runner = new RunnerEntry4("BIB2001", 80, "Open 10K");
        runner.pay(30);

        RelayTeamEntry4 relay = new RelayTeamEntry4("BIB4001", 300, 4);

        System.out.println(announceAll(new RaceEntry4[]{runner, relay}));
    }
}
