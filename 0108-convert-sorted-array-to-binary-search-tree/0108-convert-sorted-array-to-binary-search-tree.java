/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums.length==0) return null;
        return bst(nums,0,nums.length-1);
    }
    public TreeNode bst(int[] nums,int start,int end){
        if(start>end) return null;
        int mid = (start+end)/2;
        TreeNode bstree= new TreeNode(nums[mid]);
        bstree.left=bst(nums,start,mid-1);
        bstree.right=bst(nums,mid+1,end);
        return bstree;
    }
}