5. Longest Palindromic Substring - Medium

Given a string s, find the longest palindromic substring in s. You may assume that the maximum length 
of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.


Example 2:
Input: "cbbd"
Output: "bb"

******************************************************
key:
    - DP, store results, go from end of the string for (i = n, i--), then for (j = i, j<n, j++)
    - sliding window / 2 pointers
    - edge case:
        1) empty string, return empty
        2)

******************************************************


=========================================================================================================================
method 1:

    - dp(i, j) represents whether s(i ... j) can form a palindromic substring, 

        Formula:
            dp(i, j) is true if s(i) equals to s(j) && s(i+1 ... j-1) is a palindromic substring.

            Note: j-i < 3 meaning there is less than 1 char in between i and j 

    - When we found a palindrome, check if it is the longest one.

Time:
    - O(n^2)


    public String longestPalindrome(String s) {
      int n = s.length();
      String res = null;
        
      boolean[][] dp = new boolean[n][n];
        
      for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
          dp[i][j] = (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]));
        
          // update res
          if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
            res = s.substring(i, j + 1);
          }
        }
      }
        
      return res;
    }

babad
01234

i = 4, j = 4, dp[5][3]
i = 3, j = 3, 4

i = 1, j = 1, 2, 3, 4 --> for dp[1][3] = dp[2][2]

=========================================================================================================================

method 2:

    - 2 pointers

    public String longestPalindrome(String s) {
        char[] strArray = s.toCharArray();
        int start = 0, end = 0;
        int max = 0;

        for(int i = 0; i < strArray.length; i++) {

            // odd length
            if(isPalindrome(strArray, i - max - 1, i)) {
                start = i - max - 1; 
                end = i;
                max += 2;
            } 

            // right side is palindrome            
            else if(isPalindrome(strArray, i - max, i)) {
                start = i - max; 
                end = i;
                max += 1;
            }
        }
        return s.substring(start, end + 1);
    }

    private boolean isPalindrome(char[] strArray, int s, int e) {
        if(s < 0) 
            return false;
        
        while(s < e) {
            if(strArray[s++] != strArray[e--]) 
                return false;
        }

        // takes care of the case s == e
        return true;
    }



Manacher’s Algorithm – Linear Time Longest Palindromic Substring
- point: linear time solution
- part 1: https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
- part 2: https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-2/



=========================================================================================================================
method 3:
    - extend palindrome



class solution {
    private int lo, maxLen;

    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2)
            return s;
        
        for (int i = 0; i < len-1; i++) {
            extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
            extendPalindrome(s, i, i+1); //assume even length.
        }
        return s.substring(lo, lo + maxLen);
    }

    private void extendPalindrome(String s, int j, int k) {
        while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
            j--;
            k++;
        }
        if (maxLen < k - j - 1) {
            lo = j + 1;
            maxLen = k - j - 1;
        }
    }

}









