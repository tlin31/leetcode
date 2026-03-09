26. Remove Duplicates from Sorted Array --- Easy

Given a sorted array nums, remove the duplicates in-place such that each element appear only 
once and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

Example 1:

Given nums = [1,1,2],

Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.

It doesnt matter what you leave beyond the returned length.

Example 2:

Given nums = [0,0,1,1,1,2,2,3,3,4],

Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.


Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeDuplicates(nums);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}

=========================================================================================================================================================

1. 核心算法方案：快慢双指针 (Fast & Slow Pointers) 

由于数组是已排序 (Sorted) 的，重复的元素必然相邻。我们使用两个指针来处理： 
慢指针 (slow)：指向当前已去重部分的最后一个元素。
快指针 (fast)：负责向后扫描寻找新的唯一元素。 


逻辑步骤 (Logic Steps)：
1. 如果 nums[fast] != nums[slow]：说明找到了一个新元素。
2. 将慢指针后移一位 slow++。
3. 将快指针指向的新元素复制到慢指针位置 nums[slow] = nums[fast]。 


class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // slow 指针记录唯一元素的边界 (The boundary of unique elements)
        int slow = 0;
        
        for (int fast = 1; fast < nums.length; fast++) {
            // 发现新元素 (New unique element found)
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        
        // 返回唯一元素的个数 (Return count of unique elements),因为slow从0开始的
        return slow + 1;
    }
}

class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        if not nums:
            return 0
        
        # slow 维护当前“非重复序列”的末尾
        slow = 0
        
        for fast in range(1, len(nums)):
            # 只有当快指针发现不同数值时，才更新慢指针位置
            if nums[fast] != nums[slow]:
                slow += 1
                nums[slow] = nums[fast]
                
        return slow + 1




=========================================================================================================================================================
method 1:

key:
	- count = num of duplicates (all)
	- when assign non-duplicates, put it in the first place of the duplicate

// Runtime: 1 ms, faster than 97.48% of Java online submissions for Remove Duplicates from Sorted Array.
// Memory Usage: 39.1 MB, less than 99.47% 

    public int removeDuplicates(int[] nums) {
        int count = 0;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) count++;
            else nums[i - count] = nums[i];
        }
        return n - count;
    }


=========================================================================================================================================================
method 2:

key:
	- use loop variant, always keep an eye on the prev number
	- use the property that array is sorted, so use n > nums[i - 1]
	- use i to keep track of where to put the next unique number

public int removeDuplicates(int[] nums) {
    int i = 0;
    for (int n: nums) {
        if (i == 0 || n > nums[i - 1])
            nums[i++] = n;
    }
    return i;
}


public int removeDuplicates(int[] nums) {
    int i = nums.length > 0 ? 1 : 0;
    for (int n: nums)
        if (n > nums[i - 1])
            nums[i++] = n;
    return i;
}

