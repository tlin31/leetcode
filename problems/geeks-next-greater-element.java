Geeks - Next Greater Element

Question:
Given an array, print the Next Greater Element (NGE) for every element. The Next greater Element for an
element x is the first greater element on the right side of x in array. Elements for which no greater
element exist, consider next greater element as -1.

Examples:

For any array, rightmost element always has next greater element as -1.
For an array which is sorted in decreasing order, all elements have next greater element as -1.
For the input array [4, 5, 2, 25}, the next greater elements for each element are as follows.

Element       NGE
   4      -->   5
   5      -->   25
   2      -->   25
   25     -->   -1


d) For the input array [13, 7, 6, 12}, the next greater elements for each element are as follows.

  Element        NGE
   13      -->    -1
   7       -->     12
   6       -->     12
   12      -->     -1

******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Using Stack
	- Algorithm: 
		1. Push the first element to stack.
		2. Pick rest of the elements one by one and follow the following steps in loop.
			- Mark the current element as next.
			- If stack is not empty, compare top element of stack with next.
			- If next is greater than the top element, pop element from stack. 
			  next is the next greater element for the popped element.
			- Keep popping from the stack while the popped element is smaller than next. 
			  next becomes the next greater element for all such popped elements
		3. Finally, push the next in the stack.
		4. when loop ends, pop all the elements from stack and print -1 as next element for them.
	- 

stats:

	- Time Complexity: O(n).
		The worst case occurs when all elements are sorted in decreasing order. 
		If elements are sorted in decreasing order, then every element is processed at most 4 times.
		
		Initially pushed to the stack.
		Popped from the stack when next element is being processed.
		Pushed back to the stack because the next element is smaller.
		Popped from the stack in step 3 of algorithm.
	- 


public class NGE 
{ 
	static class stack 
	{ 
		int top; 
		int items[] = new int[100]; 

		// Stack functions to be used by printNGE 
		void push(int x) 
		{ 
			if (top == 99) 
			{ 
				System.out.println("Stack full"); 
			} 
			else
			{ 
				items[++top] = x; 
			} 
		} 

		int pop() 
		{ 
			if (top == -1) 
			{ 
				System.out.println("Underflow error"); 
				return -1; 
			} 
			else
			{ 
				int element = items[top]; 
				top--; 
				return element; 
			} 
		} 

		boolean isEmpty() 
		{ 
			return (top == -1) ? true : false; 
		} 
	} 

	/* prints element and NGE pair for 
	all elements of arr[] of size n */
	static void printNGE(int arr[], int n) 
	{ 
		int i = 0; 
		stack s = new stack(); 
		s.top = -1; 
		int element, next; 

		/* push the first element to stack */
		s.push(arr[0]); 

		// iterate for rest of the elements 
		for (i = 1; i < n; i++) 
		{ 
			next = arr[i]; 

			if (s.isEmpty() == false) 
			{ 
				
				// if stack is not empty, then 
				// pop an element from stack 
				element = s.pop(); 

				/* If the popped element is smaller than 
				next, then a) print the pair b) keep 
				popping while elements are smaller and 
				stack is not empty */
				while (element < next) 
				{ 
					System.out.println(element + " --> " + next); 
					if (s.isEmpty() == true) 
						break; 
					element = s.pop(); 
				} 

				/* If element is greater than next, then 
				push the element back */
				if (element > next) 
					s.push(element); 
			} 

			/* push next to stack so that we can find next 
			greater for it */
			s.push(next); 
		} 

		/* After iterating over the loop, the remaining 
		elements in stack do not have the next greater 
		element, so print -1 for them */
		while (s.isEmpty() == false) 
		{ 
			element = s.pop(); 
			next = -1; 
			System.out.println(element + " -- " + next); 
		} 
	} 

	public static void main(String[] args) 
	{ 
		int arr[] = { 11, 13, 21, 3 }; 
		int n = arr.length; 
		printNGE(arr, n); 
	} 
} 



