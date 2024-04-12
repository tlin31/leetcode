935. Knight Dialer - Medium

https://leetcode.com/problems/knight-dialer/

A chess knight can move as indicated in the chess diagram below:


This time, we place our chess knight on ANY numbered key of a phone pad (indicated above), and the 
knight makes N-1 hops.  Each hop must be from one key to another numbered key.

Each time it lands on a key (including the initial placement of the knight), it presses the number of 
that key, pressing N digits total.

How many distinct numbers can you dial in this manner?

Since the answer may be large, output the answer modulo 10^9 + 7.

 

Example 1:
Input: 1
Output: 10

Example 2:
Input: 2
Output: 20


Example 3:
Input: 3
Output: 46
 

Note:

1 <= N <= 5000




******************************************************
key:
	- backtrack or DP?
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DFS / backtrack


Stats:

	- 
	- 


Method:

	-	Same as finding total number of unique paths the knight can travel making n hops because to 
	    dial distinct numbers, the path taken by the knight must be unique.

[0] 
	If k is at index (i, j), then in a single hop, k can move to 8 possible positions:

		a (i - 1, j - 2)
		b (i - 2, j - 1)
		c (i - 2, j + 1)
		d (i - 1, j + 2)
		e (i + 1, j + 2)
		f (i + 2, j + 1)
		g (i + 2, j - 1)
		h (i + 1, j - 2)


[1] 
	Conversely, you can also say that in a single hop, there are 8 possible places (a,b,c,d,e,f,g,h) 
	from which you can move to k.

	Consider a function paths(i, j, n) which calculates the total number of unique paths to reach 
	index (i, j) for a given n, where n is the number of hops. From [0] or [1], we can recusively 
	define paths(i, j, n) for all non-trivial (n > 1, that is, more than one hop) cases as follows,

		paths(i, j, n) = paths(i - 1, j - 2, n - 1) + 
		                 paths (i - 2, j - 1, n - 1) +
		                 paths (i - 2, j + 1, n - 1) +
		                 paths (i - 1, j + 2, n - 1) +
		                 paths (i + 1, j + 2, n - 1) +
		                 paths  (i + 2, j + 1, n - 1) +
		                 paths (i + 2, j - 1, n - 1) +
		                 paths (i + 1, j - 2, n - 1)

	= total number of unique paths to (i, j) for certain hops n is equal to the sum of total number 
	   of unique paths to each valid position from which (i, j) can be reached using n - 1 hops

	Base case:
		for n = 1: paths(i, j, n) = 1


ex. 
	Our keypad is a 4 x 3 matrix which looks like below.

	1   2   3
	4   5   6
	7   8   9
	*   0   *


	We shall trace the recursion tree of paths(0, 0, 3) in this section --> all the possible unique 
	paths from 1 (0, 0) in 3 hops. From 1 (0, 0), in a single hop, we have two possible places to 
	jump to - 6 and 8.

	

                              	1 (0,0,3)
			               /		     	\
				          /              	 \
				  	  6 (1,2,2)             8 (2,1,2)	
				   	/   |    \                |   	\
				   /	|	  \		          |  	 \
		1 (0,0,1)  0 (3,1,1)  7 (2,0,1)   1 (0,0,1)  3 (0,2,1)


	Since, 6 jumps are invalid, that leaves us with only two valid jumps e (i + 1, j + 2) and 
	f (i + 2, j + 1) from 1 (0, 0). In other words, from 1 the knight can jump only to 8 and 6.

DP:

	save subproblems in M[][][], a 3D array and each index of M corresponds to a solution of n. 
	Each n is again stored as a 2D array for (i, j) values.

	All this combined, M will store the solution of each paths(i, j, n) call.


class Solution {
    
    public static final int max = 1000000007;
	private static final int[][] dirs = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2},
	                                     {-2, -1}};

    Map<String,Long> pathCount = new HashMap<>();
    
    public int knightDialer(int N) {
        long res = 0;

        // i & j are coordinates
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                res += helper(i,j,N))%max;
            }
        }
        return (int) res;
    }
    
    public long helper(int i,int j,int n){
        
        // need to be within range
        if(i < 0 || i >=4 || j<0 || j >=3 || (i == 3 && j!=1))
            return 0;

        if(n == 1)
            return 1;

        String key = i+"_"+j+"_"+n;

        if(pathCount.containsKey(key))
            return pathCount.get(key);

        long ans = 0;

        for(int k=0;k<dirs.length;k++){
            ans += helper(i+dirs[k][0],j+dirs[k][1],n-1)%max;
        }

        pathCount.put(key,ans);
        return ans;
    }
}
=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	- moves show the possible hoops from a given number. 
	  Each digit in dial[i] contains numbers that end with this digit. 
	  For each hoop, we add numbers from other digits that we can hop out from. 
	  For example, for digit 4 we add numbers from digits 3, 9 and 0:

		1 | 1 | 1    2 | 2 | 2    5 | 4 | 5
		1 | 1 | 1 -> 3 | 0 | 3 -> 6 | 0 | 6
		1 | 1 | 1    2 | 2 | 2    5 | 4 | 5
		  | 1 |        | 2 |        | 6 |


class Solution {
    public int knightDialer(int N) {
        int[][] dirs = {{4,6},{6,8},{7,9},{4,8},{0,3,9},{},{0,1,7},{2,6},{1,3},{2,4}};
        int[] dp = new int[10];
        Arrays.fill(dp,1);
        int[] dp1 = new int[10];
        int MOD = (int)1e9+7;
        
        while(--N>0){
            for(int i=0; i<10; i++){
                for(int d:dirs[i]){
                    dp1[i]=(dp[d]+dp1[i])%MOD;
                }
            }
            dp=dp1;
            dp1=new int[10];
        }
        
        int sum=0;
        for(int i=0; i<dp.length; i++){
            sum=(sum+dp[i])%MOD;
        }
        
        return sum;
        
    }
}
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	


