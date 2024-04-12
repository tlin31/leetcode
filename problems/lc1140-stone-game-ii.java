1140. Stone Game II - Medium

Alex and Lee continue their games with piles of stones.  There are a number of piles arranged 
in a row, and each pile has a positive integer number of stones piles[i].  The objective of 
the game is to end with the most stones. 

Alex and Lee take turns, with Alex starting first.  Initially, M = 1.

On each players turn, that player can take all the stones in the first X remaining piles, 
where 1 <= X <= 2M.  Then, we set M = max(M, X).

The game continues until all the stones have been taken.

Assuming Alex and Lee play optimally, return the maximum number of stones Alex can get.

 

Example 1:

Input: piles = [2,7,9,4,4]
Output: 10
Explanation:  If Alex takes one pile at the beginning, Lee takes two piles, then Alex takes 2 piles 
again. Alex can get 2 + 4 + 4 = 10 piles in total. 

If Alex takes two piles at the beginning, then Lee can take all three piles left. In this case, 
Alex get 2 + 7 = 9 piles in total. So we return 10 since its larger. 
 

Constraints:

1 <= piles.length <= 100
1 <= piles[i] <= 10 ^ 4


******************************************************
key:
	- DP, BFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	Let DP[i][m] be the maximal number of stones a player can get when the 0, 1, ..., i-1 
	    piles have already been taken and the current M is m. 

	    Lets assume the total number of stones in piles i, i+1, ..., n-1 is Si.

	-	If the player takes only the first pile (piles[i], x = 1), the other player can get up to 
	       	DP[i+1][max(m, 1)]. So the current player can get Si - DP[i+1][max(m, 1)].

		If the player takes the first two piles (piles[i], piles[i+1], x = 2), the other player can
			get up to DP[i+2][max(m, 2)]. So the current player can get Si - DP[i+2][max(m, 2)].
		...

		If the player takes the first 2m piles (piles[i], piles[i+1], ..., piles[i+2m], x = 2m), the 
		other player can get up to DP[i+2m][max(m, 2m)]. So the current player can get 
		Si - DP[i+2m][max(m, 2m)].
	-	

Stats:

	- 
	- 



Notes:

DP[i][m] can be calcuated from DP[i+x][max(m,x)]. We can use the results from larger index and no 
smaller m to calculate it. 

We can calculate the cumulative sum of the stones from the last pile backward to the first so that 
we can easily get the total number of stones in piles i, i+1, ..., n-1.

There is a small optimization to use a smaller m to cover some larger ones. If there are only n-i 
piles left and the m is (n-i+1)/2 or larger, the current player can just take all of the left piles.


public int stoneGameII(int[] piles) {

	final int n = piles.length;

	// accumulative piles
	for (int i = n - 2; i >= 0; i--) 
		piles[i] += piles[i+1];

	if (n <= 2) 
		return piles[0];
	
	int[][] dp = new int[n][(n+1)/2+1];
	for (int i = n-1; i >= 0; i--) {
	    int sum = piles[i];
	    int m = (n-i+1)/2;
	    dp[i][m] = sum;
	    while (--m > 0) {
	        dp[i][m] = 0;
	        for (int x = 1; x <= m * 2 && i+x < n; x++) {
	            int mx = Math.min((n-i-x+1)/2, Math.max(x, m));
	            dp[i][m] = Math.max(dp[i][m], sum - dp[i+x][mx]);
	        }
	    }
	}
	return dp[0][1];
}

=======================================================================================================
method 2:

Method:

	-	backtrack/dfs
	-	


Stats:

	- 
	- 

class Solution {
    public int stoneGameII(int[] piles) {
        // generate prefix sum
        for(int i=1;i<piles.length;i++)
            piles[i]+=piles[i-1];
        HashMap<String,Integer> cache=new HashMap<>();
        return dfs(1,0,piles,cache);
    }
    int dfs(int M, int i, int[] piles,HashMap<String,Integer> cache){
        if(piles.length==i) 
        	return 0;
        if(cache.containsKey(M+" "+i)) 
        	return cache.get(M+" "+i);

        int max=-1;
        int preSum= i-1<0?0:piles[i-1];

        // add this if clause, if currently player can pick all remaining stones, simply do so
        if (i+2*M >= piles.length) 
        	return piles[piles.length-1]-preSum;

        for(int k=1;k<=2*M;k++){
            int opponent = dfs(Math.max(k,M),i+k,piles,cache);
            int curr = piles[piles.length-1]-preSum-opponent;
            max = Math.max(max, curr);
        }

        cache.put(M+" "+i, max);
        return cache.get(M+" "+i);
    }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 

class Solution {
    //private int[] sums;//the sum from piles[i] to the end
    //private int[][] hash;
    public int stoneGameII(int[] piles) {
        if(piles == null || piles.length == 0) return 0;
        int n = piles.length;
        int[] sums = new int[n];
        sums[n-1] = piles[n-1];
        for(int i = n -2; i>=0;i--) {
            sums[i] = sums[i+1] + piles[i]; //the sum from piles[i] to the end
        }
        
        int[][] hash = new int[n][n];
        return helper(piles, 0, 1, sums, hash);
    }
    
    private int helper(int[] a, int i, int M, int[] sums, int[][] hash) {
        if(i == a.length) return 0;
        if(2*M >= a.length - i) {
            return sums[i];
        }
        if(hash[i][M] != 0) return hash[i][M];
        int min = Integer.MAX_VALUE;//the min value the next player can get
        for(int x=1;x<=2*M;x++){
            min = Math.min(min, helper(a, i+x, Math.max(M,x),sums,hash));
        }
        hash[i][M] = sums[i] - min;  //max stones = all the left stones - the min stones next player can get
        return hash[i][M];   
    }
}

