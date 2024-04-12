1449. Form Largest Integer With Digits That Add up to Target - Hard


Given an array of integers cost and an integer target. Return the maximum integer you can paint 
under the following rules:

The cost of painting a digit (i+1) is given by cost[i] (0 indexed).
The total cost used must be equal to target.
Integer does not have digits 0.
Since the answer may be too large, return it as string.

If there is no way to paint any integer given the condition, return "0".

 

Example 1:

Input: cost = [4,3,2,5,6,7,2,5,5], target = 9
Output: "7772"
Explanation:  The cost to paint the digit '7' is 2, and the digit '2' is 3. Then cost("7772") = 
2*3+ 3*1 = 9. You could also paint "977", but "7772" is the largest number.

Digit    cost
  1  ->   4
  2  ->   3
  3  ->   2
  4  ->   5
  5  ->   6
  6  ->   7
  7  ->   2
  8  ->   5
  9  ->   5


Example 2:

Input: cost = [7,6,5,5,5,6,8,7,8], target = 12
Output: "85"
Explanation: The cost to paint the digit '8' is 7, and the digit '5' is 5. Then cost("85") = 7 + 5 = 12.

Example 3:

Input: cost = [2,4,6,2,4,6,4,4,4], target = 5
Output: "0"
Explanation: Its not possible to paint any integer with total cost equal to target.


Example 4:

Input: cost = [6,10,15,40,40,40,40,40,40], target = 47
Output: "32211"
 

Constraints:

cost.length == 9
1 <= cost[i] <= 5000
1 <= target <= 5000


******************************************************
key:
	- DP- knapsack or DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP - knapsack


Stats:

	- Time strict O(target)
	- Space O(target)


Method:
	- The total digits contribute most to the value of the number. 7772 is larger than 997, 11111 
	  is larger than 9999. Our primary task is to separate the target into as many small sums as 
	  possible. To achieve this, we will use dynamic programming.

	- dp[i]  = The most digits we can use to reach exactly value i, i is within targets

		For each increment of target, we will go over current attempt n from 1 to 9 to seek 
		the maximum digits to reach the new target so far:

		  	dp[newTarget] = Math.max(dp[newTarget], dp[newTarget - cost[n]] + 1).

		dp[target] stores the max digits to reach target. 

	- The second part is to construct the largest possible number using dp[target] digits. 
	  Searching from largest possible number n=9 to smallest possible number n=1, we will use 
	  "whether target >= cost[n] && dp[target] == dp[target - cost[n]] + 1 ? " to test if current n 
	  can be used. 

class Solution {
    public String largestNumber(int[] cost, int target) {

    	// dp[i] = the max digits to reach i, dp[0] = 0
        int[] dp = new int[target + 1]; 
        for(int try_target = 1; try_target <= target; try_target++) {
            dp[try_target] = Integer.MIN_VALUE;
            for(int n = 0; n < cost.length; n++) {

            	// if try target is enough to cover the cost of this
                if(try_target >= cost[n])
                    dp[try_target] = Math.max(dp[try_target], dp[try_target - cost[n]] + 1);
            }
        }

        // not possible to reach target
        if(dp[target] < 0) 
        	return "0"; 

        // reform the result string
        StringBuilder sb = new StringBuilder();

        // now that we know the max digits, we add possible numbers from large to small 
        // to get largest combination
        for(int n = 8; n >= 0; n--) { 

        	// verify that we can indeed use this relatively large digit, as many times as possible
            while(target >= cost[n] && dp[target] == dp[target - cost[n]] + 1) { 
                sb.append(n + 1);
                target -= cost[n];
            }               
        }
        return sb.toString();
    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DFS

Stats:

	- Time: O(9 * target^2), the time for creating new string in line curr = d + curr; can cost up 
	        to O(target)
	- Space: O(target^2), because we store total target strings, each string up to target chars.



Method:

	- want largest number, so start from d = 9 down back to 1	
	-	

class Solution {
    public String largestNumber(int[] cost, int target) {
        String[] dp = new String[target + 1];
        return dfs(cost, target, dp);
    }

    String dfs(int[] cost, int target, String[] dp) {
        if (target < 0) return "0"; // not found
        if (target == 0) return "";
        if (dp[target] != null) return dp[target];
        String ans = "0";
        for(int d = 9; d >= 1; d--) {
            String curr = dfs(cost, target - cost[d - 1], dp);

            // skip if can't solve sub-problem
            if (curr.equals("0")) 
            	continue; 

            // add the number at the front
            curr = d + curr;
            if (ans.equals("0") || curr.length() > ans.length()) {
                ans = curr;
            }
        }

        dp[target] = ans;
        return dp[target];
    }
}








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

