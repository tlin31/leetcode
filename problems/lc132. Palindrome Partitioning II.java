132. Palindrome Partitioning II - Hard

Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

 

Example 1:

Input: s = "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
Example 2:

Input: s = "a"
Output: 0
Example 3:

Input: s = "ab"
Output: 1
 

Constraints:

1 <= s.length <= 2000
s consists of lowercase English letters only.


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

1. 核心思路：两步走
	第一步：预处理回文状态。使用区间 DP 预先计算出字符串中任意子串 s[i...j] 是否为回文。
	这样在后续计算时，判断回文的时间复杂度可降至 O(1)。

	第二步：线性规划求最少分割。定义 dp[i] 表示前缀 s[0...i] 达到回文分割要求的最少分割次数。


2. 状态转移方程
	对于 dp[i]，我们需要寻找一个位置 j （0<=j<=i）：
		如果 s[j...i] 本身就是回文：如果 j=0（即整个前缀已经是回文），则 dp[i] = 0。
		如果 j>0，则 dp[i] = min(dp[i], dp[j-1] + 1)。


Stats:

	- 
	- 



class Solution {
    public int minCut(String s) {
        int n = s.length();
        if (n <= 1) return 0;

        // 第一步：预处理回文判定矩阵 (Range DP)
        // isPal[i][j] 代表 s[i...j] 是否是回文
        boolean[][] isPal = new boolean[n][n];
        // 这里使用之前总结过的“长度变量 L”或“逆向 i 正向 j”均可
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    isPal[i][j] = (j - i < 3) || isPal[i + 1][j - 1];
                }
            }
        }

        // 第二步：线性 DP 求最小分割次数
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            // 最大分割次数初始化：每个字符都切一刀，次数为 i
            dp[i] = i; 
            
            for (int j = 0; j <= i; j++) {
                if (isPal[j][i]) {
                    if (j == 0) {
                        dp[i] = 0; // 整个 s[0...i] 是回文，不需要切割
                    } else {
                        dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }
        }

        return dp[n - 1];
    }
}


为什么要 j-i<3?

1. 逻辑拆解
	回文定义的递归逻辑是：如果首尾字符相等（s[i] == s[j]），那么 s[i...j] 是否为回文取决于
	去掉首尾后的子串 s[i+1...j-1] 是否为回文。

	但当子串非常短时，i+1 和 j-1 可能会导致索引越界或逻辑失效。

	(j - i < 3) 完美处理了以下三种场景：
	场景 1：子串长度为 1 ( i == j )
		此时 j - i = 0，满足 < 3。例如 "a"：显然是回文。

	场景 2：子串长度为 2 ( j - i == 1 )
		此时 j - i = 1，满足 < 3。例如 "aa"：只要首尾相等，它就是回文，不需要再检查“内部”（内部为空）。

	场景 3：子串长度为 3 ( j - i == 2 )
		此时 j - i = 2，满足 < 3。例如 "aba"：只要首尾相等，它就是回文，因为“内部”只有一个字符，单字符必定是回文。

2. 为什么是 3？
	当 j - i >= 3 时（即子串长度>=4），去掉首尾后，内部至少还剩下 2 个字符。
	例如 "abba"：j - i = 3。此时必须检查内部的 "bb" 是否为回文，即依赖 isPal[i+1][j-1]。

3. 如果不写 < 3，代码会变繁琐：

    if (j == i) {
        isPal[i][j] = true;
    } else if (j - i == 1) {
        isPal[i][j] = true;
    } else {
        isPal[i][j] = isPal[i + 1][j - 1];
    }
}
