709. To Lower Case - Easy


Implement function ToLowerCase() that has a string parameter str, and returns the same string in
lowercase.

 

Example 1:

Input: "Hello"
Output: "hello"


Example 2:

Input: "here"
Output: "here"


Example 3:

Input: "LOVELY"
Output: "lovely"


******************************************************
key:
	- string builder
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
	-	


class Solution {
  public String toLowerCase(String str) {
    return str.toLowerCase();
  }
}



class Solution {
  public String toLowerCase(String str) {
    Map<Character, Character> h = new HashMap();
    String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lower = "abcdefghijklmnopqrstuvwxyz";
    for (int i = 0; i < 26; ++i) {
      h.put(upper.charAt(i), lower.charAt(i));
    }

    StringBuilder sb = new StringBuilder();
    for (char x : str.toCharArray()) {
      sb.append(h.containsKey(x) ? h.get(x) : x);
    }
    return sb.toString();
  }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	It's based on the fact that ASCII code of small letter is equal to ASCII code of corresponding
	    capital letter + 2^5: ord('a') = ord('A') + 32, or ord('a') = ord('A') | 32.
	-	





class Solution {
  public boolean isUpper(char x) {
    return 'A' <= x && x <= 'Z';
  }

  public char toLower(char x) {
    return (char) ((int)x | 32);
  }

  public String toLowerCase(String str) {
    StringBuilder sb = new StringBuilder();
    for (char x : str.toCharArray()) {
      sb.append(isUpper(x) ? toLower(x) : x);
    }
    return sb.toString();
  }
}




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

