340. Longest Substring with At Most K Distinct Characters 最多有K个不同字符的最长子串

https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/

Given a string, find the length of the longest substring T that contains at most k distinct characters.

For example, Given s = “eceba” and k = 2,

T is "ece" which its length is 3.

******************************************************
key:
	- hashmap 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- hashmap 
	- 

stats:

	- 
	- 


public class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) {
            return 0;
        }
        
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int count = 0;
        int start = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);

                // if exceed k distinct char, then: 
                // 1. remove the first char from the map, start ++ 
                // 2. count-- to reduce the window size
                while (map.size() > k) {
                    char rm = s.charAt(start);
                    int tempCount = map.get(rm);
                    if (tempCount > 1) {
                        map.put(rm, tempCount - 1);
                    } else {
                        map.remove(rm);
                    }
                    start++;
                    count--;
                }
            }
            count++;
            max = Math.max(max, count);
        }
        return max;
    }
}


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



