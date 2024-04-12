1372. Longest ZigZag Path in a Binary Tree - Medium


Given a binary tree root, a ZigZag path for a binary tree is defined as follow:

	1. Choose any node in the binary tree and a direction (right or left).
	2. If the current direction is right then move to the right child of the current node otherwise 
	   move to the left child.
	3. Change the direction from right to left or right to left.
	4. Repeat the second and third step until you cant move in the tree.

Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).

Return the longest ZigZag path contained in that tree.

 

Example 1:

Input: root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
Output: 3
Explanation: Longest ZigZag path in blue nodes (right -> left -> right).


Example 2:

Input: root = [1,1,1,null,1,null,null,1,1,null,1]
Output: 4
Explanation: Longest ZigZag path in blue nodes (left -> right -> left -> right).

Example 3:

Input: root = [1]
Output: 0
 

Constraints:

Each tree has at most 50000 nodes..
Each node's value is between [1, 100].



******************************************************
key:
	- DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N)
	- Space O(height)



Method:

	- Recursive return [left, right, result], where:
		left is the maximum length in direction of root.left
		right is the maximum length in direction of root.right
		result is the maximum length in the whole sub tree.


    public int longestZigZag(TreeNode root) {
    	// dfs return [left max length, right max length, result]
        return dfs(root)[2];
    }

    private int[] dfs(TreeNode root) {
        if (root == null) 
        	return new int[]{-1, -1, -1};

        int[] left = dfs(root.left), 
        	  right = dfs(root.right);

       	// left[1] --> left, and then right
       	// right[0] --> right, then left
        int res = Math.max(Math.max(left[1], right[0]) + 1, Math.max(left[2], right[2]));
        
        return new int[]{left[1] + 1, right[0] + 1, res};
    }






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def longestZigZag(self, root):
        def dfs(root):
            if not root: return [-1, -1, -1]
            left, right = dfs(root.left), dfs(root.right)
            return [left[1] + 1, right[0] + 1, max(left[1] + 1, right[0] + 1, left[2], right[2])]
        return dfs(root)[-1]

=======================================================================================================
method 2:

Stats:

	- Time: O(n), it will visit all the nodes and each node once.
	- Space: O(h)



Method:

	-	
	-	


class Solution {
     int max=0;
    public int longestZigZag(TreeNode root) {
        dfs(root.left,1,1);
        dfs(root.right,-1,1);
        return max;
    }

    // left: dir = 1, right: dir = -1
    public void dfs(TreeNode root,int dir,int height)
    {
        if(root==null)
            return;

        max = Math.max(max,height);

        if(dir==1) {
            dfs(root.left,1,1);
            dfs(root.right,-1,height+1);
        }
        else {
            dfs(root.right,-1,1);
            dfs(root.left,1,height+1);            
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
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

