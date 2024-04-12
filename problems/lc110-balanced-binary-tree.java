110. Balanced Binary Tree - Easy



Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:

a binary tree in which the left and right subtrees of every node differ in height by no more than 1.

 

Example 1:

Given the following tree [3,9,20,null,null,15,7]:

    3
   / \
  9  20
    /  \
   15   7
Return true.



Example 2:

Given the following tree [1,2,2,3,3,null,null,4,4]:

       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
Return false.


******************************************************
key:
  - 
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
Method 1:


Stats:

  - 
  - 


Method:

  - use -1 
  - Please understand that when you return something in recursion it goes to the calling function 
    just above the stack.
    ex. [1,2,2,3,null,null,3,4,null,null,4], this is a case where the tree is unbalanced at 2 but 
        balanced at root that is 1.
    
    Now lets us consider the case where we do NOT write if (lH==-1 || rH==-1) return -1; then only 
    the call that was one step above the stack receives -1 but the thing is once we find the 
    difference is -1, there is no point in further calculations (cause the tree will be unbalanced).
    
    And to propagate this we have to return -1 everytime without changing the value until the 
    first call on the stack which is in the isBalanced() function.


    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return helper(root) != -1;
    }

    private int helper(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        if(left == -1 || right == -1 || Math.abs(left - right) > 1){
            return -1;
        }
        return Math.max(left, right) + 1;
    }






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def isBalanced(self, root):
            
        def check(root):
            if root is None:
                return 0
            left  = check(root.left)
            right = check(root.right)
            if left == -1 or right == -1 or abs(left - right) > 1:
                return -1
            return 1 + max(left, right)
            
        return check(root) != -1



=======================================================================================================
method 2:

Stats:

  - 
  - 


Method:

  - 
  - 


public:
    int depth (TreeNode *root) {
        if (root == NULL) return 0;
        return max (depth(root.left), depth (root.right)) + 1;
    }

    bool isBalanced (TreeNode *root) {
        if (root == NULL) return true;
        
        int left=depth(root.left);
        int right=depth(root.right);
        
        return abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
Iterative, based on postorder traversal:

    def isBalanced(self, root):
        stack = [(0, root)]
        depth = {None: 0}
        while stack:
            seen, node = stack.pop()
            if node is None:
                continue
            if not seen:
                stack.extend([(1, node), (0, node.right), (0, node.left)])
            else:
                if abs(depth[node.left] - depth[node.right]) > 1:
                    return False
                depth[node] = max(depth[node.left], depth[node.right]) + 1
        return True

=======================================================================================================
method 3:

Stats:

  - 
  - 


Method:

  - 
  - 












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

