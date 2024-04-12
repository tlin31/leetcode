28. Implement strStr() --- Easy

Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part 
of haystack.

Example 1:
Input: haystack = "hello", needle = "ll"
Output: 2


Example 2:
Input: haystack = "aaaaa", needle = "bba"
Output: -1

Clarification:

What should we return when needle is an empty string? This is a great question to ask during an
interview.

	- For the purpose of this problem, we will return 0 when needle is an empty string. This is 
	  consistent to C's strstr() and Java's indexOf().
=======================================================================================================
method 1:

key:
    - edge case: if needle is longer than the haystack, or if there is no needle
    - don† need †ø check all, just need ø check l1-l2
// Runtime: 0 ms, faster than 100.00% of Java online submissions for Implement strStr().
// Memory Usage: 37.8 MB, less than 67.17%
public class Solution {
    public int strStr(String haystack, String needle) {
        int l1 = haystack.length(), 
            l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }


        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; ++i) {
            if (haystack.substring(i, i + l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
}

=======================================================================================================
method 2:

key:

// Runtime: 2 ms, faster than 52.69% of Java online submissions for Implement strStr().
// Memory Usage: 36.8 MB, less than 100.00% 
    public int strStr(String haystack, String needle) {
        // empty needle appears everywhere, first appears at 0 index
        if (needle.length() == 0)
            return 0;
        if (haystack.length() == 0)
            return -1;
        
        
        for (int i = 0; i < haystack.length(); i++) {
            // no enough places for needle after i
            if (i + needle.length() > haystack.length()) break;
            
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i+j) != needle.charAt(j))
                    break;
                if (j == needle.length()-1)
                    return i;
            }
        }
        
        return -1;
    }


    public int strStr(String s, String t) {
        if (t.isEmpty()) return 0; // edge case: "",""=>0  "a",""=>0
        for (int i = 0; i <= s.length() - t.length(); i++) {
            for (int j = 0; j < t.length() && s.charAt(i + j) == t.charAt(j); j++)
                if (j == t.length() - 1) return i;
        }
        return -1;
    }