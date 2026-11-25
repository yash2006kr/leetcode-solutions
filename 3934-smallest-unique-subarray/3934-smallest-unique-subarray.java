import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sa = buildSuffixArray(nums);

        // Kasai LCP: lcp[r] = LCP of suffixes sa[r-1] and sa[r]
        int[] rank = new int[n], lcp = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;
        for (int i = 0, h = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = sa[rank[i] - 1];
                while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) h++;
                lcp[rank[i]] = h;
                if (h > 0) h--;
            } else {
                h = 0;
            }
        }

        int ans = n;
        for (int r = 0; r < n; r++) {
            int need = Math.max(lcp[r], r + 1 < n ? lcp[r + 1] : 0) + 1;
            if (sa[r] + need <= n) ans = Math.min(ans, need);
        }
        return ans;
    }

    // Prefix doubling with radix sort: O(n log n)
    private int[] buildSuffixArray(int[] s) {
        int n = s.length, maxV = 0;
        for (int x : s) maxV = Math.max(maxV, x);
        int m = maxV + 1;
        int[] sa = new int[n], sa2 = new int[n], rk = new int[n], nrk = new int[n];
        int[] cnt = new int[Math.max(n, m) + 1];

        for (int i = 0; i < n; i++) { rk[i] = s[i]; cnt[rk[i]]++; }
        for (int i = 1; i < m; i++) cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--) sa[--cnt[rk[i]]] = i;

        for (int k = 1; ; k <<= 1) {
            int p = 0;
            for (int i = Math.max(0, n - k); i < n; i++) sa2[p++] = i;
            for (int j = 0; j < n; j++) if (sa[j] >= k) sa2[p++] = sa[j] - k;

            Arrays.fill(cnt, 0, m, 0);
            for (int i = 0; i < n; i++) cnt[rk[i]]++;
            for (int i = 1; i < m; i++) cnt[i] += cnt[i - 1];
            for (int j = n - 1; j >= 0; j--) sa[--cnt[rk[sa2[j]]]] = sa2[j];

            nrk[sa[0]] = 0;
            int classes = 1;
            for (int j = 1; j < n; j++) {
                int a = sa[j - 1], b = sa[j];
                int ra = a + k < n ? rk[a + k] : -1;
                int rb = b + k < n ? rk[b + k] : -1;
                nrk[b] = (rk[a] == rk[b] && ra == rb) ? classes - 1 : classes++;
            }
            int[] t = rk; rk = nrk; nrk = t;
            m = classes;
            if (classes == n) break;
        }
        return sa;
    }
}