121. Best Time to Buy and Sell Stock

https://leetcode.com/problems/best-time-to-buy-and-sell-stock/


Say you have an array for which the ith element is the price of a given stock on day i. If you were 
only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), 
design an algorithm to find the maximum profit.

Input: [7,1,5,3,6,4]
	Output: 5  
	Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
=======================================================================================================
Method 1:

Method:
    - find a contiguous subarray giving maximum profit，== 找到后面比前面某天最多的difference
    - If the difference falls below 0, reset it to zero.
    - 我们当找到一个最低点时，会”记住这个最低的day“ by 后面 + price[i] - prices[i-1], 此时只要找到后面array
       中max的day就好了
        -- maxCur = current maximum value
        -- maxSoFar = maximum value found so far


//Method 1:
	public int maxProfit(int[] prices) {
        int maxCur = 0, 
            maxSoFar = 0;
        for(int i = 1; i < prices.length; i++) {
            maxCur += prices[i] - prices[i-1];			//+= --> cumulative!!!
            maxCur = Math.max(0, maxCur);				//set any negative num to zero
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
   	}


