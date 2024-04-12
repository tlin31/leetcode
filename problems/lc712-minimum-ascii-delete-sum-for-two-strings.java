712. Minimum ASCII Delete Sum for Two Strings - Medium

Given two strings s1, s2, find the lowest ASCII sum of deleted characters to make two strings equal.

Example 1:
Input: s1 = "sea", s2 = "eat"
Output: 231
Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
Deleting "t" from "eat" adds 116 to the sum.
At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.


Example 2:
Input: s1 = "delete", s2 = "leet"
Output: 403
Explanation: Deleting "dee" from "delete" to turn the string into "let",
adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
Note:

0 < s1.length, s2.length <= 1000.
All elements of each string will have an ASCII value in [97, 122].


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

	- Time: O(m*n), M, N are the lengths of the given strings.
	- Space: O(m*n)


Method:

	-	Very Similar to Longest Common Subsequence Problem.
	-	dp[i][j] = minimumDeleteSum( s1[0,i], s2[0,j])

	Base case:
		- When either of the strings is empty, then whole of the other string has to be deleted.
		  answer = sum of ASCII code of the characters of the other string.
		- e.g. if s1 = "", s2 = "abc", then only way we could match these strings by deleting 
		       characters is by dropping 'a','b','c' of s2 to make it empty like s1.

		- Hence the 1st rule: dp[i][j] =
				sum_ascii(s2) -> if i==0
				sum_ascii(s1) -> if j==0

	If last char are same:
		- Of the two strings, if both of their last characters match then certainly the answer comes
		  from skipping those characters.

		- i.e. Answer("zca","bza") = Answer("zc","bz")

		- Hence the 2nd rule: 

			dp[i][j] = dp[i-1][j-1] -> if s1[i] == s2[j]

	If last char are different:
		1) drop s1's last character (ASCII(s1's last) + dp[i-1][j])
		2) drop s2's last character (ASCII(s2's last) + dp[i][j-1])
		3) drop both last characters (ASCII(s1's last) + ASCII(s2's last) + dp[i-1[[j-1])

		Hence the 3rd rule: 
			dp[i][j] = Min ( ASCII(s1's last) + dp[i-1][j], 
							 ASCII(s2's last) + dp[i][j-1],
							 ASCII(s1's last) + ASCII(s2's last) + dp[i-1[[j-1] )


class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Initialization of first row and first col
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.charAt(i - 1);
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + s2.charAt(j - 1);
        }

        // dp
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), 
                                        dp[i][j - 1] + s2.charAt(j - 1));
                }
            }
        }
        return dp[m][n];
    }
}

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



