class Solution {
    public int longestSubsequence(int[] nums) {
        int xor = 0;
        boolean allZero = true;
        int n = nums.length;

        for (int x : nums) {
            xor ^= x;
            if (x != 0) allZero = false;
        }

        if (allZero) return 0;
        return xor != 0 ? n : n - 1;
    }
}