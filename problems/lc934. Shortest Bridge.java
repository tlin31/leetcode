934. Shortest Bridge - Medium

You are given an n x n binary matrix grid where 1 represents land and 0 represents water.

An island is a 4-directionally connected group of 1's not connected to any other 1's. 
There are exactly two islands in grid.

You may change 0's to 1's to connect the two islands to form one island.

Return the smallest number of 0's you must flip to connect the two islands.

 

Example 1:

Input: grid = [[0,1],[1,0]]
Output: 1
Example 2:

Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
Output: 2
Example 3:

Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Output: 1
 

Constraints:

n == grid.length == grid[i].length
2 <= n <= 100
grid[i][j] is either 0 or 1.
There are exactly two islands in grid.


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

阶段 1️：DFS 找到第一个岛

扫描整个 grid；

找到第一个 1；

用 DFS 或 BFS 把这个岛的所有陆地都标记出来；

同时，把这些点加入队列（queue），因为它们是 BFS 扩展的起点。

阶段 2️：BFS 扩展寻找第二个岛

从第一个岛的所有边界开始（即上一步的 queue），

向四周逐层扩展（每层代表“把一个 0 变成 1”）；

当 BFS 碰到第二个岛（即再次遇到 1）时，当前层数就是答案。



Stats:

项目	复杂度
时间复杂度	O(n²)，每个格子最多访问两次（一次 DFS，一次 BFS）
空间复杂度	O(n²)，主要用于队列和递归栈


class Solution {
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    int n;

    public int shortestBridge(int[][] grid) {
        n = grid.length;
        Queue<int[]> queue = new LinkedList<>();

        // 1️⃣ 找到第一个岛并用 DFS 标记它
        boolean found = false;
        for (int i = 0; i < n; i++) {
            if (found) break;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j, queue);
                    found = true;
                    break;
                }
            }
        }

        // 2️⃣ 从第一个岛开始 BFS 扩展
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int x = cell[0] + d[0];
                    int y = cell[1] + d[1];
                    if (x < 0 || y < 0 || x >= n || y >= n) continue;
                    if (grid[x][y] == 2) continue; // 已访问
                    if (grid[x][y] == 1) return steps; // 碰到第二个岛，结束
                    grid[x][y] = 2; // 标记为访问过
                    queue.offer(new int[]{x, y});
                }
            }
            steps++;
        }

        return -1;
    }

    // DFS 标记第一个岛
    private void dfs(int[][] grid, int i, int j, Queue<int[]> queue) {
        if (i < 0 || j < 0 || i >= n || j >= n || grid[i][j] != 1) return;
        grid[i][j] = 2; // 标记访问
        queue.offer(new int[]{i, j}); // 加入 BFS 起点
        for (int[] d : dirs) {
            dfs(grid, i + d[0], j + d[1], queue);
        }
    }
}

