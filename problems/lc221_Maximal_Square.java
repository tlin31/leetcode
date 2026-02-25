221. Maximal square - Hard

Given a 2D binary matrix filled with 0s and 1s, find the largest square containing only 1s and 
return its area.

Example:
Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4

******************************************************
key:
    - DP
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1:


1. 核心逻辑
    定义 dp[j] 为以当前行第 j 列为右下角的最大正方形边长。 
    状态转移：dp[j] = min(左方, 上方, 左上方) + 1。
    关键点：在更新一维数组时，原本的 dp[j] 是“上方”的值，更新后的 dp[j-1] 是“左方”的值，而“左上方”的值会被覆盖。
    因此，需要一个变量 prev 来记录被覆盖前的旧值。 

class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        // dp[i][j] 表示以 (i, j) 为右下角的正方形的最大边长
        int[][] dp = new int[rows][cols];
        int maxSide = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        // 第一行或第一列，最大边长只能是 1
                        dp[i][j] = 1;
                    } else {
                        // 核心：取左、上、左上三个方向的最小值，加 1
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    // 更新最大边长
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }

        // 题目要求返回面积，所以是边长的平方
        return maxSide * maxSide;
    }
}





    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] dp = new int[cols + 1];
        int maxSide = 0;
        int prev = 0; // 用于存储左上角的值 (dp[i-1][j-1])

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j]; // 暂存当前的 dp[j]，它将成为下一列计算时的“左上方”值
                if (matrix[i - 1][j - 1] == '1') {
                    // dp[j] 取决于：左边 dp[j-1], 上边 dp[j], 左上边 prev
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxSide = Math.max(maxSide, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp; // 更新 prev 为刚才暂存的值
            }
            prev = 0; // 每一行结束时，下一行的第一个元素左上方没有值，重置为0
        }
        return maxSide * maxSide; // 返回面积
    }
}


3. 复杂度分析

时间复杂度：O(M*N)，其中 M/N 为矩阵的行列数。空间复杂度 O(N)，仅使用一个长度为列数的一维数组





