25. Reverse Nodes in k-Group --- Hard

Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of
nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the nodes, only nodes itself may be changed.

=========================================================================================================================================================
method 1:

key:
	- tail recursive
	- first move cur
    - pre node point to the the answer of sub-problem 


// Runtime: 0 ms, faster than 100.00% 
// Memory Usage: 38.8 MB, less than 24.14%

 class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode node = head;
        int count = 0;

        // check early
        // 1.if less then k node left we just return head 
        while (count < k) { 
            if(node == null) return head;
            node = node.next;
            count++;
        }
        
        //pre node point to the the answer of sub-problem 
        ListNode pre = reverseKGroup(node, k); 
        
        // 2.reverse k node at current level 
        ListNode cur = head;
        while (count > 0) {  
            ListNode next = cur.next; 
            cur.next = pre; 
            pre = cur; 
            cur = next;
            
            count = count - 1;
        }
        return pre;
    }

}

=========================================================================================================================================================


链表：     1 → 2 → 3 → 4 → 5 → 6 → 7, k = 3
期望结果：  3 → 2 → 1 → 6 → 5 → 4 → 7

初始状态：
    dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7
     pre
     end


Step 1️: start = pre.next =1，通过for loop，找到要反转的list的end = 3, nextSublist = 4


    dummy →   1   →   2   →    3   →   4   → 5 → 6 → 7
     pre     start            end  nextSublist
     


Step 2️ 切断子链表: end.next = null;

    现在链表被切成两段：

    dummy → 1 → 2 → 3 → null

    4 → 5 → 6 → 7



Step 3️ 反转子链表,然后pre.next = reverse(start); --> dummy.next = 3
    
    注意：start 仍然指向 原来的 1, 它现在是反转后这段的尾节点

    dummy → 3 → 2 → 1 → null        4 → 5 → 6 → 7
     ↑      ↑       ↑               ↑
     pre   end    start          nextSublist



Step 4️ 接回后半段:start.next = nextSublist;


    dummy → 3 → 2 → 1   →   4   → 5 → 6 → 7
     ↑      ↑       ↑       ↑
    pre    end    start  nextSublist



Step 5️：移动 pre 和 end，为下一轮做准备， pre = end = start = 1;

dummy → 3 → 2 → 1   →   4 → 5 → 6 → 7
                ↑       ↑
             pre/end  nextSublist



class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        while (true) {

            ListNode start = pre.next;

            // 1. 找到要反转的sublist的end
            for (int i = 0; i < k; i++) {
                if(end == null) 
                    break;
                else
                    end = end.next;
            }

             // 不够 k 个，结束
            if (end == null) break;

            // 2. 标记区间
            ListNode nextSublist = end.next;

            // 3. 切断 + 反转
            end.next = null;
            pre.next = reverse(start);

            // 4. 接回
            start.next = nextSublist;

            // 5. 移动 pre 和 end到头
            pre = start;
            end = start;

        }

        return dummy.next;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}


    