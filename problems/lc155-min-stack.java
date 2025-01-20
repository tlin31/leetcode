155. Min Stack - Easy-->medium

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
 

Example:

MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.


******************************************************
key:
	- normal stack except the getMin() part, keep a global constant
	- interesting 2 stack (track the global min)
	- edge case:
		1) empty string, return empty
		2)

******************************************************

class MinStack {
    private Node head;
    
    private class Node {
        int val;
        int min;
        Node next;
            
        private Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }
        
    public void push(int x) {
        if (head == null) 
            head = new Node(x, x, null);
        else 
            head = new Node(x, Math.min(x, head.min), head);
    }
    
    public void pop() {
        head = head.next;
    }
    
    public int top() {
        return head.val;
    }
    
    public int getMin() {
        return head.min;
    }
        
    
}

=======================================================================================================
method 1:

method:
	- stack stores the pair [cur_value, min_value]
	- the numbers below it will not change for as long as number x remains on the Stack
      Numbers could come and go above x for the duration of x's presence, but never below.

	- So, whenever number x is the top of the Stack, the minimum will always be the same, as it's 
	  simply the minimum out of x and all the numbers below it. 

ex.
min stack:		[12,12]		[30,12]		[7,7]		[6,6]		[45,6]		[6,6]	[14,6]	



stats:

	- time: O(1)
	- space: O(n)

class MinStack {
    
    private Stack<int[]> stack = new Stack<>();
    
    public MinStack() { }
    
    public void push(int x) {
        
        // If the stack is empty, then the min value must just be the first value we add.
        if (stack.isEmpty()) {
            stack.push(new int[]{x, x});
            return;
        }
        
        int currentMin = stack.peek()[1];
        stack.push(new int[]{x, Math.min(x, currentMin)});
    }
    
    
    public void pop() {
        stack.pop();
    }
    
    
    public int top() {
        return stack.peek()[0];
    }
    
    
    public int getMin() {
        return stack.peek()[1];
    }
}


=======================================================================================================
method 2:

method:

	- 2 stacks
	- An improvement is to put pairs onto the min-tracker Stack. The first value of 
    the pair would be the same as before, and the second value would be how many times 
    that minimum was repeated. For example, this is how the min-tracker Stack for the 
    example just above would appear.

ex.
min stack:		12		30		7		6		45		6		6		14		6
min-tracker:	[12,1]	[7,1]	[6,4]

stats:

	- 
	- 

class MinStack {

    private Stack<Integer> stack = new Stack<>();
    private Stack<int[]> minStack = new Stack<>();
    
    
    public MinStack() { }
    
    
    public void push(int x) {
        
        // We always put the number onto the main stack.
        stack.push(x);
        
        // If  min stack is empty, OR cur_num < stack.top, put it on with a count of 1.
        if (minStack.isEmpty() || x < minStack.peek()[0]) {
            minStack.push(new int[]{x, 1});
        }
        
        // Else if this number is equal to what's currently at the top
        // of the min stack, then increment the count at the top by 1.
        else if (x == minStack.peek()[0]) {
            minStack.peek()[1]++;
        }
    }
    
    
    public void pop() {
        
        // If the top of min stack is the same as the top of stack
        // then we need to decrement the count at the top by 1.
        if (stack.peek().equals(minStack.peek()[0])) {
            minStack.peek()[1]--;
        }
        
        // If the count at the top of min stack is now 0, then remove
        // that value as we're done with it.
        if (minStack.peek()[1] == 0) {
            minStack.pop();
        }
        
        // And like before, pop the top of the main stack.
        stack.pop();
    }
    
    
    public int top() {
        return stack.peek();
    }

    
    public int getMin() {
        return minStack.peek()[0];
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



