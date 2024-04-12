1397. Find All Good Strings - Hard


Given the strings s1 and s2 of size n, and the string evil. Return the number of good strings.

A good string has size n, it is alphabetically greater than or equal to s1, it is alphabetically 
smaller than or equal to s2, and it does not contain the string evil as a substring. Since the answer 
can be a huge number, return this modulo 10^9 + 7.

 

Example 1:

Input: n = 2, s1 = "aa", s2 = "da", evil = "b"
Output: 51 
Explanation: There are 25 good strings starting with 'a': "aa","ac","ad",...,"az". Then there are 25 
good strings starting with 'c': "ca","cc","cd",...,"cz" and finally there is one good string starting
with 'd': "da". 



Example 2:

Input: n = 8, s1 = "leetcode", s2 = "leetgoes", evil = "leet"
Output: 0 
Explanation: All strings greater than or equal to s1 and smaller than or equal to s2 start with the
prefix "leet", therefore, there is not any good string.


Example 3:

Input: n = 2, s1 = "gx", s2 = "gz", evil = "x"
Output: 2
 

Constraints:

s1.length == n
s2.length == n
s1 <= s2
1 <= n <= 500
1 <= evil.length <= 50
All strings consist of lowercase English letters.


******************************************************
key:
	- KMP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time: O(n*m*2*2*26), where n<=500, m<=50 is length of evil
	- Space: O(m*n*2*2)



Method:

	-	Trie approach
	-	compute LPS: we keep track of the length of the longest prefix suffix value (we use len 
	                 variable for this purpose) for the previous index. We initialize lps[0] and len 
	                 as 0. If pat[len] and pat[i] match, we increment len by 1 and assign the incremented
	                 value to lps[i]. If pat[i] and pat[len] do not match and len is not 0, we update 
	                 len to lps[len-1]. See computeLPSArray () in the below code for details.




  	public int findGoodStrings(int n, String s1, String s2, String evil) {
        int[] dp = new int[1 << 17]; // Need total 17 bits, can check getKey() function
        return dfs(0, 0, true, true,
                n, s1.toCharArray(), s2.toCharArray(), evil.toCharArray(), computeLPS(evil.toCharArray()), dp);
    }

    int dfs(int i, int evilMatched, boolean leftBound, boolean rightBound,
            int n, char[] s1, char[] s2, char[] evil, int[] lps, int[] dp) {

		// contain `evil` as a substring -> not good string
        if (evilMatched == evil.length) 
        	return 0; 

        // find a desired good string
        if (i == n) 
        	return 1; 

        int key = getKey(i, evilMatched, leftBound, rightBound);

        if (dp[key] != 0) 
        	return dp[key];

        char from = leftBound ? s1[i] : 'a';
        char to = rightBound ? s2[i] : 'z';
        int res = 0;

        for (char c = from; c <= to; c++) {
        	// j means the next match between current string (end at char `c`) and `evil` string
            int j = evilMatched; 

            while (j > 0 && evil[j] != c) 
            	j = lps[j - 1];

            if (c == evil[j]) 
            	j++;

            res += dfs(i + 1, j, leftBound && (c == from), rightBound && (c == to),
                    n, s1, s2, evil, lps, dp);

            res %= 1000000007;
        }

        return dp[key] = res;
    }

    int getKey(int n, int m, boolean b1, boolean b2) {
        // Need 9 bits store n (2^9=512), 6 bits store m (2^6=64), 1 bit store b1, 1 bit store b2
        return (n << 8) | (m << 2) | ((b1 ? 1 : 0) << 1) | (b2 ? 1 : 0);
    }

    // find longest prefix suffic string
    int[] computeLPS(char[] str) { 
        int n = str.length;
        int[] lps = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && str[i] != str[j]) 
            	j = lps[j - 1];

            if (str[i] == str[j]) 
            	lps[i] = ++j;
        }
        return lps;
    }


How to use lps[] to decide next positions (or to know a number of characters to be skipped)?

	We start comparison of pat[j] with j = 0 with characters of current window of text.
	We keep matching characters txt[i] and pat[j] and keep incrementing i and j while pat[j] and 
	   txt[i] keep matching.
	When we see a mismatch:
		1. We know that characters pat[0..j-1] match with txt[i-j…i-1] (Note that j starts with 0 
		   and increment it only when there is a match).
		2. We also know (from above definition) that lps[j-1] is count of characters of pat[0…j-1] 
		   that are both proper prefix and suffix.
		3. From 1& 2, we do not need to match these lps[j-1] characters with txt[i-j…i-1] because we 
		   know that these characters will anyway match. 

txt[] = "AAAAABAAABA" 
pat[] = "AAAA"
lps[] = {0, 1, 2, 3} 

i = 0, j = 0
txt[] = "AAAAABAAABA" 
pat[] = "AAAA"
txt[i] and pat[j] match, do i++, j++

i = 1, j = 1
txt[] = "AAAAABAAABA" 
pat[] = "AAAA"
txt[i] and pat[j] match, do i++, j++

i = 2, j = 2
txt[] = "AAAAABAAABA" 
pat[] = "AAAA"
pat[i] and pat[j] match, do i++, j++

i = 3, j = 3
txt[] = "AAAAABAAABA" 
pat[] = "AAAA"
txt[i] and pat[j] match, do i++, j++

i = 4, j = 4
Since j == M, print pattern found and reset j,
j = lps[j-1] = lps[3] = 3

Here unlike Naive algorithm, we do not match first three 
characters of this window. Value of lps[j-1] (in above 
step) gave us index of next character to match.
i = 4, j = 3
txt[] = "AAAAABAAABA" 
pat[] =  "AAAA"
txt[i] and pat[j] match, do i++, j++

i = 5, j = 4
Since j == M, print pattern found and reset j,
j = lps[j-1] = lps[3] = 3

Again unlike Naive algorithm, we do not match first three 
characters of this window. Value of lps[j-1] (in above 
step) gave us index of next character to match.
i = 5, j = 3
txt[] = "AAAAABAAABA" 
pat[] =   "AAAA"
txt[i] and pat[j] do NOT match and j > 0, change only j
j = lps[j-1] = lps[2] = 2

i = 5, j = 2
txt[] = "AAAAABAAABA" 
pat[] =    "AAAA"
txt[i] and pat[j] do NOT match and j > 0, change only j
j = lps[j-1] = lps[1] = 1 

i = 5, j = 1
txt[] = "AAAAABAAABA" 
pat[] =     "AAAA"
txt[i] and pat[j] do NOT match and j > 0, change only j
j = lps[j-1] = lps[0] = 0

i = 5, j = 0
txt[] = "AAAAABAAABA" 
pat[] =      "AAAA"
txt[i] and pat[j] do NOT match and j is 0, we do i++.

i = 6, j = 0
txt[] = "AAAAABAAABA" 
pat[] =       "AAAA"
txt[i] and pat[j] match, do i++ and j++

i = 7, j = 1
txt[] = "AAAAABAAABA" 
pat[] =       "AAAA"
txt[i] and pat[j] match, do i++ and j++

===== ex. LPS:===========
pat[] = "AAACAAAA"

len = 0, i  = 0.
lps[0] is always 0, we move 
to i = 1

len = 0, i  = 1.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 1, lps[1] = 1, i = 2

len = 1, i  = 2.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 2, lps[2] = 2, i = 3

len = 2, i  = 3.
Since pat[len] and pat[i] do not match, and len > 0, 
set len = lps[len-1] = lps[1] = 1

len = 1, i  = 3.
Since pat[len] and pat[i] do not match and len > 0, 
len = lps[len-1] = lps[0] = 0

len = 0, i  = 3.
Since pat[len] and pat[i] do not match and len = 0, 
Set lps[3] = 0 and i = 4.
We know that characters pat
len = 0, i  = 4.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 1, lps[4] = 1, i = 5

len = 1, i  = 5.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 2, lps[5] = 2, i = 6

len = 2, i  = 6.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 3, lps[6] = 3, i = 7

len = 3, i  = 7.
Since pat[len] and pat[i] do not match and len > 0,
set len = lps[len-1] = lps[2] = 2

len = 2, i  = 7.
Since pat[len] and pat[i] match, do len++, 
store it in lps[i] and do i++.
len = 3, lps[7] = 3, i = 8



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

from functools import lru_cache

def srange(a, b):
    yield from (chr(i) for i in range(ord(a), ord(b)+1))
        
def failure(pat): 
    res = [0]
    i, target = 1, 0
    while i < len(pat): 
        if pat[i] == pat[target]: 
            target += 1
            res += target,
            i += 1
        elif target: 
            target = res[target-1] 
        else: 
            res += 0,
            i += 1
    return res                        

class Solution:
    def findGoodStrings(self, n: int, s1: str, s2: str, evil: str) -> int:
        f = failure(evil)
        @lru_cache(None)
        def dfs(idx, max_matched=0, lb=True, rb=True):
            '''
			idx: current_idx_on_s1_&_s2, 
			max_matched: nxt_idx_to_match_on_evil, 
			lb, rb: is_left_bound, is_right_bound
			'''
            if max_matched == len(evil): return 0 # evil found, break
            if idx == n: return 1 # base case
            
            l = s1[idx] if lb else 'a' # valid left bound
            r = s2[idx] if rb else 'z' # valid right bound
            candidates = [*srange(l, r)]
            
            res = 0
            for i, c in enumerate(candidates):
                nxt_matched = max_matched
                while evil[nxt_matched] != c and nxt_matched:
                    nxt_matched = f[nxt_matched - 1]
                res += dfs(idx+1, nxt_matched + (c == evil[nxt_matched]), 
                           lb=(lb and i == 0), rb=(rb and i == len(candidates)-1))
            return res                
        
        return dfs(0) % (10**9 + 7)

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

