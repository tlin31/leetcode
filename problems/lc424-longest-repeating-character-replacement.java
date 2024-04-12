424. Longest Repeating Character Replacement - Medium

Given a string s that consists of only uppercase English letters, you can perform at most k 
operations on that string.

In one operation, you can choose any character of the string and change it to any other uppercase 
English character.

Find the length of the longest sub-string containing all repeating letters you can get after 
performing the above operations.

Note:
Both the string s length and k will not exceed 104.

Example 1:

Input:
s = "ABAB", k = 2

Output:
4

Explanation:
Replace the two 'A's with two 'B's or vice versa.
 

Example 2:

Input:
s = "AABABBA", k = 1

Output:
4

Explanation:
Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.




******************************************************
key:
	- sliding window
	- edge case:
		1) k = 0, search for longest repeated string
		2) empty string, return 0
		3) string has only 1 char, return 1

******************************************************



=======================================================================================================
method 1:

method:

	- Since we are only interested in the longest valid substring, our sliding windows need not 
		shrink, even if a window may cover an invalid substring. We either grow the window by 
		appending one char on the right, or shift the whole window to the right by one. 

	- we only grow the window when the count of the new char exceeds the historical max count 
		(from a previous window that covers a valid substring).

	- we do not need the accurate max count of the current window; we only care if the max count 
		exceeds the historical max count; and that can only happen because of the new char.

	- end-start+1 = size of the current window
		maxCount = largest count of a single, unique character in the current window

	- When end-start+1-maxCount == 0, then then the window is filled with only one character
		When end-start+1-maxCount > 0, then we have characters in the window that are NOT the 
		character that occurs the most. end-start+1-maxCount is equal to exactly the # of 
		characters that are NOT the character that occurs the most in that window. 

		Example: For a window "xxxyz", end-start+1-maxCount would equal 2. (maxCount is 3 and there 
			are 2 characters here, "y" and "z" that are not "x" in the window.)

		If we have window with "xxxy" and k = 1, that is fine because end-start+1-maxCount = 1, 
			which is not > k. maxLength gets updated to 4.

		But if we then find a "z" after, like "xxxyz", then we need to shrink the window because 
			now end-start+1-maxCount = 2, and 2 > 1. The window becomes "xxyz".

	- maxCount may be invalid at some points, but this does not matter, because it was valid earlier 
		in the string, and all that matters is finding the max window that occurred anywhere in the 
		string. 

stats:

	- O(n)
	- Runtime: 4 ms, faster than 96.51% of Java online submissions for Longest Repeating Character Replacement.
	- Memory Usage: 36.1 MB, less than 100.00%


	public int characterReplacement(String s, int k) {
        int uniqueCount = 0;
        int left = 0;
        int max = 0;
        int[] count = new int[26];
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            uniqueCount = Math.max(uniqueCount, ++count[c - 'A']);
            int replaceCount = right - left + 1 - uniqueCount;

            if (replaceCount > k) {
	        	// if we don't have enough k operation to swap every elem from start to end
                count[s.charAt(left++) - 'A']--;
            } else {
            	// know the current longest repeating char
                max = Math.max(max, right - left + 1);
            }
        }
        return max;
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



