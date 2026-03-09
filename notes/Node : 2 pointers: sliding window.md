## Node

Note:
1. always start with:
```java   
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        dummy.next = head;

        use curr to go through list and perform actions

        return dummy.next;
```

2. to delete a node: 不要跳过所求的node，停在那个node之前，才能skip desired node
3. when change order, always set tempNode = x.next, then change the node before x
4. always check, head != null
5. 虽然时间复杂度都是 O(N)，但是迭代解法的空间复杂度是 O(1)，⽽递归解法需要堆栈，空间复杂度是 O(N),所以平时更推荐用迭代解法

### 反转整个链表 reverse entire list

例子：node:1,2,3,4

#### recursive
```
head
1 --> 2 --> 3 --> 4 --> null

head
1 --> reverse(2 --> 3 --> 4 --> null)


ListNode last = reverseList(head.next);
（此时，2.next 指向 null）

  head              last
    1 --> 2 <-- 3 <-- 4 
          |           
          v           
        null         


head.next.next = head

  head              last
    1 --> 2 <-- 3 <-- 4 
      <-- x           
          x           
        null         


head.next=null;
return last;

        head             last
null <-- 1 <-- 2 <-- 3 <-- 4 

```

```java
    class Solution {
        public ListNode reverseList(ListNode head) {
            if(head==null) 
                return head;
            ListNode last = reverseList(head.next);
            head.next.next = head;
            head.next = null;
            return last;
        }
    }
```

#### Iterative:

- 先存cur.next
- 让cur.next指向prev
- 让prev和cur都各自向后走一步
- 最后prev会走到最后一个node （4），return prev

```
    ListNode nextTemp = curr.next;

prev    cur、head    nextTemp
null        1   -->     2     --> 3  --> 4

    
    curr.next = prev;

prev    cur、head    nextTemp
null  <--   1           2     --> 3  --> 4

    
    prev = curr;
    curr = nextTemp;

           prev    cur、nextTemp
null  <--   1            2       --> 3  --> 4


====================================================
    ListNode nextTemp = curr.next;

           prev     cur   nextTemp
null  <--   1        2   --> 3  --> 4



    curr.next = prev;
           prev     cur   nextTemp
null  <--   1   <--  2       3  --> 4


    prev = curr;
    curr = nextTemp;

                   prev   curr、nextTemp
null  <--   1   <--  2          3   -----> 4
```


```java
    class Solution {
        public ListNode reverseList(ListNode head) {
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
    }
```

### 反转链表前 N 个节点

与上一题类似，不同点：
  * 1、base case 变为 n == 1 ，反转⼀个元素，就是它本身，同时要记录后驱节点。
  * 2、刚才我们直接把 head.next 设置为 null，因为整个链表反转后原来的 head 变成了整个链表的最后一个节点。但现在 head 节点在递归反转之后不一定是最后一个节点了了，所以要记录后驱successor (第 n + 1 个节点)，反转之后将 head 连接上。

```
      head        last    successor
        1 <-- 2 <-- 3        4   --> 5   --> null
        |                    ^
        |                    |
        ----------------------


递归调用路径：

    reverseN(1,3)
     → reverseN(2,2)
       → reverseN(3,1)  // base case
       
到达 base case 时：

    head = 3
    successor = head.next = 4

如果你 不保存 4，回溯后你只能得到：3 → 2 → 1。 但你 不知道 4 在哪，因为链表被“截断”了
```


#### recursive
```java
    class Solution {

        ListNode successor = null; // 后驱节点

        public ListNode reverseList(ListNode head, int n) {
            if(head==null) 
                return head;

            //最后一步，最底层的recursion call
            if(n==1){
                // 记录第 n+1 个节点 
                successor = head.next; 
                return head;
            }
            // 以 head.next 为起点，需要反转前 n - 1 个节点 
            ListNode last = reverseN(head.next, n - 1);
            head.next.next = head;

            // 让反转之后的 head 节点和后⾯面的节点连起来 
            head.next = successor;
            return last;
        }
    }
```

#### iteraive
- 唯一不同的是，在while loop中 n--， 然后exit loop之后链接

```
      head        pre     cur、next
        1 <-- 2 <-- 3        4   --> 5   --> null
        |                    ^
        |                    |
        ----------------------
```

```java
    class Solution {
        public ListNode reverseFirstN(ListNode head, int n) {
            if (head == null || n <= 1) return head;

            ListNode prev = null;
            ListNode curr = head;

            while (n > 0 && curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
                n--;
            }

            // head 此时是反转后的尾节点
            head.next = curr;

            return prev; // prev 是新的头
        }
    }

```



### lc 92: 反转中间部分链表 reverse a certain part in linked list

给⼀个索引区间 [m,n] (索引从 1 开始)，仅仅反转区间中的链表元素。

#### recursive:

```java
class Solution {
    ListNode reverseBetween(ListNode head, int m, int n) {
        // base case，反转前N个，和上一题一样，套用reverseListN
        if (m == 1) {
            return reverseListN(head, n);
        }

        // 前进到反转的起点触发 base case
        head.next = reverseBetween(head.next, m - 1, n - 1); 
        return head;
    }

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
```

#### iterative：

- 理论：不断把 first 后面的节点，摘下来，插到 pre 后面

- pre（反转区间前一个节点）：pre一直不变，pre.next 就是反转区间的第一个节点

- first（反转区间“尾巴”）：注意！first 在整个循环中指到的node不变，但是位置上它会被“不断往后顶”

- second（待搬运的节点）：second 是“要被搬到前面的节点”

- 为什么 for 循环是 right - left 次？
  * 因为反转区间长度是：right - left + 1。 但：第一个节点（first）不用动，后面每个节点都要被“搬”一次。👉 所以循环次数是：(right - left)


```
dummy ->  1 -> 2  ->  3 -> 4 -> 5
          ↑    ↑      ↑
         pre  first second


Step 1️⃣：first.next = second.next之后： 

dummy -> 1  -> 2 -> 4 -> 5
         ↑     ↑
        pre   second(3) 已被摘下来


Step 2️⃣：second.next = pre.next （3.next = 2）
        被搬动的节点second插在pre后面

dummy -> 1    ->    2 -> 4 -> 5
         ↑        ↗ ↑
        pre      3  1st
                2nd 


Step 3️⃣：pre.next = second  （1.next = 3）
        把pre和second连起来

dummy -> 1 ->  3 -> 2 -> 4 -> 5
         ↑     ↑    ↑
        pre   2nd  1st

✔️ 完成一次“头插”



Step 4️⃣：second = first.next (second = 4)
        update second到first后面的一个
        准备下一轮, 也就是把second （4） 查到prev的后面

dummy -> 1 ->  3 -> 2 -> 4 -> 5
         ↑          ↑    ↑
        pre        1st  2nd 


```

Note:

- 为什么一定要 pre = dummy，而不能 pre = head?
  - 因为 pre 的语义是：反转区间前一个节点, 而当 left = 1 时，这个节点并不存在，只能用 dummy 人工创造出来
  - 比如例子：1 → 2 → 3 → 4 → 5，left = 1, right = 3。 你希望反转的是：[1 → 2 → 3]
  - 如果你写：ListNode pre = head;  那么：pre 指向 1，pre.next 指向 2
  - 但逻辑上你需要的是：pre 指向 1 前面的那个节点 ⚠️ 问题：1 前面根本没有节点
  - ❌ 会直接导致这行代码出现问题 pre.next = second; --> 把反转后的节点插到 pre 后面。但如果 pre = head，你永远无法修改 head 本身 --> 你丢失了对“新头节点”的控制


```java
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
        
        for(int i=0; i<right-left; i++){
            first.next = second.next;
            second.next = pre.next;
            pre.next = second;
            second = first.next;
        }

        return dummy.next;
    }
```


### 判断单链表是否是回文结构Palindrome

1. recursion:

- If we iterate the nodes in reverse using recursion, and iterate forward at the same time 
using a variable outside the recursive function, then we can check whether or not we have a
palindrome.

- use frontPointer to point at the begining, currentNode to traverse the list and find the last node

```java
Stats:
    - time: O(n), space O(n) b/c recursion stack


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
```

2. O(1) space, 快慢指针 + 反转后半段链表 + 双指针比较

- Step 1️：用快慢指针找到中点
  - slow 走一步
  - fast 走两步

Step 2：反转后半部分链表
Step 3️：左右两段同时向中间比较



```java

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
```

### leetcode 25. Reverse Nodes in k-Group

```java
key:
    - tail recursive
    - first move cur
    - pre node point to the the answer of sub-problem 


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
```

#### iterative:
```
链表：     1 → 2 → 3 → 4 → 5 → 6 → 7, k = 3
期望结果：  3 → 2 → 1 → 6 → 5 → 4 → 7

初始状态：
    dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7
     pre
     end


Step 1️⃣: start = pre.next =1，通过for loop，找到要反转的list的end = 3, nextSublist = 4


    dummy →   1   →   2   →    3   →   4   → 5 → 6 → 7
     pre     start            end  nextSublist
     


Step 2️⃣ 切断子链表: end.next = null;

    现在链表被切成两段：

    dummy → 1 → 2 → 3 → null

    4 → 5 → 6 → 7



Step 3️⃣ 反转子链表,然后pre.next = reverse(start); --> dummy.next = 3
    
    注意：start 仍然指向 原来的 1, 它现在是反转后这段的尾节点

    dummy → 3 → 2 → 1 → null        4 → 5 → 6 → 7
     ↑      ↑       ↑               ↑
     pre   end    start          nextSublist



Step 4️⃣ 接回后半段:start.next = nextSublist;


    dummy → 3 → 2 → 1   →   4   → 5 → 6 → 7
     ↑      ↑       ↑       ↑
    pre    end    start  nextSublist



Step 5️⃣：移动 pre 和 end，为下一轮做准备， pre = end = start = 1;

dummy → 3 → 2 → 1   →   4 → 5 → 6 → 7
                ↑       ↑
             pre/end  nextSublist

```


```java

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

```


## Two pointers 双指针种类

1. Two pointers: 

        while(leftPointer< RightPointer) 

2. Peak and valley:
- when there are a simultaneously increasing or decreasing array
- ex. lc 135-Candy
- can use Stack 


3. 3 sum/ k sum

**Note: 如果找3个数， i, j , k, 先fix k然后while（i < j)**
- 先sort array
- loop的时候可以fix最边界的k
  - 逻辑：固定最大的数 nums[k]，在左侧寻找两个较小的数 nums[i] 和 nums[j]。
  - 计数：如果 nums[i] + nums[j] > nums[k]，意味着 i 到 j-1 之间所有的数作为最小边都成立。
  - 优势：这种方式最直观，因为固定了最大边后，nums[i] + nums[j] 的和具有单调性，非常适合收缩 j。

```java
class Solution {
    public int triangleNumber(int[] nums) {
        if(nums==null||nums.length==0) return 0;

        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0;
        for(int k = 2; k<n;k++){
            int i = 0;
            int j = k-1;

            while(i<j){
                if(nums[i]+nums[j]>nums[k]){
                    //valid
                    ans+=j-i;
                    j--;
                }else {
                    i++;
                }

            }
        }
        return ans;
    }
}

```

优点：
- 单次扫描 (Single Pass)：固定 k 的双指针法逻辑是 O(n^3)，且 i 和 j 的收缩非常对称，不易写错。
- 避免死循环：固定 i 的版本中，k 指针需要小心处理不能小于 j+1，且需要额外判断 nums[i] == 0 的情况（因为 0 不能做三角形边长）。
- 内存局部性 (Memory Locality)：原版从右向左收缩 j 对缓存更加友好。



### 1.快慢指针
主要解决链表中的问题，比如典型的判定链表中是否包含环。⼀般都初始化指向链表的头结点 head，前进时快指针 fast 在前，慢指针 slow 在后

经典问题1：找环

- 如果不含有环，跑得快的那个指针最终会遇到null，说明链表不不含环;如果含有环，快指针最终会超慢指针一圈，和慢指针相遇

```java
    boolean hasCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }
```

经典问题2: 已知链表中含有环，返回这个环的起始位置

```java
    ListNode detectCycle(ListNode head) {
        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }

        if (fast == null || fast.next == null) {
        // fast 遇到空指针说明没有环
                return null;
            }
        slow = head;
        while (slow != fast) {
                fast = fast.next;
                slow = slow.next;
        }
        return slow;
    }
```

经典问题3: 寻找链表的倒数第 k 个元素

让快指针先走 k 步，然后快慢指针开始同速前进。这样当快指针走到链表末尾 null 时，慢指针所在的位置就是倒数第 k 个链表节点(为了简化，假设 k 不会超过链表长度):

```java
    ListNode slow, fast;
    slow = fast = head;
    while (k-- > 0)
        fast = fast.next;
    while (fast != null) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
```


### 2.左右指针
解决数组(或者字符串串)中的问题，⽐如⼆分查找、反转数组


翻转数组
```java
    void reverse(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            // swap(nums[left], nums[right])
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++; right--;
        }
    } 

```




## Sliding Windows:
1. 框架：

```java
    public int fn(int[] arr) {
        int left = 0, ans = 0, curr = 0;

        for (int right = 0; right < arr.length; right++) {
            // do logic here to add arr[right] to curr

            while (WINDOW_CONDITION_BROKEN) {
                // remove arr[left] from curr
                left++;
            }

            // update ans
        }

        return ans;
    }
```

例子：
```java
    string s, t;
    // 在 s 中寻找 t 的「最⼩小覆盖⼦子串串」 
    int left = 0, right = 0; string res = s;

    // 从右边开始挪动
    while(right < s.size()) {
        window.add(s[right]);
        right++;

        // 如果符合要求，移动 left 缩⼩小窗⼝口 
        while (window 符合要求) {

            // 如果这个窗口的⼦串更短，则更新 res 
            res = minLen(res, window); 
            window.remove(s[left]);
            left++;
        } 
    }
    return res;
```

2. Note

1) 可以用hashmap存字符，也可以用int【】，初始为int[128]或者int[256],
- 128: allocates memory for 128 integers, The ASCII table has 128 standard characters (from code 0 to 127). By creating an array of size 128, you can use a character’s ASCII value as an index

- If you're working with Unicode or extended character sets, you'd need a larger array (e.g., 256 or 65536 for full Unicode BMP).

2) 可以视情况，初始window size。比如可以left = right = 0，如果是找string中特定的permutation p，可以把窗口大小设定为p.length()，然后再往右边挪

3) 可以用两个hashmap，string a存的时候+1，检查string b的时候-1，最后如果all zeros就说明是permutation/包含等等

ex.567. Permutation in String - Medium

Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
In other words, one of the first string permutations is the substring of the second string.


```java
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) return false;
        
        int[] count = new int[26];
        for (int i = 0; i < len1; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        if (allZero(count)) return true;
        
        for (int i = len1; i < len2; i++) {
            count[s2.charAt(i) - 'a']--;
            count[s2.charAt(i - len1) - 'a']++;
            if (allZero(count)) return true;
        }
        
        return false;
    }
    
    private boolean allZero(int[] count) {
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
}
```



3. 例子： lc 76 min window substring

Given a string S and a string T, find the minimum window in S which will contain all the letters

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"

```java
class Solution {
    public String minWindow(String s, String t) {
        int[] map= new int[128];
        for (char c: t.toCharArray()){
            map[c]++;
        }

        int left=0, right=0, minStart=0, minLen=Integer.MAX_VALUE, 
            counter=t.length(); //counter从大往小减

        while(end<s.length()){
            char c1= s.charAt(right);
            if (map[c1]>0) counter--;

            map[c1]--;
            right++;

            while(counter==0){ //all char in t should be mapped to 0
                if (minLen>right-left){
                    minLen=right-left;
                    minStart=left;
                }
                // move start pointer, shrink the window
                char c2= s.charAt(left);
                map[c2]++;

                // When map[c2]>0, then a char exists in t was deleted
                // increase counter, break out of the loop, searching for that c2 
                if (map[c2]>0) counter++;
                left++;
            }
        }
        return minLen==Integer.MAX_VALUE?"":s.substring(minStart,minStart+minLen);
    }
}
```

4. 用两个deque maintain window

例子：
1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit - Medium
3208. Alternating Groups II - Medium

```java
    public int longestSubarray(int[] A, int limit) {
        Deque<Integer> maxd = new ArrayDeque<>();
        Deque<Integer> mind = new ArrayDeque<>();

        //i是左指针，j是右指针
        int i = 0, j;
        for (j = 0; j < A.length; ++j) {
            //只要当前的比maxd最后的大，就一直从尾部pop，后面maxd.add的时候才是争取的顺序
            while (!maxd.isEmpty() && A[j] > maxd.peekLast()) maxd.pollLast();
            while (!mind.isEmpty() && A[j] < mind.peekLast()) mind.pollLast();

            maxd.add(A[j]);
            mind.add(A[j]);

        /* maxd holds the biggest elements from A[i]...A[j] in decreasing order.
        # So maxd.peek is the biggest element in the window A[i]...A[j]
        # mind holds the smallest elements from A[i]...A[j] in increasing order.
        # So mind.peek is the smallest element in the window A[i]...A[j]
        # maxd[0]-mind[0] is the biggest difference in the window A[i]...A[j] */
            if (maxd.peek() - mind.peek() > limit) {
                if (maxd.peek() == A[i]) maxd.poll();
                if (mind.peek() == A[i]) mind.poll();
                ++i;
            }

        }
        return j - i;
    }
```

5. 
