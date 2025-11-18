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

Find the mid element of the array.

If mid element > first element of array this means that we need to look for the inflection 
point on the right of mid.

If mid element < first element of array this means that we need to look for the inflection 
point on the left of mid.
	

 We stop our search when we find the inflection point, when either of the two conditions is satisfied:

nums[mid] > nums[mid + 1] Hence, mid+1 is the smallest.

nums[mid - 1] > nums[mid] Hence, mid is the smallest.


ex. 7     2    3
    left mid right
stats:

	- 
	- 

class Solution {
    public int findMin(int[] nums) {
        // If the list has just one element then return that element.
        if (nums.length == 1) {
            return nums[0];
        }

        // initializing left and right pointers.
        int left = 0, right = nums.length - 1;

        // if the last element is greater than the first element then there is no
        // rotation.
        // e.g. 1 < 2 < 3 < 4 < 5 < 7. Already sorted array.
        // Hence the smallest element is first element. A[0]
        if (nums[right] > nums[0]) {
            return nums[0];
        }

        // Binary search way
        while (right >= left) {
            // Find the mid element
            int mid = left + (right - left) / 2;

            // if the mid element is greater than its next element then mid+1 element is the
            // smallest
            // This point would be the point of change. From higher to lower value.
            if (nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }

            // if the mid element is lesser than its previous element then mid element is
            // the smallest
            if (nums[mid - 1] > nums[mid]) {
                return nums[mid];
            }

            // if the mid elements value is greater than the 0th element this means
            // the least value is still somewhere to the right as we are still dealing with
            // elements greater than nums[0]
            if (nums[mid] > nums[0]) {
                left = mid + 1;
            } else {
                // if nums[0] is greater than the mid value then this means the smallest value
                // is somewhere to the left
                right = mid - 1;
            }
        }
        return Integer.MAX_VALUE;
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



