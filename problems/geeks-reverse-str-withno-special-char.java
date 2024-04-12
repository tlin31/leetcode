Geeks-Reverse a string without affecting special characters


Given a string, that contains special character together with alphabets (‘a’ to ‘z’ and ‘A’ to ‘Z’), 
reverse the string in a way that special characters are not affected.

Examples:

Input:   str = "a,b$c"
Output:  str = "c,b$a"
Note that $ and , are not moved anywhere.  
Only subsequence "abc" is reversed

Input:   str = "Ab,c,de!$"
Output:  str = "ed,c,bA!$"
******************************************************
key:
    - 2 pointers
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:

    - 
        1) Create a temporary character array say temp[].
        2) Copy non-special characters from given array to temp[].
        3) Reverse temp[] using standard string reversal algorithm.
        4) Now traverse input string and temp in a single loop. Wherever there is alphabetic character is input string, replace it with current character of temp[].

    - 

stats:

    - 
    - 




=======================================================================================================
method 2:

method:

    - 
        1) Let input string be 'str[]' and length of string be 'n'
        2) l = 0, r = n-1
        3) While l is smaller than r, do following
            a) If str[l] is not an alphabetic character, do l++
            b) Else If str[r] is not an alphabetic character, do r--
            c) Else swap str[l] and str[r]
    - 

stats:

    - O(n)
    - requires extra space and it does two traversals of input string.


    public static void reverse(char str[]) 
    { 
        int r = str.length - 1, 
            l = 0; 

        while (l < r) 
        { 
            // Ignore special characters 
            if (!Character.isAlphabetic(str[l])) 
                l++; 
            else if(!Character.isAlphabetic(str[r])) 
                r--; 
  
            // Both str[l] and str[r] are not spacial 
            else { 
                char tmp = str[l]; 
                str[l] = str[r]; 
                str[r] = tmp; 
                l++; 
                r--; 
            } 
        } 
    } 
  
