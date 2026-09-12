package inheritance_polymorphism.assigment_problems;

class AnnounceEventTicket {
    protected double balance;

    public AnnounceEventTicket(double balance) {
        this.balance = balance;
    }

    public String printTicket() {
        return "Standard | Balance: " + balance;
    }

    public double getBalanceDue() {
        return balance;
    }
}

class AnnounceWorkshopTicket extends AnnounceEventTicket {
    private String track;

    public AnnounceWorkshopTicket(double balance, String track) {
        super(balance);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + balance;
    }
}

public class Problem4TicketAnnouncer {
    public static String batchPrint(AnnounceEventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        for (AnnounceEventTicket ticket : tickets) {
            report.append(ticket.printTicket());
            if (ticket instanceof AnnounceWorkshopTicket) {
                AnnounceWorkshopTicket workshop = (AnnounceWorkshopTicket) ticket;
                report.append(" [Track via downcast: ").append(workshop.getTrack()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        AnnounceEventTicket[] tickets = {
            new AnnounceEventTicket(500), new AnnounceWorkshopTicket(1200, "AI/ML")
        };
        System.out.println(batchPrint(tickets));
    }
}
