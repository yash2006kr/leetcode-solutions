import java.util.*;

class Solution {
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);
        
        for (int i = 1; ; i++) {
            int candidate = k * i;
            if (!set.contains(candidate)) {
                return candidate;
            }
        }
    }
}