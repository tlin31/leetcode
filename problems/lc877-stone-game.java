877. Stone Game - Medium

Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a 
row, and each pile has a positive integer number of stones piles[i].

The objective of the game is to end with the most stones.  The total number of stones is odd, 
so there are no ties.

Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire pile 
of stones from either the beginning or the end of the row.  This continues until there are no 
more piles left, at which point the person with the most stones wins.

Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.

 

Example 1:

Input: [5,3,4,5]
Output: true
Explanation: 
Alex starts first, and can only take the first 5 or the last 5.
Say he takes the first 5, so that the row becomes [3, 4, 5].
If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
This demonstrated that taking the first 5 was a winning move for Alex, so we return true.
 

Note:

2 <= piles.length <= 500
piles.length is even.
1 <= piles[i] <= 500
sum(piles) is odd.

******************************************************
key:
	- since both players are playing optimally, use minimax
	- 
	- edge case:
		- none since pile length and element in the pile is a given

******************************************************



=======================================================================================================
method 1:

method:

	- Alex is first to pick pile.
		piles.length is even, and this lead to an interesting fact:
		Alex can always pick odd piles or always pick even piles!
		For example,
		If Alex wants to pick even indexed piles piles[0], piles[2], ....., piles[n-2],
		he picks first piles[0], then Lee can pick either piles[1] or piles[n - 1].
		Every turn, Alex can always pick even indexed piles and Lee can only pick odd indexed piles.

		In the description, we know that sum(piles) is odd. so there must be a greater one
		If sum(piles[even]) > sum(piles[odd]), Alex just picks all evens and wins.
		If sum(piles[even]) < sum(piles[odd]), Alex just picks all odds and wins.

		So, Alex always defeats Lee in this game.
	- 

stats:

	- 
	- 

	return true;



=======================================================================================================
method 2:

method:

	- 2D DP
	Follow-up:
	What if piles.length can be odd?
	What if we want to know exactly the diffenerce of score?
	Then we need to solve it with DP.

	dp[i][j] = difference, means the biggest number of stones you can get more than opponent picking 
	piles in piles[i] ~ piles[j]. You can first pick piles[i] or piles[j].

	If you pick piles[i], your result will be piles[i] - dp[i + 1][j]
	If you pick piles[j], your result will be piles[j] - dp[i][j - 1]

	So we get:
	dp[i][j] = max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1])
	where dp[i + 1][j] and dp[i][j - 1] means the opponents choice

	We start from smaller subarray and then we use that to calculate bigger subarray.

stats:

	- Runtime: 6 ms, faster than 45.23% of Java online submissions for Stone Game.
	- Memory Usage: 36.5 MB, less than 90.91% 

public boolean stoneGame(int[] p) {
        int n = p.length;
        // initialize
        int[][] dp  = new int[n][n];
        for (int i = 0; i < n; i++) 
        	dp[i][i] = p[i];

        // d = length of piles, i = start index
        for (int d = 1; d < n; d++)
            for (int i = 0; i < n - d; i++){
            	int j = i + d;
                dp[i][j] = Math.max(p[i] - dp[i + 1][j], p[j] - dp[i][j - 1]);
        	}
        return dp[0][n - 1] > 0;
    }

=======================================================================================================
method 2.5: optimize space from method 2

	- Simplify to 1D DP.
	- d = length
	- Runtime: 3 ms, faster than 57.36% of Java online submissions for Stone Game.
	- Memory Usage: 34.6 MB, less than 100.00% 


 public boolean stoneGame(int[] p) {
        int[] dp = Arrays.copyOf(p, p.length);;
        for (int d = 1; d < p.length; d++)
            for (int i = 0; i < p.length - d; i++)
                dp[i] = Math.max(p[i] - dp[i + 1], p[i + d] - dp[i]);
        return dp[0] > 0;
    }


=======================================================================================================
method 3:

method:

	- minimax solution
	- 

stats:

	- 
	- 


class Solution {
    int [][][] memo;
    public boolean stoneGame(int[] piles) {
        memo = new int[piles.length + 1][piles.length + 1][2];
        for(int [][] arr : memo)
            for(int [] subArr : arr)
                Arrays.fill(subArr, -1);
        
        return (helper(0, piles.length - 1, piles, 1) >= 0);
    }
    
    public int helper(int l, int r, int [] piles, int ID){
        if(r < l)
            return 0;

        // already computed
        if(memo[l][r][ID] != -1)
            return memo[l][r][ID];
        
        int next = Math.abs(ID - 1);

        // first player, Alex
        if(ID == 1)
            memo[l][r][ID] = Math.max(piles[l] + helper(l + 1, r, piles, next), 
            						  piles[r] + helper(l, r - 1, piles, next));
        else
            memo[l][r][ID] = Math.min(-piles[l] + helper(l + 1, r, piles, next), 
            						  -piles[r] + helper(l, r - 1, piles, next));
        
        return memo[l][r][ID];
    }
}









