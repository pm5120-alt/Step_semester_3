package classes.class_problems;

public class BusTicketAccount {
    private static double defaultPenaltyPercent;

    static {
        defaultPenaltyPercent = 1.0;
    }

    protected String bookingId;
    protected double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (ticketFare < 0) {
            throw new IllegalArgumentException("Invalid ticket fare");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        double percent;

        if (minutesLate <= 5) {
            percent = minutesLate * 0.5;
        } else if (minutesLate <= 15) {
            percent = 5 * 0.5 + (minutesLate - 5) * 1.0;
        } else {
            percent = 5 * 0.5 + 10 * 1.0 + (minutesLate - 15) * 2.0;
        }

        if (percent < defaultPenaltyPercent) {
            percent = defaultPenaltyPercent;
        }

        return ticketFare * percent / 100.0;
    }

    public static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            System.out.println("Null account skipped");
            return;
        }

        double penalty = account.calculatePenalty(minutesLate);

        if (account instanceof Sleeper) {
            penalty = penalty * 0.5;
        }

        System.out.println("Processed " + account.bookingId + " | Amount: " + amount + " | Penalty: " + penalty);
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            System.out.println("0 processed | 0 null skipped | 0 sleeper | 0 regular | grand total penalties = 0.0");
            return;
        }

        int length = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));
        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double totalPenalty = 0.0;

        for (int i = 0; i < length; i++) {
            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            double penalty = accounts[i].calculatePenalty(minutesLateArray[i]);

            if (accounts[i] instanceof Sleeper) {
                penalty *= 0.5;
                sleeper++;
            } else {
                regular++;
            }

            processed++;
            totalPenalty += penalty;
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " + sleeper + " sleeper | " + regular + " regular | grand total penalties = " + totalPenalty);
    }
}
