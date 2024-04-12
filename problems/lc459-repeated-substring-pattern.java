459. Repeated Substring Pattern - Easy


Given a non-empty string check if it can be constructed by taking a substring of it and appending
multiple copies of the substring together. You may assume the given string consists of lowercase 
English letters only and its length will not exceed 10000.


Example 1:

Input: "abab"
Output: True
Explanation: Its the substring "ab" twice.



Example 2:

Input: "aba"
Output: False



Example 3:

Input: "abcabcabcabc"
Output: True
Explanation: Its the substring "abc" four times. (And the substring "abcabc" twice.)

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

	- The length of the repeating substring must be a divisor of the length of the input string
	- Search for all possible divisor of str.length, starting for length/2
	- If i is a divisor of length, repeat the substring from 0 to i the number of times i is contained 
	  in s.length
	- If the repeated substring is equals to the input str return true






	public boolean repeatedSubstringPattern(String str) {
		int l = str.length();
		StringBuilder sb = new StringBuilder();
		String subS = "";

		for(int i = l/2; i>=1; i--) {

			// i is a potential divisor
			if(l % i==0) {
				int multiple = l/i;
				subS = str.substring(0,i);

				for(int j=0; j<multiple; j++) {
					sb.append(subS);
				}

				if(sb.toString().equals(str)) 
					return true;
			}
		}
		return false;
	}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

The maximum length of a "repeated" substring that you could get from a string would be half its length
For example, s = "abcdabcd", "abcd" of len = 4, is the repeated substring.
You cannot have a substring >(len(s)/2), that can be repeated.

So, when ss = s + s , we will have at least 4 parts of "repeated substring" in ss.
(s+s)[1:-1] --> removing 1st char and last char => Out of 4 parts of repeated substring, 
			    2 part will be gone (they will no longer have the same substring).
ss.find(s) != -1, But still we have 2 parts out of which we can make s. And that is how ss should 
	have s, if s has repeated substring.


def repeatedSubstringPattern(self, str):

        """
        :type str: str
        :rtype: bool
        """
        if not str:
            return False
            
        ss = (str + str)[1:-1]
        return ss.find(str) != -1

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

