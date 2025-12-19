234. Palindrome Linked List - Easy

Given a singly linked list, determine if it is a palindrome.

Example 1:

Input: 1->2
Output: false


Example 2:
Input: 1->2->2->1
Output: true
Follow up:
Could you do it in O(n) time and O(1) space?



******************************************************
key:
	- recursive
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

If we iterate the nodes in reverse using recursion, and iterate forward at the same time 
using a variable outside the recursive function, then we can check whether or not we have a
palindrome.



Stats:

	- time: O(n), space O(n) b/c recursion stack
	- 


class Solution {

    // starts from the begining
    private ListNode frontPointer;

    // currentNode will be at the end of the list at the begining
    private boolean recursivelyCheck(ListNode currentNode) {
        if (currentNode != null) {
            if (!recursivelyCheck(currentNode.next)) return false;
            if (currentNode.val != frontPointer.val) return false;
            frontPointer = frontPointer.next;
        }
        return true;
    }

    public boolean isPalindrome(ListNode head) {
        frontPointer = head;
        return recursivelyCheck(head);
    }
}





isPalindrome(-1, 1)
	

	accumulatedEqual = isPalindrome(-1, 2) = true
	isequal = -1.next.val (2) == 2 	   = true
	curPtr.next = 2
	accumulatedEqual && isEqual = true

	isPalindrome(-1, 2)
		accumulatedEqual = isPalindrome(-1, 1) = true
		isequal = -1.next.val (1) == 1? 	   = true
		curPtr.next = 2
		accumulatedEqual && isEqual = true
			isPalindrome(-1, null): return true


=======================================================================================================
method 2:

Stats:

	- O(n)
	- O(1)


Method:
Step 1️：用快慢指针找到中点
	slow 走一步
	fast 走两步

Step 2️：反转后半部分链表
Step 3️：左右两段同时向中间比较



class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 1. 找中点
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. 如果是奇数长度，跳过中点
        if (fast != null) {
            slow = slow.next;
        }

        // 3. 反转后半段
        ListNode l2 = reverse(slow);
        ListNode l1 = head;

        // 4. 比较前后两段
        while(l2 != null){
            if(l1.val != l2.val){
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return true;

    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



