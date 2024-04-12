/**
Implement int sqrt(int x).

Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

Since the return type is an integer, the decimal digits are truncated and only the integer part of the 
result is returned.

Example 1:

Input: 4
Output: 2
**/

class Solution {
    public int mySqrt(int x) {
        if (x == 0) return 0;
        int start = 1, end = x;
        while (start < end) { 
            int mid = start + (end - start) / 2;
            if (mid <= x / mid && (mid + 1) > x / (mid + 1))    // Found the result
                return mid; 
            else if (mid > x / mid)                             // Keep checking the left part
                end = mid;
            else
                start = mid + 1;                                // Keep checking the right part
        }
        return start;
    }
}