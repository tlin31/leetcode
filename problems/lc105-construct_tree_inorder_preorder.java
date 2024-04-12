105. Construct Binary Tree from Preorder and Inorder Traversal - Medium



Given preorder and inorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]

Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7



******************************************************
key:
    - InOrder traversal is Left-Root-Right, PreOrder traversal is Root-Left-Right
    - stack + hashmap or stack + set
    - edge case:
        1) empty string, return empty
        2)

******************************************************



------------------------------------------------------------------------------------------------------------------------
method 1:

    The first element in the preorder list is a root. 
    This root splits inorder list into left and right subtrees. 
    Now one have to pop up the root from preorder list since it is already used as a tree node and 
    then repeat the step above for the left and right subtrees.

class Solution {
  // start from first preorder element
  int pre_idx = 0;
  int[] preorder;
  int[] inorder;
  HashMap<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

  // main function
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    this.preorder = preorder;
    this.inorder = inorder;

    // build a hashmap value -> its index
    int idx = 0;
    for (Integer val : inorder)
      idx_map.put(val, idx++);
  
    return helper(0, inorder.length);
  }

  public TreeNode helper(int in_left, int in_right) {
    // if there is no elements to construct subtrees
    if (in_left == in_right)
      return null;

    // pick up pre_idx element as a root
    int root_val = preorder[pre_idx];
    TreeNode root = new TreeNode(root_val);

    // root splits inorder list into left and right subtrees
    int index = idx_map.get(root_val);

    // recursion 
    pre_idx++;
    // build left subtree
    root.left = helper(in_left, index);
    // build right subtree
    root.right = helper(index + 1, in_right);
    return root;
  }

}


--------------------------------------------------------------------------------------------------------------------------------
method 2:
map + stack:

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // deal with edge case(s)
        if (preorder.length == 0) {
            return null;
        }
        
        // build a map of the indices of the values as they appear in the inorder array
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        
        // initialize the stack of tree nodes
        Stack<TreeNode> stack = new Stack<>();
        int value = preorder[0];
        TreeNode root = new TreeNode(value);
        stack.push(root);
        
        // for all remaining values, loop thru preorder list
        for (int i = 1; i < preorder.length; i ++) {
            // create a node
            value = preorder[i];
            TreeNode curNode = new TreeNode(value);
            
            // index of cur. < index of root in the map, cur. is on the left of the last node
            if (map.get(value) < map.get(stack.peek().val)) {
                stack.peek().left = curNode;
            } 

            // cur. is on the right of the last node,--> the right child of either the last node
            // or one of the last node's ancestors
            // pop the stack until we either run out of ancestors
            // or the node at the top of the stack is to the right of the new node
            else {
                TreeNode parent = null;
                while(!stack.isEmpty() && map.get(value) > map.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = curNode;
            }
            stack.push(curNode);
        }
        
        return root;
    }

ex. pre = 3,9,20, 15, 7. in = 9, 3, 15, 20, 7
                              0, 1, 2,  3,  4

root = pre[0] = 3
i = 1:
    stack: 3
    curNode = 9
    b/c map.get(9) < map.get(3) --> 0 < 1 in map, curNode is the left child of the last node in stack
    stack: 3, 9

    3
   /
  9

i = 2:
    stack: 3,9
    curNode = 20
    b/c map.get(20) > map.get(9) --> 20 is the right child of 9
    keep popping all the 'left subtree' nodes from the stack to get to the root, parent = 3
    3.right = 20
    
    3
   / \
  9  20

------------------------------------------------------------------------------------------------------------------------------------------------
method 3: stack + set  --> fast + ??  (https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/)
Maintain two data-structures : Stack (to store the path we visited while traversing PreOrder array) and Set (to maintain the node in which the next right subtree is expected).
    

    static Set<TreeNode> set = new HashSet<>(); 
    static Stack<TreeNode> stack = new Stack<>(); 
  
    // Function to build tree using given traversal 
    public TreeNode buildTree(int[] preorder, int[] inorder) 
    { 
  
        TreeNode root = null; 
        for (int pre = 0, in = 0; pre < preorder.length;) { 
  
            TreeNode node = null; 
            do { 
                node = new TreeNode(preorder[pre]); 
                if (root == null) { 
                    root = node; 
                } 
                if (!stack.isEmpty()) { 
                    if (set.contains(stack.peek())) { 
                        set.remove(stack.peek()); 
                        stack.pop().right = node; 
                    } 
                    else { 
                        stack.peek().left = node; 
                    } 
                } 
                stack.push(node); 
            } while (preorder[pre++] != inorder[in] && pre < preorder.length); 
  
            node = null; 
            while (!stack.isEmpty() && in < inorder.length && stack.peek().val == inorder[in]) { 
                node = stack.pop(); 
                in++; 
            } 
  
            if (node != null) { 
                set.add(node); 
                stack.push(node); 
            } 
        } 
  
        return root; 
    } 
  
