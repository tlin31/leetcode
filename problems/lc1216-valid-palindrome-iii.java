1216. Valid Palindrome III - Hard

Given a string s and an integer k, find out if the given string is a K-Palindrome or not.

A string is K-Palindrome if it can be transformed into a palindrome by removing at most k 
characters from it.

 
Example 1:

Input: s = "abcdeca", k = 2
Output: true
Explanation: Remove 'b' and 'e' characters.
 

Constraints:

1 <= s.length <= 1000
s has only lowercase English letters.
1 <= k <= s.length


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-  The idea is to find the longest palindromic subsequence(lps) of the given string.
		|lps - original string| <= k --> then the string is k-palindrome.

	Eg:
		One of the lps of string pqrstrp is prsrp.
		Characters not contributing to lps of the string should be removed in order to make the 
		  string palindrome. So on removing q and s (or t) from pqrstrp,
		string will transform into a palindrome.


	public boolean isValidPalindrome(String s, int k) {
	    int n = s.length();
	    int[][] dp = new int[n + 1][n + 1];
	    for (int length = 0; length < n; length++) {

	    	// i = start index, j = end index
	        for (int i = 1; i <= n - length; i++) {
	            int j = i + length;

	            // all length == 0, which is a single char in string, it is a valid palindrome
	            if (i == j) {
	                dp[i][j] = 1;
	            } else if (s.charAt(i - 1) == s.charAt(j - 1)) {
	                dp[i][j] = dp[i + 1][j - 1] + 2;
	            } else {
	                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
	            }
	        }
	    }
	    return n - dp[1][n] <= k;
	}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution:
    def isValidPalindrome(self, s: str, k: int) -> bool:
        n = len(s)
        dp = [[0] * (n + 1) for _ in range(n + 1)] 
        for i in range(n + 1): 
            for j in range(n + 1): 
                if not i or not j: 
                    dp[i][j] = i or j 
                elif s[i - 1] == s[n - j]: 
                    dp[i][j] = dp[i - 1][j - 1] 
                else: 
                    dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1])

        return dp[n][n] <= k * 2

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


dp[i][j]: the longest palindromic subsequence's length of substring(i, j), where i, j are left, right indices of the string.
State transition:
if s.charAt(i) == s.charAt(j):
dp[i][j] = dp[i+1][j-1] + 2
otherwise,
dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]).
dp[i][i] Initialized to 1.

    public boolean isValidPalindrome(String s, int k) {
        int[][] dp = new int[s.length()][s.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < s.length(); ++j) {
                if (s.charAt(i) == s.charAt(j)) { dp[i][j] = dp[i + 1][j - 1] + 2; } 
                else { dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]); }
            }
        }
        return s.length() <= k + dp[0][s.length() - 1];
    }
Analysis:
Time & space: O(n ^ 2), where n = s.length().







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

