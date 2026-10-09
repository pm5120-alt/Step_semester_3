package inheritance_polymorphism.class_problems;

import java.util.Arrays;

/** Week 9 - Pair Sum in a Sorted Array. */
public class PairSumSorted {
    public static String pairSumSorted(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            long sum = (long) nums[left] + nums[right];
            if (sum == target) return "(" + nums[left] + ", " + nums[right] + ")";
            if (sum < target) left++;
            else right--;
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        System.out.println(pairSumSorted(new int[]{-4, -1, 0, 3, 5, 9}, 4));
        System.out.println(pairSumSorted(new int[]{1, 2, 3}, 100));
    }
}
