746. Min Cost Climbing Stairs - Easy

On a staircase, the i-th step has some non-negative cost cost[i] assigned (0 indexed).

Once you pay the cost, you can either climb one or two steps. You need to find minimum cost to reach 
the top of the floor, and you can either start from the step with index 0, or the step with index 1.

Example 1:
Input: cost = [10, 15, 20]
Output: 15
Explanation: Cheapest is start on cost[1], pay that cost and go to the top.

Example 2:
Input: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
Output: 6
Explanation: Cheapest is start on cost[0], and only step on 1s, skipping cost[3].
Note:
cost will have a length in the range [2, 1000].
Every cost[i] will be an integer in the range [0, 999].


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

	-	
	-	

Stats:

	- 
	- O(n) time, O(1) space


	public int minCostClimbingStairs(int[] cost) {

        // assume cur. at 2
        int twoStepBefore = cost[0];
        int oneStepBefore = cost[1];
        int curr = 0;
        for(int i = 2; i < cost.length; i++){
            curr = Math.min(twoStepBefore,oneStepBefore) + cost[i];
            twoStepBefore = oneStepBefore;
            oneStepBefore = curr;
        }
        return Math.min(oneStepBefore,twoStepBefore);
    }

----------------------------------
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        int f1 = 0, f2 = 0;
        for (int i = cost.length - 1; i >= 0; --i) {
            int f0 = cost[i] + Math.min(f1, f2);
            f2 = f1;
            f1 = f0;
        }
        return Math.min(f1, f2);
    }
}


=======================================================================================================
method 2:

Method:

	-	top down
	-	


Stats:

	- 
	- 

public int minCostClimbingStairs(int[] cost) {
        int[] memo = new int[cost.length];
        if(cost.length == 2)
            return Math.min(cost[0], cost[1]);
        
        int cost1 = findMin(cost, 0, memo);
        int cost2 = findMin(cost, 1, memo);
        
        return Math.min(cost1, cost2);
    }
    
    int findMin(int[] cost, int i, int[] memo) {
        if(i == cost.length-1 || i == cost.length-2)
            return cost[i];

        if(memo[i] > 0) 
        	return memo[i];
        
        int cost1 = findMin(cost, i+1, memo) + cost[i];
        int cost2 = findMin(cost, i+2, memo) + cost[i];
       
        memo[i] = Math.min(cost1, cost2);
        
        return Math.min(cost1, cost2);
    }
=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 




