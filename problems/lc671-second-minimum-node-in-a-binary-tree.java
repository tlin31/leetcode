671. Second Minimum Node In a Binary Tree - Easy


Given a non-empty special binary tree consisting of nodes with the non-negative value, where each 
node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this nodes 
value is the smaller value among its two sub-nodes. More formally, the property 
root.val = min(root.left.val, root.right.val) always holds.

Given such a binary tree, you need to output the second minimum value in the set made of all the 
nodes value in the whole tree.

If no such second minimum value exists, output -1 instead.

Example 1:

Input: 
    2
   / \
  2   5
     / \
    5   7

Output: 5
Explanation: The smallest value is 2, the second smallest value is 5.
 

Example 2:

Input: 
    2
   / \
  2   2

Output: -1
Explanation: The smallest value is 2, but there isnt any second smallest value.


******************************************************
key:
	- recursino or bfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: BFS


Stats:

	- 
	- 


Method:

	-	
	-	


public int findSecondMinimumValue(TreeNode root) {
	if(root==null || root.right==null || root.left==null) 
		return -1;

	Queue<TreeNode> q = new LinkedList<>();
	q.offer(root);
	Integer secondMin = null;
	while(!q.isEmpty()) {
		TreeNode curr = q.poll();
		if(curr.right!=null) q.offer(curr.right);
		if(curr.left!=null) q.offer(curr.left);

		if(curr.val!=root.val) {
			if(secondMin==null) 
				secondMin = curr.val;
			else 
				secondMin = Math.min(secondMin, curr.val);
		}
	}
	return secondMin==null? -1 : secondMin;
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: recursion

Stats:

	- 
	- 


Method:

	-	
	-	


public int findSecondMinimumValue(TreeNode root) {
    // tree is not empty
    // root != null
    
    // each node in this tree has exactly two or zero sub-node
    if (root.left == null) {
        return -1;
    }
    
    int left = -1;
    if (root.val != root.left.val) {
        // find a value bigger than root.val
        left = root.left.val;
    } else {
        left = findSecondMinimumValue(root.left);
    }
    
    int right = -1;
    if (root.val != root.right.val) {
        right = root.right.val;
    } else {
        right = findSecondMinimumValue(root.right);
    }
    
    if (left == -1 || right == -1) {
        // the bigger value is left or right, or it not exist
        return Math.max(left, right);
    } else {
        // the min of left and right is what we want
        return Math.min(left, right);
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

