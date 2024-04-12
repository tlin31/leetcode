674. Longest Continuous Increasing Subsequence - Easy

Given an unsorted array of integers, find the length of longest continuous increasing subsequence 
(subarray).

Example 1:
Input: [1,3,5,4,7]
Output: 3
Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3. 
Even though [1,3,5,7] is also an increasing subsequence, it is not a continuous one where 5 and 7 are 
separated by 4. 


Example 2:
Input: [2,2,2,2,2]
Output: 1
Explanation: The longest continuous increasing subsequence is [2], its length is 1. 


Note: Length of the array will not exceed 10,000.
******************************************************
key:
	- 2 pointers/sliding window
	- edge case:
		1) empty array, return ?
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- sliding window
	- 

stats:

	- 
	- 

public int findLengthOfLCIS(int[] nums) {
        if(nums.length==0) return 0;
        int start = 0, 
            end = 0,
        	max = 0;
        while(end<nums.length)
        {
            if(end > 0 && nums[end] <= nums[end-1])
                start = end;

            int len = end - start + 1;
            max = Math.max(len, max);            
            end++;
        }
        return max;        
    }


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



