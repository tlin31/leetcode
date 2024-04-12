1335. Minimum Difficulty of a Job Schedule - Hard


You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the i-th job, 
you have to finish all the jobs j where 0 <= j < i).

You have to finish at least one task every day. The difficulty of a job schedule is the sum of 
difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a 
job done in that day.

Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is 
jobDifficulty[i].

Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.

 

Example 1:


Input: jobDifficulty = [6,5,4,3,2,1], d = 2
Output: 7
Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
Second day you can finish the last job, total difficulty = 1.
The difficulty of the schedule = 6 + 1 = 7 


Example 2:

Input: jobDifficulty = [9,9,9], d = 4
Output: -1
Explanation: If you finish a job per day you will still have a free day. you cannot find a 
schedule for the given jobs.


Example 3:

Input: jobDifficulty = [1,1,1], d = 3
Output: 3
Explanation: The schedule is one job per day. total difficulty will be 3.


Example 4:

Input: jobDifficulty = [7,1,7,1,7,1], d = 3
Output: 15

Example 5:

Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
Output: 843
 

Constraints:

1 <= jobDifficulty.length <= 300
0 <= jobDifficulty[i] <= 1000
1 <= d <= 10


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: bottom up DP


Stats:

	- Time Complexity(n^2*d), n = # of jobs
	- Space Complexity(n*d)



Method:


	-  F(i, j) means the minimum difficulty on i-th day who takes j-th work as its end.

	- On i-th day, the minimum index of work we can take is i, because there should be at least 
	  one job done on each previous day, when we reaches i-th day, there should be at least i-1 
	  works finished in total.
	- So if we let k to be the start work, the range of k should be [i, j]. 
	  each (k, j) pair means: at i-th day, we start from k-th work and ends at j-th work. 
	  The difficulty will depend on the one with highest difficulty within the range. 
	  And we choose the k who creates minimum difficulty.

	- At last, in order to start at k-th work on i-th day, we should finish (k-1)-th work at (i-1)-th 
	  day. Then we figure out the functional equation

			F(i, j) = MIN {F(i - 1, k - 1) + MAX_DIFFICULTY(k, j) }, for k from i to j


public int minDifficulty(int[] jobDifficulty, int d) {
    int n = jobDifficulty.length;
    if (d > n) return -1;
    int[][] F = new int[d+1][n+1];

    // base case, when need to finish i task in one day, the difficulty = max(prev max, today)
    for (int i = 1; i <= n; i++) 
    	F[1][i] = Math.max(F[1][i-1], jobDifficulty[i-1]);

    for (int i = 2; i <= d; i++) {
        for (int j = i; j <= n; j++) {
            F[i][j] = Integer.MAX_VALUE;
            int currMax = 0;
            for (int k = j; k >= i; k--) {
                currMax = Math.max(currMax, jobDifficulty[k-1]);
                F[i][j] = Math.min(F[i][j], F[i-1][k-1] + currMax);
            }
        }
    }
    return F[d][n];
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

class Solution {
    public int minDifficulty(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        if (n < d) 
        	return -1;
        int[][] cache = new int[n][d+1];
        for (int i = 0; i < n; i++) 
        	Arrays.fill(cache[i], -1);
        return dfs(jobDifficulty, n, 0, d, cache);
    }

    private int dfs(int[] arr, int n, int i, int d, int[][] cache) {
        if (cache[i][d] != -1) 
        	return cache[i][d];

        // last day, need to get all the rest jobs
        if (d == 1) {
            int max = 0;
            while (i < n) 
            	max = Math.max(max, arr[i++]);
            return max;
        }
        int res = Integer.MAX_VALUE, maxDifficulty = 0;
        for (int j = i; j < n - d + 1; j++) {
            maxDifficulty = Math.max(arr[j], maxDifficulty);
            res = Math.min(res, maxDifficulty + dfs(arr, n, j + 1, d - 1, cache));
        }
        return cache[i][d] = res;
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

