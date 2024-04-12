879. Profitable Schemes - Hard


There are G people in a gang, and a list of various crimes they could commit.

The i-th crime generates a profit[i] and requires group[i] gang members to participate.

If a gang member participates in one crime, that member can't participate in another crime.

Let's call a profitable scheme any subset of these crimes that generates at least P profit, and the 
total number of gang members participating in that subset of crimes is at most G.

How many schemes can be chosen?  

Since the answer may be very large, return it modulo 10^9 + 7.

 

Example 1:

Input: G = 5, P = 3, group = [2,2], profit = [2,3]
Output: 2
Explanation: 
To make a profit of at least 3, the gang could either commit crimes 0 and 1, or just crime 1.
In total, there are 2 schemes.


Example 2:

Input: G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
Output: 7
Explanation: 
To make a profit of at least 5, the gang could commit any crimes, as long as they commit one.
There are 7 possible schemes: (0), (1), (2), (0,1), (0,2), (1,2), and (0,1,2).
 

Note:

1 <= G <= 100
0 <= P <= 100
1 <= group[i] <= 100
0 <= profit[i] <= 100
1 <= group.length = profit.length <= 100




******************************************************
key:
	- dfs /  DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- time: O(NPG)

	- 


Method:

	dp[i][j] means the count of schemes with i profit and j members.

	The dp equation is simple here:
		dp[i + p][j + g] += dp[i][j]) 		if i + p < P
		dp[P]    [j + g] += dp[i][j]) 		if i + p >= P


	For each pair (p, g) of (profit, group), I update the count in dp.
	We don't care about the profit of the scheme if it is ≥ P, because it surely will be over the
	threshold of profitability required. 
	Similarly, can't have number of people > G

	- cur[p][g], the number of schemes with profitability p and requiring g gang members: except 
	  we'll say (without changing the answer) that all schemes that profit at least P dollars will 
	  instead profit exactly P dollars.

Algorithm

	- Keeping track of cur[p][g] as defined above, let's understand how it changes as we consider 1 
	   extra crime that will profit p0 and require g0 gang members. 
	   We will put the updated counts into cur2.

	- For each possible scheme with profit p1 and group size g1, that scheme plus the extra 
	  crime (p0, g0)  being considered, has a profit of p2 = min(p1 + p0, P), and uses a group size 
	  of g2 = g1 + g0.

class Solution {
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int MOD = 1_000_000_007;
        int N = group.length;
        long[][][] dp = new long[2][P+1][G+1];
        dp[0][0][0] = 1;

        for (int i = 0; i < N; ++i) {
            int p0 = profit[i];  // the current crime profit
            int g0 = group[i];   // the current crime group size

            long[][] cur = dp[i % 2];
            long[][] cur2 = dp[(i + 1) % 2];

            // Deep copy cur into cur2
            for (int jp = 0; jp <= P; ++jp)
                for (int jg = 0; jg <= G; ++jg)
                    cur2[jp][jg] = cur[jp][jg];

            for (int p1 = 0; p1 <= P; ++p1) {  
                // p1 : the 2nd profit
                // p2 : the new profit after committing this crime
                int p2 = Math.min(p1 + p0, P);

                for (int g1 = 0; g1 <= G - g0; ++g1) {  
                	// g1 : the current group size
                    // g2 : the new group size after committing this crime

                    int g2 = g1 + g0;
                    cur2[p2][g2] += cur[p1][g1];
                    cur2[p2][g2] %= MOD;
                }
            }
        }

        // Sum all schemes with profit P and group size 0 <= g <= G.
        long ans = 0;
        for (long x: dp[N%2][P])
            ans += x;

        return (int) ans;
    }
}


 	public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int[][] dp = new int[P + 1][G + 1];
        dp[0][0] = 1;
        int res = 0, 
            mod = (int)1e9 + 7;

        for (int k = 0; k < group.length; k++) {
            int g = group[k], 
                p = profit[k];

            for (int i = P; i >= 0; i--)
                for (int j = G - g; j >= 0; j--)
                    dp[Math.min(i + p, P)][j + g] = (dp[Math.min(i + p, P)][j + g] + dp[i][j]) % mod;
        }

        for (int x : dp[P]) 
        	res = (res + x) % mod;

        return res;
    }


Example 1, P = 3, G = 5, groups = {2,2}, profits = {2,3}
1. after (g = 2, p = 2)

  G 0 1 2 3 4 5
P
0   1 0 0 0 0 0
1   0 0 0 0 0 0
2   0 0 1 0 0 0
3   0 0 0 0 0 0

2. after (g = 2, p =3)

  G 0 1 2 3 4 5
P
0   1 0 0 0 0 0
1   0 0 0 0 0 0
2   0 0 1 0 0 0
3   0 0 1 0 1 0  

Sum up the last row, the result is 2



Example 2: G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
  G 0 1 2 3 4 5 6 7 8 9 10
P
0   1 0 0 0 0 0 0 0 0 0  0
1   0 0 0 0 0 0 0 0 0 0  0
2   0 0 0 0 0 0 0 0 0 0  0
3   0 0 0 0 0 0 0 0 0 0  0
4   0 0 0 0 0 0 0 0 0 0  0
5   0 0 0 0 0 0 0 0 0 0  0

[1] after (g=2, p=6)

  G 0 1 2 3 4 5 6 7 8 9 10
P
0   1 0 0 0 0 0 0 0 0 0  0
1   0 0 0 0 0 0 0 0 0 0  0
2   0 0 0 0 0 0 0 0 0 0  0
3   0 0 0 0 0 0 0 0 0 0  0
4   0 0 0 0 0 0 0 0 0 0  0
5   0 0 1 0 0 0 0 0 0 0  0

[2] after (g=3, p=7)

  G 0 1 2 3 4 5 6 7 8 9 10
P
0   1 0 0 0 0 0 0 0 0 0  0
1   0 0 0 0 0 0 0 0 0 0  0
2   0 0 0 0 0 0 0 0 0 0  0
3   0 0 0 0 0 0 0 0 0 0  0
4   0 0 0 0 0 0 0 0 0 0  0
5   0 0 1 1 0 1 0 0 0 0  0

[3] after (g=5, p=8)

  G 0 1 2 3 4 5 6 7 8 9 10
P
0   1 0 0 0 0 0 0 0 0 0  0
1   0 0 0 0 0 0 0 0 0 0  0
2   0 0 0 0 0 0 0 0 0 0  0
3   0 0 0 0 0 0 0 0 0 0  0
4   0 0 0 0 0 0 0 0 0 0  0
5   0 0 1 1 0 2 0 1 1 0  1

Sum up the last row, the result is 7

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DFS

Stats:

	- 
	- 


Method:

	-	For each subtree, when P <= 0, all results are the same.
	-	




class Solution {
  static final int MOD = (int)1e9 + 7;
  public int profitableSchemes(int G, int P, int[] group, int[] profit) {
    int len = group.length;
    Integer[][][] memo = new Integer[len][G + 1][P + 1];
    return dfs(0, G, P, group, profit, memo);
  }
  
  private int dfs(int idx, int G, int P, int[] group, int[] profit, Integer[][][] memo) {
    if (idx == group.length) 
    	return 0;
    int actP = Math.max(P, 0);
    if (memo[idx][G][actP] != null) 
    	return memo[idx][G][actP];
    int res = 0;
    if (G >= group[idx]) {
      if (P - profit[idx] <= 0) 
      	res++;

      res += dfs(idx + 1, G - group[idx], P - profit[idx], group, profit, memo);
      res %= MOD;
    }
    res += dfs(idx + 1, G, P, group, profit, memo);
    res %= MOD;
    memo[idx][G][actP] = res;
    return res;
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

