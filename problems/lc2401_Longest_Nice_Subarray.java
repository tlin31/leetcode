2401. Longest Nice Subarray - Medium

You are given an array nums consisting of positive integers.

We call a subarray of nums nice if the bitwise AND of every pair of elements that are in 
different positions in the subarray is equal to 0.

Return the length of the longest nice subarray.

A subarray is a contiguous part of an array.

Note that subarrays of length 1 are always considered nice.

 

Example 1:

Input: nums = [1,3,8,48,10]
Output: 3
Explanation: The longest nice subarray is [3,8,48]. This subarray satisfies the conditions:
- 3 AND 8 = 0.
- 3 AND 48 = 0.
- 8 AND 48 = 0.
It can be proven that no longer nice subarray can be obtained, so we return 3.

Example 2:

Input: nums = [3,1,5,11,13]
Output: 1
Explanation: The length of the longest nice subarray is 1. Any subarray of length 1 can be chosen.
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:


A sub array is valid when no two elements have same position set bit. Like 1001 and 110 can be 
in one window but not with 1111.

We are checking using AND operation because if no two bit in our window have same set bit then the AND operation will give 0 if not then we need to shrink the window for making it valid for our current element's insertion.

So OR operation is setting those bits on which are already on by any of the element in the window. So if the window becomes invalid then the cur element has some bit which has already been set by any of the previously added element, so we will shrink the window till it becomes valid again by removing the left most element.

When we are removing an element then we are also removing it's set bits so we do the XOR operation of used with the leftmost element to remove it's set bits from consideration.

Why xor is working is because xor will only make those bits 1 which are different. So suppose we have 11001 and we want to remove 9 - 1001 so . 11001^1001 will give 10000 , and we can see that only those bits are off which we wanted to remove.

Then when the window becomes valid we can insert our new element in it by doing OR operation with the used variable , so that we can mark those bits used which are set in cur element.


Stats:

	- 
	- 


public int longestNiceSubarray(int[] nums) {
    int used = 0, j = 0, res = 0;
    for (int i = 0; i < nums.length; ++i) {
        while ((used & nums[i]) != 0)
            used ^= nums[j++];
        used |= nums[i];
        res = Math.max(res, i - j + 1);
    }
    return res;    
}

