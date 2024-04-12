66. Plus One - Easy

Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each 
element in the array contain a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.

Example 2:
Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.


******************************************************
key:
	- record the last digit, and see whether it was 9
		- check whether last one is 9, is so, let it = 0, then update carry = 1
	- edge case:
		1) 0 --> 1
		2) empty list --> ?
		3) 999 --> 1000

******************************************************


=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 
public int[] plusOne(int[] digits) {
    int n = digits.length;
    for (int i = n - 1; i >= 0; i--) {
        if (digits[i] < 9) {
            digits[i]++;
            return digits;
        }
        digits[i] = 0;
    }

    // this case only happens at 10, 100, 10000,.....
    int[] newNumber = new int[n + 1];
    newNumber[0] = 1;
    return newNumber;
}


=======================================================================================================
method 2:

method:

	- backtrack on digits
	- 

stats:

	- 时间复杂度：O（n）。
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Plus One.
	- Memory Usage: 35.4 MB, less than 97.58% 


public int[] plusOne(int[] digits) {
    return plusOneAtIndex(digits, digits.length - 1);
}

private int[] plusOneAtIndex(int[] digits, int index) {
    //说明每一位都是 9 
    if (index < 0) {
        //新建一个更大的数组，最高位赋值为 1
        int[] ans = new int[digits.length + 1];
        ans[0] = 1;
        //其他位赋值为 0，因为 java 里默认是 0，所以不需要管了 
        return ans;
    }
    //如果当前位小于 9，直接加 1 返回
    if (digits[index] < 9) {
        digits[index] += 1;
        return digits;
    }

    //否则的话当前位置为 0,
    digits[index] = 0;
    //考虑给前一位加 1
    return plusOneAtIndex(digits, index - 1);

}




=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



