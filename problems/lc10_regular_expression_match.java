10. Regular Expression matching----Hard

Given an input string (s) and a pattern (p), implement regular expression matching with 
support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Note:
s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like . or *.

Example 2:
Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:
Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Example 4:
Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".

==============================================================================================================
method 1:recursive

stats:
    - O(N^2)

key:
	- There are 2 cases to consider:
	    - First, the second character of p is *, now p string can match any number of character 
	      before *. if(isMatch(s, p.substring(2)) means we can match the remaining s string, 
	      otherwise, we check if the first character matches or not.
		- Second, if the second character is not *, we need match character one by one.


public boolean isMatch(String s, String p) {
    if (p.length() == 0) {
        return s.length() == 0;
    }

    if (p.length() > 1 && p.charAt(1) == '*') {  // second char is '*'
        
        // if a* reduces to empty string, meaning ignore this * and its prev char
        if (isMatch(s, p.substring(2))) {
            return true;
        }

        // if char equals or p is '.', then move on to the next char
        if(s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
            return isMatch(s.substring(1), p);
        }

        return false;
    } 

    else {                                     

        // second char is not '*'
        if(s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
            return isMatch(s.substring(1), p.substring(1));
        }

        return false;
    }
}

==============================================================================================================
method 2: DP


    -  dp[i][j] denotes if s.substring(0,i) is valid for pattern p.substring(0,j). 
        
        base case:
            dp[0][0] = true  because when s and p are both empty they match. 
            dp[i][0] = false

        result:
            dp[s.length()][p.length()]



public boolean isMatch(String s, String p) {
        if (p == null || p.length() == 0) 
        	return (s == null || s.length() == 0);
        
        boolean dp[][] = new boolean[s.length()+1][p.length()+1];
        
        // base cases, when s and p are both empty
        dp[0][0] = true;

        // s is empty, there's a match when p contains a "*" in the previous position 
        // AND p's previous == empty s
        for (int j=2; j<=p.length(); j++) {

            dp[0][j] = p.charAt(j-1) == '*' && dp[0][j-2]; 
        }
        
        // 2 cases  
        for (int j=1; j<=p.length(); j++) {
            for (int i=1; i<=s.length(); i++) {

                // case 1: current characters match or pattern has '.'' 
                // use charAt(j-1) b/c dp is one index bigger than our string and pattern 
                if (p.charAt(j-1) == s.charAt(i-1) || p.charAt(j-1) == '.') 
					dp[i][j] = dp[i-1][j-1];

                
                else if(p.charAt(j-1) == '*')

                    // case 2.1: * reduces to empty set, so pattern goes back by 2
                    // case 2.2: current char of string equals the char preceding * in pattern
                    dp[i][j] = dp[i][j-2] || 
                              ((s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.') && dp[i-1][j]); 
            }
        }
        return dp[s.length()][p.length()];
    }

==============================================================================================================
method 3:
public boolean isMatch(String s, String p) {

    if (s == null || p == null) {
        return false;
    }

    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
    dp[0][0] = true;

    for (int i = 0; i < p.length(); i++) {
        if (p.charAt(i) == '*' && dp[0][i-1]) {
            dp[0][i+1] = true;
        }
    }

    for (int i = 0 ; i < s.length(); i++) {
        for (int j = 0; j < p.length(); j++) {
            if (p.charAt(j) == '.') {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == s.charAt(i)) {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == '*') {

                
                if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                    dp[i+1][j+1] = dp[i+1][j-1];
                } else {
                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                }
            }
        }
    }
    return dp[s.length()][p.length()];
}















