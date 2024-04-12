322. Coin Change - min num of coins - Medium

You are given coins of different denominations and a total amount of money amount. Write a function to 
compute the fewest number of coins that you need to make up that amount. If that amount of money cannot 
be made up by any combination of the coins, return -1.

Example 1:
Input: coins = [1, 2, 5], amount = 11
Output: 3 
Explanation: 11 = 5 + 5 + 1


Example 2:
Input: coins = [2], amount = 3
Output: -1
Note:
You may assume that you have an infinite number of each kind of coin.

******************************************************
key:
	- dp size = amount, loop through each possible coin, each coin from coin ~ amount fill up dp
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- dp[x] = num of ways to make up to x

stats:

	- Time complexity : O(S*n). On each step the algorithm finds the next F(i) in nn iterations, 
						where 1 ≤ i ≤ S. Therefore in total the iterations are S*n
	- Space complexity : O(S). We use extra space for the memoization table.



public int coinChange(int[] coins, int amount) {
    if (amount < 1) return 0;
    int[] dp = new int[amount + 1]; 
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int coin : coins) {
        for (int i = coin; i <= amount; i++) {
        	
        	// if dp[i - coin]  is already modified
            if (dp[i - coin] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
}

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



