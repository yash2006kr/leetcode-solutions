import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        int[] sq = new int[n];
        for (int i = 0; i < n; i++) {
            sq[i] = nums[i] * nums[i]; // max 1.6e9, fits in int
        }
        Arrays.sort(sq);

        long result = 0;
        for (int i = 0; i < n; i++) {
            if (i < n / 2) result -= sq[i];   // smallest half is subtracted
            else result += sq[i];             // larger half is added
        }
        return result;
    }
}