package inheritance_polymorphism.class_problems;

class FamilyEventTicket {
    protected double basePrice, amountPaid;
    public FamilyEventTicket(double price) { if (price <= 0) throw new IllegalArgumentException(); basePrice = price; }
    public void pay(double amount) { if (amount > 0) amountPaid += amount; }
    public double getBalanceDue() { return basePrice - amountPaid; }
    public String printTicket() { return "Standard Event Ticket | Balance Due: " + getBalanceDue(); }
}
class FamilyWorkshopTicket extends FamilyEventTicket {
    protected String track;
    public FamilyWorkshopTicket(double price, String track) { super(price); this.track = track; }
    public String printTicket() { return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue(); }
}
class PremiumWorkshopTicket extends FamilyWorkshopTicket {
    private double kitFee;
    public PremiumWorkshopTicket(double price, String track, double fee) { super(price, track); kitFee = fee; }
    public String printTicket() { return "Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue(); }
}
class HackathonTicket extends FamilyEventTicket {
    private String teamName;
    public HackathonTicket(double price, String team) { super(price); teamName = team; }
    public String printTicket() { return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue(); }
}
public class Problem2InheritanceFamily {
    public static String classifyGeneration(FamilyEventTicket t) {
        if (t instanceof PremiumWorkshopTicket) return "Multilevel descendant (3 generations deep)";
        if (t instanceof HackathonTicket) return "Hierarchical sibling (independent branch)";
        if (t instanceof FamilyWorkshopTicket) return "Direct workshop descendant";
        return "Base event ticket";
    }
    public static double getTotalBalanceDue(FamilyEventTicket[] tickets) {
        double total = 0; for (FamilyEventTicket t : tickets) total += t.getBalanceDue(); return total;
    }
    public static void main(String[] args) {
        FamilyEventTicket[] a = {new FamilyEventTicket(500), new FamilyWorkshopTicket(1200, "AI/ML"), new PremiumWorkshopTicket(2000, "Cloud Native", 300), new HackathonTicket(800, "Byte Force")};
        for (FamilyEventTicket t : a) System.out.println(t.printTicket());
        System.out.println(getTotalBalanceDue(a));
    }
}
