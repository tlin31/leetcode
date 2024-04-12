1092. Shortest Common Supersequence - Hard


Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.
If multiple answers exist, you may return any of them.

(A string S is a subsequence of string T if deleting some number of characters from T (possibly 0, and 
the characters are chosen anywhere from T) results in the string S.)

Note: can be longer, but need to preserve order





Example 1:

Input: str1 = "abac", str2 = "cab"
Output: "cabac"

Explanation: 
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.
 

Note:

1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.



******************************************************
key:
	- DP, longest common subsequence
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1092-shortest-common-supersequence/

Stats:

	TIme O(MN) dp,
	Space O(MN) 


Method:
	- use longest common subsequence, find such LCS and fill in unmatch char in certain order.
	- we loop from the back!

	- dp[i][j] = the longest common subseq in A[0...i-1] and B[0.... j -1] 
		case 1: i = 0, c = B[--j]  --> when string A is done, fill the rest with string B
		case 2: j = 0, c = A[--i]

		case3: str1[i - 1] == str2[j - 1] --> move to the subproblem of dp[i-2][j-2]
					c = A[--i]
					c = B[--j]

		case 4: dp[i - 1][j] == dp[i][j]  --> A[i-1] is not in LCS, so it is not matched, need to add it
		    	c = A[--i]

		case 5: dp[i][j - 1] == dp[i][j]  --> B[j-1] is not in LCS
		    	c = B[--j]

class Solution {
	public String shortestCommonSupersequence(String s1, String s2) {
	    int l1 = s1.length();
	    int l2 = s2.length(); 
	    char[] str1 = s1.toCharArray();
	    char[] str2 = s2.toCharArray();
	    int[][] dp = new int[l1 + 1][l2 + 1];

	    // build LCS
	    for (int i = 1; i <= l1; ++i)
	    	for (int j = 1; j <= l2; ++j){
	    		if (str1[i - 1] == str2[j - 1])
	    			dp[i][j] = 1 + dp[i - 1][j - 1];
	    		else
	    			dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
	   		}
	       

	    StringBuilder ans = new StringBuilder();

	    while ( l1>0 || l2>0) {
		    char c = '\0';

		    // one of the string is exhausted, just add the rest of the other string
		    if (l1 == 0) 
		    	c = str2[--l2];
		    else if (l2 == 0) 
		    	c = str1[--l1];

		    // find a match, insert this match char
		    else if (str1[l1 - 1] == str2[l2 - 1]) 
		    	c = str1[--l1] = str2[--l2];

		    //doesn't have the match, find the 'extra' char not in LCS
		    else if (dp[l1 - 1][l2] == dp[l1][l2]) 
		    	c = str1[--l1];
		    else if (dp[l1][l2 - 1] == dp[l1][l2]) 
		    	c = str2[--l2];

		    ans.insert(0, c);

	    }  

	    return ans.toString();
    }

}




=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	Change the problem to find the LCS
	-	DP Recurrence:

		Let str1[0..m - 1] and str2[0..n - 1] be two strings with lengths m and n .

		if (m == 0) return n;
		if (n == 0) return m;

		Case 1: If last characters are same, then add 1 to result and recur for str1[]
			
			if (str1.charAt(m - 1) == str2.charAt(n - 1))
				return 1 + shortestCommonSupersequence(str1, str2, m - 1, n - 1);

		Case 2: Else find shortest of following two
			2.a) Remove last character from str1 and recur
			2.b) Remove last character from str2 and recur
		
			return 1 + min( shortestCommonSupersequence(str1, str2, m - 1, n), 
							shortestCommonSupersequence(str1, str2, m, n - 1));

public String shortestCommonSupersequence(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++){
            for (int j = 0; j <= n; j++){
                if (i == 0)
                    dp[i][j] = j;
                else if (j == 0)
                    dp[i][j] = i;
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j],dp[i][j - 1]);
            }
        }

        int l = dp[m][n]; // Length of the ShortestSuperSequence
        char[] arr = new char[l];
        int i=m, j=n;

        while(i>0 && j>0){
            //	If current character in str1 and str2 are same, then current character is part 
            //	of shortest supersequence 
            if(str1.charAt(i-1) == str2.charAt(j-1)) {
                arr[--l] = str1.charAt(i-1);
                i--;
                j--;
            }

            else if(dp[i-1][j] < dp[i][j-1]) {
                arr[--l] = str1.charAt(i-1);
                i--;
            }
            else {
                arr[--l] = str2.charAt(j-1);
                j--;
            }
        }

        // append rest of str1 and str2 in the front
        while (i > 0) {
            arr[--l] = str1.charAt(i-1);
            i--;
        }
        while (j > 0) {
            arr[--l] = str2.charAt(j-1);
            j--;
        }
        return new String(arr);
    }




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
 def shortestCommonSupersequence(self, A, B):
        def lcs(A, B):
            n, m = len(A), len(B)
            dp = [["" for _ in xrange(m + 1)] for _ in range(n + 1)]
            for i in range(n):
                for j in range(m):
                    if A[i] == B[j]:
                        dp[i + 1][j + 1] = dp[i][j] + A[i]
                    else:
                        dp[i + 1][j + 1] = max(dp[i + 1][j], dp[i][j + 1], key=len)
            return dp[-1][-1]

        res, i, j = "", 0, 0
        for c in lcs(A, B):
            while A[i] != c:
                res += A[i]
                i += 1
            while B[j] != c:
                res += B[j]
                j += 1
            res += c
            i, j = i + 1, j + 1
        return res + A[i:] + B[j:]

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

