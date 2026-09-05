public class DeliveryAccount {
    private static double defaultMinimumSurgePercent;

    static {
        defaultMinimumSurgePercent = 1.0;
    }

    protected String studentId;
    protected double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        SurgeFeeCalculator calculator =
            new SurgeFeeCalculator(defaultMinimumSurgePercent);
        return calculator.calculateSurgeFee(orderValue, delayMinutes);
    }

    public static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            return;
        }

        account.orderValue = amount;
        double fee = account.calculateSurgeFee(delayMinutes);

        if (account instanceof Premium) {
            fee = fee * 0.5;
            System.out.println("Premium account " + account.studentId + " surge fee: Rs " + fee);
        } else {
            System.out.println("Regular account " + account.studentId + " surge fee: Rs " + fee);
        }
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        int count = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double total = 0.0;

        for (int i = 0; i < count; i++) {
            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            account.orderValue = amounts[i];
            double fee = account.calculateSurgeFee(delayMinutesArray[i]);

            if (account instanceof Premium) {
                fee = fee * 0.5;
                premiumCount++;
            } else {
                regularCount++;
            }

            total += fee;
            processed++;
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | "
            + premiumCount + " premium | " + regularCount + " regular |");
        System.out.println("grand total surge fees = Rs " + total);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {500, 400, 300};
        int[] delays = {10, 5, 0};

        processBatch(accounts, amounts, delays);
    }
}
