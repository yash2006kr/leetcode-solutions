import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        // Step 1: Map all key-value pairs for O(1) lookup
        Map<String, String> map = new HashMap<>();
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();
        StringBuilder keyBuilder = new StringBuilder();
        boolean isKey = false;

        // Step 2: Iterate through the string character by character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                isKey = true;
            } else if (c == ')') {
                isKey = false;
                String key = keyBuilder.toString();
                // Replace with mapped value, or "?" if key does not exist
                result.append(map.getOrDefault(key, "?"));
                keyBuilder.setLength(0); // Reset key builder for next key
            } else {
                if (isKey) {
                    keyBuilder.append(c);
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }
}