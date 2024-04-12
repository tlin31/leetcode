Design an algorithm to find the maximum profit. You may complete at most two transactions.

Input: [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
******************************************************
key:
    - dp
    - edge case:
        1) empty string, return empty
        2)

******************************************************

T[i][k][0] and T[i][k][1]: k = # of used transaction
where the former denotes the maximum profit at the end of the i-th day with at most k transactions 
and with 0 stock in our hand AFTER taking the action, while the latter denotes the maximum profit 
at the end of the i-th day with at most k transactions and with 1 stock in our hand AFTER taking 
the action. 

Base cases:
T[-1][k][0] = 0, T[-1][k][1] = -Infinity
T[i][0][0] = 0, T[i][0][1] = -Infinity

Recurrence relations:
T[i][k][0] = max(T[i-1][k][0], T[i-1][k][1] + prices[i])
T[i][k][1] = max(T[i-1][k][1], T[i-1][k-1][0] - prices[i])


Similar to the case where k = 1, except now we have four variables instead of two on each day: 
T[i][1][0], T[i][1][1], T[i][2][0], T[i][2][1], and the recurrence relations are:

T[i][2][0] = max(T[i-1][2][0], T[i-1][2][1] + prices[i])
T[i][2][1] = max(T[i-1][2][1], T[i-1][1][0] - prices[i])
T[i][1][0] = max(T[i-1][1][0], T[i-1][1][1] + prices[i])
T[i][1][1] = max(T[i-1][1][1], -prices[i])

where again we have taken advantage of the base caseT[i][0][0] = 0 for the last equation. The O(n) 
time and O(1) space solution is as follows:

public int maxProfit(int[] prices) {
    int T_i10 = 0, T_i11 = Integer.MIN_VALUE;
    int T_i20 = 0, T_i21 = Integer.MIN_VALUE;
        
    for (int price : prices) {
        T_i20 = Math.max(T_i20, T_i21 + price);
        T_i21 = Math.max(T_i21, T_i10 - price);
        T_i10 = Math.max(T_i10, T_i11 + price);
        T_i11 = Math.max(T_i11, -price);
    }
        
    return T_i20;
}



=======================================================================================================
method 1:

method:

    - bottom-up method
    - Assume we only have 0 money at first, 4 Variables to maintain some interested 'ceilings' so far:
        The maximum of:
            if we just buy 1st stock, 
            if we just sold 1nd stock, 
            if we just buy 2nd stock, 
            if we just sold 2nd stock.

stats:
    - O(n), O(1)
    - 



public class Solution {
    public int maxProfit(int[] prices) {
        int profit1 = 0, 
            profit2 = 0, 
            buy1 = Integer.MAX_VALUE, 
            buy2 = buy1; 
        
        for (int p : prices) {
            profit2 = Math.max(profit2, p - buy2);      // p-buy2 = difference
            buy2 = Math.min(buy2, p - profit1);         // want the min of today's price or difference btw today & last sell point
            profit1 = Math.max(profit1, p - buy1);      // prev profit or new difference btw 
            buy1 = Math.min(buy1, p);                   // lowest buying price
        }
        
        return profit2; 
    }
}




=======================================================================================================
method 2:

method:

    - 允许两次买卖，但同一时间只允许持有一支股票。也就意味着这两次买卖在时间跨度上不能有重叠（当然第一次的卖出时间和第二次的
      买入时间可以是同一天）。
    - 既然不能有重叠可以将整个序列以任意坐标i为分割点，分割成两部分：
                        prices[0:n-1] => prices[0:i] + prices[i:n-1]

      对于这个特定分割来说，最大收益为两段的最大收益之和。每一段的最大收益当然可以用I的解法来做。
      而III的解一定是对所有0<=i<=n-1的分割的最大收益中取一个最大值。为了增加计算效率，考虑采用dp来做bookkeeping。

    - 目标是对每个坐标i：
        1. 计算A[0:i]的收益最大值：用minPrice记录i左边的最低价格，用maxLeftProfit记录左侧最大收益
        2. 计算A[i:n-1]的收益最大值：用maxPrices记录i右边的最高价格，用maxRightProfit记录右侧最大收益。
        3. 最后这两个收益之和便是以i为分割的最大收益。

    - 将序列从左向右扫一遍可以获取1，从右向左扫一遍可以获取2。相加后取最大值即为答案。


stats:

    - 
    - 

int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) return 0;
        int n = prices.length;
        
        int[] leftProfit = new int[n];
        
        int maxLeftProfit = 0, 
            minPrice = prices[0];

        // loop 1, record max profit from left, (in array), and min num
        for(int i=1; i<n; i++) {
            if(prices[i] < minPrice)
                minPrice = prices[i];
            else
                maxLeftProfit = Math.max(maxLeftProfit, prices[i] - minPrice);

            leftProfit[i] = maxLeftProfit;

        }
        
        int ret = leftProfit[n-1];
        int maxRightProfit = 0, 
            maxPrice = prices[n-1];

        // loop 2, 
        for(int i=n-2; i>=0; i--) {
            if(prices[i]>maxPrice)
                maxPrice = prices[i];
            else
                maxRightProfit = Math.max(maxRightProfit, maxPrice-prices[i]);
            
            ret = Math.max(ret, maxRightProfit + leftProfit[i]);
        }
        
        return ret;
    }



