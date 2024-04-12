647. Palindromic Substrings - Medium

Given a string, your task is to count how many palindromic substrings in this string.

The substrings with different start indexes or end indexes are counted as different substrings 
even they consist of same characters.

Example 1:

Input: "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".
 

Example 2:
Input: "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 

Note:

The input string length wont exceed 1000.


******************************************************
key:
	- DP or backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Idea is start from each index and try to extend palindrome for both odd and even length.
	-	

Stats:

	- 
	- 


class Solution {
    public int countSubstrings(String s) {
        int count=0;
        for(int i=0;i < s.length();i++){
            count += extractPalindrome(s,i,i);		//odd length
            count += extractPalindrome(s,i,i+1);	//even length
        }
        return count;
    }

    private int extractPalindrome(String s, int left, int right){
        int count = 0;
        while(left >= 0 && right < s.length() && (s.charAt(left)==s.charAt(right))){
            left--;
            right++;

            count++;
        }
        return count;
    }
}


=======================================================================================================
method 2: recursion

Method:

	-	instead of storing the longest, just get the count of palindromic substrings.

	-	recursion on (i ,i+1, ..........j-1,j)

		- is a palindrome only if (i+1, ..........j-1) is a palindrome and a[i] == a[j]
		- For 1 element and 2 element cases j-i+1 < 3, its enough to check just for a[i] == a[j] 
		  where i == j for 1 element and j == i+1 for 2 elements

		- The outer for loop has to start from the end because the recursion is looking forward, 
			referring to i+1.

		- The inner forward can run normally, but since we are only interested in one half of 
			the diagonal, we start at i, everytime.



Stats:

	- 
	- 



public int countSubstrings(String s) {
    int n = s.length();
    int res = 0;
    boolean[][] dp = new boolean[n][n];

    // go from the back since we are checking from both ends to inwards
    for (int i = n - 1; i >= 0; i--) {

    	// run diagonally
        for (int j = i; j < n; j++) {
        	boolean sameChar = s.charAt(i) == s.charAt(j);
        	boolean shortLengthCase = (j - i < 2 || dp[i + 1][j - 1]);
            dp[i][j] = sameChar && shortLengthCase;
            if(dp[i][j]) 
            	++res;
        }
    }
    return res;
}


=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



