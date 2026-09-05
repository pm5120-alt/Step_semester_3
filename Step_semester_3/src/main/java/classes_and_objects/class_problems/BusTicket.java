package classes.class_problems;

import java.util.ArrayList;

public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty() || !passengerName.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid passenger name");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid destination");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
    }

    public void markCheckedIn() {
        if (checkedIn) {
            System.out.println("Already checked in: " + passengerName);
        } else {
            checkedIn = true;
            System.out.println("Checked in: " + passengerName);
        }
    }

    public static void processBatch(String[][] rawBookings) {
        ArrayList<String> accepted = new ArrayList<>();
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        if (rawBookings != null) {
            for (String[] booking : rawBookings) {
                if (booking == null || booking.length < 2) {
                    rejected++;
                    continue;
                }

                try {
                    BusTicket ticket = new BusTicket(booking[0], booking[1]);
                    String key = ticket.passengerName.toLowerCase() + "|" + ticket.destination.toLowerCase();

                    if (accepted.contains(key)) {
                        duplicates++;
                    } else {
                        accepted.add(key);
                        valid++;
                    }
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }
}
