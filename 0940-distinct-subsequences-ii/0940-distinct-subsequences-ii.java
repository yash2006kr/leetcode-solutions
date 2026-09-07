class Solution {
    public int distinctSubseqII(String s) {
        final int MOD = 1_000_000_007;
        long[] last = new long[26]; // last[c] = dp value just before processing an occurrence of c
        long dp = 1; // dp counts subsequences including the empty one

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            long newDp = (2 * dp % MOD - last[c] + MOD) % MOD;
            last[c] = dp;      // store dp *before* this update
            dp = newDp;
        }

        // subtract 1 for the empty subsequence
        return (int) ((dp - 1 + MOD) % MOD);
    }
}