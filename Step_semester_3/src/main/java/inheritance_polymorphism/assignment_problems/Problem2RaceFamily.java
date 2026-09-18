package inheritance_polymorphism.assignment_problems;

class RaceEntry2 {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry2(String bibNumber, double entryFee) {
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

class RunnerEntry2 extends RaceEntry2 {
    protected String category;

    public RunnerEntry2(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category
                + " | Balance: " + getBalanceDue();
    }
}

class EliteRunnerEntry2 extends RunnerEntry2 {
    private double sponsorBonus;

    public EliteRunnerEntry2(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public String announce() {
        return "Elite Runner | Bib: " + bibNumber + " | Category: " + category
                + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry2 extends RaceEntry2 {
    private int teamSize;

    public RelayTeamEntry2(String bibNumber, double entryFee, int teamSize) {
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

public class Problem2RaceFamily {
    public static String classifyGeneration(RaceEntry2 entry) {
        if (entry instanceof EliteRunnerEntry2)
            return "Multilevel descendant (3 generations deep)";
        if (entry instanceof RelayTeamEntry2)
            return "Hierarchical sibling (independent branch)";
        if (entry instanceof RunnerEntry2)
            return "Single-inheritance descendant";
        return "Base entry";
    }

    public static double getTotalBalanceDue(RaceEntry2[] entries) {
        double total = 0;
        for (RaceEntry2 entry : entries)
            total += entry.getBalanceDue();
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry2 runner = new RunnerEntry2("BIB2001", 80, "Open 10K");
        EliteRunnerEntry2 elite =
                new EliteRunnerEntry2("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry2 relay = new RelayTeamEntry2("BIB4001", 300, 4);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());
        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));
        System.out.println(getTotalBalanceDue(new RaceEntry2[]{runner, elite, relay}));
    }
}
