153. Find Minimum in Rotated Sorted Array - Medium

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

You may assume no duplicate exists in the array.

Example 1:

Input: [3,4,5,1,2] 
Output: 1



Example 2:
Input: [4,5,6,7,0,1,2]
Output: 0

******************************************************
key:
	- binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
method 2:

method:

	- The minimum element must satisfy one of two conditions: 
			1) If rotate, A[min] < A[min - 1]; 
			2) If not, A[0]. 
	- binary search: check the middle element, 
			if mid < previous one, then it is minimum. 
	   		else: 
	   			// [6,7,0] 中的7
	   			If it is greater than both left and right element, then min is on its right

	   			else on its left.


	- 

stats:

	- 
	- 

public class Solution {
    public int findMin(int[] num) {
        if (num == null || num.length == 0) {
            return 0;
        }

        if (num.length == 1) {
            return num[0];
        }

        int start = 0, 
            end = num.length - 1;

        while (start < end) {
            int mid = (start + end) / 2;
            if (mid > 0 && num[mid] < num[mid - 1]) {
                return num[mid];
            }

            if (num[start] <= num[mid] && num[mid] > num[end]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return num[start];
    }
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



