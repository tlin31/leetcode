714. Best Time to Buy and Sell Stock with Transaction Fee - Medium

Your are given an array of integers prices, for which the i-th element is the price of a given 
stock on day i; and a non-negative integer fee representing a transaction fee.

You may complete as many transactions as you like, but you need to pay the transaction fee for 
each transaction. 

You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share 
before you buy again.)

Return the maximum profit you can make.

Example 1:
Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
Buying at prices[0] = 1
Selling at prices[3] = 8
Buying at prices[4] = 4
Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.

Note:
0 < prices.length <= 50000.
0 < prices[i] < 50000.
0 <= fee < 50000.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	At the end of the i-th day, we maintain cash, the maximum profit we could have if we did 
	    not have a share of stock, and hold, the maximum profit we could have if we owned a 
	    share of stock.
	-	To transition from the i-th day to the i+1-th day, we either:
			sell our stock cash = max(cash, hold + prices[i] - fee) or 
			buy a stock hold = max(hold, cash - prices[i]). 

	-	At the end, we want to return cash. We can transform cash first without using temporary
		variables because selling and buying on the same day cant be better than just continuing 
		to hold the stock.


Stats:

	- Time Complexity: O(N), where N is the number of prices.
	- Space Complexity: O(1), the space used by cash and hold.


class Solution {
    public int maxProfit(int[] prices, int fee) {
        int cash = 0, hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }
}





=======================================================================================================
method 2:

Method:

	-	state machine
	-	https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/discuss/160964/java-Using-State-Machine-like-stock-III
	- 	We should maintain each state with maximum profit.
		So at S0, we could either do nothing, or we could buy a stock.
		At s1, we  either do nothing, or we  sell current stock with fee.

	-	To update state:
			s0: the incoming arrows from s0 itself or s1: s0 = Math.max(s0, s1 + sale_price - fee)
			s1: the incoming arrows from s0 and s1 itself: s1 = Math.max(s1, s0 - buying_prices)


Stats:

	- 
	- 


class Solution {
    public int maxProfit(int[] prices, int fee) {
      if (prices.length == 0) {
        return 0;
      }
      int s0 = 0;
      int s1 = -prices[0];
      for (int i = 1; i < prices.length; i++) {
        s1 = Math.max(s1, s0 - prices[i]);
        s0 = Math.max(s0, prices[i] + s1 - fee);
      }
      
      return s0;
    }
}
=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



