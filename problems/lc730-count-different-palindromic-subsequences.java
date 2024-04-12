730. Count Different Palindromic Subsequences - Hard

Given a string S, find the number of different non-empty palindromic subsequences in S, and return 
that number modulo 10^9 + 7.

A subsequence of a string S is obtained by deleting 0 or more characters from S.

A sequence is palindromic if it is equal to the sequence reversed.

Each character S[i] will be in the set {'a', 'b', 'c', 'd'}.


Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i != B_i.

Example 1:
Input: 
S = 'bccb'
Output: 6
Explanation: 
The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.

Example 2:
Input: 
S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
Output: 104860361
Explanation: 
There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 10^9 + 7.
Note:

The length of S will be in the range [1, 1000].



******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP with O(n^3)

Method:

	- Let dp[x][i][j] be the answer for the substring S[i...j] where S[i] == S[j] == 'a'+x. 
	  Note that since we only have 4 characters a, b, c, d, thus 0 <= x < 4. 
	- assume there are 4 tables corresponding to a, b, c, d indicated by x

	- DP:
		1) If S[i] != 'a'+x, then dp[x][i][j] = dp[x][i+1][j], note that here we leave the first
		   character S[i] in the window out due to our definition of dp[x][i][j].

		2) If S[j] != 'a'+x, then dp[x][i][j] = dp[x][i][j-1], leaving the last character S[j] out.

		3) If S[i] == S[j] == 'a'+x, then dp[x][i][j] = 2 + dp[0][i+1][j-1] + dp[1][i+1][j-1] + 
														    dp[2][i+1][j-1] + dp[3][i+1][j-1]
		   
		   When the first and last characters are the same, we need to count all the distinct
		   palindromes (for each of a,b,c,d) within the sub-window S[i+1][j-1] plus the 2 
		   palindromes contributed by the first and last characters.

	-	Let n be the length of the input string S, The final answer would be 
	        dp[0][0][n-1] + dp[1][0][n-1] + dp[2][0][n-1] + dp[3][0][n-1] mod 1000000007.

    - loop: i needs to get all i+1 cases, so go from end to start
            j needs to get all j-1 cases, and j>i, so (int j = i, j <n, j++)
            k does not matter where to start, since all 4 tables are treated equally

Stats:

	- 
	- 


class Solution {
  public int countPalindromicSubsequences(String S) {
    int n = S.length();
    int mod = 1000000007;
    int[][][] dp = new int[4][n][n];

    // i is the start index, j is the end index, k is the position of {a, b, c, d}
    for (int i = n-1; i >= 0; --i) {
      for (int j = i; j < n; ++j) {
        for (int k = 0; k < 4; ++k) {
          char c = (char) ('a' + k);

          // fills in for the correct table k.
          if (j == i) {
            if (S.charAt(i) == c) 
            	dp[k][i][j] = 1;
            else 
            	dp[k][i][j] = 0;
          } 

          else { 
            if (S.charAt(i) != c) 
            	dp[k][i][j] = dp[k][i+1][j];
            else if (S.charAt(j) != c) 
            	dp[k][i][j] = dp[k][i][j-1];
            else { 

              // S[i] == S[j] == c
              if (j == i+1) 
              	// i & j right next to each other, ex."aa" : {"a", "aa"}
              	dp[k][i][j] = 2; 

              else { 

              	// length is > 2
                dp[k][i][j] = 2;
                for (int m = 0; m < 4; ++m) { // count each one within subwindows [i+1][j-1]
                  dp[k][i][j] += dp[m][i+1][j-1];
                  dp[k][i][j] %= mod;
                }
              }
            }
          }
        }
      }
    }

    int ans = 0;
    for (int k = 0; k < 4; ++k) {
      ans += dp[k][0][n-1];
      ans %= mod;
    }

    return ans;
  }
}


python: bottom up DP
    def countPalindromicSubsequences(self, S):
        M = 1000000007
        def DFS(start, end, mem={}):
            if (start, end) in mem: 
                return mem[(start, end)]
            
            count = 0
            s = S[start:end+1]
            for x in 'abcd':
                try:
                    i = s.index(x) + start
                    j = s.rindex(x) + start
                except:
                    continue
                    
                count += DFS(i+1, j-1, mem) + 2 if i!=j else 1
            mem[(start, end)] = count % M
            return mem[(start, end)]

        // just call DFS function   
        return DFS(0, len(S)-1)


=======================================================================================================
method 2: DP with O(n^2)

Method:
	-  Assume that dp[i][j] is the number of different palindromic subsequences in range [i,j]

		base case: 
			count(x) = 1 if x is in {a,b,c,d}
					 = 0 if x is empty

		1) if s[i] != s[j], so [i,j] not a palindromic string, we can get rid of either s[i] or s[j].

				dp[i][j] = dp[i+1][j] + dp[i][j-1] - d[i+1][j-1]

		    we must -d[i+1][j-1], because the range [i+1, j-1] is calculated twice.

		2) if s[i] == s[j] 
			case 1: if s[i] not in s[i+1] ~ s[j-1]:
						dp[i][j] = dp[i+1][j-1] * 2 + 2
						// +2 because need to consider the substr 's[i]' & 's[i]s[i]''

			case 2:	if count(s[i+1, j-1], s[i]) == 1:
						dp[i][j] = dp[i+1][j-1] * 2 + 1
						// +1 because there is a repeated single char 's[i]'
						// meaning if there's a repeated char in the inner sub array [i+1, j-1]

			case 3: if 2个或以上相同的字符:
						find the first pos & last pos of s[i] in subarray s[i+i, j-1]
						dp[i][j] = dp[i+1][j-1] * 2 - d[left+1][right-1]

						- duplicated elements within range (left,right) (a sub range of [i,j]) where:
							left is the next position to the right from i so that s[i] = s[left]
							right is the next position to the left from j so that s[i] = s[right]

						- all elements in [i1+1, j1-1] will be duplicated, because:
								s[i1] { [i1+1, j1-1] } s[j1] = s[i] { [i1+1, j1-1] } s[j]

ex. 

if not equal char:
bcc --> count(bc) + count(cc) - count(c)			minus the overlapped part within first 2 terms


if equal char, here s[i] == s[j] == b:
	case 1: bccb
		cc --> 2 * count("empty string") + 2 = 2: 	c, cc
		bccb --> 2 * count(cc) + 2 = 6: 			c, cc, bcb,bccb,b,bb = 2*subproblem + left & right

	case 2: bcbcb
		cbc --> 2 * count (b) + 2 = 4				b, cbc, c, cc
		bcbcb --> 2 * count(cbc) + 1 = 9			b,  cbc,  c,  cc
													bbb,bcbcb,bcb,bccb
													bb //此时不能再有单独的b了，因为他是被cbc cover了

	case 3: bbcabb
		bcab --> 2 * count(ca) + 2					c, a, bcb, bab, b, bb
		bbcabb --> 2 * count(bcab) - count(ca)		c, a, bcb, bab, b, bb
													bcb, bab, bbcbb, bbabb, bbb,bbbb, 
													bcb, bab (these 2 are repetitive!)

			Note: left = 1, right = 4, because these two 'b' are the first and last char = s[i] = b
			so count(repetitive) = s[left +1, right -1] = s[2,3] = 'ca'



class Solution {

    int countPalindromicSubsequences(String S) {
    	long kMod = 1000000007;
        int n = S.length();
        int[][] dp = new int[n][n];

        // initialize diagnal
        for (int i = 0; i < n; ++i)
            dp[i][i] = 1;
        
        for (int len = 1; len <= n; ++len) {
            for (int i = 0; i < n - len; ++i) {
                // end index
                int j = i + len;                
                if (S[i] == S[j]) {
                    dp[i][j] = dp[i + 1][j - 1] * 2;  

                    // search for repeated characters S[i] within substring [i+1, j-1]                      
                    int l = i + 1;
                    int r = j - 1;
                    while (l <= r && S[l] != S[i]) ++l;
                    while (l <= r && S[r] != S[i]) --r;      

                    if (l == r) 
                    	dp[i][j] += 1;
                    else if (l > r) 
                    	dp[i][j] += 2;
                    else 
                    	dp[i][j] -= dp[l + 1][r - 1];

                } else {
                    dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1]; 
                }
                
                dp[i][j] = (dp[i][j] + kMod) % kMod;
            }
        }
        
        return dp[0][n - 1];
    }
}

------------------------------
Calculate right Next & left Next ahead of time:

    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        int[] rightNext = rightNext(s, new int[] {n, n, n, n});
        int[] leftNext = leftNext(s, new int[] {-1, -1, -1, -1});
        int[][] dp = new int[n][n];
        int extra = 0;

        for (int d = 0; d < n; d++) {
            for (int i = 0, j = i+d; j < n; i++, j++) {
                if (i == j) 
                	dp[i][j] = 1;
                else if (s.charAt(i) != s.charAt(j)) 
                	dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
                else {
                    int r = rightNext[i], 
                        l = leftNext[j];
                    if (r<l) 
                    	extra = - dp[r+1][l-1];
                    else if (r == l)
                    	extra = 1;
                    else if (r>l)
                    	extra = 2;

                    dp[i][j] = 2 * dp[i+1][j-1] + extra;
                }
                dp[i][j] = dp[i][j] < 0 ? dp[i][j] + 1000000007 : dp[i][j] % 1000000007;
            }
        }
        return dp[0][n - 1];
    }

	private int[] leftNext(String s, int[] rec) {
        int n = s.length(), res[] = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = rec[s.charAt(i) - 'a'];
            rec[s.charAt(i) - 'a'] = i;
        }
        return res;
    }

    private int[] rightNext(String s, int[] rec) {
        int n = s.length(), 
        res[] = new int[n];
        for (int i = n-1; i >= 0; i--) {
            res[i] = rec[s.charAt(i) - 'a'];
            rec[s.charAt(i) - 'a'] = i;
        }
        return res;
    }

ex. bccb:

the rightNext = [3, 2, 4, 4]
the leftNext = [-1, -1, 1, 0]


python:
    def countPalindromicSubsequences(self, S: str) -> int:
        n = len(S)
        table,prv,nxt,A = [[0]*n for i in range(n)],[None]*n,[None]*n,[0]*n
        for i in range(n):
            A[i] = ord(S[i])-ord('a')
        last = [None]*4
        for i in range(n):
            prv[i] = last[A[i]]
            last[A[i]] = i
            
        last = [None]*4
        for j in range(n-1,-1,-1):
            nxt[j] = last[A[j]]
            last[A[j]] = j

        for d in range(n):
            for i in range(n-d):
                j = i+d
                if(i==j):
                    table[i][j] = 1
                elif(A[i]!=A[j]):
                    table[i][j] = table[i+1][j]+table[i][j-1]-table[i+1][j-1]
                else:
                    i0, j0, extra = nxt[i], prv[j], 0
                    if(i0 is not None and j0 is not None):
                        extra = (-table[i0+1][j0-1]) if i0<j0 else (1 if i0==j0 else 2)
                    table[i][j] = 2*table[i+1][j-1]+extra
                    
                table[i][j] = (table[i][j]+10**9+7) if table[i][j]<0 else table[i][j]%(10**9+7)
        
        return table[0][n-1]


=======================================================================================================
method 3: recursion

Method:

	-	
	-	


Stats:

	- 
	- 

// Author: Huahua
// Runtime: 107 ms
class Solution {
    private int[][] m_;
    private static final int kMod = 1000000007;
    public int countPalindromicSubsequences(String S) {
        int n = S.length();
        m_ = new int[n][n];
        return count(S.toCharArray(), 0, n - 1);
    }
    
    private int count(char[] s, int i, int j) {
        if (i > j) return 0;
        if (i == j) return 1;
        if (m_[i][j] > 0) return m_[i][j];
        
        long ans = 0;
        
        if (s[i] == s[j]) {
            ans += count(s, i + 1, j - 1) * 2;
            int l = i + 1;
            int r = j - 1;
            while (l <= r && s[l] != s[i]) ++l;
            while (l <= r && s[r] != s[i]) --r;
            if (l > r) ans += 2;
            else if (l == r) ans += 1;
            else ans -= count(s, l + 1, r - 1);
        } else {
            ans = count(s, i, j - 1) 
                + count(s, i + 1, j) 
                - count(s, i + 1, j - 1);
        }
        
        m_[i][j] = (int)((ans + kMod) % kMod);
        return m_[i][j];
    }
}


python:
    def countPalindromicSubsequences(self, S):
        """
        :type S: str
        :rtype: int
        """
        if not S:
            return 0
        
        n = len(S)
        self.div = 10 ** 9 + 7
        
        # dp[i][j] => number(after mod) of palindrom subseqs in S[i:j+1]
        dp = [[-1 for _ in range(n)] for _ in range(n)]
        
        # 'abba' => [[0, 3, 3, 3], [1, 1, 2, -1], ...]
        upper = [[-1 for _ in range(n)] for _ in range(4)]
        for i in range(len(S))[::-1]:
            idx = ord(S[i]) - ord('a')
            for k in range(4):
                if k == idx:
                    upper[k][i] = i
                elif i < n - 1:
                    upper[k][i] = upper[k][i + 1]
                    
        # 'abba' => [[0, 0, 0, 3], [-1, 1, 2, 2], ...]
        lower = [[-1 for _ in range(n)] for _ in range(4)]
        for i in range(len(S)):
            idx = ord(S[i]) - ord('a')
            for k in range(4):
                if k == idx:
                    lower[k][i] = i
                elif i > 0:
                    lower[k][i] = lower[k][i - 1]
        
        return self.memo(S, 0, n - 1, dp, upper, lower)
    
    # find # of palindrom subseqs from S[start: end + 1]
    def memo(self, S, start, end, dp, upper, lower):
        if start > end:
            return 0
        elif start == end:
            return 1
        elif dp[start][end] != -1:
            return dp[start][end]
        
        res = 0
        for k in range(4):
            new_start, new_end = upper[k][start], lower[k][end]

            if new_start == -1 or new_start > end:
                continue
            # include one, a.. or ..a
            res += 1
            # include both, a..a
            if new_start != new_end:
                res +=1
            # only internal ..
            res += self.memo(S, new_start + 1, new_end - 1, dp, upper, lower)
        
        dp[start][end] = res % self.div
        return dp[start][end]
