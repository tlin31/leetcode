23. Merge k Sorted Lists --- Hard

Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6

k == lists.length
0 <= k <= 104
0 <= lists[i].length <= 500
-104 <= lists[i][j] <= 104
lists[i] is sorted in ascending order.
The sum of lists[i].length will not exceed 104.



=========================================================================================================================================================
method 1:

key:
	- recursion free
	- no extra memory, decrease end of lists each time when merge two
	- lists[i] = mergeTwo(lists[i*2],lists[i*2+1]) --> dont need to delete list!!!


// Runtime: 2 ms, faster than 98.72% 
// Memory Usage: 42.2 MB, less than 30.06% 

// O(nlgk) time and O(1) space


public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;

        int len = lists.length;  

        // keep merging 2 list until there's only 1 list left               
        while(len != 1){
            for(int i = 0;i<len/2;i++){
                lists[i] = mergeTwo(lists[i*2],lists[i*2+1]);
            }

            //move the last one
            if(len % 2 == 1){                   
                lists[len/2] = lists[len-1];
            }

            //decrease list size to half
            len = (len+1)/2;                    
        }
        return lists[0];
    }

    private ListNode mergeTwo(ListNode node1, ListNode node2){
        if(node1 == null) return node2;
        if(node2 == null) return node1;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        // recursion free! 
        while(node1 != null || node2 != null){

            //key!!!! if node2 == null, then nothing left in node 2, always append node 1
            if(node2 == null || node1 != null && node1.val < node2.val){
                curr.next = node1;
                node1 = node1.next;
            }else{
                curr.next = node2;
                node2 = node2.next;
            }
            //move to node 1 or node 2
            curr = curr.next;
        }
        return dummy.next;
    }



=========================================================================================================================================================
method 2: priority queue 

✨思想

每个链表的头节点都是当前该链表最小元素

把所有非空链表的头节点放入一个 小顶堆

每次从堆中弹出最小的节点，把它加入结果链表

若该节点有 next，则把 next 再放入堆

直到堆空

堆的大小最多为 k，插入/弹出都是 log k。

时间：O(N log k)，N 是节点总数，k is the number of linked lists.

空间：O(k)

key:

// Runtime: 35 ms, faster than 42.54% of Java online submissions for Merge k Sorted Lists.
// Memory Usage: 43.3 MB, less than 26.78% 

Time complexity : O(Nlogk) where k is the number of linked lists.

The comparison cost will be reduced to O(logk) for every pop and insertion to priority queue. 
But finding the node with the smallest value just costs O(1) time.
There are N nodes in the final linked list.



class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists==null || lists.length==0) return null;
        
        // min heap
        PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.length, (a,b)-> a.val-b.val);
        
        ListNode dummy = new ListNode(0);
        ListNode tail=dummy;
        
        // 所有链表的头部入堆        
        for (ListNode node:lists)
            if (node!=null)
                queue.add(node);
            
        while (!queue.isEmpty()){
            tail.next=queue.poll(); //min node
            tail=tail.next;
            
            if (tail.next!=null)
                queue.add(tail.next);
        }
        return dummy.next;
    }

}

=========================================================================================================================================================
method 3: 

key:

// Runtime: 1 ms, faster than 100.00% of Java online submissions for Merge k Sorted Lists.
// Memory Usage: 44 MB, less than 24.05%

public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        return mL(lists, 0, lists.length - 1);
    }
    
    private ListNode mL(ListNode[] lists, int l, int r) {
        //edge  case
        if (r < l) return null;
        if (r == l) return lists[r];
        
        int mid = (l + r) / 2;
        ListNode a = mL(lists, l, mid), 
        		 b = mL(lists, mid + 1, r);

        ListNode dmHead = new ListNode(0), 
        		 cur = dmHead;

        while (a != null && b != null) {
            if (a.val < b.val) {
                cur.next = a;
                a = a.next;
            } else {
                cur.next = b;
                b = b.next;
            }
            cur = cur.next;
        }
        cur.next = (a != null) ? a : b;
        
        return dmHead.next;
    }
}





merge sort:
public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return sort(lists, 0, lists.length - 1);
    }
    
    private ListNode sort(ListNode[] lists, int lo, int hi) {
        if (lo >= hi) return lists[lo];
        int mid = lo + (hi - lo) / 2;
        ListNode l1 = sort(lists, lo, mid);
        ListNode l2 = sort(lists, mid + 1, hi);
        return merge(l1, l2);
    }
    
    private ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = merge(l1.next, l2);
            return l1;
        }
        l2.next = merge(l1, l2.next);
        return l2;
    }
}