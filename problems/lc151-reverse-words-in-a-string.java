151. Reverse Words in a String - Medium

Given an input string, reverse the string word by word.

 

Example 1:

Input: "the sky is blue"
Output: "blue is sky the"
Example 2:

Input: "  hello world!  "
Output: "world! hello"
Explanation: Your reversed string should not contain leading or trailing spaces.
Example 3:

Input: "a good   example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the 
reversed string.
 

Note:

A word is defined as a sequence of non-space characters.
Input string may contain leading or trailing spaces. However, your reversed string should not 
contain leading or trailing spaces.
You need to reduce multiple spaces between two words to a single space in the reversed string.


******************************************************
key:
	- built in method
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 2 pointers


Stats:

	- 
	- 


Method:

	-	
	-	

public class Solution {
  
  public String reverseWords(String s) {
	if (s == null) return null;
	
	char[] a = s.toCharArray();
	int n = a.length;
	
	// step 1. reverse the whole string
	reverse(a, 0, n - 1);

	// step 2. reverse each word
	reverseWords(a, n);

	// step 3. clean up spaces
	return cleanSpaces(a, n);
  }
  
  // reverse a[] from a[i] to a[j]
  private void reverse(char[] a, int i, int j) {
	while (i < j) {
	  char t = a[i];
	  a[i] = a[j];
	  a[j] = t;
	  i++;
	  j--;
	}
  }

  void reverseWords(char[] a, int n) {
	int i = 0, j = 0;
	  
	while (i < n) {
	  while (i < j || i < n && a[i] == ' ') i++; // skip spaces

	  while (j < i || j < n && a[j] != ' ') j++; // reach a word, use j = end index of this word

	  reverse(a, i, j - 1);                      // reverse the word
	}
  }
  
  // trim leading, trailing and multiple spaces
  String cleanSpaces(char[] a, int n) {
	int i = 0, j = 0;
	  
	while (j < n) {

		// one word
	  	while (j < n && a[j] == ' ') j++;             // skip leading spaces
	  	while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
	  	while (j < n && a[j] == ' ') j++;             // skip trailing spaces
	  	if (j < n) a[i++] = ' ';                      // keep only one space in between
	}
  
	return new String(a).substring(0, i);
  }
  
}


=======================================================================================================
method 2: built in methods

Stats:

	- 
	- 


Method:

	-	 + means at least 1 so in this case " +" means at least one space
	-	


	public String reverseWords(String s) {
		String[] words = s.trim().split(" +");//first trim leading/trailing spaces, then split by space
		Collections.reverse(Arrays.asList(words));
		return String.join(" ", words);
	}

=======================================================================================================
method 3:  in place:


Stats:

	- 
	- 


Method:

	-	
	-	

public String reverseWords(String s) {
        if (s.length() < 1) return s; // empty string

        int startIdx = 0;
        char[] str = s.toCharArray();

        // reverse whole string
        reverse(str, 0, str.length - 1);

        // reverse word one by one
        for (int i = 0; i < str.length; i++) {
            if (str[i] != ' ') {
                if (startIdx != 0) str[startIdx++] = ' ';
                int j = i;
                while (j < str.length && str[j] != ' ')
                    str[startIdx++] = str[j++];
                reverse(str, startIdx - (j - i), startIdx - 1); // startIdx - 1, NOT startIdx; 
                // C++ std::reverse : Reverses the order of the elements in the range [first, last)
                i = j;
            }
        }

        return new String(str, 0, startIdx);
    }

    private void reverse(char[] str, int begin, int end) {
        for (; begin < end; begin++, end--) {
            char tmp = str[begin];
            str[begin] = str[end];
            str[end] = tmp;
        }
    }



