543. Diameter of Binary Tree - Easy

Given a binary tree, you need to compute the length of the diameter of the tree. The diameter 
of a binary tree is the length of the longest path between any two nodes in a tree. This path 
may or may not pass through the root.

Example:
Given a binary tree
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.


******************************************************
key:
	- traverse, recursion
	- bit wired, not well defined in binary tree structure?
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Any path can be written as two arrows (in different directions) from some node, where an 
	    arrow is a path that starts at some node and only travels down to child nodes.

	-  If we knew the maximum length arrows L, R for each child, then the best path touches 
	   L + R + 1 nodes.

	-	Calculate: max(depth of node.left, depth of node.right) + 1.
		While we do, a path "through" this node uses 1 + (depth of node.left) + (depth of node.right)
		nodes. 
		Let's search each node and remember the highest number of nodes used in some path. 
		The desired length is 1 minus this number.


Stats:

	- Time & space: O(n)
	- 

class Solution {
	// global ans
    int ans;

    public int diameterOfBinaryTree(TreeNode root) {
        ans = 1;
        depth(root);
        return ans - 1;
    }
    public int depth(TreeNode node) {
        if (node == null) 
        	return 0;
        int L = depth(node.left);
        int R = depth(node.right);

        ans = Math.max(ans, L+R+1);

        return Math.max(L, R) + 1;
    }
}


=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



