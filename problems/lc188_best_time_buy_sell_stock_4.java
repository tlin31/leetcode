188. Best Time to Buy and Sell Stock IV

https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.


Input: [3,2,6,5,0,3], k = 2
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
             Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.



******************************************************
key:
    - DP --> see summary: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems
    - edge case:
        1) empty string, return empty
        2)

******************************************************


=======================================================================================================
method 1: DP

Method:

    -   DP: t(i,j) is the max profit for up to i transactions by time j (0<=i<=K, 0<=j<=T).
    -   subproblems: first find a min btw the previous min and the diff between sell today & sell on 
        last day

   public int maxProfit(int k, int[] prices) {
        if (k >= prices.length / 2) return quicksolve(prices); 	// very important for edge cases!!
        
        int[] cur_dp = new int[prices.length];
        int[] prev_dp = new int[prices.length];			// store previous transaction values				
        
        for (int i = 0; i < k; i++) {					// have i times of transaction
            int min = prices[0];
            // go through all prices
            for (int j = 1; j < prices.length; j++) {
                min = Math.min(min, prices[j] - prev_dp[j-1]);
                cur_dp[j] = Math.max(cur_dp[j-1], prices[j]-min);
            }
            // swap current dp and the previous array
            int[] tmp = cur_dp;
            cur_dp = prev_dp;
            prev_dp = tmp;
        }
        
        return prev_dp[prices.length-1];
    }
    
    private int quicksolve(int[] prices) {
        int maxProfit = 0;
        
        for (int i = 0; i < prices.length-1; i++) {
            if (prices[i+1] > prices[i])		
                maxProfit += prices[i+1] - prices[i];			// accumulate max profit
        }
        
        return maxProfit;
    }


=============

dp[i][j] = maximum profit from at most i transactions using prices[0..j]

A transaction is defined as one buy + sell.

Now on day j, we have two options

    1. Do nothing (or buy) which doesnt change the acquired profit : dp[i][j] = dp[i][j-1]

    2. Sell the stock: you must have bought it on a day t=[0..j-1]. Maximum profit that can be 
       attained is: (t from 0 to j-1) max(prices[j]-prices[t]+dp[i-1][t-1]) where prices[j]-prices[t] is 
       the profit from buying on day t and selling on day j. dp[i-1][t-1] is the maximum profit 
       that can be made with at most i-1 transactions with prices prices[0..t-1].

Time complexity of this approach is O(n^2 * k).

In order to reduce it to O(nk), we find: 
    (t from 0 to j-1) max(prices[j]-prices[t]+dp[i-1][t-1]) is same as

    prices[j] + (t from 0 to j-1) max(dp[i-1][t-1]-prices[t])

Second part of the above expression maxTemp = (t from 0 to j-1) max(dp[i-1][t-1]-prices[t]) can be 
included in the dp loop by keeping track of the maximum value till j-1.

Algorithm:

    Base case:

        dp[0][j] = 0; dp[i][0] = 0

    DP loop:

        for i : 1 -> k
            maxTemp = -prices[0];
            for j : 1 -> n-1
                dp[i][j] = max(dp[i][j-1], prices[j]+maxTemp);
                maxTemp = max(maxTemp, dp[i-1][j-1]-prices[j]);
        return dp[k][n-1];


    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if (k >= len / 2) return quickSolve(prices);
        
        int[][] t = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {

            // tmpMax = the maximum profit of just doing at most i-1 transactions, using at 
            // most first j-1 prices, and buying the stock at price[j] - this is used for the next loop.
            int tmpMax =  -prices[0];
            for (int j = 1; j < len; j++) {
                t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);

                tmpMax =  Math.max(tmpMax, t[i - 1][j - 1] - prices[j]);
            }
        }
        return t[k][len - 1];
    }
    

    private int quickSolve(int[] prices) {
        int len = prices.length, profit = 0;
        for (int i = 1; i < len; i++)
            // as long as there is a price gap, we gain a profit.
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        return profit;
    }

=======================================================================================================
method 2:

https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/108870/Most-consistent-ways-of-dealing-with-the-series-of-stock-problems

Method:
    -   T[i][k][0] --> maximum profit at the end of the i-th day with at most k transactions and 
                        with 0 stock in our hand AFTER taking the action, 

        T[i][k][1] --> the maximum profit at the end of the i-th day with at most k transactions 
                        and with 1 stock in our hand AFTER taking the action.
    
    -   This is the most general case so on each day we need to update all the maximum profits 
        with different k values corresponding to 0 or 1 stocks in hand at the end of the day. 
        However, there is a minor optimization we can do if k exceeds some critical value, beyond 
        which the maximum profit will no long depend on the number of allowable transactions but 
        instead will be bound by the number of available stocks (length of the prices array). 

    -   A profitable transaction takes at least two days (buy at one day and sell at the other, 
        provided the buying price is less than the selling price). 
        If the length of the prices array is n, the maximum number of profitable transactions is 
        n/2 (integer division). 
        After that no profitable transaction is possible, which implies the maximum profit will 
        stay the same. Therefore the critical value of k is n/2. 
        If the given k is no less than this value, i.e., k >= n/2, we can extend k to positive 
        infinity and the problem is equivalent to Case II.

    -   The following is the O(kn) time and O(k) space solution. 



public int maxProfit(int k, int[] prices) {

    // if k is more than n/2, it's the same case as unlimited transaction (case 3)
    if (k >= prices.length /2) {
        int T_ik0 = 0, T_ik1 = Integer.MIN_VALUE;
    
        for (int price : prices) {
            int T_ik0_old = T_ik0;
            T_ik0 = Math.max(T_ik0, T_ik1 + price);
            T_ik1 = Math.max(T_ik1, T_ik0_old - price);
        }
        
        return T_ik0;
    }
        
    int[] T_ik0 = new int[k + 1];
    int[] T_ik1 = new int[k + 1];
    Arrays.fill(T_ik1, Integer.MIN_VALUE);
        
    for (int price : prices) {
        for (int j = k; j > 0; j--) {
            T_ik0[j] = Math.max(T_ik0[j], T_ik1[j] + price);
            T_ik1[j] = Math.max(T_ik1[j], T_ik0[j - 1] - price);
        }
    }
        
    return T_ik0[k];
}


