class Solution {
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        if (n > m) return 0;

        // dp[j] represents dp[i][j] for current i, rolled into 1D array
        long[] dp = new long[n + 1];
        dp[0] = 1; // empty t is always a valid subsequence

        for (int i = 1; i <= m; i++) {
            char sc = s.charAt(i - 1);
            // iterate j backwards so dp[j-1] still refers to previous row's value
            for (int j = n; j >= 1; j--) {
                char tc = t.charAt(j - 1);
                if (sc == tc) {
                    dp[j] += dp[j - 1];
                }
                // else dp[j] stays the same (equivalent to dp[i-1][j])
            }
        }

        return (int) dp[n];
    }
}