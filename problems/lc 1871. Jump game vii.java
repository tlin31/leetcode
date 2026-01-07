
Code


Testcase
Testcase
Test Result
1871. Jump Game VII - Medium

You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning, you are standing at index 0, which is equal to '0'. You can move from index i to index j if the following conditions are fulfilled:

i + minJump <= j <= min(i + maxJump, s.length - 1), and
s[j] == '0'.
Return true if you can reach index s.length - 1 in s, or false otherwise.

 

Example 1:

Input: s = "011010", minJump = 2, maxJump = 3
Output: true
Explanation:
In the first step, move from index 0 to index 3. 
In the second step, move from index 3 to index 5.
Example 2:

Input: s = "01101110", minJump = 2, maxJump = 3
Output: false
 

Constraints:

2 <= s.length <= 105
s[i] is either '0' or '1'.
s[0] == '0'
1 <= minJump <= maxJump < s.length


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



/** DP: 
    dp[i] = 是否能到达位置 i
    初始状态: dp[0] = true
    状态转移（暴力）dp[i] = true if
                    s[i] == '0' 且
                    存在 j ∈ [i-maxJump, i-minJump] 使 dp[j] == true
滑动窗口解法（最清晰）
    维护一个变量：
    window = 当前窗口内 dp[j] == true 的个数

    当窗口里有 ≥1 个 true，当前位置就可达。
**/

class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        boolean[] dp = new boolean[n];
        dp[0] = true;

        int window = 0;

        for (int i = 1; i < n; i++) {

            // 扩展窗口右边,添加上新增的i-minjump
            if (i - minJump >= 0 && dp[i - minJump]) {
                window++;
            }

            // 收缩窗口左边，不要最开头的 i-minJump位置的
            if (i - maxJump - 1 >= 0 && dp[i - maxJump - 1]) {
                window--;
            }

            // 判断当前位置
            if (window > 0 && s.charAt(i) == '0') {
                dp[i] = true;
            }
        }

        return dp[n - 1];
    }
}
