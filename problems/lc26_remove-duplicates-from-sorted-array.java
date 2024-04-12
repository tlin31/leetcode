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

