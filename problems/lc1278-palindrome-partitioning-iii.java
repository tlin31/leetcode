1278. Palindrome Partitioning III - Hard


You are given a string s containing lowercase letters and an integer k. You need to :

	First, change some characters of s to other lowercase English letters.
	Then divide s into k non-empty disjoint substrings such that each substring is palindrome.

Return the minimal number of characters that you need to change to divide the string.

 

Example 1:

Input: s = "abc", k = 2
Output: 1
Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.

Example 2:

Input: s = "aabbc", k = 3
Output: 0
Explanation: You can split the string into "aa", "bb" and "c", all of them are palindrome.


Example 3:

Input: s = "leetcode", k = 8
Output: 0
 

Constraints:

1 <= k <= s.length <= 100.
s only contains lowercase English letters.


******************************************************
key:
	- DP, break down into 3 problems
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
	

- If we break the question apart we get:

	How many changes do I need to perform to make S.substring(i, j) to a palindrome.
	How many ways can I split the string from i to j into groups and what are their costs.
	What is the minimum combination of these groups.

- First thing you should make is a map that contains all the cost from i to j
	So now if i want to know how many substitution I need to make s.substring(i,j) into an palindrome 
	then I can just do toPal[i][j]

		...
        int[][] toPal = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            toPal[i][i] = 0;
        }

        for (int i = s.length()-1; i >= 0; i--) {
            for (int j = i + 1; j < s.length(); j++) {
                toPal[i][j] = getChanges(s, i, j);
            }
        }
		...
	}

    private int getChanges(String s, int start, int end) {
        int changes = 0;
        while(start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                changes++;
            }
        }
        return changes;
    }


- Second I want to define my dp problem through some sort of an equation.
	Given: aabbcc, k = 3
	The possible groups can be

	(aabb)(c)(c), (aab)(bc)(c), (aa)(bbc)(c), (a)(abbc)(c) -(third group is (c))
	(aab)(b)(cc), (aa)(bb)(cc), (aa)(bb)(cc) --------------(third group is (cc))
	(aa)(b)(bcc), (a)(ab)(bcc) ---------------------------(third group is (bcc))
	(a)(a)(bbcc) ----------------------------------------(third group is (bbcc))

- Third, notice that there is 4 different ways have third group be just (c). 3 ways for (cc). 
  2 ways for (bcc). and 1 way for (bbcc).

	dp[k][end] = cost to have k splits palindrome from string [1~end]

	min((aabb)(c), (aab)(bc), (aa)(bbc), (a)(abbc)) = dp[2][4]
	or dp[2][4] = Math.min(toPal[0][3] + toPal[4][4], toPal[0][2] + toPal[3][4], toPal[0][1] + toPal[2][4], toPal[0][0] + toPal[1][4])

	min((aab)(b), (aa)(bb), (aa)(bb)) = dp[2][3]
	or dp[2][3] = Math.min(toPal[0][2] + toPal[3][3], toPal[0][1] + toPal[2][3], toPal[0][0] + toPal[1][3])

	min((aa)(b), (a)(ab)) = dp[2][2]
	or dp[2][2] = Math.min(toPal[0][1] + toPal[2][2], toPal[0][0] + toPal[1][2])

	min((a)(a)) = dp[2][1]
	or dp[2][1] = toPal[0][0] + toPal[1][1]



So:

	dp[3][5] // this our answer because we want 3rd group to end at index 5
	dp[3][5] = Math.min(dp[2][4] + toPal[5][5], dp[2][3] + toPal[4][5], dp[2][2] + toPal[3][5], 
	           dp[2][1] + toPal[2][5])
	         --> dp[2][4] + toPal[5][5]: have 2 paind in str[0~4] + cost to make the substr(5) a palind
	                                     
	         --> dp[2][1] + toPal[2][5]: have 2 palind in str[0~1] + cost to make substr(2~5) a palindrome

Optimize:
    can remove the getChanges() method, by using:
            toPal[i][j] = s.charAt(i) == s.charAt(j) ? toPal[i+1][j-1] : toPal[i+1][j-1] + 1;


class Solution {
    public int palindromePartition(String s, int k) {

    	// form cost matrix of finding toPal[i][j] = cost to make substr(i~j) a palindrome
        int[][] toPal = new int[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            toPal[i][i] = 0;
        }
        for (int i = s.length()-1; i >= 0; i--) {
            for (int j = i + 1; j < s.length(); j++) {
                toPal[i][j] = getChanges(s, i, j);
            }
        }


        // store the cost to have k palindromes in the str[0~index]
        int[][] dp = new int[k+1][s.length()];

        // base case: when we only need 1 palindrome from str(0~i), the cost is exactly
        // toPal from 0 to i
        for (int i = 0; i < s.length(); i++) {
            dp[1][i] = toPal[0][i];
        }

        // i = number of palindromes, goes to k
        for (int i = 2; i <= k; i++) {

        	// go from the back
            for (int end = i-1; end < s.length(); end++) {
                int min = s.length();

                // start goes from end-1 to 0, calculate all combinations
                for (int start = end-1; start >= 0; start--) {
                    min = Math.min(min, dp[i-1][start] + toPal[start+1][end]);
                }
                dp[i][end] = min;
            }
        }
        return dp[k][s.length()-1];
    }
    
    
    private int getChanges(String s, int start, int end) {
        int changes = 0;
        while(start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                changes++;
            }
        }
        return changes;
    }
}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution:
    def palindromePartition(self, s: str, k: int) -> int:
        n = len(s)
        memo = {}
        
        def cost(s,i,j): #calculate the cost of transferring one substring into palindrome string
            r = 0
            while i < j:
                if s[i] != s[j]:
                    r += 1
                i += 1
                j -= 1
            return r
        
        def dfs(i, k):
            if (i, k) in memo: return memo[(i, k)] #case already in memo
            if n - i == k: #base case that each substring just have one character
                return 0
            if k == 1:    #base case that need to transfer whole substring into palidrome
                return cost(s, i, n - 1)
            res = float('inf')
            for j in range(i + 1, n - k + 2): # keep making next part of substring into palidrome
                res = min(res, dfs(j, k - 1) + cost(s,i, j - 1)) #compare different divisions to get the minimum cost
            memo[(i, k)] = res
            return res
        return dfs(0 , k)
# I really appreciate it if u vote up! （￣︶￣）↗


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

