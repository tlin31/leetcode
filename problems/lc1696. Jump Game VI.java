1696. Jump Game VI - Medium

You are given a 0-indexed integer array nums and an integer k.

You are initially standing at index 0. In one move, you can jump at most k steps forward without 
going outside the boundaries of the array. That is, you can jump from index i to any index in the 
range [i + 1, min(n - 1, i + k)] inclusive.

You want to reach the last index of the array (index n - 1). Your score is the sum of all nums[j] 
for each index j you visited in the array.

Return the maximum score you can get.

 

Example 1:

Input: nums = [1,-1,-2,4,-7,3], k = 2
Output: 7
Explanation: You can choose your jumps forming the subsequence [1,-1,4,3] (underlined above). 
The sum is 7.

Example 2:

Input: nums = [10,-5,-2,4,0,3], k = 3
Output: 17
Explanation: You can choose your jumps forming the subsequence [10,4,3] (underlined above). 
The sum is 17.

Example 3:

Input: nums = [1,-5,-20,4,-1,3,-6,-3], k = 2
Output: 0
 

Constraints:

1 <= nums.length, k <= 105
-104 <= nums[i] <= 104


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

DP: 
	dp[i] = 到达位置 i 的最大得分
	状态转移: dp[i] = nums[i] + max(dp[j]), 其中 j ∈ [i-k, i-1]


单调递减队列 Deque 里存什么？

	👉 存下标 index（而不是值）

	原因：判断是否出窗口：i - k

	比较大小：通过 dp[index]


规则 1️：窗口过期 → 从队头弹
	if (!dq.isEmpty() && dq.peekFirst() < i - k) {
	    dq.pollFirst();
	}

规则 2️：维护单调递减（从队尾弹）
	while (!dq.isEmpty() && dp[dq.peekLast()] <= dp[i]) {
	    dq.pollLast();
	}


👉 当前 dp 更大，后面的永远不可能再用

Stats:

	- 
	- 

class Solution {
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];

        Deque<Integer> deque = new ArrayDeque<>();
        deque.offer(0);

        for (int i = 1; i < n; i++) {

            // 1️⃣ 移除窗口外的元素
            if (deque.peekFirst() < i - k) {
                deque.pollFirst();
            }

            // 2️⃣ 计算 dp
            dp[i] = dp[deque.peekFirst()] + nums[i];

            // 3️⃣ 维护单调递减
            while (!deque.isEmpty() && dp[deque.peekLast()] <= dp[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);
        }

        return dp[n - 1];
    }
}


