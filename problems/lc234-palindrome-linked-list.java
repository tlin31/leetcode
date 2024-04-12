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


Stats:

	- 
	- 


Method:
	- use properties of recursion to do it
	-  Example :

		1-> 2-> 3-> 4-> 2-> 1

		ref points 1 initially.
		Make recursive calls until you reach the last element - 1.
		On returning from each recurssion, check if it is equal to ref values. 
		ref values are updated to on each recurssion.
		So first check is ref 1 -  end 1
		Second ref 2 - second last 2 ...and so on.


class Solution {

	public boolean isPalindrome(ListNode head) {
	    ListNode current = new ListNode(-1);
	    current.next = head;
	    return isPalindrome(current, head);
	}

	public boolean isPalindrome(ListNode curPtr, ListNode end) {
	    if (end == null) {
	        return true;
	    }
	    
	    // recursion to go to the last node, store the previous steps
	    boolean accumulatedEqual = isPalindrome(curPtr, end.next);

	    // check the head value with the end
	    boolean isEqual = curPtr.next.val == end.val;

	    // increment current pointer
	    curPtr.next = curPtr.next.next;

	    return accumulatedEqual && isEqual;
	}
}

ex. 1 -> 2 -> 1

after adding pointer:  -1 -> 1 -> 2 -> 1


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


	1. Find the end of the first half.
	2. Reverse the second half.
	3. Determine whether or not there is a palindrome.
	4 .Restore the list.
	5. Return the result.

	- To do step 1, we could count the number of nodes, calculate how many nodes are in the first half,
	  and then iterate back down the list to find the end of the first half. Or, we could do it in a 
	  single parse using the two runners pointer technique. Either is acceptable, however well have a 
	  look at the two runners pointer technique here.

	- Imagine we have 2 runners one fast and one slow, running down the nodes of the Linked List. 
	  In each second, the fast runner moves down 2 nodes, and the slow runner just 1 node. By the time
	  the fast runner gets to the end of the list, the slow runner will be half way. By representing 
	  the runners as pointers, and moving them down the list at the corresponding speeds, we can use 
	  this trick to find the middle of the list, and then split the list into two halves.

	- If there is an odd-number of nodes, then the "middle" node should remain attached to the first half.

	- Step 2 uses the algorithm that can be found in the solution article for the Reverse Linked List
	  problem to reverse the second half of the list.

	- Step 3 is fairly straightforward. Remember that we have the first half, which might also contain a "middle" node at the end, and the second half, which is reversed. We can step down the lists simultaneously ensuring the node values are equal. When the node we're up to in the second list is null, we know we're done. If there was a middle value attached to the end of the first list, it is correctly ignored by the algorithm. The result should be saved, but not returned, as we still need to restore the list.

	- Step 4 requires using the same function you used for step 2, and then for step 5 the saved 
	  result should be returned.



class Solution {

    public boolean isPalindrome(ListNode head) {

        if (head == null) return true;

        // Find the end of first half and reverse second half.
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // Check whether or not there is a palindrome.
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) result = false;
            p1 = p1.next;
            p2 = p2.next;
        }        

        // Restore the list and return the result.
        firstHalfEnd.next = reverseList(secondHalfStart);
        return result;
    }

    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
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



