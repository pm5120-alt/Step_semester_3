package inheritance_polymorphism.assigment_problems;

class SettlementEventTicket {
    private static int ticketsIssued;
    protected double basePrice;
    protected double amountPaid;
    private final String ticketId;

    public SettlementEventTicket(double basePrice) {
        if (basePrice <= 0) throw new IllegalArgumentException("Invalid price");
        this.basePrice = basePrice;
        ticketsIssued++;
        ticketId = "TCK-" + (1000 + ticketsIssued);
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Payment mode: " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public String getTicketId() {
        return ticketId;
    }

    public static int getTicketsIssued() {
        return ticketsIssued;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) return false;
        if (code.charAt(0) != 'F') return false;
        if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2)) || !Character.isDigit(code.charAt(3))) return false;
        return Character.isUpperCase(code.charAt(4));
    }
}

class GroupTicket extends SettlementEventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        if (groupSize <= 0) throw new IllegalArgumentException("Invalid group size");
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class Problem5SettlementEngine {
    public static String processNightlySettlement(SettlementEventTicket[] tickets) {
        int processed = 0, skipped = 0, group = 0, individual = 0;
        for (SettlementEventTicket ticket : tickets) {
            if (ticket == null) {
                skipped++;
                continue;
            }
            processed++;
            if (ticket instanceof GroupTicket) group++;
            else individual++;
        }
        return processed + " processed | " + skipped + " null skipped | " + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        SettlementEventTicket t1 = new SettlementEventTicket(500);
        System.out.println(t1.getTicketId());
        System.out.println(isValidPromoCode("F123A"));
        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());
        System.out.println(processNightlySettlement(new SettlementEventTicket[]{new GroupTicket(2000, 5), null, new SettlementEventTicket(500)}));
    }

    private static boolean isValidPromoCode(String code) {
        return SettlementEventTicket.isValidPromoCode(code);
    }
}
