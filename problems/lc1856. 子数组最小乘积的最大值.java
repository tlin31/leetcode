1856. 子数组最小乘积的最大值 - 中等

一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。

比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。
由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。

请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 
64 位有符号整数 保存。

子数组 定义为一个数组的 连续 部分。

 

示例 1：

输入：nums = [1,2,3,2]
输出：14
解释：最小乘积的最大值由子数组 [2,3,2] （最小值是 2）得到。
2 * (2+3+2) = 2 * 7 = 14 。
示例 2：

输入：nums = [2,3,3,1,2]
输出：18
解释：最小乘积的最大值由子数组 [3,3] （最小值是 3）得到。
3 * (3+3) = 3 * 6 = 18 。
示例 3：

输入：nums = [3,1,5,6,4,2]
输出：60
解释：最小乘积的最大值由子数组 [5,6,4] （最小值是 4）得到。
4 * (5+6+4) = 4 * 15 = 60 。
 

提示：

1 <= nums.length <= 105
1 <= nums[i] <= 107


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

影响一个数组的 “最小乘积” 的元素是什么?
	- 1. 子数组的最小值 2. 子数组的和。所以我们只要确定了子数组的最小值，就完成了最关键的因素

贡献法
	试想，给定数组中的每个元素是否都可以成为子数组的最小值？nums = [1,2,3,2]
	答案是肯定的，nums中的所有元素都可以成为子数组的最小值。如元素2 可以成为子数组: [2]、[2, 3]、[2, 3, 2]的最小值。

	那么我们是不是可以先确定一个最小值，通过这个最小值, 算出以这个数为最小值的所有子数组的答案。
	即为 这个最小值对答案的 “贡献”。

	如 我们更具上面的例子，选出 2 作为子数组元素的最小值。那么 2 对于答案的 “贡献” 为:
	a=2∗(2)=4
	b=2∗(2+3)=10
	c=2∗(2+3+2)=14
	max(a,b,c)=14
	由上式我们可以得出结论:
	在选定了子数组的最小元素时, 数组的长度越大, 其最小乘积越大 (注： nums[i] >= 0)

解题思路：
- 先明确每一个选定的最小数字所能组成的最大子数组的边界

- 由题意我们可以看出，由最小值 i 组成的最长子数组, 其左边界为, i 左侧第一个严格小于 i 的数的下标 smaller_i和
  右边界为 i 右侧第一个严格大于 i 的数的下标 bigger_i 所组成的开区间(smaller_i, bigger_i)

- 所以我们可以在遍历题目给出的数组时，通过维护单调栈的方式, nums[i] 的边界即为 栈顶元素。

Use stack st which keeps index of elements less than nums[i] so far
	For i in [0..n-1]:
		Pop all elements from st which are greater or equal to nums[i]
		If st is not empty: left_bound[i] = st[-1] + 1 (because st[-1] is index of the nearest element smaller than nums[i])
		else: left_bound[i] = 0 (because there is no element smaller than nums[i] so far)
		Add i to st


在得出结果之前，我们需要:
1. 数组的前缀和 pre (快速计算子数组和)
2. 数组中每个元素作为最小元素可组成的子数组最大边界 (left[i], right[i])
3. 最小元素 i

现在我们直接可算出 各最小元素i对答案的“贡献”, 最终我们取最大值即可


Stats:

	- 
	- 

class Solution {
    public static final long MOD = (long) 1e9 + 7;

    public int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        long[] pre = new long[n + 1];
        for(int i = 0; i < n; i++) {
            pre[i + 1] = (long) (pre[i] + nums[i]);
        }

        int[] left = new int[n];
        Deque<Integer> stk = new ArrayDeque<>();

        // 如果元素左侧没有元素比其小，则左边界为开区间 -1
        stk.addLast(-1);
        for(int i = 0; i < n; i++) {
            while(stk.size() > 1 && nums[stk.getLast()] >= nums[i]) stk.pollLast();
            left[i] = stk.getLast();
            stk.addLast(i);
        }

        int[] right = new int[n];
        stk.clear();
        stk.addLast(n);
        for(int i = n - 1; i >= 0; i--) {
            while(stk.size() > 1 && nums[stk.getLast()] >= nums[i]) stk.pollLast();
            right[i] = stk.getLast();
            stk.addLast(i);
        }

        long ans = 0L;
        for(int i = 0; i < n; i++) {
            long sum = (long) (pre[right[i]] - pre[left[i] + 1]);
            long s = (long) (nums[i] * sum);
            ans = Math.max(ans, s);
        }

        return (int) (ans % MOD);
    }
}


