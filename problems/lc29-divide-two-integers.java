29. Divide Two Integers --- Medium

Given two integers dividend and divisor, divide two integers without using multiplication, division and mod 
operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero.

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Note:

Both dividend and divisor will be 32-bit signed integers.
The divisor will never be 0.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer 
range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 231 − 1 when the 
division result overflows.

=======================================================================================================
method 1:

key:
	- corner case: overflows when  
		1) divisor = 0 ;
		2) dividend = INT_MIN and divisor = -1 (because abs(INT_MIN) =INT_MAX + 1 ).
	- bit : We subtract 3 from 15 and we get 12 , which is positive. --> we shift 3 to the left by 1 bit 
	  and we get 6 . Subtracting 6 from 15 still gives a positive result. Well, we shift again and get 12 . 
	  We subtract 12 from 15 and it is still positive. We shift again, obtaining 24 and we know we can at 
	  most subtract 12 . 
	  since 12 is obtained by shifting 3 to left twice, we know it is 4 times of 3 . 
	  How do we obtain this 4 ? we start from 1 and shift it to left twice at the same time. 
	  We add 4 to an answer (initialized to be 0 ). -->  15 = 3 * 4 + 3 . 

class Solution {
    public:
        int divide(int dividend, int divisor) {
            if (!divisor || (dividend == INT_MIN && divisor == -1))
                return INT_MAX;
            int sign = ((dividend < 0) ^ (divisor < 0)) ? -1 : 1;

            //get absolute value
            long long dvd = labs(dividend);
            long long dvs = labs(divisor);
            int res = 0;
            while (dvd >= dvs) {
                long long temp = dvs, multiple = 1;
                while (dvd >= (temp << 1)) {
                    temp <<= 1;
                    multiple <<= 1;
                }
                dvd -= temp;
                res += multiple;
            }
            return sign == 1 ? res : -res;
        }
};


=======================================================================================================
method 2:

key:
public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        if (dividend > 0 && divisor > 0) {
            return divideHelper(-dividend, -divisor);
        } else if (dividend > 0) {
            return -divideHelper(-dividend, divisor);
        }
        else if(divisor > 0) {
            return -divideHelper(dividend, -divisor);
        }
        else {
            return divideHelper(dividend, divisor);
        }
    }


    private int divideHelper(int dividend, int divisor) {
        int result = 0;
        int currentDivisor = divisor;
        while(true) {
            if(dividend > divisor) {
                break;
            }
            int temp = 1;
            while(dividend <= currentDivisor << 1 && currentDivisor << 1 < 0) {
                temp = temp << 1;
                currentDivisor = currentDivisor << 1;
            }
            dividend -= currentDivisor;
            result += temp;
            currentDivisor = divisor;
        }
        return result;
    }


=======================================================================================================
method 3:

key:

//complexity: O(( log N)^2)

def divide(self, dividend, divisor):
    positive = (dividend < 0) is(divisor < 0)
    dividend, divisor = abs(dividend), abs(divisor)
    res = 0
    while dividend >= divisor:
        temp, i = divisor, 1
    while dividend >= temp:
        dividend -= temp
    res += i
    i <<= 1
    temp <<= 1
    if not positive:
        res = -res
    return min(max(-2147483648, res), 2147483647)













	  