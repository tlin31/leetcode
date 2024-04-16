1214. Two Sum BSTs
Medium

Given the roots of two binary search trees, root1 and root2, return true if and only if there is a node in the first tree and a node in the second tree whose values sum up to a given integer target.

 
Example 1:
Input: root1 = [2,1,4], root2 = [1,0,3], target = 5
Output: true
Explanation: 2 and 3 sum up to 5.

ex2
Input: root1 = [0,-10,10], root2 = [5,1,7,0,2], target = 18
Output: false
 

Constraints:

The number of nodes in each tree is in the range [1, 5000].
-109 <= Node.val, target <= 109

******************************************************
key:
	- The BST is not guaranteed to be a balanced BST, the space complexity of search and 
	traverse in a BST would be O(height), so in the worst case, if input BSTs are LinkedLists, 
	the space would be O(n1+n2).

	- 2 stacks, 1 ascending, 1 descending

	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
Best solution:

Traverse root 1 from smallest value to node to largest.
Traverse root 2 from largest value node to smallest.
Sum up the corresponding node’s value : If sum == target return true
If target > sum,
then move to the inorder successor of the current node of root1,
else
move to the inorder predecessor of the current node of root2.

class Solution {
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        Deque<TreeNode> stack1 = new ArrayDeque<>();
        Deque<TreeNode> stack2 = new ArrayDeque<>();
        pushAll(stack1, root1, true);  //tree 1 stored increasing
        pushAll(stack2, root2, false); // tree 2 stored dec

        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            int sum = stack1.peek().val + stack2.peek().val;
            if (sum > target)
                pop(stack2, false);
            else if (sum < target)
                pop(stack1, true);
            else
                return true;
        }
        return false;
    }
    
    private void pushAll(Deque<TreeNode> stack, TreeNode node, boolean isInc) {
        while (node != null) {
            stack.push(node);
            node = isInc ? node.left : node.right;
        }
    }
    
    private TreeNode pop(Deque<TreeNode> stack, boolean isInc) {
        TreeNode node = stack.pop();
        pushAll(stack, isInc ? node.right : node.left, isInc);
        return node;
    }
}


=======================================================================================================
Method 1: set to store array, then chceck recursive


Stats:

	- 
	- 


Method:

	-	

class Solution {
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if(root1 == null || root2 == null) return false;
        Set<Integer> set = new HashSet();
        add(root1, set);
        return find(root2, set, target);
    }
    
   private void add(TreeNode root, Set<Integer> set) {
        if(root == null) 
            return;
        set.add(root.val);
        add(root.left, set);
        add(root.right, set);
    }
    
    private boolean find(TreeNode root, Set<Integer> set, int target) {
        if(root == null) return false;
        if(set.contains(target - root.val)) return true;

        return find(root.left, set, target) || find(root.right, set, target);
    }
}


=======================================================================================================

Binary search 
Time: O(n1 * n2), space: O(n1 + n2), 
where n1 and n2 are the node numbers of root1 and root2, respectively.

    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null) return false;
        if (binarySearch(root2, target - root1.val)) return true;
        return twoSumBSTs(root1.left, root2, target) || twoSumBSTs(root1.right, root2, target);
    }
    private boolean binarySearch(TreeNode n, int t) {
        if (n == null) return false;
        if (n.val == t) return true;
        return binarySearch(t < n.val ? n.left : n.right, t);
    }



=======================================================================================================

Method 2
Put one of the tree into a hash set and traverse another tree, check the set has value 
that equals target - node.val.

Time & space: O(n1 + n2)


public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        Set<Integer> seen = new HashSet<>();
        preorder(root2, seen);
        return preorder(root1, seen, target);
    }
    private void preorder(TreeNode n, Set<Integer> seen) {
        if (n == null) return;
        seen.add(n.val);
        preorder(n.left, seen);
        preorder(n.right, seen);
    }
    private boolean preorder(TreeNode n, Set<Integer> seen, int t) {
        if (n == null) return false;
        if (seen.contains(t - n.val)) return true;
        return preorder(n.left, seen, t) || preorder(n.right, seen, t);    
    }


=======================================================================================================
Method 3: stack

Time: O(n1 * n2), space: O(n1 + n2)

    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        return inorder(root1, root2, target);
    }

    private boolean inorder(TreeNode n1, TreeNode n2, int t) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (n1 != null || !stack.isEmpty()) {

        	// push left element to stack
            leftSubtreeToStack(n1, stack);
            n1 = stack.pop();

            if (binarySearch(n2, t - n1.val)) 
                return true;
            n1 = n1.right;
        }
        return false;
    }

    private boolean binarySearch(TreeNode n, int t) {
        while (n != null) {
            if (n.val == t) 
                return true;
            n = t < n.val ? n.left : n.right;
        }
        return false;
    }

    private void leftSubtreeToStack(TreeNode n, Deque<TreeNode> stack) {
        while (n != null) {
            stack.push(n);
            n = n.left;
        }
    }


=======================================================================================================
Method 4
Time & space: O(n1 + n2), where n1 and n2 are the node numbers of root1 and root2, respectively.



public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        Set<Integer> seen = new HashSet<>();
        inorder(root2, seen);
        return inorder(root1, seen, target);
    }
    private void inorder(TreeNode n, Set<Integer> seen) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (n != null || !stack.isEmpty()) {
            leftSubtreeToStack(n, stack);
            n = stack.pop();
            seen.add(n.val);
            n = n.right;
        }
    }
    private boolean inorder(TreeNode n, Set<Integer> seen, int t) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (n != null || !stack.isEmpty()) {
            leftSubtreeToStack(n, stack);
            n = stack.pop();
            if (seen.contains(t - n.val)) 
                return true;
            n = n.right;
        }
        return false;
    }
    private void leftSubtreeToStack(TreeNode n, Deque<TreeNode> stack) {
        while (n != null) {
            stack.push(n);
            n = n.left;
        }
    }






