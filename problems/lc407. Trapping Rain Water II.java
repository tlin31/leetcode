407. Trapping Rain Water II
Hard
Topics
conpanies icon
Companies
Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map, return the volume of water it can trap after raining.

 

Example 1:


Input: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]
Output: 4
Explanation: After the rain, water is trapped between the blocks.
We have two small ponds 1 and 3 units trapped.
The total volume of water trapped is 4.
Example 2:


Input: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
Output: 10
 

Constraints:

m == heightMap.length
n == heightMap[i].length
1 <= m, n <= 200
0 <= heightMap[i][j] <= 2 * 104


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


这道题使用 最小堆（PriorityQueue）+ BFS（多源），
从 边界向内逐层扩展，像“灌水”一样，始终以当前最低边界作为水位。


===================================================================================================
Method 1:

Method:

https://leetcode.com/problems/trapping-rain-water-ii/solutions/1138028/python3visualization-bfs-solution-with-e-rtyb/

Step 1：初始化
	能够积蓄雨水的单元格必须被高度更高的单元格包围，因此位于边界的单元格无法积蓄雨水。
	我们先将这些单元格添加到堆中（按高度排序）。

	把 所有边界格子 放入最小堆

	标记 visited

Step 2：从堆中取最低点

	当前高度 = heap.poll()

	更新 maxHeight = max(maxHeight, currHeight)

Step 3：向四个方向扩展

	对邻居 (nr, nc)：

	如果未访问：

	若 height[nr][nc] < maxHeight --> 可以存水：water += maxHeight - height[nr][nc]

	加入堆

Step 4：堆空结束


Stats:

	- 
	- 




faster solution with Object oriented, Cell:


import java.util.*;

class Solution {
    public int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;

        if (m < 3 || n < 3) {
            return 0; 
        }

        boolean[][] visited = new boolean[m][n];
        // shortest cell up front
        PriorityQueue<Cell> pq = new PriorityQueue<>((a, b) -> a.height - b.height);

        // 1. push all borders
        for (int i = 0; i < m; i++) {
            pq.offer(new Cell(i, 0, heightMap[i][0]));
            pq.offer(new Cell(i, n - 1, heightMap[i][n - 1]));
            visited[i][0] = true;
            visited[i][n - 1] = true;
        }

        for (int j = 0; j < n; j++) {
            pq.offer(new Cell(0, j, heightMap[0][j]));
            pq.offer(new Cell(m - 1, j, heightMap[m - 1][j]));
            visited[0][j] = true;
            visited[m - 1][j] = true;
        }

        int water = 0;
        int[][] dir = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        int maxBoundary = 0;
        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            maxBoundary = Math.max(maxBoundary, curr.height);

            for (int[] d : dir) {
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];

                if (nx >= 0 && ny >= 0 && nx < m && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    if (heightMap[nx][ny] < maxBoundary) {
                        water += maxBoundary - heightMap[nx][ny];
                    }
                    pq.offer(new Cell(nx, ny, Math.max(heightMap[nx][ny], maxBoundary)));
                }
            }
        }

        return water;
    }
    class Cell {
        int x, y, height;

        Cell(int x, int y, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
        }
    }
}


推荐系统：

1. go to database fetch user info, 
2. gets user past purchase records, elasticsearch?
2. recommendation algo based on user's past purchase, like category/keyword/price range



