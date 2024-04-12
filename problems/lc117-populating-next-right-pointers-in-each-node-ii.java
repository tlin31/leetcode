117. Populating Next Right Pointers in Each Node II - Medium

Given a binary tree

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next 
pointer should be set to NULL.

Initially, all next pointers are set to NULL.


Input: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":null,"right":null,"val":4},"next":null,"right":{"$id":"4","left":null,"next":null,"right":null,"val":5},"val":2},"next":null,"right":{"$id":"5","left":null,"next":null,"right":{"$id":"6","left":null,"next":null,"right":null,"val":7},"val":3},"val":1}

Output: {"$id":"1","left":{"$id":"2","left":{"$id":"3","left":null,"next":{"$id":"4","left":null,"next":{"$id":"5","left":null,"next":null,"right":null,"val":7},"right":null,"val":5},"right":null,"val":4},"next":{"$id":"6","left":null,"next":null,"right":{"$ref":"5"},"val":3},"right":{"$ref":"4"},"val":2},"next":null,"right":{"$ref":"6"},"val":1}

Explanation: Given the above binary tree (Figure A), your function should populate each next pointer 
to point to its next right node, just like in Figure B.
 

Note:

You may only use constant extra space.
Recursive approach is fine, implicit stack space does not count as extra space for this problem.

******************************************************
key:
	- level order traversal, hashmap, queue
	- edge case:
		1) root node == null
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Queue
	- BFS

stats:

	- O(n) Time complexity
	- O(1) space 

 public Node connect(Node root) {
      if (root == null) return null;
      Queue<Node> queue = new LinkedList<>();
      queue.add(root);
      while (!queue.isEmpty()) {

      	// size of this level
        int size = queue.size();
        Node prev = null;

        // give pointers for this level
        for (int i = 0; i < size; ++i) {
          Node cur = queue.poll();

          // if the right most node
          if (i == size - 1) {
            cur.next = null;
          }

          if (i > 0) {
            prev.next = cur;
          }

          //move forward
          prev = cur;
          if (cur.left != null) queue.add(cur.left);
          if (cur.right != null) queue.add(cur.right);
        }
      }
      return root;
    }


   

=======================================================================================================
method 2:

method:

	- 2 while loops
	- 

stats:

	- O(n) Time complexity
	- O(1) space 
}

public class Solution {
    public void connect(TreeLinkNode root) {
    	if (root == null) return;

        //if the head of the traverse layer is not null, then traverse that layer...
        while(root != null){
            TreeLinkNode tempChild = new TreeLinkNode(0);
            TreeLinkNode currentChild = tempChild;
            while(root!=null){
                if(root.left != null) { 
                	currentChild.next = root.left; 
                	currentChild = currentChild.next;
                }
                if(root.right != null) { 
                	currentChild.next = root.right; 
                	currentChild = currentChild.next;
                }
                root = root.next;
            }
            //after traversed to the end of current layer, move to the next layer

            root = tempChild.next;
        }
    }
}


public void connect(TreeLinkNode root) {
    TreeLinkNode tempChild = new TreeLinkNode(0);
    while (root != null) {
        TreeLinkNode currentChild = tempChild;
        while (root != null) {
            if (root.left != null) {
                currentChild.next = root.left;
                currentChild = currentChild.next;
            }
            if (root.right != null) {
                currentChild.next = root.right;
                currentChild = currentChild.next;
            }
            root = root.next;
        }
        root = tempChild.next;
        tempChild.next = null;
    }
}

=======================================================================================================
method 3:

method:

	- hashmap
	- 

stats:

	- 
	- 

	public Node connect(Node root) {
        HashMap<Integer, Node> map = new HashMap<>();
        helper(root, 0, map);
        return root;
    }

    private void helper(Node root, int depth, HashMap<Integer, Node> map){
        if(root == null){
            return;
        }
        if(map.containsKey(depth)){
            Node n = map.get(depth);
            n.next = root;
        }
        map.put(depth, root);

        helper(root.left, depth+1, map);
        helper(root.right, depth+1, map);
    }


