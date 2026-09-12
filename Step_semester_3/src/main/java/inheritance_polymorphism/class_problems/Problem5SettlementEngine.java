package inheritance_polymorphism.class_problems;

class SettlementEventTicket {
    private static int ticketsIssued;
    protected double basePrice, amountPaid;
    private final String ticketId;
    public SettlementEventTicket(double price) {
        if (price <= 0) throw new IllegalArgumentException("Invalid price");
        basePrice = price; ticketsIssued++; ticketId = "TCK-" + (1000 + ticketsIssued);
    }
    public void pay(double amount) { if (amount > 0) amountPaid += amount; }
    public void pay(double amount, String mode) { System.out.println("Payment mode: " + mode); pay(amount); }
    public double getBalanceDue() { return basePrice - amountPaid; }
    public String getTicketId() { return ticketId; }
    public static int getTicketsIssued() { return ticketsIssued; }
    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5 || code.charAt(0) != 'F') return false;
        return Character.isDigit(code.charAt(1)) && Character.isDigit(code.charAt(2)) && Character.isDigit(code.charAt(3)) && Character.isUpperCase(code.charAt(4));
    }
}
class GroupTicket extends SettlementEventTicket {
    private int groupSize;
    public GroupTicket(double price, int size) { super(price); if (size <= 0) throw new IllegalArgumentException("Invalid group size"); groupSize=size; }
}
public class Problem5SettlementEngine {
    public static String processNightlySettlement(SettlementEventTicket[] tickets) {
        int processed=0, skipped=0, group=0, individual=0;
        for (SettlementEventTicket t : tickets) {
            if (t == null) { skipped++; continue; }
            processed++; if (t instanceof GroupTicket) group++; else individual++;
        }
        return processed + " processed | " + skipped + " null skipped | " + group + " group | " + individual + " individual";
    }
    public static void main(String[] args) {
        SettlementEventTicket t = new SettlementEventTicket(500); System.out.println(t.getTicketId());
        System.out.println(SettlementEventTicket.isValidPromoCode("F123A")); t.pay(200); t.pay(200,"UPI");
        System.out.println(t.getBalanceDue());
        System.out.println(processNightlySettlement(new SettlementEventTicket[]{new GroupTicket(2000,5),null,new SettlementEventTicket(500)}));
    }
}
