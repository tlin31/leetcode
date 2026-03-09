2460. Apply Operations to an Array - Easy

You are given a 0-indexed array nums of size n consisting of non-negative integers.

You need to apply n - 1 operations to this array where, in the ith operation (0-indexed), you will apply the following on the ith element of nums:

If nums[i] == nums[i + 1], then multiply nums[i] by 2 and set nums[i + 1] to 0. Otherwise, you skip this operation.
After performing all the operations, shift all the 0's to the end of the array.

For example, the array [1,0,2,0,0,1] after shifting all its 0's to the end, is [1,2,1,0,0,0].
Return the resulting array.

Note that the operations are applied sequentially, not all at once.

 

Example 1:

Input: nums = [1,2,2,1,1,0]
Output: [1,4,2,0,0,0]
Explanation: We do the following operations:
- i = 0: nums[0] and nums[1] are not equal, so we skip this operation.
- i = 1: nums[1] and nums[2] are equal, we multiply nums[1] by 2 and change nums[2] to 0. The array becomes [1,4,0,1,1,0].
- i = 2: nums[2] and nums[3] are not equal, so we skip this operation.
- i = 3: nums[3] and nums[4] are equal, we multiply nums[3] by 2 and change nums[4] to 0. The array becomes [1,4,0,2,0,0].
- i = 4: nums[4] and nums[5] are equal, we multiply nums[4] by 2 and change nums[5] to 0. The array becomes [1,4,0,2,0,0].
After that, we shift the 0's to the end, which gives the array [1,4,2,0,0,0].
Example 2:

Input: nums = [0,1]
Output: [1,0]
Explanation: No operation can be applied, we just shift the 0 to the end.
 

Constraints:

2 <= nums.length <= 2000
0 <= nums[i] <= 1000


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

逻辑步骤 (Logic Steps)：
	第一步：遍历 0 到 n-2，若 nums[i] == nums[i+1]，则 nums[i] *= 2, nums[i+1] = 0。
	第二步：初始化 slow = 0。遍历 fast 指针，若 nums[fast] != 0，则将 nums[fast] 交换或覆盖到 nums[slow] 处，
	       后 slow++。其余位置补零。


Stats:

	- 
	- 

class Solution {
    public int[] applyOperations(int[] nums) {
        int n = nums.length;
        
        // 1. 执行合并操作 (Step 1: Perform operations)
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1] && nums[i] != 0) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
        }
        
        // 2. 移动零 (Step 2: Shift non-zeros to the front)
        int slow = 0;
        for (int fast = 0; fast < n; fast++) {
            if (nums[fast] != 0) {
                // 如果 fast 和 slow 不同，将非零值移到前面
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
        return nums;
    }
}

class Solution:
    def applyOperations(self, nums: List[int]) -> List[int]:
        n = len(nums)
        
        # 1. 模拟合并过程 (Step 1: Simulation)
        for i in range(n - 1):
            if nums[i] == nums[i + 1]:
                nums[i] *= 2
                nums[i + 1] = 0
                
        # 2. 紧凑化非零元素 (Step 2: Compaction using Two Pointers)
        slow = 0
        for fast in range(n):
            if nums[fast] != 0:
                nums[slow], nums[fast] = nums[fast], nums[slow]
                slow += 1
                
        return nums




避免冗余分配 (Avoid Extra Allocation)：
	在处理高频微服务请求时，应直接在传入的数组/列表上修改，而不是 new 一个结果集。
	这能显著降低 JVM 垃圾回收 (GC) 的压力。

处理零值敏感度：
	在电商数据库索引中，“零”往往代表无效或空位。通过将零移至末尾，可以利用 顺序扫描 (Sequential Scan) 
	的特性提高读取缓存命中率。

单次遍历 (One-pass thinking)：
	虽然本题分为两步，但在更复杂的场景下，可以尝试通过一次遍历同时处理合并和移动，但这往往会增加代码的维护难度。





