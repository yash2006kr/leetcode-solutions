import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maximumLengthSubstring(String s) {
        Map<Character, Integer> cnt = new HashMap<>();
        int ans = 0, i = 0;

        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            cnt.put(c, cnt.getOrDefault(c, 0) + 1);

            while (cnt.get(c) > 2) {
                char left = s.charAt(i);
                cnt.put(left, cnt.get(left) - 1);
                i++;
            }
            ans = Math.max(ans, j - i + 1);
        }
        return ans;
    }
}