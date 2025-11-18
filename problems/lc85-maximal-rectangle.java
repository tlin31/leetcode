85. Maximal Rectangle - Hard

Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1
and return its area.

Example:

Input:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
Method 1: stack

把每一行当作 “柱状图”，用 84 题单调栈求最大矩形

📌 本题最优、最常用方法：时间复杂度 O(m·n)

height[j]：当前 row 往上连续 1 的个数

ex.

matrix:
["1","0","1","0","0"],
["1","0","1","1","1"],
["1","1","1","1","1"],
["1","0","0","1","0"]


i = 0, height= [1, 0, 1, 0, 0]
i = 1, height= [2, 0, 2, 1, 1]
i = 2, height= [3, 1, 3, 2, 2]
i = 3, height= [4, 0, 0, 3, 0]


这一行就变成了 直方图问题（84 题），直接求最大矩形。

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;

        int[] height = new int[n];
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            // 更新高度
            for (int j = 0; j < n; j++) {

                height[j] = matrix[i][j] == '1' ? height[j] + 1 : 0;
            }

            // 对 height 用 84 题的单调栈求最大矩形
            maxArea = Math.max(maxArea, largestRectangleArea(height));
        }

        return maxArea;
    }

    // 84 题代码
    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] h = Arrays.copyOf(heights, n + 1);
        h[n] = 0;

        Stack<Integer> stack = new Stack<>();
        int max = 0;

        for (int i = 0; i < h.length; i++) {
            while (!stack.isEmpty() && h[i] < h[stack.peek()]) {
                int height = h[stack.pop()];
                int right = i;
                int left = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, height * (right - left - 1));
            }
            stack.push(i);
        }

        return max;
    }
}

=======================================================================================================
Method 1:DP + 左右边界


对每一行维护三个 DP 数组：

    height[j] 当前列连续 1 的高度

    left[j] 该高度矩形的左边界

    right[j] 该高度矩形的右边界

面积 = height[j] * (right[j] - left[j])


class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;
        int[] height = new int[n];
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);

        int max = 0;

        for (int i = 0; i < m; i++) {
            int curLeft = 0, curRight = n;

            // 更新高度
            for (int j = 0; j < n; j++) {
                height[j] = matrix[i][j] == '1' ? height[j] + 1 : 0;
            }

            // 更新左边界
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], curLeft);
                } else {
                    left[j] = 0;
                    curLeft = j + 1;
                }
            }

            // 更新右边界
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], curRight);
                } else {
                    right[j] = n;
                    curRight = j;
                }
            }

            // 计算面积
            for (int j = 0; j < n; j++) {
                max = Math.max(max, height[j] * (right[j] - left[j]));
            }
        }

        return max;
    }
}


=======================================================================================================
Method 1:

Method:

	- 扩张法

	- 根据两个限定规则：
		所求矩形的第一个坐标点必然是这个二维数组中的某一点，且这一点是'1'
		所求矩形一定是从一个 1*1 的矩形逐渐扩张而来的
		所以，我们可以计算从二维数组中的每个点扩张而成的矩形的面积的最大值，即为待求解。因为已经假定是从某一个点扩张，
			所以它只能选择向右扩张或向下扩张，然后在每一个递归函数中判断是否能够完成扩张
	-	

Stats:



private static int max = 0;
public static int maximalRectangle(char[][] matrix) {
    max = 0;
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] == '1') {
                max = Math.max(max, 1);
                max(matrix, i, j, 1, 1);
            }
        }
    }
    return max;
}
public static void max(char[][] matrix, int i, int j, int w, int h) {
    //right
    if (j+w < matrix[0].length) {
        for (int k = 0; k < h; k++) {
            if (matrix[i+k][j+w] == '0') {
                break;
            }
            if (k == h-1) {
                max = Math.max(max, h * (w + 1));
                max(matrix, i, j, w+1, h);
            }
        }
    }

    //down
    if (i+h < matrix.length) {
        for (int k = 0; k < w; k++) {
            if (matrix[i+h][j+k] == '0') {
                break;
            }
            if (k == w-1) {
                max = Math.max(max, (h + 1) * w);
                max(matrix, i, j, w, h+1);
            }
        }
    }
}


=======================================================================================================
method 2: 计数法 

Stats:

    - 
    - 

Method:

	-	note the continuous 1s, need to stop accumulating once we encounter a 0

			1 0 1 0 0		1 0 1 0 0
			1 0 1 1 1	->	1 0 1 2 3
			1 1 1 1 1		1 2 3 4 5
			1 0 0 1 0		1 0 0 1 0

    -   create a row histogram, and use loop to go through each element to get the max rectangle
        ending at matrix[i][j]

        for element, we just need to find the max width with certain height.
            ex. for [2,3] in new graph, it equals to 4, meaning in this row, the element has
                4 consecutive 1s in front of it. 
                when height = 1, the max rectangle = 1*4 = 4.
                when height = 2, check the point[1,3] above it, and see it = 2, meaning it only has
                    2 consecutive 1 with height 2, thus max rectangle = height * min([1,3],[2,3])
                    = 2 * 2 = 4
                when height = 3, check [0,3], and it = 0, done
    


public static int maximalRectangle(char[][] matrix) {

	// create accumulated one's
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[i][j] == '1') {
                matrix[i][j] = (char) (matrix[i][j-1] + 1);
            } else {
                matrix[i][j] = '0';
            }
        }
    }

    int maxArea = 0;

    // loop through every element in matrix
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            int curWidth = matrix[i][j] - '0';
            if (curWidth > 0) {
                // check for case when height = 1
                maxArea = Math.max(maxArea, curWidth);

                // check for same position in previous rows
                for (int k = i-1; k >= 0 && matrix[k][j] != '0'; k--) {
                    curWidth = Math.min(curWidth, matrix[k][j] - '0');
                    height = i-k+1;
                    maxArea = Math.max(maxArea, curWidth * height);
                }
            }
        }
    }
    return maxArea;
}



=======================================================================================================

Method 4:
https://leetcode.com/problems/maximal-rectangle/discuss/225690/Java-solution-with-explanations-in-Chinese

一个二维数组，可以按行分解成下面四个蓝色标记的图，只要将下面四个蓝色组成的条形图中，能够组成的最大的矩形求出来，
那么相对于二维数组的解也就求出来了。而下面四个条形图的问题，其实就是 largest rectangle in histogram 

这一题的解，也就是说，本题可以分解成四个小问题，求出四个分问题的解，也就能得到当前问题的解。而在 largest rectangle 
in histogram 这一问题的求解中使用的所有方法都可以使用在本题的求解中，而 heights 这个数组，就是以二维数组的某一行为底,
'1'作为连续，'0'作为不连续的数组，那么，求出代表每一层的 heights ，再对这个 heights 求解，即可。

public static int maximalRectangle2(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
        return 0;
    int m = matrix.length, n = matrix[0].length;
    int[] heights = new int[n];
    int max = 0;
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] == '1') {
                heights[j]++;
            } else {
                heights[j] = 0;
            }
        }
        max = Math.max(max, largest(heights, 0, n-1));
    }
    return max;
}
public static int largest(int[] heights, int start, int end) {
    if (start > end) return 0;
    if (start == end) return heights[start];
    boolean sorted = true;
    int min = start;
    for (int i = start+1; i <= end; i++) {
        if (heights[i] < heights[i-1]) sorted = false;
        if (heights[i] < heights[min]) min = i;
    }
    if (sorted) {
        int max = heights[start] * (end - start + 1);
        for (int i = start+1; i <= end; i++) {
            max = Math.max(max, heights[i] * (end - i + 1));
        }
        return max;
    }
    return Math.max(Math.max(largest(heights, start, min-1), largest(heights, min+1, end)),
            heights[min] * (end - start + 1));
}


