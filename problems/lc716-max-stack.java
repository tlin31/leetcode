716. Max Stack - HARD


Design a max stack that supports push, pop, top, peekMax and popMax.

	1. push(x) -- Push element x onto stack.
	2. pop() -- Remove the element on top of the stack and return it.
	3. top() -- Get the element on the top.
	4. peekMax() -- Retrieve the maximum element in the stack.
	5. popMax() -- Retrieve the maximum element in the stack, and remove it. If you find more than 
					one maximum elements, only remove the top-most one.

Example 1:
MaxStack stack = new MaxStack();
stack.push(5); 
stack.push(1);
stack.push(5);
stack.top(); -> 5
stack.popMax(); -> 5
stack.top(); -> 1
stack.peekMax(); -> 5
stack.pop(); -> 1
stack.top(); -> 5


Note:
-1e7 <= x <= 1e7
Number of operations won't exceed 10000.
The last four operations won't be called when stack is empty.


******************************************************
key:
	- tree map + double linked list
	- edge case:
		1) empty string, return empty
		2)

******************************************************


解法 2（最优）：双向链表 + TreeMap（全部操作 O(log n)）

1. 一个 双向链表 保存栈顺序，栈顶就是链表尾部。

bottom <-> ... <-> ... <-> top


2. 一个 TreeMap<Integer, List<Node>> 保存每个值对应的链表节点们

key 是值，value 是一组链表节点（同值可能有很多）

TreeMap 自动给我们：

O(log n) 找最大值 (lastKey())，可以快速定位到最大值对应的节点（List 最后一个）

3. 删除操作只需要 O(1) on node（因为双向链表可以 O(1) 删除节点）


面试问题：
2. 为什么选 TreeMap？

	自动排序 → O(log n) 找 max & 可以按 value 聚合多个 Node & 支持并发扩展（ConcurrentSkipListMap）

	像 Redis ZSet（skiplist + hash）有类似结构。

3. 为什么用双向链表？

	可以 O(1) 删除任意节点（知道 Node 引用）

	保持 push/pop 顺序 → 这是“栈”的本质

	双向链表 + Map = 常见工程结构（例如 LRU Cache）。

4. 工程中的应用场景是什么？

	实时任务调度：快速删除最大优先级任务

	实时流计算：按最大权重取出元素

	实时风控：栈式结构 + 最大值监控

	面试官听到这些会直接感觉你“有工程视角”。



class MaxStack {

    class Node {
        int val;
        Node prev, next;
        Node(int v) { val = v; }
    }

    Node head, tail; //sentinel node
    TreeMap<Integer, List<Node>> map;

    public MaxStack() {
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
        map = new TreeMap<>();
    }

    private Node addNode(int x) {
        Node n = new Node(x);
        n.prev = tail.prev;
        n.next = tail;
        tail.prev.next = n;
        tail.prev = n;
        return n;
    }

    private void removeNode(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    public void push(int x) {
        Node n = addNode(x);
        map.computeIfAbsent(x, k -> new ArrayList<>()).add(n);
    }

    public int pop() {
        Node n = tail.prev;
        removeNode(n);

        List<Node> nodes = map.get(n.val);
        nodes.remove(nodes.size() - 1);
        if (nodes.isEmpty()) map.remove(n.val);

        return n.val;
    }

    public int top() {
        return tail.prev.val;
    }

    public int peekMax() {
        return map.lastKey();
    }

    public int popMax() {
        int max = peekMax();
        List<Node> nodes = map.get(max);
        Node n = nodes.remove(nodes.size() - 1);
        if (nodes.isEmpty()) map.remove(max);

        removeNode(n);
        return max;
    }
}


=======================================================================================================
Method 1:  Two Stacks


Stats:

	- Time Complexity: O(N) for the popMax operation, and O(1) for the other operations, where 
	                   N is the number of operations performed.

	- Space Complexity: O(N), the maximum size of the stack.

push: O(1)

pop: O(1)

peekMax: O(1)

popMax: O(n)（最坏）

优点：简单，面试写得完。
缺点：右边很深的栈会退化到 O(n)。

Method:

	-  A regular stack already supports the first 3 operations, so we focus on the last two.

	-  peekMax: we remember the largest value we've seen on the side. 
		For example if we add [2, 1, 5, 3, 9], we'll remember [2, 2, 5, 5, 9]. 
		it's just the maximum of the element we are adding and the previous maximum.

	-  popMax: we know what the current maximum (peekMax) is. We can pop until we find that maximum, 
	           then push the popped elements back on the stack.


class MaxStack {
    Stack<Integer> stack;
    Stack<Integer> maxStack;

    public MaxStack() {
        stack = new Stack();
        maxStack = new Stack();
    }

    public void push(int x) {
        int max = maxStack.isEmpty() ? x : maxStack.peek();
        maxStack.push(max > x ? max : x);

        stack.push(x);
    }

    public int pop() {
        maxStack.pop();
        return stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int peekMax() {
        return maxStack.peek();
    }

    public int popMax() {
        int max = peekMax();

        // create a new buffer stack
        Stack<Integer> buffer = new Stack();

        while (top() != max) 
        	buffer.push(pop());

        pop();

        while (!buffer.isEmpty()) 
        	push(buffer.pop());

        return max;
    }
}

=======================================================================================================
method 2:  tree & linked-list

Stats:

	- Time Complexity: O(logN) for all operations except peek which is O(1), where N is the number 
	 				   of operations performed. Most operations involving TreeMap are O(logN).

	- Space Complexity: O(N), the size of the data structures used.



Method:

	-  double linked list as our "stack" = ddl. This reduces the problem to finding which node to remove, 
	   since we can remove nodes in O(1) time.

	-  use a TreeMap mapping values to a list of nodes to answer this question. TreeMap can find the 
	   largest value, insert values, and delete values, all in O(log N) time.

	-  MaxStack.push(x), we add a node to our dll, and add or update our entry map.get(x).add(node).

		MaxStack.pop(), we find the value val = dll.pop(), and remove the node from our map, deleting 
		                the entry if it was the last one.

		MaxStack.popMax(), we use the map to find the relevant node to unlink, and return it's value.


	class Node {
	    int val;
	    Node prev, next;
	    public Node(int v) {
	    	val = v;
	    }
	}

	class DoubleLinkedList {
	    Node head, tail;

	    public DoubleLinkedList() {
	        head = new Node(0);
	        tail = new Node(0);
	        head.next = tail;
	        tail.prev = head;
	    }

	    // add from tail!
	    public Node add(int val) {
	        Node x = new Node(val);
	        x.next = tail;
	        x.prev = tail.prev;
	        tail.prev = tail.prev.next = x;
	        return x;
	    }

	    public int pop() {
	        return unlink(tail.prev).val;
	    }

	    public int peek() {
	        return tail.prev.val;
	    }

	    public Node unlink(Node node) {
	        node.prev.next = node.next;
	        node.next.prev = node.prev;
	        return node;
	    }
	}


	class MaxStack {

		// TreeMap is ordered by its key
	    TreeMap<Integer, List<Node>> map;
	    DoubleLinkedList dll;

	    public MaxStack() {
	        map = new TreeMap();
	        dll = new DoubleLinkedList();
	    }

	    public void push(int x) {
	        Node node = dll.add(x);
	        if(!map.containsKey(x))
	            map.put(x, new ArrayList<Node>());

	        map.get(x).add(node);
	    }

	    public int pop() {
	        int val = dll.pop();

	        // remove from map
	        List<Node> L = map.get(val);
	        L.remove(L.size() - 1);
	        if (L.isEmpty()) 
	        	map.remove(val);
	        return val;
	    }

	    public int top() {
	        return dll.peek();
	    }

	    public int peekMax() {
	        return map.lastKey();
	    }

	    public int popMax() {
	        int max = peekMax();
	        List<Node> L = map.get(max);
	        Node node = L.remove(L.size() - 1);
	        dll.unlink(node);

	        // if after pop, list is empty, then remove the key from hashmap
	        if (L.isEmpty()) 
	        	map.remove(max);

	        return max;
	    }
	}




=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	
class MaxStack {
    
    private static class ListNode {
        public ListNode prev, next;
        public int value;
        
        public ListNode(int val) {
            this.value = val;
        }
    }
    
    private final ListNode head;
    private final TreeMap<Integer, LinkedList<ListNode>> map = new TreeMap<>(); 

    /** initialize your data structure here. */
    public MaxStack() {
        head = new ListNode(0);
        head.next = head.prev = head;
    }
    
    public void push(int x) {
        ListNode node = new ListNode(x);
        node.next = head;
        node.prev = head.prev;
        head.prev.next = node;
        head.prev = node;
        map.computeIfAbsent(x, k -> new LinkedList<>()).add(node);
    }
    
    public int pop() {
        ListNode tail = head.prev;
        if (tail == head) {
            return 0;   // no element exist
        }
        deleteNode(tail);
        // since it's pop(), we are always sure that the last element in the map's value list will be the tail
        map.get(tail.value).removeLast();
        if (map.get(tail.value).isEmpty()) {
            map.remove(tail.value);
        }
        return tail.value;
    }
    
    public int top() {
        return head.prev.value;
    }
    
    public int peekMax() {
        return map.lastKey();
    }
    
    public int popMax() {
        int max = peekMax();
        ListNode node = map.get(max).removeLast();
        deleteNode(node);
        if (map.get(max).isEmpty()) {
            map.remove(max);
        }
        return max;
    }
    
    private void deleteNode(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}


