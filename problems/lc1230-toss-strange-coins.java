1230. Toss Strange Coins - Medium


You have some coins.  The i-th coin has a probability prob[i] of facing heads when tossed.

Return the probability that the number of coins facing heads equals target if you toss every 
coin exactly once.

 

Example 1:

Input: prob = [0.4], target = 1
Output: 0.40000


Example 2:
Input: prob = [0.5,0.5,0.5,0.5,0.5], target = 0
Output: 0.03125
 

Constraints:

1 <= prob.length <= 1000
0 <= prob[i] <= 1
0 <= target <= prob.length
Answers will be accepted as correct if they are within 10^-5 of the correct answer.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DFS


Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    
    Double[][] memo;
    
    public double probabilityOfHeads(double[] prob, int target) {
        
        if(target > prob.length) 
        	return 0.0;
     	
     	// memo[i][j] = prob that at index i, we have j heads
        memo = new Double[prob.length + 1][prob.length + 1];
        
        return dfs(prob, target, 0);
    }
    
    public double dfs(double[] prob, int target, int index){
        
        if(target < 0) 
        	return 0.0;
        
        if(index == prob.length) {
            
            if(target == 0) 
            	return 1.0;
            
            return 0.0;
        }
        
        if(memo[index][target] != null) 
        	return memo[index][target];
        
        double res = 0.0;
        
        // this is head
        res += prob[index] * dfs(prob, target - 1, index + 1); 

		// this is not head
        res += (1 - prob[index]) * dfs(prob, target, index + 1); 
        
        memo[index][target] = res;
        
        return res;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def probabilityOfHeads(self, probs: List[float], target: int) -> float:
        memo = {}
        def dfs(index, target):
            if index == 0 and target == 0:
                return 1

            if index == 0:
                return 0
				
            # Add this line to prune unnecessary branches (pruning)
            if index < target:
                return 0

            if (index, target) in memo:
                return memo[index, target]

            memo[index, target] = 0

            if target:
                memo[index, target] += dfs(index - 1, target - 1) * probs[index - 1]

            memo[index, target] += dfs(index - 1, target) * (1 - probs[index - 1])

            return memo[index, target]

        return dfs(len(probs), target)

=======================================================================================================
method 2: DP

Stats:

	- Time O(N^2)
	- Space O(N)



Method:

	-	dp[c][k] is the prob of tossing c first coins and get k faced up.

	-	dp[c][k] = dp[c - 1][k] * (1 - p) + dp[c - 1][k - 1] * p), where p is the prob for c-th coin.

    double probabilityOfHeads(double[] prob, int target) {
        //dp[i,j] == up until the ith position, we have j heads.. 
        int n = prob.size();
        vector<vector<double>> dp(n,vector<double>(n+1,0));

        //dp[0,0]: no heads
        dp[0][0] = 1-prob[0];
        dp[0][1] = prob[0];
        for (int i = 1; i < n; ++ i) {
            dp[i][0] = dp[i-1][0] * (1-prob[i]);
        }

        // need to set dp[0][1] is set b/c can't set dp[i-1][j], which starts from dp[0][1]
        // prob[i] * dp[i-1][j-1] = previous is head
        for ( int j = 1; j <= n; ++ j) {
            for(int i = 1; i<n; ++i) {
                dp[i][j] = prob[i] * dp[i-1][j-1] + (1-prob[i]) * dp[i-1][j];
            }
        }
        return dp[n-1][target];
    }


optimized 1d:

    public double probabilityOfHeads(double[] prob, int target) {
        double[] dp = new double[target + 1];
        dp[0] = 1.0;
        for (int i = 0; i < prob.length; ++i)
            for (int k = Math.min(i + 1, target); k >= 0; --k)
                dp[k] = (k > 0 ? dp[k - 1] : 0) * prob[i] + dp[k] * (1 - prob[i]);
        return dp[target];
    }



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def probabilityOfHeads(self, prob, target):
        dp = [1] + [0] * target
        for p in prob:
            for k in xrange(target, -1, -1):
                dp[k] = (dp[k - 1] if k else 0) * p + dp[k] * (1 - p)
        return dp[target]

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

