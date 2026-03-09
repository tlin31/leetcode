189. Rotate Array
Medium

Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.

 

Example 1:

Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: nums = [-1,-100,3,99], k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
 

Constraints:

1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
0 <= k <= 105
 

Follow up:

Try to come up with as many solutions as you can. There are at least three different ways to solve this problem.
Could you do it in-place with O(1) extra space?


******************************************************
key:
	- 1) use pointers, with a fake tail node, connecting the last and first node
	- 2) use first = num[k], last = num[k-1], when k = num.length-1, k = 0, then iterate to k-1
	- 3) use array concat, then get partial of the new ArrayList	
	- 4) reverse in place for O(1) space

	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
public void rotate(int[] nums, int k) {

    if(nums == null || nums.length < 2){
        return;
    }
    
    k = k % nums.length;
    reverse(nums, 0, nums.length - k - 1);
    reverse(nums, nums.length - k, nums.length - 1);
    reverse(nums, 0, nums.length - 1);
    
}

private void reverse(int[] nums, int i, int j){
    int tmp = 0;       
    while(i < j){
        tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
        i++;
        j--;
    }
}

===============================================
method 2： 核心原理：环状替换 (Cyclic Replacements)
想象数组中的每个位置都是一个座位。我们把索引 i 处的元素搬到它旋转后的新位置 (i + k) % n。

动作：拿起一个元素（保存在临时变量 prev 中），走到它的目标位置。
替换：放下 prev，拿起该位置原有的元素，继续走向下一个目标位置。
闭环 (Cycle)：经过若干次移动，你会回到起始位置。

处理多个环：如果 n 和 k 有最大公约数 (GCD)，一次闭环无法遍历所有元素，需要从下一个起点开始继续。


class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0) return;

        int count = 0; // 记录已移动的元素个数 (Count of moved elements)
        for (int start = 0; count < n; start++) {
            int current = start;
            int prevValue = nums[start];
            
            do {
                int next = (current + k) % n;
                // 交换与暂存 (Swap and store)
                int temp = nums[next];
                nums[next] = prevValue;
                prevValue = temp;
                
                current = next;
                count++;
            } while (start != current); // 回到起点则结束当前环
        }
    }
}

Python 实现 (Python Implementation)

class Solution:
    def rotate(self, nums: List[int], k: int) -> None:
        n = len(nums)
        k %= n
        if k == 0: return

        count = 0
        start = 0
        while count < n:
            current = start
            prev_value = nums[start]
            
            while True:
                next_idx = (current + k) % n
                # 原地交换 (In-place swap)
                nums[next_idx], prev_value = prev_value, nums[next_idx]
                
                current = next_idx
                count += 1
                
                if start == current:
                    break
            start+=1


3. 业界最佳实践 (Best Practices)
1、 最大公约数 (GCD)：
环的数量正好等于 GDC(n, k)。
例如：n = 6, k = 2, gcd = 2, 那么你会遇到两个环：(0->2->4->0) 和 (1->3->5->1)。

2、 避免死循环：使用 count < n 作为外层判定是最稳健的，它确保每个元素只被搬动一次。

3. 内存局部性 (Memory Locality)：虽然这种方法是 O（1）空间，但由于它是“跳跃式”访问内存（从 i 跳到 i+k），
   在数组极大时，其 CPU 缓存命中率 (Cache Hit Rate) 可能不如三次翻转法。


