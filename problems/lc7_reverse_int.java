7. Reverse Integer
Easy

Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. 
For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

Have you thought about this?
If the integers last digit is 0, what should the output be? ie, cases such as 10, 100.
Did you notice that the reversed integer might overflow? Assume the input is a 32-
bit integer, then the reverse of 1000000003 overflows. How should you handle such cases?

==============================================================================================================
method 1:
key:
	If overflow exists, the new result will not equal previous one. No flags needed. 

public int reverse(int x) {
    int result = 0;
    while (x != 0) {
        int tail = x % 10;
        int newResult = result * 10 + tail;
        if ((newResult - tail) / 10 != result) {
            return 0;
        }
        result = newResult;
        x = x / 10;
    }
    return result;
}

==============================================================================================================
method 2:
key:
	here set rev as a long variable

public int reverse(int x) {
    long rev = 0;
    while (x != 0) {
        rev = rev * 10 + x % 10;
        x = x / 10;
        if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE)
            return 0;
    }
    return (int) rev;
}


















