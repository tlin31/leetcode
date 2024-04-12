64. Minimum Path Sum - Medium

Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right 
which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

Example:
Input:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
Output: 7
Explanation: Because the path 1→3→1→1→1 minimizes the sum.


******************************************************
key:
	- dp
		base case:
			top row & most left column sum = add up all.
		formula:
			dp[i][j] = min (dp[i-1][j], dp[i][j-1])
	- edge case:
		1) if empty, return 0.
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- iterative/backtrack
	- 这道题的话，把递归 getAns 定义为，输出 （x，y）到 （m，n ） 的路径和最小是多少。
	- 同样如果记做 dp[x][y]。这样的话， dp[x][y] = Min（dp[x][y+1] + dp[x+1][y]）+ grid[x][y]。
	- 很好理解，就是当前点的右边和下边取一个和较小的，然后加上当前点的权值。

stats:

	- 
	- 

	public int minPathSum(int[][] grid) {
	    int m = grid.length;
	    int n = grid[0].length;
	    HashMap<String, Integer> visited = new HashMap<>();
	    return getAns(0, 0, m - 1, n - 1, 0, visited, grid);
	}

	// x & y is current index, m &n is target coordinate
	private int getAns(int x, int y, int m, int n, int num, HashMap<String, Integer> visited, 
						int[][] grid) {

	    // 到了终点，返回终点的权值
	    if (x == m && y == n) {
	        return grid[m][n];
	    }

	    int n1 = Integer.MAX_VALUE;
	    int n2 = Integer.MAX_VALUE;

	    // 往右走
	    String key = x + 1 + "@" + y;
	    if (!visited.containsKey(key)) {
	        if (x + 1 <= m) {
	            n1 = getAns(x + 1, y, m, n, num, visited, grid);
	        }
	    } else {
	        n1 = visited.get(key);
	    }

	    // 往下走
	    key = x + "@" + (y + 1);
	    if (!visited.containsKey(key)) { 
	        if (y + 1 <= n) {
	            n2 = getAns(x, y + 1, m, n, num, visited, grid);
	        }
	    } else {
	        n2 = visited.get(key);
	    }

	    // 将当前点加入 visited 中
	    key = x + "@" + y;
	    visited.put(key, Math.min(n1, n2) + grid[x][y]);

	    //返回两个之间较小的，并且加上当前权值
	    return Math.min(n1, n2) + grid[x][y];
	}



=======================================================================================================
method 2:

method:

	- dp
	- 

stats:

	- 时间复杂度：O（m * n）。
	- 空间复杂度：O（1）。
	- Runtime: 2 ms, faster than 90.17% of Java online submissions for Minimum Path Sum.
	- Memory Usage: 41.7 MB, less than 87.84% 


public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;

    //由于第一行和第一列不能用我们的递推式，所以单独更新第一行的权值
    for (int i = 1; i < n; i++) {
        grid[0][i] = grid[0][i - 1] + grid[0][i];
    }

    //更新第一列的权值
    for (int i = 1; i < m; i++) {
        grid[i][0] = grid[i - 1][0] + grid[i][0];
    }

    //利用递推式更新其它的
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            grid[i][j] = Math.min(grid[i][j - 1], grid[i - 1][j]) + grid[i][j];

        }
    }
    
    return grid[m - 1][n - 1];
}


---------------------------------------
optimize
	- use only 2 rows

	int minPathSum(vector < vector < int >> & grid) {
	    int m = grid.size();
	    int n = grid[0].size();
	    vector < int > pre(m, grid[0][0]);
	    vector < int > cur(m, 0);
	    for (int i = 1; i < m; i++)
	        pre[i] = pre[i - 1] + grid[i][0];
	    for (int j = 1; j < n; j++) {
	    	//initialize current!
	        cur[0] = pre[0] + grid[0][j];
	        for (int i = 1; i < m; i++)
	            cur[i] = min(cur[i - 1], pre[i]) + grid[i][j];
	        swap(pre, cur); 
	    }
	    return pre[m - 1];
	}


-----------------------------------------
optimize

int minPathSum(vector < vector < int >> & grid) {
    int m = grid.size();	//row
    int n = grid[0].size();	//column
    vector < int > cur(m, grid[0][0]);
    for (int i = 1; i < m; i++)
        cur[i] = cur[i - 1] + grid[i][0];
    for (int j = 1; j < n; j++) {
        cur[0] += grid[0][j];
        for (int i = 1; i < m; i++)
            cur[i] = min(cur[i - 1], cur[i]) + grid[i][j];
    }
    return cur[m - 1];
}




