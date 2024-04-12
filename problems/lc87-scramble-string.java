87. Scramble String - Hard

Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty 
substrings recursively.

Below is one possible representation of s1 = "great":

    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t

To scramble the string, we may choose any non-leaf node and swap its two children.

For example, if we choose the node "gr" and swap its two children, it produces a scrambled 
string "rgeat".

    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
We say that "rgeat" is a scrambled string of "great".

Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".

    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
We say that "rgtae" is a scrambled string of "great".

Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.

Example 1:
Input: s1 = "great", s2 = "rgeat"
Output: true

Example 2:
Input: s1 = "abcde", s2 = "caebd"
Output: false



******************************************************
key:
  - need to loop through every possibility --> recursion
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
Method 1:

Method:

  - If there exist 0 <= i <= s1.length() where
      - s1[0,i] can scramble into s2[0,i] && s1[i,length] can scramble into s2[i, length]; 
      - OR: s1[0,i] can scramble into s2[length - i, length] && s1[i,length] can scramble 
            into s2[0, length - i]
  - 

Stats:

  - I think the time-complexity is O(n!) --> T(n) = (n-1)*T(n-1) + n .... O(n!)
  - 


public class Solution {
    public boolean isScramble(String s1, String s2) {
        if (s1.equals(s2)) return true; 
        
        int[] letters = new int[26];
        for (int i=0; i<s1.length(); i++) {
            letters[s1.charAt(i)-'a']++;
            letters[s2.charAt(i)-'a']--;
        }

        // false if number & name of char doesn't match for s1 and s2
        for (int i=0; i<26; i++) 
          if (letters[i]!=0) 
            return false;
    
        for (int i=1; i<s1.length(); i++) {
            // not switched at this level
            if (isScramble(s1.substring(0,i), s2.substring(0,i)) 
                    && isScramble(s1.substring(i), s2.substring(i))) 
              return true;

            // left and right subtree is switched
            if (isScramble(s1.substring(0,i), s2.substring(s2.length()-i)) 
                    && isScramble(s1.substring(i), s2.substring(0,s2.length()-i))) 
              return true;
        }
        return false;
    }
}

=======================================================================================================
method 2: DP

Method:

  - dp[i][j][1] indiates whether s1(i) equals to s2(j) and 3rd dimension represents length.
    dp[i][j][k] indicates whether s1(i, i+k) can be changed from s2(j, j+k).

  - if dp[i][j][l] && dp[i+l][j+l][k-l] are true, dp[i][j][k] is true. 
      You can understand as which s1(i, i+l) and s2(j, j+l) is scramble and s1(i+l, i+k) and s2(j+l, j+k) is scramble, so s1(i, i+k) and s2(j, j+k) is scramble.
  - There is same argument. if dp[i][j+k-l][l] and dp[i+l][j][k-l] are true, dp[i][j][k] is true.



Stats:

  - 
  - 

public class Solution {
  public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        int m = s1.length();
        int n = s2.length();
        if (m != n) return false;

        boolean[][][] dp = new boolean[m][m][m+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }

        for (int k = 2; k <= m; k++) {
            for (int i = 0; i <= m - k; i++) {
                for (int j = 0; j <= m - k; j++) {
                    dp[i][j][k] = false;
                    for (int part = 1; part < k; part++) {
                        if ((dp[i][j][l] && dp[i+l][j+l][k-l])
                                || (dp[i][j+k-l][l] && dp[i+l][j][k-l])) {
                            dp[i][j][k] = true;
                        }
                    }
                }
            }
        }
        return dp[0][0][s1.length()];
    }
}

=======================================================================================================
method 3:

Method:

  - 
  - 


Stats:

  - 
  - 



