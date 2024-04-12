523. Continuous Subarray Sum - Medium


Given a list of non-negative numbers and a target integer k, write a function to check if the array 
has a continuous subarray of size at least 2 that sums up to a multiple of k, that is, sums up to 
n*k where n is also an integer.

 

Example 1:

Input: [23, 2, 4, 6, 7],  k=6
Output: True
Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.


Example 2:

Input: [23, 2, 6, 4, 7],  k=6
Output: True
Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
 

Note:

The length of the array wont exceed 10,000.
You may assume the sum of all the numbers is in the range of a signed 32-bit integer.


******************************************************
key:
	- hashmap + prefix sum
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-  We iterate through the input array exactly once, keeping track of the running sum mod k of 
	   the elements in the process. If we find that a running sum value at index j has been previously 
	   seen before in some earlier index i in the array, then we know that the sub-array (i,j] contains a 
	   desired sum.
	- hashmap stores the (remainder, index), so if it has the same remainder, meaning the running sum has
	  added an accumulate k.

	public boolean checkSubarraySum(int[] nums, int k) {
	    Map<Integer, Integer> map = new HashMap<>();
	    map.put(0,-1);

	    int runningSum = 0;
	    for (int i=0;i<nums.length;i++) {
	        runningSum += nums[i];

	        if (k != 0) 
	        	runningSum %= k; 

	        Integer prev = map.get(runningSum);

	        if (prev != null) {
	            if (i - prev > 1) 
	            	return true;
	        }

	        else 
	        	map.put(runningSum, i);
	    }
	    return false;
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

