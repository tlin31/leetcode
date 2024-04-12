445. Add Two Numbers II - Medium

You are given two non-empty linked lists representing two non-negative integers. The most 
significant digit comes first and each of their nodes contain a single digit. 

Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Follow up:
What if you cannot modify the input lists? In other words, reversing the lists is not allowed.

Example:

Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7

******************************************************
key:
	- ！！尾部对齐，example中要4和3相加，6和4相加，etc. --> reverse list
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- use 2 stack
	- 

stats:

	- Runtime: 6 ms, faster than 51.95% of Java online submissions for Add Two Numbers II.
	- Memory Usage: 45.4 MB, less than 67.65%



    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();
        
        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }

        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }
        
        int sum = 0;
        ListNode list = new ListNode(0);
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) 
            	sum += s1.pop();
            if (!s2.empty()) 
            	sum += s2.pop();

            list.val = sum % 10;
            ListNode head = new ListNode(sum / 10);
            head.next = list;
            list = head;
            sum /= 10;
        }
        
        return list.val == 0 ? list.next : list;
    }




=======================================================================================================
method 2:

method:

	- recursive pattern
	- 

stats:

	- 
	- 
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int size1 = getLength(l1);
        int size2 = getLength(l2);
        ListNode head = new ListNode(1);
        // Make sure l1.length >= l2.length
        head.next = size1 < size2 ? helper(l2, l1, size2 - size1) : helper(l1, l2, size1 - size2);
        // Handle the first digit
        if (head.next.val > 9) {
            head.next.val = head.next.val % 10;
            return head;
        }
        return head.next;
    }
    // get length of the list
    public int getLength(ListNode l) {
        int count = 0;
        while(l != null) {
            l = l.next;
            count++;
        }
        return count;
    }
    // offset is the difference of length between l1 and l2
    public ListNode helper(ListNode l1, ListNode l2, int offset) {
        if (l1 == null) return null;
        // check whether l1 becomes the same length as l2
        ListNode result = offset == 0 ? new ListNode(l1.val + l2.val) : new ListNode(l1.val);
        ListNode post = offset == 0 ? helper(l1.next, l2.next, 0) : helper(l1.next, l2, offset - 1);
        // handle carry 
        if (post != null && post.val > 9) {
            result.val += 1;
            post.val = post.val % 10;
        }
        // combine nodes
        result.next = post;
        return result;
    }
=======================================================================================================
method 3:

method:

	- manually reverse the list
	- 

stats:

	- 
	- 

public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode n1 = reverse(l1);
        ListNode n2 = reverse(l2);
        int carry = 0;
        ListNode temp = n1;
        ListNode pre = n1;
        while(n1!= null || n2 != null || carry != 0){
            int v1 = n1 == null? 0: n1.val;
            int v2 = n2 == null? 0: n2.val;
            if(n1 == null){
                n1 = new ListNode((v1+v2+carry) % 10);
                pre.next = n1;
            }else{
                n1.val = (v1+v2+carry) % 10;
            }
            carry = (v1+v2+carry)/10;
            pre = n1;
            n1 = n1 == null? null : n1.next;
            n2 = n2 == null? null : n2.next;
        }
        return reverse(temp);
    }
    public ListNode reverse(ListNode head){
        ListNode newHead = null;
        while(head != null){
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

