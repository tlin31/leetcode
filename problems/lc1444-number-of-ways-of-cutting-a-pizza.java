1444. Number of Ways of Cutting a Pizza - Hard


Given a rectangular pizza represented as a rows x cols matrix containing the following characters: 
'A' (an apple) and '.' (empty cell) and given the integer k. You have to cut the pizza into k 
pieces using k-1 cuts. 


For each cut you choose the direction: vertical or horizontal, then you choose a cut position at 
the cell boundary and cut the pizza into two pieces. If you cut the pizza vertically, give the 
left part of the pizza to a person. If you cut the pizza horizontally, give the upper part of 
the pizza to a person. Give the last piece of pizza to the last person.

Return the number of ways of cutting the pizza such that each piece contains at least one apple. 
Since the answer can be a huge number, return this modulo 10^9 + 7.

 

Example 1:

Input: pizza = ["A..","AAA","..."], k = 3
Output: 3 
Explanation: The figure above shows the three ways to cut the pizza. Note that pieces must contain 
at least one apple.


Example 2:

Input: pizza = ["A..","AA.","..."], k = 3
Output: 1
Example 3:

Input: pizza = ["A..","A..","..."], k = 1
Output: 1
 

Constraints:

1 <= rows, cols <= 50
rows == pizza.length
cols == pizza[i].length
1 <= k <= 10
pizza consists of characters 'A' and '.' only.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:
	- Time: O(k*m*n*(m+n)), where m<=50 is the number of rows, n<=50 is the number of columns, 
	         k<=10 is the number of pieces of pizza.

		Explain: There are total k*m*n states in dfs(...k, r, c...). Each state needs maximum (m+n) 
		         times to cut in horizontal or vertical.
	- Space: O(k*m*n)


Method:

	- The idea is to first convert the input to a binary matrix where an entry is 1 if and only if 
	  the input has an 'A' in that position. Then, we compute a "prefix sum" matrix, which will 
	  allow us to fetch the sum for any sub-matrix of the input matrix.

	- For the actual DP, if we have made k cuts, we just require that the last piece has an apple 
	  (i.e. sums to at least 1). Otherwise, we simply try all cuts horizontally, then vertically, 
	  and for each attempt we only seek the answer with 1 fewer cuts needed for the lower- or 
	  right-piece if the upper- or left-piece has at least 1 apple.

	- Addendum: Notice we always have the bottom-right point fixed as (m-1,n-1); I didnt notice 
	  during the contest, but taking this into account easily reduces the DP to O(mn(m+n)k) time 
	  complexity. 
	  Also we do not even need to preprocess the initial matrix into a binary 2D matrix explicitly; 
	  we would only use it for computing the sums matrix, so we can just check the corresponding 
	  character in the input when computing the sums matrix instead.



class Solution {
    public int ways(String[] pizza, int k) {
        int m = pizza.length, n = pizza[0].length();
        Integer[][][] dp = new Integer[k][m][n];

        // preSum[r][c] is the total number of apples in pizza[r:][c:]
        int[][] preSum = new int[m+1][n+1]; 

        // calculate presum
        for (int r = m - 1; r >= 0; r--)
            for (int c = n - 1; c >= 0; c--)
                preSum[r][c] = preSum[r][c+1] + preSum[r+1][c] - preSum[r+1][c+1] 
                               + (pizza[r].charAt(c) == 'A' ? 1 : 0);
        
        return dfs(m, n, k-1, 0, 0, dp, preSum);
    }

    int dfs(int m, int n, int k, int r, int c, Integer[][][] dp, int[][] preSum) {
        
        // if the remain piece has no apple -> invalid
        if (preSum[r][c] == 0) 
        	return 0; 

        // found valid way after using k-1 cuts && now preSum[r][c] > 0
        if (k == 0) 
        	return 1; 

        if (dp[k][r][c] != null) 
        	return dp[k][r][c];

        int ans = 0;

        // cut in horizontal
        for (int nr = r + 1; nr < m; nr++) 
        	// cut if the upper piece contains at least one apple
            if (preSum[r][c] - preSum[nr][c] > 0) 
                ans = (ans + dfs(m, n, k - 1, nr, c, dp, preSum)) % 1_000_000_007;

        // cut in vertical
        for (int nc = c + 1; nc < n; nc++)
        	// cut if the left piece contains at least one apple 
            if (preSum[r][c] - preSum[r][nc] > 0) 
                ans = (ans + dfs(m, n, k - 1, r, nc, dp, preSum)) % 1_000_000_007;
        
		dp[k][r][c] = ans;
        return ans;
    }
}

input: 3 & 
   [A 	.	.
	A 	A 	A
	.	.	. ]


[4, 2, 1, 0]
[3, 2, 1, 0]
[0, 0, 0, 0]





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def ways(self, pizza: List[str], K: int) -> int:
        m, n, MOD = len(pizza), len(pizza[0]), 10 ** 9 + 7
        preSum = [[0] * (n + 1) for _ in range(m + 1)]
        for r in range(m - 1, -1, -1):
            for c in range(n - 1, -1, -1):
                preSum[r][c] = preSum[r][c + 1] + preSum[r + 1][c] - preSum[r + 1][c + 1] + (pizza[r][c] == 'A')

        @lru_cache(None)
        def dp(k, r, c):
            if preSum[r][c] == 0: return 0
            if k == 0: return 1
            ans = 0
            # cut horizontally
            for nr in range(r + 1, m):
                if preSum[r][c] - preSum[nr][c] > 0:
                    ans = (ans + dp(k - 1, nr, c)) % MOD
            # cut vertically                    
            for nc in range(c + 1, n):
                if preSum[r][c] - preSum[r][nc] > 0:
                    ans = (ans + dp(k - 1, r, nc)) % MOD
            return ans

        return dp(K - 1, 0, 0)

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

