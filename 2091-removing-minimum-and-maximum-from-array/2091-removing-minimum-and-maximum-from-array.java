class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;
        
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        
        int lo = Math.min(minIdx, maxIdx);
        int hi = Math.max(minIdx, maxIdx);
        
        // Option 1: remove both from the left (up to hi+1 elements)
        int fromLeft = hi + 1;
        
        // Option 2: remove both from the right (up to n - lo elements)
        int fromRight = n - lo;
        
        // Option 3: remove one from left (lo+1) and one from right (n-hi)
        int fromBoth = (lo + 1) + (n - hi);
        
        return Math.min(fromLeft, Math.min(fromRight, fromBoth));
    }
}