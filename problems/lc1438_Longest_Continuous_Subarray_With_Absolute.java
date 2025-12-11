1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit - Medium


Given an array of integers nums and an integer limit, return the size of the longest non-empty 
subarray such that the absolute difference between any two elements of this subarray is less 
than or equal to limit.

 

Example 1:

Input: nums = [8,2,4,7], limit = 4
Output: 2 
Explanation: All subarrays are: 
[8] with maximum absolute diff |8-8| = 0 <= 4.
[8,2] with maximum absolute diff |8-2| = 6 > 4. 
[8,2,4] with maximum absolute diff |8-2| = 6 > 4.
[8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
[2] with maximum absolute diff |2-2| = 0 <= 4.
[2,4] with maximum absolute diff |2-4| = 2 <= 4.
[2,4,7] with maximum absolute diff |2-7| = 5 > 4.
[4] with maximum absolute diff |4-4| = 0 <= 4.
[4,7] with maximum absolute diff |4-7| = 3 <= 4.
[7] with maximum absolute diff |7-7| = 0 <= 4. 
Therefore, the size of the longest subarray is 2.
Example 2:

Input: nums = [10,1,2,4,7,2], limit = 5
Output: 4 
Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.
Example 3:

Input: nums = [4,2,2,2,4,4,2,2], limit = 0
Output: 3
 

Constraints:

1 <= nums.length <= 105
1 <= nums[i] <= 109
0 <= limit <= 109

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

	-	use 2 dequeues
	- 记住最大和最小值就行

    为了快速得到窗口的最大/最小值，我们使用两个 Monotonic Queue：

    maxDeque：维护单调递减队列 → 队头是当前最大值

    minDeque：维护单调递增队列 → 队头是当前最小值

    通过滑动窗口：

    每次加入 nums[r]

    检查窗口是否满足 max - min <= limit

    如果超出 limit，就移动左指针 l


if (nums[l] == maxDeque.peekFirst()) maxDeque.pollFirst();

    当我们移动左指针时，旧的窗口左端元素 nums[l] 会被移出窗口。

    但我们维护了两个 deque：

    maxDeque：维护窗口最大值（递减队列）

    minDeque：维护窗口最小值（递增队列）

    如果即将划出窗口的 nums[l] 恰好是当前的最大值（maxDeque.first），那么它必须从 maxDeque 去掉。

    因为它离开窗口了，就不能继续作为窗口的最大值了。


    时间复杂度：O(N)
    空间复杂度：O(N)（最坏情况队列存 N 个）


class Solution {
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();

        int l = 0, res = 0;

        for (int r = 0; r < nums.length; r++) {
            int x = nums[r];

            // Maintain max deque (decreasing)
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < x) {
                maxDeque.pollLast();
            }
            maxDeque.addLast(x);

            // Maintain min deque (increasing)
            while (!minDeque.isEmpty() && minDeque.peekLast() > x) {
                minDeque.pollLast();
            }
            minDeque.addLast(x);

            // Shrink window if condition violated,  左指针右移缩小窗口
            while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (nums[l] == maxDeque.peekFirst()) maxDeque.pollFirst();
                if (nums[l] == minDeque.peekFirst()) minDeque.pollFirst();
                l++;
            }

            res = Math.max(res, r - l + 1);
        }

        return res;
    }
}




    public int longestSubarray(int[] A, int limit) {
        Deque<Integer> maxd = new ArrayDeque<>();
        Deque<Integer> mind = new ArrayDeque<>();
        int i = 0, j;
        for (j = 0; j < A.length; ++j) {
        	//只要当前的比maxd最后的大，就一直从尾部pop，后面maxd.add的时候才是正确的顺序
            while (!maxd.isEmpty() && A[j] > maxd.peekLast()) maxd.pollLast();
            while (!mind.isEmpty() && A[j] < mind.peekLast()) mind.pollLast();

            maxd.add(A[j]);
            mind.add(A[j]);

            if (maxd.peek() - mind.peek() > limit) {
                if (maxd.peek() == A[i]) maxd.poll();
                if (mind.peek() == A[i]) mind.poll();
                ++i;
            }

        }
        return j - i;
    }


