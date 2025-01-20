200. Number of Islands - Medium


Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is 
surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 

You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input:
11110
11010
11000
00000

Output: 1

Example 2:
Input:
11000
11000
00100
00011

Output: 3

******************************************************
key:
	- dfs, bfs, union find
	- edge case:
		1) empty grid, return 0
		2)

******************************************************

=======================================================================================================

method 1:

	- dfs
	- 

stats:

	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Number of Islands.
	- Memory Usage: 40.3 MB, less than 100.00% 

    public int numIslands(char[][] A) {
        int m = A.length, n = A[0].length, res = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res += dfs(A, i, j);
        return res;
    }

    private int dfs(char[][] A, int i, int j) {
        int m = A.length, n = A[0].length;
        if (i < 0 || i == m || j < 0 || j == n || A[i][j] == '0') return 0;

        A[i][j] = '0'; //change 1 to 0， marked as visited
        dfs(A, i - 1, j);
        dfs(A, i + 1, j);
        dfs(A, i, j - 1);
        dfs(A, i, j + 1);
        return 1;
    }

-------------------------------

public class Solution {
    int y;          // The height of the given grid
    int x;          // The width of the given grid
    char[][] g;     // The given grid, stored to reduce recursion memory usage
    

    public int numIslands(char[][] grid) {
        // Store the given grid
        // This prevents having to make copies during recursion
        g = grid;

        // Our count to return
        int c = 0;
        
        // Dimensions of the given graph
        y = g.length;
        if (y == 0) 
            return 0;
        x = g[0].length;
        
        // Iterate over the entire given grid
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (g[i][j] == '1') {
                    dfs(i, j);
                    c++;
                }
            }
        }
        return c;
    }
    
    /**
     * Marks the given site as visited, then checks adjacent sites.
     * Or, Marks the given site as water, if land, then checks adjacent sites.
     * 
     * Or, Given one coordinate (i,j) of an island, obliterates the island
     * from the given grid, so that it is not counted again.
     * 
     * @param i, the row index of the given grid
     * @param j, the column index of the given grid
     */
    private void dfs(int i, int j) {
        
        // Check for invalid indices and for sites that aren't land
        if (i < 0 || i >= y || j < 0 || j >= x || g[i][j] != '1') 
        	return;
        
        // Mark the site as visited
        g[i][j] = '0';
        
        // Check all adjacent sites
        dfs(i + 1, j);
        dfs(i - 1, j);
        dfs(i, j + 1);
        dfs(i, j - 1);
    }
}



=======================================================================================================
method 2:

method:

	- union find
	- 

stats:

	- 
	- 


class Solution {
    
    // store the parent
    int[] par;
    
    public int numIslands(char[][] a) {
        if(a.length==0) 
            return 0;
        
        int n = a.length, m=a[0].length;
        par = new int[m*n];
        Arrays.fill(par, -1); 
        
        // go from bottom left corner to up.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                
                if(a[i][j]=='1'){
                    par[i*m+j]= i*m+j; // note, that `par` was filled witn -1 values

                    if(i>0 && a[i-1][j]=='1') 
                    	union(i*m+j, (i-1)*m+j); // union current+top

                    if(j>0 && a[i][j-1]=='1') 
                    	union(i*m+j, i*m+(j-1)); // union current+left
                }
                
            }
        }
        
        // since all connected 1's are grouped with the same par value
        Set<Integer> set = new HashSet<>();
        for(int k=0;k<par.length;k++){
            if(par[k]!=-1) 
                set.add(find(k));
        }
        return set.size();
    }
    
    int find(int x){
        if(par[x]==x) 
            return x;
        par[x]=find(par[x]);
        return par[x];
    }    
    
    void union(int x, int y){
        int px = find(x);
        int py = find(y);
        par[px]= par[py];
    }
    
}



=======================================================================================================
method 3:

method:

	- bfs

class Solution {
	int[][] dirs = {{0,1}, {1,0}, {0, -1}, {-1, 0}};


    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        
        int m = grid.length, n = grid[0].length;
        
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        int count = 0;

        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(grid[i][j] == '1' && !visited[i][j]) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    bfs(grid, queue, visited);
                    count++;
                }
            }
        }
        
        return count;
    }
    
    private void bfs(char[][] grid, Queue<int[]> queue, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;
        
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                
                // if not valid/not island, keep going
                if (x < 0 || x >= m || y < 0 || y >=n || visited[x][y] || grid[x][y] == '0') 
                    continue;
                
                //else add the next 'island land'
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
    }
}

----------------------
加入queue的时候，把二维坐标转为了一维，就省去了再创建一个类表示坐标。


public int numIslands(char[][] grid) {
        int count = 0;
        int rows = grid.length;
        if (rows == 0) {
            return 0;
        }
        int cols = grid[0].length;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    bfs(r, c, rows, cols, grid);
                }
            }
        }
        return count;
    }

 private void bfs(int r, int c, int rows, int cols, char[][] grid) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(r * cols + c);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int row = cur / cols;
            int col = cur % cols;
            //已经标记过就结束，这句很关键，不然会把一些节点重复加入
            if(grid[row][col] == '2'){
                continue;
            }

            grid[row][col] = '2';

            //将上下左右连通的 1 加入队列
            if (row != (rows - 1) && grid[row + 1][col] == '1') {
                queue.offer((row + 1) * cols + col);
            }
            if (col != (cols - 1) && grid[row][col + 1] == '1') {
                queue.offer(row * cols + col + 1);
            }
            if (row != 0 && grid[row - 1][col] == '1') {
                queue.offer((row - 1) * cols + col);
            }
            if (col != 0 && grid[row][col - 1] == '1') {
                queue.offer(row * cols + col - 1);
            }

        }
 }



