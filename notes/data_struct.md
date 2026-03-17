# Table of contents
1. Complexity
2. Queue
    1. Priority Queue
    2. 单调队列 Monotonic Queue
3. Stack
	1. 单调栈 Monotonic Stack
4. Deque
5. Heap
6. Tree
7. Trie
8. Graph
9. Treemap
10. Hashmap


## Summary of complexity

List                 | Add  | Remove | Get  | Contains | Next | Data Structure
---------------------|------|--------|------|----------|------|---------------
ArrayList            | O(1) |  O(n)  | O(1) |   O(n)   | O(1) | Array
LinkedList           | O(1) |  O(1)  | O(n) |   O(n)   | O(1) | Linked List
CopyOnWriteArrayList | O(n) |  O(n)  | O(1) |   O(n)   | O(1) | Array

Note 1: here add is to add at the end, if it's insert, then array list is O(n), linkedlist is O(1)

Note 2: Why arraylist add O(1)?

It cannot be just O(1) asymptotic (always) because rarely we need to grow array capacity. This single add operation which is in fact a "create new bigger array, copy old array into it, and then add one element to the end" operation is O(n) asymptotic complexity, because copying the array when increasing List capacity is O(n), complexity of growing plus addding is O(n) [calculated as O(n) + O(1) = O(n)]. Without this capacity growing operation, add complexity would have been O(1), element is always added (appended) to the end of array (max index). If we "added" (= inserted) not to array end, we would need to move rightmost elements (with bigger indexes), and complexity for single such operation would have been O(n).




Set                   |    Add   |  Remove  | Contains |   Next   | Size | Data Structure
----------------------|----------|----------|----------|----------|------|-------------------------
HashSet               | O(1)     | O(1)     | O(1)     | O(h/n)   | O(1) | Hash Table
LinkedHashSet         | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Maintain order of insertion
EnumSet               | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Bit Vector
TreeSet               | O(log n) | O(log n) | O(log n) | O(log n) | O(1) | Red-black tree
CopyOnWriteArraySet   | O(n)     | O(n)     | O(n)     | O(1)     | O(1) | Array
ConcurrentSkipListSet | O(log n) | O(log n) | O(log n) | O(1)     | O(n) | Skip List

TreeSet----The ordering of the elements is maintained by a set using their natural ordering whether or not an explicit comparator is provided
- TreeSet does not preserve the insertion order of elements but elements are sorted by keys.
- red black tree

Queue                   |  Offer   | Peak |   Poll   | Remove | Size | Data Structure
------------------------|----------|------|----------|--------|------|---------------
PriorityQueue           | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
LinkedList              | O(1)     | O(1) | O(1)     |  O(1)  | O(1) | Array
ArrayDequeue            | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Linked List
ConcurrentLinkedQueue   | O(1)     | O(1) | O(1)     |  O(n)  | O(n) | Linked List
ArrayBlockingQueue      | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Array
PriorirityBlockingQueue | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
SynchronousQueue        | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | None!
DelayQueue              | O(log n) | O(1) | O(log n) |  O(n)  | O(1) | Priority Heap
LinkedBlockingQueue     | O(1)     | O(1) | O(1)     |  O(n)  | O(1) | Linked List



Map                   |   Get    | ContainsKey |   Next   | Data Structure
----------------------|----------|-------------|----------|-------------------------
HashMap               | O(1)     |   O(1)      | O(h / n) | Hash Table
LinkedHashMap         | O(1)     |   O(1)      | O(1)     | Hash Table + Linked List
IdentityHashMap       | O(1)     |   O(1)      | O(h / n) | Array
WeakHashMap           | O(1)     |   O(1)      | O(h / n) | Hash Table
EnumMap               | O(1)     |   O(1)      | O(1)     | Array
TreeMap               | O(log n) |   O(log n)  | O(log n) | Red-black tree
ConcurrentHashMap     | O(1)     |   O(1)      | O(h / n) | Hash Tables
ConcurrentSkipListMap | O(log n) |   O(log n)  | O(1)     | Skip List

## Queue

### ✅ 1. Queue 的常见声明方式（Interface vs Implementation）

Java 中 Queue 是接口（java.util.Queue）。一般更推荐用arrayDeque

**最推荐用：queue接口 + arrayDeque实现**
```java
		Queue<Integer> q = new ArrayDeque<>();
```

- Method:

| 方法          | 作用       | 队列空时行为     | 备注      |
| ----------- | -------- | ---------- | ------- |
| `offer(e)`  | 入队（添加元素） | 返回 `false` | 推荐使用，安全 |
| `poll()`    | 出队（拿并移除） | 返回 `null`  | 推荐使用    |
| `peek()`    | 查看队头但不删除 | 返回 `null`  | 推荐使用    |
| `add(e)`    | 入队       | 抛异常        | 不推荐     |
| `remove()`  | 出队       | 抛异常        | 不推荐     |
| `element()` | 查看队头但不删除 | 抛异常        | 不推荐     |
| `isEmpty()` | 判断是否为空   | —          | 常用      |
| `size()`    | 返回队列大小   | —          | 常用      |


- 👍 优点

  * 更加抽象：你的代码依赖“队列行为”，而不是特定的数据结构。

  * 更易替换实现：如需改为 PriorityQueue 或 LinkedList，只换一行即可。

  * 更符合 Java 编程习惯（尤其在大厂面试中，这是 good practice）。

  * 使用时只关心 Queue 常规方法：offer() / poll() / peek() / isEmpty() / size()



- 不写 ArrayDeque<Integer> q = new ArrayDeque<>();
	因为耦合具体实现，不利于扩展、违背 “program to an interface” 原则



#### ① 使用 Queue 接口声明

```java
		Queue<Integer> q = new LinkedList<>();
```
- 代码灵活，可切换底层实现
- 面试中最推荐

Methods:

**更推荐！1)返回特殊值（null/false）版本**
| 方法         | 描述                   |
| ---------- | -------------------- |
| `offer(e)` | 入队，如果失败 → 返回 false   |
| `poll()`   | 出队，如果空 → 返回 null     |
| `peek()`   | 返回队首元素，如果空 → 返回 null |


2)抛异常版本
| 方法          | 描述               |
| ----------- | ---------------- |
| `add(e)`    | 入队，如果队列满了 → 抛异常  |
| `remove()`  | 出队，如果空 → 抛异常     |
| `element()` | 返回队首元素，如果空 → 抛异常 |



#### ② 使用具体类声明

```java
		LinkedList<Integer> q = new LinkedList<>();
```
- 不推荐，因为限制多态性（无法轻松换成 PriorityQueue）。


#### ③ 使用 Deque 声明（特别是单调队列、滑动窗口常用）

```java
		Deque<Integer> dq = new ArrayDeque<>();
```

- Deque 同时继承了 Queue，方法更完整：

- 队头操作
  * dq.offerFirst(x);
  * dq.pollFirst();
  * dq.peekFirst();

- 队尾操作
  * dq.offerLast(x);
  * dq.pollLast();
  * dq.peekLast();

- 常用于：
  * 单调队列
  * BFS
  * 滑动窗口最大值（239）
  * 双端操作
  * Palindrome check
  * stack


- ✔ ArrayDeque 的优点：
  * 无锁，无同步 → 更快
  * 两端操作都 O(1)
  * 性能优于 LinkedList： 因为连续内存 Cache locality、无额外节点对象、无指针跳转
  * 没有容量限制（自动扩容）

- ❌ 相比之下，不建议用 LinkedList 的原因：

  * 额外节点对象，有内存开销

  * CPU cache 不友好（链表）

  * 性能比 ArrayDeque 差（除非你需要大量中间插入）



#### ④ 使用 PriorityQueue 声明（最小堆，常用于贪心/TopK）

默认是 min-heap:

```java
		Queue<Integer> pq = new PriorityQueue<>();

		PriorityQueue<ListNode> queue= new PriorityQueue<ListNode>(lists.length, (a,b)-> a.val-b.val);

```

max heap写法：
```java
		Queue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
```

- 常用方法
  * pq.offer(x);     // add
  * pq.poll();       // get and remove smallest element
  * pq.peek();       // get smallest element

- 用于：
  * TopK elements
  * Dijkstra 最短路
  * 合并 K 个排序链表


#### ⑤ 使用 BlockingQueue（并发队列）

- 适用于多线程生产者/消费者模型。

```java
		BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(100);
```

常见子类：
  * ArrayBlockingQueue

  * LinkedBlockingQueue

  * PriorityBlockingQueue

  * DelayQueue

  * SynchronousQueue（零容量）


### ✅ 2. 不同 Queue 实现类的对比表

| 实现类                 | 类型       | 是否线程安全 | 特点                    | 常用场景      |
| ------------------- | -------- | ------ | --------------------- | --------- |
| LinkedList          | 普通队列     | ❌      | 双端队列，可用于 BFS          | BFS、简单队列  |
| ArrayDeque          | Deque    | ❌      | 性能优于 LinkedList，无容量限制 | 单调队列、滑动窗口 |
| PriorityQueue       | 堆        | ❌      | 默认最小堆                 | TopK、贪心   |
| ArrayBlockingQueue  | Blocking | ✔      | 数组结构，有界               | 生产者-消费者   |
| LinkedBlockingQueue | Blocking | ✔      | 链表结构，可无界              | 线程池工作队列   |
| DelayQueue          | Blocking | ✔      | 支持延时任务                | 定时任务      |
| SynchronousQueue    | Blocking | ✔      | 零容量，高速handoff         | 线程池       |





## Priority Queue

- First-In-First-Out algorithm
- PriorityQueue is based on the priority heap.
- elements are ordered according to the natural ordering, or by a Comparator provided at queue construction time, depending on which constructor is used.


### PQ implementations

1. Arrays: enq O(1), deq O(n), peek O(n). Check if array/PQ is full, check whether there are any items in there (count or use front index, rear index)
2. Linked list: push O(n), pop O(1), peek O(1)
3. Heap data structure: 
- better performance 
- take time O(log n) to restore the heap property for the remaining keys. However if another entry is to be inserted immediately, then some of this time may be combined with the O(log n) time needed to insert the new entry. 
- Good for large n, efficiently in contiguous storage and is guaranteed to require only logarithmic time for both insertions and deletions. 
- insert O(log n), delete O(log n), peek O(1)


4. Binary search tree

[code of implementation](https://www.geeksforgeeks.org/priority-queue-set-1-introduction/)


### Few points on Priority Queue are:

	1. PriorityQueue doesn’t permit NULL pointers.
	2. We can’t create PriorityQueue of Objects that are non-comparable
	3. PriorityQueue are unbound queues.
	4. head = least element with respect to the specified ordering. 
		- If multiple elements are tied for least value, the head is one of those elements — ties are broken arbitrarily.
	5. The queue retrieval operations poll, remove, peek, and element access the element at the head of the queue.
	6. It inherits methods from AbstractQueue, AbstractCollection, Collection and Object class.
	7. has its *iterator*

### Constructors of PriorityQueue class
- PriorityQueue(): Creates a PriorityQueue with the default initial capacity (11) that orders its elements according to their natural ordering.
- PriorityQueue(Collection<E> c): Creates a PriorityQueue containing the elements in the specified collection.
- PriorityQueue(int initialCapacity): Creates a PriorityQueue with the specified initial capacity that orders its elements according to their natural ordering.
- PriorityQueue(int initialCapacity, Comparator<E> comparator): Creates a PriorityQueue with the specified initial capacity that orders its elements according to the specified comparator.
- PriorityQueue(PriorityQueue<E> c): Creates a PriorityQueue containing the elements in the specified priority queue.
- PriorityQueue(SortedSet<E> c): Creates a PriorityQueue containing the elements in the specified sorted set.


### Example
- string PQ
```java
import java.util.*; 

class Example 
{ 
	public static void main(String args[]) 
	{ 
		// Creating empty priority queue 
		PriorityQueue<String> pQueue = new PriorityQueue<String>(); 

		// Adding items to the pQueue using add() 
		pQueue.add("C"); 
		pQueue.add("C++"); 
		pQueue.add("Java"); 
		pQueue.add("Python"); 

		// Printing the most priority element 
		System.out.println("Head value using peek function:"+ pQueue.peek()); 

		// Printing all elements using an iterator 
		System.out.println("The queue elements:"); 
		Iterator itr = pQueue.iterator(); 
		while (itr.hasNext()) 
			System.out.println(itr.next()); 

		// Removing the top priority element (or head) and printing the modified pQueue using poll() 
		pQueue.poll(); 

		// Removing a specific element using remove() 
		pQueue.remove("Java"); 

		// Check if an element is present using contains() 
		boolean b = pQueue.contains("C");  

		// Getting objects from the queue using toArray()in an array and print the array 
		Object[] arr = pQueue.toArray(); 
		System.out.println ( "Value in array: "); 
		for (int i = 0; i<arr.length; i++) 
			System.out.println ( "Value: " + arr[i].toString()) ; 
	} 
} 
```

- if priority queue of object:
    Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));


output: 
	Head value using peek function:C
	The queue elements:
	C
	C++
	Java
	Python
	After removing an elementwith poll function:
	C++
	Python
	Java
	after removing Java with remove function:
	C++
	Python
	Priority queue contains C or not?: false
	Value in array: 
	Value: C++
	Value: Python


### Methods in PriorityQueue class:

- boolean add(E element): This method inserts the specified element into this priority queue.
- public remove(): This method removes a single instance of the specified element from this queue, if it is present
- public poll(): This method retrieves and removes the head of this queue, or returns null if this queue is empty.
- public peek(): This method retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
- Iterator iterator(): Returns an iterator over the elements in this queue.
- boolean contains(Object o): This method returns true if this queue contains the specified element
- void clear(): This method is used to remove all of the contents of the priority queue.
- *boolean offer(E e)*: This method is used to insert a specific element into the priority queue.
- int size(): The method is used to return the number of elements present in the set.
- toArray(): This method is used to return an array containing all of the elements in this queue.
- Comparator comparator(): The method is used to return the comparator that can be used to order the elements of the queue.

### Applications:
- Implementing Dijkstra’s and Prim’s algorithms.
- Maximize array sum after K negations


## 单调队列 Monotonic Queue
单调队列是一种保持元素单调（递增或递减）的双端队列结构，用来**解决滑动窗口最大值/最小值**

不是heap！heap是所有元素都排序，单调队列保持元素的先后顺序

「单调队列」的核⼼思路和「单调栈」类似。单调队列的 push ⽅法依然在队尾添加元素，但是要把前⾯比新元素⼩的元素都删掉

1. 特点：
- 队列中的元素按某种顺序单调（如从队头到队尾递减）
- 新元素入队时，会“挤掉”队尾不符合单调性的元素
- 队头永远是窗口最优解（最大/最小值）
- 每个元素最多入队、出队一次 → O(n)

2. 单调队列的两种形式
- ① 单调递减队列：队头最大，常用于找滑动窗口最大值。队列从前到后：大 → 小

- ② 单调递增队列：队头最小，常用于滑动窗口最小值、最短子数组、最短窗口问题。队列从前到后：小 → 大


3. “单调队列的黄金公式”（必须记住）

- 以单调递减队列为例：

  - 1) 入队（push x）保证从队头到队尾递减 → 队头永远是最大值。

```java
	while (!dq.isEmpty() && dq.back() < x)
	    dq.pop_back();
	dq.push_back(x);
```

  - 2) 出队（pop x）

```java
	如果 x == dq.front()，则 dq.pop_front();
	否则不动。

	也要记住左右指针中，如果要把左指针右移，记得在queue中检查是否需要删除：

    if (nums[l] == maxDeque.peekFirst()) 
    	maxDeque.pollFirst();

```

  - 3) 最大值 ： 永远是 dq.front();



  - 4) 如果需要shrink window：

		```java
			// shrink window
            while (nums[maxD.peekFirst()] - nums[minD.peekFirst()] > k) {
                if (maxD.peekFirst() == l) maxD.pollFirst();
                if (minD.peekFirst() == l) minD.pollFirst();
                l++;
            }
        ```

4. 如何知道某题是否适合用单调队列？

✔ 条件 1：滑动窗口内求最大/最小值 → 一定是单调队列（LC 239, 1438）

✔ 条件 2：窗口内求 max/min 的 DP → 单调队列优化 （LC 1696）

✔ 条件 3：用 prefix sum + 单调性判断最短/最长窗口 → 单调队列 find boundary （LC 862）

✔ 条件 4：窗口扩张/收缩的过程中，需要维护最值 → 单调队列 / 双单调队列

⭐ 题 1：239. Sliding Window Maximum（滑动窗口最大值）

（典型单调递减队列题）

要找每个窗口的最大值

使用单调递减队列

每个元素最多进出队一次 → O(n)

⭐ 题 2：862. Shortest Subarray with Sum ≥ K

（单调递增队列 + 前缀和）

使用方式：

prefix 数组保持单调递增

当 prefix[j] - prefix[i] ≥ K 时，更新答案

利用单调递增队列维护 “最小 prefix”，从而能最早满足条件

这是非常典型的用法。

⭐ 题 3：1438. Longest Continuous Subarray With Absolute Diff ≤ Limit （双单调队列）

用法：

一个单调递减队列跟踪最大值

一个单调递增队列跟踪最小值

当 max - min > limit 时移动左指针缩窗口

这是“单调队列维护窗口有界性”的经典题。

⭐ 题 5：480. Sliding Window Median

虽然不是纯单调队列，但可以用双堆解决，方式相似。

⭐ 题 6：1004. Max Consecutive Ones III

移动窗口，但不需要单调队列（用计数即可）
（面试中常被误用单调队列，实际上不需要）

⭐ 题 7：1696. Jump Game VI

（单调队列优化 DP）

dp[i] = nums[i] + max(dp[i-k ... i-1])

单调队列维护 "max dp in window" → O(n)

⭐ 题 8：2397, 1499 等

很多“窗口优化 DP”的题目几乎都是单调队列。

#### 例子
1. LeetCode 239：Sliding Window Maximum（滑动窗口最大值）
- 给定数组 nums 和窗口大小 k，要求在每次窗口滑动后输出窗口内的最大值。

解法：
- 	使用一个 递减单调队列（Deque），队首是当前窗口最大值
- 	当滑动窗口移动时：把不在窗口范围的值弹出队首 --> 把所有小于当前值的元素从队尾删掉，保持递减性

```java

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n - k + 1];

        Deque<Integer> deque = new LinkedList<>();//存index

        for (int i = 0; i < n; i++) {

            // ① 移除不在窗口范围内的元素（i-k）
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // ② 删除队列中所有小于当前 nums[i] 的元素
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // ③ 将当前元素下标加入队尾
            deque.offerLast(i);

            // ④ 当窗口大小达到 k 时，记录窗口最大值
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return res;
    }
}
```

2. LeetCode 1438 — Longest Continuous Subarray With Absolute Diff ≤ Limit

为了快速得到窗口的最大/最小值，我们使用两个 Monotonic Queue：
- maxDeque：维护单调递减队列 → 队头是当前最大值
- minDeque：维护单调递增队列 → 队头是当前最小值

通过滑动窗口：
- 每次加入 nums[r]
- 检查窗口是否满足 max - min <= limit
- 如果超出 limit，就移动左指针 l


```java
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();

        int l = 0, res = 0;

        for (int r = 0; r < nums.length; r++) {
            int x = nums[r];

            // Maintain max deque (decreasing)
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < x) {
                maxDeque.pollLast();
            }
            maxDeque.addLast(x);

            // Maintain min deque (increasing)
            while (!minDeque.isEmpty() && minDeque.peekLast() > x) {
                minDeque.pollLast();
            }
            minDeque.addLast(x);

            // Shrink window if condition violated,  左指针右移缩小窗口
            while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (nums[l] == maxDeque.peekFirst()) maxDeque.pollFirst();
                if (nums[l] == minDeque.peekFirst()) minDeque.pollFirst();
                l++;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;
    }
}
```




# Stack

用deque创建更好！stack已经过时&syntax不好用


## java syntax
1. initialize + import:

```java
	import java.util.*; 
		Stack<Integer> stack = new Stack<Integer>(); 
		Deque<TreeNode> stack = new ArrayDeque<>(); 
		Deque<TreeNode> stack = new LinkedList<>(); 
```
插入元素: 使用 push()，将元素压入栈顶。
移除元素: 使用 pop()，从栈顶弹出元素。
查看元素: 使用 peek()，查看栈顶元素而不弹出。 

isEmpty(): 检查 Deque 是否为空。
size(): 返回 Deque 中的元素数量。
contains(Object o): 检查 Deque 是否包含特定元素。


作为双端队列 (两端都可操作)

从头部插入: addFirst() 或 offerFirst()
从尾部插入: addLast() 或 offerLast()
从头部移除: removeFirst() 或 pollFirst()
从尾部移除: removeLast() 或 pollLast()
查看头部元素: peekFirst()
查看尾部元素: peekLast()

常用实现类
在大多数情况下，应优先使用 ArrayDeque。 
ArrayDeque: 基于可变大小的数组实现，高效且内存占用少。不支持存储 null 值。
LinkedList: 基于双向链表实现，支持 null 值，但在性能上通常不如 ArrayDeque。 

## 单调栈 Monotonic Stack


当你遇到 **“找临近比它大/小的元素”、“下一次更大/更小”、“区间扩展到不能扩展为止”**

单调栈（Monotonic Stack）是处理 **“局部与周围关系”** 的神器

✅ 一、最核心规律

🔥 规律 1：

当想找“下一个更大”或“上一个更大” → 用**单调递减栈（栈顶最小）,条件是cur > stack.peek()就pop**，pop出来的数字的next greater element就是cur

保持栈顶最小，栈底最大 → 栈中存放的都是“还没找到更大值”的候选元素。

	当你看到一个新元素 x 时：

	1、如果 x > 栈顶：
	→ x 就是栈顶元素的“下一个更大元素”
	→ 栈顶弹出并记录答案

	2、重复 1 直到栈空或 x ≤ 栈顶

	3、将 x 入栈（等待未来有人比它更大）

当想找“下一个更小”或“上一个更小” → 用单调递增栈


🔥 规律 2：

往左找元素，只要把遍历方向反过来

往右找 → 正序
往左找 → 倒序
循环数组 → 遍历两遍（i % n）

✅ 二、例子
**模式1：Next Greater Element / Next Smaller Element**

给定数组，每个元素要找到：

- 下一个更大的元素 / 上一个更大的元素

- 下一个更小的元素 / 上一个更小的元素


例子：
- ① Next Greater Element I — LC 496: 用decreasing stack, 条件是cur > stack.peek()就pop
- ② Next Greater Element II — LC 503
- ③ Daily Temperatures — LC 739 → 找下一天更高温度
- ④ Online Stock Span — LC 901 → 找连续小于等于当前的天数


ex. LC 739,Daily Temperatures
- 找下一天更高温度
```java 
public int[] dailyTemperatures(int[] temperatures) {
    Stack<Integer> stack = new Stack<>();
    int[] ret = new int[temperatures.length];
    for(int i = 0; i < temperatures.length; i++) {
        while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int idx = stack.pop();
            ret[idx] = i - idx;
        }
        stack.push(i);
    }
    return ret;
}
```


ex. LC 496 Next Greater Element I
```java

    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
        Stack<Integer> stack = new Stack<>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() < num)
                map.put(stack.pop(), num);
            stack.push(num);
        }   
        for (int i = 0; i < findNums.length; i++)
            findNums[i] = map.getOrDefault(findNums[i], -1);
        return findNums;
    }
```

变形：根据单调stack构建suffix array，比如lc 2104

```java

//根据范围和的定义，可以推出范围和 sum 等于所有子数组的最大值之和 sumMax 减去所有子数组的最小值之和 sumMin。

//minLeft[i] 表示 nums[i] 左侧最近的比它小的数的下标，minRight[i] 表示 nums[i] 右侧最近的比它小的数的下标。

        for (int i = 0; i < n; i++) {
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);
        }
```



**模式 2：连续区间向左右延伸，直到遇到更大/更小的阻碍**

求以当前元素为中心，向两侧能够延伸多远

求“最大矩形”、“最大面积”等区间类问题 （柱状图、可以拍扁成柱状图的3x3图）

求滑动窗口中最大/最小（但更常用双端队列）

ex. 
- Largest Rectangle in Histogram — LC 84（⭐ 最经典单调栈算法题）: 利用单调递增栈找到每个条形向左右能扩展的最大宽度。

- Maximal Rectangle — LC 85: 把每一行转为 histogram，用 LC84 求最大矩形。

- Sum of Subarray Minimums — LC 907: 求所有子数组的最小值之和 → 需要前一个更小+后一个更小

- Sum of Subarray Maximums — LC 2104

- 1504. Count Submatrices With All Ones - Medium



ex. Largest Rectangle in Histogram — LC 84, 存increasing

给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
求在该柱状图中，能够勾勒出来的矩形的最大面积。

解法：stack，因为一旦碰到小的，就不把他算到面积里，直接结算他之前的
  
```
Input: heights = [2,1,5,6,2,3]
Output: 10

	  x  
    x x  
    x x
    x x   x
x   x x x x
x x x x x x
```


```java
        for (int i = 0; i < newHeights.length; i++) {
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                int h = newHeights[stack.pop()]; // 当前柱子高度

                int right = i;                   // 右边界（遇到更矮的了）
                int left = stack.isEmpty() ? -1 : stack.peek();  // 左边界

                int width = right - left - 1;    // 宽度
                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }
```

ex. 907. 子数组的最小值之和

给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
由于答案可能很大，因此 返回答案模 10^9 + 7 。

示例 1：
输入：arr = [3,1,2,4]
输出：17
解释：
子数组为 [3]，[1]，[2]，[4]，[3,1]，[1,2]，[2,4]，[3,1,2]，[1,2,4]，[3,1,2,4]。 
最小值为 3，1，2，4，1，1，2，1，1，1，和为 17。

解答：
每个元素 arr[i] 对多少个子数组贡献它自己作为“最小值”？计算它作为“子数组最小值”出现的次数，并乘以 arr[i]，再累加即可。

要计算贡献次数，必须知道：

	左边有多少个连续比它 严格大 的元素

	右边有多少个连续比它 大或等于 的元素

	再用：

	贡献=arr[i]×left[i]×right[i]

这可用 单调递增栈（Monotonic Stack） 在 O(n) 完成。

```java

class Solution {
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int MOD = 1_000_000_007;

        int[] left = new int[n];
        int[] right = new int[n];

        // 计算 left：上一个更小元素（严格小）
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            //stack is empty说明左边没有比他小的，		
            //若不存在更小元素，则 preSmallIndex = -1 → left[i] = i-(-1)=i + 1
            //如果stack里面有，算i到左边那个数字的距离
            left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        // 清空栈，计算 right：下一个小于等于元素
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            //right[i]=rleIndex−i
			// 若不存在更小或等值元素，则 rleIndex = n, 变成n-i
            right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
            stack.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + (long) arr[i] * left[i] * right[i]) % MOD;
        }

        return (int) ans;
    }
}

```


ex. 1504. Count Submatrices With All Ones - Medium

Given an m x n binary matrix mat, return the number of submatrices that have all ones.

Example 1:
Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
Output: 13

Explanation: 
There are 6 rectangles of side 1x1.
There are 2 rectangles of side 1x2.
There are 3 rectangles of side 2x1.
There is 1 rectangle of side 2x2. 
There is 1 rectangle of side 3x1.
Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.


解法：

Step 1：先求 height 数组（和 85 一样）

	对每一行 i：height[j] = 当前行向上连续 1 的高度

	例：

	1 0 1
	1 1 1

	第二行 height = 2 1 2

Step 2：对每一行的 height[j]，统计“以该点为右下角”的矩形数量

	如果 height = [h1, h2, h3 ...]
	对于每个位置 j：

	向左扩展，最小高度 minH, 每次扩展能生成 minH 个矩形


```java


	public int numSubmat(int[][] mat) {
	        
		int M = mat.length, N = mat[0].length;

		int res = 0;

		int[] h = new int[N];
		for (int i = 0; i < M; ++i) {
			for (int j = 0; j < N; ++j) {
				h[j] = (mat[i][j] == 1 ? h[j] + 1 : 0);
			}
			res += helper(h);
		}

		return res;
	}

	private int helper(int[] A) {

		int[] sum = new int[A.length];
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < A.length; ++i) {
			//单调递增栈找 “左边第一个比我小的数”
			while (!stack.isEmpty() && A[stack.peek()] >= A[i]) stack.pop();

			// 情况 1：左边存在比 A[i] 小的高度 preIndex,栈顶元素就是 preIndex。
			// ... preIndex ... i
			// 从 preIndex+1 到 i 都是 ≥ A[i] 的高度区间，可以延伸的宽度为：i - preIndex
			// 此时矩形数：新形成的矩形 = A[i] * (i - preIndex)
			// 但注意：在 preIndex 之前的那些以 preIndex 为右端点的矩形，也可以延伸到 i，因此应该加上：sum[preIndex]
			if (!stack.isEmpty()) {
				int preIndex = stack.peek();
				sum[i] = sum[preIndex];
				sum[i] += A[i] * (i - preIndex);
			} 
			else {
				// 情况2： stack is empty: 左边没有比 A[i] 小的
				// 说明 A[i] 是左侧最小高度，可以往左扩到最左端 0。
				// 宽度 = i - (-1) = i+1
				// 矩形总数 = 高度 * 宽度 = A[i] * (i+1)
				sum[i] = A[i] * (i + 1);
			}

			stack.push(i);
		}

		int res = 0;
		for (int s : sum) res += s;

		return res;
	}



```	




模式 3：求 “前一个更小/大” + “后一个更小/大” 的组合

- 常见于包含当前元素的最大面积/最大乘积/最优区间等问题。

- ex. 2104. Sum of Subarray Ranges: The range of a subarray of nums is the difference 
between the largest and smallest element in the subarray. Return the sum of all subarray ranges of nums.
```
	Input: nums = [1,2,3]
	Output: 4
	Explanation: The 6 subarrays of nums are the following:
	[1], range = largest - smallest = 1 - 1 = 0 
	[2], range = 2 - 2 = 0
	[3], range = 3 - 3 = 0
	[1,2], range = 2 - 1 = 1
	[2,3], range = 3 - 2 = 1
	[1,2,3], range = 3 - 1 = 2
	So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.
```

核心思想：

    所有子数组的 max - min
    =（每个元素作为 max 时贡献的总和） -（每个元素作为 min 时贡献的总和）

也就是：

    数组中每个元素 nums[i]，会在若干个子数组中充当“最大值”和“最小值”。
    要做的是 算它出现多少次。

① 如何算 “nums[i] 在多少个子数组里是最大值”？

    用 单调递减栈 找：
    左侧第一个比它大的元素距离 L
    右侧第一个比它大的元素距离 R

    当 j 左边没有更大的（或更小的）元素时，左边的边界就是索引 -1，所以左边距离是 j - (-1) = j + 1。
    这表示：从 0 到 j 之间的所有子数组，都可以让 nums[j] 充当最大/最小值。

    则它作为最大值的子数组个数：

    count_max = L * R
    贡献 = nums[i] * count_max

② 如何算 “nums[i] 在多少个子数组里是最小值”？

    用 单调递增栈 找：
    左侧第一个比它小的元素距离 L, 右侧第一个比它小的元素距离 R
    则它作为最小值的子数组个数：count_min = L * R
    贡献 = nums[i] * count_min

```java
class Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        int[] minLeft = new int[n];
        int[] minRight = new int[n];
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        Deque<Integer> minStack = new ArrayDeque<Integer>();
        Deque<Integer> maxStack = new ArrayDeque<Integer>();

        for (int i = 0; i < n; i++) {
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);
            
            // 如果 nums[maxStack.peek()] == nums[i], 那么根据定义，
            // nums[maxStack.peek()] 逻辑上小于 nums[i]，因为 maxStack.peek() < i
            while (!maxStack.isEmpty() && nums[maxStack.peek()] <= nums[i]) { 
                maxStack.pop();
            }
            maxLeft[i] = maxStack.isEmpty() ? -1 : maxStack.peek();
            maxStack.push(i);
        }

        minStack.clear();
        maxStack.clear();
        
        for (int i = n - 1; i >= 0; i--) {
            // 如果 nums[minStack.peek()] == nums[i], 那么根据定义，
            // nums[minStack.peek()] 逻辑上大于 nums[i]，因为 minStack.peek() > i
            while (!minStack.isEmpty() && nums[minStack.peek()] >= nums[i]) { 
                minStack.pop();
            }
            minRight[i] = minStack.isEmpty() ? n : minStack.peek();
            minStack.push(i);

            while (!maxStack.isEmpty() && nums[maxStack.peek()] < nums[i]) {
                maxStack.pop();
            }
            maxRight[i] = maxStack.isEmpty() ? n : maxStack.peek();
            maxStack.push(i);
        }

        long sumMax = 0, sumMin = 0;
        for (int i = 0; i < n; i++) {
            sumMax += (long) (maxRight[i] - i) * (i - maxLeft[i]) * nums[i];
            sumMin += (long) (minRight[i] - i) * (i - minLeft[i]) * nums[i];
        }
        return sumMax - sumMin;
    }
}

```

## Deque

### Definition
- double-ended queue that supports addition or removal of elements from either end of the data structure
- it can be used as a queue (first-in-first-out/FIFO) or as a stack (last-in-first-out/LIFO). 

### Features

1. It provides the support of resizable array and helps in restriction-free capacity, so to grow the array according to the usage.
2. Array deques prohibit the use of Null elements and do not accept any such elements.
3. Any concurrent access by multiple threads is not supported.
4. In the absence of external synchronization, Deque is not thread-safe.

### Usage
![Usage](https://www.geeksforgeeks.org/wp-content/uploads/Selection_034.png)





## Heap

### Properties:

- A Heap is a special Tree-based data structure in which the tree is a complete binary tree. Generally, Heaps can be of two types --> Max-Heap and Min-Heap
	- Max-Heap: the key present at the root node must be greatest among the keys present at all of it’s children. The same property must be recursively true for all sub-trees in that Binary Tree.
	- doens't order left and right children nodes

- Binary heap:
	- is a Complete Binary Tree. A binary heap is typically represented as an array.
	- The root element will be at Arr[0].
	
	Arr[(i-1)/2]	Returns the parent node
	Arr[(2*i)+1]	Returns the left child node
	Arr[(2*i)+2]	Returns the right child node

1. 怎么记用哪种堆？（核心口诀）
最直观的记法是：“想要什么，就让什么浮到堆顶”，同时根据 Top-K 问题 的需求反向思考：

**找最大的 K 个数 (Top-K Large) --> 用 最小堆 (Min Heap)**
- 要找“最大的 K 个”，本质上是要剔除掉那些不够大的数。
- 堆的职责：在最小堆中，堆顶（Root）永远是当前堆里最小的那个。
- 筛选策略：我们将堆看作一个“准入委员会”。如果新来的数比堆顶（委员会里最弱的那个）还要大，说明堆顶已经没资格待在前 K 名里了。
- 结果：我们踢掉堆顶，把新数放进去。这样，经过一轮洗牌，留在堆里的永远是到目前为止发现的最大的 K 个。

```java
public List<Integer> getTopK(int[] nums, int k) {
    // 1. 初始化最小堆（默认即为最小堆）
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

    for (int num : nums) {
        if (minHeap.size() < k) {
            minHeap.offer(num);
        } else if (num > minHeap.peek()) {
            // 如果当前数比堆里最小的还大，替换它
            minHeap.poll();
            minHeap.offer(num);
        }
    }

    // 返回结果（此时堆中是最大的K个数，但由于是最小堆，出来的顺序是升序）
    return new ArrayList<>(minHeap);
}

```

**找最小的 K 个数 (Top-K Small) -->  用 最大堆 (Max Heap)**

2. Java 如何初始化 (Initialize)？

A. 最小堆 (Min Heap) —— 升序出列

// 方式 1：默认即为最小堆

PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// 方式 2：显式指定初始容量（为了性能优化）

PriorityQueue<Integer> minHeap = new PriorityQueue<>(11);

// 方式 3：Lambda 表达式（语义最清晰）

PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);



B. 最大堆 (Max Heap) —— 降序出列

// 方式 1：使用 Collections 翻转（最快，2026 推荐）

PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// 方式 2：Lambda 表达式（面试常用）

PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// 方式 3：对象属性比较

PriorityQueue<User> maxHeap = new PriorityQueue<>(Comparator.comparingInt(u -> u.id).reversed());

升序 (Min Heap)：a - b。逻辑：如果 a > b，结果为正，表示 a 的优先级低于 b（a 往后排，b 往堆顶走）。
降序 (Max Heap)：b - a。逻辑：如果 b > a，结果为正，表示 a 的优先级低于 b（b 往堆顶走）。

安全建议：
- 在处理可能溢出的整数（如 Integer.MIN_VALUE）时，不要直接相减。推荐使用：

- 升序：(a, b) -> Integer.compare(a, b)
- 降序：(a, b) -> Integer.compare(b, a)


### Complexity

1. 空间复杂度极其节省 O(K)：
- 无论总数据量 N 是 100 万还是 10 亿，你永远只需要一个大小为 K 的内存空间。这对于单机处理海量日志至关重要。

2. 时间复杂度极其稳定O(N log K)：
- 遍历 N 个数。每次调整堆的时间是 log K。相比于全量排序（O(N log N)，当 K 远小于 N 时，堆排序快得多。



### Applications of Heaps:

1. Heap Sort: Heap Sort uses Binary Heap to sort an array in *O(nLogn)* time.

	Algorithm for sorting in increasing order:
		1. Build a max heap from the input data.
		2. largest item is at the root of the heap. Replace it with the last item of the heap followed by reducing the size of heap by 1. Finally, heapify the root of tree.
		3. Repeat above steps while size of heap is greater than 1. (recursive!!)


```java

public void sort(int arr[]) 
    { 
        int n = arr.length; 
  
        // Build heap (rearrange array) 
        for (int i = n / 2 - 1; i >= 0; i--) 
            heapify(arr, n, i); 
  
        // One by one extract an element from heap 
        for (int i=n-1; i>=0; i--) 
        { 
            // Move current root to end 
            int temp = arr[0]; 
            arr[0] = arr[i]; 
            arr[i] = temp; 
  
            // call max heapify on the reduced heap 
            heapify(arr, i, 0); 
        } 
    } 

	// To heapify a subtree rooted with node i which is an index in arr[]. n is size of heap 
    void heapify(int arr[], int n, int i) 
    { 
        int largest = i; // Initialize largest as root 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 
  
        // If left child is larger than root 
        if (l < n && arr[l] > arr[largest]) 
            largest = l; 
  
        // If right child is larger than largest so far 
        if (r < n && arr[r] > arr[largest]) 
            largest = r; 
  
        // If largest is not root/ if we made change before
        if (largest != i) 
        { 
            int swap = arr[i]; 
            arr[i] = arr[largest]; 
            arr[largest] = swap; 
  
            // Recursively heapify the affected sub-tree 
            heapify(arr, n, largest); 
        } 
    }     

```

2. Priority Queue: Priority queues can be efficiently implemented using Binary Heap because it supports insert(), delete() and extractmax(), decreaseKey() operations in *O(logn)* time. Binomoial Heap and Fibonacci Heap are variations of Binary Heap. These variations perform union also efficiently.

3. Graph Algorithms: The priority queues are especially used in Graph Algorithms like Dijkstra’s Shortest Path and Prim’s Minimum Spanning Tree.

4. Many problems can be efficiently solved using Heaps. See following for example.
	- K’th Largest Element in an array.
	- Sort an almost sorted array/
	- Merge K Sorted Arrays.

### Operations & time complexity(Min Heap)

Operation               |Complexity| meaning 
------------------------|----------|------------------------------------------------
getMin()                | O(1)     | returns the root element of Min Heap.
extractMin()            | O(log n) | Removes the minimum element from MinHeap.O(Logn) because needs to  									 maintain the heap property (by calling heapify()) after removing root.
decreaseKey()           | O(log n) | Decreases value of key.
                                     If the decreases key value of a node is greater than the parent of  the node, then we don’t need to do anything. Otherwise, we need to  traverse up to fix the violated heap property.
insert()                | O(log n) | add a new key at the end of the tree. 
									 if new key is greater than its parent, then done. Otherwise, we need to traverse up to fix the violated heap property.
delete()                | O(log n) | We replace the key to be deleted with minum infinite by calling 
									 decreaseKey(). After decreaseKey(), the minus infinite value must reach root, so we call extractMin() to remove the key.
### Use deque to implement heap

	Deque<Integer> q = new ArrayDeque<>();
	q.isEmpty()
	q.peek() --> look at top
	peekLast();
	q.poll();
	q.pollLast();
	q.offer(i)

### Use priority queue to implement heap:

```java
initialize: 	PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>(); 
  
add: 			pQueue.add(10); 
peak:	        pQueue.peek()); 
  
printing all elements (with iterator):
		        Iterator itr = pQueue.iterator(); 
		        while (itr.hasNext()) 
		            System.out.println(itr.next()); 
  
Removing the top priority element (or head): 
				pQueue.poll(); 
        		pQueue.remove(30); 

Check if an element is present using contains(): 
        		boolean b = pQueue.contains(20); 

Getting objects from the queue using toArray() 
        		Object[] arr = pQueue.toArray(); 
        		
		        for (int i = 0; i < arr.length; i++) 
		            System.out.println("Value: " + arr[i].toString()); 
		     

```

### Implementation of a min heap

1. Use priority queue:

```java

	public static void main(String args[]) 
    { 
        // Creating empty priority queue 
        PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>(); 
  
  		// when need to implement a comparator
  		PriorityQueue<Node> minHeap = new PriorityQueue<Node>((n1, n2) -> matrix[n1.row][n1.col] - matrix[n2.row][n2.col]);

        // Adding items to the pQueue using add() 
        pQueue.add(10); 
        pQueue.add(30); 
        pQueue.add(20); 
        pQueue.add(400); 
  
        // Printing the most priority element/ root?
        pQueue.peek(); 
  
        // Printing all elements 
        Iterator itr = pQueue.iterator(); 
        while (itr.hasNext()) 
            System.out.println(itr.next()); 
  
        // Removing the top priority element (or head) and printing the modified pQueue using poll() 
        pQueue.poll(); 
  
        // Removing 30 using remove() 
        pQueue.remove(30); 
  
        // Check if an element is present using contains() 
        boolean b = pQueue.contains(20); 
  
        // Getting objects from the queue using toArray() in an array and print the array 
        Object[] arr = pQueue.toArray(); 
        System.out.println("Value in array: "); 
        for (int i = 0; i < arr.length; i++) 
            System.out.println("Value: " + arr[i].toString()); 
    } 
} 
```


2. Implement using java

```java
public class MinHeap { 
    private int[] Heap; 
    private int size; 
    private int maxsize; 
  
    private static final int FRONT = 1; 
  
    public MinHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = 0; 
        Heap = new int[this.maxsize + 1]; 
        Heap[0] = Integer.MIN_VALUE; 
    } 
  
    // Function to return the position of the parent for the node currently at pos 
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
  
    // Function to return the position of the left child for the node currently at pos 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
  
    // Function to return the position of the right child for the node currently at pos 
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
  
    // Function that returns true if the passed node is a leaf node 
    private boolean isLeaf(int pos) 
    { 
        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  
    // Function to swap two nodes of the heap 
    private void swap(int fpos, int spos) 
    { 
        int tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
  
    // Function to heapify the node at pos 
    private void minHeapify(int pos) 
    { 
  
        // If the node is a non-leaf node and greater than any of its child 
        if (!isLeaf(pos)) { 
            if (Heap[pos] > Heap[leftChild(pos)] || Heap[pos] > Heap[rightChild(pos)]) { 
  
                // Swap with the left child and heapify the left child 
                if (Heap[leftChild(pos)] < Heap[rightChild(pos)]) { 
                    swap(pos, leftChild(pos)); 
                    minHeapify(leftChild(pos)); 
                } 
  
                // Swap with the right child and heapify the right child 
                else { 
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
            } 
        } 
    } 
  
    // Function to insert a node into the heap 
    public void insert(int element) 
    { 
        if (size >= maxsize) { 
            return; 
        } 
        Heap[++size] = element; 
        int current = size; 
  
        while (Heap[current] < Heap[parent(current)]) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } 
  
    // Function to print the contents of the heap 
    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + Heap[i] 
                             + " LEFT CHILD : " + Heap[2 * i] 
                             + " RIGHT CHILD :" + Heap[2 * i + 1]); 
            System.out.println(); 
        } 
    } 
  
    // Function to build the min heap using 
    // the minHeapify 
    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        } 
    } 
  
    // Function to remove and return the minimum element from the heap 
    public int remove() 
    { 
        int popped = Heap[FRONT]; 
        Heap[FRONT] = Heap[size--]; 
        minHeapify(FRONT); 
        return popped; 
    } 
```


3. min heap of size k

```java

 	PriorityQueue<int[]> pq = new PriorityQueue<int[]>((p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
 	
    for (int[] p : points) {
        pq.offer(p);
        if (pq.size() > K) {
            pq.poll();
        }
    }




```
## Tree

### Binary search tree

若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
任意节点的左、右子树也分别为二叉查找树；
没有键值相等的节点。

#### Check for BST:
- 1. recursion: need to keep current lower & upper in the function call

```java

class Solution {
  public boolean helper(TreeNode node, Integer lower, Integer upper) {
    if (node == null) return true;

    int val = node.val;
    if (lower != null && val <= lower) return false;
    if (upper != null && val >= upper) return false;

    if (! helper(node.right, val, upper)) return false;
    if (! helper(node.left, lower, val)) return false;
    return true;
  }

  public boolean isValidBST(TreeNode root) {
    return helper(root, null, null);
  }
}



```

- 2. in order traversal:

```java
public boolean isValidBST(TreeNode root) {
   if (root == null) return true;
   Stack<TreeNode> stack = new Stack<>();
   TreeNode pre = null;
   while (root != null || !stack.isEmpty()) {
      while (root != null) {
         stack.push(root);
         root = root.left;
      }
      root = stack.pop();
      if(pre != null && root.val <= pre.val) return false;
      pre = root;
      root = root.right;
   }
   return true;
}
```	


### Trie -（Prefix Tree）

#### Properties of Trie:
![Trie](https://img-blog.csdn.net/20150509003807271)
- 上图是一棵Trie树，表示了关键字集合{“a”, “to”, “tea”, “ted”, “ten”, “i”, “in”, “inn”}

	1. 根节点不包含字符，除根节点外的每一个子节点都包含一个字符。
	2. 从根节点到某一个节点，路径上经过的字符连接起来，为该节点对应的字符串 --> 每个关键字保存在一条路径上，而不是一个结点中
	3. 每个节点的所有子节点包含的字符互不相同。
	4. Time Complexity: Insert and search costs O(key_length)
	5. Space: O(ALPHABET_SIZE * key_length * N) where N is number of keys in Trie. 

- 通常在实现的时候，会在节点结构中设置一个标志，用来标记该结点处是否构成一个单词（关键字）。

- 另外，两个有公共前缀的关键字，在Trie树中前缀部分的路径相同，所以Trie树又叫做前缀树（Prefix Tree）。

#### Pros and Cons

- Trie树的核心思想是空间换时间，利用字符串的公共前缀来减少无谓的字符串比较以达到提高查询效率的目的。

- Pros: 
1. 插入和查询的效率很高，都为O(m)，其中 m 是待插入/查询的字符串的长度。
	- 关于查询，会有人说 hash 表时间复杂度是O(1)不是更快？但是，哈希搜索的效率通常取决于 hash 函数的好坏，若一个坏的 hash 函数导致很多的冲突，效率并不一定比Trie树高。
	- 如果内存有限, 用trie树来压缩下空间，因为公共前缀都是用一个节点保存的

2. Trie树中不同的关键字不会产生冲突。

3. Trie树只有在允许一个关键字关联多个值的情况下才有类似hash碰撞发生。

4. Trie树不用求 hash 值，对短字符串有更快的速度。通常，求hash值也是需要遍历字符串的。

5. Trie树可以对关键字按字典序排序。

- Cons:
1. 当 hash 函数很好时，Trie树的查找效率会低于哈希搜索。
2. 空间消耗比较大。

#### Applications:

1. 字符串检索
	- 检索/查询功能是Trie树最原始的功能。思路就是从根节点开始一个一个字符进行比较
	- 如果沿路比较，发现不同的字符，则表示该字符串在集合中  <b>不存在 </b>
	- 如果所有的字符全部比较完并且全部相同，还需判断最后一个节点的标志位（标记该节点是否代表一个关键字）

```java
	struct trie_node
	{
	    bool isKey;   				// 标记该节点是否代表一个关键字
	    trie_node children[26]; 	// 各个子节点 
	}
```
2. 词频统计
	- 思路：为了实现词频统计，我们修改了节点结构，用一个整型变量count来计数。对每一个关键字执行插入操作，若已存在，计数加1，若不存在，插入后count置1。
	- 注意：第一、第二种应用也都可以用 hash table 来做。
```java
	struct trie_node
	{
	    int count;   				// 记录该节点代表的单词的个数
	    trie_node children[26]; 	// 各个子节点 
	}
```

3. 字符串排序
	- Trie树可以对大量字符串按字典序进行排序
	- 思路:遍历一次所有关键字，将它们全部插入trie树，树的每个结点的所有儿子很显然地按照字母表排序，然后先序遍历输出Trie树中所有关键字即可。

4. 前缀匹配
	- 例如：找出一个字符串集合中所有以ab开头的字符串。我们只需要用所有字符串构造一个trie树，然后输出以a->b->开头的路径上的关键字即可。
	- trie树前缀匹配常用于搜索提示。如当输入一个网址，可以自动搜索出可能的选择。当没有完全匹配的搜索结果，可以返回前缀最相似的可能。

5. 作为其他数据结构和算法的辅助结构
	- 后缀树，AC自动机等。


#### 操作

1. Insert new key:
	- The key character acts as an index into the array children. 
	- If the input key is new or an extension of the existing key, we need to construct non-existing nodes of the key, and mark end of the word for the last node. 
	- If the input key is a prefix of the existing key in Trie, we simply mark the last node of the key as the end of a word. The key length determines Trie depth.

2. Search:
	- We compare the characters and move down. 
	- search terminates if:
		1) reach the end of a string 
			- if the isEndofWord field of the last node is true, then the key exists in the trie
		2) lack of key in the trie.
			- the search terminates without examining all the characters of the key, since the key is not present in the trie.



#### Implementation

- Abbreviate version --> iterative 
```java
    public class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public String item = "";
    }
    
    private TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            // move down 1 level in trie
            node = node.children[c - 'a'];
        }

        // store item at the leaf node 
        node.item = word;
    }

```




- entire implementation
```java
 
public class Trie { 
	
	// Alphabet size (# of symbols) 
	static final int ALPHABET_SIZE = 26; 
	
	// trie node 
	static class TrieNode 
	{ 
		TrieNode[] children = new TrieNode[ALPHABET_SIZE]; 
	
		// isEndOfWord is true if the node represents end of a word 
		boolean isEndOfWord; 
		
		//constructor
		TrieNode(){ 
			isEndOfWord = false; 
			for (int i = 0; i < ALPHABET_SIZE; i++) 
				children[i] = null; 
		} 
	}; 
	
	static TrieNode root; 
	
	static void insert(String key) 
	{ 
		int level; 
		int length = key.length();  	// the level of bottom leaf
		int index; 
	
		TrieNode temp = root; 
	
		for (level = 0; level < length; level++) 
		{ 
			index = key.charAt(level) - 'a'; 

			// not present, inserts key into trie 
			if (temp.children[index] == null) 
				temp.children[index] = new TrieNode(); 
	
			// move down to the next layer
			temp = temp.children[index]; 
		} 
	
		// mark last node as leaf 
		temp.isEndOfWord = true; 
	} 
	
	// Returns true if key presents in trie, else false 
	static boolean search(String key) 
	{ 
		int level; 
		int length = key.length(); 
		int index; 
		
		TrieNode temp = root; 
	
		for (level = 0; level < length; level++) 
		{ 
			index = key.charAt(level) - 'a'; 
	
			if (temp.children[index] == null) 
				return false; 
	
			temp = temp.children[index]; 
		} 
	
		return (temp != null && temp.isEndOfWord); 
	} 
	
	// Driver 
	public static void main(String args[]) 
	{ 
		// Input keys (use only 'a' through 'z' and lower case) 
		String keys[] = {"the", "a", "there", "answer", "any", 
						"by", "bye", "their"}; 
	
		String output[] = {"Not present in trie", "Present in trie"}; 
	
	
		root = new TrieNode(); 
	
		// Construct trie 
		int i; 
		for (i = 0; i < keys.length ; i++) 
			insert(keys[i]); 
	
		// Search for different keys 
		if(search("the") == true) 
			System.out.println("the --- " + output[1]); 
		else System.out.println("the --- " + output[0]); 
		
		if(search("these") == true) 
			System.out.println("these --- " + output[1]); 
		else System.out.println("these --- " + output[0]); 
		
		if(search("their") == true) 
			System.out.println("their --- " + output[1]); 
		else System.out.println("their --- " + output[0]); 
		
		if(search("thaw") == true) 
			System.out.println("thaw --- " + output[1]); 
		else System.out.println("thaw --- " + output[0]); 
		
	} 
} 

```

- output: 
```java
the --- Present in trie
these --- Not present in trie
their --- Present in trie
thaw --- Not present in trie
```

#### Practise Problems
        lc 421: use search to do XOR
        lc 208: implement trie
        lc 336: palindrome
        lc 1065: 
        lc 720:
        lc 211:
        lc 648
        lc 677:




## Graph
[a relative link](graph.md)


### putIfAbsent


## TreeMap

!! sorted hashmap~

### General

The TreeMap in Java is used to implement Map interface and NavigableMap along with the Abstract Class.

The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time, depending on which constructor is used. This proves to be an efficient way of sorting and storing the key-value pairs. The storing order maintained by the treemap must be consistent with equals just like any other sorted map, irrespective of the explicit comparators. The treemap implementation is not synchronized in the sense that if a map is accessed by multiple threads, concurrently and at least one of the threads modifies the map structurally, it must be synchronized externally. Some important features of the treemap are:

TreeMap offers O(log N) lookup and insertion. Keys are ordered, so if you need to iterate through the keys in sorted order, you can. This means that keys must implement the Comparable interface. TreeMap is implemented by a Red-Black Tree.

Syntax:
public class TreeMap extends AbstractMap implements
NavigableMap, Cloneable, Serializable
A TreeMap contains values based on the key. It implements the NavigableMap interface and extends AbstractMap class.
It contains only unique elements.
It cannot have null key but can have multiple null values.
It is same as HashMap instead maintains ascending order(Sorted using the natural order of its key).

### functions

- ceilingKey() function of TreeMap Class returns the least key greater than or equal to the 
  given key or null if the such a key is absent.

Syntax:

public K ceilingKey(K key)
Parameters: This method accepts a mandatory parameter key which is the key to be searched for.


Return Value: This method returns the least key which is greater than or equal to the given key value.
If such a key is absent, null is returned.




### Example:
input:{1, -1, 0, 2,-2}; 

-2, -1, 0, 1, 2,   
// Keys are in sorted order

```java
    Map<String, String> treeMap = new TreeMap<>(map);
    for (String str : treeMap.keySet()) {
        System.out.println(str);
    }
```
### Question








## LinkedHashMap

sorted by insertion order. It is implemented by doubly-linked buckets.

LinkedHashMap: LinkedHashMap offers 0(1) lookup and insertion. 

Syntax:
    
    public class LinkedHashMap extends HashMap 
    0 implements Map

A LinkedHashMap contains values based on the key.
It contains only unique elements.
It may have one null key and multiple null values.
It is same as HashMap instead maintains insertion order.


### Example
input:{1, -1, 0, 2,-2}; 

 1, -1, 0, 2, -2,     
// Keys are ordered by their insertion order


## Hashmap

Syntax:

	import java.util.HashMap; // import the HashMap class
	HashMap<String, String> map = new HashMap<String, String>();

	map.put("key", "value");
	map.get("England"); //use key to get value
	map.remove("key");
	map.clear() //delete all items
	map.size;

	Hash_Map.clone(); //clone a hashmap

	// Print keys
	for (String i : map.keySet()) {
	  System.out.println(i);
	}

	// Print values
	for (String i : map.values()) {
	  System.out.println(i);
	}

	// Print keys and values
	for (String i : capitalCities.keySet()) {
	  System.out.println("key: " + i + " value: " + capitalCities.get(i));
	}

	containsKey(), containsValue()

	String value = map.toString(); //output as key=value, 0=135

;Note:
1.  LinkedHashMap is a variation of HashMap that preserves the insertion order.


### Hashtable & Hashmap
Hashtable is **synchronized** by default. This means that two or more threads may modify the data structure and will each wait their turn to do so
race condition could easily occur here between the containsKey() and put() method calls. This problem, and many similar issues, are solved in the ConcurrentHashMap implementation, which is a more modern and recommended alternative to the Hashtable.
- HashMap: no built-in synchronization and is therefore not safe to use in multithreaded applications. For example, iterating over a HashMap is fail-fast, which means that an exception will be thrown as soon as modification by another thread is detected. This lack of synchronization does, however, give a significant performance boost over synchronized alternatives in single-threaded implementations.
- Hashtable does not allow keys or values to be set to null. HashMap,allows any of its values to be null, as well as one of its keys. Keys still have to be unique, so this is why only one can be null.
- Why HashTable doesn’t allow null and HashMap do?: to store and retrieve objects from a HashTable, the objects used as keys must implement the hashCode method and the equals method. Since null is not an object, it can’t implement these methods


### Collision Handelling
What is Collision?
Since a hash function gets us a small number for a key which is a big integer or string, there is a possibility that two keys result in the same value. The situation where a newly inserted key maps to an already occupied slot in the hash table is called collision and must be handled using some collision handling technique.


What are the chances of collisions with large table?
Collisions are very likely even if we have big table to store keys. An important observation is Birthday Paradox. With only 23 persons, the probability that two people have the same birthday is 50%.


How to handle Collisions?
There are mainly two methods to handle collision:
1) Separate Chaining
2) Open Addressing

Separate Chaining:
The idea is to make each cell of hash table point to a linked list of records that have same hash function value.

Let us consider a simple hash function as “key mod 7” and sequence of keys as 50, 700, 76, 85, 92, 73, 101.

C++ program for hashing with chaining

Advantages:
1) Simple to implement.
2) Hash table never fills up, we can always add more elements to the chain.
3) Less sensitive to the hash function or load factors.
4) It is mostly used when it is unknown how many and how frequently keys may be inserted or deleted.

Disadvantages:
1) Cache performance of chaining is not good as keys are stored using a linked list. Open addressing provides better cache performance as everything is stored in the same table.
2) Wastage of Space (Some Parts of hash table are never used)
3) If the chain becomes long, then search time can become O(n) in the worst case.
4) Uses extra space for links.



Performance of Chaining:
Performance of hashing can be evaluated under the assumption that each key is equally likely to be hashed to any slot of table (simple uniform hashing).

 m = Number of slots in hash table
 n = Number of keys to be inserted in hash table
 
 Load factor α = n/m 
  
 Expected time to search = O(1 + α)
 
 Expected time to insert/delete = O(1 + α)

 Time complexity of search insert and delete is 
 O(1) if  α is O(1)

 ----
 https://www.geeksforgeeks.org/hashing-set-3-open-addressing/
 
Open Addressing
Like separate chaining, open addressing is a method for handling collisions. In Open Addressing, all elements are stored in the hash table itself. So at any point, size of the table must be greater than or equal to the total number of keys (Note that we can increase table size by copying old data if needed).

Insert(k): Keep probing until an empty slot is found. Once an empty slot is found, insert k.



Search(k): Keep probing until slot’s key doesn’t become equal to k or an empty slot is reached.

Delete(k): Delete operation is interesting. If we simply delete a key, then search may fail. So slots of deleted keys are marked specially as “deleted”.
Insert can insert an item in a deleted slot, but the search doesn’t stop at a deleted slot.

Open Addressing is done following ways:

a) Linear Probing: In linear probing, we linearly probe for next slot. For example, typical gap between two probes is 1 as taken in below example also.
let hash(x) be the slot index computed using hash function and S be the table size

If slot hash(x) % S is full, then we try (hash(x) + 1) % S
If (hash(x) + 1) % S is also full, then we try (hash(x) + 2) % S
If (hash(x) + 2) % S is also full, then we try (hash(x) + 3) % S 
..................................................
..................................................
Let us consider a simple hash function as “key mod 7” and sequence of keys as 50, 700, 76, 85, 92, 73, 101.

openAddressing

Clustering: The main problem with linear probing is clustering, many consecutive elements form groups and it starts taking time to find a free slot or to search an element.

b) Quadratic Probing We look for i2‘th slot in i’th iteration.

let hash(x) be the slot index computed using hash function.  
If slot hash(x) % S is full, then we try (hash(x) + 1*1) % S
If (hash(x) + 1*1) % S is also full, then we try (hash(x) + 2*2) % S
If (hash(x) + 2*2) % S is also full, then we try (hash(x) + 3*3) % S
..................................................
..................................................
c) Double Hashing We use another hash function hash2(x) and look for i*hash2(x) slot in i’th rotation.

let hash(x) be the slot index computed using hash function.  
If slot hash(x) % S is full, then we try (hash(x) + 1*hash2(x)) % S
If (hash(x) + 1*hash2(x)) % S is also full, then we try (hash(x) + 2*hash2(x)) % S
If (hash(x) + 2*hash2(x)) % S is also full, then we try (hash(x) + 3*hash2(x)) % S
..................................................
..................................................
See this for step by step diagrams.



Comparison of above three:
Linear probing has the best cache performance but suffers from clustering. One more advantage of Linear probing is easy to compute.

Quadratic probing lies between the two in terms of cache performance and clustering.

Double hashing has poor cache performance but no clustering. Double hashing requires more computation time as two hash functions need to be computed.

S.No.   Seperate Chaining   Open Addressing
1.  Chaining is Simpler to implement.   Open Addressing requires more computation.
2.  In chaining, Hash table never fills up, we can always add more elements to chain.   In open addressing, table may become full.
3.  Chaining is Less sensitive to the hash function or load factors.    Open addressing requires extra care for to avoid clustering and load factor.
4.  Chaining is mostly used when it is unknown how many and how frequently keys may be inserted or deleted. Open addressing is used when the frequency and number of keys is known.
5.  Cache performance of chaining is not good as keys are stored using linked list. Open addressing provides better cache performance as everything is stored in the same table.
6.  Wastage of Space (Some Parts of hash table in chaining are never used). In Open addressing, a slot can be used even if an input doesn’t map to it.
7.  Chaining uses extra space for links.    No links in Open addressing
Performance of Open Addressing:
Like Chaining, the performance of hashing can be evaluated under the assumption that each key is equally likely to be hashed to any slot of the table (simple uniform hashing)

 m = Number of slots in the hash table
 n = Number of keys to be inserted in the hash table
 
 Load factor α = n/m  ( < 1 )

 Expected time to search/insert/delete < 1/(1 - α) 

 So Search, Insert and Delete take (1/(1 - α)) time


## Linked List
