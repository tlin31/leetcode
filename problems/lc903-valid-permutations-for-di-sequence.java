903. Valid Permutations for DI Sequence - Hard

We are given S, a length n string of characters from the set {'D', 'I'}. (These letters stand for
"decreasing" and "increasing".)

A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1,.. n}, such that for all i:

	If S[i] == 'D', then P[i] > P[i+1]
	If S[i] == 'I', then P[i] < P[i+1].


How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.

 

Example 1:

Input: "DID"
Output: 5
Explanation: 
The 5 valid permutations of (0, 1, 2, 3) are:
(1, 0, 3, 2)
(2, 0, 3, 1)
(2, 1, 3, 0)
(3, 0, 2, 1)
(3, 1, 2, 0)
 

Note:

1 <= S.length <= 200
S consists only of characters from the set {'D', 'I'}.


******************************************************
key:
	- DP, need to keep the order of the newly append sequence
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
method 1: Another DP (Lee)

Stats:

	- Time O(N^2)
	- Space O(N^2)


Method:
	
	-	dp[i][j] means the number of possible permutations of first i + 1 digits,
		where the i + 1-th digit is j + 1th smallest in the rest of unused digits.
		(i = position of the newest append digit, j = order of the newest append digit)

ex. "DID"

dp[0][3] = 1	(4)
dp[0][2] = 1	(3)		dp[1][2] = 1	(43)										dp[3][0] = 5
dp[0][3] = 1	(2)		dp[1][1] = 2	(42,32)			dp[2][1] = 5 (413,314,214,423,324)
dp[0][0] = 1	(1)		dp[1][0] = 3	(41,31,21)		dp[2][0] = 3 (412,312,213)


	- Base case:
		permutation can start from 1, 2, 3, 4 --> dp[0][0] = dp[0][1] = dp[0][2] = dp[0][3] = 1.

	- DP:
		s.charAt(i - 1) == 'I': 
				dp[i][j] = dp[i-1][n] + dp[i-1][n-1] + ... + dp[i-1][j]

				ex.dp[2][1] = dp[1][2] + dp[1][1]

		s.charAt(i - 1) == 'D': 
				dp[i][j] = dp[i - 1][j+1] + dp[i - 1][j + 2] + ... + dp[i - 1][n]
				
				ex. dp[1][0] = dp[0][1] + dp[0][2] + dp[0][3]

		dp[2][1] = 5, mean the # of permutations where the third digitis the second smallest of the rest.
		We have 413,314,214,423,324.
		Fow example 413, where 2,3 are left and 3 the second smallest of them.


	- for "I", we calculate prefix sum of the array,
	  for "D", we calculate sufixsum of the array.


class Solution {
        public int numPermsDISequence(String S) {
        int n = S.length(), 
            mod = (int)1e9 + 7;
            
        int[][] dp = new int[n + 1][n + 1];

        // initialize, when there's only 1 digit, then doesn't matter the order of this digit
        for (int j = 0; j <= n; j++) 
        	dp[0][j] = 1;


        for (int i = 0; i < n; i++){
        	int cur = 0;

            if (S.charAt(i) == 'I'){
                for (int j = 0; j < n - i; j++) {
                    cur = (cur + dp[i][j]) % mod;
                    dp[i + 1][j] = cur;
                }
            } 

            else
                for (int j = n - i - 1; j >= 0; j--) {
                    cur = (cur + dp[i][j+1]) % mod;
                    dp[i + 1][j] = cur;

                }
        }

        return dp[n][0];
    }

}


ex. DID:
[1, 1, 1, 1]
[3, 2, 1, 0]
[3, 5, 0, 0]
[5, 0, 0, 0]

n = 3

i = 0, 
j = 2, 0 + dp[0][3] = 1
j = 1, 0 + dp[0][3] + dp[0][2] = 2
j = 0, 0 + dp[0][3] + dp[0][2] + dp[0][1]

dp[i+i][j] = dp[i][j] + dp[i+1][j+1] + .... + dp[i][0]

-----------------------------------------------------------------------
Solution 2:
Now as we did for every DP, make it 1D dp.
Time O(N^2)
Space O(N)

  public int numPermsDISequence(String S) {
        int n = S.length(), mod = (int)1e9 + 7;
        int[] dp = new int[n + 1], dp2 = new int[n];;
        for (int j = 0; j <= n; j++) dp[j] = 1;
        for (int i = 0; i < n; i++) {
            if (S.charAt(i) == 'I')
                for (int j = 0, cur = 0; j < n - i; j++)
                    dp2[j] = cur = (cur + dp[j]) % mod;
            else
                for (int j = n - i - 1, cur = 0; j >= 0; j--)
                    dp2[j] = cur = (cur + dp[j + 1]) % mod;
            dp = Arrays.copyOf(dp2, n);
        }
        return dp[0];
    }



=======================================================================================================
Method 2: DP


Stats:

	- N^3
	- 
	
Method:
	- Let dp[i][j] = number of permutation of number 0, 1, ... , i which ends with j. 
	- Also, it represents the answer of s.substring(0, i) which ends with j.
	- when we append j to the array, in order to avoid duplicate, all the elements that are larger 
	  or equal to j in the previous array will automatically increment by one...

	Cases:
		s.charAt(i - 1) == 'I': 
				dp[i][j] = sum(dp[i - 1][0], dp[i - 1][1], ... , dp[i - 1][j - 1]).

		s.charAt(i - 1) == 'D': 
				dp[i][j] = sum(dp[i - 1][j], dp[i - 1][j + 1], ... , dp[i - 1][i - 1]).

	
	Imagine each time when appending the j to the previous permutations, you have to add 1 to each 
	number in the previous permutation which is greater than or equals to j. 

	In this way, we keep the orders and counts of previous permutations and cumulate.

eg. 
	We already have permutation (1, 0, 3, 2). We are trying to append 2. Now the (1, 0, 3, 2) changes 
	to (1, 0, 4, 3) then appended with a 2. We have (1, 0, 4, 3, 2). 
	Although the values change but the order and count don't change.


class Solution {
    public int numPermsDISequence(String S) {
        int n = S.length(), M = (int)1e9 + 7;
        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (S.charAt(i - 1) == 'D') {
                    for (int k = j; k < i; k++) {
                        dp[i][j] += dp[i - 1][k];
                        dp[i][j] %= M;
                    }
                } else {
                    for (int k = 0; k < j; k++) {
                        dp[i][j] += dp[i - 1][k];
                        dp[i][j] %= M;
                    }
                }
            }
        }
        int result = 0;
        for (int j = 0; j <= n; j++) {
            result += dp[n][j];
            result %= M;
        }
        return result;
    }
}


ex.

DID

[1, 0, 0, 0]
[1, 0, 0, 0]
[0, 1, 1, 0]
[2, 2, 1, 0]

------------------------------------------------------

N^2 DP:

Notice that in the previous method, we are actually calculate the prefix sum and suffix sum in the two conditions:

s.charAt(i - 1) == 'I': In this case, dp[i][j] = sum[i - 1][j - 1].
s.charAt(i - 1) == 'D': In this case, dp[i][j] = sum[i - 1][i - 1] - sum[i - 1][j - 1].
We can define dp[i][j] as sum(dp[i][0], dp[i][1], ... dp[i][j]) which is sum[i][j].

Time complexity: O(n^2)

Code:

class Solution {
    public int numPermsDISequence(String S) {
        int n = S.length(), M = (int)1e9 + 7;
        int[][] dp = new int[n + 1][n + 1];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = j == 0 ? 0 : dp[i][j - 1];
                if (S.charAt(i - 1) == 'D') {
                    dp[i][j] += (dp[i - 1][i - 1] - (j == 0 ? 0 : dp[i - 1][j - 1])) % M;
                    if (dp[i][j] < 0) {
                        dp[i][j] += M;
                    }
                } else {
                    dp[i][j] += j == 0 ? 0 : dp[i - 1][j - 1];
                }
                dp[i][j] %= M;
            }
        }
        return dp[n][n];
    }
}



O(N) space:

class Solution {
    public int numPermsDISequence(String S) {
        int n = S.length(), M = (int)1e9 + 7;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1);
        for (int i = 1; i <= n; i++) {
            int[] temp = new int[n + 1];
            for (int j = 0; j <= i; j++) {
                temp[j] = j == 0 ? 0 : temp[j - 1];
                if (S.charAt(i - 1) == 'D') {
                    temp[j] += (dp[i - 1] - (j == 0 ? 0 : dp[j - 1])) % M;
                    if (temp[j] < 0) {
                        temp[j] += M;
                    }
                } else {
                    temp[j] += j == 0 ? 0 : dp[j - 1];
                }
                temp[j] %= M;
            }
            dp = temp;
        }
        return dp[n];
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



