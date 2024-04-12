572. Subtree of Another Tree - Easy

Given two non-empty binary trees s and t, check whether tree t has exactly the same structure 
and node values with a subtree of s. A subtree of s is a tree consists of a node in s and 
all of this node descendants. The tree s could also be considered as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2

Return true, because t has the same structure and node values with a subtree of s.


Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.


******************************************************
key:
  - use preorder traversal, then compare two strings
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
method 1:

method:

  - For each node during pre-order traversal of s, use a recursive function isSame to validate 
    if sub-tree started with this node is the same with t.

stats:

  - n^2
  - Runtime: 5 ms, faster than 96.83% of Java online submissions for Subtree of Another Tree.
  - Memory: 41.4 MB, less than 6.67%

public class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null) return false;
        if (isSame(s, t)) return true;

        // recursively checking subtrees
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
    
    private boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) return true;
        if (s == null || t == null) return false;
        
        if (s.val != t.val) return false;
        
        // recursively check levels of subtree
        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }
}



=======================================================================================================

method 2:

method:

  - encode in a string
  - 

stats:

  - 
  - 

public boolean isSubtree(TreeNode s, TreeNode t) {
    // Java uses a naive contains algorithm so to ensure linear time, replace with KMP algorithm
    return serialize(s).contains(serialize(t)); 
                                                
}

public String serialize(TreeNode root) {
    StringBuilder res = new StringBuilder();
    serialize(root, res);
    return res.toString();
}

private void serialize(TreeNode cur, StringBuilder res) {
    if (cur == null) {res.append(",#"); return;}
    res.append("," + cur.val);
    serialize(cur.left, res);
    serialize(cur.right, res);
}

=======================================================================================================
method 3:

method:

  - Instead of creating an inorder traversal, we can treat every node of the given tree tt as the root, treat it as a subtree and compare the corresponding subtree with the given subtree ss for equality. For checking the equality, we can compare the all the nodes of the two subtrees.

For doing this, we make use a function traverse(s,t) which traverses over the given tree ss and treats every node as the root of the subtree currently being considered. It also checks the two subtrees currently being considered for their equality. In order to check the equality of the two subtrees, we make use of equals(x,y) function, which takes xx and yy, which are the roots of the two subtrees to be compared as the inputs and returns True or False depending on whether the two are equal or not. It compares all the nodes of the two subtrees for equality. Firstly, it checks whether the roots of the two trees for equality and then calls itself recursively for the left subtree and the right subtree.
  - 

stats:

  - Time complexity : O(m*n)O(m∗n). In worst case(skewed tree) traverse function takes O(m*n)O(m∗n) time.

Space complexity : O(n)O(n). The depth of the recursion tree can go upto nn. nn refers to the number of nodes in ss.
  - 

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
 
public class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        return traverse(s,t);
    }
    public boolean equals(TreeNode x,TreeNode y)
    {
        if(x==null && y==null)
            return true;
        if(x==null || y==null)
            return false;
        return x.val==y.val && equals(x.left,y.left) && equals(x.right,y.right);
    }
    public boolean traverse(TreeNode s,TreeNode t)
    {
        return  s!=null && ( equals(s,t) || traverse(s.left,t) || traverse(s.right,t));
    }
}

=======================================================================================================


