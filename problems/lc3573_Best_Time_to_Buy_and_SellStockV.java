3573. Best Time to Buy and Sell Stock V - Medium

You are given an integer array prices where prices[i] is the price of a stock in dollars 
on the ith day, and an integer k.

You are allowed to make at most k transactions, where each transaction can be either of 
the following:

Normal transaction: Buy on day i, then sell on a later day j where i < j. You profit 
prices[j] - prices[i].

Short selling transaction: Sell on day i, then buy back on a later day j where i < j. 
You profit prices[i] - prices[j].

Note that you must complete each transaction before starting another. Additionally, you 
can't buy or sell on the same day you are selling or buying back as part of a previous transaction.

Return the maximum total profit you can earn by making at most k transactions.

 

Example 1:

Input: prices = [1,7,9,8,2], k = 2

Output: 14

Explanation:

We can make $14 of profit through 2 transactions:
A normal transaction: buy the stock on day 0 for $1 then sell it on day 2 for $9.
A short selling transaction: sell the stock on day 3 for $8 then buy back on day 4 for $2.

Example 2:

Input: prices = [12,16,19,19,8,1,19,13,9], k = 3

Output: 36

Explanation:

We can make $36 of profit through 3 transactions:
A normal transaction: buy the stock on day 0 for $12 then sell it on day 2 for $19.
A short selling transaction: sell the stock on day 3 for $19 then buy back on day 4 for $8.
A normal transaction: buy the stock on day 5 for $1 then sell it on day 6 for $19.
 

Constraints:

2 <= prices.length <= 103
1 <= prices[i] <= 109
1 <= k <= prices.length / 2


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:


我们定义一个三维 DP：

	f[i][j][s]


	i 表示第 i 天（0-based），价格为 prices[i]

	j 表示最多进行了 j 笔交易（普通 + short 算在这 j 笔以内）

	s 是当前状态，有三种：

	s = 0：当前没有持股也没空头仓位（空仓状态）

	s = 1：当前持有一股（普通买入但未卖出）

	s = 2：当前处于空头（已卖出而未买回）

状态转移如下：

for i in 1..n-1, for j in 1..k:

  f[i][j][0] = max(
      f[i-1][j][0],                             // 前一天空仓，今天什么也不做
      f[i-1][j][1] + prices[i],                 // 前一天持股，今天卖出普通股票
      f[i-1][j][2] - prices[i]                  // 前一天空头仓位，今天买回（cover short）
  )

  f[i][j][1] = max(
      f[i-1][j][1],                             // 前一天已经持股，今天保持
      f[i-1][j-1][0] - prices[i]                // 前一天空仓，用一笔交易买入普通股票
  )

  f[i][j][2] = max(
      f[i-1][j][2],                             // 前一天空头仓位，今天保持
      f[i-1][j-1][0] + prices[i]                // 前一天空仓，用一笔交易“卖空”（即先卖再买）
  )


初始值（第 0 天）：

f[0][j][0] = 0                      // 第一天还没做任何交易，没持有
f[0][j][1] = -prices[0]             // 第一天买入普通股票
f[0][j][2] = prices[0]              // 第一天做空（卖空）: 卖空获得 prices[0]，之后买回要扣除价格


最终答案是 f[n-1][k][0]，即最后一天“空仓”（既不持有也未空头）状态下的最大利润。

Stats:

	Time O(nk)
	Space O(k)

    public long maximumProfit(int[] A, int k) {
        long[] res = new long[k + 1], 
        bought = new long[k], 
        sold = new long[k];
        
        Arrays.fill(bought, -1000000000);
        for (int a : A) {
            for (int j = k; j >= 1; j--) {
                res[j] = Math.max(res[j], Math.max(bought[j - 1] + a, sold[j - 1] - a));
                bought[j - 1] = Math.max(bought[j - 1], res[j - 1] - a);
                sold[j - 1] = Math.max(sold[j - 1], res[j - 1] + a);
            }
        }

        long ans = 0;
        for (long v : res)
            ans = Math.max(ans, v);
        return ans;
    }

