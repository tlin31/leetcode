567. Permutation in String - Medium

Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
In other words, one of the first string permutations is the substring of the second string.


Example 1:

Input: s1 = "ab" s2 = "eidbaooo"
Output: True
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input:s1= "ab" s2 = "eidboaoo"
Output: False
 

Note:

The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].

******************************************************
key:
	- sliding window
	- can try with sorting, hashmap, etc.
	- key when knowing whether it is a permutation: check all characters, so ab and aob will be different
	- edge case:
		1) empty s1 or s2, return true.
		2) if s1.length > s2.length, return false

******************************************************



=======================================================================================================
method 1:

method:

	- Hashmap
	- As discussed above, one string will be a permutation of another string only if both of them 
		contain the same charaters with the same frequency. We can consider every possible substring 
		in the long string s2 of the same length as that of s1 and check the frequency of occurence 
		of the characters appearing in the two. 

		If the frequencies of every letter match exactly, then only s1 permutation can be a substring 
		of s2

	- s1map which stores the frequency of occurence of all the characters in the short string s1. 
		We consider every possible substring of s2 of the same length as that of s1, find its 
		corresponding hashmap as well, namely s2map. Thus, the substrings considered can be viewed 
		as a window of length as that of s1 iterating over s2. 

		If the two hashmaps obtained are identical for any such window, we can conclude that s1 
		permutation is a substring of s2s2, otherwise not.

stats:

	- Time complexity : O(l1 + 26 * l1 * (l2-l1)), hashmap contains at most 26 keys. where l1 
		is the length of string l1 and l2 is the length of string 2

	- Space complexity : O(1). hashmap contains atmost 26 key-value pairs.



public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;

        HashMap < Character, Integer > s1map = new HashMap < > ();

        for (int i = 0; i < s1.length(); i++)
            s1map.put(s1.charAt(i), s1map.getOrDefault(s1.charAt(i), 0) + 1);

        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            HashMap < Character, Integer > s2map = new HashMap < > ();

            for (int j = 0; j < s1.length(); j++) {
                s2map.put(s2.charAt(i + j), s2map.getOrDefault(s2.charAt(i + j), 0) + 1);
            }

            if (matches(s1map, s2map))
                return true;
        }
        return false;
    }

    public boolean matches(HashMap < Character, Integer > s1map, 
    	HashMap < Character, Integer > s2map) {
        for (char key: s1map.keySet()) {
            if (s1map.get(key) - s2map.getOrDefault(key, -1) != 0)
                return false;
        }
        return true;
    }
}



=======================================================================================================
method 2:

method:

	- improve from hash map --> use array
	- use simpler array data structure to store the frequencies. Given strings contains only lowercase 
		alphabets ('a' to 'z'). So we need to take an array of size 26.The rest of the process remains 
		the same as the last approach.



stats:

	- same as method 1
	- 

    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length())
            return false;

        int[] s1map = new int[26];

        for (int i = 0; i < s1.length(); i++)
            s1map[s1.charAt(i) - 'a']++;

        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] s2map = new int[26];
            for (int j = 0; j < s1.length(); j++) {
                s2map[s2.charAt(i + j) - 'a']++;
            }
            if (matches(s1map, s2map))
                return true;
        }
        return false;
    }
    public boolean matches(int[] s1map, int[] s2map) {
        for (int i = 0; i < 26; i++) {
            if (s1map[i] != s2map[i])
                return false;
        }
        return true;
    }



=======================================================================================================
method 3:

method:

	- we can create the hashmap just once for the first window in s2. Then, later on when we slide 
		the window, we know that we add one preceding character and add a new succeeding character 
		to the new window considered. 

	- Thus, we can update the hashmap by just updating the indices associated with those two 
		characters only. Again, for every updated hashmap, we compare all the elements of the hashmap 
		for equality to get the required result.
	- 

stats:

	- Time complexity : O(l1+26*(l2-l1))
	- Space complexity : O(1). Constant space is used.
	- Runtime: 7 ms, faster than 59.90% of Java online submissions for Permutation in String.
	- Memory Usage: 36 MB, less than 100.00%

public boolean checkInclusion(String s1, String s2) {
    int len1 = s1.length(), len2 = s2.length();
    if (len1 > len2) return false;
    
    int[] count = new int[26];
    for (int i = 0; i < len1; i++) {
        count[s1.charAt(i) - 'a']++;
    }
    
    for (int i = 0; i < len2; i++) {
        count[s2.charAt(i) - 'a']--;
        if(i - len1 >= 0) 
        	count[s2.charAt(i - len1) - 'a']++;
        if (allZero(count)) return true;
    }
    
    return false;
}

private boolean allZero(int[] count) {
    for (int i = 0; i < 26; i++) {
        if (count[i] != 0) return false;
    }
    return true;
}


ex. s1 = ab, s2 = cba

Count: a    b    c    d
init:  1	1	 0	  0

i=0	   1    1    -1   0
i=1    1    0    -1   0
i=2    0    0    -1   0
(if)   0    0    0    0 --> solution!!!
	


-----
Sliding window:

Algo

    - how do we know string p is a permutation of string s? Easy, each character in p is in s too. 
        So we can abstract all permutation strings of s to a map (Character -> Count). 
        i.e. abba -> {a:2, b:2}. Since there are only 26 lower case letters in this problem, we can 
        just use an array to represent the map.
    - How do we know string s2 contains a permutation of s1? We just need to create a sliding window 
        with length of s1, move from beginning to the end of s2. 

        When a character moves in from right of the window, we subtract 1 to that character count 
        from the map. 

        When a character moves out from left of the window, we add 1 to that character count.

        So once we see all zeros in the map, meaning equal numbers of every characters between s1 and 
        the substring in the sliding window, we know the answer is true.

public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) return false;
        
        int[] count = new int[26];
        for (int i = 0; i < len1; i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        if (allZero(count)) return true;
        
        for (int i = len1; i < len2; i++) {
            count[s2.charAt(i) - 'a']--;
            count[s2.charAt(i - len1) - 'a']++;
            if (allZero(count)) return true;
        }
        
        return false;
    }
    
    private boolean allZero(int[] count) {
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
}




--------


 public boolean checkInclusion(String s1, String s2) {
        if(s1 == null || s2 == null) {
            return false;
        }
        int len = s1.length();
        Map<Character, Integer> map = new HashMap<>();
        for(char c : s1.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int count = map.size();
        int begin = 0;
        int end = 0;

        while(end < s2.length()) {
            char ch = s2.charAt(end);
            if(map.containsKey(ch)) {
                map.put(ch, map.get(ch) - 1);
                if(map.get(ch) == 0) {
                    count--;
                }
            }

            while(count == 0) {

            	// when our window size = length of s1, and all chars match
                if(end - begin + 1 == len) {
                    return true;
                }

                char temp = s2.charAt(begin);

                if(map.containsKey(temp)) {
                    map.put(temp, map.get(temp) + 1);
                    if(map.get(temp) > 0) {
                        count++;
                    }
                }
                // move window forward
                begin++;
            }
            end++;
        }
        return false;
    }

