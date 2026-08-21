class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int minCoin = Integer.MAX_VALUE;
        for (int c : coins) minCoin = Math.min(minCoin, c);

        long lo = 1, hi = (long) k * minCoin;

        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countMultiples(mid, coins) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private long countMultiples(long X, int[] coins) {
        int n = coins.length;
        long count = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    lcm = lcm(lcm, coins[i]);
                    if (lcm > X) { overflow = true; break; }
                }
            }
            if (overflow) continue;

            int bits = Integer.bitCount(mask);
            if (bits % 2 == 1) count += X / lcm;
            else count -= X / lcm;
        }
        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}