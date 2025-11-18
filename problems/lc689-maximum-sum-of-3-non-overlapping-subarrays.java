689. Maximum Sum of 3 Non-Overlapping Subarrays - Hard

In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.

Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.

Return the result as a list of indices representing the starting position of each interval (0-indexed). 
If there are multiple answers, return the lexicographically smallest one.

Example:

Input: [1,2,1,2,6,7,5,1], 2
Output: [0, 3, 5]
Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
 

Note:

nums.length will be between 1 and 20000.
nums[i] will be between 1 and 65535.
k will be between 1 and floor(nums.length / 3).

******************************************************
key:
	- DP, buy & sell stock, 从左到右走一遍 & 从右到左走一遍
	- edge case:
		1) empty string, return empty
		2)

******************************************************



1. Calculate Subarray Sums:
First, we compute the sums of all possible subarrays of length k. - 
This can be done efficiently using a sliding window technique.

2. Track Maximum Indices: We maintain two arrays:
maxAtLeft[i]: This array stores the index of the max subarray sum from the left up to index i.
maxAtRight[i]: stores the index of the max subarray sum from the right starting from index i.

3. Find Maximum Sum of Three Subarrays:
We iterate through the possible starting indices for the middle subarray and use the 
maxAtLeft and maxAtRight arrays to find the best combination of three subarrays.
We keep track of the maximum sum and the corresponding indices.


class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        if (nums == null || nums.length < k * 3)
            return new int[] {};

        int numSub = nums.length - k + 1;
        int[] subSum = new int[numSub];

        int sum = 0;

        for (int i = 0; i < k; i++)
            sum += nums[i];

        subSum[0] = sum;

        for (int i = k; i < nums.length; i++) {
            sum -= nums[i - k];
            sum += nums[i];
            subSum[i - k + 1] = sum;
        }

        int[] maxAtLeft = new int[numSub];

        for (int i = 1; i < numSub; i++)
            maxAtLeft[i] = (subSum[i] > subSum[maxAtLeft[i - 1]]) ? i : maxAtLeft[i - 1];

        int[] maxAtRight = new int[numSub];
        maxAtRight[numSub - 1] = numSub - 1;

        for (int i = numSub - 2; i >= 0; i--)
            maxAtRight[i] = (subSum[i] >= subSum[maxAtRight[i + 1]]) ? i : maxAtRight[i + 1];

        int[] maxThree = new int[3];
        int maxSum = 0;
        for (int i = k; i < numSub - k; i++) {
            int curSum = subSum[maxAtLeft[i - k]] + subSum[i] + subSum[maxAtRight[i + k]];
            if (curSum > maxSum) {
                maxSum = curSum;
                maxThree = new int[] { maxAtLeft[i - k], i, maxAtRight[i + k] };
            }
        }
        return maxThree;
    }
}


=======================================================================================================
method 1:

method:
	- DP
	- The question asks for three non-overlapping intervals with maximum sum of all 3 intervals. 
	   	If middle interval is [i, i+k-1], where k <= i <= n-2k, 
	   		the left interval has to be in subrange [0, i-1], 
	   		right interval is from subrange [i+k, n-1].

		posLeft[i] is the starting index for the left interval in range [0, i];
		posRight[i] is the starting index for the right interval in range [i, n-1]; 
		Then we test every possible starting index of middle interval, i.e. k <= i <= n-2k, and we 
        can get the corresponding left and right max sum intervals easily from DP. 

	- !!! In order to get lexicographical smallest order, when there are two intervals with equal 
      max sum, always select the left most one. So in the code, the if condition is ">= tot" for 
      right interval due to backward searching, and "> tot" for left interval. 


	- 

stats:

	- The overall time complexity is (O(n)), where (n) is the length of the input array nums. 
      This is because we make a constant number of passes through the array.

	- 

class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length, maxsum = 0;
        int[] sum = new int[n+1], 
              posLeft = new int[n], 
              posRight = new int[n], 
              ans = new int[3];

        // store cumulated-sum
        for (int i = 0; i < n; i++) 
        	sum[i+1] = sum[i]+nums[i];


        // DP for starting index of the left max sum interval
        int total = sum[k]-sum[0];
        for (int i = k; i < n; i++) {

        	// update
            if (sum[i+1]-sum[i+1-k] > tot) {
                posLeft[i] = i+1-k;
                tot = sum[i+1]-sum[i+1-k];
            }
            else
                posLeft[i] = posLeft[i-1];
        }

        // DP for starting index of the right max sum interval
        // caution: the condition is ">= tot" for right interval, and "> tot" for left interval
        posRight[n-k] = n-k;
        int tot = sum[n]-sum[n-k];
        for (int i = n-k-1; i >= 0; i--) {
            if (sum[i+k]-sum[i] >= tot) {
                posRight[i] = i;
                tot = sum[i+k]-sum[i];
            }
            else
                posRight[i] = posRight[i+1];
        }

        // test all possible middle interval
        for (int i = k; i <= n-2*k; i++) {
            int l = posLeft[i-1], 
                r = posRight[i+k];

            int tot = (sum[i+k]-sum[i]) + (sum[l+k]-sum[l]) + (sum[r+k]-sum[r]);

            if (tot > maxsum) {
                maxsum = tot;
                ans[0] = l; 
                ans[1] = i; 
                ans[2] = r;
            }
        }

        return ans;
    }
}

Input:
[1,2,1,2,6,7,5,1], k = 2

Output:
[0, 1, 3, 4, 6, 12, 19, 24, 25]
[0, 0, 0, 0, 3, 4, 4, 4]
[4, 4, 4, 4, 4, 5, 6, 0]

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 


Can be generalize to more intervals!!

public class Solution {
    public int[] MaxSumOfThreeSubarrays(int[] nums, int k, int intervals = 3)
        {
            int[] sums = new int[nums.Length + 1];
            int[] ksums = new int[nums.Length + 1];
            int sum = 0;
            State[,] dp = new State[nums.Length + 1, intervals];
            dp[nums.Length, 0] = new State(new int[intervals], 0);
            dp[nums.Length, 1] = new State(new int[intervals], 0);
            dp[nums.Length, intervals-1] = new State(new int[intervals], 0);
            for (int i = nums.Length - 1; i >= 0; i--)
            {
                sum += nums[i];
                if (nums.Length - i >= k)
                {
                    ksums[i] = sum - sums[i + k];
                    dp[i, 0] = dp[i + 1, 0];
                    if (ksums[i] >= dp[i + 1, 0].sum)
                        dp[i, 0] = new State(new int[] { 0, 0, i }, ksums[i]);
                }
                else
                    dp[i, 0] = new State(new int[intervals], 0);
                dp[i, 1] = new State(new int[intervals], 0);
                dp[i, intervals-1] = new State(new int[intervals], 0);
                sums[i] = sum;
            }
            for (int i = 1; i < intervals; i++)
            {
                for (int j = nums.Length - ((i + 1) * k); j >= 0; j--)
                {
                    dp[j, i] = dp[j + 1, i];
                    if (ksums[j] + dp[j + k, i - 1].sum >= dp[j + 1, i].sum)
                    {
                        dp[j, i] = new State(new int[intervals], ksums[j] + dp[j + k, i - 1].sum);
                        for (int n = 0; n < intervals; n++)
                            dp[j, i].indices[n] = dp[j + k, i - 1].indices[n];
                        dp[j, i].indices[intervals-1 - i] = j;
                    }
                }
            }
            return dp[0, intervals-1].indices;
        }
        public class State
        {
            public int[] indices;
            public int sum;
            public State(int[] a, int s)
            {
                indices = a;
                sum = s;
            }
        }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



