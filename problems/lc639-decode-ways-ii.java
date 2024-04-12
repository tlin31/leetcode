639. Decode Ways II - Hard


A message containing letters from A-Z is being encoded to numbers using the following mapping way:

	'A' -> 1
	'B' -> 2
	...
	'Z' -> 26

Beyond that, now the encoded string can also contain the character '*', which can be treated as one 
of the numbers from 1 to 9.

Given the encoded message containing digits and the character '*', return the total number of ways 
to decode it.

Also, since the answer may be very large, you should return the output mod 109 + 7.

Example 1:
Input: "*"
Output: 9
Explanation: The encoded message can be decoded to the string: "A", "B", "C", "D", "E", "F", "G",
 "H", "I".


Example 2:
Input: "1*"
Output: 9 + 9 = 18


Note:
The length of the input string will fit in range [1, 105].
The input string will only contain the character '*' and digits '0' - '9'.



******************************************************
key:
	- DP, recursive --> # at s[i] = num ways for s[i] * (num ways of s[0 ~ i-1])
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: recursion


Stats:

	- 
	- 


Method:

	-	
	-	


public class Solution {
    int M = 1000000007;
    public int numDecodings(String s) {
        Integer[] memo=new Integer[s.length()];
        return ways(s, s.length() - 1,memo);
    }

    public int ways(String s, int i,Integer[] memo) {
        if (i < 0)
            return 1;
        if(memo[i]!=null)
            return memo[i];

        if (s.charAt(i) == '*') {
            long res = 9 * ways(s, i - 1, memo);
            if (i > 0 && s.charAt(i - 1) == '1')
                res = (res + 9 * ways(s, i - 2,memo)) % M;

            else if (i > 0 && s.charAt(i - 1) == '2')
                res = (res + 6 * ways(s, i - 2,memo)) % M;

            else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + 15 * ways(s, i - 2,memo)) % M;
            memo[i]=(int)res;
            return memo[i];
        }

        long res = s.charAt(i) != '0' ? ways(s, i - 1,memo) : 0;
        if (i > 0 && s.charAt(i - 1) == '1')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '2' && s.charAt(i) <= '6')
            res = (res + ways(s, i - 2,memo)) % M;
        else if (i > 0 && s.charAt(i - 1) == '*')
                res = (res + (s.charAt(i)<='6'?2:1) * ways(s, i - 2,memo)) % M;
        memo[i]= (int)res;
        return memo[i];
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP

Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    private static final int M = 1000000007;

    // function of addition with mod
    private long add(long num1, long num2) {
        return (num1 % M + num2 % M) % M;
    }

    // how many ways to decode using one char
    private long ways(char a) {
        return (a == '*') ? 9 : (a != '0') ? 1 : 0; // simple way to write if else statement
    }

    // how many ways to decode using two chars
    private long ways(char a, char b) {
        if (a == '*' && b == '*') { // "**" neither is digit --> * only represent [1 - 9] , not [0 - 9]
        							// 11 ~ 26
            return 15;
        } 

        else if (a == '*') {      // "*D" second is a digit
            return (b > '6') ? 1 : 2;
        } 

        else if (b == '*') {      // "D*" first is a digit
            return (a == '1') ? 9 : (a == '2') ? 6 : 0;
        } 

        else {                    // "DD" both r digits
            int val = Integer.valueOf("" + a + b);
            return (10 <= val && val <= 26) ? 1 : 0;
        }
    } 
    
    public int numDecodings(String s) {
     	// use long to prevent overflow
        long[] dp = new long[s.length() + 1];
        dp[0] = 1;
        dp[1] = ways(s.charAt(0));

        // ! s.charAt(i)'s result is stored in dp[i + 1]
        for (int i = 1; i < s.length(); i++) { 
            long oneCharWays = ways(s.charAt(i)) * dp[i];
            long twoCharWays = ways(s.charAt(i - 1), s.charAt(i))  * dp[i - 1];
            dp[i + 1] = add(oneCharWays, twoCharWays);
        }
        return (int)dp[s.length()]; // cast back
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
