25. Reverse Nodes in k-Group --- Hard

Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of
nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the nodes, only nodes itself may be changed.

=========================================================================================================================================================
method 1:

key:
	- tail recursive
	- first move cur
    - pre node point to the the answer of sub-problem 

// Runtime: 0 ms, faster than 100.00% 
// Memory Usage: 38.8 MB, less than 24.14%

 public ListNode reverseKGroup(ListNode head, int k) {
    ListNode node = head;
    int count = 0;

 	// check early
    // 1. test wheather we have more then k node left, if less then k node left we just return head 
    //    if not, then ends with count = k, and proceed to the recursion
    while (count < k) { 
        if(node == null) return head;
        node = node.next;
        count++;
    }
    
    //pre node point to the the answer of sub-problem 
    ListNode pre = reverseKGroup(node, k); 
    
    // 2.reverse k node at current level 
    while (count > 0) {  
        ListNode next = head.next; 
        head.next = pre; 
        pre = head; 
        head = next;
        count = count - 1;
    }
    return pre;
}

=========================================================================================================================================================
method 2:

key:

	- non-recursive

// Runtime: 1 ms, faster than 37.44% 
// Memory Usage: 38.7 MB, less than 24.14% 

public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;

        //now, n = length of linked list
        for (ListNode i = head; i != null; n++, i = i.next);
        
        ListNode dmy = new ListNode(0);
        dmy.next = head;
        
        for(ListNode prev = dmy, tail = head; n >= k; n -= k) {
            for (int i = 1; i < k; i++) {
                ListNode next = tail.next.next;
                tail.next.next = prev.next;
                prev.next = tail.next;
                tail.next = next;
            }
            
            prev = tail;
            tail = tail.next;
        }
        return dmy.next;
    }


    