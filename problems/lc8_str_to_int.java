8. String to Integer (atoi) Medium

Implement atoi which converts a string to an integer.

The function first discards as many whitespace characters as necessary until the first non-whitespace 
character is found. Then, starting from this character, takes an optional initial plus or minus 
sign followed by as many numerical digits as possible, and interprets them as a numerical value.

The string can contain additional characters after those that form the integral number, which are 
ignored and have no effect on the behavior of this function.

If the first sequence of non-whitespace characters in str is not a valid integral number, or if 
no such sequence exists because either str is empty or it contains only whitespace characters, 
no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

Note:

Only the space character ' ' is considered as whitespace character.
Assume we are dealing with an environment which could only store integers within the 32-bit 
signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of 
representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.

Example 1:
Input: "42"
Output: 42

Example 2:
Input: "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign.
             Then take as many numerical digits as possible, which gets 42.

Example 3:
Input: "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.

Example 4:
Input: "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical 
             digit or a +/- sign. Therefore no valid conversion could be performed.

Example 5:
Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−231) is returned.



========================================================================================================
key:
	always check for special case: str = "", " ", and "  "

class Solution {
    public int myAtoi(String str) {
        int index = 0;
        int total = 0;
        int sign = 1;
        
        // Check if empty string
        if(str.length() == 0)
            return 0;
        
        // remove white spaces from the string
        while(index < str.length() && str.charAt(index) == ' ')
            index++;
        
        // after strip
        if (index == str.length()) 
            return 0;
        
        // get the sign
        if(str.charAt(index) == '+' || str.charAt(index) == '-') {
            sign = str.charAt(index) == '+' ? 1 : -1;
            index++;
        }
        
        // convert to the actual number and make sure it's not overflow
        while(index < str.length()) {
            int digit = str.charAt(index) - '0';

            if(digit < 0 || digit > 9) break;
            
            // check for overflow
            if(Integer.MAX_VALUE / 10 < total || Integer.MAX_VALUE / 10 == total 
                && Integer.MAX_VALUE % 10 < digit)
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            
            total = total*10 + digit;
            index++; // don't forget to increment the counter
        }

        return total * sign;
    }
}


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ python ~~~~~~~~~~~~~
    def myAtoi(self, s):
        """
        :type str: str
        :rtype: int
        """
        ls = list(s.strip())
        if len(s) == 0 : return 0
        
        sign = -1 if ls[0] == '-' else 1

        if ls[0] in ['-','+'] : 
            del ls[0]

        ret, i = 0, 0

        while i < len(ls) and ls[i].isdigit() :
            ret = ret*10 + ord(ls[i]) - ord('0')
            i += 1
        
        return max(-2**31, min(sign * ret,2**31-1))






