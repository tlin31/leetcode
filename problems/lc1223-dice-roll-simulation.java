1223. Dice Roll Simulation - Medium


A die simulator generates a random number from 1 to 6 for each roll. You introduced a constraint 
to the generator such that it cannot roll the number i more than rollMax[i] (1-indexed) consecutive times. 

Given an array of integers rollMax and an integer n, return the number of distinct sequences that 
can be obtained with exact n rolls.

Two sequences are considered different if at least one element differs from each other. Since the 
answer may be too large, return it modulo 10^9 + 7.

 

Example 1:

Input: n = 2, rollMax = [1,1,2,2,2,3]
Output: 34
Explanation: There will be 2 rolls of die, if there are no constraints on the die, there are 6 * 6 = 36
possible combinations. In this case, looking at rollMax array, the numbers 1 and 2 appear at most once 
consecutively, therefore sequences (1,1) and (2,2) cannot occur, so the final answer is 36-2 = 34.


Example 2:

Input: n = 2, rollMax = [1,1,1,1,1,1]
Output: 30


Example 3:

Input: n = 3, rollMax = [1,1,1,2,2,3]
Output: 181
 

Constraints:

1 <= n <= 5000
rollMax.length == 6
1 <= rollMax[i] <= 15



******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: naive dp

huahua: https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1223-dice-roll-simulation/

Stats:
	// 6 = dice possible value, 15 = max of roll max

	Time complexity: O(n*6*6*15)
	Space complexity: O(n*6*15) -> O(6*15)


Method:

	-	
	-	

	def: dp[i][j][k] := # of sequences ends with k consecutive j after i rolls
	Init: dp[1][*][1] = 1

	transition at p:
	dp[i][j][1] = sum(dp[i-1][p][*])	if p != j
	dp[i][j][k + 1] = dp[i-1][j]][k]



public int dieSimulator(int n, int[] rollMax) {
        int mod = 1000000007;
        int[][][] dp = new int[n + 1][7][16]; // add each parameter by one since we need to reach the top of these number
        for(int i = 1; i < 7; i++){
            dp[1][i][1] = 1;
        }

        // state tranfer 
        for(int i = 2; i <= n; i++){
            for(int j = 1; j <= 6; j++){
                for(int t = 1; t <= 6; t++){
                    if(t != j){
                        // for non-consecutive situation
                         for(int k = 1; k <= 15; k++){
                            dp[i][j][1] = (dp[i][j][1] + dp[i - 1][t][k]) % mod;
                        }
                    }else{
                        // for consecutive situation
                        for(int k = 1; k <= rollMax[j - 1] - 1; k++){
                            dp[i][j][k + 1] += dp[i - 1][j][k];
                        }
                    }
                    
                }
            }
        }
        
        int ans = 0;
        for(int i = 1; i <= 6; i++){
            for(int j = 1; j <= 15; j++){
                ans = (ans + dp[n][i][j]) % mod;
            }
        }
        return ans;
    }

=======================================================================================================
method 2: DP with i dices & last number

Stats:

	Time complexity: O(n*6)
	Space complexity: O(n*6)


Method:

	- dp[i][j] := # of sequences of length i end with value j
	- dp[i][j] := sum(dp[i-1]) – invalid

		Base case: 
			dp[1][x] = 1 for x from 0~6 --> length = 1 only has 1 sequence

		Without constraints:
			dp[i][j] = sum(dp[i-1]) --> sum of all previous dp[i-1][1]+dp[i-1][2]+dp[i-1][3]+....
									--> just append this new value j to all possible previous sequence

		Invalid ones:
			let k = i - rollMax[j]

			if k == 1: dp[i][j] -= 1  --> 超过了1位，
										  ex. rollMax[6] = 3, i = 4, k = 1. xxx|6 = sum(all), but 
										  	  only 1 number is invalid: 666|6
			if k > 1:
				 dp[i][j] -= sums[k - 1] - dp[k - 1][j]

				 超过了多位数. ex. rollMax[6] = 3, i = 8, k = 5.
				 all possible sequence: xxxxxxx|6 --> sum(dp[i-1]) = sum(dp[7]) sequence, 
				 									  所有长度为7结尾的seq，不用管最后一位的value

				 valid sequence: xxxx66x|6 和 xxxxx66|6 ....subproblems already taken care of

				 invalid seq只有: xxxx666|6
				 求 # of xxxx666 = # of xxx@, 并且@不等于6, 即求长度为4的sequence并且最后一位不是6 
				 				 = sum of (xxxx) - (xxx6)
				 				 = sum(4) - dp[4][6]

				 Thus, dp[8][6] = sum(dp[7]) - (sum[4] - dp[4][6])


class Solution {
    public int dieSimulator(int n, int[] rollMax) {
		int kMod = (int)1e9 + 7;
		int[][] dp = new int[n+1][7];
		int[] sums = new int[n+1];			// = sum[i] of all previous dp[i-1][1] ~ dp[i-1][6]

		for (int val = 0; val < 6; val++) {
			dp[1][val] = 1;
			sums[1] += dp[1][val];
		}

	    for (int i = 2; i <= n; ++i)
	      for (int j = 0; j < 6; ++j) {        
	        int k = i - rollMax[j];

	        if (k <= 1)
	        	int invalid = Math.max(k, 0)		// either 1 or zero

	        else
	        	int invalid = sums[k - 1] - dp[k - 1][j]

	        dp[i][j] = ((sums[i - 1] - invalid) % kMod + kMod) % kMod;

	        sums[i] = (sums[i] + dp[i][j]) % kMod;
	      }
	  
	    return sums[n];	

	}
}

=======================================================================================================
method 3: top-down dfs

Stats:

	- 
	- 


Method:

	-	
	-	
  
Class Solution{
	int MOD = (int)1e9 + 7;

	public int dieSimulator(int n, int[] rollMax) {
		int[][][] mem = new int[n+1][7][16];
		return dfs(n, 0, 0, rollMax, mem);
	}

	// tail is the actual number(1~6) of seq, consectiveNum is the # of consectives in sequence tail.
	private int dfs(int leftRolls, int tail, int consectiveNum, int[] rollMax, int[][][] mem) {
		if (leftRolls <= 0) 
			return 1;

		int ans = 0;

		if (mem[leftRolls][tail][consectiveNum] > 0) {
			return mem[leftRolls][tail][consectiveNum];
		}

		for (int k = 1; k <= 6; ++k) {

			// new tail
			if (k != tail && rollMax[k-1] >= 1) {
				ans = (ans + dfs(leftRolls-1, k, 1, rollMax, mem))%MOD;
			}

			// same tail
			if (k == tail && rollMax[k-1] >= consectiveNum+1) {
				ans = (ans + dfs(leftRolls-1, k, consectiveNum+1, rollMax, mem))%MOD;
			}
		}

		mem[leftRolls][tail][consectiveNum] = ans;
		return ans;
	}
}
