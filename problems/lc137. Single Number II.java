137. Single Number II - Medium

Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.

You must implement a solution with a linear runtime complexity and use only constant extra space.

 

Example 1:

Input: nums = [2,2,3,2]
Output: 3
Example 2:

Input: nums = [0,1,0,1,0,1,99]
Output: 99
 

Constraints:

1 <= nums.length <= 3 * 104
-231 <= nums[i] <= 231 - 1
Each element in nums appears exactly three times except for one element which appears once.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
✔ 解法1：逐位统计（bit count）

Stats:
Time  O(32n) → O(n)
Space O(1)
	

核心想法：

如果一个数字出现 3 次，那么它们的每一位出现次数也会被 3 整除。

只要统计每一位 1 的数量，然后对 3 取模，留下的就是那个只出现 1 次数字在该位的值。

举例：

nums = [2,2,3,2]


写成二进制：

num	bit
2	010
2	010
3	011
2	010

各 bit 求和：

bit0: 1 次
bit1: 4 次
bit2: 0 次


对 3 取模：

bit0: 1 % 3 = 1
bit1: 4 % 3 = 1
bit2: 0 % 3 = 0


得到：011 = 3

Java 代码（位计数）
class Solution {
    public int singleNumber(int[] nums) {
        int result = 0;

        for (int i = 0; i < 32; i++) {
            int bitSum = 0;

            for (int num : nums) {
                bitSum += (num >> i) & 1;
            }

            if (bitSum % 3 != 0) {
                result |= (1 << i);
            }
        }

        return result;
    }
}

===================================================================================================

✔ 解法2：状态机 + 位运算（最优）

核心思想：

	对每一位进行出现次数统计，
	但不是用整数计数，而是用两个变量存状态：

	ones   表示该位出现过 1 次
	twos   表示该位出现过 2 次



	class Solution {
	    public int singleNumber(int[] nums) {
	        int ones = 0, twos = 0;

	        for (int n : nums) {
	            ones = (ones ^ n) & ~twos;
	            twos = (twos ^ n) & ~ones;
	        }

	        return ones;
	    }
	}

状态机解释（超直观）

我们只关心每一位：

出现次数	状态
0 次	00
1 次	01
2 次	10
3 次	回到 00

而 ones 和 twos 正好形成一个 2-bit 状态机。

手动模拟例子 nums = [2, 2, 3, 2]

以简化的 3-bit 说明：

2 = 010
3 = 011


初始：

ones = 000
twos = 000


处理第一个 2：

ones = 010
twos = 000


处理第二个 2：

ones = 000
twos = 010


处理 3：

ones = 011
twos = 010


处理第三个 2：

ones = 011
twos = 000


答案就是：

ones = 011 = 3

Why this works?

数学上它做到了：

把出现 3 次的位清零
只保留出现 1 次的位
