package inheritance_polymorphism.assigment_problems;

class FamilyEventTicket {
    protected double basePrice;
    protected double amountPaid;

    public FamilyEventTicket(double basePrice) {
        if (basePrice <= 0) throw new IllegalArgumentException("Invalid price");
        this.basePrice = basePrice;
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + getBalanceDue();
    }
}

class FamilyWorkshopTicket extends FamilyEventTicket {
    protected String track;

    public FamilyWorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String printTicket() {
        return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
    }
}

class PremiumWorkshopTicket extends FamilyWorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(double basePrice, String track, double kitFee) {
        super(basePrice, track);
        this.kitFee = kitFee;
    }

    public String printTicket() {
        return "Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
    }
}

class HackathonTicket extends FamilyEventTicket {
    private String teamName;

    public HackathonTicket(double basePrice, String teamName) {
        super(basePrice);
        this.teamName = teamName;
    }

    public String printTicket() {
        return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
    }
}

public class Problem2TicketFamily {
    public static String classifyGeneration(FamilyEventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) return "Multilevel descendant (3 generations deep)";
        if (ticket instanceof HackathonTicket) return "Hierarchical sibling (independent branch)";
        if (ticket instanceof FamilyWorkshopTicket) return "Direct workshop descendant";
        return "Base event ticket";
    }

    public static double getTotalBalanceDue(FamilyEventTicket[] tickets) {
        double total = 0;
        for (FamilyEventTicket ticket : tickets) total += ticket.getBalanceDue();
        return total;
    }

    public static void main(String[] args) {
        FamilyEventTicket[] tickets = {
            new FamilyEventTicket(500), new FamilyWorkshopTicket(1200, "AI/ML"),
            new PremiumWorkshopTicket(2000, "Cloud Native", 300), new HackathonTicket(800, "Byte Force")
        };
        for (FamilyEventTicket ticket : tickets) System.out.println(ticket.printTicket());
        System.out.println(getTotalBalanceDue(tickets));
    }
}
