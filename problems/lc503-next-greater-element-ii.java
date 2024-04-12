503. Next Greater Element II - Medium


Given a circular array (the next element of the last element is the first element of the array), 
print the Next Greater Number for every element. The Next Greater Number of a number x is the 
first greater number to its traversing-order next in the array, which means you could search 
circularly to find its next greater number. If it doesnt exist, output -1 for this number.

Example 1:
Input: [1,2,1]
Output: [2,-1,2]
Explanation: The first 1s next greater number is 2; 
The number 2 cant find next greater number; 
The second 1s next greater number needs to search circularly, which is also 2.
Note: The length of given array wont exceed 10000.


******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N) for one pass, Spce O(N) in worst case
	- 


Method:

	-	Loop once, we can get the Next Greater Number of a normal array.
      	Loop twice, we can get the Next Greater Number of a circular array



    public int[] nextGreaterElements(int[] A) {
        int n = A.length, 
            res[] = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n * 2; i++) {
            while (!stack.isEmpty() && A[stack.peek()] < A[i % n])
                res[stack.pop()] = A[i % n];

            stack.push(i % n);
        }
        return res;
    }





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


    def nextGreaterElements(self, A):
        stack, res = [], [-1] * len(A)
        for i in range(len(A)) * 2:
            while stack and (A[stack[-1]] < A[i]):
                res[stack.pop()] = A[i]
            stack.append(i)
        return res

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

