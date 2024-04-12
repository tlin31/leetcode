222. Count Complete Tree Nodes - Medium

Given a complete binary tree, count the number of nodes.

Note:
Definition of a complete binary tree:
	In a complete binary tree every level, except possibly the last, is completely filled, and all 
	nodes in the last level are as far left as possible. It can have between 1 and 2h nodes 
	inclusive at the last level h.

Example:

Input: 
    1
   / \
  2   3
 / \  /
4  5 6

Output: 6


******************************************************
key:
	- find height, then check where is the last node at (left or right subtree)
	- recursion
	- edge case:
		1) null root, then return 0
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- The height of a tree can be found by just going left. 
	- Let a single node tree have height 0. Find the height h of the whole tree. 
	- Then check whether the height of the right subtree is just one less than that of the whole 
	  tree, meaning left and right subtree have the same height.
		- If yes, then the last node on the last tree row is in the right subtree and the left subtree
		  is a full tree of height h-1. 
		  So we take the 2^h-1 nodes of the left subtree plus the 1 root node plus recursively the 
		  number of nodes in the right subtree.

		- If no, then the last node on the last tree row is in the left subtree and the right subtree 
		  is a full tree of height h-2. 
		  So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus recursively the 
		  number of nodes in the left subtree.
	- Note: 1 << h ==> 2^h
	- Note: in height function, if root == null, then return -1. 

stats:
	- Since I halve the tree in every recursive step, I have O(log(n)) steps. 
	  Finding a height costs O(log(n)). So overall O(log(n)^2).
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Count Complete Tree Nodes.
	- Memory Usage: 45.6 MB, less than 9.76% 

class Solution {
    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }

    public int countNodes(TreeNode root) {
        int h = height(root);
        if (h < 0) 
        	return 0;
        else {
        	if (height(root.right) == h-1) 
        		// 2^h
        		return (1 << h) + countNodes(root.right);
        	else
        		return (1 << h-1) + countNodes(root.left);
        }

    }
}

ex. in example: return 2^2 + 2^0 + 2^0 = root + at (3) + at (6)
=======================================================================================================
method 2:

method:

	- iterative
	- 

stats:

	- 
	- 

class Solution {
    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int nodes = 0, h = height(root);
        while (root != null) {
            if (height(root.right) == h - 1) {
                nodes += 1 << h;
                root = root.right;
            } else {
                nodes += 1 << h-1;
                root = root.left;
            }
            h--;
        }
        return nodes;
    }
}
=======================================================================================================
method 3:

method:

	- 

Return 0 if the tree is empty.

Compute the tree depth d.

Return 1 if d == 0.

The number of nodes in all levels but the last one is 2^d - 12 
d
 −1. The number of nodes in the last level could vary from 1 to 2^d2 
d
 . Enumerate potential nodes from 0 to 2^d - 12 
d
 −1 and perform the binary search by the node index to check how many nodes are in the last level. Use the function exists(idx, d, root) to check if the node with index idx exists.

Use binary search to implement exists(idx, d, root) as well.

Return 2^d - 12 
d
 −1 + the number of nodes in the last level.
	- 

stats:

	- 
	- 
class Solution {
  // Return tree depth in O(d) time.
  public int computeDepth(TreeNode node) {
    int d = 0;
    while (node.left != null) {
      node = node.left;
      ++d;
    }
    return d;
  }

  // Last level nodes are enumerated from 0 to 2**d - 1 (left -> right).
  // Return True if last level node idx exists. 
  // Binary search with O(d) complexity.
  public boolean exists(int idx, int d, TreeNode node) {
    int left = 0, right = (int)Math.pow(2, d) - 1;
    int pivot;
    for(int i = 0; i < d; ++i) {
      pivot = left + (right - left) / 2;
      if (idx <= pivot) {
        node = node.left;
        right = pivot;
      }
      else {
        node = node.right;
        left = pivot + 1;
      }
    }
    return node != null;
  }

  public int countNodes(TreeNode root) {
    // if the tree is empty
    if (root == null) return 0;

    int d = computeDepth(root);
    // if the tree contains 1 node
    if (d == 0) return 1;

    // Last level nodes are enumerated from 0 to 2**d - 1 (left -> right).
    // Perform binary search to check how many nodes exist.
    int left = 1, right = (int)Math.pow(2, d) - 1;
    int pivot;
    while (left <= right) {
      pivot = left + (right - left) / 2;
      if (exists(pivot, d, root)) left = pivot + 1;
      else right = pivot - 1;
    }

    // The tree contains 2**d - 1 nodes on the first (d - 1) levels
    // and left nodes on the last level.
    return (int)Math.pow(2, d) - 1 + left;
  }
}


