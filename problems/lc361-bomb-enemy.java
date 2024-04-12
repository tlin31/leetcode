361. Bomb Enemy - Medium

Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), 
return the maximum enemies you can kill using one bomb.

The bomb kills all the enemies in the same row and column from the planted point until it hits the wall
since the wall is too strong to be destroyed.

Note: You can only put the bomb at an empty cell.

Example:

Input: [["0","E","0","0"],
		["E","0","W","E"],
		["0","E","0","0"]]

Output: 3 
Explanation: For the given grid,

0 E 0 0 
E B W E 
0 E 0 0

Placing a bomb at (1,1) kills 3 enemies.


******************************************************
key:
	- DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(mn) time, O(n) space.
	- 


Method:

	-	
	-	Walk through the matrix. At the start of each non-wall-streak (row-wise or column-wise), count 
	    the number of hits in that streak and remember it. 


public class Solution {
    public int maxKilledEnemies(char[][] grid) {
        int m = grid.length;
        int n = m != 0 ? grid[0].length : 0;
        int result = 0;
        int rowhits = 0;
        int[] colhits = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // A. If left is wall or at the begining of the row, count all enemies to the right
                // need to reset row
                if (j == 0 || grid[i][j - 1] == 'W') {
                    rowhits = 0;
                    for (int k = j; k < n && grid[i][k] != 'W'; k++)
                        rowhits += grid[i][k] == 'E' ? 1 : 0;
                }

                // B. If above is wall, check downwards for enimes
                if (i == 0 || grid[i - 1][j] == 'W') {
                    colhits[j] = 0;
                    for (int k = i; k < m && grid[k][j] != 'W'; k++)
                        colhits[j] += grid[k][j] == 'E' ? 1 : 0;
                }

                // C. If this grid is empty, update result
                if (grid[i][j] == '0')
                    result = Math.max(result, rowhits + colhits[j]);
            }
        }
        return result;
    }
}

ex.

input: 
[["0","E","0","0"], 
 ["0","E","0","0"],
 ["E","0","W","E"],
 ["0","E","0","0"]]

output:
at i = 0, j = 0, rowhits = 1, colhits = [1, 0, 0, 0]
at i = 0, j = 1, rowhits = 1, colhits = [1, 3, 0, 0]
at i = 0, j = 2, rowhits = 1, colhits = [1, 3, 0, 0]
at i = 0, j = 3, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 1, j = 0, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 1, j = 1, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 1, j = 2, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 1, j = 3, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 2, j = 0, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 2, j = 1, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 2, j = 2, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 2, j = 3, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 3, j = 0, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 3, j = 1, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 3, j = 2, rowhits = 1, colhits = [1, 3, 0, 1]
at i = 3, j = 3, rowhits = 1, colhits = [1, 3, 0, 1]


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

public class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) return 0;
        int m = grid.length, n = grid[0].length;
        int[][] count = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            int tmp = 0;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'E') tmp++;
                if (grid[i][j] == 'W') tmp = 0;
                if (grid[i][j] == '0') {
                    count[i][j] += tmp;
                    res = Math.max(count[i][j], res);
                }
            }
            tmp = 0;
            for (int j = n-1; j >= 0; j--) {
                if (grid[i][j] == 'E') tmp++;
                if (grid[i][j] == 'W') tmp = 0;
                if (grid[i][j] == '0') {
                    count[i][j] += tmp;
                    res = Math.max(count[i][j], res);
                }
            }
        }
        for (int j = 0; j < n; j++) {
            int tmp = 0;
            for (int i = 0; i < m; i++) {
                if (grid[i][j] == 'E') tmp++;
                if (grid[i][j] == 'W') tmp = 0;
                if (grid[i][j] == '0') {
                    count[i][j] += tmp;
                    res = Math.max(count[i][j], res);
                }
            }
            tmp = 0;
            for (int i = m-1; i >= 0; i--) {
                if (grid[i][j] == 'E') tmp++;
                if (grid[i][j] == 'W') tmp = 0;
                if (grid[i][j] == '0') {
                    count[i][j] += tmp;
                    res = Math.max(count[i][j], res);
                }
            }
        }
        return res;
    }
}
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



