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
Method 1:

Method:

	-	S1:扩张法

	- 根据两个限定规则：
		所求矩形的第一个坐标点必然是这个二维数组中的某一点，且这一点是'1'
		所求矩形一定是从一个 1*1 的矩形逐渐扩张而来的
		所以，我们可以计算从二维数组中的每个点扩张而成的矩形的面积的最大值，即为待求解。因为已经假定是从某一个点扩张，
			所以它只能选择向右扩张或向下扩张，然后在每一个递归函数中判断是否能够完成扩张：
	-	

Stats:

	- 
	- 



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
method 3:

Method:

	-	This question is similar as [Largest Rectangle in Histogram]:
		You can maintain a row length of Integer array H recorded its height of '1's, and scan 
		and update row by row to find out the largest rectangle of each row.

		For each row, if matrix[row][i] == '1'. H[i] +=1, or reset the H[i] to zero.
		and accroding the algorithm of [Largest Rectangle in Histogram], to update the maximum area.
	-	


Stats:

	- O(n^2)
	- 


class Solution {
    public int maximalRectangle(char[][] matrix) {
       int rLen = matrix.length, cLen = rLen == 0 ? 0 : matrix[0].length, max = 0;
        int[] h = new int[cLen+1];
   
        for (int row = 0; row < rLen; row++) {
            Stack<Integer> s = new Stack<Integer>();
            s.push(-1);
            for (int i = 0; i <= cLen ;i++) {
                if(i < cLen && matrix[row][i] == '1')
                    h[i] += 1;
                else h[i] = 0;

                while(s.peek() != -1 && h[i] < h[s.peek()]) {
                    max = Math.max(max, h[s.pop()] * (i - s.peek() - 1));
                }
                s.push(i);
            }
        }
        return max;
    }
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


