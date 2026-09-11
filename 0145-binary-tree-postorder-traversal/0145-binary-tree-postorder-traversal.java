import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }

    private void helper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        helper(node.left, result);  // Traverse Left
        helper(node.right, result); // Traverse Right
        result.add(node.val);       // Visit Root
    }
}
