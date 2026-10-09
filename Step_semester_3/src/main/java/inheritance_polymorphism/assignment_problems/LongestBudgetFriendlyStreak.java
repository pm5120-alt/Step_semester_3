package inheritance_polymorphism.assignment_problems;

/** Week 9 assignment - longest non-negative-cost subarray within budget. */
public class LongestBudgetFriendlyStreak {
    // Returns {length, startIndex}; ties keep the earliest starting index.
    public static int[] longestStreak(int[] costs, long budget) {
        int left = 0, bestLength = 0, bestStart = -1;
        long sum = 0;
        for (int right = 0; right < costs.length; right++) {
            if (costs[right] < 0) throw new IllegalArgumentException("Costs must be non-negative");
            sum += costs[right];
            while (left <= right && sum > budget) sum -= costs[left++];
            int length = right - left + 1;
            if (length > bestLength) {
                bestLength = length;
                bestStart = left;
            }
        }
        return bestLength == 0 ? new int[]{0, -1} : new int[]{bestLength, bestStart};
    }

    public static void main(String[] args) {
        int[] result = longestStreak(new int[]{4, 2, 1, 7, 3, 1, 2, 1, 5}, 8);
        System.out.printf("(%d, %d)%n", result[0], result[1]);
    }
}
