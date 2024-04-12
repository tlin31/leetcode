917. Reverse Only Letters - Easy


Given a string S, return the "reversed" string where all characters that are not a letter stay in 
the same place, and all letters reverse their positions.

 

Example 1:

Input: "ab-cd"
Output: "dc-ba"
Example 2:

Input: "a-bC-dEf-ghIj"
Output: "j-Ih-gfE-dCba"
Example 3:

Input: "Test1ng-Leet=code-Q!"
Output: "Qedo1ct-eeLg=ntse-T!"
 

Note:

S.length <= 100
33 <= S[i].ASCIIcode <= 122 
S does not contain \ or ' " '


******************************************************
key:
	- stack or 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: 2 pointers + string builder


Stats:

	- O(N)
	- O(N)


Method:

	-	
	-	



class Solution {
    public String reverseOnlyLetters(String S) {
        StringBuilder ans = new StringBuilder();

        int j = S.length() - 1;

        for (int i = 0; i < S.length(); ++i) {
            if (Character.isLetter(S.charAt(i))) {
                while (!Character.isLetter(S.charAt(j)))
                    j--;
                ans.append(S.charAt(j--));
            } 

            else {

            	// append special character & move forward in the for loop
                ans.append(S.charAt(i));
            }
        }

        return ans.toString();
    }
}

----------

In-place 2 pointers:

class Solution {
    public String reverseOnlyLetters(String S) {
        StringBuilder ans = new StringBuilder();
        char[] str = S.toCharArray();
        int j = S.length() - 1;
        int i = 0;
        
        while (i<j) {
            if (Character.isLetter(str[i]) && Character.isLetter(str[j])) {
                swap(str, i, j);
                i++;
                j--;
            } else if (Character.isLetter(str[i])) {
                while (!Character.isLetter(S.charAt(j)))
                    j--;
                swap(str, i, j);
                i++;
                j--;
            } else {
                i++;
            }
                
        }
        
       String res = new String(str);

        return res;
    }
    
    private char[] swap (char[] orig, int i, int j) {
        char temp = orig[i];
        orig[i] = orig[j];
        orig[j] = temp;
        return orig;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: stack

Stats:

	- 
	- 


Method:

	-  Collect the letters of S separately into a stack, so that popping the stack reverses the letters. 

	-  when writing the characters of S, any time we need a letter, we use the one we have prepared 




class Solution {
    public String reverseOnlyLetters(String S) {
        Stack<Character> letters = new Stack();
        for (char c: S.toCharArray())
            if (Character.isLetter(c))
                letters.push(c);

        StringBuilder ans = new StringBuilder();
        for (char c: S.toCharArray()) {
            if (Character.isLetter(c))
                ans.append(letters.pop());
            else
            	// append special character
                ans.append(c);
        }

        return ans.toString();
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

