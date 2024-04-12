229. Majority Element II - Medium


Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.

Note: The algorithm should run in linear time and in O(1) space.

Example 1:

Input: [3,2,3]
Output: [3]
Example 2:

Input: [1,1,1,3,3,2,2,2]
Output: [1,2]


******************************************************
key:
	- Majority vote, use 2 counters for ⌊ n/3 ⌋
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

	-	
	-	1. there are no elements that appears more than n/3 times, then whatever the algorithm 
		   got from 1st round wound be rejected in the second round.
		2. there are only one elements that appears more than n/3 times, after 1st round one of 
		   the candicate must be that appears more than n/3 times(<2n/3 other elements could only
		   pair out for <n/3 times), the other candicate is not necessarily be the second most 
		   frequent but it would be rejected in 2nd round.
		3. there are two elments appears more than n/3 times, candicates would contain both of
		   them. (<n/3 other elements couldnt pair out any of the majorities.)


public List<Integer> majorityElement(int[] nums) {
	if (nums == null || nums.length == 0)
		return new ArrayList<Integer>();
	List<Integer> result = new ArrayList<Integer>();
	int number1 = nums[0], number2 = nums[0], 
	    count1 = 0, count2 = 0, 
	    len = nums.length;

	// use 2 counters to find 2 candidates    
	for (int i = 0; i < len; i++) {
		if (nums[i] == number1)
			count1++;
		else if (nums[i] == number2)
			count2++;
		else if (count1 == 0) {
			number1 = nums[i];
			count1 = 1;
		} else if (count2 == 0) {
			number2 = nums[i];
			count2 = 1;
		} else {
			count1--;
			count2--;
		}
	}
	count1 = 0;
	count2 = 0;

	// check if these 2 candidates are indeed over len/3
	for (int i = 0; i < len; i++) {
		if (nums[i] == number1)
			count1++;
		else if (nums[i] == number2)
			count2++;
	}
	if (count1 > len / 3)
		result.add(number1);
	if (count2 > len / 3)
		result.add(number2);
	return result;
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










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

