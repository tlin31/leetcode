651. 4 Keys Keyboard - Medium


Imagine you have a special keyboard with the following keys:

	Key 1: (A): Print one 'A' on screen.

	Key 2: (Ctrl-A): Select the whole screen.

	Key 3: (Ctrl-C): Copy selection to buffer.

	Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.

Now, you can only press the keyboard for N times (with the above four keys), find out the maximum 
numbers of 'A' you can print on screen.

Example 1:
Input: N = 3
Output: 3
Explanation: 
We can at most get 3 A's on screen by pressing following key sequence:
A, A, A

Example 2:
Input: N = 7
Output: 9
Explanation: 
We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V

Note:
1 <= N <= 50
Answers will be in the range of 32-bit signed integer.

******************************************************
key:
	- kind of greedy --> leave the copy paste work at the end
	- math + dp
	- edge case:
		1) if N <= 4? return N
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time Complexity: O(N^2) We have two nested for-loops, each of which do O(N)O(N) work.

	- Space Complexity: O(N), the size of best.



Method:

	- We use i steps to reach maxA(i) then use the remaining n - i steps to reach n - i - 1 copies 
	  of maxA(i)

		ex. A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
		  Here we have n = 7 and we used i = 3 steps to reach AAA
		  Then we use the remaining n - i = 4 steps: Ctrl A, Ctrl C, Ctrl V, Ctrl V, to reach 
		  n - i - 1 = 3 copies total of AAA

	- We either don not make copies at all, in which case the answer is just n, or if we want to 
	  make copies, we need to have 3 steps reserved for Ctrl A, Ctrl C, Ctrl V so i can be at 
	  most n - 3

    public int maxA(int n) {
        int max = n;
        for (int i = 1; i <= n - 3; i++)
            max = Math.max(max, maxA(i) * (n - i - 1));
        return max;
    }


Now making it a DP where dp[i] is the solution to sub-problem maxA(i)

    public int maxA(int n) {
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = i;
            for (int j = 1; j <= i - 3; j++)
                dp[i] = Math.max(dp[i], dp[j] * (i - j - 1));
        }
        return dp[n];
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

