616. Add Bold Tag in String - Medium

You are given a string s and an array of strings words.

You should add a closed pair of bold tag <b> and </b> to wrap the substrings in s that exist in words.

If two such substrings overlap, you should wrap them together with only one pair of closed bold-tag.
If two substrings wrapped by bold tags are consecutive, you should combine them.
Return s after adding the bold tags.

 

Example 1:

Input: s = "abcxyz123", words = ["abc","123"]
Output: "<b>abc</b>xyz<b>123</b>"
Explanation: The two strings of words are substrings of s as following: "abcxyz123".
We add <b> before each substring and </b> after each substring.
Example 2:

Input: s = "aaabbb", words = ["aa","b"]
Output: "<b>aaabbb</b>"
Explanation: 
"aa" appears as a substring two times: "aaabbb" and "aaabbb".
"b" appears as a substring three times: "aaabbb", "aaabbb", and "aaabbb".
We add <b> before each substring and </b> after each substring: "<b>a<b>a</b>a</b><b>b</b><b>b</b><b>b</b>".
Since the first two <b>'s overlap, we merge them: "<b>aaa</b><b>b</b><b>b</b><b>b</b>".
Since now the four <b>'s are consecutive, we merge them: "<b>aaabbb</b>".
 

Constraints:

1 <= s.length <= 1000
0 <= words.length <= 100
1 <= words[i].length <= 1000
s and words[i] consist of English letters and digits.
All the values of words are unique.
 

Note: This question is the same as 758. Bold Words in String.


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

Boolean Masking & Interval Merging

1. Mark Bold Positions: Use a boolean array isBold of size len(s). For each position i in s, check if any word in words starts at that position. If it does, mark all indices from i to i + len(word) - 1 as true.
2. Optimized Marking: Instead of re-marking every index for every word, maintain a boldEnd pointer to track the furthest bolded position reached so far. This effectively merges overlapping intervals as you scan.
3. Construct Final String: Iterate through the boolean array.
Insert <b> when you transition from a non-bold character to a bold one.
Insert </b> when you transition from a bold character to a non-bold one (or reach the end of the string). 


Stats:

	- 
	- 

class Solution {
    public String addBoldTag(String s, String[] words) {
        int n = s.length();
        boolean[] isBold = new boolean[n];
        int boldEnd = -1;

        // Step 1: Identify all bold characters
        for (int i = 0; i < n; i++) {
            for (String word : words) {
                if (s.startsWith(word, i)) {
                    boldEnd = Math.max(boldEnd, i + word.length());
                }
            }
            isBold[i] = boldEnd > i;
        }

        // Step 2: Build the resulting string with tags
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (isBold[i] && (i == 0 || !isBold[i - 1])) {
                sb.append("<b>");
            }
            sb.append(s.charAt(i));
            if (isBold[i] && (i == n - 1 || !isBold[i + 1])) {
                sb.append("</b>");
            }
        }
        return sb.toString();
    }
}


class Solution:
    def addBoldTag(self, s: str, words: list[str]) -> str:
        n = len(s)
        is_bold = [False] * n
        bold_end = -1
        
        # Step 1: Mark character positions that should be bold
        for i in range(n):
            for word in words:
                if s.startswith(word, i):
                    bold_end = max(bold_end, i + len(word))
            is_bold[i] = bold_end > i
            
        # Step 2: Traverse and insert tags at boundaries
        res = []
        for i in range(n):
            if is_bold[i] and (i == 0 or not is_bold[i-1]):
                res.append("<b>")
            res.append(s[i])
            if is_bold[i] and (i == n - 1 or not is_bold[i+1]):
                res.append("</b>")
                
        return "".join(res)




