public class Canteen implements Comparable<Canteen> {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {
        if (trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, trustScore);
        }

        int codeResult = canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeResult != 0) {
            return codeResult;
        }

        return Integer.compare(canteenName.length(), other.canteenName.length());
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();

        for (int i = 0; i < result.length - 1; i++) {
            int best = i;

            for (int j = i + 1; j < result.length; j++) {
                if (result[j].compareTo(result[best]) < 0) {
                    best = j;
                }
            }

            Canteen temp = result[i];
            result[i] = result[best];
            result[best] = temp;
        }

        return result;
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(canteens);

        for (Canteen canteen : ranked) {
            System.out.println(canteen.getCanteenCode());
        }
    }
}
