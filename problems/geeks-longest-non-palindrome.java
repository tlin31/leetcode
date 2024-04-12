Longest Non-palindromic substring

Given a string of size n. Find the length of the largest substring which is not palindrome.

Examples:

Input : abba 
Output : 3
Here maximum length non-palindromic substring is
'abb' which is of length '3'. There could be other
non-palindromic sub-strings also of length three 
like 'bba' in this case.

Input : a
Output : 0



Algorithm:
	Check for the case where all characters of
	the string are same or not.
	    If yes, then answer will be '0'.
	Else check whether the given string of size 
	'n' is palindrome or not. 
	    If yes, then answer will be 'n-1'
	Else answer will be 'n' 

//Java implementation to find maximum length 
//substring which is not palindrome 
public class GFG 
{ 
	// utility function to check whether 
	// a string is palindrome or not 
	static Boolean isPalindrome(String str) 
	{ 
		int n = str.length(); 

		// Check for palindrome. 
		for (int i = 0; i < n/2; i++) 
			if (str.charAt(i) != str.charAt(n-i-1)) 
				return false; 

		// palindrome string 
		return true; 
	} 

	// function to find maximum length 
	// substring which is not palindrome 
	static int maxLengthNonPalinSubstring(String str) 
	{ 
		int n = str.length(); 
		char ch = str.charAt(0); 

		// to check whether all characters 
		// of the string are same or not 
		int i = 1; 
		for (i = 1; i < n; i++) 
			if(str.charAt(i) != ch) 
				break; 

		// All characters are same, we can't 
		// make a non-palindromic string. 
		if (i == n) 
			return 0; 

		// If string is palindrome, we can make 
		// it non-palindrome by removing any 
		// corner character 
		if (isPalindrome(str)) 
			return n-1; 

		// Complete string is not a palindrome. 
		return n; 
	} 

	// Driver Program to test above function 
	public static void main(String args[]) 
	{ 
		String str = "abba"; 
		System.out.println("Maximum Length = "
			+ maxLengthNonPalinSubstring(str)); 
	} 
} 
// This code is contributed by Sumit Ghosh 
