class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        // build prefix sums in place
        for (int i = 1; i < n; i++) {
            stones[i] += stones[i - 1];
        }
        
        int dp = stones[n - 1]; // dp for index n-2 conceptually; start value
        for (int i = n - 2; i >= 1; i--) {
            dp = Math.max(dp, stones[i] - dp);
        }
        
        return dp;
    }
}