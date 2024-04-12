1246. Palindrome Removal - Hard



Given an integer array arr, in one move you can select a palindromic subarray arr[i], arr[i+1], 
..., arr[j] where i <= j, and remove that subarray from the given array. Note that after 
removing a subarray, the elements on the left and on the right of that subarray move to fill 
the gap left by the removal.

Return the minimum number of moves needed to remove all numbers from the array.

 

Example 1:

Input: arr = [1,2]
Output: 2

Example 2:

Input: arr = [1,3,4,1,5]
Output: 3
Explanation: Remove [4] then remove [1,3,1] then remove [5].

Constraints:

1 <= arr.length <= 100
1 <= arr[i] <= 20


******************************************************
key:
	- DP
	- edge case:
		1) empty array, return 0
		2) array with 1 elem, return 1

******************************************************



=======================================================================================================
Method 1: Bottom up DP


Stats:

	- O(n^3)
	- 


Method:

	- A[i] can be removed alone or it makes a pair.
	- dp[i][j] = the number of steps it takes to delete the substring s[i, j]. 

	  case 1:
	  	we will delete the character itself and call subproblem (i+1, j). 
	  	--> 1 + dp[i+1][j]

	  case 2:
	  	we will iterate over all occurrence of the current character btw i & j 
	  	but need to have s[i] == s[k] --> will reduce to two subproblems:(i+1, K – 1) and (K+1, j). 
	  	
	  	We can reach to this subproblem (i+1, K-1) because we can just delete the same character 
	  	and call for mid substring. 

	  	--> dp[i+1][K-1] + dp[K+1][j], for s[i] == s[k]

	  case 3:
	  	first two characters are same --> directly reduce to the subproblem (i+2, j)


	- 	dp[i][j] = min(1 + dp[i+1][j],dp[i+1][K-1] + dp[K+1][j], 1 + dp[i+2][j]) where s[i] == s[K]

    

    public int minimumMoves(int[] A) {
        int N = A.length;

        //  declare dp array and initialize it with 0s
        int[][] dp = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++)
            for (int j = 0; j <= N; j++)
                dp[i][j] = 0;

        // loop for subarray length we are considering
        for (int len = 1; len <= N; len++) {

            // loop with two variables i and j, denoting starting and ending of subarray
            // (i,j) = (0,0), (1,1) (2,2) (N-1,N-1)
            //         (0,1), (1,2) (2,3)...(N-2, N-1)
            //         (0,5), (1,6)....(N-6, N-1)
            for (int i = 0, j = len - 1; j < N; i++, j++) {

                // If subarray length is 1, then 1 step will be needed
                if (len == 1)
                    dp[i][j] = 1;

                else {

                    // case 1: delete char A[i] and assign result for subproblem (i+1,j)
                    dp[i][j] = 1 + dp[i + 1][j];

                    // case 2: loop over all right elements 
                    // choose min from current and two subarray after ignoring ith and A[K]
                    for (int K = i + 2; K <= j; K++)
                        if (A[i] == A[K])
                            dp[i][j] = Math.min(dp[i + 1][K - 1] + dp[K + 1][j], dp[i][j]);


                    // case 3: if current and next element are same, choose min from current and 
                    // subproblem (i+2,j)
                    if (A[i] == A[i + 1])
                        dp[i][j] = Math.min(1 + dp[i + 2][j], dp[i][j]);
                }
            }
        }
        return dp[0][N - 1];
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DFS

Stats:

	- 
	- 


Method:

	-	
	-	



    int[][] memo;
    public int minimumMoves(int[] A) {
        int n = A.length;
        memo = new int[n][n];
        return dfs(0, n - 1, A);
    }
    int dfs(int i, int j, int[] A) {
        if (i > j) return 0;
        if (memo[i][j] > 0) 
        	return memo[i][j];

        int res = dfs(i, j - 1, A) + 1;

        if (j > 0 && A[j] == A[j - 1])
            res = Math.min(res, dfs(i, j - 2, A) + 1);

        for (int k = i; k < j - 1; ++k)
            if (A[k] == A[j])
                res = Math.min(res, dfs(i, k - 1, A) + dfs(k + 1, j - 1, A));

        memo[i][j] = res;
        return res;
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

