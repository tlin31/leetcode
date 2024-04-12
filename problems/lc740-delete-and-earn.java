740. Delete and Earn - Medium

Given an array nums of integers, you can perform operations on the array.

In each operation, you pick any nums[i] and delete it to earn nums[i] points. After, you must delete 
every element equal to nums[i] - 1 or nums[i] + 1.

You start with 0 points. Return the maximum number of points you can earn by applying such operations.

Example 1:

Input: nums = [3, 4, 2]
Output: 6
Explanation: 
Delete 4 to earn 4 points, consequently 3 is also deleted.
Then, delete 2 to earn 2 points. 6 total points are earned.
 

Example 2:

Input: nums = [2, 2, 3, 3, 3, 4]
Output: 9
Explanation: 
Delete 3 to earn 3 points, deleting both 2 s and the 4.
Then, delete 3 again to earn 3 points, and 3 again to earn 3 points.
9 total points are earned.
 

Note:

The length of nums is at most 20000.
Each element nums[i] is an integer in the range [1, 10000].

******************************************************
key:
	- Greedy O(n)
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************


=======================================================================================================
method 1:

method:

	- Greedy solution --> use 1 array
	- dp[i] = max(take i-th number + not take (i-1)th  OR not take i-th number + take (i-1) th)
	- 
	- 	1. every single element in our original nums array, you have 2 choices: To earn or not to 
		earn. Based on problem, whichever element you earn, you must delete any values of nums[i]-1 
		and nums[i]+1. 

		2. if you earn an element, you cannot earn its immediate unequal neighbors on both sides.

		3. if you have duplicate values in nums array, and you earn one of them --> end up earning 
		   all of them. This is because you have deleted its neighbors and therefore make its remaining 
		   duplicates "undeletable". 

		   This is important because you notice the problem simplifies to which values can earn you 
		   the largest total.

	-   So I aggregated the sums into a sums array to map each value (arrays index) with the total 
		sum you can earn by deleting all elements of that value (arrays value). 

		sum[i] = Max(sum[i-1], sum[i-2] + sum[i])

		Then write a for loop to compute the maximum sum ending at i At each step, your sum can either 
		depend on your previous sum or the prior plus the current. 

		You use a greedy algorithm to always pick the maximum value for each i.

stats:

	- Time: O(M+N)
	- Space: O(N)
		M: the length of input array
		N: the range of the value of each int element

        public int deleteAndEarn(int[] nums) {
            int[] buckets = new int[10001];
            for (int num : nums) {
                buckets[num] += num;
            }
            int[] dp = new int[10001];
            dp[0] = buckets[0];
            dp[1] = buckets[1];
            for (int i = 2; i < buckets.length; i++) {
                dp[i] = Math.max(buckets[i] + dp[i - 2], dp[i - 1]);
            }
            return dp[10000];
        }



House Robbery:

    public int rob(int[] nums) {
        int len  = nums.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return nums[0];
        }
        int[] dp = new int[len];
        dp[0] = nums[0];
        for (int i = 1; i < len; i++) {
            if (i == 1) {
                dp[i] = Math.max(nums[i],dp[i - 1]);
                continue;
            }
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }
        return dp[len-1]; 
    }

=======================================================================================================
method 2:

method:
	- optimized DP, using onlg 2 variables instead of DP array

	-   bucket sort first, then problem becomes asking you to repetitively take an bucket while giving
	 	up the 2 buckets next to it. (the range of these numbers is [1, 10000])

	-	2 variables skip_i, take_i, which stands for:
			skip_i = the best result for sub-problem of first (i+1) buckets from 0 to i, 
					 while you skip the ith bucket.

			take_i : the best result for sub-problem of first (i+1) buckets from 0 to i, 
					while you take the ith bucket.

	- 	DP formula:
		take[i] = skip[i-1] + values[i];
		skip[i] = Math.max(skip[i-1], take[i-1]);

		take[i] --> if you skipped the [i-1]th bucket, and you take bucket[i].
		skip[i] --> either take[i-1] or skip[i-1], whatever the bigger;

stats:

	- Time: O(M+N)
	- Space: O(N)

M: the length of input array
N: the range of the value of each int element


/**
 * for numbers from [1 - 10000], each has a total sum sums[i]; if you earn sums[i], you cannot earn sums[i-1] and sums[i+1]
 * kind of like house robbing. you cannot rob 2 connected houses.
 * 
 */

class Solution {
    public int deleteAndEarn(int[] nums) {
        int n = 10001;
        int[] values = new int[n];
        for (int num : nums)
            values[num] += num;

        // skip stores skip[i-1]
        // take stores take[i-1]
        int take = 0, 
            skip = 0;
            
        for (int i = 0; i < n; i++) {
            int takei = skip + values[i];
            int skipi = Math.max(skip, take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take, skip);
    }
}





=======================================================================================================
method 3:

method:


stats:

	- 
	- 

