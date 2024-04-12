813. Largest Sum of Averages - Medium

We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the 
sum of the average of each group. What is the largest score we can achieve?

Note that our partition must use every number in A, and that scores are not necessarily integers.

Example:
Input: 
A = [9,1,2,3,9]
K = 3
Output: 20
Explanation: 
The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
We could have also partitioned A into [9, 1], [2], [3, 9], for example.
That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.
 

Note:

1 <= A.length <= 100.
1 <= A[i] <= 10000.
1 <= K <= A.length.
Answers within 10^-6 of the correct answer will be accepted as correct.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- O(KN^2)
	- 


Method:

	-  dp[k][i] = largest avg sum of partitioning first i elements into k groups.

	Init
		dp[1][i] = sum(a[0] ~ a[i – 1]) / i, for i in 1, 2, … , n.

	Transition
		dp[k][i] = max(dp[k – 1][j] + sum(a[j] ~ a[i – 1]) / (i – j)) for j in k – 1,…,i-1.

		This is to find the best j from k-1 to i-1 to max dp[k][i] 
		 = largest sum of partitioning first j elements --> (a[0] ~ a[j – 1]) into k – 1 groups  
		   + average of a[j] ~ a[i – 1] 				--> (partition a[j] ~ a[i – 1] into 1 group).

	Answer
		dp[K][n]


  double largestSumOfAverages(int[] A, int K) {
    int n = A.size();
    double[][] dp = new double[K + 1][n + 1];
    double[] sums = new double[n + 1];

    for (int i = 1; i <= n; ++i) {
      sums[i] = sums[i - 1] + A[i - 1];
      dp[1][i] = (double)(sums[i]) / i;
    }

    for (int k = 2; k <= K; ++k)
      for (int i = k; i <= n; ++i)

      	// find the midpoint
        for (int j = k - 1; j < i; ++j)
          dp[k][i] = max(dp[k][i], dp[k - 1][j] + (sums[i] - sums[j]) / (i - j));
    
    return dp[K][n];
  }


ex. (9,1,2,3,9), k = 3

sum = (9, 10, 12, 15, 24)

[0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
[0.0, 9.0, 5.0, 4.0, 3.75, 4.8]
[0.0, 0.0, 10.0, 10.5, 11.0, 12.75]
[0.0, 0.0, 0.0, 12.0, 13.5, 20.0]

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	

Recursive Approach - Find recurrence equation
class Solution {
    public double largestSumOfAverages(int[] A, int K) {
        int[] sum = new int[A.length];

        // prefix sum
        for (int i = 0; i < A.length; i++) 
        	sum[i] = A[i] + (i > 0 ? sum[i-1] : 0);

        return h(A, K, sum, A.length, 0);
    }
    
    // original array, k groups, prefix sum, length, start index
    public double h(int[] A, int k, int[] sum, int len, int s) {
        if (k == 1) 
        	return ((double)(sum[len-1] - sum[s] + A[s]) / (len-s));

        double num = 0;
        for (int i = s; i + k <= len ; i++) {
            num = Math.max(num, ((double) (sum[i] - sum[s] + A[s]) / (i - s + 1)) + 
            					 h(A, k-1, sum, len, i+1));
        }
        return num;
    }
}

----------

Bottom-UP DP approach O(N ^ 3) Run Time and O(N ^ 2) space - This is crucial step. Recognise base case for Approach-2 and make sure you evolve from that base case to your result.
class Solution {
    public double largestSumOfAverages(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) sum[i] = A[i] + (i > 0 ? sum[i-1] : 0); 
        double[][] dp = new double[A.length][K+1];
        
        for (int groups = 1; groups <= K; groups++) {
            for (int s = 0; s + groups <= A.length; s++) {
                if (groups == 1) {
                    dp[s][groups] = ((double)(sum[A.length-1] - sum[s] + A[s]) / (A.length-s));
                    continue;
                }
                for (int e = s; e + groups <= A.length; e++) {
                    dp[s][groups] = Math.max(dp[s][groups], (dp[e+1][groups-1] + (double) (sum[e] - sum[s] + A[s]) / (e - s + 1)));
                }
            }
        }
        return dp[0][K];
    }
}

----------------
Bottom-UP DP approach O(N ^ 3) Run Time and O(N) space - Check if can reduce Space complexity.
class Solution {
    public double largestSumOfAverages(int[] A, int K) {
        int[] sum = new int[A.length];
        for (int i = 0;i < A.length; i++) sum[i] = A[i] + (i > 0 ? sum[i-1] : 0); 
        double[] dp = new double[A.length];
        
        for (int groups = 1; groups <= K; groups++) {
            for (int s = 0; s + groups <= A.length; s++) {
                if (groups == 1) {
                    dp[s] = ((double)(sum[A.length-1] - sum[s] + A[s]) / (A.length-s));
                    continue;
                }
                for (int e = s; e + groups <= A.length; e++) {
                    dp[s] = Math.max(dp[s], (dp[e+1] + (double) (sum[e] - sum[s] + A[s]) / (e - s + 1)));
                }
            }
        }
        return dp[0];
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


