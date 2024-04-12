1155. Number of Dice Rolls With Target Sum - Medium



You have d dice, and each die has f faces numbered 1, 2, ..., f.

Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice so the 
sum of the face up numbers equals target.

 

Example 1:

Input: d = 1, f = 6, target = 3
Output: 1
Explanation: 
You throw one die with 6 faces.  There is only one way to get a sum of 3.


Example 2:

Input: d = 2, f = 6, target = 7
Output: 6
Explanation: 
You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
1+6, 2+5, 3+4, 4+3, 5+2, 6+1.


Example 3:

Input: d = 2, f = 5, target = 10
Output: 1
Explanation: 
You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.


Example 4:

Input: d = 1, f = 2, target = 3
Output: 0
Explanation: 
You throw one die with 2 faces.  There is no way to get a sum of 3.


Example 5:

Input: d = 30, f = 30, target = 500
Output: 222616187
Explanation: 
The answer must be returned modulo 10^9 + 7.
 

Constraints:

1 <= d, f <= 30
1 <= target <= 1000


******************************************************
key:
	-  DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:
	Time complexity: O(|d|*|f|*target)
	Space complexity: O(|d|*target) -> O(target)


Method:

	-	
	-	


dp[i][k] := ways to have sum of k using all first i dices.

Base case:
	dp[0][0] = 1
ans: 
	dp[d][target]

	dp[i][k] = sum(dp[i – 1][k – j]), 1 <= j <= f
			 = without current dice & without this dice value, how many ways there are



    public int numRollsToTarget(int d, int f, int target) {
        int MOD = 1000000007;
        int[][] dp = new int[d + 1][target + 1]; 
        dp[0][0] = 1;

        // i = number of dices
        for(int i = 1; i <= d; i++) {

        	// j = face value on the dice
            for(int j = 1; j<= f; j++) {

            	// k = sum values of all previous dices
                for(int k = j; k <= target; k++) {
                    dp[i][k] = (dp[i][k] + dp[i - 1][k - j]) % MOD; 
            }
        }
            
        
        return dp[d][target];
    }




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


class Solution:
    def numRollsToTarget(self, d: int, f: int, target: int) -> int:
        dp = [[0 for i in range(target + 1)] for j in range(d + 1)]
        dp[0][0] = 1
        mod = 10 ** 9 + 7
        for i in range(1, d + 1):
            for j in range(1, target + 1):
                k = 1
                while k <= min(j, f):
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % mod
                    k += 1
        return dp[d][target] % mod


=======================================================================================================
method 2: DFS

Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    int MOD = 1000000000 + 7;
    Map<String, Integer> memo = new HashMap<>();
    public int numRollsToTarget(int d, int f, int target) {
        if (d == 0 && target == 0) {
            return 1;
        }
        if (d == 0 || target == 0) {
            return 0;
        }
        String str = d + " " + target;
        if (memo.containsKey(str)) {
            return memo.get(str);
        }
        int res = 0;
        for (int i = 1; i <= f; i++) {
            if (target >= i) {
                res = (res + numRollsToTarget(d - 1, f, target - i)) % MOD;
            } else {
                break;
            }
        }
        memo.put(str, res);
        return res;
    }
}


Bottom Up Version

class Solution {
    public int numRollsToTarget(int d, int f, int target) {
        int MOD = (int)Math.pow(10, 9) + 7;
        long[][] dp = new long[d + 1][target + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= d; i++) {
            for (int j = 0; j <= target; j++) {
                for (int k = 1; k <= f; k++) {
                    if (j >= k) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                    } else {
                        break;
                    }
                }
            }
        }
        return (int)dp[d][target];
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

