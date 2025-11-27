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

思路：

	找最长的 从左到右非递减前缀

	找最长的 从右到左非递减后缀

	尝试把前缀和后缀“拼接”起来，看能否保持有序

	在所有删除方案中取最短

	为什么可行？
	因为你最终剩下的数组必然形如：

	[非递减前缀] + [非递减后缀]


	中间被删除的部分是连续的一段。

尝试“拼接前缀 + 后缀”

	对于前缀每个位置 i，我们尝试匹配右边一个位置 j，使：

	arr[i] <= arr[j]


	这样保留 [0..i] + [j..n-1] 是有序的。

	删除长度是：delete = j - i - 1

	移动两个指针 i（从左 prefix）和 j（从右 suffix）。

	找最小的 delete。


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
        
        // Step 3: Find the minimum length if remove entire prefix or suffix
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









