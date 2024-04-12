35. Search Insert Position	- Easy

Given a sorted array and a target value, return the index if the target is found. 
If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Example 1:

Input: [1,3,5,6], 5
Output: 2
Example 2:

Input: [1,3,5,6], 2
Output: 1
Example 3:

Input: [1,3,5,6], 7
Output: 4
Example 4:

Input: [1,3,5,6], 0
Output: 0

******************************************************
key:
	- binary search 
	- edge case
		1) empty input array --> return 0, 
	- loop variant

https://leetcode.wang/leetCode-35-Search-Insert-Position.html
******************************************************



=======================================================================================================
method 1:

method:

	- 数组剩下偶数个元素的时候，中点取的是左端点。例如 1 2 3 4，中点取的是 2 (b/c int(lo+hi)/2)。正因为如此，
      我们更新 start 的时候不是直接取 mid ，而是 mid + 1。因为剩下两个元素的时候，mid 和 start 是相同的，
      如果不进行加 1 会陷入死循环。

stats:
	- 时间复杂度：O（log（n））。
	- 空间复杂度：O（1）。

public int searchInsert(int[] A, int target) {
    int low = 0, high = A.length - 1;
    while (low <= high) {
        int mid = (low + high) / 2;
        if (A[mid] == target) return mid;
        else if (A[mid] > target) high = mid - 1;
        else low = mid + 1;
    }
    return low;
} 


method 2:

	- 我们开始更新 start 的时候，是 mid + 1，如果剩两个元素，例如 2 4，target = 6 的话，此时 mid = 0，
	  start = mid + 1 = 1，我们返回 start + 1 = 2。如果 mid 是右端点，那么 mid = 1，start = mid + 1 = 2，
	  这样就可以直接返回 start 了，不需要在返回的时候加 1 了。

	- --> 最最开始的时候我们取 end 的时候是 end = nums.length - 1。如果我们改成 end = nums.length，这样每次取
	  元素的时候，如果和之前对比，取到的就是右端点了。这样的话，最后返回的时候就不需要多加 1 了。

public int searchInsert(int[] nums, int target) {
    int start = 0;
    int end = nums.length;
    if (nums.length == 0) {
        return 0;
    }
    while (start < end) {
        int mid = (start + end) / 2;
        if (target == nums[mid]) {
            return mid;
        } else if (target < nums[mid]) {
            end = mid;
        } else {
            start = mid + 1;
        }
    }

    return start;

}


-----------------   python  -------------------

    def searchInsert(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: int
        """
        return bisect.bisect_left(nums, target)


    def searchInsert(self, nums, target):
        low = 0
        high = len(nums) - 1
        while low <= high:
            mid = (low + high) / 2
            if nums[mid] == target:
                return mid
            elif nums[mid] < target:
                low = mid + 1
            else:
                high = mid - 1
        return low







