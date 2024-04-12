44. Wildcard Matching - Hard

Given an input string (s) and a pattern (p), implement wildcard pattern matching 
with support for '?' and '*'.

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like ? or *.


Example 2:
Input:
s = "aa"
p = "*"
Output: true
Explanation: '*' matches any sequence.

Example 3:
Input:
s = "cb"
p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.

Example 4:
Input:
s = "adceb"
p = "*a*b"
Output: true
Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".




******************************************************
key:
	- iterative & for loop --> only need 1 for loop
	- loop thru s, but check based on p
	- edge case:
		1) either is empty, return false, if both empty string, ask interviewer (return true)
		2) 
	- general cases:
		1) if (s[i] == p[j]) || (p[j] == '?' || '*') || (p[j-1] == '*') --> return true 
			subcases:
				i) if (s[j] == p[j] || (p[j] == '?') )--> i++
				ii) if (p[i] ==  '*' || (p[j-1] == '*')) --> i++
				iii)

******************************************************



=======================================================================================================
method 1:

method:

	- use two pointers
	- 

stats:

	- Runtime: 2 ms, faster than 100.00% of Java online submissions for Wildcard Matching.
	- Memory Usage: 40 MB, less than 51.16% 
	- 时间复杂度：如果 str 长度是 T，pattern 长度是 P，虽然只有一个 while 循环，但是 s 并不是每次都加 1，
	  	所以最坏的时候时间复杂度会达到 O（TP），例如 str = "bbbbbbbbbb"，pattern = "*bbbb"。每次 pattern 
	  	到最后时，又会重新开始到开头。

	- Time complexity : O(min(S,P)) for the best case and better than O(SlogP) for the average case,
	- 空间复杂度：O（1）。


s = "adceb"
p = "*a*b"

example run @ p[3] = '*'
	staridx = 2, match = 1, p++ = 3
	p = 2+1 = 3, match ++ = 2, s = match = 2 ='c'
	p = 2+1 = 3, match ++ = 3, s = match = 3 ='e'
	p = 2+1 = 3, match ++ = 4, s = match = 4 ='b'
	p[3] = 'b' = s[4] --> s++ = 5, p++ = 4

boolean isMatch(String str, String pattern) {
	int s = 0, 
		p = 0, 
		match = 0, 
		starIdx = -1;

	// let s < str.length(), so will let pointer go to the last char
	while (s < str.length()){

		// advancing both pointers
		if (p < pattern.length() && 
			(pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))){
			s++;
			p++;
		}
		
		// * found, only advancing pattern pointer, use startIdx to record
		// position of *，also note the position of current string, then move p
		else if (p < pattern.length() && pattern.charAt(p) == '*'){
			starIdx = p;
			match = s;
			p++;
		}

		// last pattern pointer was *, advancing string pointer
		// 当前字符不匹配，并且也没有 *，回退, p 回到 * 的下一个位置
        // match 更新到下一个位置, s 回到更新后的 match 
        // 这步代表用 * 匹配了一个字符
		else if (starIdx != -1){
			p = starIdx + 1;
			match++;
			s = match;
		}

		//current pattern pointer is not star, last patter pointer was not *
		//characters do not match
		else return 
			false;
	}

	//go through s, now check for remaining characters in pattern
	//将末尾多余的 * 直接匹配空串 例如 text = ab, pattern = a*******
	while (p < pattern.length() && pattern.charAt(p) == '*')
		p++;

	return p == pattern.length();
}


=======================================================================================================
method 2:

method:

	- dp, which we define the state P[i][j] to be whether 
		s[0..i) matches p[0..j) 
	- The state equations are as follows:
		1. if p[j - 1] != '*'
			dp[i][j] = dp[i - 1][j - 1] && (s[i - 1] == p[j - 1] || p[j - 1]== '?')
		2. if p[j - 1] == '*'
			dp[i][j] = dp[i][j - 1] || dp[i - 1][j]

	- !! build from p.length & s.length to p = 1, s = 1

stats:

	- Runtime: 62 ms, faster than 6.25% of Java online submissions for Wildcard Matching.
	- Memory Usage: 38.5 MB, less than 90.70%
	- 时间复杂度：text 长度是 T，pattern 长度是 P，那么就是 O（TP）。
	- 空间复杂度：O（TP）。

class Solution {
  public boolean isMatch(String s, String p) {
    int sLen = s.length(), pLen = p.length();

    // base cases
    if (p.equals(s) || p.equals("*")) return true;
    if (p.isEmpty() || s.isEmpty()) return false;

    // init all matrix except [0][0] element as False
    boolean[][] d = new boolean[pLen + 1][sLen + 1];
    d[0][0] = true;


    for(int pIdx = 1; pIdx < pLen + 1; pIdx++) {
      
      // the current character in the pattern is '*'
      if (p.charAt(pIdx - 1) == '*') {
        int sIdx = 1;
        
        // d[p_idx - 1][s_idx - 1] is a string-pattern match on the previous step
        // Find the first idx in string with the previous math.
        while ((!d[pIdx - 1][sIdx - 1]) && (sIdx < sLen + 1)) 
        	sIdx++;


        // If (string) matches (pattern), when (string) matches (pattern)* as well
        d[pIdx][sIdx - 1] = d[pIdx - 1][sIdx - 1];
        
        // If (string) matches (pattern),
        // when (string)(whatever_characters) matches (pattern)* as well
        while (sIdx < sLen + 1) 
        	d[pIdx][sIdx++] = true;
      }

      // the current character in the pattern is '?'
      else if (p.charAt(pIdx - 1) == '?') {
        for(int sIdx = 1; sIdx < sLen + 1; sIdx++)
          d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1];
      }

      // the current character in the pattern is not '*' or '?'
      else {
        for(int sIdx = 1; sIdx < sLen + 1; sIdx++) {
          // Match is possible if there is a previous match
          // and current characters are the same
          d[pIdx][sIdx] = d[pIdx - 1][sIdx - 1] &&
                  (p.charAt(pIdx - 1) == s.charAt(sIdx - 1));
        }
      }
    }
    return d[pLen][sLen];
  }
}
=======================================================================================================
method 3: optimize method 2

stats:
	- Runtime: 20 ms, faster than 17.49% of Java online submissions for Wildcard Matching.
	- Memory Usage: 38.1 MB, less than 93.02% o
	- 时间复杂度：text 长度是 T，pattern 长度是 P，那么就是 O（TP）。
	- 空间复杂度：O（P）

	public boolean isMatch(String text, String pattern) {
	    // 多一维的空间，因为求 dp[len - 1][j] 的时候需要知道 dp[len][j] 的情况，
	    // 多一维的话，就可以把 对 dp[len - 1][j] 也写进循环了
	    boolean[][] dp = new boolean[2][pattern.length() + 1];
	    dp[text.length() % 2][pattern.length()] = true;

	    // 从 len 开始减少
	    for (int i = text.length(); i >= 0; i--) {
	        for (int j = pattern.length(); j >= 0; j--) {
	            if (i == text.length() && j == pattern.length())
	                continue;
	            boolean first_match = (i < text.length() && j < pattern.length() && (pattern.charAt(j) == text.charAt(i)
	                                                                                 || pattern.charAt(j) == '?' || pattern.charAt(j) == '*'));
	            if (j < pattern.length() && pattern.charAt(j) == '*') {
	                dp[i % 2][j] = dp[i % 2][j + 1] || first_match && dp[(i + 1) % 2][j];
	            } else {
	                dp[i % 2][j] = first_match && dp[(i + 1) % 2][j + 1];
	            }
	        }
	    }
	    return dp[0][0];
	}













