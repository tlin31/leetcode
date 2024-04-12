680. Valid Palindrome II - Easy

Given a non-empty string s, you may delete at most one character. 

Judge whether you can make it a palindrome.

Example 1:
Input: "aba"
Output: True
Example 2:
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
Note:
The string will only contain lowercase characters a-z. The maximum length of the string is 50000.


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Greedy

Method:

	-  Suppose we want to know whether s[i], s[i+1], ..., s[j] form a palindrome. 
	    	If i >= j then we are done. 
	    	If s[i] == s[j] then we may take i++; j--. 
	    	Otherwise, the palindrome must be either s[i+1], s[i+2], ..., s[j] or s[i], s[i+1], ..., s[j-1], and we should check both cases.

	-	

Stats:

	- O(n)
	- O(1)

class Solution {

	// main function
    public boolean validPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
        	int j = s.length() - 1 - i;
            if (s.charAt(i) != s.charAt(j)) {
                return (isPalindromeRange(s, i+1, j) ||
                        isPalindromeRange(s, i, j-1));
            }
        }
        return true;
    }

    public boolean isPalindromeRange(String s, int i, int j) {
        for (int k = i; k <= i + (j - i) / 2; k++) {
            if (s.charAt(k) != s.charAt(j - k + i)) 
            	return false;
        }
        return true;
    }
}

------------	only delete 1 character

class Solution {
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j && s.charAt(i) == s.charAt(j)) {
            i++; j--;
        }

        if (i >= j) return true;

        // here is the first not-same pair
        if (isPalin(s, i + 1, j) || isPalin(s, i, j - 1)) 
        	return true;

        return false;
    }

    private boolean isPalin(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++; j--;
            }
            else 
            	return false;
        }
        return true;
    }
}
=======================================================================================================
method 2:

Method:

	-	Check from left and right at the same time until the first different pair.
		Now we have something like a****b, where a and b are different.
		We need to delete either a or b to make it a palindrome.
	-	


Stats:

	- 
	- 

  public boolean validPalindrome(String s) {
        for (int i = 0, j = s.length() - 1; i < j; i++, j--)
            if (s.charAt(i) != s.charAt(j)) {
                int i1 = i, j1 = j - 1, i2 = i + 1, j2 = j;
                while (i1 < j1 && s.charAt(i1) == s.charAt(j1)) {i1++; j1--;};
                while (i2 < j2 && s.charAt(i2) == s.charAt(j2)) {i2++; j2--;};
                return i1 >= j1 || i2 >= j2;
            }
        return true;
    }

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



