import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Only seats 2 to 9 matter
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Every completely empty row can fit 2 families
        int answer = (n - map.size()) * 2;

        for (int mask : map.values()) {

            // Left: seats 2,3,4,5
            boolean left = (mask & (1 << 2)) == 0 &&
                           (mask & (1 << 3)) == 0 &&
                           (mask & (1 << 4)) == 0 &&
                           (mask & (1 << 5)) == 0;

            // Right: seats 6,7,8,9
            boolean right = (mask & (1 << 6)) == 0 &&
                            (mask & (1 << 7)) == 0 &&
                            (mask & (1 << 8)) == 0 &&
                            (mask & (1 << 9)) == 0;

            // Middle: seats 4,5,6,7
            boolean middle = (mask & (1 << 4)) == 0 &&
                             (mask & (1 << 5)) == 0 &&
                             (mask & (1 << 6)) == 0 &&
                             (mask & (1 << 7)) == 0;

            if (left && right) {
                answer += 2;
            } else if (left || middle || right) {
                answer += 1;
            }
        }

        return answer;
    }
}