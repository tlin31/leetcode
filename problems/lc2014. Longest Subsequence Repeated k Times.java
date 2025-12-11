2014. Longest Subsequence Repeated k Times - Hard

You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k times in string s.

A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.

A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a string constructed by concatenating seq k times.

For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by concatenating "bba" 2 times, is a subsequence of the string "bababcba".
Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the lexicographically largest one. If there is no such subsequence, return an empty string.

 

Example 1:

example 1
Input: s = "letsleetcode", k = 2
Output: "let"
Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
"let" is the lexicographically largest one.
Example 2:

Input: s = "bb", k = 2
Output: "b"
Explanation: The longest subsequence repeated 2 times is "b".
Example 3:

Input: s = "ab", k = 2
Output: ""
Explanation: There is no subsequence repeated 2 times. Empty string is returned.
 

Constraints:

n == s.length
2 <= k <= 2000
2 <= n < min(2001, k * 8)
s consists of lowercase English letters.


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

1️、限定候选字符 （trim）

	如果某字符 c 在 s 中出现次数 < k
	那么它不可能出现在 t 中（因为 t 重复 k 次需要 k 次 c）。

	所以先统计频率，把不可能的字符去掉。

2️、枚举可能的字符串（按长度从大到小）

	由于 t 的长度不会超过 |s|/k
	通常最大长度很有限（比如 s 最多 26 种字符）

	可以用 BFS/DFS 枚举：按字母顺序构建候选字符串（越长越优）

	对每个候选字符串验证：它重复 k 次是否仍是 s 的子序列？

3️、 使用双指针检查子序列匹配（O(n)）

	检查：t + t + ... + t (k次) 是否是 s 的子序列。


Stats:

	枚举候选串：字符集小，因此可控

	每次检查子序列：O(n)

	总体：实际运行非常快


class Solution {
    public String longestSubsequenceRepeatedK(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray()) freq[c - 'a']++;

        // only characters that appear at least k times can be in t
        List<Character> candidates = new ArrayList<>();
        for (int i = 25; i >= 0; i--) {
            if (freq[i] >= k) {
                candidates.add((char) ('a' + i));
            }
        }

        // BFS queue for generating candidate strings
        Queue<String> queue = new LinkedList<>();
        for (char ch : candidates) {
            queue.add(String.valueOf(ch));
        }
        String best = "";

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            if (cur.length() > best.length()) {
                best = cur;
            }
            for (char c : candidates) {
                String next = cur + c;

                if (isKRepeatingSubsequence(s, next, k)) {
                    queue.offer(next);
                }
            }
        }

        return best;
    }

    private boolean isKRepeatingSubsequence(String s, String t, int k) {
        int count = 0;
        int i = 0; //当前正在匹配 t 的索引位置，取值范围 0..t.length()-1。当 i == t.length() 表示 t 整个串已经匹配完一次。

        for (char c : s.toCharArray()) {
            if (c == t.charAt(i)) {
                i++;
                if (i == t.length()) {//已经完整匹配了一次 t（即找到了 t 的一个拷贝作为子序列）。
                    i = 0;
                    count++;
                    if (count == k) return true;
                }
            }
        }

        return false;
    }
}





① DNA / 基因序列中重复模式检测

	比如生物学中常常需要检测：

	哪段 DNA 片段重复出现 ≥ k 次

	且重复部分必须顺序一致（子序列匹配）

	本题的 “t 重复 k 次仍是子序列” 与基因重复序列检测高度契合。

② 日志 / 行为序列模式挖掘

	例如：

	分析用户行为日志

	找到用户最常重复的操作模式（例如 “开屏 → 点击 → 浏览”）

	如果这个模式连续或分散出现 k 次，就是有效模式。

	完全对应本题。

③ 字符串压缩 / OCR 模式识别

	例如 OCR 识别中：

	找到重复出现的图形字符结构

	判定它是否可用于模板提取

	需要保证模板重复出现 k 次 → 与题意一致。

④ 大规模日志中的攻击行为检测

	攻击者常有固定模式（如 "login → token → attempt"）。
	若这个攻击序列重复 k 次 → 形成攻击信号。

	这题本质是找重复子序列模式。

