213. House Robber II - Medium

Question: can not have 2 adj house


Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. 
That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security
system connected and it will automatically contact the police if two adjacent houses were broken into 
on the same night.


Given a list of non-negative integers representing the amount of money of each house, determine the
maximum amount of money you can rob tonight without alerting the police.


Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.
Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.


******************************************************
key:
	- DP, knapsack problem, need to check when reach end of the house
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

	-  rob helper is the original rob / DP problem
	-  if house i is not robbed, then you are free to choose whether to rob house i + 1, you can break
	    the circle by assuming a house is not robbed.

		For example, 1 -> 2 -> 3 -> 1 becomes 2 -> 3 if 1 is not robbed.

	-  Since every house is either robbed or not robbed and at least half of the houses are not robbed, 
	   the solution is  the larger of two cases with consecutive houses:
	    	1. house i not robbed, break the circle, done
	    	2. house i + 1 not robbed. 



	// main fucntion
	public int rob(int[] nums) {
	    if (nums.length == 1) 
	    	return nums[0];

	    // 2 case, if rob the first one, of if not rob the first one
	    return Math.max(robHelper(nums, 0, nums.length - 2), robHelper(nums, 1, nums.length - 1));
	}

	private int robHelper(int[] num, int lo, int hi) {
	    int prev_include = 0, prev_exclude = 0;
	    for (int j = lo; j <= hi; j++) {
	        int cur_include = prev_include, 
	        	cur_exclude = prev_exclude;

	        prev_include = cur_exclude + num[j];
	        prev_exclude = Math.max(cur_exclude, cur_include);
	    }
	    return Math.max(prev_include, prev_exclude);
	}



=======================================================================================================
method 2: recursive

Stats:

	- 
	- 


Method:

	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



