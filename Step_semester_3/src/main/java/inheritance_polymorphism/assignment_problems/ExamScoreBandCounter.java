package inheritance_polymorphism.assignment_problems;

/** Week 9 assignment - count sorted scores in the inclusive band [low, high]. */
public class ExamScoreBandCounter {
    private static int lowerBound(int[] a, int target) {
        int left = 0, right = a.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] < target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private static int upperBound(int[] a, int target) {
        int left = 0, right = a.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (a[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    public static int countInBand(int[] scores, int low, int high) {
        if (low > high) return 0;
        return upperBound(scores, high) - lowerBound(scores, low);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println(countInBand(scores, 42, 58));
        System.out.println(countInBand(scores, 90, 100));
    }
}
