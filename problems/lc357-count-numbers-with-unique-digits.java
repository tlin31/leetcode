357. Count Numbers with Unique Digits - Medium

Given a non-negative integer n, count all numbers with unique digits, x, where 0 ≤ x < 10^n.

Example:

Input: 2
Output: 91 
Explanation: The answer should be the total numbers in the range of 0 ≤ x < 100, 
             excluding 11,22,33,44,55,66,77,88,99


******************************************************
key:
	- Backtrack, combinatories
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- backtrack

	- The idea is to append one digit at a time recursively (only append digits that has not been
	  appended before)
	  Number zero is a special case, because we do not want to deal with the leading zero, so it is
	  counted separately at the beginning of the program. 



stats:

	- The running time for this program is O(10!) worst case, or O(n!) if n < 10.
	- 


public class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        // there are 10 unique digits from 0 to 9 to form the numbers, 
        // obviousely `count[i] = count[10]` for any i > 10,
        // because there must be at least two digits are the same for a number of length larger than 10.
        if (n > 10) 
        	n = 10;
        return getCount(n, 0, new boolean[10]);
    }
    
    // idx -- current length of the number
    // 		we've assigned `idx` digits to form the number, and are about to assign the `idx`th digit.
    // used -- A boolean array of length 10 that indicates which digits in [0:9] have been assigned 
    // 		   to form the current number.
    // For lower level calls, i.e. `idx > 0`, the returned value is the count of valid target numbers 
    // that have the current number as a prefix. The current number is formed by the assigned digits 
    // in former calls.
    private int getCount(int n, int idx, boolean[] used) {
        // terminate: current number contains n digits, we can't add more digits, 
        //            and there are only one number that has the current number as a prefix, 
        //            which is the current prefix number itself.
        // A special case is that when n equals 0, there's no prefix number, and the only one number is 0.
        if (idx == n) 
            return 1;

        // initialize count to be 1, because the prefix number itself can be a valid target number.
        // when idx equals 0, there's no prefix number, but the number 0 is a valid target number.
        int count = 1;

        // loop through all possible numbers
        for (int i = (idx == 0) ? 1 : 0; i <= 9; i++) {
            if (!used[i]) {    
                used[i] = true;
                
                // add the count of valid target numbers that have the "updated" current 
                // number as a prefix
                count += getCount(n, idx + 1, used);

                // backtrack
                used[i] = false;
            }
        }
        return count;
    }
}

=======================================================================================================
method 2:

method:

	- TRICK
	- 

stats:

	- 
	- 

This is a digit combination problem. Can be solved in at most 10 loops.

When n == 0, return 1. I got this answer from the test case.

When n == 1, _ can put 10 digit in the only position. [0, ... , 10]. Answer is 10.

When n == 2, _ _ first digit has 9 choices [1, ..., 9], second one has 9 choices excluding the 
            already chosen one. So totally 9 * 9 = 81. answer should be 10 + 81 = 91

When n == 3, _ _ _ total choice is 9 * 9 * 8 = 684. answer is 10 + 81 + 648 = 739

When n == 4, _ _ _ _ total choice is 9 * 9 * 8 * 7.

...

When n == 10, _ _ _ _ _ _ _ _ _ _ total choice is 9 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1

When n == 11, _ _ _ _ _ _ _ _ _ _ _ total choice is 9 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1 * 0 = 0

public static int countNumbersWithUniqueDigits(int n) {
    if (n == 0) {
        return 1;
    }
    int ans = 10, base = 9;
    for (int i = 2; i <= n && i <= 10; i++) {
        base = base * (9 - i + 2);
        ans += base;
    }
    return ans;
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



