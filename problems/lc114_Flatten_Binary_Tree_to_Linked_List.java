114. Flatten Binary Tree to Linked List - Medium

Given the root of a binary tree, flatten the tree into a "linked list":

The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 

Example 1:


Input: root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]
Example 2:

Input: root = []
Output: []
Example 3:

Input: root = [0]
Output: [0]
 

Constraints:

The number of nodes in the tree is in the range [0, 2000].
-100 <= Node.val <= 100
 

Follow up: Can you flatten the tree in-place (with O(1) extra space)?




******************************************************
key:
	- 
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
	- 
class Solution {
    public void flatten(TreeNode root) {
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();

            // 如果有右节点，先推入栈
            if (curr.right != null) {
                stack.push(curr.right);
            }
            // 左节点再推入，这样左节点会先处理
            if (curr.left != null) {
                stack.push(curr.left);
            }

            // 重建指针：
            // 下一个前序节点就是栈顶元素
            if (!stack.isEmpty()) {
                curr.right = stack.peek();
            }
            curr.left = null;  // 必须清空
        }
    }
}

假设树为：

    1
   / \
  2   5
 / \   \
3   4   6


stack push 顺序：

push 1
pop → 1
push 5
push 2
pop → 2
push 4
push 3
...


重建后的链表：

1 → 2 → 3 → 4 → 5 → 6


完全符合前序。


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
	public void flatten(TreeNode root) {
	    flatten(root,null);
	}
	private TreeNode flatten(TreeNode root, TreeNode pre) {
	    if(root==null) return pre;
	    pre=flatten(root.right,pre);    
	    pre=flatten(root.left,pre);
	    root.right=pre;
	    root.left=null;
	    pre=root;
	    return pre;
	}
}


or


class Solution {
    public void flatten(TreeNode root) {
        if (root == null) return;

        // 1. flatten left and right children
        flatten(root.left);
        flatten(root.right);

        // 2. save right subtree
        TreeNode tempRight = root.right;

        // 3. move left subtree to right
        root.right = root.left;
        root.left = null;

        // 4. attach saved right subtree at end
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = tempRight;
    }
}
