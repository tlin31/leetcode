34. Find First and Last Position of Element in Sorted Array

Given an array of integers nums sorted in ascending order, find the starting and 
ending position of a given target value.

Your algorithms runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example 1:
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]

Example 2:
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]


******************************************************
key:
    - modify binary search
    - bias
    - edge case:
        1) didnt find -> return [-1,-1] --> loop variant, while start < end
        2) if empty list, return [-1,-1]

******************************************************


=======================================================================================================
method 1:

method:
    - 2 binary search
    - modified:



class Solution {
    public int[] searchRange(int[] nums, int target) {
    int[] result = new int[2];
    result[0] = findFirst(nums, target);
    result[1] = findLast(nums, target);
    return result;
}

private int findFirst(int[] nums, int target){
	// always bias to the left
    int idx = -1;
    int start = 0;
    int end = nums.length - 1;
    while(start <= end){
        int mid = (start + end) / 2;

        // different
        if(nums[mid] >= target){
            end = mid - 1;
        }else{
            start = mid + 1;
        }
        if(nums[mid] == target) idx = mid;
    }
    return idx;
}

	private int findLast(int[] nums, int target){
	    int idx = -1;
	    int start = 0;
	    int end = nums.length - 1;
	    while(start <= end){
	        int mid = (start + end) / 2;

	        // different
	        if(nums[mid] <= target){
	            start = mid + 1;
	        }else{
	            end = mid - 1;
	        }
	        if(nums[mid] == target) idx = mid;
	    }
	    return idx;
	}
}

~~~~~~~~~~~~~~  python  ~~~~~~~~~~
        
    def searchRange(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        if not nums:
            return [-1, -1]

        def bisect_left(nums, target):
            l, r = 0, len(nums) - 1
            while l < r:
                # always get the left most
                m = (l + r) // 2
                if nums[m] < target:
                    l = m + 1
                else:
                    r = m

            return l if nums[l] == target else -1

        def bisect_right(nums, target):
            l, r = 0, len(nums) - 1
            while l < r:

                # avoid from meeting with left
                m = (l + r) // 2 + 1
                if nums[m] > target:
                    r = m - 1
                else:
                    l = m
            return l if nums[l] == target else -1

        return [bisect_left(nums, target), bisect_right(nums, target)]


=======================================================================================================
method 2:

method:
    - a universal method, use the 0.5 to make it more biased.
bs(nums, target - 0.5) → 第一个 ≥ target - 0.5 的位置，也就是 target 第一次出现的位置。

bs(nums, target + 0.5) → 第一个 ≥ target + 0.5 的位置，也就是 target 最后一次出现之后的位置。

ex. 所有 < 7.5 的数都算小；所有 > 8.5 的数都算大；
这样二分就能准确分出区间 [7.5, 8.5)，从而锁定所有 == 8 的元素。


public int[] searchRange(int[] nums, int target) {
        double left = target - 0.5, right = target + 0.5;
        int l = bs(nums, left), r = bs(nums, right);
        if(l == r) return new int[]{-1, -1};
        return new int[]{l, r-1};
}
    
public int bs(int[] nums, double target) {
            int l = 0, h = nums.length-1;
            while(l <= h){
                int m = l + (h - l)/2;
                if(target > nums[m]) 
                    l = m+1;
                else 
                    h = m-1;
            }
            return l;
}




=======================================================================================================
method 3:

method:
    - optimize, end the loop earlier, ex. if always let end = mid - 1, then when mid = target
      dont need to go over the loop again
    - 如果我们找最左边等于 target 的，此时 mid 的位置已经是我们要找的了，而解法二更新成了 end = mid - 1，
      然后继续循环了，而此时我们其实完全可以终止了。只需要判断 nums[ mid - 1] 是不是小于 nums [ mid ] ，
      如果小于就刚好是我们要找的了。

public int[] searchRange(int[] nums, int target) {
    int start = 0;
    int end = nums.length - 1;
    int[] ans = { -1, -1 };
    if (nums.length == 0) {
        return ans;
    }
    while (start <= end) {
        int mid = (start + end) / 2;
        if (target == nums[mid]) {
            int n = mid > 0 ? nums[mid - 1] : Integer.MIN_VALUE;

            //changed!!
            //除了小于 n，还判断了当前是不是在两端。
            if (target > n || mid == 0) {
                ans[0] = mid;
                break;
            }
            end = mid - 1;
        } else if (target < nums[mid]) {
            end = mid - 1;
        } else {
            start = mid + 1;
        }
    }
    start = 0;
    end = nums.length - 1;
    while (start <= end) {
        int mid = (start + end) / 2;
        if (target == nums[mid]) {
            int n = mid < nums.length - 1 ? nums[mid + 1] : Integer.MAX_VALUE;
            if (target < n || mid == nums.length - 1) {
                ans[1] = mid;
                break;
            }
            start = mid + 1;
        } else if (target < nums[mid]) {
            end = mid - 1;
        } else {
            start = mid + 1;
        }
    }
    return ans;
}
