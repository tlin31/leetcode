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

计算面积：对于每一根柱子，只需找到它左边和右边第一个比它矮的柱子索引，即可确定以此柱子高度为准的最大矩形宽度，从而计算出面积。

2. 单调栈的作用
    为了快速找到左右两侧第一个比当前柱子矮的柱子的位置，我们需要用单调递增栈。
    • 栈内保存柱子的索引。
    • 遍历柱子，当遇到比栈顶元素矮的柱子时，说明栈顶柱子“向右延伸的边界“已经找到了。
    • 此时可以将栈顶元素弹出，栈顶元素左侧的柱子就是它的左边界，当前遍历到的柱子就是它的右边界。

3.算法步骤
    1. 初始化：创建一个栈（用于存储索引），并在原数组的首尾各添加一个高度为0的柱子。这样做是为了统一边界处理，确保栈中所有元素在遍历结束后都能进行面积结算。
    2. 遍历数组：
        1. 若当前柱子的高度大于或等于栈顶柱子的高度，将当前索引入栈（维持栈的单调递增性）。
        2. 若当前柱子的高度小于栈顶柱子的高度，说明栈顶柱子的右边界已确定（即当前柱子）。

    3. 计算与更新：
        1. 将栈顶元素弹出（设为 H），此时H 就是我们要计算矩形高度的柱子。
        2. 矩形的高 = 原数组中的H。
        3. 矩形的宽 = 当前柱子的索引 - 栈中新的栈顶元素索引 -1。
        4. 计算面积并尝试更新最大面积记录。

    重复步骤 2 和 3，直到遍历结束。



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


heights = [2,1,5,6,2,3]
stack = []  (index, height) pairs

i=0: h=2, stack=[] → push (0,2) → stack=[(0,2)]
i=1: h=1, stack top h=2 > 1 → pop (0,2)
     width = 1 - (-1) - 1 = 1  (no element left of stack, use -1)
     wait — stack is now empty, so left boundary = -1
     area = 2 * (1 - (-1) - 1) = 2 * 1 = 2
     start = 0  ← remember leftmost index popped!
     stack=[] → push (0, 1)  ← use start=0, not i=1!
     
i=2: h=5 > 1 → push (2,5) → stack=[(0,1),(2,5)]
i=3: h=6 > 5 → push (3,6) → stack=[(0,1),(2,5),(3,6)]
i=4: h=2, stack top h=6 > 2 → pop (3,6)
     start = 3
     stack top now (2,5) > 2 → pop (2,5)
     start = 2
     area = 5 * (4 - 2) = 5 * 2 = 10 ✅ best!
     stack top now (0,1) ≤ 2 → stop
     push (2, 2)  ← use start=2!  → stack=[(0,1),(2,2)]

i=5: h=3 > 2 → push (5,3) → stack=[(0,1),(2,2),(5,3)]

End of array — drain remaining stack:
  pop (5,3): area = 3 * (6 - 5) = 3 * 1 = 3
  pop (2,2): area = 2 * (6 - 2) = 2 * 4 = 8
  pop (0,1): area = 1 * (6 - 0) = 1 * 6 = 6

→ best = 10 ✅



=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

