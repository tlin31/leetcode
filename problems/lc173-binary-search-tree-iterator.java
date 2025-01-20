173. Binary Search Tree Iterator - Medium

Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the 
root node of a BST.

Calling next() will return the next smallest number in the BST.

 

Example:


BSTIterator iterator = new BSTIterator(root);
iterator.next();    // return 3
iterator.next();    // return 7
iterator.hasNext(); // return true
iterator.next();    // return 9
iterator.hasNext(); // return true
iterator.next();    // return 15
iterator.hasNext(); // return true
iterator.next();    // return 20
iterator.hasNext(); // return false
 

Note:

next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of 
	the tree.
You may assume that next() call will always be valid, that is, there will be at least a next smallest 
	number in the BST when next() is called.

******************************************************
key:
	- stack
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- stack
	- once you get to a TreeNode, in order to get the smallest, you need to go all the way down its 
		left branch. So our first step is to point to pointer to the left most TreeNode. 
	- The problem is how to do back trace. Since the TreeNode does not have father pointer, we cannot 
		get a TreeNode s father node in O(1) without store it beforehand. Back to the first step, when 
		we are traversal to the left most TreeNode, we store each TreeNode we met ( They are all father 
		nodes for back trace).
	- 用一个Stack记录从根节点到当前节点的路径。next的时候就返回Stack最上面的元素。不过拿出最上面的元素后，我们还要看
		一下这个被返回的元素是否有右节点，如果有的话，就把它的右节点及右节点的所有左边节点都压入栈中。另外，初始化栈时，
		我们要找到最左边的节点，也就是中序遍历的第一个节点，并把根到第一个节点的路径都压入栈。

	- 

stats:

	- Runtime: 57 ms, faster than 90.70% of Java online submissions for Binary Search Tree Iterator.
	- Memory Usage: 50.4 MB, less than 92.59% o

O(h) memory, hasNext() in O(1) time, But next() is O(h) time.




public class BSTIterator {
	private Stack<TreeNode> stk;
	public BSTIterator(TreeNode root) {
		stk = new Stack<>();
		// 先找到第一个节点，并把路径入栈
		while (root != null) {
			stk.push(root);
			root = root.left;
		}
 
	}
 
	/**
	 * @return whether we have a next smallest number
	 */
	public boolean hasNext() {
		// 栈为空时不再有下一个
		return !stk.isEmpty();
 
	}
 
	/**
	 * @return the next smallest number
	 */
	public int next() {
		TreeNode curr = stk.pop();
		int res = curr.val;
		// 如果该元素有右节点，把它的右节点及右节点的所有左边节点都压入栈中
		curr = curr.right;
		while (curr != null) {
			stk.push(curr);
			curr = curr.left;
		}
		return res;
 
	}
}



=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

public List<Integer> inorderTraversal(TreeNode root) {  
        List<Integer> list = new ArrayList<>();  
        if (root == null)  
            return list;  
        Stack<TreeNode> stack = new Stack<>();  
        while (root != null || !stack.isEmpty()) {  
            if (root != null) {  
                stack.push(root);  
                root = root.left;  
            } else {  
                root = stack.pop();  
                list.add(root.val);  
                root = root.right;  
            }  
        }  
        return list;  
    } 
 

=======================================================================================================
method 3：

 public class BSTIterator {
    private Stack<TreeNode> stack = new Stack<TreeNode>();
    
    public BSTIterator(TreeNode root) {

    	//push the left most node
    	
        pushAll(root);
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode tmpNode = stack.pop();
        pushAll(tmpNode.right);
        return tmpNode.val;
    }
    
    private void pushAll(TreeNode node) {
        for (; node != null; stack.push(node), node = node.left);
    }
}
