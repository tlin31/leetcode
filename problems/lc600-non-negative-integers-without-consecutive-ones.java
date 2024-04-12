600. Non-negative Integers without Consecutive Ones - Hard


Given a positive integer n, find the number of non-negative integers less than or equal to n, whose 
binary representations do NOT contain consecutive ones.



Example 1:
Input: 5
Output: 5
Explanation: 
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule. 
Note: 1 <= n <= 109


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	let one[i] be the number of non-consecutive-1 solutions with the i-th significant bit being 1;
		let zero[i] be the number of non-consecutive-1 solutions with the i-th significant bit being 0;

	-	We first calculate number of all n-bits solutions: res = one[n - 1] + zero[n - 1].
		then we subtract the solutions which is larger than num, we iterate from the MSB to LSB of num:

	if bit[i] == 1
		if bit[i + 1] == 0, there's no solutions in res that is larger than num, we go further into
		                    smaller bit and subtract.

		if bit[i + 1] == 1, we know in those res solutions it won't have any consecutive ones. when 
		                    bit[i + 1] == 1, in one[i + 1], the i-th bit in valid solutions must be 0, 
		                    which are all smaller than 'num', we don't need to check smaller bits and 
		                    subtract, so we break form the loop.
	if bit[i] == 0
		if bit[i + 1] == 1, there's no solutions in res that is larger than num, we go further 
							into smaller bit and subtract.

		if bit[i + 1] == 0, we know zero[i + 1] includes solutions of i-th == 0 (00***) and i-th 
							bit == 1 (01***), we know the i-th bit of num is 0, so we need to subtract 
							all the 01*** solutions because it is larger than num. Therefore, one[i] is 
							subtracted from res.


public class Solution {
    public int findIntegers(int num) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(num)).reverse();
        int n = sb.length();
        
        int zero[] = new int[n];
        int one[] = new int[n];
        zero[0] = one[0] = 1;
        for (int i = 1; i < n; i++) {
            zero[i] = zero[i - 1] + one[i - 1];
            one[i] = zero[i - 1];
        }
        
        int result = zero[n - 1] + one[n - 1];

        // iterate from most significant bits to LSB
        for (int i = n - 2; i >= 0; i--) {
            if (sb.charAt(i) == '1' && sb.charAt(i + 1) == '1') 
            	break;
            if (sb.charAt(i) == '0' && sb.charAt(i + 1) == '0') 
            	result -= one[i];
        }
        
        return result;
    }
}

ex. input = 6

sb = 011
zero: [1, 2, 3]
one:  [1, 1, 2]
result = 5

这道题给了我们一个数字，让我们求不大于这个数字的所有数字中，其二进制的表示形式中没有连续1的个数。
我们首先来考虑二进制的情况，对于1来说，有0和1两种，对于11来说，有00，01，10，三种情况，我们可以通过DP的方法求出长度为k的
二进制数的无连续1的数字个数。
由于题目给我们的并不是一个二进制数的长度，而是一个二进制数，比如100，如果我们按长度为3的情况计算无连续1点个数个数，就会多计算
101这种情况。所以我们的目标是要将大于num的情况去掉 ！！！ if (sb.charAt(i) == '0' && sb.charAt(i + 1) == '0')

step 1: 我们要把十进制数转为二进制数，将二进制数存在一个字符串中，并统计字符串的长度。
step 2: 计算该字符串长度的二进制数所有无连续1的数字个数
step 3: 我们从倒数第二个字符开始往前遍历这个二进制数字符串，如果当前字符和后面一个位置的字符均为1，说明我们并没有多计算任何情况，
         不明白的可以带例子来看。如果当前字符和后面一个位置的字符均为0，说明我们有多计算一些情况，就像之前举的100这个例子，
         我们就多算了101这种情况。
         我们怎么确定多了多少种情况呢，假如给我们的数字是8，二进制为1000，我们首先按长度为4算出所有情况，共8种。仔细观察我们
         十进制转为二进制字符串的写法，发现转换结果跟真实的二进制数翻转了一下，所以我们的t为"0001"，那么我们从倒数第二位开始
         往前遍历，到i=1时，发现有两个连续的0出现，那么i=1这个位置上能出现1的次数，就到one数组中去找，那么我们减去1，减去的
         就是0101这种情况，再往前遍历，i=0时，又发现两个连续0，那么i=0这个位置上能出1的次数也到one数组中去找，我们再减去1，
         减去的是1001这种情况



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



