788. Rotated Digits - Easy

X is a good number if after rotating each digit individually by 180 degrees, we get a valid number that 
is different from X.  Each digit must be rotated - we cannot choose to leave it alone.

A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 
2 and 5 rotate to each other (in other words 2 or 5 gets mirrored); 
6 and 9 rotate to each other, 
and the rest of the numbers do not rotate to any other number and become invalid.

Now given a positive number N, how many numbers X from 1 to N are good?

Example:
Input: 10
Output: 4
Explanation: 
There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
Note:

N  will be in range [1, 10000].



******************************************************
key:
	- DP or brute force + pruning
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

	-	Valid if N contains AT LEAST ONE 2, 5, 6, 9 AND NO 3, 4 or 7s
	
Increments i if the leading digit of i is 3, 4 or 7.
For example, if i = 300, then we know 300, 301, 302 ... 399 are all invalid. 

IncrementIfNeeded(int i) returns 99 to set i to 399 and 400 after i++ from loop increment.
The same thing happens for 400.
Therefore, we only check 300 and 400, rather than 300, 301, 302, ..., 399, 400, 401, 402, ..., 499.

class Solution {

	// main function
    public int rotatedDigits(int N) {
        int count = 0;

        // check for every possible N
        for (int i = 1; i <= N; i ++) {
            if (isValid(i)) 
            	count ++;
            
            i += incrementIfNeeded(i);
        }
        return count;
    }
    
    public int incrementIfNeeded(int i) {
        int inc = 1;

        // get the most significant bit in number i, prune here.
        while (i >= 10) {
            inc *= 10;
            i /= 10;
        }

        // now i is the most significant bit
        if (i == 3 || i == 4 || i == 7) 
        	return inc - 1;
        else 
        	return 0;
    }
    
    public boolean isValid(int N) {

        boolean validFound = false;
        while (N > 0) {
            if (N % 10 == 2) validFound = true;
            if (N % 10 == 5) validFound = true;
            if (N % 10 == 6) validFound = true;
            if (N % 10 == 9) validFound = true;
            if (N % 10 == 3) return false;
            if (N % 10 == 4) return false;
            if (N % 10 == 7) return false;
            N = N / 10;
        }
        return validFound;
    }
}

=======================================================================================================
method 2:

Stats:

	- O(N)
	- 


Method:

	Using a int[] for dp.
		dp[i] = 0, invalid number             --> 3, 4, 7
		dp[i] = 1, valid and same number      --> 0, 1, 8
		dp[i] = 2, valid and different number --> 2, 5, 6, 9



    public int rotatedDigits(int N) {
        int[] dp = new int[N + 1];
        int count = 0;

        // loop through every number
        for(int i = 0; i <= N; i++){
            if(i < 10){
                if(i == 0 || i == 1 || i == 8) 
                	dp[i] = 1;
                else if(i == 2 || i == 5 || i == 6 || i == 9){
                    dp[i] = 2;
                    count++;
                }
            } 

            else {
                int multiples = dp[i / 10], 
                    remainder = dp[i % 10];

                // get the subproblem stored result, ex. 112 --> multiples = 11, remainder = 2
                // dp[multiples] = 1, remainder = 2

                // means the left sub & right sub are valid and is the same number
                if(multiples == 1 && remainder == 1) 
                	dp[i] = 1;

                else if(multiples >= 1 && remainder >= 1){
                    dp[i] = 2;
                    count++;
                }
            }
        }
        return count;
    }

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	- Here is solution in O(logN) complexity.

def rotatedDigits(self, N):
        s1 = set([0, 1, 8])
        s2 = set([0, 1, 8, 2, 5, 6, 9])
        s = set()
        res = 0
        N = map(int, str(N))
        for i, v in enumerate(N):
            for j in range(v):
                if s.issubset(s2) and j in s2:
                    res += 7**(len(N) - i - 1)
                if s.issubset(s1) and j in s1:
                    res -= 3**(len(N) - i - 1)
            if v not in s2:
                return res
            s.add(v)
        return res + (s.issubset(s2) and not s.issubset(s1))


