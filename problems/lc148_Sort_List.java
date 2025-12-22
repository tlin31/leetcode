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

🌱 从长度 = 1 的子链表开始
    4 → 2 → 1 → 3

    step = 1:
    [4] [2] [1] [3]

    step = 2:
    [2 → 4] [1 → 3]

    step = 4:
    [1 → 2 → 3 → 4]

👉 每一轮：

    1、把链表按 step 拆成 左右两段： left | right | curr

    2、两两 merge

    3、step *= 2


变量     含义
dummy   虚拟头，统一操作
step    当前子链表长度
curr    扫描未处理部分
left    左子链表
right   右子链表
tail    已合并部分的尾部

例子
    dummy → 4 → 2 → 1 → 3 → null

第 1 轮：step = 1（每 1 个一组）
    tail = dummy
    curr = dummy.next = 4


🟢 第一次 merge（处理 4 和 2）
1️、切分
    left  = [4]
    right = [2]
    curr  = [1 → 3]

（通过 split(left,1) 和 split(right,1)）

2️、 merge(left, right)
    merge [4] & [2] → [2 → 4]

3️、接回链表
    dummy → 2 → 4
                ↑ 
                tail

    tail 移动到 4


🟢 第二次 merge（处理 1 和 3）
1️、 当前状态
    curr = 1 → 3
    tail = 4

2️、 切分
    left  = [1]
    right = [3]
    curr  = null

3️、merge
    merge [1] & [3] → [1 → 3]

4️、 接回
    dummy → 2 → 4 → 1 → 3 → null
                        ↑
                       tail

✅ step = 1 结束后整体链表
2 → 4 → 1 → 3


（每两个内部是有序的）

五、第 2 轮：step = 2
    step = 2
    curr = dummy.next = 2
    tail = dummy

🟢 第一次 merge（处理 [2,4] 和 [1,3]）
1️、 切分
    left  = [2 → 4]
    right = [1 → 3]
    curr  = null

2️、 merge（关键动画）
    比较 2 vs 1 → 取 1
    比较 2 vs 3 → 取 2
    比较 4 vs 3 → 取 3
    剩余 → 4


    结果：
    1 → 2 → 3 → 4

3️、 接回
    dummy → 1 → 2 → 3 → 4 → null


    class Solution {
        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) return head;

            // 1. get length
            int length = 0;
            ListNode p = head;
            while (p != null) {
                length++;
                p = p.next;
            }

            ListNode dummy = new ListNode(0);
            dummy.next = head;

            // 2. bottom-up merge
            for (int step = 1; step < length; step <<= 1) {
                ListNode curr = dummy.next;
                ListNode tail = dummy;

                while (curr != null) {
                    ListNode left = curr;
                    ListNode right = split(left, step);
                    curr = split(right, step);

                    tail = merge(left, right, tail);
                }
            }

            return dummy.next;
        }

        // split list after step nodes
        private ListNode split(ListNode head, int step) {
            for (int i = 1; head != null && i < step; i++) {
                head = head.next;
            }
            if (head == null) return null;
            ListNode second = head.next;
            head.next = null;
            return second;
        }

        // merge two sorted lists, attach after prev, return new tail
        private ListNode merge(ListNode l1, ListNode l2, ListNode prev) {
            ListNode cur = prev;
            while (l1 != null && l2 != null) {
                if (l1.val <= l2.val) {
                    cur.next = l1;
                    l1 = l1.next;
                } else {
                    cur.next = l2;
                    l2 = l2.next;
                }
                cur = cur.next;
            }

            cur.next = (l1 != null) ? l1 : l2;
            while (cur.next != null) cur = cur.next;
            return cur;
        }
    }


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