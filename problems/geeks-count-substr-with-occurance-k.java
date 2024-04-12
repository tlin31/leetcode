Count substrings with each character occurring at most k times

https://www.geeksforgeeks.org/count-substrings-character-occurring-k-times/

Given a string S. Count number of substrings in which each character occurs at most k times. 
Assume that the string consists of only lowercase English alphabets.

Examples:
Input : S = ab
        k = 1
Output : 3
All the substrings a, b, ab have individual character count less than 1. 

Input : S = aaabb
        k = 2
Output : 12
Substrings that have individual character count at most 2 are: a, a, a, b, b, aa, aa, 
ab, bb, aab, abb, aabb.
 
******************************************************
key:
    - sliding windows
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:

    - sliding window, two pointers left and right.
      We initialize left and the right pointer to 0, 
      move the right pointer until the count of each alphabet is less than k, 
      when the count is greater than k, we start incrementing left pointer and decrement the count 
        of the corresponding alphabet, 
      once the condition is satisfied we add (right-left + 1) to the answer.
    - 

stats:
    - Time complexity: O(n)
    - Auxiliary Space: O(1)


    static int find_sub(String s, int k) 
    { 
        int len = s.length(); 

        // initialize left and right pointer to 0 
        int lp = 0, rp = 0; 
        int ans = 0; 

        // an array to keep track of count of each alphabet 
        int[] hash_char = new int[26]; 
        for (; rp < len; rp++) 
        { 
            hash_char[s.charAt(rp) - 'a']++; 
            while (hash_char[s.charAt(rp) - 'a'] > k) 
            { 
                // decrement the count 
                hash_char[s.charAt(lp) - 'a']--; 

                //increment left pointer 
                lp++; 
            } 
            ans += rp - lp + 1; 
        } 
        return ans; 
    } 

    






