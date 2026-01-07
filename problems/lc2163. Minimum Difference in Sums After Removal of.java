2163. Minimum Difference in Sums After Removal of Elements - Hard

You are given a 0-indexed integer array nums consisting of 3 * n elements.

You are allowed to remove any subsequence of elements of size exactly n from nums. The remaining 2 * n elements will be divided into two equal parts:

The first n elements belonging to the first part and their sum is sumfirst.
The next n elements belonging to the second part and their sum is sumsecond.
The difference in sums of the two parts is denoted as sumfirst - sumsecond.

For example, if sumfirst = 3 and sumsecond = 2, their difference is 1.
Similarly, if sumfirst = 2 and sumsecond = 3, their difference is -1.
Return the minimum difference possible between the sums of the two parts after the removal of n elements.

 

Example 1:

Input: nums = [3,1,2]
Output: -1
Explanation: Here, nums has 3 elements, so n = 1. 
Thus we have to remove 1 element from nums and divide the array into two equal parts.
- If we remove nums[0] = 3, the array will be [1,2]. The difference in sums of the two parts will be 1 - 2 = -1.
- If we remove nums[1] = 1, the array will be [3,2]. The difference in sums of the two parts will be 3 - 2 = 1.
- If we remove nums[2] = 2, the array will be [3,1]. The difference in sums of the two parts will be 3 - 1 = 2.
The minimum difference between sums of the two parts is min(-1,1,2) = -1. 
Example 2:

Input: nums = [7,9,5,8,1,3]
Output: 1
Explanation: Here n = 2. So we must remove 2 elements and divide the remaining array into two parts containing two elements each.
If we remove nums[2] = 5 and nums[3] = 8, the resultant array will be [7,9,1,3]. The difference in sums will be (7+9) - (1+3) = 12.
To obtain the minimum difference, we should remove nums[1] = 9 and nums[4] = 1. The resultant array becomes [7,5,8,3]. The difference in sums of the two parts is (7+5) - (8+3) = 1.
It can be shown that it is not possible to obtain a difference smaller than 1.
 

Constraints:

nums.length == 3 * n
1 <= n <= 105
1 <= nums[i] <= 105


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

🔑 删除 n 个元素 ⇔ 等价于：

	左边选 n 个（尽量小）

	右边选 n 个（尽量大）

	中间那 n 个自然被“删掉”

于是问题转化为：

	在某个分割点 i：

	左边 [0..i] 里选 n 个最小

	右边 [i+1..end] 里选 n 个最大

	最小化差值

三、总体算法框架
1️. 预处理左边：leftMin[i]

leftMin[i]：👉 在 nums[0..i] 中，选 n 个最小数的和

用 大顶堆（maxHeap）

保留当前最小的 n 个

超过 n 就踢掉最大的

2️. 预处理右边：rightMax[i]

rightMax[i]：
👉 在 nums[i..end] 中，选 n 个最大数的和

用 小顶堆（minHeap）

保留当前最大的 n 个

超过 n 就踢掉最小的

3️. 枚举分割点

合法的分割点是：

i ∈ [n-1, 2n-1]


因为：

左边至少有 n 个

右边至少有 n 个

计算：

min(leftMin[i] - rightMax[i+1])


Stats:

	- 
	- 
class Solution {
    public long minimumDifference(int[] nums) {
        int n = nums.length / 3;
        int len = nums.length;

        long[] leftMin = new long[len];
        long[] rightMax = new long[len];

        // 1. 左边：n 个最小 → 大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        long sum = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            maxHeap.offer(nums[i]);

            if (maxHeap.size() > n) {
                sum -= maxHeap.poll();
            }
            leftMin[i] = (maxHeap.size() == n) ? sum : Long.MAX_VALUE;
        }

        // 2. 右边：n 个最大 → 小顶堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        sum = 0;
        for (int i = len - 1; i >= 0; i--) {
            sum += nums[i];
            minHeap.offer(nums[i]);

            if (minHeap.size() > n) {
                sum -= minHeap.poll();
            }
            rightMax[i] = (minHeap.size() == n) ? sum : Long.MIN_VALUE;
        }

        // 3. 枚举分割点
        long res = Long.MAX_VALUE;
        for (int i = n - 1; i < 2 * n; i++) {
            res = Math.min(res, leftMin[i] - rightMax[i + 1]);
        }

        return res;
    }
}

