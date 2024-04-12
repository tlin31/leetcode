235. Lowest Common Ancestor of a Binary Search Tree - Easy

Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two 
nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to 
be a descendant of itself).”

Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]


Example 1:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.
Example 2:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the 
LCA definition.
 

Note:

All of the nodes values will be unique.
p and q are different and both values will exist in the BST.

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- recursion
	- 

stats:

	- 
	- 
	if(root.val > p.val && root.val > q.val) 
		return lowestCommonAncestor(root.left, p, q);
    else if(root.val < p.val && root.val < q.val) 
    	return lowestCommonAncestor(root.right, p, q);
    else 
    	return root;



=======================================================================================================
method 2:

method:

	- iterative
	- Just walk down from the whole trees root as long as both p and q are in the same subtree (meaning their values are both smaller or both larger than root's). This walks straight from the root to the LCA, not looking at the rest of the tree, so it's pretty much as fast as it gets. A few ways to do it:



stats:

	- 
	- 

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	    while ((root.val - p.val) * (root.val - q.val) > 0)
	    	if (p.val < root.val)
	    		root = root.left
	    	else 
	    		root = root.right
	    return root;
	}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



