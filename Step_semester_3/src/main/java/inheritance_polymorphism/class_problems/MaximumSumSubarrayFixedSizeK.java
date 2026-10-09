package inheritance_polymorphism.class_problems;

/** Week 9 - maximum sum among all contiguous windows of size k. */
public class MaximumSumSubarrayFixedSizeK {
    public static long maxSumSubarray(int[] sales, int k) {
        if (sales == null || k <= 0 || k > sales.length)
            throw new IllegalArgumentException("k must be between 1 and sales.length");
        long windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += sales[i];
        long best = windowSum;
        for (int right = k; right < sales.length; right++) {
            windowSum += sales[right] - (long) sales[right - k];
            best = Math.max(best, windowSum);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3));
    }
}
