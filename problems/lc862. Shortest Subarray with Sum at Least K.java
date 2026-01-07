862. Shortest Subarray with Sum at Least K - Hard

Given an integer array nums and an integer k, return the length of the shortest non-empty 
subarray of nums with a sum of at least k. If there is no such subarray, return -1.

A subarray is a contiguous part of an array.

 

Example 1:

Input: nums = [1], k = 1
Output: 1
Example 2:

Input: nums = [1,2], k = 4
Output: -1
Example 3:

Input: nums = [2,-1,2], k = 3
Output: 3
 

Constraints:

1 <= nums.length <= 105
-105 <= nums[i] <= 105
1 <= k <= 109


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

用 前缀和 + 单调递增队列（Deque），在 O(n) 时间内找 最短 的子数组，使得子数组和 ≥ K。

为什么要用「单调队列」？

我们需要维护一个 候选 j 的集合，满足：

1. j 越小越好（子数组更长 → 可能不是最短）

2. prefix[j] 越小越好（更容易 ≥ K）

👉 所以维护： prefix 值单调递增的下标队列


现实场景类比（你偏好这个）
	场景：服务器负载监控

	nums[i]：第 i 秒负载变化（可能负）， K：需要触发告警的累计阈值

	问题：最短多久会触发告警？



Stats:

	- 
	- 


class Solution {
    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        Deque<Integer> dq = new ArrayDeque<>();
        int ans = n + 1;

        for (int i = 0; i <= n; i++) {

            // 1️⃣ 尝试缩短窗口
            while (!dq.isEmpty() && prefix[i] - prefix[dq.peekFirst()] >= k) {
                ans = Math.min(ans, i - dq.pollFirst());
            }

            // 2️⃣ 保持单调递增
            while (!dq.isEmpty() && prefix[i] <= prefix[dq.peekLast()]) {
                dq.pollLast();
            }

            dq.offerLast(i);
        }

        return ans == n + 1 ? -1 : ans;
    }
}


