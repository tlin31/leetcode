19. Remove Nth Node From End of List --- Medium

Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:

Given n will always be valid.
=========================================================================================================================================================
method 1:

key:
    - 龟兔， 1 fast pointer, 1 slow pointer
    - start = head
    - !! 理清楚，不要跳过所求的node，停在那个node之前，才能skip desired node

// Runtime: 0 ms, faster than 100.00%
// Memory Usage: 34.9 MB, less than 100.00% 

public ListNode removeNthFromEnd(ListNode head, int n) {
    
    ListNode start = new ListNode(0);
    ListNode slow = start, fast = start;
    slow.next = head;
    
    //Move fast in front so that the gap between slow and fast becomes n
    for(int i=1; i<=n+1; i++)   {
        fast = fast.next;
    }
    //Move fast to the end, maintaining the gap
    while(fast != null) {
        slow = slow.next;
        fast = fast.next;
    }
    //Skip the desired node
    slow.next = slow.next.next;
    return start.next;
}





