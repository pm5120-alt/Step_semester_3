package inheritance_polymorphism.class_problems;

/** Week 9 - total inventory and first maximum bin coordinate. */
public class WarehouseBinGridScan {
    // Returns {total, maxRow, maxColumn}; ties keep the first row-major coordinate.
    public static long[] warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0)
            return new long[]{0, -1, -1};
        long total = 0;
        int max = Integer.MIN_VALUE, maxRow = 0, maxCol = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int value = grid[r][c];
                total += value;
                if (value > max) {
                    max = value;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return new long[]{total, maxRow, maxCol};
    }

    public static void main(String[] args) {
        int[][] grid = {{4, 9, 2}, {7, 1, 6}, {3, 12, 5}};
        long[] result = warehouseSummary(grid);
        System.out.printf("total = %d, maxCoordinate = (%d, %d)%n",
                result[0], result[1], result[2]);
    }
}
