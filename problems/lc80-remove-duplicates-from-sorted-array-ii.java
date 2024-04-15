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

public int removeDuplicates2(int[] nums) {
    int slow = 1;
    int fast = 2;
    int max = 2;
    for (; fast < nums.length; fast++) {
        //不相等的话就添加
        if (nums[fast] != nums[slow - max + 1]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    return slow + 1;
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


