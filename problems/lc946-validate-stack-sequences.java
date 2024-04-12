946. Validate Stack Sequences - Medium

Given two sequences pushed and popped with distinct values, return true if and only if this 
could have been the result of a sequence of push and pop operations on an initially empty stack.

 

Example 1:
Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
Output: true
Explanation: We might do the following sequence:
push(1), push(2), push(3), push(4), pop() -> 4,
push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1


Example 2:
Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
Output: false
Explanation: 1 cannot be popped before 2.
 

Note:

0 <= pushed.length == popped.length <= 1000
0 <= pushed[i], popped[i] < 1000
pushed is a permutation of popped.
pushed and popped have distinct values.


******************************************************
key:
	- Greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- If the stack has say, 2 at the top, then if we have to pop that value next, we must do it now.
	  That is because any subsequent push will make the top of the stack different from 2, and we 
	  will never be able to pop again.

Algorithm
	- For each value, push it to the stack.
	- Then, greedily pop values from the stack if they are the next values to pop.
	- At the end, we check if we have popped all the values successfully.


stats:

	- Time: O(n)
	- Space: O(n, where N is the length of pushed and popped.)

class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int N = pushed.length;
        Stack<Integer> stack = new Stack();

        int j = 0;
        for (int x: pushed) {
            stack.push(x);
            while (!stack.isEmpty() && j < N && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

        return j == N;
    }
}


=======================================================================================================
method 2:

method:

	- Using pushed as Stack but will change the input


	- 

stats:

	- Time O(N)
	- Space O(1)


    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0, j = 0;
        for (int x : pushed) {
            pushed[i++] = x;
            while (i > 0 && pushed[i - 1] == popped[j]) {
                --i; ++j;
            }
        }
        return i == 0;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



