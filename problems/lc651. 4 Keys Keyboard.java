651. 4 Keys Keyboard - Medium

Imagine you have a special keyboard with the following keys:

A: Print one 'A' on the screen.
Ctrl-A: Select the whole screen.
Ctrl-C: Copy selection to buffer.
Ctrl-V: Print buffer on screen appending it after what has already been printed.
Given an integer n, return the maximum number of 'A' you can print on the screen with at most n presses on the keys.

 

Example 1:

Input: n = 3
Output: 3
Explanation: We can at most get 3 A's on screen by pressing the following key sequence:
A, A, A
Example 2:

Input: n = 7
Output: 9
Explanation: We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
 

Constraints:

1 <= n <= 50

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

记 dp[i] 为恰好按 i 次键能得到的最多 A 个数。

两类策略：

1、直接按 A：从 i-1 扩展，dp[i] = dp[i-1] + 1（一直按 A）。

2、在某一步 j（1 ≤ j ≤ i-3）做一次 Ctrl-A、一次 Ctrl-C（占两步），然后用剩下的 (i - j - 2) 步做多次 Ctrl-V。

在步骤 j 时已有 dp[j] 个 A，被复制到剪贴板；

剩余的 r = i - j - 2 次 Ctrl-V 会把 dp[j] 追加 r 次，最终总量为 dp[j] * (r + 1) = dp[j] * (i - j - 1)。

所以状态转移为：

dp[i] = max(dp[i-1] + 1, max_{j=1..i-3} dp[j] * (i - j - 1))

（解释：若 j 选得好，就在 j 后做 Ctrl-A, Ctrl-C，然后连续 Ctrl-V）

Stats:

	- 时间复杂度：O(N^2)（外层 i，内层枚举 j）

		空间复杂度：O(N)
	- 


public class Solution {
    public int maxA(int N) {
        if (N <= 0) return 0;
        int[] dp = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            // option 1: press 'A'
            dp[i] = dp[i - 1] + 1;
            // option 2: choose a breakpoint j to do Ctrl-A, Ctrl-C, then many Ctrl-V
            // need at least 3 keys after j: Ctrl-A, Ctrl-C, Ctrl-V
            for (int j = 1; j <= i - 3; j++) {
                int timesOfPastes = i - j - 2; // number of Ctrl-V
                int candidate = dp[j] * (timesOfPastes + 1); // dp[j] * (i - j - 1)
                dp[i] = Math.max(dp[i], candidate);

            }
        }
        return dp[N];
    }

    // For quick test
    public static void main(String[] args) {
        Solution s = new Solution();
        for (int i = 1; i <= 20; i++) {
            System.out.printf("N=%d -> %d\n", i, s.maxA(i));
        }
    }
}




