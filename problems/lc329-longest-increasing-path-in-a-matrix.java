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

	- Time complexity: O(n * m) where (n, m) are dimensions of the given matrix.
		Since, if we ve already run starting from a grid-cell, we would be simply returning 
		the value in subsequent calls.
	- Runtime: 7 ms, faster than 93.24% of Java online submissions for Longest Increasing Path in a Matrix.
	- Memory Usage: 39.4 MB, less than 97.96%


	public class Solution {
		int[][] dis = {{1,0},{-1,0},{0,1},{0,-1}};

		public int longestIncreasingPath(int[][] matrix) {
		  if(matrix.length == 0 ){
		        return 0;
		  }
		  int[][] state = new int[matrix.length][matrix[0].length];
		  int res = 0;
		  for(int i = 0; i < matrix.length; i++){
		      for(int j = 0; j < matrix[0].length; j++){
		         res = Math.max(res,dfs(i,j,matrix,state));
		      }
		  }
		  return res;
		}
		public int dfs(int i, int j, int[][] matrix, int[][] state){
		      if(state[i][j] > 0) return state[i][j];
		      int max = 0;
		      for(int m = 0; m < dis.length; m++){
		          if(i + dis[m][0] >= 0 && i + dis[m][0] < matrix.length && j + dis[m][1] >= 0 
		          	 && j + dis[m][1] < matrix[0].length 
		          	 && matrix[i+dis[m][0]][j+dis[m][1]] > matrix[i][j]){

		              max = Math.max(max,dfs(i + dis[m][0],j + dis[m][1],matrix,state));
		          }
		      }
		      state[i][j] = 1 + max;
		      return state[i][j];
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



