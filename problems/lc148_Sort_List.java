148. Sort List - Medium

Given the head of a linked list, return the list after sorting it in ascending order.

 

Example 1:

Input: head = [4,2,1,3]
Output: [1,2,3,4]

Example 2:


Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]

Example 3:

Input: head = []
Output: []
 

Constraints:

The number of nodes in the list is in the range [0, 5 * 104].
-105 <= Node.val <= 105
 

Follow up: Can you sort the linked list in O(n logn) time and O(1) memory (i.e. constant space)?


******************************************************
key:
	- divide and conquer & merge sort
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

The Bottom Up approach for merge sort starts by splitting the problem into the smallest subproblem 
and iteratively merge the result to solve the original problem.

First, the list is split into sublists of size 1 and merged iteratively in sorted order. The 
merged list is solved similarly.

The process continues until we sort the entire list.This approach is solved iteratively and can 
be implemented using constant extra space


[Algorithm]

Assume, n is the number of nodes in the linked list.

Start with splitting the list into sublists of size 1. Each adjacent pair of sublists of size 
1 is merged in sorted order. After the first iteration, we get the sorted lists of size 2. 
A similar process is repeated for a sublist of size 2. In this way, we iteratively split the 
list into sublists of size 1,2,4,8.. and so on until we reach n.

To split the list into two sublists of given size beginning from start, we use two pointers, 
mid and end that references to the start and end of second linked list respectively. The split 
process finds the middle of linked lists for the given size.

Merge the lists in sorted order as discussed in Approach 1

As we iteratively split the list and merge, we have to keep track of the previous merged list 
using pointer tail and the next sublist to be sorted using pointer nextSubList.


Stats:

	Time Complexity: O(nlogn), where n is the number of nodes in linked list.
	Let's analyze the time complexity of each step:
	Count Nodes - Get the count of number nodes in the linked list requires O(n) time.

	Split and Merge - This operation is similar to Approach 1 and takes O(nlogn) time.
	For n = 16, the split and merge operation in Bottom Up fashion can be visualized as follows
	
	Space Complexity: O(1) We use only constant space for storing the reference pointers tail , nextSubList etc.


class Solution {
    ListNode tail = new ListNode();
    ListNode nextSubList = new ListNode();

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        int n = getCount(head);
        ListNode start = head;
        ListNode dummyHead = new ListNode();
        for (int size = 1; size < n; size = size * 2) {
            tail = dummyHead;

            while (start != null) {
                if (start.next == null) {
                    tail.next = start;
                    break;
                }
                ListNode mid = split(start, size);
                merge(start, mid);
                start = nextSubList;
            }
            start = dummyHead.next;
        }
        return dummyHead.next;
    }


// Split first sublist of size 'step'

// This method takes the current position (start) and splits the list into two parts:

// First sublist: size nodes long.

// Second sublist: next size nodes long.

// Returns the head of the second sublist.

// ⚡ The tricky part: it uses a fast/slow technique to find the midpoint and endpoint efficiently.

// midPrev walks to the end of the first half.

// end walks to the end of the second half.

// Then it cuts the list into two smaller linked lists by setting .next = null.

// Updates nextSubList to remember where the next group starts.
    ListNode split(ListNode start, int size) {
        ListNode midPrev = start;
        ListNode end = start.next;
        //use fast and slow approach to find middle and end of second linked list
        for (
            int index = 1;
            index < size && (midPrev.next != null || end.next != null);
            index++
        ) {
            if (end.next != null) {
                end = (end.next.next != null) ? end.next.next : end.next;
            }
            if (midPrev.next != null) {
                midPrev = midPrev.next;
            }
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        nextSubList = end.next;
        end.next = null;
        // return the start of second linked list
        return mid;
    }

    void merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode newTail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newTail.next = list1;
                list1 = list1.next;
                newTail = newTail.next;
            } else {
                newTail.next = list2;
                list2 = list2.next;
                newTail = newTail.next;
            }
        }
        newTail.next = (list1 != null) ? list1 : list2;
        // traverse till the end of merged list to get the newTail
        while (newTail.next != null) {
            newTail = newTail.next;
        }
        // link the old tail with the head of merged list
        tail.next = dummyHead.next;
        // update the old tail to the new tail of merged list
        tail = newTail;
    }

    int getCount(ListNode head) {
        int cnt = 0;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            cnt++;
        }
        return cnt;
    }
}