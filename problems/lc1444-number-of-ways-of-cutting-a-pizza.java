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


难点：
    二维前缀和（快速判断一个区域有没有苹果）

    三维 DP（位置 + 剩余切数）

    切法顺序有方向性（只能横/竖切）

二维前缀和（是否有苹果）
    🍎 定义： preSum[i][j]：从 (i,j) 到右下角 (m-1,n-1) 的苹果数量

    🍎 构建方式（倒着建）
    preSum[i][j] =
        (pizza[i][j] == 'A' ? 1 : 0)
        + preSum[i + 1][j]
        + preSum[i][j + 1]
        - preSum[i + 1][j + 1];

    🍎 用途（非常关键）

    判断某个子矩阵是否 至少有一个苹果：

    boolean hasApple(int r1, int c1, int r2, int c2) {
        return preSum[r1][c1] - preSum[r2][c1] - preSum[r1][c2] + preSum[r2][c2] > 0;
    }


    👉 这是后面所有剪枝和合法性的基础

DP 状态设计

    dp[k][i][j]： 从 (i,j) 这个左上角开始的子披萨，切 k 次（得到 k+1 块），且每块都有苹果的方案数

    🔷 初始状态（base case）
    dp[0][i][j] = 1 if 子披萨(i,j) 有苹果
                = 0 otherwise


    👉 不再切了，只要当前这块合法，就是 1 种



状态转移（横切 + 竖切）

    1️、横切（horizontal cut）

    在 i+1 ~ m-1 之间找一条横线 x

    条件：

    上半部分 (i,j) → (x-1,n-1) 必须有苹果

    剩下部分交给 dp[k-1][x][j]

    if (hasApple(i, j, x, n))
        dp[k][i][j] += dp[k-1][x][j];

    2️、 竖切（vertical cut）

    在 j+1 ~ n-1 之间找一条竖线 y

    条件：

    左半部分 (i,j) → (m-1,y-1) 有苹果

    剩下部分 dp[k-1][i][y]

    if (hasApple(i, j, m, y))
        dp[k][i][j] += dp[k-1][i][y];

    🔥 为什么不管“下半 / 右半有没有苹果”？

    👉 因为：

    下半 / 右半 会在 dp[k-1][...] 中继续检查

    递归 + DP 已经保证了每一块都会被验证


状态数：K * m * n

每个状态枚举行和列：O(m + n)

总时间复杂度：O(K * m * n * (m + n))

在题目约束内是 可接受的




class Solution {
    private static final int MOD = 1_000_000_007;

    public int ways(String[] pizza, int K) {
        int m = pizza.length, n = pizza[0].length();

        // 1. build prefix sum
        int[][] pre = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                pre[i][j] =
                    (pizza[i].charAt(j) == 'A' ? 1 : 0)
                    + pre[i + 1][j]
                    + pre[i][j + 1]
                    - pre[i + 1][j + 1];
            }
        }

        // 2. dp[k][i][j]
        int[][][] dp = new int[K][m][n];

        // base case: k = 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pre[i][j] > 0)
                    dp[0][i][j] = 1;
            }
        }

        // 3. transition
        for (int k = 1; k < K; k++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {

                    // horizontal cuts
                    for (int x = i + 1; x < m; x++) {
                        // presum array, check whether this area has apple
                        if (pre[i][j] - pre[x][j] > 0) {
                            dp[k][i][j] = (dp[k][i][j] + dp[k - 1][x][j]) % MOD;
                        }
                    }

                    // vertical cuts
                    for (int y = j + 1; y < n; y++) {
                        if (pre[i][j] - pre[i][y] > 0) {
                            dp[k][i][j] = (dp[k][i][j] + dp[k - 1][i][y]) % MOD;
                        }
                    }
                }
            }
        }

        return dp[K - 1][0][0];
    }
}




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

