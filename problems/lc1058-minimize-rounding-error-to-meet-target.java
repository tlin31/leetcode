1058. Minimize Rounding Error to Meet Target - Medium


Given an array of prices [p1,p2...,pn] and a target, round each price pi to Roundi(pi) so that 
the rounded array [Round1(p1),Round2(p2)...,Roundn(pn)] sums to the given target. 

Each operation Roundi(pi) could be either Floor(pi) or Ceil(pi).

Return the string "-1" if the rounded array is impossible to sum to target. 
Otherwise, return the smallest rounding error, which is defined as Σ |Roundi(pi) - (pi)| for i from 
1 to n, as a string with three places after the decimal.

 

Example 1:

Input: prices = ["0.700","2.800","4.900"], target = 8
Output: "1.000"
Explanation: 
Use Floor, Ceil and Ceil operations to get (0.7 - 0) + (3 - 2.8) + (5 - 4.9) = 0.7 + 0.2 + 0.1 = 1.0 .


Example 2:

Input: prices = ["1.500","2.500","3.500"], target = 10
Output: "-1"
Explanation: 
It is impossible to meet the target.
 

Note:

1 <= prices.length <= 500.
Each string of prices prices[i] represents a real number which is between 0 and 1000 and has 
exactly 3 decimal places.
target is between 0 and 1000000.


******************************************************
key:
	- greedy, knapsack DP, Math
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: greedy


Stats:

	- 
	- 


Method:

	-	
	-  In order to meet a target by rounding, we can first floor every number and check out if 
	   sum is within range [target-length, target]. 
	   If so, we can switch the numbers from floored to ceiled one by one until we reach the target.

	-  In order to minimize the rounding error, we can determine the optimal order to switch the 
	   elements. Ideally, the closer the value to ceiling, the sooner it should be switched.

	-  Note integer representation's floor and ceil are the same, so don't have a choice to switch.

class Solution {
    public String minimizeError(String[] prices, int target) {
        float roundErr = 0;
        int allFloor = target;
        PriorityQueue<Double> diffHeap = new PriorityQueue<>();
        
        for (String s : prices) {
            float f = Float.valueOf(s);
            double low = Math.floor(f);
            double high = Math.ceil(f);
            
            // currently we always round to floor, but want to know the cost if an elem switch from 
            // floor to ceiling --> orig cost + (cost to ceil) - (cost to floor)
            if (low != high)
                diffHeap.offer((high-f)-(f-low));
            
            roundErr += f-low;
            allFloor -= low;
        }
        
        // allFloor = sum of all smallest rounded possible price, must be >= 0 b/c can't go 
        // any smaller. 
        // need to > heap size, because if every element switch from floor to ceil, the max diff = 1
        // can't make up for decreasing the target to 1
        if (allFloor < 0 || allFloor > diffHeap.size())
            return "-1";
        
        // use what's left in allFloor = # of elements we can switch from floor it to ceiling it
        while (allFloor-- > 0)
            roundErr += diffHeap.poll();
        
        return String.format("%.3f", roundErr);
    }
}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	hashmap stores < ceil or floor, cost to ceil/floor>
	-	generates all possible combination




class Solution {
    public String minimizeError(String[] prices, int target) {
        int n = prices.length;
        Map<Integer, Double>[] dp = new HashMap[n + 1];
        dp[0] = new HashMap<>();
        dp[0].put(0, 0.0);
        for (int i = 1; i <= n; i++) {
            double num = Double.parseDouble(prices[i - 1]);

            double upperCost = Math.ceil(num) - num;
            int upper = (int)Math.ceil(num);
            double lowerCost = num - Math.floor(num);
            int lower = (int)Math.floor(num);

            dp[i] = new HashMap<>();

            for (Map.Entry<Integer, Double> entry : dp[i - 1].entrySet()) {
                int upperKey = entry.getKey() + upper;
                dp[i].put(upperKey, Math.min(dp[i].getOrDefault(upperKey, Double.MAX_VALUE), 
                							entry.getValue() + upperCost));

                int lowerKey = entry.getKey() + lower;
                dp[i].put(lowerKey, Math.min(dp[i].getOrDefault(lowerKey, Double.MAX_VALUE), 
                								entry.getValue() + lowerCost));
            }
        } 
        return dp[n].containsKey(target) ? String.format ("%.3f", dp[n].get(target)) : "-1";
    }
}

ex. ["0.700","2.800","4.900"], target = 8

{0=0.7, 1=0.3}
{2=1.5, 3=0.95, 4=0.5} --> key = 2 = floor(0.7) + floor(2.8), cost = 0.5 + 0.8 = 1.5
{6=2.4, 7=1.6, 8=1, 9=0.6}


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

