416. Partition Equal Subset Sum - Medium


Given a non-empty array containing only positive integers, find if the array can be partitioned into 
two subsets such that the sum of elements in both subsets is equal.

Note:

Each of the array element will not exceed 100.
The array size will not exceed 200.
 

Example 1:

Input: [1, 5, 11, 5]

Output: true

Explanation: The array can be partitioned as [1, 5, 5] and [11].
 

Example 2:

Input: [1, 2, 3, 5]

Output: false

Explanation: The array cannot be partitioned into equal sum subsets.


******************************************************
key:
	- DP, knapsack problem
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(n^2)
	- O(n)


Method:

	-  = find whether there are several numbers in a set that sum to sum/2

	-  0/1 knapsack problem: 
		for each number, we can pick it or not. 

	-  dp[i][j] = whether the specific sum j can be gotten from the first i numbers. 

		True if we can pick such a series of numbers from 0-i whose sum is j


	- Base case: dp[0][0] is true; (zero number consists of sum 0 is true)

	- Transition function: for current element i 
		1) if we do not pick it:  
			dp[i][j] = dp[i-1][j]

			which means if the first i-1 elements has made it to j, dp[i][j] would also make it to j, we
			can just ignore nums[i]

		2) if we pick nums[i]: 
			dp[i][j] = dp[i-1][j-nums[i]]

			which represents that j is composed of the current value nums[i] and the remaining composed
			of other previous numbers. 

		Thus, the transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]


public boolean canPartition(int[] nums) {
    int sum = 0;
    
    for (int num : nums) {
        sum += num;
    }
    
    // check whether sum is odd, if it's odd, then it's always false
    if ((sum & 1) == 1) {
        return false;
    }

    sum /= 2;

    int n = nums.length;

    // initialize
    boolean[][] dp = new boolean[n+1][sum+1];
    for (int i = 0; i < dp.length; i++) {
        Arrays.fill(dp[i], false);
    }
    
    dp[0][0] = true;
    
    // always can find a subset from 0 ~ i which sums up to 0, the solution is to not choose any num.
    for (int i = 1; i < n+1; i++) {
        dp[i][0] = true;
    }

    // can't use 0 elements to make up a sum = j
    for (int j = 1; j < sum+1; j++) {
        dp[0][j] = false;
    }
    
    for (int i = 1; i < n+1; i++) {
        for (int j = 1; j < sum+1; j++) {
            dp[i][j] = dp[i-1][j];

            // the sum is greater than previous item, then we can decide whether to take nums[i-1] or not
            if (j >= nums[i-1]) {
                dp[i][j] = (dp[i][j] || dp[i-1][j-nums[i-1]]);
            }
        }
    }
   
    return dp[n][sum];
}



Optimize: 1d

public boolean canPartition(int[] nums) {
    int sum = 0;
    
    for (int num : nums) {
        sum += num;
    }
    
    if ((sum & 1) == 1) {
        return false;
    }
    sum /= 2;
    
    int n = nums.length;
    boolean[] dp = new boolean[sum+1];
    Arrays.fill(dp, false);
    dp[0] = true;
    
    for (int num : nums) {
        for (int i = sum; i > 0; i--) {
            if (i >= num) {
                dp[i] = dp[i] || dp[i-num];
            }
        }
    }
    
    return dp[sum];
}

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



