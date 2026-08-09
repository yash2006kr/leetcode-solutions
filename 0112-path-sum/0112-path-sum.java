class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        // If this is a leaf node
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        // Subtract current node's value and search both subtrees
        int remaining = targetSum - root.val;

        return hasPathSum(root.left, remaining) ||
               hasPathSum(root.right, remaining);
    }
}