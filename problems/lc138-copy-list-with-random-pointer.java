138. Copy List with Random Pointer Medium

A linked list is given such that each node contains an additional random pointer which 
could point to any node in the list or null.

Return a deep copy of the list.

The Linked List is represented in the input/output as a list of n nodes. Each node is represented 
as a pair of [val, random_index] where:
	val: an integer representing Node.val
	random_index: the index of the node (range from 0 to n-1) where random pointer points to, 
	or null if it does not point to any node.
 

Example 1:

Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]


Example 3:

Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Example 4:
Input: head = []
Output: []
Explanation: Given linked list is empty (null pointer), so return null.
 

Constraints:

-10000 <= Node.val <= 10000
Node.random is null or pointing to a node in the linked list.
Number of Nodes will not exceed 1000.

******************************************************
key:
	- Trick solution, hashmap
	- edge case:
		1) empty list, return empty
		2)

******************************************************

// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}


method:

	- Hashmap with pair <old node, new node>
	- 

stats:

	- 
	- 

public Node copyRandomList(Node head) {
    if (head == null) return null;

    Map<Node, Node> map = new HashMap<>();

    // 1. 复制所有节点
    Node cur = head;
    while (cur != null) {
        map.put(cur, new Node(cur.val));
        cur = cur.next;
    }

    // 2. 处理 next 和 random
    cur = head;
    while (cur != null) {
        Node copy = map.get(cur);
        copy.next = map.get(cur.next);
        copy.random = map.get(cur.random);
        cur = cur.next;
    }

    return map.get(head);
}



=======================================================================================================
method O(1) 空间

method:

Step 1：在原节点后插入拷贝节点
  原：A → B → C
  变：A → A' → B → B' → C → C'

  Node cur = head;
  while (cur != null) {
      Node copy = new Node(cur.val);
      copy.next = cur.next;
      cur.next = copy;
      cur = copy.next;
  }

Step 2：设置 random 指针（最妙的一步）
  关键观察

  如果 A.random = C
  那么 A'.random = C.next

  cur = head;
  while (cur != null) {
      if (cur.random != null) {
          cur.next.random = cur.random.next;
      }
      cur = cur.next.next;
  }

Step 3：拆分新旧链表
  Node dummy = new Node(0);
  Node copyCur = dummy;
  cur = head;

  while (cur != null) {
      copyCur.next = cur.next;
      cur.next = cur.next.next;

      copyCur = copyCur.next;
      cur = cur.next;
  }

  return dummy.next;

stats:

	- 
	- 















