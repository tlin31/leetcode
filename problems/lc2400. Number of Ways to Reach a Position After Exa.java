2400. Number of Ways to Reach a Position After Exactly k Steps - Medium

You are given two positive integers startPos and endPos. Initially, you are standing at position startPos on an infinite number line. With one step, you can move either one position to the left, or one position to the right.

Given a positive integer k, return the number of different ways to reach the position endPos starting from startPos, such that you perform exactly k steps. Since the answer may be very large, return it modulo 109 + 7.

Two ways are considered different if the order of the steps made is not exactly the same.

Note that the number line includes negative integers.

 

Example 1:

Input: startPos = 1, endPos = 2, k = 3
Output: 3
Explanation: We can reach position 2 from 1 in exactly 3 steps in three ways:
- 1 -> 2 -> 3 -> 2.
- 1 -> 2 -> 1 -> 2.
- 1 -> 0 -> 1 -> 2.
It can be proven that no other way is possible, so we return 3.
Example 2:

Input: startPos = 2, endPos = 5, k = 10
Output: 0
Explanation: It is impossible to reach position 5 from position 2 in exactly 10 steps.
 

Constraints:

1 <= startPos, endPos, k <= 1000



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

1. 题目逻辑分析
	目标：从 startPos 出发，经过 恰好 k 步，到达 endPos。
	规则：每一步可以向左（+1）或向右（-1）。

	核心转换：
		设向右走了 a 步，向左走了 b 步。
		总步数限制：a + b = k。
		净位移要求：a - b = target（其中 target = |endPos - startPos|）。
	
	联立方程解得：2a = k + target，即 a = (k + target) / 2。
	
	无法到达的判定条件：
		如果 k + target 是奇数，无法到达（步数与位移的奇偶性必须一致）。
		如果 k < target，步数不够，无法到达。


记忆化递归（动态规划）
	如果你在面试中一时想不起组合公式，可以使用标准的 DP 思路：
	状态定义：dfs(dist, remain) 表示当前距离目标还有 dist，剩余 remain 步时的方案数。
	转移方程：dfs(dist, remain) = dfs(dist + 1, remain - 1) + dfs(abs(dist - 1), remain - 1)。


class Solution {
    private Integer[][] memo;
    private final int MOD = 1_000_000_007;

    public int numberOfWays(int startPos, int endPos, int k) {
        int target = Math.abs(endPos - startPos);
        if (target > k) return 0;
        // k 的最大值为 1000
        memo = new Integer[target + k + 1][k + 1];
        return dfs(target, k);
    }

    private int dfs(int dist, int remain) {
        if (dist > remain) return 0; // 步数不够，无法到达
        if (remain == 0) return dist == 0 ? 1 : 0;
        
        if (memo[dist][remain] != null) return memo[dist][remain];
        
        // 向左走（距离变大）或向右走（距离变小，注意取绝对值处理对称性）
        long res = (long) dfs(dist + 1, remain - 1) + dfs(Math.abs(dist - 1), remain - 1);
        return memo[dist][remain] = (int) (res % MOD);
    }
}



Stats:

	- 
	- 







