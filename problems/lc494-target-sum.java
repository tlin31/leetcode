494. Target Sum - Medium

You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 
symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.

Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5

Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.

Note:
The length of the given array is positive and will not exceed 20.
The sum of elements in the given array will not exceed 1000.
Your output answer is guaranteed to be fitted in a 32-bit integer.



******************************************************
key:
	- backtrack / DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: backtrack with memo


Stats:

	- 
	- 


Method:

	-	!! in memo use [sum+1000] because the sum of elements in the given array will not exceed 1000, 
	       and so the range is from -1000 ~ +1000
	-	

public class Solution {

    public int findTargetSumWays(int[] nums, int S) {
        int[][] memo = new int[nums.length][2001];
        for (int[] row: memo)
            Arrays.fill(row, Integer.MIN_VALUE);
        return calculate(nums, 0, 0, S, memo);
    }

    public int calculate(int[] nums, int i, int sum, int S, int[][] memo) {
        if (i == nums.length) {
            if (sum == S)
            	// find 1 solution! yay
                return 1;
            else
                return 0;
        } else {

        	// already calculated
            if (memo[i][sum + 1000] != Integer.MIN_VALUE) {
                return memo[i][sum + 1000];
            }

            int add = calculate(nums, i + 1, sum + nums[i], S, memo);
            int subtract = calculate(nums, i + 1, sum - nums[i], S, memo);

            memo[i][sum + 1000] = add + subtract;
            return memo[i][sum + 1000];
        }
    }
}

Improved: DP

dp[i][j] refers to the number of assignments which can lead to a sum of j upto the i-th index


public class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        
        for (int i = 1; i < nums.length; i++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i - 1][sum + 1000] > 0) {
                    dp[i][sum + nums[i] + 1000] += dp[i - 1][sum + 1000];
                    dp[i][sum - nums[i] + 1000] += dp[i - 1][sum + 1000];
                }
            }
        }
        return S > 1000 ? 0 : dp[nums.length - 1][S + 1000];
    }
}

=======================================================================================================
method 2: 2 hashmaps

Stats:

	- 
	- 


Method:

	-	It uses a dictionary to store all possible sums using all the numbers with +/- signs and 
	    return the number of ways of the target sum in the dictionary
	-	hashmap (key = sum, value = number of ways)

    public int findTargetSumWays(int[] nums, int s) {
        Map<Integer, Integer> dp = new HashMap();
        dp.put(0, 1);
        for (int num : nums) {
            Map<Integer, Integer> dp2 = new HashMap();
            for (int tempSum : dp.keySet()) {
                int key1 = tempSum + num;
                dp2.put(key1, dp2.getOrDefault(key1, 0) + dp.get(tempSum));

                int key2 = tempSum - num;
                dp2.put(key2, dp2.getOrDefault(key2, 0) + dp.get(tempSum));
            }
            dp = dp2;
        }
        return dp.getOrDefault(s, 0);
    }


-----


=======================================================================================================
method 3: DP - knapsack problem (whether we choose this item or not)

Stats:

	- 
	- 


Method:

	-	
	-  dp[i][j] represents number of possible ways to reach sum j by using first ith items
 		base case: dp[0][sum], position sum represents sum 0
 
 		recurrence :
 			if j + nums[i - 1] <= sum * 2: 
				dp[i][j] += dp[i - 1][j + nums[i - 1]] 

			if j - nums[i - 1] >= 0:
				dp[i][j] += dp[i - 1][j - nums[i - 1]] 

	  Because if j + nums[i - 1] or j - nums[i - 1] is in correct range, we can use nums[i - 1] 
	  to generate next state of dp array


public int findTargetSumWays2(int[] nums, int S) {
    if (nums.length == 0) {
        return 0;
    }

    int sum = 0;
    for (int num : nums) {
        sum += num;
    }

    // corner case: when S is out of range [-sum, sum]
    if (S < -sum || S > sum) {
        return 0;
    }

    int[][] dp = new int[nums.length + 1][sum * 2 + 1];
    dp[0][sum] = 1;
    int leftBound = 0;
    int rightBound = sum * 2;
    for (int i = 1; i <= nums.length; i++) {
        for (int j = leftBound; j < rightBound + 1; j++) {
            // try all possible sum of (previous sum j + current number nums[i - 1]) and all 
            // possible difference of (previous sum j - current number nums[i - 1])
            if (j + nums[i - 1] <= rightBound) {
                dp[i][j] += dp[i - 1][j + nums[i - 1]];
            }
            if (j - nums[i - 1] >= leftBound) {
                dp[i][j] += dp[i - 1][j - nums[i - 1]];
            }
        }
    }
    return dp[nums.length][sum + S];
}


/**
 * if we calculate total sum of all candidate numbers, then the range of possible calculation result will be in
 * the range [-sum, sum]. So we can define an dp array with size sum * 2 + 1 to calculate number of possible ways
 * to reach every target value between -sum to sum, and save results to dp array. dp[sum + S] will be out final
 * result. (because dp[sum] or less represents number of possible ways to reach a number in range [-sum, 0])
 *
 * sub-problem: dp[i] represents number of possible ways to reach target i
 * base case: dp[sum] = 1  //if we add all numbers
 * recurrence relation: when doing inner loop iterations, we should create another temp dp array to store temp
 * target array. Because if we use dp array to store temp results directly, we may have array boundary exception
 * eg: for input [1,1,1,1,1], we will never reach dp[6] or d[-6]. However, if we use dp[j + nums[i]] to store
 * temp results, we may proceed dp[5 + 1] += dp[5], which is considered incorrect case
 * */
public int findTargetSumWays3(int[] nums, int S) {
    if (nums.length == 0) {
        return 0;
    }

    int sum = 0;
    for (int num : nums) {
       sum += num;
    }

    // corner case: when S is out of range [-sum, sum]
    if (S < -sum || S > sum) {
        return 0;
    }

    int[] dp = new int[sum * 2 + 1];
    dp[sum] = 1;
    for (int i = 0; i < nums.length; i++) {
        int[] tempTarget = new int[sum * 2 + 1];
        for (int j = 0; j < sum * 2 + 1; j++) {
            // WARNING: DO NOT FORGET to check condition whether dp[i] is 0 or not
            // if it is NOT 0, it means we at least have one possible way to reach target j. Otherwise, we may have
            // array out of bound exception
            if (dp[j] != 0) {
                tempTarget[j + nums[i]] += dp[j];
                tempTarget[j - nums[i]] += dp[j];
            }
        }
        dp = tempTarget;
    }
    return dp[sum + S];
}

