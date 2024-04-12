482. License Key Formatting - Easy

You are given a license key represented as a string S which consists only alphanumeric character and 
dashes. The string is separated into N+1 groups by N dashes.

Given a number K, we would want to reformat the strings such that each group contains exactly K 
characters, except for the first group which could be shorter than K, but still must contain at least 
one character. Furthermore, there must be a dash inserted between two groups and all lowercase letters 
should be converted to uppercase.

Given a non-empty string S and a number K, format the string according to the rules described above.

Example 1:
Input: S = "5F3Z-2e-9-w", K = 4

Output: "5F3Z-2E9W"

Explanation: The string S has been split into two parts, each part has 4 characters.
Note that the two extra dashes are not needed and can be removed.
Example 2:
Input: S = "2-5g-3-J", K = 2

Output: "2-5G-3J"

Explanation: The string S has been split into three parts, each part has 2 characters except the first part as it could be shorter as mentioned above.
Note:
The length of string S will not exceed 12,000, and K is a positive integer.
String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
String S is non-empty.


******************************************************
key:
	- start from the back
	- use string builder
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	Use string builder, go from the back and insert the '-'

Stats:

	- 
	- 


class Solution {
    public String licenseKeyFormatting(String S, int K) {
        String string = S.replace("-","").toUpperCase();
        StringBuilder sb = new StringBuilder(string);

        // insert from the back
        for(int i = sb.length()-K; i > 0; i = i-K){
        	sb.insert(i, '-');
        }
        return sb.toString();
    }
}

=======================================================================================================
method 2:

Method:

	The trick here is simply to loop backwards through the input string and use a pre-allocated 
	char[] large enough for our output. 

	This way, we don't have to worry about inserting taking extra O(n) time, and we don't have to 
	do any special calculation for the size of the first group.


Stats:

	- 
	- 


class Solution {
    public String licenseKeyFormatting(String S, int K) {
       
        //1. Allocate our buffers, output buffer should be large enough to fit as many dashes as needed
        char[] inputBuffer = S.toCharArray();
        char[] outputBuffer = new char[S.length() + S.length() / K + 1];
        int count = 0, start = 0, end = outputBuffer.length - 1;
        
        //2. Find the start index of the string (i.e. the first non-dash index)
        while(start < inputBuffer.length && inputBuffer[start] == '-') start++;
        
        //3. Loop backwards through the input and insert dashes as needed
        for(int i = inputBuffer.length - 1; i >= start; i--) {
            
            // skip over dashes in the input
            if(inputBuffer[i] == '-')
                continue;
            
            //4. add the character and uppercase if necessary
            outputBuffer[end--] = (char)(inputBuffer[i] + ((inputBuffer[i] > 'Z') ? - ' ' : 0));
            count++;

            //5. insert a dash when our count reaches K
            if(i > start && count == K) {
                outputBuffer[end--] = '-';
                count = 0;
            }
            
        }
        
        // Note: may not use all spaces in output buffer
        //6. Build the new string starting from our final output index
        return new String(outputBuffer, end + 1, outputBuffer.length - end - 1);
    }
}

=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 



