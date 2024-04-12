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



