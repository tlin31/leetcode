902. Numbers At Most N Given Digit Set - Hard


We have a sorted set of digits D, a non-empty subset of {'1','2','3','4','5','6','7','8','9'}.  
(Note that '0' is not included.)

Now, we write numbers using these digits, using each digit as many times as we want.  
For example, if D = {'1','3','5'}, we may write numbers such as '13', '551', '1351315'.

Return the number of positive integers that can be written (using the digits of D) that are less 
than or equal to N.

 

Example 1:
Input: D = ["1","3","5","7"], N = 100
Output: 20
Explanation: 
The 20 numbers that can be written are:
1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.


Example 2:
Input: D = ["1","4","9"], N = 1000000000
Output: 29523
Explanation: 
We can write 3 one digit numbers, 9 two digit numbers, 27 three digit numbers,
81 four digit numbers, 243 five digit numbers, 729 six digit numbers,
2187 seven digit numbers, 6561 eight digit numbers, and 19683 nine digit numbers.
In total, this is 29523 integers that can be written using the digits of D.
 

Note:

D is a subset of digits '1'-'9' in sorted order.
1 <= N <= 10^9




******************************************************
key:
	- Math
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- O(log N)
	- O(log N)


Method:

	-  dp, go from the least significant bit to most, actually just keep on adding
	-	

class Solution {
    public int atMostNGivenDigitSet(String[] D, int N) {
        String S = String.valueOf(N);
        int K = S.length();
        int[] dp = new int[K+1];
        dp[K] = 1;

        for (int i = K-1; i >= 0; --i) {
            // compute dp[i]
            int Si = S.charAt(i) - '0';
            for (String d: D) {
                if (Integer.valueOf(d) < Si)
                    dp[i] += Math.pow(D.length, K-i-1);
                else if (Integer.valueOf(d) == Si)
                    dp[i] += dp[i+1];

                // for num in set d > Si, do nothing/can't add, take care in the next loop
                // which is the next more significant bit
            }
        }

        for (int i = 1; i < K; ++i)
            dp[0] += Math.pow(D.length, i);
        return dp[0];
    }
}


ex. N = 52525, D = {1,2,5}

dp = [	,	,	,	,	,1], K = 5

i = 4: Si = 5, d = 1, dp[4] = 3^0
			   d = 2, dp[4] = 3^0 + 3^0
			   d = 5, dp[4] = 2 * 3^ 0 +dp[5] = 2*3^0 + 1
i = 3: Si = 2, d = 1, dp[3] = 


=======================================================================================================
method 2: Math

Stats:

	Time complexity: O(log10(N))
	Space complexity: O(1)


Method:

Suppose N has k digits, D = {}

 1) for nums less than k digits
	- We can use all the numbers from D to construct numbers of with length 1,2,…,n-1 which are 
	  guaranteed to be less than N.

	e.g. n = 52125, D = [1, 2, 5]

		format X: 		e.g. 1, 2, 5 					 counts = |D| ^ 1
		format XX: 		e.g. 11,12,15,21,22,25,51,52,55, counts = |D|^2
		format XXX:  	counts = |D|^3
		format XXXX: 	counts = |D|^4

		Ans = sum(|D|^i) for 1 <= i < k  	！！ strictly less than k 

2) exact k digits
	- if current digit in N is not in set D, counts =  num * |D|^(n-1), num = number of elements in set 
																			D that are smaller than N[?]

		e.g. N = 34567, D = [1,2,8], here check for N[0]

			1XXXX, 	|3|^4
			2XXXX, 	|3|^4
			8xxxx, --> can not do it, since 8 > 3

			Total = (3^1 + 3^2 + 3^3 + 3^4) + 2 * |3|^ 4 = 120 + 162 = 282

	- if d = N[i], we need to check the next digit…
		e.g. N = 52525, D = [1,2,5],  

		X 			|3|^1
		XX 			|3| ^ 2
		XXX 		|3| ^ 3
		XXXX 		|3| ^ 3
		1XXXX, 		|3|^4
		2XXXX, 		|3|^4
		total = (120) + |3|^4 + |3|^4
		
		5????
			51XXX |3|^3
			52???
				521XX |3|^2
				522XX |3|^2
				525??
					5251X |3|^1
					5252?
						52521 |3|^0
						52522 |3|^0
						52525 +1	--> if every digit of N is from D,have a valid solution, so + 1.
		total += |3|^3 + 2*|3|^2 + |3|^1 + 2 * |3|^0 + 1 = 120 + 213 = 333



class Solution {
	public int atMostNGivenDigitSet(String[] D, int N) {
		char[] s = String.valueOf(N).toCharArray();
		int n = s.length;
		int ans = 0;

		// for length strictly less than N
		for (int i = 1; i < n; ++i)
		  ans += (int)Math.pow(D.length, i);


		for (int i = 0; i < n; ++i) {
			boolean prefix = false;
			for (String d : D) {
				if (d.charAt(0) < s[i]) {
					ans += Math.pow(D.length, n - i - 1);
				} else if (d.charAt(0) == s[i]) {
					prefix = true;
					break;
				}
			}
			
			// if prefix = true, then need to go on to the next digits, meaning the current
			// s[i] is in the set of strings D
			if (!prefix) 
				return ans;
		}
		return ans + 1;
	}
}
--------------------
1. has n digits, so all numbers less than n digits are valid, which are: sum(len(D) ** i for i 
   in range(1, n))
2. The loop is to deal with all numbers with n digits, considering from N[0], N[1] back to N[n-1]. 
   ex. N[0] is valid only for c in D if c <= N[0]. 
   If c < N[0], then N[1], ..., N[n-1] can take any number in D  
   if c == N[0], then we need consider N[1], and the iteration repeats.
   That's why if N[i] not in D, then we don't need to repeat the loop anymore.

3. Finally i==n is addressed at the end when there exists all c in D that matches N


	def atMostNGivenDigitSet(self, D, N):
        def less(c):
            return len([char for char in D if char < c])

        d, cnt, l = len(D), 0, len(str(N))
        # For numbers which have less digits than N, simply len(D) ** digits_length
        for i in range(1, l):
            cnt += d ** i
        """
        We should also consider edge cases where previous digits match with related digits in N. 
        In this case, we can make a number with previous digits + (digits less than N[i]) + D ** remaining length
        If current digit, N[i] not in D, we should break because we cannot continue for further edge cases
        """
        for i, c in enumerate(str(N)):
            cnt += less(c) * (d ** (l - i - 1))
            if c not in D: break
            if i == l - 1: cnt += 1
        return cnt
 
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



