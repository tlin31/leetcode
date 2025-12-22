61. Rotate List - Medium

Given the head of a linked list, rotate the list to the right by k places.

 
Example 1:

Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]

Example 2:
Input: head = [0,1,2], k = 4
Output: [2,0,1]
 

Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 109

******************************************************
key:
	- first mod,
	- edge case:
		1) first command is get, return error or '#'
		2)

******************************************************



====================================================================================================
method 1:

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null||head.next==null) return head;
        ListNode dummy = new ListNode();
        dummy.next = head;

        int size = 0;
        ListNode cur = head;
        for (size = 1; cur.next != null; size++)
            cur = cur.next;

        // make the list circular
        cur.next = head;

        k = k%size; 

        // find new tail : (n - k - 1)th node
        // and new head : (n - k)th node
        ListNode new_tail = head;
        for (int i = 0; i < size - k - 1; i++) 
            new_tail = new_tail.next;

        ListNode new_head = new_tail.next;
        new_tail.next = null;
        return new_head;


        
    }
}





