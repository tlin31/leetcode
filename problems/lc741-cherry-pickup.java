741. Cherry Pickup - Hard

In a N x N grid representing a field of cherries, each cell is one of three possible integers.

	0 means the cell is empty, so you can pass through;
	1 means the cell contains a cherry, that you can pick up and pass through;
	-1 means the cell contains a thorn that blocks your way.
 

Your task is to collect maximum number of cherries possible by following the rules below:

	1. Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through 
	   valid path cells (cells with value 0 or 1);
	2. After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
	3. When passing through a path cell containing a cherry, you pick it up and the cell becomes 
	   an empty cell (0);
	4. If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 

 

Example 1:

Input: grid =
[[0, 1, -1],
 [1, 0, -1],
 [1, 1,  1]]

Output: 5

Explanation: 
The player started at (0, 0) and went down, down, right right to reach (2, 2).
4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
Then, the player went left, up, up, left to return home, picking up one more cherry.
The total number of cherries picked up is 5, and this is the maximum possible.
 

Note:

grid is an N by N 2D array, with 1 <= N <= 50.
Each grid[i][j] is an integer in the set {-1, 0, 1}.
It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.



******************************************************
key:
	- round trip, go through as many 1 as possible
	- DP, dont modify grid

	- edge case:
		1) empty string, return empty
		2)

******************************************************

Analysis:

1. the max number of cherries of the second leg actually depends on the choice of path for the 
   first leg. This is because if we pluck some cherry in the first leg, it will no longer be 
   available for the 2nd leg (remember we reset the cell value from 1 to 0). 

2. max number of cherries of the trip not only depends on the starting position (i, j), but also 
   on the status of the grid matrix when that position is reached. This is because the grid matrix 
   may be modified differently along different paths towards the same position (i, j), therefore, 
   even if the starting position is the same, the maximum number of cherries may be different since 
   we are working with different grid matrix now.



关键思维转换
    ❌ 错误直觉: 先去一趟 DP, 再回来一趟 DP 👉 会 重复计数 / 无法避免冲突

    ✅ 正确建模（面试官最想听）

        👉 不是“走两次”
        👉 而是：两个人同时从 (0,0) 走到 (n-1,n-1)

        每一步：都只能向 右 or 下。两个人同步走， 如果走到同一个格子，樱桃只能算一次

DP 状态设计（核心）
    dp[k][i1][i2]：一共走了 k 步，第一个人在 (i1, k - i1)，第二个人在 (i2, k - i2) 能拿到的最多樱桃数

    📌 因为：row + col = k

状态转移（4 种情况）

    两个人每一步：

    下 / 下， 下 / 右， 右 / 下， 右 / 右

    dp[k][i1][i2] =
        max(
          dp[k-1][i1-1][i2-1],
          dp[k-1][i1-1][i2],
          dp[k-1][i1][i2-1],
          dp[k-1][i1][i2]
        )

加上当前格子的樱桃
    if (i1 == i2 && j1 == j2)
        cherries += grid[i1][j1];  // 同一个格子
    else
        cherries += grid[i1][j1] + grid[i2][j2];

初始化 & 边界

    dp 初始为 -∞（非法状态）

    dp[0][0][0] = grid[0][0]

    如果某一步走到 -1 → 跳过

import java.util.Arrays;

class Solution {
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        // dp[r1][r2] 表示在步数 k 下，两人的行坐标分别为 r1, r2 时的最大得分
        int[][] dp = new int[n][n];
        
        // 初始化：-1 表示不可达
        for (int[] row : dp) Arrays.fill(row, -1);
        dp[0][0] = grid[0][0];

        // 总步数 k = r + c，从 1 步走到 2n-2 步
        for (int k = 1; k <= 2 * n - 2; k++) {
            int[][] nextDp = new int[n][n];
            for (int[] row : nextDp) Arrays.fill(row, -1);

            // 遍历两个人的行坐标 r1, r2, 就是row
            // 范围限制：行不能超过 n-1，且 c = k - r 也不能超过 n-1 (即 r >= k - (n-1))
            for (int r1 = Math.max(0, k - (n - 1)); r1 <= Math.min(n - 1, k); r1++) {
                for (int r2 = Math.max(0, k - (n - 1)); r2 <= Math.min(n - 1, k); r2++) {
                    
                    // 如果当前格有荆棘，不可达
                    if (grid[r1][k - r1] == -1 || grid[r2][k - r2] == -1) continue;

                    // 寻找上一步的 4 种来源中的最大值
                    int res = -1;
                    for (int i = r1 - 1; i <= r1; i++) {
                        for (int j = r2 - 1; j <= r2; j++) {
                            if (i >= 0 && j >= 0) {
                                res = Math.max(res, dp[i][j]);
                            }
                        }
                    }

                    if (res != -1) {
                        // 加上当前格的樱桃
                        int cherries = (r1 == r2) ? grid[r1][k - r1] : 
                                       grid[r1][k - r1] + grid[r2][k - r2];
                        nextDp[r1][r2] = res + cherries;
                    }
                }
            }
            dp = nextDp;
        }

        return Math.max(0, dp[n - 1][n - 1]);
    }
}






class Solution {
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int maxK = 2 * n - 2; //两人一共总的最多的步数
        int[][][] dp = new int[maxK + 1][n][n];

        // 初始化为 -1 表示不可达
        for (int k = 0; k <= maxK; k++) {
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[k][i], -1);
            }
        }

        if (grid[0][0] == -1) return 0;

        dp[0][0][0] = grid[0][0];

        //总步数 k = r + c，从 1 步走到 2n-2 步
        for (int k = 1; k <= maxK; k++) {

            // 遍历两个人的行坐标 i1, i2
            // 范围限制：行不能超过 n-1，且 column = k - i 也不能超过 n-1 (即 i >= k - (n-1))
            for (int i1 = Math.max(0, k - (n - 1)); i1 <= Math.min(n - 1, k); i1++) {
                for (int i2 = Math.max(0, k - (n - 1)); i2 <= Math.min(n - 1, k); i2++) {
                    int j1 = k - i1;
                    int j2 = k - i2;

                    if (grid[i1][j1] == -1 || grid[i2][j2] == -1) continue;

                    int best = -1;

                    // 枚举两个人在上一步（k-1）可能来自的 4 种走法，选出“能到达当前状态的最大樱桃数”。
                    for (int di1 = 0; di1 <= 1; di1++) {
                        for (int di2 = 0; di2 <= 1; di2++) {
                            int pi1 = i1 - di1;
                            int pi2 = i2 - di2;
                            if (pi1 >= 0 && pi2 >= 0 && dp[k - 1][pi1][pi2] != -1) {
                                best = Math.max(best, dp[k - 1][pi1][pi2]);
                            }
                        }
                    }

                    if (best == -1) continue;

                    int cherries = best;
                    if (i1 == i2 && j1 == j2) {
                        cherries += grid[i1][j1];
                    } else {
                        cherries += grid[i1][j1] + grid[i2][j2];
                    }

                    dp[k][i1][i2] = cherries;
                }
            }
        }

        return Math.max(0, dp[maxK][n - 1][n - 1]);
    }
}

项目  复杂度
时间  O(n³)
空间  O(n³)（可优化为 O(n²) 👉 用 滚动数组：dp[k] 只依赖 dp[k-1]

=======================================================================================================
Method 1: DP - top down

Method:
	Assupmtion:
    - Go from (0, 0) -> (n-1, n-1) -> (0, 0) can be opt to two men go from (0, 0) -> (n-1, n-1)
      together, but when they go into the same cell, the cur state can only be added 1 (use once)

DP:
    1.  dp[x1][y1][x2] to represent the largest ans we can get when first guy (marked as A) 
        at(x1, y2) and second guy(marked as B) at (x2, x1 + y1 - x2)
        * because we can only go right and down, so we have x1 + y1 = x2 + y2 as num of steps
          taken is the same
    2.  Induction: every time we calculate the maximum of :
        * dp[x1 - 1][y1][x2] : A down, B right
        * dp[x1][y1 - 1][x2] : A right, B right
        * dp[x1 - 1][y1][x2 - 1]: A down, B down
        * dp[x1][y1 - 1][x2 - 1]: A right, B down

        if the Max of these values is negative:
        	we dont have a path to this point

        else if x1 != x2 && y1 != y2
        	dp[x1][y1][x2] = Max + grid[x1 - 1][y1 - 1] + grid[x2 - 1][y2 - 1]

        else:
        	we only add once.

    3.  Base case;
        we use dp[][][]from 1 - n, so we have:
            dp[1][1][1] = 1 and all other values are MIN_VALUE

    4.  Ans: dp[n][n][n]

    5.  Direction:
        from top left -> bottom right


Stats:

	- Time: O(n^3)
	- Space: O(n^3)

class Solution {
    int[][][] memo;
    int[][] grid;
    int N;

    public int cherryPickup(int[][] grid) {
        this.grid = grid;
        N = grid.length;
        memo = new int[N][N][N];
        for (int[][] layer: memo)
            for (int[] row: layer)
                Arrays.fill(row, Integer.MIN_VALUE);
        return Math.max(0, dp(0, 0, 0));
    }

    public int dp(int x1, int y1, int y2) {
        int x2 = x1 + y1 - y2;

        // if at the boundary of the grid / cannot access with -1 for player 1 or player 2
        if (N == x1 || N == x2 || N == y1 || N == y2 || grid[x1][y1] == -1 || grid[x2][y2] == -1) {
            return -999999;    

        } else if (x1 == N-1 && y1 == N-1) {
        	// reach the end
            return grid[x1][y1];

        } else if (memo[x1][y1][y2] != Integer.MIN_VALUE) {
        	// already modified, go to the next step
            return memo[x1][y1][y2];

        } else {
            int ans = grid[x1][y1];

            // if player 1 and 2 are not in the same cell, add player 2's value as well
            if (y1 != y2) 
            	ans += grid[x2][y2];

            ans += Math.max(Math.max(dp(x1, y1+1, y2+1), dp(x1+1, y1, y2+1)),
                            Math.max(dp(x1, y1+1, y2),   dp(x1+1, y1, y2)));

            memo[x1][y1][y2] = ans;
            return ans;
        }
    }
}

=======================================================================================================
method 2: DP - bottom up

Method:

	-	Since our recursion only references the next layer, we only need to keep two layers in 
		memory at a time.
	-	At time t, let dp[c1][c2] be the most cherries that we can pick up for two people going 
		from (0, 0) to (r1, c1) and (0, 0) to (r2, c2), where r1 = t-c1, r2 = t-c2. 



Stats:

	- Time Complexity: O(N^3), where N is the length of grid. We have three for-loops of size O(N).
	- Space Complexity: O(N^2), the sizes of dp and dp2.



class Solution {
    public int cherryPickup(int[][] grid) {
        int N = grid.length;
        int[][] dp = new int[N][N];
        for (int[] row: dp) 
        	Arrays.fill(row, Integer.MIN_VALUE);
        dp[0][0] = grid[0][0];

        // go through time t
        for (int t = 1; t <= 2*N - 2; ++t) {
            int[][] dp2 = new int[N][N];
            for (int[] row: dp2) 
            	Arrays.fill(row, Integer.MIN_VALUE);

            for (int i = Math.max(0, t-(N-1)); i <= Math.min(N-1, t); ++i) {
                for (int j = Math.max(0, t-(N-1)); j <= Math.min(N-1, t); ++j) {
                    if (grid[i][t-i] == -1 || grid[j][t-j] == -1) 
                    	continue;
                    int val = grid[i][t-i];

                    if (i != j) 
                    	val += grid[j][t-j];

                    for (int pi = i-1; pi <= i; ++pi)
                        for (int pj = j-1; pj <= j; ++pj)
                            if (pi >= 0 && pj >= 0)
                                dp2[i][j] = Math.max(dp2[i][j], dp[pi][pj] + val);
                }
            }
            dp = dp2;
        }
        return Math.max(0, dp[N-1][N-1]);
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



