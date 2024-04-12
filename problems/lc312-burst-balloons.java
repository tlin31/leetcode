312. Burst Balloons - Hard

Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by 
array nums. You are asked to burst all the balloons. If  you burst balloon i you will get 
nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. 

After the burst, the left and right then becomes adjacent.

Find the maximum coins you can collect by bursting the balloons wisely.

Note:

You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
Example:

Input: [3,1,5,8]
Output: 167 
Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
             coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

******************************************************
key:
	- matrix chain multiplication --> DP
	- but here want max coins, so take the max
	- edge case:
		1) empty string, return 0
		2) input.len = 1, return nums[0]

******************************************************



=======================================================================================================
method 1:

method:

	- 	c[i][j] = maxCoins (nums[i] ~ nums[j])
		ans = c[1][n]
	- 	c[i][j] = max{c[i][k-1] + nums[i-1] * nums[k] * nums[j+1] +c[k+1][j], for all i<=k<=j}
		loop through all possible partitions between i & j (need to find mid k)
	- 	先把i~k-1 这些气球打破（subproblem），再把 k+1~j 这些气球打破（subproblem), 所以我保留k，打破左边和右边的
		气球，最后打破k，此时的 [i-1, k, j+1] 为最后剩的3个气球，把他们打破，得到的
		coins=nums[i-1] * nums[k] * nums[j+1]

stats:
	- O(n^2)
	- 

class Solution {
  public int maxCoins(int[] nums) {
    final int n = nums.length;
    //padding
    int[] vals = new int[n + 2];

    for (int i = 0; i < n; ++i) 
    	vals[i + 1] = nums[i];


    vals[0] = vals[n + 1] = 1;
    int[][] dp = new int[n + 2][n + 2];

    // length of subproblem
    for (int length = 1; length <= n; ++length)

      //here for every possible start of i, assume we want to find subproblem from i to j with
      // l = length (from len = 1 to len = n)
      for (int i = 1; i + length -1 <= n ; ++i) {

      	//ending ballon = i (start index) + l(length of subproblem)
        int endBallon = i + length - 1;
        int best = 0;
        for (int k = i; k <= endBallon; ++k){
          	best = Math.max(best, dp[i][k - 1] + vals[i - 1] * vals[k] * vals[j + 1] + dp[k + 1][endBallon]);

          	// store matrix
          	// s[i,j] = k --> remember for sequence btw i and endBallon, the best partition is at k

        }
        
        dp[i][j] = best;
      }
    return dp[1][n];        
  }
}

Print-Opt(s, i, j)

	If i = j 
		print "Ai"
	else 
		// 左边的是： 当i = k时，print Ai
		// 右边要找后面的 subproblem， 找k+i = j的时候
		print { "(" + Print-Opt(s, i, s[i,j]) + "x" Print-Opt(s, s[i,j]+1, j) + ")" }


Main: Print-Opt(s, 1, n)




=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 




