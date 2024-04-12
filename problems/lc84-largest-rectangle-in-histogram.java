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




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	- We traverse all bars from left to right, maintain a stack of bars. 
	  Every bar is pushed to stack once. A bar is popped from stack when a bar of smaller height 
	  is seen. When a bar is popped, we calculate the area with the popped bar as smallest bar. 
	  How do we get left and right indexes of the popped bar –-> the current index tells us the 
	  ‘right index’ and index of previous item in stack is the ‘left index’. 

		1) Create an empty stack.

		2) Start from 1st bar, and do following for every bar ‘hist[i]’ where ‘i’ varies from 0 to n-1.
			a) If stack is empty or cur > bar at top of stack, then push i to stack.
			b) If this bar < the top of stack, then keep removing the top of stack while top of 
			   the stack is greater. 
			   Let the removed bar be hist[top of stack]. 
			   Calculate area of rectangle with hist[top of stack] as smallest bar. 
			   For hist[top of stack],‘left index’ = previous top in stack,right index = cur i

		3) If the stack is not empty, then one by one remove all bars from stack and do step 2.b 
		   for every removed bar.


static int getMaxArea(int hist[], int n)  
    { 
        // Create an empty stack. The stack holds indexes of hist[] array 
        // The bars stored in stack are always in increasing order of their 
        // heights. 
        Stack<Integer> s = new Stack<>(); 
          
        int max_area = 0; // Initialize max area 
        int tp;  // To store top of stack 
        int area_with_top; // To store area with top bar as the smallest bar 
       
        // Run through all bars of given histogram 
        int i = 0; 
        while (i < n) 
        { 
            // If this bar is higher than the bar on top stack, push it to stack 
            if (s.empty() || hist[s.peek()] <= hist[i]) 
                s.push(i++); 
       
            // If this bar is lower than top of stack, then calculate area of rectangle  
            // with stack top as the smallest (or minimum height) bar. 'i' is  
            // 'right index' for the top & i is unchanged!
            // element before top in stack is 'left index' 
            else
            { 
                tp = s.peek();  // store the top index 
                s.pop();  // pop the top 
       
                // Calculate the area with hist[tp] stack as smallest bar 
                area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1); 
       
                // update max area, if needed 
                if (max_area < area_with_top) 
                    max_area = area_with_top; 
            } 
        } 
       
        // Now pop the remaining bars from stack and calculate area with every 
        // popped bar as the smallest bar 
        while (s.empty() == false) 
        { 
            tp = s.peek(); 
            s.pop(); 
            area_with_top = hist[tp] * (s.empty() ? i : i - s.peek() - 1); 
       
            if (max_area < area_with_top) 
                max_area = area_with_top; 
        } 
       
        return max_area; 
  
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

