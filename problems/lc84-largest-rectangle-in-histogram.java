84. Largest Rectangle in Histogram - Hard


Given n non-negative integers representing the histogram bar height where the width of each 
bar is 1, find the area of largest rectangle in the histogram.

https://leetcode.com/problems/largest-rectangle-in-histogram/

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].


The largest rectangle is shown in the shaded area, which has area = 10 unit.

 

Example:

Input: [2,1,5,6,2,3]
Output: 10


******************************************************
key:
	- divide & conquer, or Stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: divide & conquerer


Stats:
	- Time complexity :
		Average Case: O(nlogn).
		Worst Case: O(n^2) If the numbers in the array are sorted, we do not gain the advantage 
		of divide and conquer.	
	- Space complexity : O(n). Recursion with worst case depth n



Method:

	- find the minimum value in the given array. Once we have index of the minimum value, the max 
	  area is maximum of following three values.
		a) Maximum area in left side of minimum value (Not including the min value)
		b) Maximum area in right side of minimum value (Not including the min value)
		c) Number of bars multiplied by minimum value.

	- The areas in left and right of minimum value bar can be calculated recursively. 
	  If we use linear search to find the minimum value, then the worst case time complexity of this 
	  algorithm becomes O(n^2). 
	  In worst case, we always have (n-1) elements in one side and 0 elements in other side and if the 
	  finding minimum takes O(n) time, we get the recurrence similar to worst case of Quick Sort.

	- How to find the minimum efficiently? 
		we can use Range Minimum Query using Segment Tree can be used for this. 
		We build segment tree of the given histogram heights. Once the segment tree is built, all range 
		minimum queries take O(Logn) time



public class Solution {
    public int calculateArea(int[] heights, int start, int end) {
        if (start > end)
            return 0;
        int minindex = start;
        for (int i = start; i <= end; i++)
            if (heights[minindex] > heights[i])
                minindex = i;

        return Math.max(heights[minindex] * (end - start + 1), 
                       Math.max(calculateArea(heights, start, minindex - 1), 
                                calculateArea(heights, minindex + 1, end)));
    }

    public int largestRectangleArea(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }
}







=======================================================================================================
method 2:

Stats:

	- O(n)
	- 


Method:

用单调递增栈存放柱子索引

当出现“破坏递增”的柱子时，把栈顶柱子弹出，并计算它作为最矮柱子时的最大矩形面积。

因为：栈内柱子高度递增，当遇到一个更矮的柱子 h 时，说明栈顶柱子不能再往右扩展了，所以应该在这里结算它的最大面积


假设 heights = [2, 1, 5, 6, 2, 3]

我们扫描到 2（i= 4）时，发现：5 和 6 都比 2 高 → 他们右边界遇到限制了 → 必须计算面积


例如：

对柱子 6：左右伸展边界是 [5,6] → 宽度=1

对柱子 5：左右伸展边界是 [5,6,2] → 宽度=2

class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] newHeights = Arrays.copyOf(heights, n + 1);
        newHeights[n] = 0;  // 添加哨兵

        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        for (int i = 0; i < newHeights.length; i++) {
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                int h = newHeights[stack.pop()]; // 当前柱子高度

                int right = i;                   // 右边界（遇到更矮的了）
                int left = stack.isEmpty() ? -1 : stack.peek();  // 左边界

                int width = right - left - 1;    // 宽度
                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }

        return maxArea;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def largestRectangleArea(self, height):
    height.append(0)
    stack = [-1]
    ans = 0
    for i in xrange(len(height)):
        while height[i] < height[stack[-1]]:
            h = height[stack.pop()]
            w = i - stack[-1] - 1
            ans = max(ans, h * w)
        stack.append(i)
    height.pop()
    return ans


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

