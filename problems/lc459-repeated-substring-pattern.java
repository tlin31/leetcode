459. Repeated Substring Pattern - Easy


Given a non-empty string check if it can be constructed by taking a substring of it and appending
multiple copies of the substring together. You may assume the given string consists of lowercase 
English letters only and its length will not exceed 10000.


Example 1:

Input: "abab"
Output: True
Explanation: Its the substring "ab" twice.



Example 2:

Input: "aba"
Output: False



Example 3:

Input: "abcabcabcabc"
Output: True
Explanation: Its the substring "abc" four times. (And the substring "abcabc" twice.)

******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************

KMP:

class Solution {

    public boolean repeatedSubstringPattern(String s) {
        if (s.isEmpty()) return false;
        int len = s.length();

        // 第一步：构建 next 数组（预处理模式串）
        int[] next = buildNext(s);

        int repeat = next[len-1];
        
        if(next[len - 1] != 0 && len % (len - next[len - 1]) == 0) return true;
        
        return false;

    }

    /**
     * 构建 next 数组的核心逻辑
     * 理解点：寻找每个子串 [0...i] 的“最长相等前后缀长度”
     */
    private static int[] buildNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        int j = 0; // j 代表当前最长公共前后缀的长度 

        // 下标 i 从 1 开始，因为单个字符没有前后缀
        for (int i = 1; i < n; i++) {
            // 如果不匹配，j 回退到之前的对称位置
            // 这是 KMP 最精妙的地方，利用已知的 next 结果递归回退
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }

            // 如果字符相等，说明最长前后缀长度增加了
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            // 记录当前位置的长度
            next[i] = j;
        }
        return next;
    }
}


=======================================================================================================
String Concatenation

Time complexity: O(n).
Space complexity: O(n).

If a string can be constructed by taking a substring of it and appending multiple copies 
of the substring together, it must be a rotation of itself. However, it would be inefficient 
to check all rotations.



class Solution {
    public boolean repeatedSubstringPattern(String s) {
        String t = s + s;
        if (t.substring(1, t.length() - 1).contains(s))
            return true;

        return false;
    }
}





Method 1:


Stats:

Time complexity: O(n⋅ sqrt(n) ).
Space complexity: O(n).	


Method:

	- The length of the repeating substring must be a divisor of the length of the input string
	- Search for all possible divisor of str.length, starting for length/2
	- If i is a divisor of length, repeat the substring from 0 to i the number of times i is contained 
	  in s.length
	- If the repeated substring is equals to the input str return true





class Solution {
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) {
                StringBuilder pattern = new StringBuilder();
                for (int j = 0; j < n / i; j++) {
                    pattern.append(s.substring(0, i));
                }
                if (s.equals(pattern.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

The maximum length of a "repeated" substring that you could get from a string would be half its length
For example, s = "abcdabcd", "abcd" of len = 4, is the repeated substring.
You cannot have a substring >(len(s)/2), that can be repeated.

So, when ss = s + s , we will have at least 4 parts of "repeated substring" in ss.
(s+s)[1:-1] --> removing 1st char and last char => Out of 4 parts of repeated substring, 
			    2 part will be gone (they will no longer have the same substring).
ss.find(s) != -1, But still we have 2 parts out of which we can make s. And that is how ss should 
	have s, if s has repeated substring.


def repeatedSubstringPattern(self, str):

        """
        :type str: str
        :rtype: bool
        """
        if not str:
            return False
            
        ss = (str + str)[1:-1]
        return ss.find(str) != -1

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	






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

