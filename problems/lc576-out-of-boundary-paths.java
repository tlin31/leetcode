576. Out of Boundary Paths - Medium


There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball, you can move 
the ball to adjacent cell or cross the grid boundary in four directions (up, down, left, right). 
However, you can at most move N times. Find out the number of paths to move the ball out of grid 
boundary. The answer may be very large, return it after mod 109 + 7.

 

Example 1:

Input: m = 2, n = 2, N = 2, i = 0, j = 0
Output: 6
Explanation:

Example 2:

Input: m = 1, n = 3, N = 3, i = 0, j = 1
Output: 12
Explanation:

 

Note:

Once you move the ball out of boundary, you cannot move it back.
The length and height of the grid is in range [1,50].
N is in range [0,50].


******************************************************
key:
	- DFS --> DP
	- edge case:
		1) invalid ball position ( i & j not in m&n)
		2)

******************************************************



=======================================================================================================
Method 1: Brute force --> DFS with memo


Stats:

	- Time: O(n^4) --> O(mnN). We need to fill the memo array once with dimensions m×n×N. 
	        m, n refer to the number of rows and columns of the given grid respectively. 
	        N refers to the total number of allowed moves.

	- Space: O(n) = depth of the recursion tree --> O(mnN) = memo array


Method:

	-	
	-	

class Solution {
  public int findPaths(int m, int n, int N, int i, int j) {
    // reach the boarder, just take 1 more step
    if (i == m || j == n || i < 0 || j < 0) 
    	return 1;

    // can't form a path
    if (N == 0) 
    	return 0;


    return findPaths(m, n, N - 1, i - 1, j)
        + findPaths(m, n, N - 1, i + 1, j)
        + findPaths(m, n, N - 1, i, j - 1)
        + findPaths(m, n, N - 1, i, j + 1);
  }
}

----------
class Solution {
  int M = 1000000007;

  public int findPaths(int m, int n, int N, int i, int j) {
    int[][][] memo = new int[m][n][N + 1];
    for (int[][] l : memo) 
    	for (int[] sl : l) 
    		Arrays.fill(sl, -1);

    return findPaths(m, n, N, i, j, memo);
  }

  public int findPaths(int m, int n, int N, int i, int j, int[][][] memo) {
    if (i == m || j == n || i < 0 || j < 0) return 1;
    if (N == 0) return 0;
    if (memo[i][j][N] >= 0) return memo[i][j][N];

    memo[i][j][N] = (
        (findPaths(m, n, N - 1, i - 1, j, memo) + findPaths(m, n, N - 1, i + 1, j, memo)) % M +
        (findPaths(m, n, N - 1, i, j - 1, memo) + findPaths(m, n, N - 1, i, j + 1, memo)) % M
    ) % M;


    return memo[i][j][N];
  }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:

	-	
	-	

DP[i][j][k] stands for how many possible ways to walk into cell j,k in step i, 
DP[i][j][k] only depends on DP[i - 1][j][k], so we can compress 3 dimensional dp array to 2 dimensional.

public class Solution {
    public int findPaths(int m, int n, int N, int i, int j) {
        if (N <= 0) return 0;
        
        final int MOD = 1000000007;
        int[][] count = new int[m][n];
        count[i][j] = 1;
        int result = 0;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int step = 0; step < N; step++) {
            int[][] temp = new int[m][n];
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    for (int[] d : dirs) {
                        int nr = r + d[0];
                        int nc = c + d[1];

                        // update result
                        if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                            result = (result + count[r][c]) % MOD;
                        }
                        else {
                            temp[nr][nc] = (temp[nr][nc] + count[r][c]) % MOD;
                        }
                    }
                }
            }
            count = temp;
        }
        
        return result;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
def findPaths(self, R, C, N, sr, sc):
    MOD = 10**9 + 7
    nxt = [[0] * C for _ in xrange(R)]
    nxt[sr][sc] = 1
    
    ans = 0
    for time in xrange(N):
        cur = nxt
        nxt = [[0] * C for _ in xrange(R)]
        for r, row in enumerate(cur):
            for c, val in enumerate(row):
                for nr, nc in ((r-1, c), (r+1, c), (r, c-1), (r, c+1)):
                    if 0 <= nr < R and 0 <= nc < C:
                        nxt[nr][nc] += val
                        nxt[nr][nc] %= MOD
                    else:
                        ans += val
                        ans %= MOD
        
    return ans

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

