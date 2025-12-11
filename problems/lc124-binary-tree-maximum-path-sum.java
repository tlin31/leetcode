124. Binary Tree Maximum Path Sum - Hard

Given a non-empty binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the 
tree along the parent-child connections. The path must contain at least one node and does not need to go 
through the root.

Example 1:

Input: [1,2,3]

       1
      / \
     2   3

Output: 6
Example 2:

Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************


后序遍历

int ans = INT_MIN;
int oneSideMax(TreeNode* root) {
    if (root == null) return 0;
    int left = max(0, oneSideMax(root->left));
    int right = max(0, oneSideMax(root->right));
    ans = max(ans, left + right + root->val);
    return max(left, right) + root->val;
}


=======================================================================================================
method 1:

method:
	- 	Possible solution: 
			1) pick root 
			2) pick left branch 
			3) pick right branch 
			4) pick branch go through root with positive left branch and right branch

	- 	A path from start to end, goes up on the tree for 0 or more steps, then goes down for 0 
        or more steps. 

		Once it goes down, it can not go up. Each path has a highest node, which is also the lowest 
        common ancestor of all other nodes on the path.

	- 	A recursive method maxPathDown(TreeNode node):
		(1) computes the maximum path sum with highest node is the input node, update maximum if 
            necessary
		(2) returns the maximum sum of the path that can be extended to input nodes parent.

	- 	maxValue = Math.max(maxValue, left + right + node.val);
		括号里的maxValue contains the bigger between the left sub-tree and right sub-tree.
		if (left + right + node.val < maxValue )： 
			then the maximum path is in the left branch or right branch.

stats:

	- Time complexity : O(N) where N is number of nodes, since we visit each node not more than 2 times.
	- Space complexity : O(log(N)). We have to keep a recursion stack of the size of the tree height, 
						which is O(log(N)) for the binary tree.



// The basic idea is to traversal every nodes as the top of sub tree and calculate left max and 
// right max individually, then keep update max. 

public class Solution {

	// global max value (with highest node being the input node)
    int maxValue;
    
    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxValue;
    }
    
    private int maxPathDown(TreeNode node) {
        if (node == null) return 0;

        // only want to add positive numbers!!
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        maxValue = Math.max(maxValue, left + right + node.val);

        // reutrn maximum sum of this subtree --> only can select 1 path, because this node 
        // is not the final root (as a part of other function's call)
        return Math.max(left, right) + node.val;
    }
}

-----------------------------------------------------------------------------

public class BinaryTreeMaximumPathSum {
    private int maxSum;

    public int maxSumHelper(TreeNode root) {
		
		// base case
        if (root == null) return 0; 
		
		// recursing through left and right subtree
        int leftMax = maxSumHelper(root.left);
        int rightMax = maxSumHelper(root.right);

		// finding all the four paths and the maximum between all of them
        int maxRightLeft = Math.max(leftMax, rightMax);
        int maxOneNodeRoot = Math.max(root.val, (root.val + maxRightLeft));
        int maxAll = Math.max(maxOneNodeRoot, leftMax + rightMax + root.val);
		
		// Storing the result in the maxSum holder
        maxSum = Math.max(maxSum, maxAll);
		
		// returning the value if root was part of the answer
        return maxOneNodeRoot;

    }

    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxSumHelper(root);
        return maxSum; // as maxSum will always store the result

    }
}
=======================================================================================================
method 2:

method:

	- 讨论：这道题有一个很好的 Follow up，就是返回这个最大路径，那么就复杂很多，因为这样递归函数就不能返回路径和了，
	  而是返回该路径上所有的结点组成的数组，递归的参数还要保留最大路径之和，同时还需要最大路径结点的数组，然后对左右子节点调
	  用递归函数后得到的是数组，要统计出数组之和，并且跟0比较，

	  如果小于0，和清零，数组清空。

	  然后就是更新最大路径之和跟数组啦，还要拼出来返回值数组


	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



