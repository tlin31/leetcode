885. Spiral Matrix III - Medium

You start at the cell (rStart, cStart) of an rows x cols grid facing east. The northwest corner is at the first row and column in the grid, and the southeast corner is at the last row and column.

You will walk in a clockwise spiral shape to visit every position in this grid. Whenever you move outside the grid's boundary, we continue our walk outside the grid (but may return to the grid boundary later.). Eventually, we reach all rows * cols spaces of the grid.

Return an array of coordinates representing the positions of the grid in the order you visited them.

 

Example 1:


Input: rows = 1, cols = 4, rStart = 0, cStart = 0
Output: [[0,0],[0,1],[0,2],[0,3]]
Example 2:


Input: rows = 5, cols = 6, rStart = 1, cStart = 4
Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
 

Constraints:

1 <= rows, cols <= 100
0 <= rStart < rows
0 <= cStart < cols
 

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: 模拟 + 方向数组 + 动态扩展步长


Method:

路线方向顺序：

右 → 下 → 左 → 上 → 重复…


并且每次移动距离变化为：

1,1,2,2,3,3,4,4 ...

=== 每走两个方向，步数 +1

并且我们不用担心走出边界, 只要记录走到过的合法坐标,直到收集满 R*C 就结束


Stats:
✔ 时间复杂度 O(R*C)

只访问每个格一次。

✔ 空间 O(1) 除输出数组


class Solution {
    public int[][] spiralMatrixIII(int R, int C, int rStart, int cStart) {
        int[][] result = new int[R * C][2];
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}}; // 右 下 左 上

        int step = 1;
        int row = rStart, col = cStart;
        int idx = 0;

        result[idx++] = new int[]{row, col}; //在result【0】处存起点

        while (idx < R * C) {
            for (int d = 0; d < 4; d++) {
                for (int s = 0; s < step; s++) {
                    row += dirs[d][0];
                    col += dirs[d][1];

                    if (row >= 0 && row < R && col >= 0 && col < C) {
                        result[idx++] = new int[]{row, col};
                    }
                }

                // 每走两次方向，步数 +1
                if (d % 2 == 1) {
                    step++;
                }
            }
        }

        return result;
    }
}



