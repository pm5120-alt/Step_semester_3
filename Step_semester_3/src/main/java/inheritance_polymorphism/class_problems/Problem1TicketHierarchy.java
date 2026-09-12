package inheritance_polymorphism.class_problems;

class EventTicket {
    protected String attendeeId;
    protected double basePrice, amountPaid;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.length() < 4 || basePrice <= 0)
            throw new IllegalArgumentException("Invalid ticket details");
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }
    public void pay(double amount) { if (amount > 0) amountPaid += amount; }
    public double getBalanceDue() { return basePrice - amountPaid; }
}

class WorkshopTicket extends EventTicket {
    private String track;
    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice); this.track = track;
    }
    public String getTrack() { return track; }
}

public class Problem1TicketHierarchy {
    public static String registerBatch(String[] ids, double price) {
        int registered = 0, rejected = 0;
        for (String id : ids) {
            try { new EventTicket(id, price); registered++; }
            catch (IllegalArgumentException e) { rejected++; }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());
        System.out.println(registerBatch(new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
    }
}
