9. Palindrome Number---Easy

Determine whether an integer is a palindrome. An integer is a palindrome when it reads the 
same backward as forward.

Example 1:
Input: 121
Output: true


Example 2:
Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.


Example 3:
Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.

Follow up: Coud you solve it without converting the integer to a string?

=========================================================================================================
method 1:
	convert int to string

method 2:
key:
	1. need to keep 2 variable, one representing the first half of the number, another represent the tail
		ex. x = 1221, then head = 12, tail = 21

	2. the looping ends once tail > head, meaning we have passed the midpoint of the string

public boolean isPalindrome(int x) {
	int endDigits = 0;
	int tail = 0;
	int head = x;

	// corner case: if x = 0 or x's last digit is 0, then it should always return false, because
	// we can never have a number starting with 0
    if (x < 0 || (x != 0 && x % 10 == 0)) 
    	return false;

    // looping condition
    while (x > endDigits) {
    	tail = x % 10
        endDigits = endDigits * 10 + tail;
        x = x / 10;
    }
    return (x == endDigits || x == endDigits / 10);
}









