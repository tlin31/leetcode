790. Domino and Tromino Tiling - Medium


We have two types of tiles: a 2x1 domino shape, and an "L" tromino shape. These shapes may be rotated.

XX  <- domino

XX  <- "L" tromino
X


Given N, how many ways are there to tile a 2 x N board? Return your answer modulo 10^9 + 7.

(In a tiling, every square must be covered by a tile. Two tilings are different if and only if there 
are two 4-directionally adjacent cells on the board such that exactly one of the tilings has both 
squares occupied by a tile.)

Example:
Input: 3
Output: 5

Explanation: 
The five different ways are listed below, different letters indicates different tiles:
XYZ XXZ XYY XXY XYY
XYZ YYZ XZZ XYY XXY


Note:

N  will be in range [1, 1000].


******************************************************
key:
	- Pattern + math
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


https://leetcode.com/problems/domino-and-tromino-tiling/discuss/116581/Detail-and-explanation-of-O(n)-solution-why-dpn2*dn-1%2Bdpn-3

Stats:

	- 
	- 


Method:

	-	
	-	




dp[n]=dp[n-1]+dp[n-2]+ 2*(dp[n-3]+...+d[0])
	=dp[n-1]+dp[n-2]+dp[n-3]+dp[n-3]+2*(dp[n-4]+...+d[0])
	=dp[n-1]+dp[n-3]+(dp[n-2]+dp[n-3]+2*(dp[n-4]+...+d[0]))
	=dp[n-1]+dp[n-3]+dp[n-1]
	=2*dp[n-1]+dp[n-3]

 int numTilings(int N) {
    int md=1e9;
    md+=7;
    vector<long long> v(1001,0);
    v[1]=1;
    v[2]=2;
    v[3]=5;
    if(N<=3)
        return v[N];
    for(int i=4;i<=N;++i){
        v[i]=2*v[i-1]+v[i-3]; 
        v[i]%=md;
    }
    return v[N];
    
}



=======================================================================================================
method 2:



https://leetcode.com/problems/domino-and-tromino-tiling/solution/

Stats:

	- 
	- 


Method:

	- dp[state] = previous number of ways to fill i columns where the i-th row of the last column 
	              is filled if the i-th bit of state is 1.

	- dp[0] will be the number of ways to fill i columns where the last column has nothing filled; 
	  dp[1] will be the number of ways with the square in the last row filled; 
	  dp[2] will be the number of ways with the square in the first row filled;  
	  dp[3] will be the number of ways with the squares in both rows filled.

Graph: 0 is empty, x is tiled
	(0): 0 0   	--> 0 rows filled - it could have come from either:
    	 0 0 			having 0 rows filled and a vertical domino, or both rows filled and nothing.

	(1): 0 0	-->last row filled - it could have come from either:
		 x 0			0 rows filled and an L shaped tromino, or top row filled and a horizontal domino


	(2): x 0	--> first row filled - case is symmetric to the 'last row filled' case
		 0 0 

	(3): x 0	--> both rows filled - could have come from either:
		 x 0			0 rows filled and two horizontal dominos, OR 1 row filled and an L shaped tromino


	


class Solution {
    public int numTilings(int N) {
        int MOD = 1_000_000_007;
        long[] dp = new long[]{1, 0, 0, 0};
        for (int i = 0; i < N; ++i) {
            long[] ndp = new long[4];
            ndp[0b00] = (dp[0b00] + dp[0b11]) % MOD;
            ndp[0b01] = (dp[0b00] + dp[0b10]) % MOD;
            ndp[0b10] = (dp[0b00] + dp[0b01]) % MOD;
            ndp[0b11] = (dp[0b00] + dp[0b01] + dp[0b10]) % MOD;
            dp = ndp;
        }
        return (int) dp[0];
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution(object):
    def numTilings(self, N):
        MOD = 10**9 + 7
        dp = [1, 0, 0, 0]
        for _ in xrange(N):
            ndp = [0, 0, 0, 0]
            ndp[0b00] = (dp[0b00] + dp[0b11]) % MOD
            ndp[0b01] = (dp[0b00] + dp[0b10]) % MOD
            ndp[0b10] = (dp[0b00] + dp[0b01]) % MOD
            ndp[0b11] = (dp[0b00] + dp[0b01] + dp[0b10]) % MOD
            dp = ndp

        return dp[0]



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

