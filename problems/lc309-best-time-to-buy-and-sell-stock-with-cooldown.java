309. Best Time to Buy and Sell Stock with Cooldown - Medium

Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you 
like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

	1. You may not engage in multiple transactions at the same time (ie, you must sell the stock 
	   before you buy again).
	2. After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

Example:
Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/discuss/75928/Share-my-DP-solution-(By-State-Machine-Thinking)

Method:

	-	state diagram
	-	There are three states, according to the action that you can take.

		s0[i] = max(s0[i - 1], s2[i - 1]); // Stay at s0, or rest from s2
		s1[i] = max(s1[i - 1], s0[i - 1] - prices[i]); // Stay at s1, or buy from s0
		s2[i] = s1[i - 1] + prices[i]; // Only one way from s1

	-	Then, you just find the maximum of s0[n] and s2[n], since they will be the maximum 
	  	profit we need (No one can buy stock and left with more profit that sell right )

		Define base case:

		s0[0] = 0; // At the start, you don't have any stock if you just rest
		s1[0] = -prices[0]; // After buy, you should have -prices[0] profit. Be positive!
		s2[0] = INT_MIN; // Lower base case

Stats:

	- 
	- 


int maxProfit(int[] prices) {
    int sold = 0, hold = -1*prices[0], rest = 0;
    for (int i=0; i<prices.size(); ++i) {
        int prvSold = sold;
        sold = hold + prices[i];
        hold = max(hold, rest-prices[i]);
        rest = max(rest, prvSold);
        System.out.println("at i = " + i + ", sold = " + sold + ", hold = " +hold + ", rest = " + rest);

    }
    return max(sold, rest);
}


input: [1,2,3,0,2]


at i = 0, sold = 0, hold = -1, rest = 0
at i = 1, sold = 1, hold = -1, rest = 0
at i = 2, sold = 2, hold = -1, rest = 1
at i = 3, sold = -1, hold = 1, rest = 2
at i = 4, sold = 3, hold = 1, rest = 2

Explanation: transactions = [buy, sell, cooldown, buy, sell]
output = 3
=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

1. Define States

To represent the decision at index i:

	buy[i]: Max profit till index i. The series of transaction is ending with a buy.
	sell[i]: Max profit till index i. The series of transaction is ending with a sell.

To clarify:

	1. Till index i, the buy / sell action must happen and must be the last action. It may not 
	   happen at index i. It may happen at i - 1, i - 2, ... 0.
	2. In the end n - 1, return sell[n - 1]. Apparently we cannot finally end up with a buy. 
	   In that case, we would rather take a rest at n - 1.
	3. For special case no transaction at all, classify it as sell[i], so that in the end, we can 
	   still return sell[n - 1].


2. Define Recursion

	1. buy[i]: To make a decision whether to buy at i, we either take a rest, by just using the 
	           old decision at i - 1, or sell at/before i - 2, then buy at i, We cannot sell 
	           at i - 1, then buy at i, because of cooldown.

	           buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);   

	2. sell[i]: To make a decision whether to sell at i, we either take a rest, by just using 
	            the old decision at i - 1, or buy at/before i - 1, then sell at i.

				sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);

3. Optimize to O(1) Space
DP solution only depending on i - 1 and i - 2 can be optimized using O(1) space.
	Let b2, b1, b0 represent buy[i - 2], buy[i - 1], buy[i]
	Let s2, s1, s0 represent sell[i - 2], sell[i - 1], sell[i]

Then arrays turn into Fibonacci like recursion:
	b0 = Math.max(b1, s2 - prices[i]);
	s0 = Math.max(s1, b1 + prices[i]);



public int maxProfit(int[] prices) {
    if(prices == null || prices.length <= 1) return 0;
  
  	// We can buy. The max profit at i = 0 ending with a buy is -prices[0].
	// We cannot sell. The max profit at i = 0 ending with a sell is 0.
    int b0 = -prices[0], b1 = b0;
    int s0 = 0, s1 = 0, s2 = 0;
 
    for(int i = 1; i < prices.length; i++) {
    	b0 = Math.max(b1, s2 - prices[i]);
    	s0 = Math.max(s1, b1 + prices[i]);
    	b1 = b0; 
    	s2 = s1; 
    	s1 = s0; 
    }
    return s0;
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 


Define:

profit1[i] = max profit on day i if I sell

profit2[i] = max profit on day i if I do nothing
How will those profits on day i+1 relate to profits on day i ?

1. profit1[i+1] means I must sell on day i+1, and there are 2 cases:

a. If I just sold on day i, then I have to buy again on day i and sell on day i+1

b. If I did nothing on day i, then I have to buy today and sell today 

Taking both cases into account, profit1[i+1] = max(profit1[i]+prices[i+1]-prices[i], profit2[i])

2. profit2[i+1] means I do nothing on day i+1, so it will be max(profit1[i], profit2[i])
And the code:

public int maxProfit(int[] prices) {
    int profit1=0, profit2=0;   
    for(int i=1; i<prices.length; i++){
        int copy=profit1;
        profit1=Math.max(profit1+prices[i]-prices[i-1], profit2);
        profit2=Math.max(copy, profit2);
    }
    return Math.max(profit1, profit2);
}
