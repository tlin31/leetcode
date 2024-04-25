122. Best Time to Buy and Sell Stock II


Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like 
(i.e., buy one and sell one share of the stock multiple times).

Input: [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
             Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.

Input: [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
             Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
             engaging multiple transactions at the same time. You must sell before buying again.

=======================================================================================================     

method 1:

	- one pass
	- This solution follows the logic used in method 2 itself, but with only a slight variation. 
		In this case, instead of looking for every peak following a valley, we can simply go on 
		crawling over the slope and keep on adding the profit obtained from every consecutive 
		transaction. 

		In the end,we will be using the peaks and valleys effectively, but we need not track the 
		costs corresponding to the peaks and valleys along with the maximum profit, but we can 
		directly keep on adding the difference between the consecutive numbers of the array if 
		the second number is larger than the first one, and at the total sum we obtain will be 
		the maximum profit. 



public class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;
        
        for (int i = 1; i < prices.length; i++) 
            profit += Math.max(0, prices[i] - prices[i - 1]);
        
        return profit;
    }
}


------
    - If k is positive infinity, then there isnt really any difference between k and k - 1 which 
      implies T[i-1][k-1][0] = T[i-1][k][0] and T[i-1][k-1][1] = T[i-1][k][1]. 
      Therefore, we still have two unknown variables on each day: 
        T[i][k][0] and T[i][k][1] with k = +Infinity, and the recurrence relations say:

        T[i][k][0] = max(T[i-1][k][0], T[i-1][k][1] + prices[i])
        T[i][k][1] = max(T[i-1][k][1], T[i-1][k-1][0] - prices[i]) 
                   = max(T[i-1][k][1], T[i-1][k][0] - prices[i])

    where we have taken advantage of the fact that T[i-1][k-1][0] = T[i-1][k][0] for the second 
    equation. The O(n) time and O(1) space solution is as follows:

    -  The caching of the old values of T_ik0, that is, the variable T_ik0_old, is unnecessary. 

    - This solution suggests a greedy strategy of gaining maximum profit: as long as possible, 
      buy stock at each local minimum and sell at the immediately followed local maximum. 
      This is equivalent to finding increasing subarrays in prices (the stock price array), and 
      buying at the beginning price of each subarray while selling at its end price. 


public int maxProfit(int[] prices) {
    int T_ik0 = 0, T_ik1 = Integer.MIN_VALUE;
    
    for (int price : prices) {
        int T_ik0_old = T_ik0;
        T_ik0 = Math.max(T_ik0, T_ik1 + price);
        T_ik1 = Math.max(T_ik1, T_ik0_old - price);
    }
    
    return T_ik0;
}


====================================================================================================
method 2:
	- peak & valley
	- Peak Valley Approach
		The key point is we need to consider every peak immediately following a valley to maximize 
		the profit. In case we skip one of the peaks (trying to obtain more profit), we will end up 
		losing the profit over one of the transactions leading to an overall lesser profit.

		For example, in the above case, if we skip peak_i and valley_j trying to obtain more profit by 
		considering points with more difference in heights, the net profit obtained will always be 
		lesser than the one obtained by including them, since C will always be lesser than A+B.

class Solution {
    public int maxProfit(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxprofit = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1])
                i++;
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1])
                i++;
            peak = prices[i];
            maxprofit += peak - valley;
        }
        return maxprofit;
    }
}

















