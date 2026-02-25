259. 3Sum Smaller - Medium

Given an array of n integers nums and an integer target, find the number of index 
triplets i, j, k with 0 <= i < j < k < n that satisfy the condition 
nums[i] + nums[j] + nums[k] < target.

 
Example 1:

Input: nums = [-2,0,1,3], target = 2
Output: 2
Explanation: Because there are two triplets which sums are less than 2:
[-2,0,1]
[-2,0,3]

Example 2:
Input: nums = [], target = 0
Output: 0

Example 3:
Input: nums = [0], target = 0
Output: 0
 

Constraints:

n == nums.length
0 <= n <= 3500
-100 <= nums[i] <= 100
-100 <= target <= 100


******************************************************
key:
	- is the array sorted?
	- don't return the equal triplets

	
	- edge case:
		1) array contains less than 3 elements, return 0
		2) empty array, return 0
		3) can store in set, and return set size?

******************************************************


这道题的最优解法是 排序 (Sorting) + 双指针 (Two Pointers)。
与 15. 3Sum 不同的是，这里求的是个数，且是不等式，这让我们可以利用排序后的单调性进行“批量计数”。

核心思路：利用单调性批量计数
    如果 nums[left] + nums[right] < current_target，那么由于数组已排序，
    left 与 left+1, left+2, ..., right 之间的任何元素组合也一定小于 current_target。


步骤 (Steps)：
1. 排序：将 nums 升序排列。
2. 外层循环：固定第一个数 nums[i]，目标变为寻找 nums[j] + nums[k] < target - nums[i]。
3. 内层双指针：
    设置 left = i + 1, right = n - 1。
    如果 nums[left] + nums[right] < sum：
        关键点：此时从 left + 1 到 right 的所有位置作为第三个数都符合条件。
        直接累加 count += (right - left)。
        left++ 继续尝试更大的值。
    否则：right-- 减小总和。



public int threeSumSmaller(int[] nums, int target) {
    // 1. 排序 (Sorting is essential for Two Pointers)
    Arrays.sort(nums);
    int count = 0;
    int n = nums.length;

    for (int i = 0; i < n - 2; i++) {
        int left = i + 1;
        int right = n - 1;
        int currentTarget = target - nums[i];

        while (left < right) {
            if (nums[left] + nums[right] < currentTarget) {
                // 2. 批量计数 (Batch Counting)
                // 如果 nums[left] + nums[right] 达标，
                // 那么 nums[left] 配合中间任何一个数都达标
                count += (right - left);
                left++; 
            } else {
                right--;
            }
        }
    }
    return count;
}


3. 业界最佳实践 (Best Practices)
早期停止 (Early Exit)：
    在电商促销引擎中，如果 nums[i] + nums[i+1] + nums[i+2] 已经大于等于 target，且数组已排序，
    那么后续所有组合都不可能达标，可以直接 break循环

避免重复计算：
    如果 target 是静态的，可以利用 Caffeine Cache 缓存计算结果。在微服务环境下，如果数据源频繁变动，
    建议在 Redis 中存储计算出的 count 并设置合理的 TTL (Time To Live)。

处理溢出 (Overflow Handling)：
    在处理财务或大数据金额时，int 可能会溢出。建议在计算 sum 时使用 long 类型，或者在 Java 
    中使用 Math.addExact() 来捕获溢出异常。


=======================================================================================================
Method 1: fix 1 element, then 2 pointers


Stats:

	- time: 0(n^2)
	- space:O(1)


Method:
	target = 7
	-	[1, 2, 3, 5, 8]
 		 ↑        ↑
		left    right
Now the pair sum is 1+5=6, which is less than target. How many pairs 
with one of the index=left that satisfy the condition? You can tell by the 
difference between right and left which is 3, namely (1,2), (1,3),
and (1,5). Therefore, we move left one step to its right.




class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            sum += twoSumSmaller(nums, i + 1, target - nums[i]);
        }
        return sum;
    }

    private int twoSumSmaller(int[] nums, int startIndex, int target) {
        int sum = 0;
        int left = startIndex;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                sum += right - left;
                left++;
            } else {
                right--;
            }
        }
        return sum;
    }
}