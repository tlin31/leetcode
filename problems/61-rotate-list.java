61. Rotate List - Medium

Given a linked list, rotate the list to the right by k places, where k is non-negative.

Example 1:
Input: 1->2->3->4->5->NULL, k = 2
Output: 4->5->1->2->3->NULL
Explanation:
rotate 1 steps to the right: 5->1->2->3->4->NULL
rotate 2 steps to the right: 4->5->1->2->3->NULL

Example 2:
Input: 0->1->2->NULL, k = 4
Output: 2->0->1->NULL
Explanation:
rotate 1 steps to the right: 2->0->1->NULL
rotate 2 steps to the right: 1->2->0->NULL
rotate 3 steps to the right: 0->1->2->NULL
rotate 4 steps to the right: 2->0->1->NULL


******************************************************
key:
	- node, use mode to escape the edge situation 
	- !! if list length = 3, but k = 5, then use mode
	- edge case:
		1) empty list, return empty --> if head == null or k = 0, return list
		2) 

******************************************************


=======================================================================================================
method 1:

method:

	- traverse twice, first time record length, and then calculate which position is the new start
	- 很明显我们不需要真的一个一个移，如果链表长度是 len， n = k % len，我们只需要将末尾 n 个链表节点整体移动到最
		前边就可以了。我们只需要找到倒数 n + 1 个节点的指针把它指向 null，以及末尾的指针指向头结点就可以了。

stats:

	- 时间复杂度：O ( n ) 
	- 空间复杂度：O（1）
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate List.
	- Memory Usage: 37.7 MB, less than 79.31% 

	public ListNode rotateRight(ListNode head, int n) {
	    if (head == null || head.next == null) return head;
	    ListNode dummy = new ListNode(0);
	    dummy.next = head;
	    ListNode fast = dummy, slow = dummy;

	    int length;
	    for (length = 0; fast.next != null; length++) //Get the total length
	        fast = fast.next;
	    for (int j = length - n % length; j > 0; j--) //Get the i-n%i th node
	        slow = slow.next;
	    fast.next = dummy.next; //Do the rotation
	    dummy.next = slow.next;
	    slow.next = null;
	    return dummy.next; // which is head
	}
-----------------------------------
    //尾指针指向头结点
    tail.next = head;
    //头指针更新为倒数第 n 个节点
    head = slow.next;
    //尾指针置为 null
    slow.next = null;
    return head;



