98. Validate Binary Search Tree - Medium


Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:

    2
   / \
  1   3

Input: [2,1,3]
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6

Input: [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.



******************************************************
key:
	- recursion or in-order traversal
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: recursion


Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {

  public boolean isValidBST(TreeNode root) {
    return helper(root, null, null);
  }

  public boolean helper(TreeNode node, Integer lower, Integer upper) {
    if (node == null) return true;

    int val = node.val;
    if (lower != null && val <= lower) return false;
    if (upper != null && val >= upper) return false;

    if (! helper(node.right, val, upper)) return false;
    if (! helper(node.left, lower, val)) return false;
    return true;
  }

}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def isValidBST(self, root):
        """
        :type root: TreeNode
        :rtype: bool
        """
        def helper(node, lower = float('-inf'), upper = float('inf')):
            if not node:
                return True
            
            val = node.val
            if val <= lower or val >= upper:
                return False

            if not helper(node.right, val, upper):
                return False
            if not helper(node.left, lower, val):
                return False
            return True

        return helper(root)

=======================================================================================================
method 2: in order: left, current, right

Stats:

	- 
	- 


Method:

	-	Compute inorder traversal list inorder & Check if each element in inorder is < the next one.
	-  the last added inorder element is enough to ensure at each step that the tree is BST (or not). 
	-  use a tree node pre to record the last node,
		while current root is not null OR still nodes in stack:
			while root is not null:
				add root to the stack and move to its left child

			(now reach the left most child)
			root = top of stack
			check whether this (left) subtree is a BST
			update pre
			move to root right child



public boolean isValidBST(TreeNode root) {
   if (root == null) return true;

   Stack<TreeNode> stack = new Stack<>();
   TreeNode pre = null;

   while (root != null || !stack.isEmpty()) {

   	  
      	while (root != null) {
         	stack.push(root);
         	root = root.left;
      	}
        
      	root = stack.pop();

      	// root need to be larger than pre node, right node need to be > than root
      	if(pre != null && root.val <= pre.val) 
      		return false;
      	pre = root;
      	root = root.right;
   }

   return true;
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def isValidBST(self, root):
        """
        :type root: TreeNode
        :rtype: bool
        """
        stack, inorder = [], float('-inf')
        
        while stack or root:
            while root:
                stack.append(root)
                root = root.left
            root = stack.pop()
            # If next element in inorder traversal
            # is smaller than the previous one
            # that's not BST.
            if root.val <= inorder:
                return False
            inorder = root.val
            root = root.right

        return True
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

