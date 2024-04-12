283. Move Zeroes - Easy

Given an array nums, write a function to move all 0s to the end of it while maintaining the relative
order of the non-zero elements.

Example:

Input: [0,1,0,3,12]
Output: [1,3,12,0,0]

Note:
You must do this in-place without making a copy of the array.
Minimize the total number of operations.


******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 2 pointer

Method:

	-  fast pointer which is denoted by variable "cur" does the job of processing new elements. 
	   If the newly found element is not a 0, we record it just after the last found non-0 element. 
	   The position of last found non-0 element is denoted by the slow pointer "lastNonZeroFoundAt"
	   variable. As we keep finding new non-0 elements, we just overwrite them at the 
	   "lastNonZeroFoundAt + 1" th index. 

	   This overwrite will not result in any loss of data because we already processed what was 
	   there(if it were non-0,it already is now written at its corresponding index,or if it were 
	   0 it will be handled later in time).

	-  After the "cur" index reaches the end of array, we now know that all the non-0 elements have 
	   been moved to beginning of array in their original order. Now comes the time to fulfil other
	   requirement, "Move all 0's to the end". We now simply need to fill all the indexes after the
	   "lastNonZeroFoundAt" index with 0.



Stats:

	- O(n)
	- O(1)

public void moveZeroes(int[] nums) {

    if(nums == null || nums.length == 0){
        return;
    }
    
    int prevZero = 0;

    for(int i = 0; i < nums.length; i++){
        if(nums[i] != 0){
            nums[prevZero] = nums[i];
            prevZero++;
        }
    }
    
    // fill up the rest of the array
    for(int m = prevZero; m < nums.length; m++){
        nums[m] = 0;
    }
    
    return;
} 

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 



