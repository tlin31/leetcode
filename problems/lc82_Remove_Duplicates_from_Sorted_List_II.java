82. Remove Duplicates from Sorted List II -- Medium


Given the head of a sorted linked list, delete all nodes that have duplicate numbers, 
leaving only distinct numbers from the original list. Return the linked list sorted as well.

Example 1:
Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]

Example 2:
Input: head = [1,1,1,2,3]
Output: [2,3]
 
Constraints:

The number of nodes in the list is in the range [0, 300].
-100 <= Node.val <= 100
The list is guaranteed to be sorted in ascending order.

******************************************************
key:
	- use dummy node
	- edge case:
		1) check empty linked list
		2)

******************************************************



=======================================================================================================
method 1:
The idea is simple, we maintain two pointers, pre, cur in the given List. Pre pointer is 
always referring to one position before the cur pointer. 
When we found pre.val != cur.val && cur.val != cur.next.val, the node referred by cur pointer 
is a unique node.

public ListNode deleteDuplicates(ListNode head) {
    if (head == null) {
        return null;
    }
    
    ListNode dummy = new ListNode(0 == head.val ? 1 : 0);// to guarantee the dummy node is not same as the original head.
    dummy.next = head;
    
    ListNode pre = dummy;
    ListNode cur = head;

    ListNode first = dummy; // the first node in the new unduplicated(result) list.
    
    while (cur != null && cur.next != null) {
        if (cur.val != pre.val && cur.val != cur.next.val) { 
        // we found a unique node, we connect it at the tail of the unduplicated list, and 
        // update the first node.
            first.next = cur;
            first = first.next;
        }
        pre = cur;
        cur = cur.next;
    }
    
    if (pre.val != cur.val) {  // the last node needs to be dealt with independently
        first.next = cur;
        first = first.next;
    }
    
    first.next = null;  // the subsequent list is duplicate.
    
    return dummy.next;
}

=======================================================================================================

public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null||head.next==null) return head;
        
        if(head.val!=head.next.val){
            head.next=deleteDuplicates(head.next);
            return head;
        }
        else{
            while(head.next!=null&&head.val==head.next.val)
                head=head.next;
            
            return   deleteDuplicates(head.next);
        }
        
    }
}







