class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int k = nums[0];
        int best = Integer.MAX_VALUE;

        // Case 1: ascending cyclic rotation
        boolean asc = true;
        for (int i = 0; i < n && asc; i++) {
            if (nums[i] != (k + i) % n) asc = false;
        }
        if (asc) {
            best = (k == 0) ? 0 : Math.min(n - k, k + 2);
        }

        // Case 2: descending cyclic rotation
        boolean desc = true;
        for (int i = 0; i < n && desc; i++) {
            if (nums[i] != (((k - i) % n) + n) % n) desc = false;
        }
        if (desc) {
            int a = (k + 1) % n;
            int cost = (a == 0) ? 1 : Math.min(a, n - a) + 1;
            best = Math.min(best, cost);
        }

        return best == Integer.MAX_VALUE ? -1 : best;
    }
}