class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] sCount = new int[26];
        for (char c : s.toCharArray()) sCount[c - 'a']++;

        // Palindrome feasibility check
        int oddCount = 0, oddChar = -1;
        for (int c = 0; c < 26; c++) {
            if (sCount[c] % 2 != 0) { oddCount++; oddChar = c; }
        }
        if (n % 2 == 0) {
            if (oddCount != 0) return "";
        } else {
            if (oddCount != 1) return "";
        }

        int h = n / 2;
        int[] halfCounts = new int[26];
        for (int c = 0; c < 26; c++) halfCounts[c] = sCount[c] / 2;
        char midChar = (n % 2 == 1) ? (char) ('a' + oddChar) : 0;

        // --- Try full prefix match: P == target[0:h] ---
        int[] prefixFreq = new int[26];
        for (int i = 0; i < h; i++) prefixFreq[target.charAt(i) - 'a']++;
        boolean fullMatch = true;
        for (int c = 0; c < 26; c++) {
            if (prefixFreq[c] != halfCounts[c]) { fullMatch = false; break; }
        }

        if (fullMatch) {
            String P = target.substring(0, h);
            String reverseP = new StringBuilder(P).reverse().toString();
            if (n % 2 == 1) {
                char tMid = target.charAt(h);
                if (midChar > tMid) {
                    return P + midChar + reverseP;
                } else if (midChar == tMid) {
                    String suffix = target.substring(h + 1);
                    if (reverseP.compareTo(suffix) > 0) return P + midChar + reverseP;
                }
            } else {
                String suffix = target.substring(h);
                if (reverseP.compareTo(suffix) > 0) return P + reverseP;
            }
        }

        // --- Divergence search within the free first half ---
        for (int d = h - 1; d >= 0; d--) {
            int[] avail = halfCounts.clone();
            boolean feasible = true;
            for (int j = 0; j < d; j++) {
                int idx = target.charAt(j) - 'a';
                if (--avail[idx] < 0) { feasible = false; break; }
            }
            if (!feasible) continue;

            int tIdx = target.charAt(d) - 'a';
            int chosen = -1;
            for (int c = tIdx + 1; c < 26; c++) {
                if (avail[c] > 0) { chosen = c; break; }
            }
            if (chosen == -1) continue;

            avail[chosen]--;
            StringBuilder pb = new StringBuilder();
            pb.append(target, 0, d);
            pb.append((char) ('a' + chosen));
            for (int c = 0; c < 26; c++)
                for (int k = 0; k < avail[c]; k++) pb.append((char) ('a' + c));

            String P = pb.toString();
            String reverseP = new StringBuilder(P).reverse().toString();
            return (n % 2 == 1) ? P + midChar + reverseP : P + reverseP;
        }

        return "";
    }
}