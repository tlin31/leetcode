545. Boundary of Binary Tree - Medium

Given a binary tree, return the values of its boundary in anti-clockwise direction starting 
from root. Boundary includes left boundary, leaves, and right boundary in order without 
duplicate nodes.  (The values of the nodes may still be duplicates.)

    Left boundary: path from root to the left-most node. 
    Right boundary: path from root to the right-most node. 
    Note: If the root does not have left subtree or right subtree, then the root itself is left boundary or right boundary. 

    left-most node: leaf node you could reach when you always firstly travel to the left subtree 
                    if exists. If not, travel to the right subtree. Repeat until you reach a leaf node.


Example 1

Input:
  1
   \
    2
   / \
  3   4

Ouput:
[1, 3, 4, 2]

Explanation:
The root does not have left subtree, so the root itself is left boundary.
The leaves are node 3 and 4.
The right boundary are node 1,2,4. Note the anti-clockwise direction means you should output reversed right boundary.
So order them in anti-clockwise without duplicates and we have [1,3,4,2].
 

Example 2

Input:
    ____1_____
   /          \
  2            3
 / \          / 
4   5        6   
   / \      / \
  7   8    9  10  
       
Ouput:
[1,2,4,7,8,9,10,6,3]

Explanation:
The left boundary are node 1,2,4. (4 is the left-most node according to definition)
The leaves are node 4,7,8,9,10.
The right boundary are node 1,3,6,10. (10 is the right-most node).
So order them in anti-clockwise without duplicate nodes we have [1,2,4,7,8,9,10,6,3].


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

  - node.left is left bound if node is left bound;
    node.right could also be left bound if node is left bound && node has no right child;
    
    Same applys for right bound;
      
      if node is left bound, add it before 2 child - pre order;
      if node is right bound, add it after 2 child - post order;
  - A leaf node that is neither left or right bound belongs to the bottom line;


stats:

  - 
  - 

public class Solution {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root != null) {
            res.add(root.val);

            // check for left bound
            getBounds(root.left, res, true, false);

            // check for right bound
            getBounds(root.right, res, false, true);
        }
        return res;
    }

    private void getBounds(TreeNode node, List<Integer> res, boolean lb, boolean rb) {
        if (node == null) 
          return;

        // add every node in the path of reaching the left most node
        if (lb) 
          res.add(node.val);

        // bottom leaf node
        if (!lb && !rb && node.left == null && node.right == null) 
          res.add(node.val);

        // recursion
        getBounds(node.left, res, lb, rb && node.right == null);
        getBounds(node.right, res, lb && node.left == null, rb);
        
        // add right bound last
        if (rb) 
          res.add(node.val);
    }
}


=======================================================================================================
method 2:

method:

  - 
  - 

stats:

  - Both O(n)
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
List<Integer> nodes = new ArrayList<>(1000);
public List<Integer> boundaryOfBinaryTree(TreeNode root) {
    
    if(root == null) return nodes;

    nodes.add(root.val);
    leftBoundary(root.left);
    leaves(root.left);
    leaves(root.right);
    rightBoundary(root.right);
    
    return nodes;
}
public void leftBoundary(TreeNode root) {
    if(root == null || (root.left == null && root.right == null)) 
      return;
    nodes.add(root.val);
    if(root.left == null) 
      leftBoundary(root.right);
    else 
      leftBoundary(root.left);
}
public void rightBoundary(TreeNode root) {
    if(root == null || (root.right == null && root.left == null)) return;
    if(root.right == null)rightBoundary(root.left);
    else rightBoundary(root.right);
    nodes.add(root.val); // add after child visit(reverse)
}
public void leaves(TreeNode root) {
    if(root == null) return;
    if(root.left == null && root.right == null) {
        nodes.add(root.val);
        return;
    }
    leaves(root.left);
    leaves(root.right);
}

=======================================================================================================
method 3:

method:

  - pre order
  - From the above figure, we can observe that our problem statement is very similar to the Preorder traversal. Actually, the order of traversal is the same(except for the right boundary nodes, for which it is the reverse), but we need to selectively include the nodes in the return result list. Thus, we need to include only those nodes in the result, which are either on the left boundary, the leaves or the right boundary.

In order to distinguish between the various kinds of nodes, we make use of a flagflag as follows:

Flag=0: Root Node.

Flag=1: Left Boundary Node.

Flag=2: Right Boundary Node.

Flag=3: Others(Middle Node).

We make use of three lists \text{left_boundary}, \text{right_boundary}, \text{leaves}leaves to store the appropriate nodes and append the three lists at the end.

We go for the normal preorder traversal, but while calling the recursive function for preorder traversal using the left child or the right child of the current node, we also pass the flagflag information indicating the type of node that the current child behaves like.

For obtaining the flag information about the left child of the current node, we make use of the function leftChildFlag(node, flag). In the case of a left child, the following cases are possible, as can be verified by looking at the figure above:

The current node is a left boundary node: In this case, the left child will always be a left boundary node. e.g. relationship between E & J in the above figure.

The current node is a root node: In this case, the left child will always be a left boundary node. e.g. relationship between A & B in the above figure.

The current node is a right boundary node: In this case, if the right child of the current node doesn't exist, the left child always acts as the right boundary node. e.g. G & N. But, if the right child exists, the left child always acts as the middle node. e.g. C & F.

Similarly, for obtaining the flag information about the right child of the current node, we make use of the function rightChildFlag(node, flag). In the case of a right child, the following cases are possible, as can be verified by looking at the figure above:

The current node is a right boundary node: In this case, the right child will always be a right boundary node. e.g. relationship between C & G in the above figure.

The current node is a root node: In this case, the right child will always be a left boundary node. e.g. relationship between A & C in the above figure.

The current node is a left boundary node: In this case, if the left child of the current node doesn't exist, the right child always acts as the left boundary node. e.g. B & E. But, if the left child exists, the left child always acts as the middle node.

Making use of the above information, we set the flagflag appropriately, which is used to determine the list in which the current node has to be appended.

stats:

  - Time complexity : O(n)O(n) One complete traversal of the tree is done.

  - Space complexity : O(n)O(n) The recursive stack can grow upto a depth of nn. Further, \text{left_boundary}, \text{right_boundary} and \text{leaves}leaves combined together can be of size nn.



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
    public List < Integer > boundaryOfBinaryTree(TreeNode root) {
        List < Integer > left_boundary = new LinkedList < > (), 
                        right_boundary = new LinkedList < > (), 
                        leaves = new LinkedList < > ();

        preorder(root, left_boundary, right_boundary, leaves, 0);
        left_boundary.addAll(leaves);
        left_boundary.addAll(right_boundary);
        return left_boundary;
    }

    public boolean isLeaf(TreeNode cur) {
        return (cur.left == null && cur.right == null);
    }

    public boolean isRightBoundary(int flag) {
        return (flag == 2);
    }

    public boolean isLeftBoundary(int flag) {
        return (flag == 1);
    }

    public boolean isRoot(int flag) {
        return (flag == 0);
    }

    public int leftChildFlag(TreeNode cur, int flag) {
        if (isLeftBoundary(flag) || isRoot(flag))
            return 1;
        else if (isRightBoundary(flag) && cur.right == null)
            return 2;
        else return 3;
    }

    public int rightChildFlag(TreeNode cur, int flag) {
        if (isRightBoundary(flag) || isRoot(flag))
            return 2;
        else if (isLeftBoundary(flag) && cur.left == null)
            return 1;
        else return 3;
    }

    public void preorder(TreeNode cur, List < Integer > left_boundary, List < Integer > right_boundary, List < Integer > leaves, int flag) {
        if (cur == null)
            return;
        if (isRightBoundary(flag))
            right_boundary.add(0, cur.val);
        else if (isLeftBoundary(flag) || isRoot(flag))
            left_boundary.add(cur.val);
        else if (isLeaf(cur))
            leaves.add(cur.val);
        preorder(cur.left, left_boundary, right_boundary, leaves, leftChildFlag(cur, flag));
        preorder(cur.right, left_boundary, right_boundary, leaves, rightChildFlag(cur, flag));
    }
}

