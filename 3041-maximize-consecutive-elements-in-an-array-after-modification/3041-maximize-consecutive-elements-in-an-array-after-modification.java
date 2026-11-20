import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        
        // dp maps a value `v` to the max length of a consecutive sequence ending at `v`
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;
        
        for (int num : nums) {
            // Update (num + 1) first so we don't chain the same element twice
            int lenPlusOne = dp.getOrDefault(num, 0) + 1;
            int lenCurrent = dp.getOrDefault(num - 1, 0) + 1;
            
            dp.put(num + 1, Math.max(dp.getOrDefault(num + 1, 0), lenPlusOne));
            dp.put(num, Math.max(dp.getOrDefault(num, 0), lenCurrent));
            
            maxLen = Math.max(maxLen, Math.max(dp.get(num), dp.get(num + 1)));
        }
        
        return maxLen;
    }
}