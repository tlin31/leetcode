3651. Minimum Cost Path with Teleportations- Hard

You are given a m x n 2D integer array grid and an integer k. You start at the top-left cell (0, 0) and your goal is to reach the bottom‐right cell (m - 1, n - 1).

There are two types of moves available:

Normal move: You can move right or down from your current cell (i, j), i.e. you can move to (i, j + 1) (right) or (i + 1, j) (down). The cost is the value of the destination cell.

Teleportation: You can teleport from any cell (i, j), to any cell (x, y) such that grid[x][y] <= grid[i][j]; the cost of this move is 0. You may teleport at most k times.

Return the minimum total cost to reach cell (m - 1, n - 1) from (0, 0).

 

Example 1:

Input: grid = [[1,3,3],[2,5,4],[4,3,5]], k = 2

Output: 7

Explanation:

Initially we are at (0, 0) and cost is 0.

Current Position	Move	New Position	Total Cost
(0, 0)	Move Down	(1, 0)	0 + 2 = 2
(1, 0)	Move Right	(1, 1)	2 + 5 = 7
(1, 1)	Teleport to (2, 2)	(2, 2)	7 + 0 = 7
The minimum cost to reach bottom-right cell is 7.

Example 2:

Input: grid = [[1,2],[2,3],[3,4]], k = 1

Output: 9

Explanation:

Initially we are at (0, 0) and cost is 0.

Current Position	Move	New Position	Total Cost
(0, 0)	Move Down	(1, 0)	0 + 2 = 2
(1, 0)	Move Right	(1, 1)	2 + 3 = 5
(1, 1)	Move Down	(2, 1)	5 + 4 = 9
The minimum cost to reach bottom-right cell is 9.

 

Constraints:

2 <= m, n <= 80
m == grid.length
n == grid[i].length
0 <= grid[i][j] <= 104
0 <= k <= 10


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:DP

Method:

min cost: teleport at the largest value cell
DP[t][i][j]: cost at position (i,j) using t times of transportation

initialize: first row and column, at t = 0, just keep adding from the prev cell
            dp[0][0][0] = 0

1. normal move: DP[t][i][j] = min(DP[t][i-1][j],DP[t][i][j-1]) + grid[i][j]

2. Teleportation: if grid[new_i][new_j] <= grid[i][j]]

    首先，pre compute一个min_prev的array，
    Each layer t already includes the "best of the best" from the layer before it.
    Layer 1 contains the best results using at most 1 teleport (including 0 teleports).
    Layer 2 contains the best results using at most 2 teleports (including 1 and 0 teleports).

    To do this efficiently, find the minimum cost in layer t-1 among all cells whose value is >= V. 
    Let min prev[V] = min(dp[t - 1][r][c]) for all (r, c) where grid[r]|c] ≥ V

    Initial dp[t][i][j] = min(dp[t - 1][i][j], min_prev[grid[i][j]]).

        dp[i][j] is the cost to reach that cell in the previous layer (using t-1 teleports).
        min_at_val[...] is the cost to reach that cell by using exactly one more teleport from the previous layer.
        By taking the min of these two, you ensure that if teleporting actually increased your cost, you just keep the better value from the previous layer.



    然后，After a teleport, you can still move right or down within the same layer t.
        Update dp[t][i|[j] = min(dp[t|[i][j], grid[i|[j] + min(dp[t|[i — 1][j], dp[t|[i][j - 1]))


Stats:

	- 
	- 
/** min cost: teleport at the largest value cell
 DP[t][i][j]: cost at position (i,j) using t times of transportation
 initialize: first row and column, at t = 0, just keep adding from the prev cell
            dp[0][0][0] = 0

1) normal move: DP[t][i][j] = min(DP[t][i-1][j],DP[t][i][j-1]) + grid[i][j]

2)Teleportation: if grid[new_i][new_j] <= grid[i][j]]

    首先，pre compute一个min_prev的array，
    Each layer t already includes the "best of the best" from the layer before it.
    Layer 1 contains the best results using at most 1 teleport (including 0 teleports).
    Layer 2 contains the best results using at most 2 teleports (including 1 and 0 teleports).

    To do this efficiently, find the minimum cost in layer t-1 among all cells whose value is >= V. 
    Let min prev[V] = min(dp[t - 1][r][c]) for all (r, c) where grid[r]|c] ≥ V

    Initial dp[t][i][j] = min(dp[t - 1][i][j], min_prev[grid[i][j]]).

        dp[i][j] is the cost to reach that cell in the previous layer (using t-1 teleports).
        min_at_val[...] is the cost to reach that cell by using exactly one more teleport from the previous layer.
        By taking the min of these two, you ensure that if teleporting actually increased your cost, you just keep the better value from the previous layer.



    然后，After a teleport, you can still move right or down within the same layer t.
        Update dp[t][i|[j] = min(dp[t|[i][j], grid[i|[j] + min(dp[t|[i — 1][j], dp[t|[i][j - 1]))

 */
class Solution {
    public int minCost(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        long[][] dp = new long[m][n];
        for (long[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        
        int maxVal = 0;
        for(int[] row : grid) {
            for(int val : row) maxVal = Math.max(maxVal, val);
        }

        // Base Case: 0 teleports
        dp[0][0] = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + grid[i][j]);
                if (j > 0) dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + grid[i][j]);
            }
        }
        
        long ans = dp[m-1][n-1];
        
        // with teleportation
        for (int t = 1; t <= k; t++) {
            long[] minAtVal = new long[maxVal+1]; 
            Arrays.fill(minAtVal, Long.MAX_VALUE  / 2);

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    minAtVal[grid[i][j]] = Math.min(minAtVal[grid[i][j]], dp[i][j]);
                }
            }

            
            // Suffix minimum: minAtVal[v] = min cost of any cell with grid value >= v
            for (int v = maxVal - 1; v >= 0; v--) {
                minAtVal[v] = Math.min(minAtVal[v], minAtVal[v + 1]);
            }


            long[][] nextDp = new long[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    nextDp[i][j] = Math.min(dp[i][j], minAtVal[grid[i][j]]);
                }
            }
            
            // Standard move relaxation in current layer
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > 0) nextDp[i][j] = Math.min(nextDp[i][j], nextDp[i-1][j] + grid[i][j]);
                    if (j > 0) nextDp[i][j] = Math.min(nextDp[i][j], nextDp[i][j-1] + grid[i][j]);
                }
            }
            
            dp = nextDp;
            ans = Math.min(ans, dp[m-1][n-1]);
        }
        return (int) ans;
    }
}

