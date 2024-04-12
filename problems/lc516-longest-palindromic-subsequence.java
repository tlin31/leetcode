516. Longest Palindromic Subsequence


Given a string s, find the longest palindromic subsequence’s length in s. You may assume that the 
maximum length of s is 1000.

Example 1:
Input:

"bbbab"
Output:

4
One possible longest palindromic subsequence is “bbbb”.

Example 2:
Input:

"cbbd"
Output:

2
One possible longest palindromic subsequence is “bb”.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 	DP
	- 	use matrix[i][j] to be the longest palindromic substring start & end at index i and j
	-	Algorithm:
		start from the end of string
		declare matrix[n][n] = 1, because a single char is a palindrome

		formula:
			if s.charAt(i) == s.charAt(j)
				matrix[i][j] = matrix[i+1][j-1] + 2		// check inner string's longest palindrome
			else 
				matrix[i][j] = max of (dp[i + 1][j] or dp[i][j - 1])



stats:

	- Time complexity: O(n^2)
	- Space complexity: O(n^2)

class Solution {
    public int longestPalindromeSubseq(String s) {
        int size = s.length();
        int[][] dp = new int[size][size];

        // i starts from end
        for(int i = size - 1; i >= 0; i--){
            dp[i][i] = 1;
            for(int j = i + 1; j < size; j++){
                if(s.charAt(i) == s.charAt(j)){
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][size - 1];
    }
}





=======================================================================================================
method 2:

method:

	- optimize time
	- 

stats:

	- 
	- 

O(n) space

public int longestPalindromeSubseq(String s) {
	int[] dp = new int[s.length()];
	for (int i = s.length() - 1; i >= 0; i--) {
		dp[i] = 1;
		int pre = 0;
		for (int j = i + 1; j < s.length(); j++) {
			int tmp = dp[j];
			if (s.charAt(i) == s.charAt(j)) {
				dp[j] = pre + 2;
			}
			else {
				dp[j] = Math.max(dp[j], dp[j - 1]);
			}
			pre = tmp;
		}
	}

	return dp[s.length() - 1];
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



