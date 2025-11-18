291. Word Pattern II - Medium

Given a pattern and a string s, return true if s matches the pattern.

A string s matches a pattern if there is some bijective mapping of single characters to 
non-empty strings such that if each character in pattern is replaced by the string it maps 
to, then the resulting string is s. A bijective mapping means that no two characters map to 
the same string, and no character maps to two different strings.

 

Example 1:

Input: pattern = "abab", s = "redblueredblue"
Output: true
Explanation: One possible mapping is as follows:
'a' -> "red"
'b' -> "blue"

Example 2:

Input: pattern = "aaaa", s = "asdasdasdasd"
Output: true
Explanation: One possible mapping is as follows:
'a' -> "asd"

Example 3:

Input: pattern = "aabb", s = "xyzabcxzyabc"
Output: false
 

Constraints:

1 <= pattern.length, s.length <= 20
pattern and s consist of only lowercase English letters.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************

ood-常见题-学习指导


===================================================================================================
Method 1:

Method:

	-	Instead of using HashMap, I use a String array to store the character --> String mapping
    - early fail

pattern = "abca"
str = "xxxyyzzxxx"
Suppose we have successfully matched a to xxx and b to yy, and now we are at the third character of pattern, i.e., character c. We have not mapped c to anything, so we could try any of the following:

z
zz
zzx
zzxx
zzxxx
But do we really need to try them all? The answer is NO. Because we have already mapped a and b, and under this constraint, we have to save enough space for the fourth character of pattern, i.e., a. In other words, we can at most try z and zz, otherwise we are doomed to return false when we reach the fourth character a. This is what endPoint is about in the code.


Stats:

	- 
	- 

public boolean wordPatternMatch(String pattern, String str) {
    String[] map = new String[26]; // mapping of characters 'a' - 'z'
    HashSet<String> set = new HashSet<>(); // mapped result of 'a' - 'z'
    return wordPatternMatch(pattern, str, map, set, 0, str.length()-1, 0, pattern.length()-1);
}

private boolean wordPatternMatch(String pattern, String str, String[] map, HashSet<String> set, int start, int end, int startP, int endP) {

	// both pattern and str are exhausted
	if(startP==endP+1 && start==end+1) 
		return true; 

	// either of pattern or str is exhausted
	if((startP>endP && start<=end) || (startP<endP && start>end)) return false; 

	char ch = pattern.charAt(startP);
	String matched = map[ch-'a'];

	// ch is already mapped, then continue
	if(matched!=null) { 

		int count = matched.length();

		// substring equals previously mapped string
		return start+count<=end+1 && matched.equals(str.substring(start, start+count)) 
				&& wordPatternMatch(pattern, str, map, set, start+matched.length(), end, startP+1, endP); // moving forward
	}

	else {
		
	    int endPoint = end;
	    for(int i=endP; i>startP; i--) {
	        endPoint -= map[pattern.charAt(i)-'a']==null ? 1 : map[pattern.charAt(i)-'a'].length();
	    }

		for(int i=start; i<=endPoint; i++) { // try every possible mapping, from 1 character to the end
			matched = str.substring(start, i+1);
			if(set.contains(matched)) continue; // different pattern cannot map to same substring

			map[ch-'a'] = matched; // move forward, add corresponding mapping and set content
			set.add(matched);

			if(wordPatternMatch(pattern, str, map, set, i+1, end, startP+1, endP)) return true;

			else { // backtracking, remove corresponding mapping and set content
				map[ch-'a'] = null;
				set.remove(matched);
			}
		}
	}
	return false; // exhausted
}









