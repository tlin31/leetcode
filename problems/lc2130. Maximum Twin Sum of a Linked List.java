2130. Maximum Twin Sum of a Linked List - Medium
Topics
conpanies icon
Companies
Hint
In a linked list of size n, where n is even, the ith node (0-indexed) of the linked list is known as the twin of the (n-1-i)th node, if 0 <= i <= (n / 2) - 1.

For example, if n = 4, then node 0 is the twin of node 3, and node 1 is the twin of node 2. These are the only nodes with twins for n = 4.
The twin sum is defined as the sum of a node and its twin.

Given the head of a linked list with even length, return the maximum twin sum of the linked list.

 

Example 1:


Input: head = [5,4,2,1]
Output: 6
Explanation:
Nodes 0 and 1 are the twins of nodes 3 and 2, respectively. All have twin sum = 6.
There are no other nodes with twins in the linked list.
Thus, the maximum twin sum of the linked list is 6. 
Example 2:


Input: head = [4,2,2,3]
Output: 7
Explanation:
The nodes with twins present in this linked list are:
- Node 0 is the twin of node 3 having a twin sum of 4 + 3 = 7.
- Node 1 is the twin of node 2 having a twin sum of 2 + 2 = 4.
Thus, the maximum twin sum of the linked list is max(7, 4) = 7. 
Example 3:


Input: head = [1,100000]
Output: 100001
Explanation:
There is only one node with a twin in the linked list having twin sum of 1 + 100000 = 100001.
 

Constraints:

The number of nodes in the list is an even integer in the range [2, 105].
1 <= Node.val <= 105 


******************************************************
key:
	- 与 lc234 check linked list is palindrome类似
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: recursive

Method:

	-	



Stats:

	- 
	- 

class Solution {
    ListNode frontNode;
    int maxSum = 0;
    public int pairSum(ListNode head) {
        frontNode = head;
        recursion(head);
        return maxSum;
    }

    private int recursion(ListNode cur){
        if (cur.next == null) return cur.val;
        int lastNodeValue = recursion(cur.next);
        int curSum = lastNodeValue+frontNode.val;
        maxSum = Math.max(maxSum, curSum);
        frontNode = frontNode.next;
     
        return cur.val;
    }


===================================================================================================
Method 2: reverse second half + slow fast pointer



class Solution {
    public int pairSum(ListNode head) {
        // reverse the second half the linked list, then add sum

        if(head==null) return 0;

        int maxSum = 0, curSum = 0;

        ListNode fast = head;
        ListNode slow = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode secondHalf = reverse(slow);
        ListNode firstHalf = head;

        
        while(secondHalf!=null){
            curSum=firstHalf.val+secondHalf.val;
            maxSum = Math.max(curSum,maxSum);

            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return maxSum;
        
    }

    private ListNode reverse(ListNode head){

        ListNode pre = null;
        ListNode cur = head;
        while(cur!=null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;

        }
        return pre;
        
    }
}
