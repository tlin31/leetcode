633. Sum of Square Numbers

Given a non-negative integer c, your task is to decide whether there are two integers a and b such 
that a2 + b2 = c.

Example 1:

Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5


******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1: binary search


Stats:

    - 
    - 


Method:

    -  the bigger of the two numbers must be less than sqrt(c)


public class Solution {
    public boolean judgeSquareSum(int c) {
        if (c < 0)  return false;
        
        int left = 0, right = (int)Math.sqrt(c);

        while (left <= right) {
            int cur = left * left + right * right;
            if (cur < c) {
                left++;
            } else if (cur > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }
}



