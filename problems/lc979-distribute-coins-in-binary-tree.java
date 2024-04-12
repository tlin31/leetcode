979. Distribute Coins in Binary Tree - Medium


Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there 
are N coins total.

In one move, we may choose two adjacent nodes and move one coin from one node to another.  (The move 
may be from parent to child, or from child to parent.)

Return the number of moves required to make every node have exactly one coin.

 

Example 1:

Input: [3,0,0]
Output: 2
Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right 
child.



Example 2:

Input: [0,3,0]
Output: 3
Explanation: From the left child of the root, we move two coins to the root [taking two moves].  
Then, we move one coin from the root of the tree to the right child.


Example 3:

Input: [1,0,2]
Output: 2
Example 4:



Input: [1,0,0,null,3]
Output: 4
 

Note:

1<= N <= 100
0 <= node.val <= N


******************************************************
key:
	- Post order
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

	-	Each coin (excessive or needed) need one step to be sent to the parent node.

	-  Helper function returns the amount of coins each node need or have excessively. For each node, 
	   it will try to balance the amount of the coins used by its left child and right child.

	-  it will return a positive number if there is excessive coins which could be used by its parent
	   node, or a negative number if current node or its children need coins.

Class Solution{
	private int steps = 0; 

	public int distributeCoins(TreeNode root) {
	    postorder(root);
	    return steps;
	}

	// return coins of this level
	private int postorder(TreeNode node) {
	    if(node == null) return 0;
	    
	    // coin from children -- post-order traversal
	    int coin = postorder(node.left) + postorder(node.right);
		
	    // current node coin, keep one coin for current node
	    coin += node.val-1; 
	    
	    steps += Math.abs(coin); // each coin move up to parent node need 1 step
	    
	    return coin; 
	}
}

-------------------------

class Solution {
    int ans;
    public int distributeCoins(TreeNode root) {
        distribute(root);
        return ans;
    }
    private int distribute(TreeNode node){
        if(node == null) {
            return 0;
        }
        int left = distribute(node.left);
        int right = distribute(node.right);
        ans += Math.abs(left) + Math.abs(right);
        int diff = node.val + left + right -1
        return diff; 
    }
}


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



