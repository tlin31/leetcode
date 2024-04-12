1312. Minimum Insertion Steps to Make a String Palindrome - Hard


Given a string s. In one step you can insert any character at any index of the string.

Return the minimum number of steps to make s palindrome.

A Palindrome String is one that reads the same backward as well as forward.

 

Example 1:

Input: s = "zzazz"
Output: 0
Explanation: The string "zzazz" is already palindrome we dont need any insertions.

Example 2:

Input: s = "mbadm"
Output: 2
Explanation: String can be "mbdadbm" or "mdbabdm".

Example 3:

Input: s = "leetcode"
Output: 5
Explanation: Inserting 5 characters the string becomes "leetcodocteel".

Example 4:

Input: s = "g"
Output: 0

Example 5:

Input: s = "no"
Output: 1
 

Constraints:

1 <= s.length <= 500
All characters of s are lower case English letters.



******************************************************
key:
	- DP ??
	- edge case:
		1) s == null or s.length == 0, return 0
		2) s.length == 1, return 0

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N^2)
	- Space O(N^2)



Method:
	- Split the string s into two parts, and we try to make them symmetrical by adding letters.
	- The more common symmetrical subsequence they have, the less letters we need to add.
		Now we change the problem to find the length of longest common sequence.
		This is a typical dynamic problem.

	Step1.
		Initialize dp[n+1][n+1],
		dp[i][j] = the length of longest common sequence between i first letters in s1 and j 
		           first letters in s2.

	Step2.
		Find the the longest common sequence between s1 and s2,
		where s1 = s and s2 = reversed(s)

	Step3.
		return n - dp[n][n]



    public int minInsertions(String s) {
        int n = s.length();
        int[][] dp = new int[n+1][n+1];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j){
            	if (s.charAt(i) == s.charAt(n - 1 - j))
            		dp[i + 1][j + 1] = dp[i][j] + 1;
            	else
            		 dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            }

        return n - dp[n][n];
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
    def minInsertions(self, s: str) -> int:
        def lcs(s: str, r: str) -> int:
            n = len(r)
            dp = [[0] * (n + 1) for _ in range(n + 1)]
            for i, a in enumerate(s):
                for j, b in enumerate(r):
                    dp[i + 1][j + 1] = dp[i][j] + 1 if a == b else max(dp[i][j + 1], dp[i + 1][j])
            return dp[n][n]
        return len(s) - lcs(s, s[::-1])

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

