940. Distinct Subsequences II - Hard


Given a string S, count the number of distinct, non-empty subsequences of S .

Since the result may be large, return the answer modulo 10^9 + 7.

 

Example 1:

Input: "abc"
Output: 7
Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".
Example 2:

Input: "aba"
Output: 6
Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".
Example 3:

Input: "aaa"
Output: 3
Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
 

 

Note:

S contains only lowercase letters.
1 <= S.length <= 2000


******************************************************
key:
	- DP, seq that resulted from putting "b" the last time (ie."b", "ab"`) will get double counted.
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- O(n)
	- O(n)


Method:

	-  count the number of states dp[k] (distinct subsequences) that use letters S[0], S[1], ..., S[k].
	-  ex. S = "abcx", we have dp[k] = dp[k-1] * 2. 
	   b/c for dp[2] which counts ("", "a", "b", "c", "ab", "ac", "bc", "abc"), dp[3] counts all 
	   of those, plus all of those with the x ending, like ("x", "ax", "bx", "cx", "abx", "acx", "bcx",
	   "abcx").

	-  ex. S = "abab"
		dp[0] = 2, as it counts ("", "a")
		dp[1] = 4, as it counts ("", "a", "b", "ab");					   --> add 'b'
		dp[2] = 7 as it counts   ("", "a", "b", "aa", "ab", "ba", "aba");  --> add 'a'
		dp[3] = 12, as it counts ("", "a", "b", "aa", "ab", "ba", "aba",   --> add 'b'
		                                   "bb", "aab","abb", "bab", "abab").

	  We have that dp[3] counts dp[2], plus("b", "aa", "ab", "ba", "aba") with "b" added to it. 
	  Notice that("", "a")are missing from this list, as they get double counted. 
	  
	  !!! seq that resulted from putting "b" the last time (ie."b", "ab"`) will get double counted.

		dp[k] = 2 * dp[k-1] - dp[last[S[k]] - 1]

		ex. dp[3] = 2 * dp[2] - dp[1-1] = 14 - dp[0] = 12

	-  The number of distinct subsequences ending at S[k], is twice the distinct subsequences counted 
	   by dp[k-1] (all of them, plus all of them with S[k] appended), minus the amount we double counted, 
	   which is dp[last[S[k]] - 1].





class Solution {
    public int distinctSubseqII(String S) {
        int MOD = 1_000_000_007;
        int N = S.length();
        int[] dp = new int[N+1];
        dp[0] = 1;	// assume we add "" at the begining

        int[] last = new int[26];		// array store the last seen index of this character
        Arrays.fill(last, -1);

        for (int i = 0; i < N; ++i) {
            int x = S.charAt(i) - 'a';
            dp[i+1] = dp[i] * 2 % MOD;

            if (last[x] >= 0)
                dp[i+1] -= dp[last[x]];

            dp[i+1] %= MOD;

            last[x] = i;
        }

        dp[N]--;	// delete ""

        if (dp[N] < 0) 
        	dp[N] += MOD;
        
        return dp[N];
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution(object):
    def distinctSubseqII(self, S):
        dp = [1]
        last = {}
        for i, x in enumerate(S):
            dp.append(dp[-1] * 2)
            if x in last:
                dp[-1] -= dp[last[x]]
            last[x] = i

        return (dp[-1] - 1) % (10**9 + 7)
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


Furthermore, we can use a sum to represent sum(dp[0], ..., dp[i - 1]).
And also a count array, in which count[S.charAt(i) - 'a'] represents the count of presented subsequence ends with S.charAt(i).
Then dp[i] = sum - count[S.charAt(i) - 'a'].
Time complexity: O(n)

class Solution {
    public int distinctSubseqII(String S) {
        int n = S.length(), M = (int)1e9 + 7;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int[] count = new int[26];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int index = S.charAt(i) - 'a';
            dp[i] += sum - count[index];
            dp[i] = (dp[i] + M) % M;
            sum = (sum + dp[i]) % M;
            count[index] = (count[index] + dp[i]) % M;
        }
        return sum;
    }
}







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

