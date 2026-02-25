2908. Minimum Sum of Mountain Triplets I - Easy

You are given a 0-indexed array nums of integers.

A triplet of indices (i, j, k) is a mountain if:

i < j < k
nums[i] < nums[j] and nums[k] < nums[j]
Return the minimum possible sum of a mountain triplet of nums. If no such triplet exists, return -1.

 
Example 1:

Input: nums = [8,6,1,5,3]
Output: 9
Explanation: Triplet (2, 3, 4) is a mountain triplet of sum 9 since: 
- 2 < 3 < 4
- nums[2] < nums[3] and nums[4] < nums[3]
And the sum of this triplet is nums[2] + nums[3] + nums[4] = 9. It can be shown that there are no mountain triplets with a sum of less than 9.
Example 2:

Input: nums = [5,4,8,7,10,2]
Output: 13
Explanation: Triplet (1, 3, 5) is a mountain triplet of sum 13 since: 
- 1 < 3 < 5
- nums[1] < nums[3] and nums[5] < nums[3]
And the sum of this triplet is nums[1] + nums[3] + nums[5] = 13. It can be shown that there are no mountain triplets with a sum of less than 13.
Example 3:

Input: nums = [6,5,4,3,4,5]
Output: -1
Explanation: It can be shown that there are no mountain triplets in nums.
 

Constraints:

3 <= nums.length <= 50
1 <= nums[i] <= 50



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

	-	



Stats:

	- 
	- 

核心思路：预处理左右最小值 (Precompute Minima) 
	对于每个元素 nums[j]，如果它能作为山顶，我们需要：
	它左侧的最小值 leftMin 必须小于 nums[j]。
	它右侧的最小值 rightMin 必须小于 nums[j]。

步骤 (Steps)：
	预处理右侧最小值数组 (Right Min Array)：从右往左遍历，记录每个位置右侧的所有元素中的最小值。
	动态维护左侧最小值 (Dynamic Left Min)：从左往右遍历 nums[j]，同时记录遇到的最小值。
	计算结果：对于每个 j，如果满足山形条件，计算 sum = leftMin + nums[j] + rightMin 并更新全局最小值。 

public int minimumSum(int[] nums) {
    int n = nums.length;
    int[] rightMin = new int[n];
    
    // 1. 预处理右侧最小值 (Suffix Min)
    rightMin[n - 1] = nums[n - 1];
    for (int i = n - 2; i >= 0; i--) {
        rightMin[i] = Math.min(rightMin[i + 1], nums[i]);
    }

    int minSum = Integer.MAX_VALUE;
    int leftMin = nums[0]; // 2. 动态维护左侧最小值 (Prefix Min)

    for (int j = 1; j < n - 1; j++) {
        // 3. 检查是否满足山形条件 (Check Mountain condition)
        if (leftMin < nums[j] && rightMin[j + 1] < nums[j]) {
            minSum = Math.min(minSum, leftMin + nums[j] + rightMin[j + 1]);
        }
        // 更新左侧最小值供下一个 j 使用
        leftMin = Math.min(leftMin, nums[j]);
    }

    return minSum == Integer.MAX_VALUE ? -1 : minSum;
}





