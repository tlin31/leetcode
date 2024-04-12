206. Reverse Linked List - Easy

Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL

Follow up:
A linked list can be reversed either iteratively or recursively. Could you implement both?


******************************************************
key:
	- recursive & iterative
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	
	-	

Stats:

	- 
	- 

/* iterative solution */
public ListNode reverseList(ListNode head) {
    
    ListNode prev = null;
    ListNode next = null;
    ListNode curr = head;

    while (curr != null) {
        next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}




/* recursive solution in place
public ListNode reverseList(ListNode head) {
    return reverseListInt(head, null);
}

private ListNode reverseListInt(ListNode head, ListNode newHead) {
    if (head == null)
        return newHead;
    ListNode next = head.next;
    head.next = newHead;
    return reverseListInt(next, head);
}



// recursive solution returning a new pointer
public ListNode reverseList(ListNode head) {
    ListNode cur = head;
    return reverseListInt(cur, null);
}

private ListNode reverseListInt(ListNode cur, ListNode newHead) {
    if (cur == null)
        return newHead;
    ListNode next = cur.next;
    cur.next = newHead;
    return reverseListInt(next, cur);
}
=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



