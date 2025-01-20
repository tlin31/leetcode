242. Valid Anagram - Easy

Given two strings s and t , write a function to determine if t is an anagram of s.

Example 1:

Input: s = "anagram", t = "nagaram"
Output: true
Example 2:

Input: s = "rat", t = "car"
Output: false
Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?

******************************************************
key:
	- count array or hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- Runtime: 4 ms, faster than 78.39% of Java online submissions for Valid Anagram.
	- Memory Usage: 35.8 MB, less than 98.06%



class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length()!= t.length()) return false;

        int[] map = new int[26];

        for(char c:s.toCharArray()){
            map[c-'a']++;
        }

        for(char c:t.toCharArray()){
            if(map[c-'a']==0) return false;
            else map[c-'a']--;
        }

        for(int value:map){
            if(value !=0) return false;
        }
        return true;
    }
}

=======================================================================================================
method 2:

method:

	- sort
	- 

stats:

	- Runtime: 5 ms, faster than 64.93% of Java online submissions for Valid Anagram.
	- Memory Usage: 37 MB, less than 97.42%

public boolean isAnagram(String s, String t) 
{
    char[] sChar = s.toCharArray();
    char[] tChar = t.toCharArray();
    
    Arrays.sort(sChar);
    Arrays.sort(tChar);
    
    return Arrays.equals(sChar, tChar);   
}

=======================================================================================================
method 3 (follow up):

method:

	In Java, a Unicode could be represented by a single char(BMP, Basic Multilingual Plane) or 
        two chars (high surrogate). 
    use String.codePointAt(int index) method to get the integer representation of a Unicode (as the 
        key in the hash table) and use Character.charCount(int code) to count how many the characters 
        are used there (to correctly increase our index)


stats:

	- 
	- 



public class Solution {
    public boolean isAnagram(String s, String t) {
        if (s==null && t==null) return true;
        else if (s==null || t==null) return false;
        else if (s.length() != t.length()) return false;
        
        Map<Integer, Integer> dict = new HashMap<>();
        int index = 0;
        while(index < s.length()) {
            int charCode = s.codePointAt(index); // Get the integer representation of Unicode 
            dict.put(charCode, dict.getOrDefault(charCode, 0) + 1);
            index += Character.charCount(charCode); // The Unicode could be represented by either one char or two chars
        }
        
        index = 0;
        while(index < t.length()) {
            int charCode = t.codePointAt(index);
            int count = dict.getOrDefault(charCode, 0);
            
            if (count == 0) return false;
            else dict.put(charCode, count - 1);
            
            index += Character.charCount(charCode);
        }
        
        return true;
    }
}

