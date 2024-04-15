129. Sum Root to Leaf Numbers - Medium


Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

An example is the root-to-leaf path 1->2->3 which represents the number 123.

Find the total sum of all root-to-leaf numbers.

Note: A leaf is a node with no children.

Example:

Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.


Example 2:

Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.

******************************************************
key:
- 
- edge case:
	1) empty string, return empty
	2) empty node?
	3) only 1 element
	4) no element

******************************************************



=======================================================================================================
Method 1:Iterative Preorder Traversal.


Stats:

	- Time complexity: O(N) since one has to visit each node.

	- Space complexity: up to O(H) to keep the stack, where H is a tree height.



Method:

	- Here we implement standard iterative preorder traversal with the stack:

		Push root into stack.

		While stack is not empty:

			Pop out a node from stack and update the current number.

			If the node is a leaf, update root-to-leaf sum.

			Push right and left child nodes into stack.

		Return root-to-leaf sum.



class Solution {
	  public int sumNumbers(TreeNode root) {
	    int rootToLeaf = 0, 
	        currNumber = 0;
	    Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque();
	    stack.push(new Pair(root, 0));

	    while (!stack.isEmpty()) {
	      Pair<TreeNode, Integer> p = stack.pop();
	      root = p.getKey();
	      currNumber = p.getValue();

	      if (root != null) {
	        currNumber = currNumber * 10 + root.val;

	        // if it's a leaf, update root-to-leaf sum
	        if (root.left == null && root.right == null) {
	          rootToLeaf += currNumber;
	        } 

	        else {
	          stack.push(new Pair(root.right, currNumber));
	          stack.push(new Pair(root.left, currNumber));
	        }
	      }
	    }
	    return rootToLeaf;
	  }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution:
    def sumNumbers(self, root: TreeNode):
        root_to_leaf = 0
        stack = [(root, 0) ]
        
        while stack:
            root, curr_number = stack.pop()
            if root is not None:
                curr_number = curr_number * 10 + root.val
                # if it's a leaf, update root-to-leaf sum
                if root.left is None and root.right is None:
                    root_to_leaf += curr_number
                else:
                    stack.append((root.right, curr_number))
                    stack.append((root.left, curr_number))
                        
        return root_to_leaf
=======================================================================================================
method 2:Recursive Preorder Traversal.

Stats:

	- 
	- 


Method:

	- follow Root->Left->Right direction, i.e. do all the business with the node (= update the current 
	  number and root-to-leaf sum), and then do the recursive calls for the left and right child nodes.



class Solution {
  int rootToLeaf = 0;

  public int sumNumbers(TreeNode root) {
    preorder(root, 0);
    return rootToLeaf;
  }

  public void preorder(TreeNode r, int currNumber) {
    if (r != null) {
      currNumber = currNumber * 10 + r.val;

      // if it's a leaf, update root-to-leaf sum
      if (r.left == null && r.right == null) {
        rootToLeaf += currNumber;
      }
      
      preorder(r.left, currNumber);
      preorder(r.right, currNumber) ;
    }
  }


}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

