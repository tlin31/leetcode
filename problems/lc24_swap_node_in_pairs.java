24. Swap Nodes in Pairs --- Medium

Given a linked list, swap every two adjacent nodes and return its head.

You may not modify the values in the lists nodes, only nodes itself may be changed.

 
Example:

Given 1->2->3->4, you should return the list as 2->1->4->3.

=========================================================================================================================================================
method 1:

key:
	- recursive solution 
	- when reaches the end, the last recursion will return null to the upper level head.next

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

// Runtime: 0 ms, faster than 100.00% of Java online submissions for Swap Nodes in Pairs.
// Memory Usage: 34.2 MB, less than 100.00%

public class Solution {
    public ListNode swapPairs(ListNode head) {
        if ((head == null) || (head.next == null))
            return head;

        ListNode second = head.next;
        ListNode third = second.next;
        
        second.next = head;
        head.next = swapPairs(third);
        
        return second;
    }
}
=========================================================================================================================================================
method 2:

key:
	- iterative solution 
	- 

// Runtime: 0 ms, faster than 100.00% of Java online submissions for Swap Nodes in Pairs.

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        while (current.next != null && current.next.next != null) {
            ListNode first = current.next;
            ListNode second = current.next.next;
            first.next = second.next;
            current.next = second;
            current.next.next = first;
            current = current.next.next;
        }
        return dummy.next;
    }
    
=========================================================================================================================================================
general solutin to swap nodes
https://www.geeksforgeeks.org/swap-nodes-in-a-linked-list-without-swapping-data/

static Node swapNodes(Node head_ref, int x, int y)  
{  
    Node head=head_ref; 

    // Nothing to do if x and y are same  
    if (x == y)  
        return null;  
  
    Node a = null, b = null;  
  
    // search for x and y in the linked list  
    // and store therir pointer in a and b  
    while (head_ref.next!=null) {  
  
        if ((head_ref.next).data == x) {  
            a = head_ref;  
        }  
  
        else if ((head_ref.next).data == y) {  
            b = head_ref;  
        }  
  
  		// move forward the pointer
        head_ref = ((head_ref).next);  
    }  
  
    // if we have found both a and b  in the linked list swap current pointer and next pointer of these  
    if (a!=null &&  b!=null) {  
	    Node temp = a.next;  
	    a.next = b.next;  
	    b.next = temp;      
	    temp = a.next.next;  
	    a.next.next = b.next.next;  
	    b.next.next = temp;  
    }  
    return head; 
}  


