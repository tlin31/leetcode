1254. Number of Closed Islands - Medium

Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected 
group of 0s and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.

Return the number of closed islands.

 

Example 1:



Input: grid = [[1,1,1,1,1,1,1,0],
               [1,0,0,0,0,1,1,0],
               [1,0,1,0,1,1,1,0],
               [1,0,0,0,0,1,0,1],
               [1,1,1,1,1,1,1,0]]
Output: 2
Explanation: 
Islands in gray are closed because they are completely surrounded by water (group of 1s).

Example 2:



Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
Output: 1

Example 3:

Input: grid = [[1,1,1,1,1,1,1],
               [1,0,0,0,0,0,1],
               [1,0,1,1,1,0,1],
               [1,0,1,0,1,0,1],
               [1,0,1,1,1,0,1],
               [1,0,0,0,0,0,1],
               [1,1,1,1,1,1,1]]
Output: 2
 

Constraints:

1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1

******************************************************
key:
	- NEED TO HAVE 1s in all 3 direction
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- flood fill + dfs
	- 超级厉害！！ flood all 0 (change 0 to 1) if it is close to edge = can not be a closed island
	- graph : https://leetcode.com/problems/number-of-closed-islands/discuss/425150/JavaC%2B%2B-with-picture-Number-of-Enclaves

stats:

	- 
	- 


class Solution {
    int[] directions = new int[] {0, 1, 0, -1, 0};
    
    public int closedIsland(int[][] grid) {

    	// fill all the edges
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == 0 || j == 0 || i == grid.length - 1 || j == grid[i].length - 1) {
                    fill(grid, i, j);
                }
            }
        }

        // count the closed islands, keep count!
        int res = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                if (grid[i][j] == 0) {
                    res++;
                    fill(grid, i, j);
                }
            }
        }

        return res;
    }
    
    private void fill(int[][] g, int x, int y) {
        if (x < 0 || y < 0 || x >= g.length || y >= g[0].length || g[x][y] == 1)
            return;

        // change it to 1
        g[x][y] = 1;

        // dfs the neighbors
        for (int i = 0; i < directions.length - 1; i++) {
            fill(g, x + directions[i], y + directions[i + 1]);
        }
    }
}

=======================================================================================================
method 2:

method:

	- traverse grid, for each 0, do DFS to check if it is a closed island;
	  Within each DFS:
	  	if the current cell is out of the boundary of grid, return 0; 
	  	if the current cell value is positive, return 1; 
	  	OW., it is 0, change it to 2 then recurse to its 4 neighors and return the multification 
             of them.

	- 

stats:

	- Time complexity: O(n*m)
	- Space complexity: O(n*m)




 public int closedIsland(int[][] grid) {
        int cnt = 0;
        for (int i = 0; i < grid.length; ++i)
            for (int j = 0; j < grid[0].length; ++j)
                if (grid[i][j] == 0)
                    cnt += dfs(i, j, grid);
        return cnt;
    }
    
    private int dfs(int i, int j, int[][] g) {
        if (i < 0 || i >= g.length || j < 0 || j >= g[0].length)
            return 0;

        // either = 1 (currently is water) or = 2 (was water) --> reach the boundry
        if (g[i][j] > 0)
            return 1;

        g[i][j] = 2;
        return dfs(i + 1, j, g) * dfs(i - 1, j, g) * dfs(i, j + 1, g) * dfs(i, j - 1, g);
    }


----------------------------------

For each land never seen before, BFS to check if the land extends to boundary. 
If yes, return 0, if not, return 1.


public int closedIsland(int[][] grid) {
        int ans = 0;
        for(int i = 1;i<grid.length - 1;i++){
            for(int j = 1;j<grid[0].length - 1;j++){
                if(grid[i][j] == 0){
                    if(isClosedIsland(grid,i,j)) ans++;
                }
            }
        }
        return ans;
    }

    private boolean isClosedIsland(int[][] grid, int i, int j){
        if(i == 0 || i == grid.length - 1 || j == 0 || j == grid[0].length - 1) return false;
        if(grid[i][j] == 1 || grid[i][j] == 2) return true;
        grid[i][j] = 2;
        int[][] dirs = {{0,1},{1,0},{-1,0},{0,-1}};
        boolean isClosed = true;
        for(int[] dir: dirs){
            int m = i + dir[0];
            int n = j + dir[1];
            if(grid[m][n] == 0){
                isClosed = isClosedIsland(grid,m,n) && isClosed; 
                //if isClosed = isClosed && dfs(grid,m,n), your island will be partially filled, 
               //and even worse, the another part may form a 'island' which is wrong.
            }
        }
        return isClosed;
    }
=======================================================================================================
method 3:

method:
	- bfs
	- For each land never seen before, BFS to check if the land extends to boundary. 
    If yes, return 0, if not, return 1.


	- 

stats:

	- 
	- 

   private static final int[] d = {0, 1, 0, -1, 0};
    private int m, n;
    
    public int closedIsland(int[][] grid) {
        int cnt = 0; 
        m = grid.length; n = m == 0 ? 0 : grid[0].length;
        Set<Integer> seenLand = new HashSet<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0 && seenLand.add(i * n + j)) { // (i, j) is land never seen before.
                    cnt += bfs(i, j, grid, seenLand);
                }
            }
        }    
        return cnt;
    }
    
    private int bfs(int i, int j, int[][] g, Set<Integer> seenLand) {
        int ans = 1;
        Queue<Integer> q = new LinkedList<>();
        q.offer(i * n + j);
        while (!q.isEmpty()) {
            i = q.peek() / n; j = q.poll() % n;
            for (int k = 0; k < 4; ++k) { // traverse 4 neighbors of (i, j)
                int r = i + d[k], c = j + d[k + 1];
                if (r < 0 || r >= m || c < 0 || c >= n) { // out of boundary.
                    ans = 0; // set 0;
                }else if (g[r][c] == 0 && seenLand.add(r * n + c)) { // (r, c) is land never seen before.
                    q.offer(r * n + c);
                }
            }
        }
        return ans;
    }
==========


Union Find

Traverse all cells not on boundary of the grid; For each land with a land neighbor, if the neighbor belongs to open island, merge it into the open island; otherwise, merge the neighbor into the island including current cell;
Traverse all cells not on boundary of the grid again; Whenever encountering a land that its parent (id) is itself, then it is the root of the component (island), increase count by 1; The final value of the count is the number of closed island.
    private static final int[] d = {0, 1, 0, -1, 0};
    private int m, n; // numbers of rows and comlumns of grid.
    private int[] id; // parent ids. 
    
    public int closedIsland(int[][] grid) {
        m = grid.length; n = m == 0 ? 0 : grid[0].length;
        id = IntStream.range(0, m * n).toArray(); // Initialized as i * n + j the parent id of cell (i, j).
        for (int i = 1; i < m - 1; ++i) // traverse non-boundary rows.
            for (int j = 1; j < n - 1; ++j) // traverse non-boundary cells within a row.
                if (grid[i][j] == 0) // (i, j) is land.
                    for (int k = 0; k < 4; ++k) { // traverse the neighbors of (i, j).
                        int r = i + d[k], c = j + d[k + 1];
                        if (grid[r][c] == 0) // (r, c) is a land neighbor.
                            union(i * n + j, r * n + c);
                    }
        int cnt = 0; // number of closed islands: number of the non-boundary lands that are ids (parent) of itself.
        for (int i = 1; i < m - 1; ++i) // traverse non-boundary rows.
            for (int j = 1; j < n - 1; ++j) // traverse non-boundary cells within a row.
                if (grid[i][j] == 0 && id[i * n + j] == i * n + j) // Is (i, j) a land as well as the id (parent) of self? 
                    ++cnt;
        return cnt;
    }
    
    private int find(int x) {
        while (x != id[x]) 
            x = id[x];
        return x;
    }
    
    private void union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) 
            return;
        if (isBoundary(rootY)) {
            id[rootX] = rootY; 
        }else {
            id[rootY] = rootX;
        } 
    }
    
    private boolean isBoundary(int id) {
        int i = id / n, j = id % n;
        return i == 0 || j == 0 || i == m - 1 || j == n - 1;
    }

