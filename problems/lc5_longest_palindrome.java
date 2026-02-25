5. Longest Palindromic Substring - Medium

Given a string s, find the longest palindromic substring in s. You may assume that the maximum length 
of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.


Example 2:
Input: "cbbd"
Output: "bb"

******************************************************
key:
    - DP, store results, go from end of the string for (i = n, i--), then for (j = i, j<n, j++)
    - sliding window / 2 pointers
    - edge case:
        1) empty string, return empty
        2)

******************************************************

dp：用len 解

class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;

        boolean[][] dp = new boolean[n][n];
        int maxLen = 1;
        int begin = 0;

        // 初始化：所有长度为 1 的子串都是回文
        for (int i = 0; i < n; i++) 
            dp[i][i] = true;

        // L 为子串长度，从 2 开始遍历到 n
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i < n; i++) {
                int j = L + i - 1; // 结束索引
                if (j >= n) break;

                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
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




=========================================================================================================================
method 1:

    - dp(i, j) represents whether s(i ... j) can form a palindromic substring, 

        Formula:
            dp(i, j) is true if s(i) equals to s(j) && s(i+1 ... j-1) is a palindromic substring.

            Note: j-i < 3 meaning there is less than 1 char in between i and j 

    - When we found a palindrome, check if it is the longest one.

Time:
    - O(n^2)


    public String longestPalindrome(String s) {
      int n = s.length();
      String res = null;
        
      boolean[][] dp = new boolean[n][n];
        
      for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
          dp[i][j] = (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]));
        
          // update res
          if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
            res = s.substring(i, j + 1);
          }
        }
      }
        
      return res;
    }

babad
01234

i = 4, j = 4, dp[5][3]
i = 3, j = 3, 4

i = 1, j = 1, 2, 3, 4 --> for dp[1][3] = dp[2][2]

=========================================================================================================================

method 2:

    - 2 pointers
    回文串是左右对称的。我们可以遍历字符串，将每个位置（或两个字符中间的空隙）视为回文的“中心”，尝试向左右两边扩散。
    奇数长度回文：中心是一个字符（如 aba，中心是 b）。
    偶数长度回文：中心是两个字符之间的空隙（如 abba，中心是 bb 之间的位置）。

时间复杂度：O(N^2)。虽然也是双重循环，但由于回文串通常不会特别长，在实际测试中（LeetCode 提交）
它往往比动态规划快得多。
空间复杂度：O(1)。我们只使用了几个变量来记录位置，不需要额外的矩阵存储状态。


class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0; // 记录最长回文串的起始位置
        int end = 0;   // 记录最长回文串的结束位置

        for (int i = 0; i < s.length(); i++) {
            // 情况 1: 奇数回文，中心是 s[i]
            int len1 = expandAroundCenter(s, i, i);
            
            // 情况 2: 偶数回文，中心是 s[i] 和 s[i+1] 之间
            int len2 = expandAroundCenter(s, i, i + 1);
            
            // 获取当前中心扩散出的最长长度
            int maxLen = Math.max(len1, len2);
            
            // 如果比之前记录的更长，则更新坐标
            if (maxLen > end - start) {
                // 计算新的起始和结束索引
                // 这里利用了整数除法的特性巧妙处理了奇偶情况
                start = i - (maxLen - 1) / 2;
                end = i + maxLen / 2;
            }
        }
        
        // substring 在 Java 中是左闭右开，所以需要 end + 1
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        // 当左右字符相等时，继续向外扩散
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 循环跳出时，s[left] != s[right]，回文长度为 (right-1) - (left+1) + 1
        // 简化后即为 right - left - 1
        return right - left - 1;
    }
}



为什么 start = i - (maxLen - 1) / 2 既能处理奇数也能处理偶数？
奇数：len=5, i=2 --> start = 2 - 4/2 = 0。
偶数：len=4, i=1 --> start = 1 - 3/2 = 0。这种写法能让你的代码看起来非常精炼，体现你对边界处理的思考。




=========================================================================================================================
method 3:




Manacher’s Algorithm – Linear Time Longest Palindromic Substring
- point: linear time solution

它通过利用已有的回文对称性，将时间复杂度降低到惊人的 O(N)。
核心思想：
    预处理：在字符间插入 #（如 aba -> #a#b#a#），统一奇偶长度。
    回文半径数组 P[i]：记录以 i 为中心的最长回文半径。
        例如 #a#b#a# 中，中心 b 的半径是 4（#a#b）。
        关键点：P[i] - 1 正好是原始字符串中回文的长度。

    C (Center)：当前探测到的、右边界最远的回文串的中心。
    R (Right)：当前最远回文串的右边界 (\(R=C+P[C]\))。
    右边界 R 和中心 C：利用之前计算过的回文信息，跳过不必要的比较。

public String longestPalindrome(String s) {
    // 1. 预处理
    StringBuilder sb = new StringBuilder("$#"); // $ 防止越界
    for (char c : s.toCharArray()) {
        sb.append(c).append("#");
    }
    String T = sb.toString();
    
    int n = T.length();
    int[] P = new int[n];
    int C = 0, R = 0;
    
    int maxLen = 0, centerIndex = 0;

    for (int i = 1; i < n; i++) {
        // 2. 利用对称性初始化 P[i]
        if (i < R) {
            P[i] = Math.min(R - i, P[2 * C - i]);
        }
        
        // 3. 暴力扩散（只会从 R 之后开始比较）
        while (i + 1 + P[i] < n && i - 1 - P[i] >= 0 
            && T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) {
            
            P[i]++;
        }
        
        // 4. 更新 C 和 R
        if (i + P[i] > R) {
            C = i;
            R = i + P[i];
        }
        
        // 5. 记录最长
        if (P[i] > maxLen) {
            maxLen = P[i];
            centerIndex = i;
        }
    }
    
    // 6. 映射回原字符串
    int start = (centerIndex - maxLen) / 2;
    return s.substring(start, start + maxLen);
}







