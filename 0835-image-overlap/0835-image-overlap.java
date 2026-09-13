import java.util.*;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> onesA = new ArrayList<>();
        List<int[]> onesB = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) onesA.add(new int[]{i, j});
                if (img2[i][j] == 1) onesB.add(new int[]{i, j});
            }
        }

        Map<Integer, Integer> shiftCount = new HashMap<>();
        int maxOverlap = 0;

        for (int[] a : onesA) {
            for (int[] b : onesB) {
                int dx = a[0] - b[0];
                int dy = a[1] - b[1];
                // encode (dx, dy) into a single key; offset to keep it non-negative
                int key = (dx + n) * 200 + (dy + n);
                int c = shiftCount.merge(key, 1, Integer::sum);
                maxOverlap = Math.max(maxOverlap, c);
            }
        }

        // Handle edge case: no 1s in either image at all
        return onesA.isEmpty() || onesB.isEmpty() ? 0 : maxOverlap;
    }
}