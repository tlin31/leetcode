918. Maximum Sum Circular Subarray - Medium

Given a circular integer array nums of length n, return the maximum possible sum of a non-empty 
subarray of nums.

A circular array means the end of the array connects to the beginning of the array. Formally, 
the next element of nums[i] is nums[(i + 1) % n] and the previous element of nums[i] is 
nums[(i - 1 + n) % n].

A subarray may only include each element of the fixed buffer nums at most once. Formally, for 
a subarray nums[i], nums[i + 1], ..., nums[j], there does not exist i <= k1, k2 <= j with 
k1 % n == k2 % n.

 

Example 1:

Input: nums = [1,-2,3,-2]
Output: 3
Explanation: Subarray [3] has maximum sum 3.

Example 2:

Input: nums = [5,-3,5]
Output: 10
Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
Example 3:

Input: nums = [-3,-2,-3]
Output: -2
Explanation: Subarray [-2] has maximum sum -2.
 

Constraints:

n == nums.length
1 <= n <= 3 * 104
-3 * 104 <= nums[i] <= 3 * 104


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



为什么「环形数组」可以这样拆？

情况 1️：最大子数组 不跨环

    这就是经典的 Kadane（LC 53）：maxSubarraySum

情况 2️：最大子数组 跨环

    跨环意味着：[中间一段不要] + [两头拼起来]

    等价于：totalSum - minSubarraySum

    💡 因为你“拿走”了一段最小和的连续子数组。


三、最关键的坑（90% WA 出在这里）
❌ 全是负数怎么办？

    例子：nums = [-3, -2, -5]

    maxSubarray = -2

    minSubarray = -10

    totalSum - min = 0 ❌（非法！）

    ⚠️ 环形子数组必须是非空子数组

    ✅ 正确判断
    如果 maxSubarray < 0：
        return maxSubarray
    否则：
        return max(maxSubarray, totalSum - minSubarray)

maxSub：最大子数组和（Kadane）
minSub：最小子数组和（反向 Kadane）


class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int total = 0;

        int curMax = 0, maxSum = Integer.MIN_VALUE;
        int curMin = 0, minSum = Integer.MAX_VALUE;

        for (int num : nums) {
            curMax = Math.max(num, curMax + num);
            maxSum = Math.max(maxSum, curMax);

            curMin = Math.min(num, curMin + num);
            minSum = Math.min(minSum, curMin);

            total += num;
        }

        // 全负数情况
        if (maxSum < 0) return maxSum;

        return Math.max(maxSum, total - minSum);
    }
}






