280. Wiggle Sort - Medium


Given an unsorted array nums, reorder it in-place such that 
nums[0] <= nums[1] >= nums[2] <= nums[3]....

Example:

Input: nums = [3,5,2,1,6,4]
Output: One possible answer is [3,5,1,6,2,4]


******************************************************
key:
	- find pattern
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Sort


Stats:

	- 
	- 


Method:

	-	
	-	



The obvious solution is to just sort the array first, then swap elements pair-wise starting 
from the second element. 

   [1, 2, 3, 4, 5, 6]
       ↑  ↑  ↑  ↑
       swap  swap

=> [1, 3, 2, 5, 4, 6]


public void wiggleSort(int[] nums) {
    Arrays.sort(nums);
    for (int i = 1; i < nums.length - 1; i += 2) {
        swap(nums, i, i + 1);
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: one pass

Stats:

	- 
	- 


Method:

	-	As we iterate through the array, we compare the current element to its next element and 
	    if the order is incorrect, we swap them.

	-	



public void wiggleSort(int[] nums) {
    boolean less = true;
    for (int i = 0; i < nums.length - 1; i++) {

    	// check for num1 < num2 case
        if (less) {
            if (nums[i] > nums[i + 1]) {
                swap(nums, i, i + 1);
            }
        } 

        // check for num1 > num2 case
        else {
            if (nums[i] < nums[i + 1]) {
                swap(nums, i, i + 1);
            }
        }

        // flip
        less = !less;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

