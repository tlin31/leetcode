236. Lowest Common Ancestor of a Binary Tree - Medium

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes 
p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a 
descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]


Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the 
LCA definition.
 

Note:

All of the nodes values will be unique.
p and q are different and both values will exist in the binary tree.

******************************************************
key:
	- use parent pointers to store path
	- recursion: 
	- edge case:
		1) empty tree, null
		2) if p or q == null? 

******************************************************

public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
 }


	
=======================================================================================================
method 1: recursion

Runtime: 5 ms, faster than 100.00% of Java online submissions for Lowest Common Ancestor of a Binary Tree.
Memory Usage: 34.7 MB, less than 6.35%

if both p and q exist in Tree rooted at root, then return their LCA
if neither p and q exist in Tree rooted at root, then return null
if only one of p or q (NOT both of them), exists in Tree rooted at root, return it

对于 lowestCommonAncestor 这个函数的理解的话，它不一定可以返回最近的共同祖先，如果子树中只能找到 p 节点
或者 q 节点，它最终返回其实就是 p 节点或者 q 节点。这其实对应于最后一种情况，也就是 leftCommonAncestor 和 
rightCommonAncestor 都不为 null，说明 p 节点和 q 节点分处于两个子树中，直接 return root。


public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if( root == p || root == q || root == null)
            return root;

        TreeNode left = lowestCommonAncestor( root.left,  p,  q);
        TreeNode right = lowestCommonAncestor( root.right,  p,  q);

    	//在左子树中没有找到，那一定在右子树中
        if(left == null)
            return right;
        else if (right == null)
            return left;
        else
            return root;
        
    }

=======================================================================================================
method 2:

method:

	- Iterative using parent pointers
	- If we have parent pointers for each node we can traverse back from p and q to get their ancestors.
	  The first common node we get during this traversal would be the LCA node. We can save the parent 
	  pointers in a dictionary as we traverse the tree.

	-	1. Start from the root node and traverse the tree until we find p and q both, 
			keep storing the parent pointers in a dictionary.
		2. if found both p and q, we get all the ancestors for p using the parent dictionary and add 
		   to a set called ancestors.
		3. Similarly, we traverse through ancestors for node q. If the ancestor is present in the 
		   ancestors set for p, this means this is the first ancestor common between p and q 
		   (while traversing upwards) and hence this is the LCA node.
	- 

stats:

	- O(n)
	- 

class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // Stack for tree traversal
        Deque<TreeNode> stack = new ArrayDeque<>();

        // HashMap for parent pointers --> (node a, node a's parent)
        Map<TreeNode, TreeNode> parent = new HashMap<>();

        parent.put(root, null);
        stack.push(root);

        // Iterate until we find BOTH the nodes p and q
        while (!parent.containsKey(p) || !parent.containsKey(q)) {

            TreeNode node = stack.pop();

            // While traversing the tree, keep saving the parent pointers.
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }

        // Ancestors set() for node p.
        Set<TreeNode> ancestors = new HashSet<>();

        // Process all ancestors for node p using parent pointers.
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p);
        }

        // The first ancestor of q which appears in p's ancestor set() is their lowest common ancestor.
        while (!ancestors.contains(q))
            q = parent.get(q);
        return q;
    }

}
=======================================================================================================
method 3:

method:

	- Iterative without parent pointers
	- pointer to the probable LCA and the moment we find both the nodes we return the pointer as the 
	  answer.
	- algorithm:
		1. Start with root node.
		2. Put the (root, root_state) on to the stack. root_state defines whether one of the children 
		   or both children of root are left for traversal.
		3. While the stack != empty, 
			see top element of the stack represented as (parent_node, parent_state).
		4. Before traversing any of the child nodes of parent_node we check if the parent_node itself 
		   is one of p or q.
		5. First time we find either of p or q, set a boolean flag called one_node_found to True. 
		   Also start keeping track of the lowest common ancestors by keeping a note of the top index 
		   of the stack in the variable LCA_index. Since all the current elements of the stack are ancestors of the node we just found.
		The second time parent_node == p or parent_node == q it means we have found both the nodes and we can return the LCA node.
		Whenever we visit a child of a parent_node we push the (parent_node, updated_parent_state) onto the stack. We update the state of the parent since a child/branch has been visited/processed and accordingly the state changes.
		A node finally gets popped off from the stack when the state becomes BOTH_DONE implying both left and right subtrees have been pushed onto the stack and processed. If one_node_found is True then we need to check if the top node being popped could be one of the ancestors of the found node. In that case we need to reduce LCA_index by one. Since one of the ancestors was popped off.
		Whenever both p and q are found, LCA_index would be pointing to an index in the stack which would contain all the common ancestors between p and q. And the LCA_index element has the lowest ancestor common between p and q.
	- 

stats:

	- 
	- 

import javafx.util.*;

class Solution {

    // Three static flags to keep track of post-order traversal.

    // Both left and right traversal pending for a node.
    // Indicates the nodes children are yet to be traversed.
    private static int BOTH_PENDING = 2;

    // Left traversal done.
    private static int LEFT_DONE = 1;

    // Both left and right traversal done for a node.
    // Indicates the node can be popped off the stack.
    private static int BOTH_DONE = 0;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        Stack<Pair<TreeNode, Integer>> stack = new Stack<Pair<TreeNode, Integer>>();

        // Initialize the stack with the root node.
        stack.push(new Pair<TreeNode, Integer>(root, Solution.BOTH_PENDING));

        // This flag is set when either one of p or q is found.
        boolean one_node_found = false;

        // This is used to keep track of the LCA.
        TreeNode LCA = null;

        // Child node
        TreeNode child_node = null;

        // We do a post order traversal of the binary tree using stack
        while (!stack.isEmpty()) {

            Pair<TreeNode, Integer> top = stack.peek();
            TreeNode parent_node = top.getKey();
            int parent_state = top.getValue();

            // If the parent_state is not equal to BOTH_DONE,
            // this means the parent_node can't be popped off yet.
            if (parent_state != Solution.BOTH_DONE) {

                // If both child traversals are pending
                if (parent_state == Solution.BOTH_PENDING) {

                    // Check if the current parent_node is either p or q.
                    if (parent_node == p || parent_node == q) {

                        // If one_node_found was set already, this means we have found
                        // both the nodes.
                        if (one_node_found) {
                            return LCA;
                        } else {
                            // Otherwise, set one_node_found to True,
                            // to mark one of p and q is found.
                            one_node_found = true;

                            // Save the current top element of stack as the LCA.
                            LCA = stack.peek().getKey();
                        }
                    }

                    // If both pending, traverse the left child first
                    child_node = parent_node.left;
                } else {
                    // traverse right child
                    child_node = parent_node.right;
                }

                // Update the node state at the top of the stack
                // Since we have visited one more child.
                stack.pop();
                stack.push(new Pair<TreeNode, Integer>(parent_node, parent_state - 1));

                // Add the child node to the stack for traversal.
                if (child_node != null) {
                    stack.push(new Pair<TreeNode, Integer>(child_node, Solution.BOTH_PENDING));
                }
            } else {

                // If the parent_state of the node is both done,
                // the top node could be popped off the stack.
                // Update the LCA node to be the next top node.
                if (LCA == stack.pop().getKey() && one_node_found) {
                    LCA = stack.peek().getKey();
                }

            }
        }

        return null;
    }
}
=======================================================================================================
method 1:

method:

	- By Storing root to n1 and root to n2 paths:
		1) Find path from root to n1 and store it in a vector or array.
		2) Find path from root to n2 and store it in another vector or array.
		3) Traverse both paths till the values in arrays are same. Return the common element just 
			before the mismatch.
	- 

stats:

	- O(n)
	- 


// A Binary Tree node 
class Node { 
	int data; 
	Node left, right; 

	Node(int value) { 
		data = value; 
		left = right = null; 
	} 
} 


	Node root; 
	private List<Integer> path1 = new ArrayList<>(); 
	private List<Integer> path2 = new ArrayList<>(); 

	// Finds the path from root node to given root of the tree. 
	int findLCA(int n1, int n2) { 
		path1.clear(); 
		path2.clear(); 
		return findLCAInternal(root, n1, n2); 
	} 

	private int findLCAInternal(Node root, int n1, int n2) { 

		if (!findPath(root, n1, path1) || !findPath(root, n2, path2)) { 
			System.out.println((path1.size() > 0) ? "n1 is present" : "n1 is missing"); 
			System.out.println((path2.size() > 0) ? "n2 is present" : "n2 is missing"); 
			return -1; 
		} 

		int i; 
		for (i = 0; i < path1.size() && i < path2.size(); i++) { 
			
		// System.out.println(path1.get(i) + " " + path2.get(i)); 
			if (!path1.get(i).equals(path2.get(i))) 
				break; 
		} 

		return path1.get(i-1); 
	} 
	
	// Finds the path from root node to given root of the tree, Stores the 
	// path in a vector path[], returns true if path exists otherwise false 
	private boolean findPath(Node root, int n, List<Integer> path) 
	{ 
		if (root == null) { 
			return false; 
		} 
		
		// Store this node . The node will be removed if not in path from root to n. 
		path.add(root.data); 

		if (root.data == n) return true; 
		

		if (root.left != null && findPath(root.left, n, path)) { 
			return true; 
		} 

		if (root.right != null && findPath(root.right, n, path)) { 
			return true; 
		} 

		// If not present in subtree rooted with root, remove root from 
		// path[] and return false 
		path.remove(path.size()-1); 

		return false; 
	} 

