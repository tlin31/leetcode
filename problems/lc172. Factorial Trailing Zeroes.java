172. Factorial Trailing Zeroes - Medium

Given an integer n, return the number of trailing zeroes in n!.

Note that n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1.

 

Example 1:

Input: n = 3
Output: 0
Explanation: 3! = 6, no trailing zero.
Example 2:

Input: n = 5
Output: 1
Explanation: 5! = 120, one trailing zero.
Example 3:

Input: n = 0
Output: 0
 

Constraints:

0 <= n <= 104
 

Follow up: Could you write a solution that works in logarithmic time complexity?


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

n! 结果中的尾随零的数量，完全由 10 = 2 × 5 决定。

在 n! 的所有因子中：因子 2 的数量永远比 5 多

所以尾随零的数量 = 因子 5 的数量

公式：ans = n/5 + n/25 + n/125 + ...

直到商为 0 为止。


Stats:

	- 
	- 

class Solution {
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }
}


