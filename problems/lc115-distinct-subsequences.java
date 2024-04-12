115. Distinct Subsequences - Hard

Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting 
some (can be none) of the characters without disturbing the relative positions of the remaining
characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

Example 1:

Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:

As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
rabbbit
^^^^ ^^


Example 2:
Input: S = "babgbag", T = "bag"
Output: 5
Explanation:

As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:
	- we can build this array rows-by-rows:
		1) the first row must be filled with 1：
			b/c the empty string is a subsequence of any string but only 1 time. 
			mem[0][j] = 1 for every j. 
		2) the first column of every rows except the first must be 0. 
			b/c an empty string cannot contain a non-empty string as a substring -- the very 
			first item of the array: mem[0][0] = 1, because an empty string contains the empty 
			string 1 time.
		

			  S 0123....j
			T +----------+
			  |1111111111|
			0 |0         |
			1 |0         |
			2 |0         |
			. |0         |
			. |0         |
			i |0         |


	
	An example:
	S: [acdabefbc] and T: [ab]

	first we check with a:

	           *  *
	      S = [acdabefbc]
	mem[1] = [0111222222]
	then we check with ab:

	               *  * 
	      S = [acdabefbc]
	mem[1] = [0111222222]
	mem[2] = [0000022244]
	And the result is 4, as the distinct subsequences are:

	      S = [a   b    ]
	      S = [a      b ]
	      S = [   ab    ]
	      S = [   a   b ]

	- State transition: 
		s[i-1] != t[j-1] --> dp[i][j] = dp[i-1][j]
		s[i-1] == t[j-1] --> dp[i][j] = dp[i-1][j] + dp[i-1][j-1]
		dp[i][j] represents the number of distinct subsequences for s[0, i-1] and t[0, t-1];

	- We first keep in mind that s is the longer string [0, i-1], t is the shorter substring [0, j-1]. 
	- We can assume t is fixed, and s is increasing in character one by one (this is the key point).

		For example:
		t : ab--> ab --> ab --> ab
		s: a --> ac --> acb --> acbb

	1) When the new character in s, s[i-1] != the head char in t, t[j-1], we can 
	   no longer increment the number of distinct subsequences, it is the same as the situation 
	   before incrementing the s, so dp[i][j] = dp[i-1][j].

	2) when the new incrementing character in s, s[i-1] == t[j-1], there are 2 case:

		1. We do not match those two characters, which means that it still has original number 
		   of distinct subsequences, so dp[i][j] = dp[i-1][j].
		2. We match those two characters, in this way. dp[i][j] = dp[i-1][j-1];

	   Thus, including both two case, dp[i][j] = dp[i-1][j] + dp[i-1][j-1]

Stats:

	- 
	- 


public int numDistinct(String s, String t) {
    int n = s.length();
    int m = t.length();
    
    int[][] dp = new int[n+1][m+1];
    
    for (int i = 0; i < n+1; i++) {
        dp[i][0] = 1;
    }
    
    for (int j = 1; j < m+1; j++) {
        dp[0][j] = 0;
    }
    
    for (int j = 1; j < m+1; j++) {
        for (int i = 1; i < n+1; i++) {
            dp[i][j] = dp[i-1][j];
            if (s.charAt(i-1) == t.charAt(j-1)) {
                dp[i][j] += dp[i-1][j-1];
            }
        }
    }
    
    return dp[n][m];
}




==================================
AC Java Solution with O(T) Space and O(TS) Time

I think there is no need to maintain a matrix TxS; instead, I used a list of counters to track the
number of prefix of T. For example, given T="ABC", count[0] = count of "A", count[1] = count of "AB",
count[2] = count of "ABC".

public int numDistinct(String S, String T) {
    if (T.length() == 0)
        return 0;
        
    int[] counts = new int[T.length()];
    for (int indexS = 0; indexS < S.length(); indexS++) {
        char charS = S.charAt(indexS);
        for (int indexT = T.length() - 1; indexT >= 0; indexT--) {
            if (T.charAt(indexT) == charS) {
                if (indexT == 0)
                    counts[0]++;
                else
                    counts[indexT] += counts[indexT-1];
            }
        }
    }
    
    return counts[counts.length-1];
}


=======================================================================================================
method 2:

Method:

	-	DFS


Stats:
	- Runtime: 76 ms, faster than 5.20% 
	- Memory Usage: 115.1 MB, less than 7.69% 


	public int numDistinct(String s, String t) {
	    HashMap<String, Integer> map = new HashMap<>();
	    return numDistinctHelper(s, 0, t, 0, map);
	}

	private int numDistinctHelper(String s, int s_start, String t, int t_start, 
									HashMap<String, Integer> map) { 
	    if (t_start == t.length()) { 
	        return 1;
	    } 
	    if (s_start == s.length()) {
	        return 0;
	    }
	    String key = s_start + "@" + t_start;
	    if (map.containsKey(key)) {
	        return map.get(key); 
	    }
	    int count = 0; 
	    if (s.charAt(s_start) == t.charAt(t_start)) { 
	        count = numDistinctHelper(s, s_start + 1, t, t_start + 1, map) 
	              + numDistinctHelper(s, s_start + 1, t, t_start, map); 
	    }else{  
	        count = numDistinctHelper(s, s_start + 1, t, t_start, map);
	    } 
	    map.put(key, count);
	    return count; 
	}

