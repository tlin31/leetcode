112. Path Sum - Easy


Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up
all the values along the path equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \      \
7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.


******************************************************
key:
	- recursion or stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- The basic idea is to subtract the value of current node from sum until it reaches a leaf 
	  node and the subtraction equals 0, then we know that we got a hit. 
	  Otherwise the subtraction at the end could not be 0.



public class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;
    
    	// know this is a leaf
        if(root.left == null && root.right == null && sum - root.val == 0) 
        	return true;
    
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }
}









~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	- the idea is preorder traverse , instead of using recursive call, I am using a stack.




public boolean hasPathSum(TreeNode root, int sum) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Stack<Integer> sums = new Stack<Integer>();

        stack.push(root);
        sums.push(sum);

        while(!stack.isEmpty()&&(root!=null)){
            int value = sums.pop();
            TreeNode top = stack.pop();
            
            if(top.left==null && top.right==null && top.val==value){
                return true;
            }

            if(top.right!=null){
                stack.push(top.right);
                sums.push(value-top.val);
            }

            if(top.left!=null){
                stack.push(top.left);
                sums.push(value-top.val);
            }

        }
        return false;
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

