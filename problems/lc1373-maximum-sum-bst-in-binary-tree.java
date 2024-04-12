1373. Maximum Sum BST in Binary Tree - Hard


Given a binary tree root, the task is to return the maximum sum of all keys of any sub-tree which 
is also a Binary Search Tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:

Input: root = [1,4,3,2,4,2,5,null,null,null,null,null,null,4,6]
Output: 20
Explanation: Maximum sum in a valid Binary search tree is obtained in root node with key equal to 3.


Example 2:

Input: root = [4,3,null,1,2]
Output: 2
Explanation: Maximum sum in a valid Binary search tree is obtained in a single root node with key 
equal to 2.


Example 3:

Input: root = [-4,-2,-5]
Output: 0
Explanation: All values are negatives. Return an empty BST.


Example 4:

Input: root = [2,1,3]
Output: 6


Example 5:

Input: root = [5,4,8,3,null,6,3]
Output: 7
 

Constraints:

Each tree has at most 40000 nodes..
Each node value is between [-4 * 10^4 , 4 * 10^4].


******************************************************
key:
	- recursion： 1) check valid tree  2) have a sum
	- edge case:
		1) empty string, return empty
		2)

******************************************************

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

=======================================================================================================
Method 1:


Stats:

	- Time: O(n)
	- Space: O(h), where h is the height of the binary tree



Method:

	-	post order traversal
	-	


class Solution {
    private int maxSum = 0;

    public int maxSumBST(TreeNode root) {
        postOrderTraverse(root);
        return maxSum;
    }

    private int[] postOrderTraverse(TreeNode root) {
    	// {min, max, sum}, initialize: min=MAX_VALUE, max=MIN_VALUE, sum = 0
        if (root == null) 
        	return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}; 

        int[] left = postOrderTraverse(root.left);
        int[] right = postOrderTraverse(root.right);

        // The BST is not a tree:
        if (!( left != null             	// the left subtree must be BST
                && right != null            // the right subtree must be BST
                && root.val > left[1]       // the root's key must > maximum keys of the left subtree
                && root.val < right[0]))    // the root's key must < minimum keys of the right subtree
            
            return null;

        // if left & right is a BST, calculate sum at this point
        int sum = root.val + left[2] + right[2]; 
        maxSum = Math.max(maxSum, sum);
        
        int min = Math.min(root.val, left[0]);
        int max = Math.max(root.val, right[1]);
        return new int[]{min, max, sum};
    }

}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- Time: O(N)
	- Space: O(logN) for function calls, worst case O(N) if the given tree is not balanced



Method:
		
	For each subtree, we return 4 elements.

		1. the status of this subtree, 1 means it is empty, 2 means it's a BST, 0 means it's not a BST
		2. size of this subtree (we only care about size of BST though)
		3. the minimal value in this subtree
		4. the maximal value in this subtree

	Then we only need to make sure for every BST:
		1. both of its children are BST
		2. the right bound of its left child is smaller than root.val
		3. the left bound of its right child is larger than root.val


    def maxSumBST(self, root: TreeNode) -> int:
        res = 0
        def traverse(root):
            '''return status_of_bst, sum of bst, left_bound, right_bound'''

            nonlocal res

            # this subtree is empty
            if not root: 
            	return 1, 0, None, None 
            
            left_status, l_size, l_left, l_right = traverse(root.left)
            right_status, r_size, r_left, r_right = traverse(root.right)
            
            if ((left_status == 2 and l_right < root.val) or left_status == 1) and 
               ((right_status == 2 and r_left > root.val) or right_status == 1):

		        # this subtree is a BST
                sum = root.val + l_size + r_size
                res = max(res, sum)
                return 2, sum, (l_left if l_left is not None else root.val), 
                               (r_right if r_right is not None else root.val)

            return 0, None, None, None # this subtree is not a BST
        
        traverse(root)
        return res









=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

