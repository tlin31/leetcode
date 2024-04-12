1143. Longest Common Subsequence - Medium

Given two strings text1 and text2, return the length of their longest common subsequence.

A subsequence of a string is a new string generated from the original string with some characters
(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" is 
a subsequence of "abcde" while "aec" is not). 

A common subsequence of two strings is a subsequence that is common to both strings.

If there is no common subsequence, return 0.

 

Example 1:

Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:

Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:

Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.
 

Constraints:

1 <= text1.length <= 1000
1 <= text2.length <= 1000
The input strings consist of lowercase English characters only.

******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Recursion


Stats:

	- time complexity - O(2^(m+n))
	- space complexity - O(m+n)


Method:
	-	recursive solution(Top- down approach)

	public static int LCSM1(char[] X, char[] Y, int i, int j) {
		if (i <= 0 || j <= 0)
			return 0;
		if (X[i - 1] == Y[j - 1])
			return 1 + LCSM1(X, Y, i - 1, j - 1);
		else
			return Math.max(LCSM1(X, Y, i, j - 1), LCSM1(X, Y, i - 1, j));

	}

-------------------
Stats:

	- time complexity - O(m*n)
	- space complexity - O(m*n)


Method:

	-	Method2()- recursive solution with memoization

	public static int LCSM2(char[] X, char[] Y, int i, int j, Integer[][] dp) {
		if (i <= 0 || j <= 0)
			return 0;

		if (dp[i][j] != null)
			return dp[i][j];

		if (X[i - 1] == Y[j - 1])
			return 1 + LCSM2(X, Y, i - 1, j - 1, dp);
		else
			return dp[i][j] = Math.max(LCSM2(X, Y, i, j - 1, dp), LCSM2(X, Y, i - 1, j, dp));

	}
=======================================================================================================
method 2: DP

Stats:

	- time complexity - O(m*n)
	- space complexity - O(m*n)


Method:

	-  DP solution(Bottom up approach)
	
 	public int longestCommonSubsequence(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i < s1.length(); ++i)
            for (int j = 0; j < s2.length(); ++j)

                if (s1.charAt(i) == s2.charAt(j)) 
                	dp[i + 1][j + 1] = 1 + dp[i][j];

                else 
                	dp[i + 1][j + 1] =  Math.max(dp[i][j + 1], dp[i + 1][j]);

        return dp[s1.length()][s2.length()];
    }

---------------------------------
DP solution(Bottom up approach)

	- time complexity - O(m*n)
	- space complexity - O(n)

	public static int LCSM4(char[] X, char[] Y, int m, int n) {
		int memo[] = new int[n + 1];

		for (int i = 1; i <= m; i++) {
			int prev = 0;
			for (int j = 1; j <= n; j++) {
				int temp = memo[j];
				if (X[i - 1] == Y[j - 1]) {
					memo[j] = prev + 1;
				} else {
					memo[j] = Math.max(memo[j], memo[j - 1]);
				}
				prev = temp;
			}

		}
		return memo[n];
	}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



