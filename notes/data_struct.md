# Table of contents
1. Complexity
2. Queue
    1. Priority Queue
3. Stack
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

1. add()和offer()区别:

- add()和offer()都是向队列中添加一个元素。
	- 一些队列有大小限制，因此如果想在一个满的队列中加入一个新项，调用 add() 方法就会抛出一个 unchecked 异常，而调用 offer() 方法会返回 false。因此就可以在程序中进行有效的判断！

2. poll()和remove()区别：

- remove() 和 poll() 方法都是从队列中删除第一个元素。
	- 如果队列元素为空，调用remove() 的行为与 Collection 接口的版本相似会抛出异常，但是新的 poll() 方法在用空集合调用时只是返回 null。因此新的方法更适合容易出现异常条件的情况。

 
3. element() 和 peek() 区别：

- element() 和 peek() 用于在队列的头部查询元素。
	- 与 remove() 方法类似，在队列为空时， element() 抛出一个异常，而 peek() 返回 null。

### declare
1. import java.util.* and directly use Queue

	Queue < TreeNode > queue = new LinkedList <> ();

2. use a linkedlist to store the queue, which supports add(), poll(), size(), etc.

    LinkedList<Integer> queue = new LinkedList<Integer>(); 


### Java中Queue的一些常用方法：
add         增加一个元索                  如果队列已满，则抛出一个IIIegaISlabEepeplian异常

remove   	移除并返回队列头部的元素     	如果队列为空，则抛出一个NoSuchElementException异常

element  	返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常

offer       添加一个元素并返回true        	如果队列已满，则返回false

poll        移除并返问队列头部的元素     	如果队列为空，则返回null

peek       	返回队列头部的元素             如果队列为空，则返回null

put         添加一个元素                  如果队列满，则阻塞

take        移除并返回队列头部的元素    


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






## Stack

### java
1. initialize + import:

```java
	import java.util.*; 
		Stack<Integer> stack = new Stack<Integer>(); 
		Deque<TreeNode> stack = new ArrayDeque<>(); //deque
```

2. push to the stack:

		stack.push(i); 

3. pop from stack:

		Integer y = (Integer) stack.pop();

4. look at the top, but don't pop it		

		Integer element = (Integer) stack.peek();
		
5. search in the stack:

		Integer pos = (Integer) stack.search(element); 


6. print stack from bottom up:

	ex. stack: (top) a b c d (bottom)

		for (String dir : stack) {
            result.append("/");
            result.append(dir);
        }

    output: /d/c/b/a

===== or use deque:
	    Deque < TreeNode > stack = new LinkedList < > ();


### 单调栈 Monotonic Stack

当你遇到 **“找临近比它大/小的元素”、“下一次更大/更小”、“区间扩展到不能扩展为止”**

单调栈（Monotonic Stack）是处理 **“局部与周围关系”** 的神器

✅ 一、最核心规律

当想找“下一个更大”或“上一个更大” → 用**单调递减栈,条件是cur > stack.peek()就pop**，pop出来的数字的next greater element就是cur
单调递减栈 = 栈顶最大 → 当遇到更大的元素时才会 Pop

当想找“下一个更小”或“上一个更小” → 用单调递增栈
单调递增栈 = 栈顶最小 → 当遇到更小的元素时才会 Pop

栈里永远保持你不想被 Pop 的元素。




✅ 二、例子
**模式1：Next Greater Element / Next Smaller Element**

给定数组，每个元素要找到：

- 下一个更大的元素 / 上一个更大的元素

- 下一个更小的元素 / 上一个更小的元素


例子：
- ① Next Greater Element I — LC 496: 用decreasing stack, 条件是cur > stack.peek()就pop，越小的数越靠上
- ② Next Greater Element II — LC 503
- ③ Daily Temperatures — LC 739 → 找下一天更高温度
- ④ Online Stock Span — LC 901 → 找连续小于等于当前的天数（我们刚讲过）


ex. LC 739,Daily Temperatures

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

stack，因为一旦碰到小的，就不把他算到面积里，直接结算他之前的
  
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



模式 3：求 “前一个更小/大” + “后一个更小/大” 的组合

常见于包含当前元素的最大面积/最大乘积/最优区间等问题。





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
