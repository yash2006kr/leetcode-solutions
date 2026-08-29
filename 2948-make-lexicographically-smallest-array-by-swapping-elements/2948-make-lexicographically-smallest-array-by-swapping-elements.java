import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        
        // sort indices by value
        Arrays.sort(idx, (a, b) -> nums[a] - nums[b]);
        
        int[] result = new int[n];
        int i = 0;
        
        while (i < n) {
            int j = i;
            // extend group while consecutive sorted values are within limit
            while (j + 1 < n && nums[idx[j + 1]] - nums[idx[j]] <= limit) {
                j++;
            }
            
            // group is idx[i..j] -> values are already sorted (since idx sorted by value)
            // collect original indices in this group, sort them
            Integer[] originalIndices = Arrays.copyOfRange(idx, i, j + 1);
            Arrays.sort(originalIndices);
            
            // assign sorted values to sorted original positions
            for (int k = 0; k < originalIndices.length; k++) {
                result[originalIndices[k]] = nums[idx[i + k]];
            }
            
            i = j + 1;
        }
        
        return result;
    }
}