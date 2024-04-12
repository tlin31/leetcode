521. Longest Uncommon Subsequence I - Easy


Given two strings, you need to find the longest uncommon subsequence of this two strings. The 
longest uncommon subsequence is defined as the longest subsequence of one of these strings and 
this subsequence should not be any subsequence of the other string.

A subsequence is a sequence that can be derived from one sequence by deleting some characters 
without changing the order of the remaining elements. Trivially, any string is a subsequence of 
itself and an empty string is a subsequence of any string.

The input will be two strings, and the output needs to be the length of the longest uncommon subsequence.
If the longest uncommon subsequence doesnt exist, return -1.

 

Example 1:

Input: a = "aba", b = "cdc"
Output: 3
Explanation: The longest uncommon subsequence is "aba", 
because "aba" is a subsequence of "aba", 
but not a subsequence of the other string "cdc".
Note that "cdc" can be also a longest uncommon subsequence.
Example 2:

Input: a = "aaa", b = "bbb"
Output: 3
Example 3:

Input: a = "aaa", b = "aaa"
Output: -1
 

Constraints:

Both strings lengths will be between [1 - 100].
Only letters from a ~ z will appear in input strings.




******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	3 cases:
		1. a=b. If both the strings are identical, it is obvious that no subsequence will be uncommon. Hence, return -1.

		2.length(a)=length(b) and a!=b. Example: abcabc and abdabd. 
		  In this case we can consider any string i.e. abcabc or abdabd as a required subsequence, 
		  as out of these two strings one string will never be a subsequence of other string. 
		  Hence, return length(a) or length(b).

		3. length(a) !=length(b). Example abcdabcd and abcabc. In this case we can consider bigger 
		   string as a required subsequence because bigger string cant be a subsequence of smaller 
		   string. Hence, return max(length(a),length(b))max(length(a),length(b)).



public class Solution {
    public int findLUSlength(String a, String b) {
        if (a.equals(b))
            return -1;
        return Math.max(a.length(), b.length());
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

