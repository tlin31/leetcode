443. String Compression - Easy

Given an array of characters, compress it in-place.

The length after compression must always be smaller than or equal to the original array.

Every element of the array should be a character (not int) of length 1.

After you are done modifying the input array in-place, return the new length of the array.

 
Follow up:
Could you solve it using only O(1) extra space?

 
Example 1:

Input:
["a","a","b","b","c","c","c"]

Output:
Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]

Explanation:
"aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
 

Example 2:

Input:
["a"]

Output:
Return 1, and the first 1 characters of the input array should be: ["a"]

Explanation:
Nothing is replaced.
 

Example 3:
Input:
["a","b","b","b","b","b","b","b","b","b","b","b","b"]

Output:
Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].

Explanation:
Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by "b12".
Notice each digit has its own entry in the array.
 

Note:
All characters have an ASCII value in [35, 126].
1 <= len(chars) <= 1000.


******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	For a character ch in chars, we count its frequency freq.
		If freq = 1, we append ch only.
		Or else, we append ch then freq (when freq >= 10, freq should cost String.valueOf(
			freq).length() indexes, e.g. 'a12' should be [a, 1, 2]).
	-	

    public int compress(char[] chars) {
        
        if (chars == null || chars.length == 0)
            return 0;
        
        int index = 0, n = chars.length, i = 0;
        while (i < n) {
            char ch = chars[i];
            int j = i;
            while (j < n && chars[i] == chars[j]) { // chars[i..j - 1] are ch.
                j++;
            }
            int freq = j - i; // The frequency of ch.
            chars[index++] = ch;
            if (freq == 1) {
                
            }                
            else if (freq < 10) {
                chars[index++] = (char)(freq + '0');
            }
            else {
            	// freq > 10
                String strFreq = String.valueOf(freq); 
                for (char chFreq : strFreq.toCharArray())
                    chars[index++] = chFreq;
            }
            i = j;
        }
        
        return index;
    }
=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



