1504. Count Submatrices With All Ones - Medium

Given an m x n binary matrix mat, return the number of submatrices that have all ones.

 

Example 1:


Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
Output: 13
Explanation: 
There are 6 rectangles of side 1x1.
There are 2 rectangles of side 1x2.
There are 3 rectangles of side 2x1.
There is 1 rectangle of side 2x2. 
There is 1 rectangle of side 3x1.
Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
Example 2:


Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
Output: 24
Explanation: 
There are 8 rectangles of side 1x1.
There are 5 rectangles of side 1x2.
There are 2 rectangles of side 1x3. 
There are 4 rectangles of side 2x1.
There are 2 rectangles of side 2x2. 
There are 2 rectangles of side 3x1. 
There is 1 rectangle of side 3x2. 
Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
 

Constraints:

1 <= m, n <= 150
mat[i][j] is either 0 or 1.

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: 高度 + 单调栈计数

Stats:

	- （O(m·n））
	- 

Step 1：先求 height 数组（和 85 一样）

	对每一行 i：height[j] = 当前行向上连续 1 的高度

	例：

	1 0 1
	1 1 1

	第二行 height = 2 1 2

Step 2：对每一行的 height[j]，统计“以该点为右下角”的矩形数量

	如果 height = [h1, h2, h3 ...]
	对于每个位置 j：

	向左扩展，最小高度 minH, 每次扩展能生成 minH 个矩形


O(m*n^2)解法：

		class Solution {
		    public int numSubmat(int[][] mat) {
		        int m = mat.length, n = mat[0].length;
		        int[] height = new int[n];
		        int ans = 0;

		        for (int i = 0; i < m; i++) {
		            // 更新高度
		            for (int j = 0; j < n; j++) {
		                height[j] = mat[i][j] == 1 ? height[j] + 1 : 0;
		            }

		            // 关键：对该行进行计数
		            ans += countFromHistogram(height);
		        }
		        return ans;
		    }

		    // 对当前 height 数组计算矩形数量
		    private int countFromHistogram(int[] height) {
		        int n = height.length;
		        int count = 0;

		        for (int j = 0; j < n; j++) {
		            int minH = height[j];
		            for (int k = j; k >= 0 && minH > 0; k--) {
		                minH = Math.min(minH, height[k]);
		                count += minH;
		            }
		        }
		        return count;
		    }
		}


最优 O(m·n)

用栈快速求：每个 height[j] 在作为最矮柱时贡献的矩形数。

🎯 利用递减栈：

压栈 structure：(高度，高度持续的宽度，贡献的总矩形数) 当遇到更短的高度时，弹出并计算贡献。

合并比当前高的矩形, 统一在更矮的柱子上重建矩形计数, 避免 O(n²) 的双循环

class Solution {
        public static int numSubmat(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;

        // height[j] 表示：以当前行为底，第 j 列连续 1 的高度
        int[] height = new int[cols];
        long total = 0;

        // 枚举每一行作为“底边”
        for (int i = 0; i < rows; i++) {
            // 1. 更新当前行对应的高度直方图
            for (int j = 0; j < cols; j++) {
                height[j] = mat[i][j] == 1 ? height[j] + 1 : 0;
            }
            // 2. 在当前这行的高度数组上，用单调栈统计矩形个数
            total += countRectangles(height);
        }

        return (int) total;
    }

    // 在一维 height 数组上，统计“以这一行为底”的所有全 1 矩形数量
    // 用array替代stack，加快速度
    private static long countRectangles(int[] height) {
        int n = height.length;
        int[] stack = new int[n];
        int stackSize = 0;
        long res = 0;

        // 遍历每一列
        for (int i = 0; i < n; i++) {
            // 和“子数组最小值之和”一样：用 >= 来保证“右侧是下一个严格更小”
            while (stackSize > 0 && height[stack[stackSize - 1]] >= height[i]) {
                int cur = stack[--stackSize];    
                // 左边第一个比它小的下标          
                int left = (stackSize == 0) ? -1 : stack[stackSize - 1]; 
                // 右边第一个比它小的下标
                int right = i;                             

                // 以 cur 为“最矮高度”的区间是 (left, right)
                // 横向可以选的子数组个数 = (cur - left) * (right - cur)
                // 纵向可以选的高度数 = height[cur] 种（从 1 层到 height[cur] 层）
                res += (long) height[cur] * (cur - left) * (right - cur);
            }
            stack[stackSize++] = i;
        }

        // 清算阶段：右边界视为 n，把stack里面剩下的进行计算
        int right = n;
        while (stackSize > 0) {
            int cur = stack[--stackSize];
            int left = (stackSize == 0) ? -1 : stack[stackSize - 1];
            res += (long) height[cur] * (cur - left) * (right - cur);
        }

        return res;
    }

}



