3578. Count Partitions With Max-Min Difference at Most K - Medium

You are given an integer array nums and an integer k. Your task is to partition nums into one or more non-empty contiguous segments such that in each segment, the difference between its maximum and minimum elements is at most k.

Return the total number of ways to partition nums under this condition.

Since the answer may be too large, return it modulo 10^9 + 7.

 

Example 1:

Input: nums = [9,4,1,3,7], k = 4

Output: 6

Explanation:

There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:

[[9], [4], [1], [3], [7]]
[[9], [4], [1], [3, 7]]
[[9], [4], [1, 3], [7]]
[[9], [4, 1], [3], [7]]
[[9], [4, 1], [3, 7]]
[[9], [4, 1, 3], [7]]
Example 2:

Input: nums = [3,3,4], k = 0

Output: 2

Explanation:

There are 2 valid partitions that satisfy the given conditions:

[[3], [3], [4]]
[[3, 3], [4]]
 

Constraints:

2 <= nums.length <= 5 * 104
1 <= nums[i] <= 109
0 <= k <= 109


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************

给一个数组 nums，统计有多少种 连续子数组（subarray）划分方式，
使得 每一段子数组的 max − min ≤ k。

===================================================================================================
Method 1:

Method:

dp[i] = 前 i 个元素（0..i-1）能形成的合法划分数量
最终答案是：dp[n]

dp[i] += dp[j]
前提是：max(nums[j..i-1]) - min(nums[j..i-1]) ≤ k


对每个右端点 i：

找所有满足条件的 j
把 dp[j] 累加到 dp[i]


对于固定的 r = i-1：

假设最左合法起点是 l，那么所有 [j..r]，只要 j ≥ l，都是合法区间

于是：dp[i] = dp[l] + dp[l+1] + ... + dp[i-1]




五、优化 DP 累加：前缀和

定义：

prefix[i] = dp[0] + dp[1] + ... + dp[i-1]


那么：

dp[i] = prefix[i] - prefix[l]


💡 这一步是整题从 O(n²) → O(n) 的关键
Stats:

	- 
	- 



class Solution {
    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        long MOD = 1_000_000_007;

        long[] dp = new long[n + 1];
        long[] prefix = new long[n + 2];

        dp[0] = 1;
        prefix[1] = 1;

        Deque<Integer> maxD = new ArrayDeque<>();
        Deque<Integer> minD = new ArrayDeque<>();

        int l = 0;

        for (int r = 0; r < n; r++) {
            // max deque
            while (!maxD.isEmpty() && nums[maxD.peekLast()] < nums[r])
                maxD.pollLast();
            maxD.addLast(r);

            // min deque
            while (!minD.isEmpty() && nums[minD.peekLast()] > nums[r])
                minD.pollLast();
            minD.addLast(r);

            // shrink window
            while (nums[maxD.peekFirst()] - nums[minD.peekFirst()] > k) {
                if (maxD.peekFirst() == l) maxD.pollFirst();
                if (minD.peekFirst() == l) minD.pollFirst();
                l++;
            }

            dp[r + 1] = (prefix[r + 1] - prefix[l] + MOD) % MOD;
            prefix[r + 2] = (prefix[r + 1] + dp[r + 1]) % MOD;
        }

        return (int) dp[n];
    }
}


