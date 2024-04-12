/**
Deletion:https://www.geeksforgeeks.org/binary-search-tree-set-2-delete/, 
& leetcode #450 https://leetcode.com/problems/delete-node-in-a-bst/
1) Node to be deleted is leaf: Simply remove from the tree.
2) Node to be deleted has only one child: Copy the child to the node and delete the child
3) Node to be deleted has two children: Find inorder successor of the node. Copy contents of the inorder successor 
o the node and delete the inorder successor. --> find left most child
TreeNode x=root.right; // root.right should be the new root
 while (x.left!=null) x=x.left; // find the left-most node
  x.left=root.left;
  return root.right;
**/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root==null || root.val==key) return deleteRoot(root);
        TreeNode p=root;
        
        while (true) { // search the node
            if (key>p.val) {
                if (p.right==null || p.right.val==key) {
                    p.right=deleteRoot(p.right);
                    break;
                }
                p=p.right;
            }
            else {
                if (p.left==null || p.left.val==key) {
                    p.left=deleteRoot(p.left);
                    break;
                }
                p=p.left;
            }
        }
        return root;
    }

    private TreeNode deleteRoot(TreeNode root) {
        if (root==null) return null;
        if (root.right==null) return root.left;
        TreeNode x=root.right; // root.right should be the new root
        while (x.left!=null) x=x.left; // find the left-most node
        x.left=root.left;
        return root.right;
    }
}