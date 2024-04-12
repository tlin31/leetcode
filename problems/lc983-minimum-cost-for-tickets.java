983. Minimum Cost For Tickets - Medium

In a country popular for train travel, you have planned some train travelling one year in advance.  
The days of the year that you will travel is given as an array days.  Each day is an integer from 
1 to 365.

Train tickets are sold in 3 different ways:

a 1-day pass is sold for costs[0] dollars;
a 7-day pass is sold for costs[1] dollars;
a 30-day pass is sold for costs[2] dollars.
The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on 
day 2, then we can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.

Return the minimum number of dollars you need to travel every day in the given list of days.

 

Example 1:

Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation: 
For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
In total you spent $11 and covered all the days of your travel.


Example 2:

Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
Output: 17
Explanation: 
For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
In total you spent $17 and covered all the days of your travel.
 

Note:

1 <= days.length <= 365
1 <= days[i] <= 365
days is in strictly increasing order.
costs.length == 3
1 <= costs[i] <= 1000


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1: input's day variant 

Method:

	-  dp[i] = i-th day the minimum cost of the tickets. 
	   The size of the dp array depends on the last travel day, so don't need an array w/ length 365.

	- use a boolean array to mark the travel days: if it is not a travel day we don't need a ticket. 
	  if it is a travel day, we consider three scenarios (with three types of tickects):

		If a 1-day ticket on day i, dp[i] = dp[i - 1] + cost[0]
		If a 7-day ticket ending on day i, dp[i] = min(dp[i - 7], dp[i - 6] ... dp[i - 1]) + cost[1]
		If a 30-day ticket ending on day i, dp[i] = min(dp[i - 30], dp[i - 29] ... dp[i - 1]) + cost[2]

	But since the value of dp array is always [increasing]:
		For a 7-day ticket ending on day i, dp[i] = dp[i - 7] + cost[1]
		For a 30-day ticket ending on day i, dp[i] = dp[i - 30] + cost[2]
	-	


Stats:

	- Time Complexity: O(n), where n is the number of travel days.
	- Space Complexity: O(38). Stricter, it's a sum of duration for all pass types (1 + 7 + 30 in our case).


 public int mincostTickets(int[] days, int[] costs) {
    int lastDay = days[days.length - 1]; 
    int[] dp = new int[lastDay + 1]; 

    boolean[] isTravelDays = new boolean[lastDay + 1];

    // mark the travel days
    for(int day : days) 
    	isTravelDays[day] = true;
    
    for(int i = 1; i <= lastDay; i++) {
        // no need to buy ticket if it is not a travel day
        if(!isTravelDays[i]) { 
            dp[i] = dp[i - 1];
            continue;
        }
        // select which type of ticket to buy
        dp[i] = costs[0] + dp[i - 1]; // 1-day
        dp[i] = Math.min(costs[1] + dp[Math.max(i - 7, 0)], dp[i]); // 7-day
        dp[i] = Math.min(costs[2] + dp[Math.max(i - 30, 0)], dp[i]); // 30-day
    }
    return dp[lastDay];
}

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 


public int mincostTickets(int[] days, int[] costs) {
        if(days.length == 1) 
        	return costs[0];

    	int lastDay = days[days.length - 1]; 
        int[] dp = new int[lastDay + 1];

        for(int i = 0; i < days.length; i++){
            int c1 = 0;
            int c7 = 0;
            int c30 = 0;
            int min = 0;
            if(days[i] - 1 <= 0) 
            	c1 = costs[0];
            else 
            	c1 = dp[days[i]-1] + costs[0];

            if(days[i] - 7 <= 0) 
            	c7 = costs[1];
            else 
            	c7 = dp[days[i]-7] + costs[1];

            if(days[i] - 30 <= 0) 
            	c30 = costs[2];
            else 
            	c30 = dp[ days[i]-30 ] + costs[2];

            min = Math.min(c1,Math.min(c7,c30));

            dp[days[i]] = min;
            for(int j = days[i]+1;i+1 < days.length && j < days[i+1];j++){
                dp[j] = dp[j-1];
            }
        }
        return dp[dp.length-1];
    }
