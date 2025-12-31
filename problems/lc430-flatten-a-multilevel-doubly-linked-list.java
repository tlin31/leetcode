430. Flatten a Multilevel Doubly Linked List - Medium

https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/


You are given a doubly linked list which in addition to the next and previous pointers, it 
could have a child pointer, which may or may not point to a separate doubly linked list. 
These child lists may have one or more children of their own, and so on, to produce a multilevel 
data structure, as shown in the example below.

Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are 
given the head of the first level of the list.

 

Example 1:

Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
Output: [1,2,3,7,8,11,12,9,10,4,5,6]
Explanation:

The multilevel linked list in the input is as follows:



After flattening the multilevel linked list it becomes:


Example 2:

Input: head = [1,2,null,3]
Output: [1,3,2]
Explanation:

The input multilevel linked list is as follows:

  1---2---NULL
  |
  3---NULL


Example 3:

Input: head = []
Output: []
 

How multilevel linked list is represented in test case:

We use the multilevel linked list from Example 1 above:

 1---2---3---4---5---6--NULL
         |
         7---8---9---10--NULL
             |
             11--12--NULL
The serialization of each level is as follows:

[1,2,3,4,5,6,null]
[7,8,9,10,null]
[11,12,null]
To serialize all levels together we will add nulls in each level to signify no node connects to the upper node of the previous level. The serialization becomes:

[1,2,3,4,5,6,null]
[null,null,7,8,9,10,null]
[null,11,12,null]
Merging the serialization of each level and removing trailing nulls we obtain:

[1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
 

Constraints:

Number of Nodes will not exceed 1000.
1 <= Node.val <= 10^5


******************************************************
key:
	- dfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************


class Solution {
    public Node flatten(Node head) {
        if (head == null) return null;

        Stack<Node> stack = new Stack<>();
        Node cur = head;

        while (cur != null) {

            // 如果有 child
            if (cur.child != null) {
 
                // 如果有 next，先存起来
                if (cur.next != null) {
                    stack.push(cur.next);
                    cur.next.prev = null;
                }

                // child 接到 next
                cur.next = cur.child;
                cur.child.prev = cur;
                cur.child = null;
            }

            // 如果走到当前链表末尾，且栈不空
            if (cur.next == null && !stack.isEmpty()) {
                Node next = stack.pop();
                cur.next = next;
                next.prev = cur;
            }

            cur = cur.next;
        }

        return head;
    }
}



=======================================================================================================
method 1:

method:

	- recursion
	- We always keep a pre-global variable to keep track of the last node we visited and connect 
	  current node head with this pre node. 
	  So for each recursive call, we do:
		1. Connect last visited node with current node by letting pre.next point to current 
		   node head and current node prev point to pre
		2. Mark current node as pre. pre = head
		3. If there is head.child, we recursively visit and flatten its child node
		4. Recursively visit and flatten its next node

stats:
	- Time Complexity: O(N)
	- Space Complexity: O(N)
	- Runtime: 0 ms, faster than 100.00% 
	- Memory Usage: 38 MB, less than 70.00% 


/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node() {}

    public Node(int _val,Node _prev,Node _next,Node _child) {
        val = _val;
        prev = _prev;
        next = _next;
        child = _child;
    }
};
*/
class Solution {
  public Node flatten(Node head) {
    if (head == null) return head;
    // pseudo head to ensure the `prev` pointer is never none
    Node pseudoHead = new Node(0, null, head, null);

    flattenDFS(pseudoHead, head);

    // detach the pseudo head from the real head
    pseudoHead.next.prev = null;
    return pseudoHead.next;
  }
  /* return the tail of the flatten list */
  public Node flattenDFS(Node prev, Node curr) {
    if (curr == null) return prev;
    curr.prev = prev;
    prev.next = curr;

    // the curr.next would be tempered in the recursive function
    Node tempNext = curr.next;

    Node tail = flattenDFS(curr, curr.child);
    curr.child = null;

    return flattenDFS(tail, tempNext);
  }
}


=======================================================================================================
method 2:

method:

	- stack
	- 

stats:

	- Runtime: 0 ms, faster than 100.00% 
	- Memory Usage: 38 MB, less than 67.50% 


class Solution {
    public Node flatten(Node head) {
        Stack<Node> stack = new Stack<>();
        Node travel = head;
        while(travel != null || !stack.isEmpty()) {
            if(travel.child != null) {
            	// start flatten
                if(travel.next != null) 
                	stack.push(travel.next);
                travel.next = travel.child;
                travel.next.prev = travel;
                travel.child = null;
            } else {
                if(travel.next == null && !stack.isEmpty()) {
                    travel.next = stack.pop();
                    travel.next.prev = travel;
                }
            }
            travel = travel.next;
        }
        return head;
    }
}

