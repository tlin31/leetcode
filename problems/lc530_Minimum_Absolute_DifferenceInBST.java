530. Minimum Absolute Difference in BST- Easy

Given the root of a Binary Search Tree (BST), return the minimum absolute difference 
between the values of any two different nodes in the tree.

 

Example 1:


Input: root = [4,2,6,1,3]
Output: 1
Example 2:


Input: root = [1,0,48,null,null,12,49]
Output: 1
 

Constraints:

The number of nodes in the tree is in the range [2, 104].
0 <= Node.val <= 105

******************************************************
key:
	- inorder
	- follow up: what if not BST?  -->use tree sort
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- In-Order traverse, time complexity O(N), space complexity O(1).

public class Solution {
    int min = Integer.MAX_VALUE;
    Integer prev = null;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        getMinimumDifference(root.left);
        
        if (prev != null) {
            min = Math.min(min, root.val - prev);
        }
        prev = root.val;
        
        getMinimumDifference(root.right);
        
        return min;
    }
    
}

What if it is not a BST? (Follow up of the problem) 
The idea is to put values in a TreeSet and then every time we can use O(lgN) time to 
lookup for the nearest values.

Solution 2 - Pre-Order traverse, time complexity O(NlgN), space complexity O(N).

public class Solution {
    TreeSet<Integer> set = new TreeSet<>();
    int min = Integer.MAX_VALUE;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        
        if (!set.isEmpty()) {
            if (set.floor(root.val) != null) {
                min = Math.min(min, root.val - set.floor(root.val));
            }
            if (set.ceiling(root.val) != null) {
                min = Math.min(min, set.ceiling(root.val) - root.val);
            }
        }
        
        set.add(root.val);
        
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        
        return min;
    }
}




