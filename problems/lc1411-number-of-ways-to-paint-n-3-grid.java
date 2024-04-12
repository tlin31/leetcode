1411.Number of Ways to Paint N × 3 Grid - Hard


You have a grid of size n x 3 and you want to paint each cell of the grid with exactly one of the 
three colours: Red, Yellow or Green while making sure that no two adjacent cells have the same 
colour (i.e no two cells that share vertical or horizontal sides have the same colour).

You are given n the number of rows of the grid.

Return the number of ways you can paint this grid. As the answer may grow large, the answer must 
be computed modulo 10^9 + 7.

 

Example 1:

Input: n = 1
Output: 12
Explanation: There are 12 possible way to paint the grid as shown:

Example 2:

Input: n = 2
Output: 54
Example 3:

Input: n = 3
Output: 246
Example 4:

Input: n = 7
Output: 106494
Example 5:

Input: n = 5000
Output: 30228214
 

Constraints:

n == grid.length
grid[i].length == 3
1 <= n <= 5000


******************************************************
key:
	- DP + find pattern OR DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time O(N), Space O(1)
	- 


Method:

	Two pattern for each row, 121 and 123.
		121, the first color same as the third in a row.
		123, one row has three different colors.

	We consider the state of the first row,
		pattern 121: 121, 131, 212, 232, 313, 323.
		pattern 123: 123, 132, 213, 231, 312, 321.
		So we initialize a121 = 6, a123 = 6.

	We consider the next possible for each pattern (need to avoid have same color with the row above):
		Patter 121 can be followed by: 212, 213, 232, 312, 313
		Patter 123 can be followed by: 212, 231, 312, 232

	121 => three 121 pattern, two 123 pattern
	123 => two 121 pattern, two 123 pattern

	So we can write this dynamic programming transform equation:
		b121 = a121 * 3 + a123 * 2
		b123 = a121 * 2 + a123 * 2

	We calculate the result iteratively and finally return the sum of both pattern a121 + a123



    public int numOfWays(int n) {
        long a121 = 6, a123 = 6, b121, b123, mod = (long)1e9 + 7;
        for (int i = 1; i < n; ++i) {
            b121 = a121 * 3 + a123 * 2;
            b123 = a121 * 2 + a123 * 2;
            a121 = b121 % mod;
            a123 = b123 % mod;
        }
        return (int)((a121 + a123) % mod);
    }



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
    def numOfWays(self, n):
        a121, a123, mod = 6, 6, 10**9 + 7
        for i in xrange(n - 1):
            a121, a123 = a121 * 3 + a123 * 2, a121 * 2 + a123 * 2
        return (a121 + a123) % mod


=======================================================================================================
method 2: DFS 

Stats:

	- 
	- 


Method:

	-	
	-	




    public int numOfWays(int n) {
        int[][][][] dp = new int[n+1][4][4][4]; 
        return dfs(n, 0, 0, 0, dp);
    }

    // dfs(n, a0, b0, c0) is the number of ways to color the first n rows of the grid 
    //      keeping in mind that the previous row (n + 1) has colors a0, b0 and c0

    int dfs(int n, int a0, int b0, int c0, int[][][][] dp) {
        if (n == 0) return 1;
        if (dp[n][a0][b0][c0] != 0) 
        	return dp[n][a0][b0][c0];
        int ans = 0;
        int[] colors = new int[]{1, 2, 3}; // Red: 1, Yellow: 2, Green: 3
        for (int a : colors) {
            if (a == a0) continue; // Check if the same color with the below neighbor

            for (int b : colors) {
            	// Check if the same color with the below neighbor or the left neighbor
                if (b == b0 || b == a) continue; 
                
                for (int c : colors) {
                    if (c == c0 || c == b) continue;
                    ans += dfs(n - 1, a, b, c, dp);
                    ans %= 1000_000_007;
                }
            }
        }
        return dp[n][a0][b0][c0] = ans;
    }





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

