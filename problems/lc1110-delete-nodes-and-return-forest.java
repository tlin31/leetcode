1110. Delete Nodes And Return Forest - Medium

Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we have a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest.  You may return the result in any order.

 

Example 1:

Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
Output: [[1,2,null,4],[6],[7]]
 

Constraints:

The number of nodes in the given tree is at most 1000.
Each node has a distinct value between 1 and 1000.
to_delete.length <= 1000
to_delete contains distinct values between 1 and 1000.


******************************************************
key:
	- recursion
	- edge case:
		1) empty string, return empty
		2)

******************************************************

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */


=======================================================================================================
method 1:

method:

	- 	if (is_root && !deleted), then res.add(node)
		is_root: The node parent is deleted. The node is the root node of the tree in the forest.
		!deleted: The node is not in the to_delete array.
		We only need to add the root node of every tree.
	- 

stats:

	- Time O(N)
	- Space O(H + N), where H is the height of tree.
	- Runtime: 1 ms, faster than 98.63% of Java online submissions for Delete Nodes And Return Forest.
	- Memory Usage: 41.8 MB, less than 100.00% of Java online submissions for Delete Nodes And Return Forest.



class Solution {
    List<TreeNode> res = new ArrayList<>();
    Set<Integer> set = new HashSet<>();

    // main function
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        for (int i : to_delete) {
            set.add(i);
        }

        helper(root, true);
        return res;
    }

    public TreeNode helper(TreeNode node, boolean isRoot) {
        if (node == null) {
            return null;
        }

        boolean isDeleted = set.contains(node.val);

        // if its parent is deleted (isRoot == True) and itself is not deleted
        // it forms a new tree
        if (isRoot && !isDeleted) {
            res.add(node);
        }

        node.left = helper(node.left, isDeleted);
        node.right = helper(node.right, isDeleted);

        // if this is a deleted note, just return null
        if (isDeleted) {
            return null;
        } else {
            return node;
        }
    }
}

=======================================================================================================
method 2:

method:

	- 
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



