1574. Shortest Subarray to be Removed to Make Array Sorted - Medium

Given an integer array arr, remove a subarray (can be empty) from arr such that the remaining 
elements in arr are non-decreasing.

Return the length of the shortest subarray to remove.

A subarray is a contiguous subsequence of the array.

 

Example 1:

Input: arr = [1,2,3,10,4,2,3,5]
Output: 3
Explanation: The shortest subarray we can remove is [10,4,2] of length 3. The remaining elements 
after that will be [1,2,3,3,5] which are sorted.
Another correct solution is to remove the subarray [3,10,4].

Example 2:

Input: arr = [5,4,3,2,1]
Output: 4
Explanation: Since the array is strictly decreasing, we can only keep a single element. Therefore we need to remove a subarray of length 4, either [5,4,3,2] or [4,3,2,1].
Example 3:

Input: arr = [1,2,3]
Output: 0
Explanation: The array is already non-decreasing. We do not need to remove any elements.
 

Constraints:

1 <= arr.length <= 105
0 <= arr[i] <= 109

******************************************************
key:
	- 2 pointers
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	We should look for any parts of the array that are already sorted and try to retain as much 
	of that as possible.
	If we have a long sorted prefix (from the start) and a long sorted suffix (from the end), we 
	might only need to remove a small subarray in between to make the entire array sorted.

Steps:

	Find the longest non-decreasing prefix from the beginning of the array. 
	Let's call this left. This gives us the starting part of the array that’s already sorted.

	Find the longest non-decreasing suffix from the end of the array. 
	Let's call this right. This gives us the ending part of the array that’s already sorted.

	Check if the entire array is sorted by verifying if left spans the whole array. 
	If so, the answer is 0 because we don't need to remove anything.

	Calculate minimum removal:

	  Initially, consider removing either the entire suffix (n - left - 1 elements) or the 
	  entire prefix (right elements).
	
	  Use a two-pointer technique to see if we can merge parts of the prefix and suffix by 
	  skipping the smallest possible middle section. This allows us to explore shorter subarrays 
	  to remove.
	
	Return the result which is the minimum number of elements we need to remove.



Stats:

	- Time complexity: (O(n)), since we’re only scanning the array a few times (once for the prefix, once for the suffix, and once for merging with two pointers).

	- Space complexity: (O(1)), as we’re only using a few extra variables for indices and result calculation.


class Solution {
    public int findLengthOfShortestSubarray(int[] arr) {
        int n = arr.length;
        
        // Step 1: Find the longest non-decreasing prefix
        int left = 0;
        while (left + 1 < n && arr[left] <= arr[left + 1]) {
            left++;
        }
        
        // If the entire array is already sorted
        if (left == n - 1) return 0;
        
        // Step 2: Find the longest non-decreasing suffix
        int right = n - 1;
        while (right > 0 && arr[right - 1] <= arr[right]) {
            right--;
        }
        
        // Step 3: Find the minimum length to remove by comparing prefix and suffix
        int result = Math.min(n - left - 1, right);
        
        // Step 4: Use two pointers to find the smallest middle part to remove
        int i = 0, j = right;
        while (i <= left && j < n) {
            if (arr[i] <= arr[j]) {
            	//是递增的，缩小window，update result
                result = Math.min(result, j - i - 1);
                i++;
            } else {
            	//违反递增规律，只能扩大window的范围
                j++;
            }
        }
        
        return result;
    }
}









