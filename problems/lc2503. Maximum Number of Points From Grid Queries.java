2503. Maximum Number of Points From Grid Queries - Hard

You are given an m x n integer matrix grid and an array queries of size k.

Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:

If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
Otherwise, you do not get any points, and you end this process.
After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.

Return the resulting array answer.

 

Example 1:


Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
Output: [5,8,1]
Explanation: The diagrams above show which cells we visit to get points for each query.
Example 2:


Input: grid = [[5,2,1],[1,1,2]], queries = [3]
Output: [0]
Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
 

Constraints:

m == grid.length
n == grid[i].length
2 <= m, n <= 1000
4 <= m * n <= 105
k == queries.length
1 <= k <= 104
1 <= grid[i][j], queries[i] <= 106


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

❗关键转化

queries 是“阈值”问题，而不是路径问题

也就是说：query 越大 → 可访问的点 只会增加，不会减少

这是一个 单调性问题

👉 立刻想到：

	排序 queries

	一次 BFS / Dijkstra 式扩展

	逐步“解锁”格子


整体策略 (非常像 Dijkstra / Flood Fill)

	1、queries 排序（从小到大）

	2、使用 最小堆（PriorityQueue），按 grid 值从小到大扩展

	3、每个格子 只访问一次

	4、随着 query 增大，不断“吃掉”更小值的格子

	5、记录当前已经访问的格子数量


heap 确保：每次扩展的是当前最小 grid 值

	一旦某格子 grid[x][y] < q：

	它在所有后续更大的 query 中 必然可达

	每个格子只进堆一次 → 总体 O(MN log MN)

Stats:

	时间复杂度：
	排序 queries		O(Q log Q)
	每个格子进堆一次	O(MN log MN)
	总计				O((MN + Q) log MN)	- 





	class Solution {
	    public int[] maxPoints(int[][] grid, int[] queries) {
	        int m = grid.length, n = grid[0].length;
	        int qn = queries.length;

	        // 因为后面的sort会打乱queries顺序，先存原先的query position
	        int[][] qs = new int[qn][2];
	        for (int i = 0; i < qn; i++) {
	            qs[i][0] = queries[i];
	            qs[i][1] = i;
	        }

	        Arrays.sort(qs, (a, b) -> a[0] - b[0]);

	        boolean[][] visited = new boolean[m][n];
	        PriorityQueue<int[]> pq =
	            new PriorityQueue<>((a, b) -> a[0] - b[0]);

	        // start point, upper left corner
	        pq.offer(new int[]{grid[0][0], 0, 0});
	        visited[0][0] = true;

	        int[] res = new int[qn];
	        int count = 0;

	        int[] dirs = {0, 1, 0, -1, 0};

	        for (int[] query : qs) {
	            int limit = query[0];
	            int idx = query[1];

	            while (!pq.isEmpty() && pq.peek()[0] < limit) {
	                int[] cur = pq.poll();
	                count++;
	                int x = cur[1], y = cur[2];

	                for (int d = 0; d < 4; d++) {
	                    int nx = x + dirs[d];
	                    int ny = y + dirs[d + 1];
	                    if (nx >= 0 && nx < m && ny >= 0 && ny < n &&
	                        !visited[nx][ny]) {
	                    	
	                        visited[nx][ny] = true;
	                        pq.offer(new int[]{grid[nx][ny], nx, ny});
	                    }
	                }
	            }
	            res[idx] = count;
	        }
	        return res;
	    }
	}



===================================================================================================

union find:

class Solution {

    // Represents a cell in the grid with row index, column index, and value
    record Cell(int row, int col, int value) {}

    // Represents a query with its original index and value
    record Query(int index, int value) {}

    // Right, Left, Down, Up
    private static final int[] ROW_DIRECTIONS = { 0, 0, 1, -1 };
    // Corresponding column moves
    private static final int[] COL_DIRECTIONS = { 1, -1, 0, 0 };

    public int[] maxPoints(int[][] grid, int[] queries) {
        int rowCount = grid.length, colCount = grid[0].length;
        int totalCells = rowCount * colCount;

        // Store queries with their original indices to maintain result order
        Query[] sortedQueries = new Query[queries.length];
        for (int i = 0; i < queries.length; i++) {
            sortedQueries[i] = new Query(i, queries[i]);
        }
        // Sort queries in ascending order
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a.value, b.value));

        // Store all grid cells and sort them by value
        Cell[] sortedCells = new Cell[totalCells];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                sortedCells[row * colCount + col] = new Cell(
                    row,
                    col,
                    grid[row][col]
                );
            }
        }
        // Sort cells by value
        Arrays.sort(sortedCells, (a, b) -> Integer.compare(a.value, b.value));

        // Union-Find to track connected components
        UnionFind uf = new UnionFind(totalCells);
        int[] result = new int[queries.length];
        int cellIndex = 0;

        // Process queries in sorted order
        for (Query query : sortedQueries) {
            // Process cells whose values are smaller than the current query value
            while (
                cellIndex < totalCells &&
                sortedCells[cellIndex].value < query.value
            ) {
                int row = sortedCells[cellIndex].row;
                int col = sortedCells[cellIndex].col;
                // Convert 2D position to 1D index
                int cellId = row * colCount + col;

                // Merge the current cell with its adjacent cells that have already been
                // processed
                for (int direction = 0; direction < 4; direction++) {
                    int newRow = row + ROW_DIRECTIONS[direction];
                    int newCol = col + COL_DIRECTIONS[direction];

                    // Check if the new cell is within bounds and its value is smaller than the
                    // query value
                    if (
                        newRow >= 0 &&
                        newRow < rowCount &&
                        newCol >= 0 &&
                        newCol < colCount &&
                        grid[newRow][newCol] < query.value
                    ) {
                        uf.union(cellId, newRow * colCount + newCol);
                    }
                }
                cellIndex++;
            }
            // Get the size of the connected component containing the top-left cell (0,0)
            result[query.index] = query.value > grid[0][0] ? uf.getSize(0) : 0;
        }
        return result;
    }
}

class UnionFind {

    private final int[] parent;
    private final int[] size;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        // Initialize all parents to -1 (disjoint sets)
        Arrays.fill(parent, -1);
        // Each component starts with size 1
        Arrays.fill(size, 1);
    }

    // Find with path compression
    public int find(int node) {
        // If negative, it's the root
        if (parent[node] < 0) return node;
        // Path compression
        return parent[node] = find(parent[node]);
    }

    // Union by size (merge smaller tree into larger tree)
    public boolean union(int nodeA, int nodeB) {
        int rootA = find(nodeA), rootB = find(nodeB);
        // Already connected
        if (rootA == rootB) return false;

        if (size[rootA] > size[rootB]) {
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        } else {
            parent[rootA] = rootB;
            size[rootB] += size[rootA];
        }
        return true;
    }

    // Get the size of the component containing a given node
    public int getSize(int node) {
        return size[find(node)];
    }
}

