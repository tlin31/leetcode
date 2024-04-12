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
class Tree  
{ 
    Node root; 
   
    Node buildTree(int in[], int level[])  
    { 
        Node startnode = null; 		// artificial root node
        return constructTree(startnode, level, in, 0, in.length - 1); 
    } 
   
    // main function
    Node constructTree(Node startNode, int[] levelOrder, int[] inOrder,  int inStart, int inEnd)  
    { 
   
        // if start index is more than end index 
        if (inStart > inEnd)   return null; 
   
        if (inStart == inEnd)   return new Node(inOrder[inStart]); 
              
        boolean found = false; 
        int index = 0; 
   
        // it represents the index in inOrder array of element that 
        // appear first in levelOrder array. 
        for (int i = 0; i < levelOrder.length - 1; i++)  
        { 
            int data = levelOrder[i]; 
            for (int j = inStart; j < inEnd; j++)  
            { 
            	// found the node in in order array, then create the node and record its index.
                if (data == inOrder[j])  
                { 
                    startNode = new Node(data); 
                    index = j; 
                    found = true; 
                    break; 
                } 
            } 
            if (found == true) 
                break; 
        } 
   
        //elements present before index are part of left child of startNode. 
        //elements present after index are part of right child of startNode. 
        startNode.setLeft(constructTree(startNode, levelOrder, inOrder,    inStart, index - 1)); 
        startNode.setRight(constructTree(startNode, levelOrder, inOrder,   index + 1, inEnd)); 
        return startNode; 
    } 
   
    /* Utility function to print inorder traversal of binary tree */
    void printInorder(Node node)  
    { 
        if (node == null)   return; 
        printInorder(node.left); 
        System.out.print(node.data + " "); 
        printInorder(node.right); 
    } 
   
    // Driver program to test the above functions 
    public static void main(String args[])  
    { 
        Tree tree = new Tree(); 
        int in[] = new int[]{4, 8, 10, 12, 14, 20, 22}; 
        int level[] = new int[]{20, 8, 22, 4, 12, 10, 14}; 
        int n = in.length; 
        Node node = tree.buildTree(in, level); 
   
        /* Let us test the built tree by printing Inorder traversal */
        System.out.print("Inorder traversal of the constructed tree is "); 
        tree.printInorder(node); 
    } 
} 



=======================================================================================================
method 2:

Stats:

    - 
    - 


Method:

    -  Instead of scanning the preorder array from beginning to end and using inorder array as a 
       kind of mark, in this question, the key point is to scanning the postorder array from end 
       to beginning and also use inorder array from end to beginning as a mark because the logic 
       is more clear in this way. 
       
    -  The core idea is: Starting from the last element of the postorder and inorder array, we put 
       elements from postorder array to a stack and each one is the right child of the last one 
       until an element in postorder array is equal to the element on the inorder array. 

       Then, we pop as many as elements we can from the stack and decrease the mark in inorder 
       array until the peek() element is not equal to the mark value or the stack is empty. 

       Then, the new element that we are gonna scan from postorder array is the left child of the 
       last element we have popped out from the stack.
   

public TreeNode buildTree(int[] inorder, int[] postorder) {
    if (inorder.length == 0 || postorder.length == 0) return null;
    int ip = inorder.length - 1;
    int pp = postorder.length - 1;
    
    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode prev = null;
    TreeNode root = new TreeNode(postorder[pp]);
    stack.push(root);
    pp--;
    
    while (pp >= 0) {
        while (!stack.isEmpty() && stack.peek().val == inorder[ip]) {
            prev = stack.pop();
            ip--;
        }
        TreeNode newNode = new TreeNode(postorder[pp]);
        if (prev != null) {
            prev.left = newNode;
        } else if (!stack.isEmpty()) {
            TreeNode currTop = stack.peek();
            currTop.right = newNode;
        }
        stack.push(newNode);
        prev = null;
        pp--;
    }
    
    return root;
}


