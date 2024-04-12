3. Longest Substring Without Repeating Characters
Medium

Given a string, find the length of the longest substring without repeating characters.

Example 1:

Input: "abcabcbb"
Output: 3 
Explanation: The answer is "abc", with the length of 3. 
Example 2:

Input: "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3. 
             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

******************************************************
key:
    - stack
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:

    - 2 pointers / sliding window with Hashmap recording position
    - keep a hashmap which stores the characters in string as keys and their positions as values, 
      keep two pointers which define the max substring.
    - move the right pointer to scan through the string , and meanwhile update the hashmap. 
    - If the character is already in the hashmap, then move the left pointer to
      the right of the same character last found. 
    - can also use an int array of size 26

stats:
    - runtime O(n)
    - Runtime: 8 ms, faster than 65.57% 


public int lengthOfLongestSubstring(String s) {
    if (s.length() == 0) 
        return 0;
    HashMap < Character, Integer > map = new HashMap < Character, Integer > ();
    int max = 0;

    // i is right pointer, j is left pointer
    for (int i = 0, j = 0; i < s.length(); ++i) {
        if (map.containsKey(s.charAt(i))) {
            j = Math.max(j, map.get(s.charAt(i)) + 1);
        }
        map.put(s.charAt(i), i);
        max = Math.max(max, i - j + 1);
    }
    return max;
}


=======================================================================================================
method 2:

method:

    - Sliding Window with Set
    - If a substring s_{ij} from index i to j - 1 is already checked to 
        have no duplicate characters. We only need to check if s[j] is already in the 
        substring s_{ij}
    
    - To check if a character is already in the substring, we can scan the substring, which leads 
        to an O(n^2)

    - By using HashSet as a sliding window, checking if a character in the current can be done in O(1)
    - A sliding window is an abstract concept commonly used in array/string problems. A window is a range 
        of elements in the array/string which usually defined by the start and end indices, 
        i.e. [i, j) (left-closed, right-open). 

    A sliding window is a window "slides" its two boundaries to the certain direction. For example, 
        if we slide [i, j) to the right by 1 element, then it becomes [i+1, j+1) (left-closed, right-open).

    - use HashSet to store the characters in current window [i, j) (j = i first). 
        Then we slide the index j to the right. If it is not in the HashSet, we slide j further. 
        Doing so until s[j] is already in the HashSet. At this point, we found the maximum size of 
        substrings without duplicate characters start with index ii. 

        If we do this for all i, we get our answer.


stats:

    - Time complexity : O(2n) = O(n). worst case: each character will be visited twice by i and j
    - Space complexity : O(min(m, n)) Same as the previous approach. We need O(k) space for the 
        sliding window, where k is the size of the Set. The size of the Set is upper bounded by 
        the size of the string nn and the size of the charset/alphabet mm.

 

public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();

        //the window
        Set<Character> set = new HashSet<>();

        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {

            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j));
                j++;
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return ans;
    }
}







