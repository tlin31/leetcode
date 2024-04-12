11. Container With Most water---Medium

https://leetcode.com/problems/container-with-most-water/

Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). 
n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
Find two lines, which together with x-axis forms a container, such that the container contains the 
most water.

Note: You may not slant the container and n is at least 2.

Example:
Input: [1,8,6,2,5,4,8,3,7]
Output: 49

===============================================================================================================================
method 1:

key:
	  - two pointers initialized at both ends of the array. Every time move the smaller value pointer 
	  	to inner array. Then after the two pointers meet, all possible max cases have been scanned 

	  - Given a1,a2,a3.....an as input array. Lets assume a10 and a20 are the max area situation. We need to 
	   	prove that a10 can be reached by left pointer and during the time left pointer stays at a10, a20 can 
	   	be reached by right pointer. That is to say, the core problem is to prove: when left pointer is at a10 
	   	and right pointer is at a21, the next move must be right pointer to a20.

	  - Since we are always moving the pointer with the smaller value, i.e. if a10 > a21, we should move pointer 
	    at a21 to a20, as we hope. Why a10 >a21? Because if a21>a10, then area of a10 and a20 must be less than 
	    area of a10 and a21. Because the area of a10 and a21 is at least height[a10] * (21-10) while the area of 
	    a10 and a20 is at most height[a10] * (20-10). So there is a contradiction of assumption a10 and a20 has 
	    the max area. So, a10 must be greater than a21, then next move a21 has to be move to a20. The max cases 
	    must be reached.



//Runtime: 2 ms, faster than 94.38% of Java online submissions for Container With Most Water.
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1;
	int maxArea = 0;
	while (left < right) {
		maxArea = Math.max(maxArea, Math.min(height[left], height[right])* (right - left));

		// always good to keep the taller one
		if (height[left] < height[right])
			left++;
		else
			right--;
	}

	return maxArea;
}