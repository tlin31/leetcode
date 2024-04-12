716. Max Stack - Easy


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



=======================================================================================================
Method 1:  Two Stacks


Stats:

	- Time Complexity: O(N) for the popMax operation, and O(1) for the other operations, where 
	                   N is the number of operations performed.

	- Space Complexity: O(N), the maximum size of the stack.



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


