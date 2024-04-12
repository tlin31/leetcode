392. Is Subsequence - Easy

Given a string s and a string t, check if s is subsequence of t.

You may assume that there is only lower case English letters in both s and t. 
t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).


A subsequence of a string is a new string which is formed from the original string by deleting some 
(can be none) of the characters without disturbing the relative positions of the remaining characters. 
(ie, "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:
s = "abc", t = "ahbgdc"

Return true.

Example 2:
s = "axc", t = "ahbgdc"

Return false.

Follow up:
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one 
to see if T has its subsequence. In this scenario, how would you change your code?


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 2 pointers


Stats:

	- O(n)
	- O(n)


Method:

	-  


public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        int indexS = 0, indexT = 0;
        while (indexT < t.length()) {
            if (t.charAt(indexT) == s.charAt(indexS)) {
                indexS++;

                if (indexS == s.length()) 
                	return true;
            }
            indexT++;
        }
        return false;
    }
}


=======================================================================================================
method 2: Follow up with binary search

Stats:

	- O(N) time for pre-processing, O(Mlog?) for each S.
	- 


Method:

	-  Since we scan T from beginning to the end (index itself is in increasing order), List will be
	   sufficient. 
	-  Binary search: record the indexes for each character in t, if s[i] matches t[j], then s[i+1]
	   should match a character in t with index bigger than j. 

	   This can be reduced to find the first element larger than a value in an sorted array (find upper
	   bound), which can be achieved using binary search.

	-  Trie: For example, if s1 has been matched, s1[last char] matches t[j]. Now, s2 comes, if s1 is 
	         a prefix of s2, i.e., s1 == s2.substr[0, i-1], we can start match s2 from s2[i]!
		
		So, the idea is to create a Trie for all string that have been matched so far. At a node, we
		record the position in t which matches this char represented by the node. Now, for an incoming
		string s, we first search the longest prefix in the Trie, find the matching position of the last
		prefix-char in t, say j. Then, we can start matching the first non-prefix-char of s from j+1.

    // Eg-1. s="abc", t="bahbgdca"
    // idx=[a={1,7}, b={0,3}, c={6}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=3
    //  i=2 ('c'): prev=6 (return true)

    // Eg-2. s="abc", t="bahgdcb"
    // idx=[a={1}, b={0,6}, c={5}]
    //  i=0 ('a'): prev=1
    //  i=1 ('b'): prev=6
    //  i=2 ('c'): prev=? (return false)

    public boolean isSubsequence(String s, String t) {
        List<Integer>[] idx = new List[256]; // Just for clarity

        // only create arraylist for existed chars in t
        for (int i = 0; i < t.length(); i++) {
            if (idx[t.charAt(i)] == null)
                idx[t.charAt(i)] = new ArrayList<>();
            idx[t.charAt(i)].add(i);
        }
        
        int prev = 0;

        // loop through s
        for (int i = 0; i < s.length(); i++) {
            if (idx[s.charAt(i)] == null) 
            	return false; // 
            int j = Collections.binarySearch(idx[s.charAt(i)], prev);

            // where j should be if it's not existed
            if (j < 0) 
            	j = -j - 1;

            if (j == idx[s.charAt(i)].size()) 
            	return false;

            prev = idx[s.charAt(i)].get(j) + 1;
        }
        return true;
    }



=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



