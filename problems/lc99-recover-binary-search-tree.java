99. Recover Binary Search Tree - Hard

Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

Example 1:

Input: [1,3,null,null,2]

   1
  /
 3
  \
   2

Output: [3,1,null,null,2]

   3
  /
 1
  \
   2


Example 2:

Input: [3,1,4,null,null,2]

  3
 / \
1   4
   /
  2

Output: [2,1,4,null,null,3]

  2
 / \
1   4
   /
  3

Follow up:
A solution using O(n) space is pretty straight forward.
Could you devise a constant space solution?


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

  - It is known that inorder traversal of BST is an array sorted in the ascending order. 
  - Here two nodes are swapped, and hence inorder traversal is an almost sorted array where only 
    two elements are swapped. To identify two swapped elements in a sorted array is a classical 
    problem that could be solved in linear time.

  - Algo: 
    1. Construct inorder traversal of the tree. it is almost sorted w/ 2 elements are swapped.

    2. Identify 2 swapped elements x and y in an almost sorted array in linear time.

    3. Traverse the tree again. Change value x to y and value y to x.

stats:

  - Time: O(n)
  - Space:O(N)


class Solution {

  // main function
  public void recoverTree(TreeNode root) {
    List<Integer> nums = new ArrayList();
    inorder(root, nums);
    int[] swapped = findTwoSwapped(nums);
    recover(root, 2, swapped[0], swapped[1]);
  }

  public void inorder(TreeNode root, List<Integer> nums) {
    if (root == null) 
      return;

    inorder(root.left, nums);
    nums.add(root.val);
    inorder(root.right, nums);
  }

  public int[] findTwoSwapped(List<Integer> nums) {
    int n = nums.size();
    int x = -1, y = -1;
    for(int i = 0; i < n - 1; ++i) {
      if (nums.get(i + 1) < nums.get(i)) {
        y = nums.get(i + 1);
        // first swap occurence

        if (x == -1) 
          x = nums.get(i);
        // second swap occurence
        else 
          break;
      }
    }
    return new int[]{x, y};
  }

  public void recover(TreeNode root, int count, int x, int y) {
    if (root != null) {

      // reach wrong order nodes, change its value
      if (root.val == x || root.val == y) {
        root.val = root.val == x ? y : x;

        if (--count == 0) return;
      }

      recover(root.left, count, x, y);
      recover(root.right, count, x, y);
    }
  }


}

=======================================================================================================
method 2:

method:

  - Here we construct inorder traversal by iterations and identify swapped nodes at the same time
  - use deque: go left as far as you can, then one step right. 
    Repeat till the end of nodes in the tree.

  - To identify swapped nodes, track the last node pred in the inorder traversal (i.e. the predecessor 
    of the current node) and compare it with current node value. 
    If the current node value is smaller than its predecessor pred value, the swapped node is here.

  - since only 2 swapped nodes, we break after having the second node identified.


stats:

  - Time complexity : O(1) in the best case, and O(N) in the worst case when one of the swapped 
                      nodes is a rightmost leaf.
  - Space complexity : up to O(H) to keep the stack where H is a tree height.


class Solution {
  public void swap(TreeNode a, TreeNode b) {
    int tmp = a.val;
    a.val = b.val;
    b.val = tmp;
  }

  public void recoverTree(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque();
    TreeNode x = null, y = null, pred = null;

    while (!stack.isEmpty() || root != null) {
      while (root != null) {
        stack.add(root);
        root = root.left;
      }

      root = stack.removeLast();
      if (pred != null && root.val < pred.val) {
        y = root;
        if (x == null) 
          x = pred;
        else 
          break;
      }

      // go 1 step to the right (switch branch, get the next greatest number)
      pred = root;
      root = root.right;
    }

    swap(x, y);
  }
}

=======================================================================================================
method 3:

method:

  - Morris: The idea of Morris inorder traversal is simple: to use no space but to traverse the tree.
  - set the temporary link between the node and its predecessor: predecessor.right = root. 
    So one starts from the node, computes its predecessor and verifies if the link is present.

    There is no link? Set it and go to the left subtree.

    There is a link? Break it and go to the right subtree.

    what if there is no left child, i.e. there is no left subtree? Then go to the right subtree.



stats:

  - Time: O(N)
  - Space: O(1)


class Solution {
  public void swap(TreeNode a, TreeNode b) {
    int tmp = a.val;
    a.val = b.val;
    b.val = tmp;
  }

  public void recoverTree(TreeNode root) {
    // predecessor is a Morris predecessor. 
    // In the 'loop' cases it could be equal to the node itself predecessor == root.
    // pred is a 'true' predecessor, 
    // the previous node in the inorder traversal.
    TreeNode x = null, y = null, pred = null, predecessor = null;

    while (root != null) {
      // If there is a left child
      // then compute the predecessor.
      // If there is no link predecessor.right = root --> set it.
      // If there is a link predecessor.right = root --> break it.
      if (root.left != null) {
        // Predecessor node is one step left 
        // and then right till you can.
        predecessor = root.left;
        while (predecessor.right != null && predecessor.right != root)
          predecessor = predecessor.right;

        // set link predecessor.right = root
        // and go to explore left subtree
        if (predecessor.right == null) {
          predecessor.right = root;
          root = root.left;
        }
        // break link predecessor.right = root
        // link is broken : time to change subtree and go right
        else {
          // check for the swapped nodes
          if (pred != null && root.val < pred.val) {
            y = root;
            if (x == null) x = pred;
          }
          pred = root;

          predecessor.right = null;
          root = root.right;
        }
      }
      // If there is no left child
      // then just go right.
      else {
        // check for the swapped nodes
        if (pred != null && root.val < pred.val) {
          y = root;
          if (x == null) x = pred;
        }
        pred = root;

        root = root.right;
      }
    }
    swap(x, y);
  }
}
