1269. Number of Ways to Stay in the Same Place After Some Steps - Hard


You have a pointer at index 0 in an array of size arrLen. At each step, you can move 1 position 
to the left, 1 position to the right in the array or stay in the same place  (The pointer should 
not be placed outside the array at any time).

Given two integers steps and arrLen, return the number of ways such that your pointer still at 
index 0 after exactly steps steps.

Since the answer may be too large, return it modulo 10^9 + 7.

 

Example 1:

Input: steps = 3, arrLen = 2
Output: 4
Explanation: There are 4 differents ways to stay at index 0 after 3 steps.
Right, Left, Stay
Stay, Right, Left
Right, Stay, Left
Stay, Stay, Stay


Example 2:

Input: steps = 2, arrLen = 4
Output: 2
Explanation: There are 2 differents ways to stay at index 0 after 2 steps
Right, Left
Stay, Stay


Example 3:

Input: steps = 4, arrLen = 2
Output: 8
 

Constraints:

1 <= steps <= 500
1 <= arrLen <= 10^6


******************************************************
key:
	- DFS
	- edge case:
		1) # of steps --> if step == 1, early check
		2) length of array
		3) 

******************************************************



=======================================================================================================
Method 1: recursive


Stats:

	- Time: O(N^2) //worst case arrLen > steps
	- Space: O(Min(N, M))



Method:

	- shouldnt go beyond [steps/2] position or the array, because will never come back
	-	


memo[i][j]: numWays of "till index i with j steps"

class Solution {
    double mod = 1e9+7;

    public int numWays(int steps, int arrLen) {
        double[][] memo = new double[steps/2+1][steps+1]; 
        for (double[] m : memo) {
            Arrays.fill(m, -1);
        }
        return (int) helper(memo, 0, arrLen, steps);
    }

    private double helper(double[][] memo, int index, int arrLen, int remainingSteps) {
        
        // come back to index 0
        if (index==0 && remainingSteps==0) {
            return 1;
        }

        if (index<0 || index>=arrLen || remainingSteps==0 || index > remainingSteps) {
            return 0;
        }

        if (memo[index][remainingSteps] != -1) {
            return memo[index][remainingSteps];
        }

        double moveRight = helper(memo, index + 1, arrLen, remainingSteps - 1);
        double moveLeft = helper(memo, index - 1, arrLen, remainingSteps - 1);
        double stay = helper(memo, index, arrLen, remainingSteps - 1);

        memo[index][remainingSteps] = ((moveRight % mod + moveLeft % mod) % mod + stay % mod) % mod;

        return memo[index][remainingSteps];
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:

	- dp[steps][position], from this state we can either:
		1. Stay consume one step and stay at the same position => dp[steps-1][position]
		2. Go right: consume one step and go right => dp[steps-1][position+1]
		3. Go left (if not at position zero): consume one step and go left 
		      => if position > 0, then dp[steps-1][position-1]

		dp[steps][position] = dp[steps-1][position] + dp[steps-1][position+1] + dp[steps-1][position-1].

	- base case:
		dp[1][0] = 1:
			at position zero and with only one step, then there is only one way (just stay)
		dp[1][1] = 1:
			We are at position one and with only one step, then there is only one way (go left) => 
		Notice that we can only go right as far as many steps we have. 



class Solution {
    public int numWays(int steps, int arrLen) {
        int maxPos = Math.min(steps, arrLen);
        long[][] dp = new long[steps+1][maxPos+1];
        
        dp[1][0] = 1;
        dp[1][1] = 1;
        for(int i = 2; i <= steps; i++) {
            for(int j = 0; j < maxPos; j++) {
                dp[i][j] = (dp[i-1][j] + dp[i-1][j+1] + (j>0?dp[i-1][j-1]:0))%1000000007;
            }
        }
        
        return (int)dp[steps][0];
    }
}




----- space optimize:

class Solution {
    private final int MOD = 1000000007;
    public int numWays(int steps, int arrLen) {
        int[] last = new int[Math.min(steps, arrLen)+1];
        last[0] = 1;
        for (int i = 1; i <= steps; i++) {
            int[] curr = new int[Math.min(arrLen, steps)+1];
            for (int j = 0; j < Math.min(steps, arrLen); j++) {
                if (last[j] > 0) {
                    curr[j] = (curr[j] + last[j]) % MOD;
                }
                if (j+1 < arrLen && last[j+1] > 0) {
                    curr[j] = (curr[j] +last[j+1]) % MOD;
                }
                if (j-1 >= 0 && last[j-1] > 0) {
                    curr[j] = (curr[j] + last[j-1]) % MOD;
                }
            }
            last = curr.clone();
        }
        return last[0]; 
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

