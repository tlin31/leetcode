92. Reverse Linked List II - Medium

Given the head of a singly linked list and two integers left and right where left <= right, 
reverse the nodes of the list from position left to position right, and return the reversed list.



Example 1:


Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]
Example 2:

Input: head = [5], left = 1, right = 1
Output: [5]
 

Constraints:

The number of nodes in the list is n.
1 <= n <= 500
-500 <= Node.val <= 500
1 <= left <= right <= n
 

Follow up: Could you do it in one pass?
=======================================================================================================
iterative：

- 理论：不断把 first 后面的节点，摘下来，插到 pre 后面

- pre（反转区间前一个节点）：pre一直不变，pre.next 就是反转区间的第一个节点

- first（反转区间“尾巴”）：注意！first 在整个循环中指到的node不变，但是位置上它会被“不断往后顶”

- second（待搬运的节点）：second 是“要被搬到前面的节点”

- 为什么 for 循环是 right - left 次？
  * 因为反转区间长度是：right - left + 1。 但：第一个节点（first）不用动，后面每个节点都要被“搬”一次。👉 所以循环次数是：(right - left)


 public ListNode reverseBetween(ListNode head, int left, int right) {
        if(head == null) return null;
        ListNode dummy = new ListNode(0); 
    
        dummy.next = head;

        // pre来traverse，直到pre的下一个是需要反转的第一个即first
        ListNode pre = dummy; 
        for(int i = 0; i<left-1; i++) 
            pre = pre.next;
        
        ListNode first = pre.next; 
        ListNode second = first.next; 
        
        for(int i=0; i<right-left; i++)
        {
            first.next = second.next;
            second.next = pre.next;
            pre.next = second;
            second = first.next;
        }

        return dummy.next;
    }

图例：
dummy ->  1 -> 2  ->  3 -> 4 -> 5
          ↑    ↑      ↑
         pre  first second



Step 1️：first.next = second.next之后： 

dummy -> 1  -> 2 -> 4 -> 5
         ↑     ↑
        pre   second(3) 已被摘下来


Step 2️：second.next = pre.next （3.next = 2）
        被搬动的节点second插在pre后面

dummy -> 1     3 -> 2 -> 4 -> 5
         ↑     ↑    ↑
        pre   2nd  1st


Step 3️：pre.next = second  （1.next = 3）
        把pre和second连起来

dummy -> 1 ->  3 -> 2 -> 4 -> 5
         ↑     ↑    ↑
        pre   2nd  1st

✔️ 完成一次“头插”



Step 4️：second = first.next (second = 4)
        update second到first后面的一个
        准备下一轮, 也就是把second （4） 查到prev的后面

dummy -> 1 ->  3 -> 2 -> 4 -> 5
         ↑          ↑    ↑
        pre        1st  2nd 


=======================================================================================================

recursive：
class Solution {
    ListNode reverseBetween(ListNode head, int m, int n) {
        // base case
        if (m == 1) {
            return reverseListN(head, n);
        }
        // 前进到反转的起点触发 base case
        head.next = reverseBetween(head.next, m - 1, n - 1); 
        return head;
    }

        // 和前一题一样，变成reverse前N个节点
        ListNode successor = null; // 后驱节点

        public ListNode reverseListN(ListNode head, int n) {
            if(head==null||head.next==null) 
                return head;

            if(n==1){
                // 记录第 n+1 个节点 
                successor = head.next; 
                return head;
            }
            // 以 head.next 为起点，需要反转前 n - 1 个节点 
            ListNode last = reverseListN(head.next, n - 1);
            head.next.next = head;

            // 让反转之后的 head 节点和后⾯面的节点连起来 
            head.next = successor;
            return last;
        }
}

