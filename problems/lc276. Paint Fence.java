276. Paint Fence - Medium

You are painting a fence of n posts with k different colors. You must paint the posts following these rules:

Every post must be painted exactly one color.
There cannot be three or more consecutive posts with the same color.
Given the two integers n and k, return the number of ways you can paint the fence.

 

Example 1:


Input: n = 3, k = 2
Output: 6
Explanation: All the possibilities are shown.
Note that painting all the posts red or all the posts green is invalid because there cannot be three posts in a row with the same color.
Example 2:

Input: n = 1, k = 1
Output: 1
Example 3:

Input: n = 7, k = 2
Output: 42
 

Constraints:

1 <= n <= 50
1 <= k <= 105
The testcases are generated such that the answer is in the range [0, 231 - 1] for the given n and k.

有 n 个栅栏，k 种颜色。
要求：不能有 超过 2 个连续的栅栏颜色一样。

问总方案数。

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


栅栏要么：

① 与前一个颜色相同（same）

但同色连续不能超过 2，因此：

same[i] = diff[i−1]

因为要让第 i 栅栏和第 i-1 栅栏同色，
那第 i-1 必须是和前一个不同色（否则连续3个同色）。

② 与前一个颜色不同（diff）

前一个随便是什么颜色，总有 (k - 1) 种选择：

diff[i] = (same[i−1] + diff[i−1]) * (k - 1)

⭐ 状态转移方程
same[i] = diff[i−1]
diff[i] = (same[i−1] + diff[i−1]) * (k − 1)

total[i] = same[i] + diff[i]


起始状态：
当 n = 1：

same[1] = 0         // 单个栏杆不会有“同色连续”
diff[1] = k


Stats:

	- 
	- 

class Solution {
    public int numWays(int n, int k) {
        if (n == 0) return 0;
        if (n == 1) return k;


        int same = 0;      // same[i - 1]
        int diff = k;      // diff[i - 1]

        for (int i = 2; i <= n; i++) {
            int newSame = diff;
            int newDiff = (same + diff) * (k - 1);

            same = newSame;
            diff = newDiff;
        }

        return same + diff;
    }
}




