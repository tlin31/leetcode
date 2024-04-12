162. Find Peak Element - Medium


A peak element is an element that is greater than its neighbors.

Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.

The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.

You may imagine that nums[-1] = nums[n] = -∞.

Example 1:

Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.


Example 2:

Input: nums = [1,2,1,3,5,6,4]
Output: 1 or 5 
Explanation: Your function can return either index number 1 where the peak element is 2, 
             or index number 5 where the peak element is 6.


******************************************************
key:
	- binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Binary search


Stats:
	Run time: O(logn)
	Memory: constant


Method:

	- if an element(not the right-most one) is smaller than its right neighbor, then there must be 
	  a peak element on its right, because the elements on its right is either 
		   1. always increasing  -> the right-most element is the peak
		   2. always decreasing  -> the left-most element is the peak
		   3. first increasing then decreasing -> the pivot point is the peak
		   4. first decreasing then increasing -> the left-most element is the peak  

	   Therefore, we can find the peak only on its right elements( cut the array to half)

	   The same idea applies to that an element(not the left-most one) is smaller than its left neighbor.



	Conditions:
	     1. array length is 1  -> return the only index 
	     2. array length is 2  -> return the bigger number's index 
	     3. array length is bigger than 2 -> 
	           (1) find mid, compare it with its left and right neighbors  
	           (2) return mid if nums[mid] greater than both neighbors
	           (3) take the left half array if nums[mid] > its right neighbor
	           (4) otherwise, take the left half

         5   5                           5
        / \ / \                         / \
       4   4   4                       4  -∞
      /         \                     /
     3           3           3       3
    /             \         / \     /
   2               2       2   2   2
  /                 \     /     \ /
-∞                   1   1       1
                      \ /
                       0                      
   0 1 2 3 4 5 6 7 8 910111213141516171819 indices
   2,3,4,5,4,5,4,3,2,1,0,1,2,3,2,1,2,3,4,5 (20 nums)
   l                 m                   r l=0, m=9, r=19
   l       m         r                     l=0, m=4, r= 9
             l   m   r                     l=5, m=7, r= 9
             l>m r                         l=5, m=6, r= 7
  return n[l] > n[l + 1])? l : r





public class Solution {
    public int findPeakElement(int[] nums) {
        int l = 0, 
            r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;

            if ((mid-1)>=0 && (mid+1)<=nums.length-1 && 
                (nums[mid]>nums[mid+1]) && (nums[mid]>nums[mid-1]))
                return mid;

            if (nums[mid] > nums[mid + 1])
                r = mid;

            else
                l = mid + 1;
        }
        return l;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


Test cases: 
     [1]
     [1,2]
     [2,1]
     [1,2,3]
     [3,2,1]
     [2,1,3]


def findPeakElement(self, nums):
    left = 0
    right = len(nums)-1

    # handle condition 3
    while left < right-1:
        mid = (left+right)/2
        if nums[mid] > nums[mid+1] and nums[mid] > nums[mid-1]:
            return mid
            
        if nums[mid] < nums[mid+1]:
            left = mid+1
        else:
            right = mid-1
            
    #handle condition 1 and 2
    return left if nums[left] >= nums[right] else right


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

