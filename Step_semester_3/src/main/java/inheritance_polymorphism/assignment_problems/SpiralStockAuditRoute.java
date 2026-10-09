package inheritance_polymorphism.assignment_problems;

import java.util.ArrayList;
import java.util.List;

/** Week 9 assignment - clockwise spiral traversal of a rectangular grid. */
public class SpiralStockAuditRoute {
    public static List<Integer> auditRoute(int[][] grid) {
        List<Integer> route = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) return route;
        int top = 0, bottom = grid.length - 1;
        int left = 0, right = grid[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) route.add(grid[top][c]);
            top++;
            for (int r = top; r <= bottom; r++) route.add(grid[r][right]);
            right--;
            if (top <= bottom) {
                for (int c = right; c >= left; c--) route.add(grid[bottom][c]);
                bottom--;
            }
            if (left <= right) {
                for (int r = bottom; r >= top; r--) route.add(grid[r][left]);
                left++;
            }
        }
        return route;
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(auditRoute(grid));
    }
}
