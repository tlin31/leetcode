226. Invert Binary Tree - Easy

Invert a binary tree.

Example:

Input:

     4
   /   \
  2     7
 / \   / \
1   3 6   9
Output:

     4
   /   \ 
  7     2
 / \   / \
9   6 3   1

Trivia:
This problem was inspired by this original tweet by Max Howell:
Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary 
tree on a whiteboard so f*** off.


******************************************************
key:
	- stack, dfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- recursive DFS 
	- 

stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
	- Memory Usage: 34.5 MB, less than 100.00%


	public TreeNode invertTree(TreeNode root) {
	    if (root == null) {
	        return null;
	    }
	    final TreeNode left = root.left,
	        		   right = root.right;
	    root.left = invertTree(right);
	    root.right = invertTree(left);
	    return root;
	}



=======================================================================================================
method 2:

method:

	- stack
	- 

stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
	- Memory Usage: 34 MB, less than 100.00%

	public TreeNode invertTree(TreeNode root) {
	    if (root == null) {
	        return null;
	    }
	    final Deque < TreeNode > stack = new LinkedList < > ();
	    stack.push(root);

	    while (!stack.isEmpty()) {
	        final TreeNode node = stack.pop();

	        //first store left
	        final TreeNode leftTemp = node.left;

	        //switch left and right
	        node.left = node.right;
	        node.right = leftTemp;

	        if (node.left != null) {
	            stack.push(node.left);
	        }
	        if (node.right != null) {
	            stack.push(node.right);
	        }
	    }
	    return root;
	}

=======================================================================================================
method 3:

method:

	- BFS
	- 

stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Invert Binary Tree.
	- Memory Usage: 34.1 MB, less than 100.00%


	public TreeNode invertTree(TreeNode root) {
	    if (root == null) {
	        return null;
	    }
	    final Queue < TreeNode > queue = new LinkedList < > ();

	    // add root to the queue
	    queue.offer(root);

	    while (!queue.isEmpty()) {
	    	// get the head out 
	        final TreeNode node = queue.poll();
	        final TreeNode leftTemp = node.left;
	        node.left = node.right;
	        node.right = leftTemp;
	        if (node.left != null) {
	            queue.offer(node.left);
	        }
	        if (node.right != null) {
	            queue.offer(node.right);
	        }
	    }
	    return root;
	}

