106. Construct Binary Tree from Inorder and Postorder Traversal - Medium

Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1:
https://www.geeksforgeeks.org/construct-tree-inorder-level-order-traversals-set-2/


Stats:

    - Time Complexity: O(N^2), uses the hash table 

    - 


Method:

    -   
    -   

Ex.
	inorder = [9,3,15,20,7]
    postorder = [9,15,7,20,3]

Algo:

    1. Build hashmap (value -> its index) for inorder traversal.

    2. Return helper function which takes as the arguments the left and right boundaries for 
       the current subtree in the inorder traversal. 

       These boundaries are used only to check if the subtree is empty or not. 


helper(in_left = 0, in_right = n - 1):

    1. If in_left > in_right, the subtree is empty, return None.

    2. Pick the last element in postorder traversal as a root.

    3. Root value has index index in the inorder traversal, elements from in_left to index - 1 
       belong to the left subtree, and elements from index + 1 to in_right belong to the right subtree.

    4. Following the postorder logic, proceed recursively first to construct the right subtree 
       helper(index + 1, in_right) and then to construct the left subtree helper(in_left, index - 1).

    5. Return root.

class Solution {
  int post_idx;
  int[] postorder;
  int[] inorder;
  HashMap<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

  // main function
  public TreeNode buildTree(int[] inorder, int[] postorder) {
    this.postorder = postorder;
    this.inorder = inorder;

    // start from the last postorder element
    post_idx = postorder.length - 1;

    // build a hashmap value -> its index
    int idx = 0;
    for (Integer val : inorder)
      idx_map.put(val, idx++);
    return helper(0, inorder.length - 1);
  }

  public TreeNode helper(int in_left, int in_right) {
    // if there is no elements to construct subtrees
    if (in_left > in_right)
      return null;

    // pick up post_idx element as a root
    int root_val = postorder[post_idx];
    TreeNode root = new TreeNode(root_val);

    // root splits inorder list
    // into left and right subtrees
    int index = idx_map.get(root_val);

    // recursion 
    post_idx--;
    // build right subtree
    root.right = helper(index + 1, in_right);
    // build left subtree
    root.left = helper(in_left, index - 1);
    return root;
  }


}


------------------------------------------------------------------------------------
class Solution {public TreeNode buildTree(int[] inorder, int[] postorder) {
    Map<Integer,Integer> inorderMap = new HashMap<>();

    for(int i=0;i<inorder.length;i++) inorderMap.put(inorder[i],i);

    return buildTreeFromPostIn(0,inorder.length-1,postorder,0,
        postorder.length-1,inorderMap);
}
private TreeNode buildTreeFromPostIn(int inorderStart, int inorderEnd, 
int[] post, int postStart, int postEnd, Map<Integer,Integer> inorderMap){

    if(inorderStart>inorderEnd || postStart>postEnd) return null;

    TreeNode root = new TreeNode(post[postEnd]);
    int rootIndex = inorderMap.get(post[postEnd]);

    int leftSubTree= rootIndex-inorderStart;

    root.left=buildTreeFromPostIn(inorderStart,rootIndex-1,post,
        postStart,postStart+leftSubTree-1,inorderMap);

    root.right=buildTreeFromPostIn(rootIndex+1,inorderEnd,post,
        postStart+leftSubTree,postEnd-1,inorderMap);
    return root;
}
}



=======================================================================================================
method 2: stack

Stats:

    - 
    - 

Method:
    -  scan the postorder array from end to beginning and 
       also use inorder array from end to beginning as a mark 
       
    -  The core idea is: Starting from the last element of the postorder and inorder array, we put 
       elements from postorder array to a stack and each one is the right child of the last one 
       until an element in postorder array is equal to the element on the inorder array. 

       Then, we pop as many as elements we can from the stack and decrease the mark in inorder 
       array until the peek() element is not equal to the mark value or the stack is empty. 

       Then, the new element that we are gonna scan from postorder array is the left child of the 
       last element we have popped out from the stack.
   

class Solution 
{
    public TreeNode buildTree(int[] inorder, int[] postorder) {
    // If either of the input arrays are empty, the tree is empty, so return null
    if (inorder.length == 0 || postorder.length == 0) return null;
    
    // Initialize indices to the last elements of the inorder and postorder traversals
    int ip = inorder.length - 1;
    int pp = postorder.length - 1;

    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode prev = null;
    TreeNode root = new TreeNode(postorder[pp]);
    // Push the root onto the stack and move to the next element in the postorder traversal
    stack.push(root);
    pp--;

    // Process the rest of the nodes in the postorder traversal
    while (pp >= 0) {
        // While the stack is not empty and the top of the stack is the current inorder element
        while (!stack.isEmpty() && stack.peek().val == inorder[ip]) {
            // The top of the stack is the parent of the current node, so pop it off the stack and update prev
            prev = stack.pop();
            ip--;
        }

        // Create a new node for the current postorder element
        TreeNode newNode = new TreeNode(postorder[pp]);
        // If prev is not null, the parent of the current node is prev, so attach the node 
        // as the left child of prev
        if (prev != null) {
            prev.left = newNode;
        } 
        
        // If prev is null, the parent of the current node is the current top of the stack, so 
        // attach the node as the right child of the current top of the stack
        else if (!stack.isEmpty()) {
            TreeNode currTop = stack.peek();
            currTop.right = newNode;
        }
        // Push the new node onto the stack, reset prev to null, and move to the next element in the postorder traversal
        stack.push(newNode);
        prev = null;
        pp--;
    }

    // Return the root of the binary tree
    return root;
    }
}






