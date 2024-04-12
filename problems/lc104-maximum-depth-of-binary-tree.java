104. Maximum Depth of Binary Tree - Easy

Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down 
to the farthest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
return its depth = 3.

******************************************************
key:
	- dfs
	- if store in the array, left kid = 2n+1, right kid = 2n+2
	- edge case:
		1) empty array --> 0
		2) if = 1, length = 1

******************************************************

int max = 0;

main function {
	dfs(root);

}

int dfs (root){
	if (root == null) return 0;
	int count = 1
	int left =  dfs(root.left);
	int right = dfs(root.left);
	max = Math.max(max, 1+Math.max(left, right))
}


=======================================================================================================
method 1:

method:

	- recursion
	- backtrack also

stats:

	- 
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Maximum Depth of Binary Tree.
	- Memory Usage: 39.3 MB, less than 93.01%

	public int maxDepth(TreeNode root) {
	    if (root == null) {
	        return 0;
	    }
	    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
	}



=======================================================================================================
method 2:

method:

	- iterative
	- use the stack

stats:

	- 
	- 
	// iteration method
	public int maxDepth(TreeNode root) {
	    int max = 0;
	    if (root == null) return 0;

	    Stack<TreeNode> path = new Stack<>();
	    Stack<Integer> sub = new Stack<>();
	    path.push(root);
	    sub.push(1);
	    while (!path.isEmpty()) {
	        TreeNode temp = path.pop();
	        int tempVal = sub.pop();

	        // reach the leaf node
	        if (temp.left == null && temp.right == null) {
	        	max = Math.max(max, tempVal);
	        }
	        else {
	            if (temp.left != null) {
	                path.push(temp.left);
	                sub.push(tempVal + 1);
	            }
	            if (temp.right != null) {
	                path.push(temp.right);
	                sub. push(tempVal + 1);
	            }
	        }
	    }
	    return max;
	}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



