class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int x : stones) {
            cnt[x % 3]++;
        }

        // Alice must start with remainder 1 or 2.
        if (cnt[1] == 0 && cnt[2] == 0) {
            return false;
        }

        // If cnt[0] is even, Alice can win when both
        // remainder groups are available.
        if (cnt[0] % 2 == 0) {
            return cnt[1] > 0 && cnt[2] > 0;
        }

        // If cnt[0] is odd, one remainder group must have
        // at least two more stones than the other.
        return Math.abs(cnt[1] - cnt[2]) > 2;
    }
}