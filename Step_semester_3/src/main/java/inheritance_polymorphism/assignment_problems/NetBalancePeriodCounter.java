package inheritance_polymorphism.assignment_problems;

import java.util.HashMap;
import java.util.Map;

/** Week 9 assignment - count subarrays summing to k, including negative values. */
public class NetBalancePeriodCounter {
    public static long countPeriods(int[] transactions, long k) {
        Map<Long, Long> frequency = new HashMap<>();
        frequency.put(0L, 1L);
        long prefix = 0, count = 0;
        for (int transaction : transactions) {
            prefix += transaction;
            count += frequency.getOrDefault(prefix - k, 0L);
            frequency.put(prefix, frequency.getOrDefault(prefix, 0L) + 1L);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countPeriods(new int[]{3, 4, -7, 1, 3, 3, 1, -4}, 7));
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10));
    }
}
