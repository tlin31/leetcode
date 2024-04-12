951. Flip Equivalent Binary Trees - Medium

For a binary tree T, we can define a flip operation as follows: choose any node, and swap the 
left and right child subtrees.

A binary tree X is flip equivalent to a binary tree Y if and only if we can make X equal to Y after 
some number of flip operations.

Write a function that determines whether two binary trees are flip equivalent.  The trees are given by
root nodes root1 and root2.

 

Example 1:

Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
Output: true
Explanation: We flipped at nodes with values 1, 3, and 5.
Flipped Trees Diagram
 

Note:

Each tree will have at most 100 nodes.
Each value in each tree will be a unique integer in the range [0, 99].


******************************************************
key:
	- recursion, dfs, bfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	If root1 and root2 have the same root value, then we only need to check if their children are 
	equal (up to ordering.)

	There are 3 cases:

		1. If root1 or root2 is null, then they are equivalent if and only if they are both null.

		2. Else, if root1 and root2 have different values, they aren't equivalent.

		3. Else, let's check whether the children of root1 are equivalent to the children of root2. There are two different ways to pair these children.

stats:

	- Time Complexity: O(min(N1, N2)), N1 and N2 are the lengths of root1 and root2.

	- Space Complexity: O(min(H1, H2)), H1 and H2 are the heights of the trees of root1 and root2.


class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if (root1 == root2)
            return true;

        if (root1 == null || root2 == null || root1.val != root2.val)
            return false;

        return (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) ||
                flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
    }
}


=======================================================================================================
method 2:

method:

	- DFS / canonical representation
	- Flip each node so that the left child is smaller than the right, and call this the canonical
	  representation. All equivalent trees have exactly one canonical representation.

	- We can use a depth-first search to compare the canonical representation of each tree. 
	  If the traversals are the same, the representations are equal.

	- When traversing, we should be careful to encode both when we enter or leave a node.

stats:

	- Time Complexity: O(N1 + N2) N are the lengths of root1 and root2. 
	- Space Complexity: O(N1 + N2)



class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        List<Integer> vals1 = new ArrayList();
        List<Integer> vals2 = new ArrayList();
        dfs(root1, vals1);
        dfs(root2, vals2);
        return vals1.equals(vals2);
    }

    public void dfs(TreeNode node, List<Integer> vals) {
        if (node != null) {
            vals.add(node.val);
            int L = node.left != null ? node.left.val : -1;
            int R = node.right != null ? node.right.val : -1;

            if (L < R) {
                dfs(node.left, vals);
                dfs(node.right, vals);
            } else {
                dfs(node.right, vals);
                dfs(node.left, vals);
            }

            vals.add(null);
        }
    }
}

------------------------

Iterative DFS:

 public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        Stack<TreeNode> stk1 = new Stack<>(), stk2 = new Stack<>();
        stk1.push(root1);
        stk2.push(root2);
        while (!stk1.isEmpty() && !stk2.isEmpty()) {
            TreeNode n1 = stk1.pop(), n2 = stk2.pop();
            if (n1 == null && n2 == null) {
                continue;
            }else if (n1 == null || n2 == null || n1.val != n2.val) {
                return false;
            }

            if (n1.left == n2.left || n1.left != null && n2.left != null && n1.left.val == n2.left.val) {
                stk1.addAll(Arrays.asList(n1.left, n1.right));
            }else {
                stk1.addAll(Arrays.asList(n1.right, n1.left));
            }
            stk2.addAll(Arrays.asList(n2.left, n2.right));
        }
        return stk1.isEmpty() && stk2.isEmpty();
    }
    
=======================================================================================================
method 3:

method:

	- BFS
	- 

stats:

	- 
	- 

public boolean flipEquivIterative(TreeNode root1, TreeNode root2) {

        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root1);
        queue.offer(root2);
        while (!queue.isEmpty()) {
            TreeNode curr1 = queue.poll();
            TreeNode curr2 = queue.poll();

            if (curr1 == null && curr2 == null) {
                continue;
            }
            if (!equals(curr1, curr2)) {
                return false;
            }
            if (isEquals(curr1.left, curr2.left) && isEquals(curr1.right, curr2.right)) {
                queue.offer(curr1.left);
                queue.offer(curr2.left);
                queue.offer(curr1.right);
                queue.offer(curr2.right);
            } else if (isEquals(curr1.left, curr2.right) && isEquals(curr1.right, curr2.left)) {
                queue.offer(curr1.left);
                queue.offer(curr2.right);
                queue.offer(curr1.right);
                queue.offer(curr2.left);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isEquals(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 != null && root1.val == root2.val) {
            return true;
        } else {
            return false;
        }
    }

