package inheritance_polymorphism.class_problems;

class AnnounceEventTicket {
    protected double balance;
    public AnnounceEventTicket(double balance) { this.balance = balance; }
    public String printTicket() { return "Standard | Balance: " + balance; }
}
class AnnounceWorkshopTicket extends AnnounceEventTicket {
    private String track;
    public AnnounceWorkshopTicket(double balance, String track) { super(balance); this.track = track; }
    public String getTrack() { return track; }
    @Override public String printTicket() { return "Workshop | Track: " + track + " | Balance: " + balance; }
}
public class Problem4TicketAnnouncer {
    public static String batchPrint(AnnounceEventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        for (AnnounceEventTicket t : tickets) {
            report.append(t.printTicket());
            if (t instanceof AnnounceWorkshopTicket) {
                AnnounceWorkshopTicket w = (AnnounceWorkshopTicket)t;
                report.append(" [Track via downcast: ").append(w.getTrack()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }
    public static void main(String[] args) {
        System.out.println(batchPrint(new AnnounceEventTicket[]{new AnnounceEventTicket(500), new AnnounceWorkshopTicket(1200, "AI/ML")}));
    }
}
