/**
Implement int sqrt(int x).

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, the decimal digits are truncated and only the integer part of the 
result is returned.

Example 1:

Input: 4
Output: 2
**/

// binary search
class Solution {
    public int mySqrt(int x) {
        if (x < 2) return x;

        long num;
        int pivot, left = 2, right = x / 2;
        while (left <= right) {
            pivot = left + (right - left) / 2;
            num = (long) pivot * pivot;
            if (num > x) 
                right = pivot - 1;
            else if (num < x) 
                left = pivot + 1;
            else 
                return pivot;
        }

        return right;
    }
}