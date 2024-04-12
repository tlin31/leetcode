59. Spiral Matrix II - Medium

Given a positive integer n, generate a square matrix filled with elements from 1 to n^2 in spiral order.

Example:
Input: 3

Output:
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]

******************************************************
key:
	- stack
	- edge case:
		1) 0, return empty int[][]
		2) 1, return [1]?

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 时间复杂度：O（n²）
	- 空间复杂度：O（1）
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Spiral Matrix II.
	- Memory Usage: 34.2 MB, less than 8.33%

    public int[][] generateMatrix(int n) {

        int[][] matrix = new int[n][n];

        // Edge Case
        if (n == 0) {
            return matrix;
        }

        // Normal Case
        int rowStart = 0;
        int rowEnd = n - 1;
        int colStart = 0;
        int colEnd = n - 1;

        int num = 1; 
        while (num <= n*n)
        // while (rowStart <= rowEnd && colStart <= colEnd) {
            for (int i = colStart; i <= colEnd; i++) {
                matrix[rowStart][i] = num++; 
            }
            rowStart++;

            for (int i = rowStart; i <= rowEnd; i++) {
                matrix[i][colEnd] = num++; 
            }
            colEnd--;

            for (int i = colEnd; i >= colStart; i--) {
                if (rowStart <= rowEnd)
                    matrix[rowEnd][i] = num++; 
            }
            rowEnd--;

            for (int i = rowEnd; i >= rowStart; i--) {
                if (colStart <= colEnd)
                    matrix[i][colStart] = num++; 
            }
            colStart++;
        }
        return matrix;
    }

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

	/*
     * direction 0 代表向右, 1 代表向下, 2 代表向左, 3 代表向上
     */
	public int[][] generateMatrix(int n) {
	    int[][] ans = new int[n][n];
	    int start_x = 0, start_y = 0, direction = 0, top_border = -1, // 上边界
	    right_border = n, // 右边界
	    bottom_border = n, // 下边界
	    left_border = -1; // 左边界
	    int count = 1;
	    while (true) {
	        // 全部遍历完结束
	        if (count == n * n + 1) {
	            return ans;
	        }
	        // 注意 y 方向写在前边，x 方向写在后边
	        ans[start_y][start_x] = count;
	        count++;
	        switch (direction) {
	                // 当前向左
	            case 0:
	                // 继续向左是否到达边界
	                // 到达边界就改变方向，并且更新上边界
	                if (start_x + 1 == right_border) {
	                    start_y += 1;
	                    top_border += 1;
	                    direction = 1;

	                } else {
	                    start_x += 1;
	                }
	                break;
	                // 当前向下

	            case 1:
	                // 继续向下是否到达边界
	                // 到达边界就改变方向，并且更新右边界
	                if (start_y + 1 == bottom_border) {
	                    direction = 2;
	                    start_x -= 1;
	                    right_border -= 1;
	                } else {
	                    start_y += 1;
	                }
	                break;

	            case 2:
	                if (start_x - 1 == left_border) {
	                    direction = 3;
	                    start_y -= 1;
	                    bottom_border -= 1;
	                } else {
	                    start_x -= 1;
	                }
	                break;
	                
	            case 3:
	                if (start_y - 1 == top_border) {
	                    direction = 0;
	                    start_x += 1;
	                    left_border += 1;
	                } else {
	                    start_y -= 1;
	                }
	                break;
	        }
	    }

	}



