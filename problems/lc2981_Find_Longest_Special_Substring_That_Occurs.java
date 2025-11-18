2981. Find Longest Special Substring That Occurs Thrice I - Medium

You are given a string s that consists of lowercase English letters.

A string is called special if it is made up of only a single character. For example, the 
string "abc" is not special, whereas the strings "ddd", "zz", and "f" are special.

Return the length of the longest special substring of s which occurs at least thrice, or -1 
if no special substring occurs at least thrice.

A substring is a contiguous non-empty sequence of characters within a string.

 

Example 1:

Input: s = "aaaa"
Output: 2
Explanation: The longest special substring which occurs thrice is "aa": substrings "aaaa", 
"aaaa", and "aaaa".
It can be shown that the maximum length achievable is 2.

Example 2:

Input: s = "abcdef"
Output: -1
Explanation: There exists no special substring which occurs at least thrice. Hence return -1.

Example 3:

Input: s = "abcaba"
Output: 1
Explanation: The longest special substring which occurs thrice is "a": substrings "abcaba", "abcaba", and "abcaba".
It can be shown that the maximum length achievable is 1.
 

Constraints:

3 <= s.length <= 50
s consists of only lowercase English letters.



******************************************************
key:
	- 注意constraints！因为很小，所以O（n^2）可以接受
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
public int maximumLength(String s) {
	int res = -1;

	char[] arr = s.toCharArray();
	Map<String, Integer> map = new HashMap<>();

	for(int i=0; i<arr.length; i++){
		for(int j=i; j<arr.length; j++){
			String curr = s.substring(i, j+1);
			map.put(curr, map.getOrDefault(curr, 0)+1);
		}
	}

	for(String str : map.keySet()){
		if(map.get(str) >= 3){
			boolean flag = true;

			char ch = str.charAt(0);
			for(int i=1; i<str.length()&&flag; i++){
				if(str.charAt(i) != ch) 
					flag = false;
			}
			if(flag) 
				res = Math.max(res, str.length());
		}
	}

	return res;
}


class Solution {
    public int maximumLength(String s) {
        // maps a character and a character's substring to a number of occurences.
        int[][] counts = new int[26][s.length() + 1];
        int n = s.length(), max = 0;
        for (int i = 0; i < n; i++) {
            int j = i;
            char c = s.charAt(i);
            //找到最长的字符相同的substr
            while (j < n - 1 && c == s.charAt(j + 1)) {
                j++;
            }
            int windowSize = j - i + 1;

            // count window for len，a为出现的相同字符长度
            for (int a = 1; a <= windowSize; a++) {
                // 枚举，number of substrings of len A for a string s(i, j + 1)
                int numOfSubstrings = windowSize - a + 1;
                int count = counts[c - 'a'][a]  + numOfSubstrings;
                counts[c - 'a'][a] = count;
                if (count >= 3) {
                    max = Math.max(max, a);
                }
            }
            i = j;
        }

        return max != 0 ? max : -1;
    }
}








