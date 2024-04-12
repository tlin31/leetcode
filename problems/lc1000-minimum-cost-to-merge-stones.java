1000. Minimum Cost to Merge Stones - Hard

There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.

A move consists of merging exactly K consecutive piles into one pile, and the cost of this move is 
equal to the total number of stones in these K piles.

Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.

 

Example 1:

Input: stones = [3,2,4,1], K = 2
Output: 20
Explanation: 
We start with [3, 2, 4, 1].
We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
We merge [4, 1] for a cost of 5, and we are left with [5, 5].
We merge [5, 5] for a cost of 10, and we are left with [10].
The total cost was 20, and this is the minimum possible.
Example 2:

Input: stones = [3,2,4,1], K = 3
Output: -1
Explanation: After any merge operation, there are 2 piles left, and we cant merge anymore.  
So the task is impossible.

Example 3:

Input: stones = [3,5,1,2,6], K = 3
Output: 25
Explanation: 
We start with [3, 5, 1, 2, 6].
We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
We merge [3, 8, 6] for a cost of 17, and we are left with [17].
The total cost was 25, and this is the minimum possible.
 

Note:

1 <= stones.length <= 30
2 <= K <= 30
1 <= stones[i] <= 100


******************************************************
key:
	- parenthesis / chain multiplication problem
	- edge case:
		1) empty string, return empty
		2)

******************************************************
=======================================================================================================
method 1:

Stats:

    - Time Complexity: O(n^3 * K) (n is the number of stone)
    - 


Method:
  
    -  Simplified problem: if we can only merge 2 adjacent piles into one pile?
        --> becomes which two piles should we merge at the last step?

        We dont know which two piles to merge for now, but we can know the cost of that step, which 
        is the sum of that two piles.
            [3 | 2, 4, 1]
            3 + 7 = 10

            [3 , 2 | 4, 1]
            5 + 5 = 10

            [3 , 2, 4 | 1]
            9 + 1 = 10

        No matter how to split the two piles, the sum is always the sum of the two piles, so the
        only thing that matters is how to get the minimum cost to split to two piles.

    -  we need to know the minimum cost of merging left part to 1 pile, and minimum cost of merging 
       right part to 1 pile:

            dp[i][j] = minimum cost merging piles from i to j to 1 pile.

            Function: dp[i][j] = min(sum[i][j] + dp[i][k] + dp[k + 1][j]) (i <= k < j)

            Base case: dp[i][i] = 0 (Already a pile)

            Answer: dp[1][len] (len is the stones number)


    public int mergeStonesTwo(int[] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        int len = stones.length;
        int max = Integer.MAX_VALUE;
        int[][] dp = new int[len + 1][len + 1];
        int[] prefixSum = new int[len + 1];
        int i, j, k, l;

        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }
        
        for (i = 1; i <= len; i++) {
            dp[i][i] = 0;
        }
        
        // l = length, i = start index, j = end index
        for (l = 2; l <= len; l++) {
            for (i = 1; i <= len - l + 1; i++) {
                j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;
                int sum = prefixSum[j] - prefixSum[i - 1];
                for (k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + sum);
                }
            }
        }
        
        return dp[1][len];
    }

-----------------------
This problem: if we can merge k consecutive piles? 

    -  we need to know the minimum cost of merging left part to k - 1 piles and right part to 1 pile.
       Our state has one more information to know, how many piles. So we add the field to our dp array.

        dp[i][j][m] = Minimum cost merging piles from i to j to k pile.

    Function:
        if (dp[i][j][K] != max)
            dp[i][j][1] = dp[i][j][K] + sum[i][j] 

        for (mid in the range of [i, j) && dp[i][mid][k - 1] != max && dp[mid+1][j][1] != max 
                && k ∈ [2, K])
            dp[i][j][k] = min(dp[i][mid][k - 1] + dp[mid + 1][j][1]) 

    Base case:    
        dp[i][i][1] = 0 (Already a pile) 
        others = max

    Answer: 
        dp[1][len][1] (len is the stones number)


// bottom-up
class Solution {
    public int mergeStones(int[] stones, int K) {
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }
        
        int i, j, k, l, t;
        
        int[] prefixSum = new int[len + 1];
        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }
        
        int max = Integer.MAX_VALUE;
        int[][][] dp = new int[len + 1][len + 1][K + 1];
        for (i = 1; i <= len; i++) {
            for (j = 1; j <= len; j++) {
                for (k = 1; k <= K; k++) {
                    dp[i][j][k] = max;
                }
            }
        }
        
        for (i = 1; i <= len; i++) {
            dp[i][i][1] = 0;
        }

        for (l = 2; l <= len; l++) {
            for (i = 1; i <= len - l + 1; i++) {
                j = i + l - 1;

                // k = sequence of the length for this
                for (k = 2; k <= K; k++) {
                    for (t = i; t < j; t++) {

                        // if not yet processed, skip the cycle
                        if (dp[i][t][k - 1] == max || dp[t + 1][j][1] == max) {
                            continue;
                        }
                        dp[i][j][k] = Math.min(dp[i][j][k], dp[i][t][k - 1] + dp[t + 1][j][1]);
                    }
                }

                if (dp[i][j][K] == max) {
                    continue;
                }

                dp[i][j][1] = dp[i][j][K] + prefixSum[j] - prefixSum[i - 1];   
            }
        }

        return dp[1][len][1];
    }
}



// Top-down
class Solution {
    int[][][] dp;
    int max = Integer.MAX_VALUE;
    int K;
    
    public int mergeStones(int[] stones, int K) {
        this.K = K;
        int len = stones.length;
        if ((len - 1) % (K - 1) != 0) {
            return -1;
        }
        dp = new int[len + 1][len + 1][K + 1];
        int[] prefixSum = new int[len + 1];
        
        int i;
        for (i = 1; i <= len; i++) {
            prefixSum[i] = prefixSum[i - 1] + stones[i - 1];
        }
        
        getResult(prefixSum, 1, len, 1);
        return dp[1][len][1];
    }
    
    private int getResult(int[] prefixSum, int left, int right, int piles) {
        if (dp[left][right][piles] != 0) {
            return dp[left][right][piles];
        }
        int res = max;
        int t;
        if (left == right) {
            res = (piles == 1) ? 0 : max;
        }
        else {
            if (piles == 1) {
                int mergeK = getResult(prefixSum, left, right, K);
                if (mergeK != max) {
                    res = mergeK + prefixSum[right] - prefixSum[left - 1]; 
                }
            }
            else {
                for (t = left; t < right; t++) {
                    int l = getResult(prefixSum, left, t, piles - 1);
                    int r = getResult(prefixSum, t + 1, right, 1);
                    if (l != max && r != max) {
                        res = Math.min(res, l + r);
                    }
                }
            }
        }
        dp[left][right][piles] = res;
        return res;
    }
}

=======================================================================================================
Method 2:


Stats:

	- Time O(N^3/K), Space O(KN^2)

	- 


Method:

	-  dp[i][j][m]: the cost needed to merge stone[i] ~ stones[j] into m piles.

	-  Base case:
			dp[i][i][1] = 0,
			dp[i][i][m] = infinity

	   DP:
			dp[i][j][1] = dp[i][j][k] + stonesNumber[i][j]
			dp[i][j][m] = min(dp[i][mid][1] + dp[mid + 1][j][m - 1])



Q: Why mid jump at step K - 1
A: We can merge K piles into one pile,
we can not merge K + 1 piles into one pile.
We can merge K + K - 1 piles into one pile,
We can merge K + (K - 1) * steps piles into one pile.


public class Solution {
    Integer[][][] memo;
    int[] preSum;

    public int mergeStones(int[] stones, int K) {
        if (stones == null || stones.length == 0) 
        	return -1;

        int n = stones.length;
        if (n == 1) return 0;
        preSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + stones[i];
        }

        if (n == K) {
            return preSum[n];
        }

        if ((n - 1) % (K - 1) != 0) return -1;

        memo = new Integer[n][n][K + 1];

        for (int i = 0; i < n; i++) {
            memo[i][i][1] = 0;
        }

        return calculate(stones, 0, n - 1, 1, K);
    }

    // piles = current number of pile
    private int calculate(int[] stones, int i, int j, int piles, int K) {
        if ((j - i + 1 - piles) % (K - 1) != 0) {
            return Integer.MAX_VALUE;
        }
        if (memo[i][j][piles] != null) {
            return memo[i][j][piles];
        }
        int cost = Integer.MAX_VALUE;

        if (piles == 1) {
            int prev = calculate(stones, i, j, K, K);
            if (prev != Integer.MAX_VALUE) {
                cost = prev + preSum[j + 1] - preSum[i];
            }
        } else {
            for (int mid = i; mid < j; mid++) {
                int left = calculate(stones, i, mid, 1, K);
                if (left >= cost) 
                	continue;

                int right = calculate(stones, mid + 1, j, piles - 1, K);
                if (right >= cost) 
                	continue;

                cost = Math.min(cost, left + right);
            }
        }
        memo[i][j][piles] = cost;
        return cost;
    }
}


=======================================================================================================
method 3:

Stats:

	- Time O(N^3/K) Space O(N^2)

	- 


Method:

	-	
	-	2D DP
		stones[i] ~ stones[j] will merge as much as possible.

		Finally (j - i) % (K - 1) + 1 piles will be left.

		It is less than K piles and no more merger happens.

		dp[i][j] means the minimum cost needed to merge stones[i] ~ stones[j].

    public int mergeStones(int[] stones, int K) {
        int n = stones.length;
        if ((n - 1) % (K - 1) > 0) return -1;

        int[] prefix = new int[n+1];
        for (int i = 0; i <  n; i++)
            prefix[i + 1] = prefix[i] + stones[i];

        int[][] dp = new int[n][n];
        for (int m = K; m <= n; ++m)
            for (int i = 0; i + m <= n; ++i) {
                int j = i + m - 1;
                dp[i][j] = Integer.MAX_VALUE;
                for (int mid = i; mid < j; mid += K - 1)
                    dp[i][j] = Math.min(dp[i][j], dp[i][mid] + dp[mid + 1][j]);
                if ((j - i) % (K - 1) == 0)
                    dp[i][j] += prefix[j + 1] - prefix[i];
            }
        return dp[0][n - 1];
    }






