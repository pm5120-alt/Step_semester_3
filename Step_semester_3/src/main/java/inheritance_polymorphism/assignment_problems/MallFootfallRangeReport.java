package inheritance_polymorphism.assignment_problems;

import java.util.ArrayList;
import java.util.List;

/** Week 9 assignment - answer many inclusive [start, end) range-sum queries. */
public class MallFootfallRangeReport {
    public static List<Long> footfallReport(int[] visitors, int[][] queries) {
        long[] prefix = new long[visitors.length + 1];
        for (int i = 0; i < visitors.length; i++)
            prefix[i + 1] = prefix[i] + visitors[i];
        List<Long> result = new ArrayList<>();
        for (int[] query : queries) {
            int start = query[0], end = query[1];
            if (start < 0 || start > end || end >= visitors.length)
                throw new IllegalArgumentException("Invalid query range");
            result.add(prefix[end + 1] - prefix[start]);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};
        System.out.println(footfallReport(visitors, queries));
    }
}
