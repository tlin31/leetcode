303. Range Sum Query - Immutable - Easy

Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

Example:
Given nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3

Note:
You may assume that the array does not change.
There are many calls to sumRange function.


******************************************************
key:
	- prefix sum
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Prefix sum

Method:

	-	Notice in the code above we inserted a dummy 0 as the first element in the sum array. 
	-	This trick saves us from an extra conditional check in sumRange function.

Stats:

	- Time complexity : O(1) time per query, O(n) time pre-computation. Since the cumulative sum is 
	                    cached, each sumRange query can be calculated in O(1) time.

	- Space complexity : O(n).


private int[] sum;

public NumArray(int[] nums) {
    sum = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
        sum[i + 1] = sum[i] + nums[i];
    }
}

public int sumRange(int i, int j) {
    return sum[j + 1] - sum[i];
}

Complexity analysis




=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



