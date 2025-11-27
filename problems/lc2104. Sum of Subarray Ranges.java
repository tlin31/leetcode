2104. Sum of Subarray Ranges - Medium

You are given an integer array nums. The range of a subarray of nums is the difference 
between the largest and smallest element in the subarray.

Return the sum of all subarray ranges of nums.

A subarray is a contiguous non-empty sequence of elements within an array.

 

Example 1:

Input: nums = [1,2,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0 
[2], range = 2 - 2 = 0
[3], range = 3 - 3 = 0
[1,2], range = 2 - 1 = 1
[2,3], range = 3 - 2 = 1
[1,2,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.


Example 2:

Input: nums = [1,3,3]
Output: 4
Explanation: The 6 subarrays of nums are the following:
[1], range = largest - smallest = 1 - 1 = 0
[3], range = 3 - 3 = 0
[3], range = 3 - 3 = 0
[1,3], range = 3 - 1 = 2
[3,3], range = 3 - 3 = 0
[1,3,3], range = 3 - 1 = 2
So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.
Example 3:

Input: nums = [4,-2,-3,4,1]
Output: 59
Explanation: The sum of all subarray ranges of nums is 59.
 

Constraints:

1 <= nums.length <= 1000
-109 <= nums[i] <= 109

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: monotonic stack

Method:

核心思想：

    所有子数组的 max - min
    =（每个元素作为 max 时贡献的总和） -（每个元素作为 min 时贡献的总和）

也就是：
    数组中每个元素 nums[i]，
    会在若干个子数组中充当“最大值”和“最小值”。
    要做的是 算它出现多少次。

① 如何算 “nums[i] 在多少个子数组里是最大值”？

    用 单调递减栈 找：

    左侧第一个比它大的元素距离 L

    右侧第一个比它大的元素距离 R

    当 j 左边没有更大的（或更小的）元素时，左边的边界就是索引 -1，所以左边距离是 j - (-1) = j + 1。
    这表示：从 0 到 j 之间的所有子数组，都可以让 nums[j] 充当最大/最小值。

    则它作为最大值的子数组个数：

    count_max = L * R
    贡献 = nums[i] * count_max

② 如何算 “nums[i] 在多少个子数组里是最小值”？

    用 单调递增栈 找：

    左侧第一个比它小的元素距离 L

    右侧第一个比它小的元素距离 R

    则它作为最小值的子数组个数：

    count_min = L * R
    贡献 = nums[i] * count_min

Stats:

	- O（n）
	- O（n）

class Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        int[] minLeft = new int[n];
        int[] minRight = new int[n];
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];

        Deque<Integer> minStack = new ArrayDeque<Integer>();
        Deque<Integer> maxStack = new ArrayDeque<Integer>();

        for (int i = 0; i < n; i++) {
            while (!minStack.isEmpty() && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            //如果minStack是空的说明当前就是最小的了，左边比他更小的在index-1，这样算长度/有几个数字比他更小时
            //算的i-(-1)=i+1个，才是对的
            minLeft[i] = minStack.isEmpty() ? -1 : minStack.peek();
            minStack.push(i);
            
            // 如果 nums[maxStack.peek()] == nums[i], 那么根据定义，
            // nums[maxStack.peek()] 逻辑上小于 nums[i]，因为 maxStack.peek() < i
            while (!maxStack.isEmpty() && nums[maxStack.peek()] <= nums[i]) { 
                maxStack.pop();
            }
            maxLeft[i] = maxStack.isEmpty() ? -1 : maxStack.peek();
            maxStack.push(i);
        }

        minStack.clear();
        maxStack.clear();
        
        for (int i = n - 1; i >= 0; i--) {
            // 如果 nums[minStack.peek()] == nums[i], 那么根据定义，
            // nums[minStack.peek()] 逻辑上大于 nums[i]，因为 minStack.peek() > i
            while (!minStack.isEmpty() && nums[minStack.peek()] >= nums[i]) { 
                minStack.pop();
            }
            minRight[i] = minStack.isEmpty() ? n : minStack.peek();
            minStack.push(i);

            while (!maxStack.isEmpty() && nums[maxStack.peek()] < nums[i]) {
                maxStack.pop();
            }
            maxRight[i] = maxStack.isEmpty() ? n : maxStack.peek();
            maxStack.push(i);
        }

        long sumMax = 0, sumMin = 0;
        for (int i = 0; i < n; i++) {
            sumMax += (long) (maxRight[i] - i) * (i - maxLeft[i]) * nums[i];
            sumMin += (long) (minRight[i] - i) * (i - minLeft[i]) * nums[i];
        }
        return sumMax - sumMin;
    }
}

