class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        
        // prefixMax[i] = max(nums[0..i])
        int[] prefixMax = new int[n];
        prefixMax[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }
        
        // suffixMin[i] = min(nums[i..n-1])
        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }
        
        for (int i = 0; i < n; i++) {
            long score = (long) prefixMax[i] - suffixMin[i];
            if (score <= k) {
                return i;
            }
        }
        
        return -1;
    }
}