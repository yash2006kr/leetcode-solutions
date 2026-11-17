class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        
        // cost[i][j] stores the minimum changes to make substring s[i..j] a semi-palindrome
        int[][] cost = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int minChanges = len; // at worst, we can change all characters
                
                // Check all proper divisors of len
                for (int d = 1; d * d <= len; d++) {
                    if (len % d == 0) {
                        // divisor d
                        minChanges = Math.min(minChanges, getChanges(s, i, j, d));
                        // divisor len / d
                        if (d * d != len && d > 1) {
                            minChanges = Math.min(minChanges, getChanges(s, i, j, len / d));
                        }
                    }
                }
                cost[i][j] = minChanges;
            }
        }
        
        // dp[i][parts] = min changes to partition suffix s[i..n-1] into 'parts' semi-palindromes
        int[][] dp = new int[n + 1][k + 1];
        final int INF = 1_000_000;
        
        for (int i = 0; i <= n; i++) {
            for (int p = 0; p <= k; p++) {
                dp[i][p] = INF;
            }
        }
        
        dp[0][0] = 0;
        
        for (int parts = 1; parts <= k; parts++) {
            for (int i = 0; i < n; i++) {
                if (dp[i][parts - 1] == INF) continue;
                
                // Try all valid next substring endpoints j starting from i + 1
                // Each semi-palindrome must have length at least 2
                for (int j = i + 1; j < n; j++) {
                    // remaining length check: we need enough characters for remaining parts
                    int remainingParts = k - parts;
                    int remainingLen = n - (j + 1);
                    if (remainingLen < remainingParts * 2) {
                        break; 
                    }
                    
                    dp[j + 1][parts] = Math.min(dp[j + 1][parts], dp[i][parts - 1] + cost[i][j]);
                }
            }
        }
        
        return dp[n][k];
    }
    
    // Helper to calculate changes needed for s[i..j] with a fixed divisor d
    private int getChanges(String s, int start, int end, int d) {
        int changes = 0;
        // There are 'd' groups, starting at offset 0, 1, ..., d-1
        for (int offset = 0; offset < d; offset++) {
            int l = start + offset;
            int r = end - ((end - l) % d); // rightmost element in the same group
            
            while (l < r) {
                if (s.charAt(l) != s.charAt(r)) {
                    changes++;
                }
                l += d;
                r -= d;
            }
        }
        return changes;
    }
}