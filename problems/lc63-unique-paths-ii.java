63. Unique Paths II - Medium

A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach 
the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?

An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Note: m and n will be at most 100.

Example 1:
Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right

******************************************************
key:
	- dp & back track
	- different from #62, now it has obstacles, add an if.
	- edge case:
		1) if empty, return 0 or 1?
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- backtrack + memo
	- 

stats:

	- 
	- 

	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
	    int m = obstacleGrid.length;
	    int n = obstacleGrid[0].length;
	    HashMap<String, Integer> visited = new HashMap<>();

	    //起点是障碍，直接返回 0 
	    if (obstacleGrid[0][0] == 1)
	        return 0;

	    return getAns(0, 0, m - 1, n - 1, 0, visited, obstacleGrid);
	}

	private int getAns(int x, int y, int m, int n, int num, HashMap<String, Integer> visited, 
						int[][] obstacleGrid) {

	    if (x == m && y == n) {
	        return 1;
	    }

	    int n1 = 0;
	    int n2 = 0;

	    // step right
	    String key = x + 1 + "@" + y;
	    if (!visited.containsKey(key)) {
	        // check within range & not an obstacle
	        if (x + 1 <= m && obstacleGrid[x + 1][y] == 0) {
	            n1 = getAns(x + 1, y, m, n, num, visited, obstacleGrid); 
	        }
	    } else {
	        n1 = visited.get(key);
	    }

	    // step down
	    key = x + "@" + (y + 1);
	    if (!visited.containsKey(key)) {
	        if (y + 1 <= n && obstacleGrid[x][y + 1] == 0) {
	            n2 = getAns(x, y + 1, m, n, num, visited, obstacleGrid); 
	        }
	    } else {
	        n2 = visited.get(key);
	    }

	    //将当前点加入 visited 中
	    key = x + "@" + y;
	    visited.put(key, n1+n2);
	    return n1 + n2;
	}
=======================================================================================================
method 2:

method:

	- dp
	- use 2d matrix to store


public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    if(obstacleGrid==null||obstacleGrid.length==0)
        return 0;
 
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
 
    if(obstacleGrid[0][0]==1||obstacleGrid[m-1][n-1]==1) 
        return 0;
 
 
    int[][] dp = new int[m][n];
    dp[0][0]=1;
 
    //left column
    for(int i=1; i<m; i++){
        if(obstacleGrid[i][0]==1){
            dp[i][0] = 0;
        }else{
            dp[i][0] = dp[i-1][0];
        }
    }
 
    //top row
    for(int i=1; i<n; i++){
        if(obstacleGrid[0][i]==1){
            dp[0][i] = 0;
        }else{
            dp[0][i] = dp[0][i-1];
        }
    }
 
    //fill up cells inside
    for(int i=1; i<m; i++){
        for(int j=1; j<n; j++){
            if(obstacleGrid[i][j]==1){
                dp[i][j]=0;
            }else{
                dp[i][j]=dp[i-1][j]+dp[i][j-1];
            }
 
        }
    }
 
    return dp[m-1][n-1];
}

=======================================================================================================
method 3:

method:

	- dp
	- since only goes to right!
	- process row by row from top to bottom, optimize space

stats:

	- 时间复杂度：O（m * n）。
	- 空间复杂度：O（n）。
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Paths II.
	- Memory Usage: 39.1 MB, less than 66.15%


	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
	    int width = obstacleGrid[0].length;
	    int[]  dp = new int[width];
	    dp[0] = 1;
	    for (int[] row: obstacleGrid) {
	        for (int j = 0; j < width; j++) {
	        	// check in the grid, if it's an obstacle, then set it to 0.
	            if (row[j] == 1)
	                dp[j] = 0;
	            else if (j > 0)
	                dp[j] += dp[j - 1];
	        }
	    }
	    return dp[width - 1];
	}
