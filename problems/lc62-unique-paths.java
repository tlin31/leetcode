62. Unique Paths - Medium

A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the 
bottom-right corner of the grid (marked 'Finish' in the diagram below).

Q: number of unique paths?


Note: m and n will be at most 100.

Example 1:
Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right

Example 2:
Input: m = 7, n = 3
Output: 28

******************************************************
key:
	- method 1: dp
		- base case dp[i][0] = 1, dp[0][j] = 1
		- dp[i][j] = dp[i-1][j] + dp[i][j-1]

	- backtrack
	- edge case:
		1) m or n == 0, return 0
		2)other case will be covered in dp s base case

******************************************************



=======================================================================================================
method 1: backtrack + memo

method:

	- backtrack

我们可以优化下，问题出在当我们求点 （x，y）到（m - 1 , n - 1） 点的走法的时候，递归求了点 （x，y）点右边的点 
(x + 1，0）到（m - 1 , n - 1）的走法和（x，y）下边的点（x，y + 1）到（m - 1 , n - 1）的走法。而没有考虑到
(x + 1，0）到（m - 1 , n - 1）的走法和点（x，y + 1）到（m - 1 , n - 1）的走法是否是之前已经求过了。事实上，
很多点求的时候后边的的点已经求过了，所以再进行递归是没有必要的。基于此，我们可以用 visited 保存已经求过的点。

	public int uniquePaths(int m, int n) {
	    HashMap<String, Integer> visited = new HashMap<>();
	    return getAns(0, 0, m - 1, n - 1, 0, visited); 

	}
	private int getAns(int x, int y, int m, int n, int num, HashMap<String, Integer> visited) {
	    if (x == m && y == n) {
	        return 1;
	    }
	    int n1 = 0;
	    int n2 = 0;

	    // next step going right
	    String key = x + 1 + "@" + y;
	    
	    //判断当前点是否已经求过了
	    if (!visited.containsKey(key)) {
	        if (x + 1 <= m) {
	            n1 = getAns(x + 1, y, m, n, num, visited);
	        }
	    } 

	    else {
	        n1 = visited.get(key);
	    }

	    key = x + "@" + (y + 1);
	    if (!visited.containsKey(key)) {
	        if (y + 1 <= n) {
	            n2 = getAns(x, y + 1, m, n, num, visited);
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
	- two possibilities:
		1. It arrives at that point from above (moving down to that point);
		2. It arrives at that point from left (moving right to that point).
	- --> suppose the number of paths to arrive at a point (i, j) is denoted as P[i][j] , it is 
		easily concluded that P[i][j] = P[i - 1][j] + P[i][j - 1] .
	- The boundary conditions of the above equation occur at the leftmost column (P[i] [j - 1] does 
		not exist) and the uppermost row (P[i - 1][j] does not exist).
	- These conditions can be handled by initialization (pre-processing) --- initialize P[0][j] = 1, 
		P[i][0] = 1 for all valid i, j . Note the initial value is 1 instead of 0 !

stats:

	- optimize in space, do not need a table of m * n, can just use 2 column.
	- O(min(m, n)) space complexity.

	int uniquePaths(int m, int n) {
	    if (m > n) return uniquePaths(n, m);
	    vector < int > pre(m, 1);
	    vector < int > cur(m, 1);
	    for (int j = 1; j < n; j++) {
	        for (int i = 1; i < m; i++)
	            cur[i] = cur[i - 1] + pre[i];
	        swap(pre, cur);
	    }
	    return pre[m - 1];
	}

---------------------------------
method 2.2

	- !!!! keeping two columns is used to recover pre[i] , which is just cur[i] before its update. 
	- So use one array is just enough. 

	public int uniquePaths(int m, int n) {
	    if (m > n) return uniquePaths(n, m);
	    int[] cur= new int[m];
        for (int i = 0; i < m; i++) {
            cur[i] = 1;
        }
	    for (int j = 1; j < n; j++)
	        for (int i = 1; i < m; i++)
	            cur[i] += cur[i - 1];
	    return cur[m - 1];
	}



-----------------------------------
stats:
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Unique Paths.
	- Memory Usage: 32.9 MB, less than 5.10% 
	public int uniquePaths(int m, int n) {
	    int[] dp = new int[n];
	    for (int i = 0; i < n; i++) {
	        dp[i] = 1;
	    }

	    for (int i = 1; i < m; i++) {
	        for (int j = 1; j < n; j++) {
	            dp[j] = dp[j] + dp[j - 1];
	        }
	    }
	    return dp[(n - 1)];
	}


- BOTTOM-TOP (the same as the other way)
	public int uniquePaths(int m, int n) {
	    int[] dp = new int[m];

	    //初始化最后一列
	    for (int i = 0; i < m; i++) {
	        dp[i] = 1;
	    }

	    //从右向左更新所有列,最后一行永远是 1，所以从倒数第 2 行开始
	    for (int i = n - 2; i >= 0; i--) {
	        
	        //从下向上更新所有行
	        for (int j = m - 2; j >= 0; j--) {
	            //右边的和下边的更新当前元素
	            dp[j] = dp[j] + dp[j + 1];
	        }
	    }
	    return dp[0];
	}



=======================================================================================================
method 3:

method:
	- binomial, calculate
	- we need to do n + m - 2 movements : m - 1 down, n - 1 right, because we start from cell (1, 1).
	- the path  is the sequence of movements( go down / go right), therefore we can say that two paths 
		are different when there is i-th (1 .. m + n - 2) movement in path1 differ i-th movement in 
		path2.
	- Thus, we can choose (n - 1) movements(number of steps to the right) from (m + n - 2), and rest 
		(m - 1) is (number of steps down).
	- count of different paths are all combinations (n - 1) movements from (m + n-2).

stats:

	- 时间复杂度：O（m）。
	- 空间复杂度：O（1）。


        int uniquePaths(int m, int n) {
            int N = n + m - 2; // how much steps we need to do
            int k = m - 1; // number of steps that need to go down
            double res = 1;

            // here we calculate the total possible path number
            // Combination(N, k) = n! / (k!(n - k)!)
            // reduce the numerator and denominator and get
            // C = ( (n - k + 1) * (n - k + 2) * ... * n ) / k!
            for (int i = 1; i <= k; i++)
                res = res * (N - k + i) / i;
            return (int) res;
        }


