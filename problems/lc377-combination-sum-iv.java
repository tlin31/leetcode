377. Combination Sum IV - Medium

Given an integer array with all positive numbers and no duplicates, find the number of possible
combinations that add up to a positive integer target.

Example:

nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.
 

Follow up:
What if negative numbers are allowed in the given array?
How does it change the problem?
What limitation we need to add to the question to allow negative numbers?

Credits:
Special thanks to @pbrother for adding this problem and creating all test cases.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: recursion + hashmap


Stats:

	- 
	- 


Method:

	-	The DP solution goes through every possible sum from 1 to target one by one.
	-	Using recursion can skip those sums that are not the combinations of the numbers in the 
	    given array. 



public class Solution {
    Map<Integer, Integer> map = new HashMap<>();

    public int combinationSum4(int[] nums, int target) {
        int count = 0;
        if (nums == null || nums.length ==0 || target < 0 ) 
        	return 0;

        // find a combination sum!
        if ( target == 0 ) return 1;

        if (map.containsKey(target)) 
        	return map.get(target);

        for (int num: nums){
            count += combinationSum4(nums, target-num);
        }

        map.put(target, count);
        return count;
    }
}


Follow up:
	- to allow negative integers, the length of the combination sum needs to be restricted, or the
	  search will NOT stop. 
	- or break once it go pass the target

Class Solution{
	Map<Integer, Map<Integer,Integer>> map2 = new HashMap<>();

	public int combinationSum4(int[] nums, int target, int MaxLen) {
        if (nums == null || nums.length ==0 || MaxLen <= 0 ) return 0;
        map2 = new HashMap<>();
        return helper2(nums, 0, target, MaxLen);
    }

    private int helper2(int[] nums, int len, int target, int MaxLen) {
    	int count = 0;
        if (  len > MaxLen  ) 
        	return 0;

        if ( map2.containsKey(target) && map2.get(target).containsKey(len)) { 
        	return map2.get(target).get(len);
        }

        if ( target == 0 )   
        	count++;

        for (int num: nums) {
            count+= helper2(nums, len+1, target-num, MaxLen);
        }

        if ( ! map2.containsKey(target) ) 
        	map2.put(target, new HashMap<Integer,Integer>());

        Map<Integer,Integer> mem = map2.get(target);
        mem.put(len, count);
        return count;
    }
       
}

=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:

	- How does the # of combinations of the target related to the # of combinations of numbers that 
	  are smaller than the target?

	- Imagine we only need one more number to reach target, this number can be any one in the array,
	  right? So the # of combinations of target, comb[target] = sum(comb[target - nums[i]]), where 
	  0 <= i < nums.length, and target >= nums[i].

	- In the example given, we can actually find the # of combinations of 4 with the # of combinations 
	  of 3 = (4 - 1), 2 = (4- 2) and 1 = (4 - 3). 
	  As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

	- base case: 
		if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.


	- Now for a DP solution, we just need to figure out a way to store the intermediate results, to 
	  avoid the same combination sum being calculated many times. We can use an array to save those
	  results, and check if there is already a result before calculation. 

	  We can fill the array with -1 to indicate that the result hasn't been calculated yet. 
	  0 is not a good choice because it means there is no combination sum for the target.

Class Solution{

	private int[] dp;

	public int combinationSum4(int[] nums, int target) {
	    dp = new int[target + 1];
	    Arrays.fill(dp, -1);
	    dp[0] = 1;
	    return helper(nums, target);
	}

	private int helper(int[] nums, int target) {
	    if (dp[target] != -1) {
	        return dp[target];
	    }

	    int res = 0;

	    for (int i = 0; i < nums.length; i++) {
	        if (target >= nums[i]) {
	            res += helper(nums, target - nums[i]);
	        }
	    }

	    dp[target] = res;
	    return res;
	}
}


[1, 1, -1, -1, -1]
[1, 1, 2, -1, -1]
[1, 1, 2, 4, -1]
[1, 1, 2, 4, 7]



EDIT: The above solution is top-down. How about a bottom-up one?

public int combinationSum4(int[] nums, int target) {
    int[] comb = new int[target + 1];
    comb[0] = 1;
    for (int i = 1; i < comb.length; i++) {
        for (int j = 0; j < nums.length; j++) {
            if (i - nums[j] >= 0) {
                comb[i] += comb[i - nums[j]];
            }
        }
    }
    return comb[target];
}
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



