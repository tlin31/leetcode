80. Remove Duplicates from Sorted Array II - Medium


Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most 
twice and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place 
with O(1) extra memory.

Example 1:

Given nums = [1,1,1,2,2,3],

Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 
respectively.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,0,1,1,1,1,2,3,3],

Your function should return length = 7, with the first seven elements of nums being modified to 0, 
0, 1, 1, 2, 3 and 3 respectively.

It doesn't matter what values are set beyond the returned length.
Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array 
will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeDuplicates(nums);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}



******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************


将当前快指针遍历的数字和慢指针指向的数字的前一个数字比较（也就是满足条件的倒数第 2 个数），如果相等，因为有序，
所有倒数第 1 个数字和倒数第 2 个数字都等于当前数字，再添加就超过 2 个了，所有不添加，如果不相等，那么就添加


通用快慢双指针 (General Fast & Slow Pointers)
    这道题的核心技巧是：比较快指针当前指向的元素与“已确定序列”倒数第 K 个元素。 在本题中 K=2。

逻辑步骤 (Logic Steps)：
1. 初始化：如果数组长度 N<2，直接返回长度。

2. 慢指针 (slow)：代表当前有效序列的长度（也指向下一个待插入的位置）。
   由于前两个元素无论是否重复都符合要求，我们从索引 2 开始。

3. 快指针 (fast)：从索引 2 开始遍历整个数组。

4. 判定条件：如果 nums[fast] != nums[slow - 2]，说明当前元素与有效序列中倒数第二个元素不同，符合“最多重复两次”的
   规则。
    将 nums[fast] 复制到 nums[slow]。
    slow 指针后移。

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        
        // slow 指针表示下一个可以放置元素的位置
        // 前两个元素 [0, 1] 默认保留
        int slow = 2;
        
        for (int fast = 2; fast < nums.length; fast++) {
            // 关键：检查当前元素是否与“确定序列”的倒数第二个元素相同
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        return slow;
    }
}



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	- 	We define two pointers, i and j for our algorithm. The pointer i iterates of the array 		
		processing one element at a time and j keeps track of the next location in the array where we can overwrite an element.

		We also keep a variable count which keeps track of the count of a particular element in the 
		array. Note that the minimum count would always be 1.

		We start with index 1 and process one element at a time in the array.

		If we find that the current element is the same as the previous element i.e. nums[i] == nums[i 
		- 1], then we increment the count. If the value of count > 2, then we have encountered an 
		unwanted duplicate element. In this case, we simply move forward i.e. we increment i but not j.

		However, if the count is <= 2, then we can move the element from index i to index j.


		If we encounter that the current element is not the same as the previous element i.e. nums[i] 
		!= nums[i - 1], then it means we have a new element at hand and so accordingly, we update count 
		= 1 and also move this element to index j.




class Solution {
    
    public int removeDuplicates(int[] nums) {
        
        // j records the unwanted dup position in previous nums
        int j = 1, count = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1]) {
                
                count++;
                
            } else {
                
                count = 1;
            }

            if (count <= 2) {
                nums[j++] = nums[i];
            }
        }
        return j;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution(object):
    def removeDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        
        # Initialize the counter and the second pointer.
        j, count = 1, 1
        
        # Start from the second element of the array and process
        # elements one by one.
        for i in range(1, len(nums)):
            
            # If the current element is a duplicate, 
            # increment the count.
            if nums[i] == nums[i - 1]:
                count += 1
            else:
                # Reset the count since we encountered a different element
                # than the previous one
                count = 1
            
            # For a count <= 2, we copy the element over thus
            # overwriting the element at index "j" in the array
            if count <= 2:
                nums[j] = nums[i]
                j += 1
                
        return j


