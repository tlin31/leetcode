456. 132 Pattern - Medium

Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak 
such that i < j < k and ai < ak < aj. 

Design an algorithm that takes a list of n numbers as input and checks whether there is 
a 132 pattern in the list.

Note: n will be less than 15,000.

Example 1:
Input: [1, 2, 3, 4]

Output: False

Explanation: There is no 132 pattern in the sequence.


Example 2:
Input: [3, 1, 4, 2]

Output: True
Explanation: There is a 132 pattern in the sequence: [1, 4, 2].

Example 3:
Input: [-1, 3, 2, 0]

Output: True

Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].


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

	- stack
	- use a stack to keep track of previous min-max intervals, push (min, max)
	- Algo:
		For each number num in the array
			If stack is empty:
				push a new Pair (n,n) of num into stack

			If stack is not empty:
				if num < stack.peek().min, push a new Pair of num into stack
				if num >= stack.peek().min, we first pop() out the peek element, denoted as last
				if num < last.max, we are done, return true;
				if num >= last.max, we merge num into last, which means last.max = num.
			
			Once we update last, if stack is empty, we just push back last.
		
		However, If stack is not empty, the updated last might:
			Entirely covered stack.peek(), i.e. last.min < stack.peek().min (which is always true) 
					&& last.max >= stack.peek().max, in which case we keep popping out stack.peek().
			Form a 1-3-2 pattern, we are done ,return true

	- So at any time in the stack, non-overlapping Pairs are formed in descending order by their 
	  min value, which means the min value of peek element in the stack is always the min value globally.

stats:

	- O(n)
	- 



   class Pair{
        int min, max;
        public Pair(int min, int max){
            this.min = min;
            this.max = max;
        }
    }

    public boolean find132pattern(int[] nums) {
        Stack<Pair> stack = new Stack();
        for(int n: nums){
            if(stack.isEmpty() || n <stack.peek().min ) 
            	stack.push(new Pair(n,n));
            else if(n > stack.peek().min){ 
                Pair last = stack.pop();

                // find the pattern
                if(n < last.max) 
                	return true;

                // if n > the max, move this to the new max
                // only update the max, min is always the global min
                else {
                    last.max = n;

                    // delete all pair with max < n
                    while(!stack.isEmpty() && n >= stack.peek().max) 
                        stack.pop();

                    // At this time, stack.peek().min < n < stack.peek().max 
                    // 132 = min, stack.peek.max, n
                    if(!stack.isEmpty() && stack.peek().min < n) 
                    	return true;

                    stack.push(last);
                }
                
            }
        }
        return false;
    }

--------- Or let stack always contain : (top) min, num (bottom)
    
    // push aj before ai (i < j, ai < aj)
    Stack<Integer> stack = new Stack<>(); 
    int min = Integer.MAX_VALUE;
    for (int num : nums) {
        if (num <= min) {
            min = num;
        } else {
        	// num > min, it is a potential '3'
            while (!stack.empty()) {
                if (stack.peek() >= num) break;
                stack.pop();
                if (stack.pop() > num) return true;
            }
            stack.push(num);
            stack.push(min);
        }
    }
    return false;


=======================================================================================================
method 2:

method:

	- use array as stack
	- We can remove those elements(update the index k) which are not greater than nums[i](min[j]). 
	  Thus, in case no element is larger than min[j] the index k reaches the last element.
	- Now, at every step, only nums[j] will be added and removed from consideration in the next 
	  step, improving the time complexity in the worst case. The rest of the method remains the 
	  same as in Approach 4.




stats:

	- Runtime: 2 ms, faster than 100.00% of Java online submissions for 132 Pattern.
	- Memory Usage: 42.5 MB, less than 14.29% 


public class Solution {
    public boolean find132pattern(int[] nums) {
        if (nums.length < 3)
            return false;
        int[] min = new int[nums.length];
        min[0] = nums[0];

        // compare current with last min
        for (int i = 1; i < nums.length; i++)
            min[i] = Math.min(min[i - 1], nums[i]);

        // go from back
        for (int j = nums.length - 1, k = nums.length; j >= 0; j--) {
            if (nums[j] > min[j]) {
                while (k < nums.length && nums[k] <= min[j])
                    k++;
                if (k < nums.length && nums[k] < nums[j])
                    return true;
                nums[--k] = nums[j];
            }
        }
        return false;
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



