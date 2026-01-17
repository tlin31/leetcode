214. Shortest Palindrome - Hard


Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. 
Find and return the shortest palindrome you can find by performing this transformation.

Example 1:

Input: "aacecaaa"
Output: "aaacecaaa"

Example 2:

Input: "abcd"
Output: "dcbabcd"


******************************************************
key:
	- KMP, REDO
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
一、题目一句话理解（先定方向）

给你一个字符串 s，只能在字符串前面添加字符，
让最终字符串变成 回文串，并且 添加字符数量最少。

二、核心转化（这一步是关键）
👉 等价问题

找到 s 的 最长回文前缀
剩下的后缀 反转后加到最前面

举个直观例子
s = "aacecaaa"


最长回文前缀是：

"aacecaa"


剩下：

"a"


反转加前面：

"a" + "aacecaaa" = "aaacecaaa"

三、难点在哪？

❌ 暴力判断每个前缀是否回文 → O(n²)
✅ KMP 一次扫描搞定 → O(n)

四、KMP 的“魔改用法”（灵魂）
关键构造
t = s + "#" + reverse(s)

为什么这样构造？

next[t.length - 1]

表示：
👉 s 的 最长前缀
👉 等于 reverse(s) 的 后缀
👉 等价于 s 的最长回文前缀


class Solution {
    public String shortestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) return s;

        String rev = new StringBuilder(s).reverse().toString();
        String t = s + "#" + rev;

        int[] next = buildNext(t);
        int len = next[t.length() - 1];

        // 非回文部分
        String add = s.substring(len);
        return new StringBuilder(add).reverse().toString() + s;
    }

    // KMP next / lps 数组
    private int[] buildNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        int j = 0;

        for (int i = 1; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: brute force

Stats:

	- 
	- 


Method:

	-	
	-  find the longest palindrome starting from the first character, and then reverse the remaining 
	   part as the prefix to s. 


public String shortestPalindrome(String s) {
    int i = 0, 
    	j = end,
    	end = s.length() - 1; 
    char chs[] = s.toCharArray();

    while(i < j) {
         if (chs[i] == chs[j]) {
             i++; 
             j--;
         } else { 
             i = 0; 
             end--; 
             j = end;
         }
    }
    return new StringBuilder(s.substring(end+1)).reverse().toString() + s;
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

