980. Unique Paths III - Hard

On a 2-dimensional grid, there are 4 types of squares:

	1. 1 represents the starting square.  There is exactly one starting square.
	2. 2 represents the ending square.  There is exactly one ending square.
	3. 0 represents empty squares we can walk over.
	4. -1 represents obstacles that we cannot walk over.

Return the number of 4-directional walks from the starting square to the ending square, that walk 
over every non-obstacle square exactly once.

 

Example 1:

Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)


Example 2:
Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
Explanation: We have the following four paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)


Example 3:
Input: [[0,1],[2,0]]
Output: 0
Explanation: 
There is no path that walks over every empty square exactly once.
Note that the starting and ending square can be anywhere in the grid.
 

Note:

1 <= grid.length * grid[0].length <= 20


******************************************************
key:
	- DFS, backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	First find out where the start and the end is. Also We need to know the number of empty cells.
	-	We we try to explore a cell, it will change 0 to -2 and do a dfs in 4 direction.
	-	If we hit the target and pass all empty cells, increment the result.


Stats:

	- Time complexity is as good as dp, but it take less space and easier to implement.
	- Time: N^2 * 2^N, where N is grid.length * grid[0].length
	- Space: N * 2^N


class Solution{

	// start coordinates: (sx, sy)
	// end coordinates: (ex, ey)
	// empty indicates whether we have go through every cell
	int res = 0, empty = 1, sx, sy, ex, ey;

	// main function
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) 
                	empty++;

                // start
                else if (grid[i][j] == 1) {
                    sx = i;
                    sy = j;
                // end
                } else if (grid[i][j] == 2) {
                    ex = i;
                    ey = j;
                }
            }
        }
        dfs(grid, sx, sy);
        return res;
    }

    public void dfs(int[][] grid, int x, int y) {

    	// if out of the range or at '-1' which is the impediments
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] < 0)
            return;

        // reach end position
        if (x == ex && y == ey) {
            if (empty == 0) 
            	res++;
            return;
        }



        grid[x][y] = -2;
        empty--;
        dfs(grid, x + 1, y);
        dfs(grid, x - 1, y);
        dfs(grid, x, y + 1);
        dfs(grid, x, y - 1);
        grid[x][y] = 0;
        empty++;
    }
}

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



