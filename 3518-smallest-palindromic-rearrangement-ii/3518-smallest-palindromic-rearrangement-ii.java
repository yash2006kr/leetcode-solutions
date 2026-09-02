class Solution {
    private static final long LIMIT = 1_000_001;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        int[] half = new int[26];
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;

            if (freq[i] % 2 == 1) {
                middle = (char) ('a' + i);
            }
        }

        // Total number of distinct permutations of the left half
        long totalWays = countWays(half, k);

        if (totalWays < k) {
            return "";
        }

        int n = s.length();
        int halfLen = n / 2;

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0) {
                    continue;
                }

                // Try putting this character here
                half[c]--;

                long ways = countWays(half, k);

                if (ways >= k) {
                    // This character contains the k-th answer
                    left.append((char) ('a' + c));
                    break;
                }

                // Skip all permutations starting with this character
                k -= ways;
                half[c]++;
            }
        }

        // Build palindrome
        StringBuilder ans = new StringBuilder();

        ans.append(left);

        if (middle != 0) {
            ans.append(middle);
        }

        ans.append(left.reverse());

        return ans.toString();
    }

    // Number of distinct permutations of the remaining characters.
    // We only care whether the answer reaches k, so we cap it.
    private long countWays(int[] count, int k) {
        int total = 0;

        for (int x : count) {
            total += x;
        }

        long ways = 1;

        // Multinomial:
        // total! / (c1! * c2! * ...)
        //
        // Build it as:
        // C(total, c1) * C(total-c1, c2) * ...
        for (int c : count) {
            if (c == 0) {
                continue;
            }

            ways *= combinationCapped(total, c, k);

            if (ways >= LIMIT) {
                return LIMIT;
            }

            total -= c;
        }

        return ways;
    }

    private long combinationCapped(int n, int r, int limit) {
        r = Math.min(r, n - r);

        long result = 1;

        for (int i = 1; i <= r; i++) {
            result = result * (n - i + 1) / i;

            if (result >= limit) {
                return limit;
            }
        }

        return result;
    }
}