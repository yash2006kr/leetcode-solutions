class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] sCount = new int[26];
        for (char c : s.toCharArray()) sCount[c - 'a']++;

        for (int i = n - 1; i >= 0; i--) {
            int[] avail = sCount.clone();
            boolean feasible = true;

            for (int j = 0; j < i; j++) {
                int idx = target.charAt(j) - 'a';
                if (--avail[idx] < 0) {
                    feasible = false;
                    break;
                }
            }
            if (!feasible) continue;

            int tIdx = target.charAt(i) - 'a';
            int chosen = -1;
            for (int c = tIdx + 1; c < 26; c++) {
                if (avail[c] > 0) {
                    chosen = c;
                    break;
                }
            }
            if (chosen == -1) continue;

            avail[chosen]--;

            StringBuilder sb = new StringBuilder();
            sb.append(target, 0, i);
            sb.append((char) ('a' + chosen));
            for (int c = 0; c < 26; c++) {
                for (int k = 0; k < avail[c]; k++) sb.append((char) ('a' + c));
            }
            return sb.toString();
        }

        return "";
    }
}