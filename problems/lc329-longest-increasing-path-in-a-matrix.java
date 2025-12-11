329. Longest Increasing Path in a Matrix - Hard

Given an integer matrix, find the length of the longest increasing path.

From each cell, you can either move to four directions: left, right, up or down. 
You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

Example 1:

Input: nums = 
[
  [9,9,4],
  [6,6,8],
  [2,1,1]
] 
Output: 4 
Explanation: The longest increasing path is [1, 2, 6, 9].

Example 2:

Input: nums = 
[
  [3,4,5],
  [3,2,6],
  [2,2,1]
] 
Output: 4 
Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.


******************************************************
key:
	- !! high fre
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- DFS + DP
	- DFS + Memoization 
 	- Traverse all points in matrix, use every point as starting point to do dfs traversal. DFS 
 		function returns max increasing path after comparing four max return distance from 4 directions. 
  	@param cache: cache[i][j] represents longest increasing path starts from point matrix[i][j]
  	@param prev: previous value used by DFS traversal, to compare whether current value is greater 
  		than previous value

	- Compare every 4 direction and skip cells that are out of boundary or smaller
	- Get matrix max from every cell max
	- Use matrix[x][y] <= matrix[i][j] so we dont need a visited[m][n] array
	- memorization!!! cache the distance because it is highly possible to revisit a cell

stats:

每个 cell 的 DFS 最多执行一次,每次 DFS 访问 4 个方向

时间复杂度：O(m·n)
空间复杂度：O(m·n)（递归栈 + memo）

class Solution {

    // directions: up, down, left, right
    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};
    
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] memo = new int[m][n];

        int ans = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dfs(matrix, i, j, memo));
            }
        }

        return ans;
    }

    private int dfs(int[][] mat, int i, int j, int[][] memo) {
    		//如果已经visit过这个点
        if (memo[i][j] != 0) return memo[i][j];

        int best = 1; // minimum path length is 1 (itself)

        for (int[] d : DIRS) {
            int ni = i + d[0];
            int nj = j + d[1];

            // bounds + strictly increasing condition
            if (ni >= 0 && nj >= 0 && ni < mat.length && nj < mat[0].length 
                && mat[ni][nj] > mat[i][j]) {

                best = Math.max(best, 1 + dfs(mat, ni, nj, memo));
            }
        }

        memo[i][j] = best;
        return best;
    }
}

=======================================================================================================
method 2:

method:

	-  topological sort
	- I think the core idea is treat the graph as topology sorted, and each time we delete from 
	  the end (which means we will delete all nodes within the same level), and increment count. 
	  The reason behind is: we can choose one (and only one) node from current level for next step.

	  So in this way we get the longest path from end to start.

stats:

	- O(v^2)
	- Runtime: 66 ms, faster than 7.74% of Java online submissions for Longest Increasing Path in 
	  a Matrix.
	- Memory Usage: 41.6 MB, less than 24.49%


	public static class Point{
	    int x;
	    int y;
	    public Point(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	}

	public static int longestIncreasingPath(int[][] matrix) {
	    if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0)
	        return 0;
	    int n = matrix.length, m = matrix[0].length, count = m * n, ans = 0;
	    while (count > 0) {
	        HashSet<Point> remove = new HashSet<Point>();
	        
	        // each round, remove the peak number.
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < m; j++) {
	                if (matrix[i][j] == Integer.MIN_VALUE)
	                    continue;
	                boolean up = (i == 0 || matrix[i][j] >= matrix[i - 1][j]);
	                boolean bottom = (i == n - 1 || matrix[i][j] >= matrix[i + 1][j]);
	                boolean left = (j == 0 || matrix[i][j] >= matrix[i][j - 1]);
	                boolean right = (j == m - 1 || matrix[i][j] >= matrix[i][j + 1]);
	                if (up && bottom && left && right)
	                    remove.add(new Point(i, j));
	            }
	        }
	        for (Point point : remove) {
	            matrix[point.x][point.y] = Integer.MIN_VALUE;
	            count--;
	        }
	        ans++;
	    }
	    return ans;
	}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



