2982. Find Longest Special Substring That Occurs Thrice II - Medium

You are given a string s that consists of lowercase English letters.

A string is called special if it is made up of only a single character. For example, the string 
"abc" is not special, whereas the strings "ddd", "zz", and "f" are special.

Return the length of the longest special substring of s which occurs at least thrice, or -1 
if no special substring occurs at least thrice.

A substring is a contiguous non-empty sequence of characters within a string.

 

Example 1:

Input: s = "aaaa"
Output: 2
Explanation: The longest special substring which occurs thrice is "aa": substrings "aaaa", "aaaa", 
and "aaaa".
It can be shown that the maximum length achievable is 2.

Example 2:

Input: s = "abcdef"
Output: -1
Explanation: There exists no special substring which occurs at least thrice. Hence return -1.

Example 3:

Input: s = "abcaba"
Output: 1
Explanation: The longest special substring which occurs thrice is "a": substrings "abcaba", 
"abcaba", and "abcaba".
It can be shown that the maximum length achievable is 1.
 

Constraints:

3 <= s.length <= 5 * 10^5
s consists of only lowercase English letters.


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
Time complexity: O(N)
Space complexity: O(N)


class Solution {
    public int maximumLength(String s) {
        int n = s.length();
        //字母i 的substring长度，出现了多少次
        int[][] freq = new int[26][n + 1];
        char pre = s.charAt(0);

        int len = 1;
        freq[s.charAt(0) - 'a'][1] = 1;
        int ans = -1;

        //如果当前的和之前的相同，可能是special substring
        for (int i = 1; i < n; i++) {
            char cur = s.charAt(i);
            if (cur == pre) {
                len++;
                int count = 1;
                freq[cur - 'a'][len] += 1;
            } 
            else {
            	//重新开始，reset pre和len
                freq[cur - 'a'][1] += 1;
                pre = cur;
                len = 1;
            }
        }

        for (int i = 0; i < 26; i++) {
            int preSum = 0;
            //j是special substr的长度，从后往前算，碰到的第一个就是最长的！
            for (int j = n; j >= 1; j--) {
                preSum += freq[i][j];
                if (preSum >= 3) {
                    ans = Math.max(ans, j);
                    break;
                }
            }
        }

        return ans;
    }
}









