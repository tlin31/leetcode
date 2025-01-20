## Binary search tree

## balanced binary tree
### self balancing tree
- A binary tree is balanced if the height of the tree is O(Log n) where n is the number of nodes. 
- Balanced Binary Search trees are performance-wise good as they provide O(log n) time for search, insert and delete. 

### Advantages of Balanced Binary Tree:
1. Balanced binary trees maintain their height in logarithmic proportion to the number of nodes. This ensures that fundamental operations like insertion, deletion, and search are executed with O(log n) time complexity.
2. Non Destructive: In a balanced binary tree are performed in such a way that the tree remains balanced without requiring a complete reorganization.
3. Balanced binary trees are well-suited for range queries, where you need to find all elements within a specified range.

### How to Check if a Binary Tree is balanced?

	The absolute difference between heights of left and right subtrees at any node should be less than 1.
	For each node, its left subtree should be a balanced binary tree.
	For each node, its right subtree should be a balanced binary tree.

### AVL tree
AVL tree is a self-balancing Binary Search Tree (BST) where the difference between heights of left and right subtrees cannot be more than **one** for all nodes. 


### Red Black tree
Red Black Trees are a type of balanced binary search tree that use a set of rules to maintain balance, ensuring logarithmic time complexity for operations like insertion, deletion, and searching, regardless of the initial shape of the tree. Red Black Trees are self-balancing, using a simple color-coding scheme to adjust the tree after each modification.

Most of the BST operations (e.g., search, max, min, insert, delete.. etc) take O(h) time where h is the height of the BST. The cost of these operations may become O(n) for a skewed Binary tree. If we make sure that the height of the tree remains O(log n) after every insertion and deletion, then we can guarantee an upper bound of O(log n) for all these operations. The height of a Red-Black tree is always O(log n) where n is the number of nodes in the tree. 


#### 1) Properties of Red-Black Trees

1. Node Color: Each node is either red or black.
2. Root Property: The root of the tree is always black.
3. Red Property: Red nodes cannot have red children (no two consecutive red nodes on any path).
4. Black Property: Every path from a node to its descendant null nodes (leaves) has the same number of black nodes.
5. Leaf Property: All leaves (NIL nodes) are black.

#### 2) Insertion of Red-Black Tree

Insertion Steps
1) BST Insert: Insert the new node like in a standard BST.
2) Fix Violations:
- If the parent of the new node is black, no properties are violated.
- If the parent is red, the tree might violate the Red Property, requiring fixes.

Fixing Violations During Insertion

After inserting the new node as a **red** node, we might encounter several cases depending on the colors of the node’s parent and uncle (the sibling of the parent):

- Case 1: Uncle is Red: Recolor the parent and uncle to black, and the grandparent to red. Then move up the tree to check for further violations.

- Case 2: Uncle is Black:
- 2.1: Node is a right child: Perform a left rotation on the parent.
- 2.2: Node is a left child: Perform a right rotation on the grandparent and recolor appropriately.

## Tree Problems/tricks

1. Always check if the node == null!
2. recursive: 第一行一定是if node ==null的break条件
3. iterative：bfs用queue，dfs用stack
4. tree recursion的第一行check也可以是：
```	java		
	if (inStart > inEnd || postStart > postEnd) {
     return null;
  }
```


### Traversals:

		 1
	 2		3
  4	   5	    


Depth First Traversals:
(a) Inorder (Left, Root, Right) : 4 2 5 1 3 
(b) Preorder (Root, Left, Right) : 1 2 4 5 3
(c) Postorder (Left, Right, Root) : 4 5 2 3 1


Breadth First or Level Order Traversal : 1 2 3 4 5 


Inorder Traversal:

Algorithm Inorder(tree)

 			if (node == null)
            return;
      
        // Recur on the left subtree
        inorderTraversal(node.left);
      
        // Visit the current node
        System.out.print(node.data + " ");
      
        // Recur on the right subtree
        inorderTraversal(node.right);
  

Uses of Inorder
- In case of binary search trees (BST), Inorder traversal gives nodes in non-decreasing order.


Preorder Traversal：

Algorithm Preorder(tree)
   1. Visit the root.
   2. Traverse the left subtree, i.e., call Preorder(left-subtree)
   3. Traverse the right subtree, i.e., call Preorder(right-subtree) 
Uses of Preorder
Preorder traversal is used to create a copy of the tree. Preorder traversal is also used to get prefix expression on of an expression tree. Please see http://en.wikipedia.org/wiki/Polish_notation to know why prefix expressions are useful.
Example: Preorder traversal for the above given figure is 1 2 4 5 3.


Postorder Traversal (Practice):

Algorithm Postorder(tree)
   1. Traverse the left subtree, i.e., call Postorder(left-subtree)
   2. Traverse the right subtree, i.e., call Postorder(right-subtree)
   3. Visit the root.
Uses of Postorder
Postorder traversal is used to delete the tree. Please see the question for deletion of tree for details. Postorder traversal is also useful to get the postfix expression of an expression tree. Please see http://en.wikipedia.org/wiki/Reverse_Polish_notation to for the usage of postfix expression.

### Level related questions
- use queue/bfs

template:
```java
class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<Double> res = new LinkedList<>();
        if (root == null)
            return res;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size(); // 当前层元素的个数
            double sum = 0;
            for (int i = 0; i < levelNum; i++) {
                TreeNode curNode = queue.poll();
                sum +=curNode.val;
                //只保存当前层的最后一个元素
                if (i == levelNum - 1) {
                    res.add(sum/levelNum);
                }
                if (curNode.left != null) {
                    queue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    queue.offer(curNode.right);
                }

            }
        }
        return res;
    }
}
```

- use recursion

```java
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        levelHelper(res, root, 0);
        return res;
    }
    
    public void levelHelper(List<List<Integer>> res, TreeNode root, int height) {
        if (root == null) return;

        if (height >= res.size()) {
            res.add(new LinkedList<Integer>());
        }
        
        res.get(height).add(root.val);
        levelHelper(res, root.left, height+1);
        levelHelper(res, root.right, height+1);
    }

```

example:

	199. Binary Tree Right Side View- Medium
	637. Average of Levels in Binary Tree - Easy

### invert binary tree
|226|Invert Binary Tree
https://leetcode.com/problems/invert-binary-tree
[Java](problems/lc226-invert-binary-tree.java)


1. recursion

```java
		public TreeNode invertTree(TreeNode root) {
	    if (root == null) {
	        return null;
	    }
	    final TreeNode left = root.left,
	        		   right = root.right;
	    root.left = invertTree(right);
	    root.right = invertTree(left);
	    return root;
	}
```

2. stack

```java
	if (root == null) {
	        return null;
	    }
	    final Deque < TreeNode > stack = new LinkedList < > ();
	    stack.push(root);
	    while (!stack.isEmpty()) {
	        final TreeNode node = stack.pop();

	        //first store left
	        final TreeNode leftTemp = node.left;

	        //switch left and right
	        node.left = node.right;
	        node.right = leftTemp;
	        
	        if (node.left != null) {
	            stack.push(node.left);
	        }
	        if (node.right != null) {
	            stack.push(node.right);
	        }
	    }
	    return root;

```


3. BFS

```java
	public TreeNode invertTree(TreeNode root) {
	    if (root == null) {
	        return null;
	    }
	    final Queue < TreeNode > queue = new LinkedList < > ();

	    // add root to the queue
	    queue.offer(root);

	    while (!queue.isEmpty()) {
	    	// get the head out 
	        final TreeNode node = queue.poll();
	        final TreeNode leftTemp = node.left;
	        node.left = node.right;
	        node.right = leftTemp;
	        if (node.left != null) {
	            queue.offer(node.left);
	        }
	        if (node.right != null) {
	            queue.offer(node.right);
	        }
	    }
	    return root;
	}
```



### calculate unique binary search trees

求当前根的数量，只需要左子树的数量乘上右子树

### return all Unique Binary Search Trees given int n

!!! 95. Unique Binary Search Trees II - Medium

solution：
  1. recursion
```java
		List<TreeNode> leftTrees = getAns(start, i - 1);
           //得到所有可能的右子树
          List<TreeNode> rightTrees = getAns(i + 1, end);
          //左子树右子树两两组合
          for (TreeNode leftTree : leftTrees) {
              for (TreeNode rightTree : rightTrees) {
                  TreeNode root = new TreeNode(i);
                  root.left = leftTree;
                  root.right = rightTree;
                  //加入到最终结果中
                  ans.add(root);
              }
          }
```
 
  2. dp
  	- 方法1： 同构， link tree nodes & subtrees
  	- 方法2：首先我们每次新增加的数字大于之前的所有数字，所以新增加的数字出现的位置只可能是根节点或者是根节点的右孩子，
  		右孩子的右孩子，右孩子的右孩子的右孩子等等，总之一定是右边。
  	- 其次，新数字所在位置原来的子树，改为当前插入数字的左孩子即可，因为插入数字是最大的。






### next() for binary search tree
1. 用一个Stack记录从根节点到当前节点的路径。next的时候就返回Stack最上面的元素。
2. 拿出最上面的元素后，我们还要看一下这个被返回的元素是否有右节点，如果有的话，就把它的右节点及右节点的所有左边节点都压入栈中
3. 另外，初始化栈时，我们要找到最左边的节点，也就是中序遍历的第一个节点，并把根到第一个节点的路径都压入栈。

### build a BST from sorted array list

key: 
	- 首先我们每次新增加的数字大于之前的所有数字，所以新增加的数字出现的位置只可能是根节点或者是根节点的右孩子，右孩子的右孩子，右孩子的右孩子的右孩子等等，总之一定是右边。

	- 其次，新数字所在位置原来的子树，改为当前插入数字的左孩子即可，因为插入数字是最大的。


### find a swapped pair noeds in BST
- in-order traversal gives a sorted ascending list, can use this property
- leetcode #99


## TreeMap

### tail map

- The java.util.TreeMap.tailMap(from_Key) method in Java is used to get a part or view of the map whose keys are greater than equal to the from_key in the parameter. Any changes made in one map will reflect the change in the other map.

Syntax:

	Tree_Map.tailMap(from_Key)

Parameters: The method takes one parameter from_key of the Key type in the TreeMap and refers to the key which is set as the lower point greater than whose mappings are to be returned.

Return Value: The method returns the portion of the mapping whose keys are greater than the from_Key.


