1067. Digit Count in Range - Hard


Given an integer d between 0 and 9, and two positive integers low and high as lower and upper bounds, 
respectively. Return the number of times that d occurs as a digit in all integers between low and 
high, including the bounds low and high.
 

Example 1:

Input: d = 1, low = 1, high = 13
Output: 6
Explanation: 
The digit d=1 occurs 6 times in 1,10,11,12,13. Note that the digit d=1 occurs twice in the number 11.


Example 2:

Input: d = 3, low = 100, high = 250
Output: 35
Explanation: 
The digit d=3 occurs 35 times in 103,113,123,130,131,...,238,239,243.
 

Note:

0 <= d <= 9
1 <= low <= high <= 2×10^8


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
	-	

https://leetcode.com/articles/number-of-digit-one/

1. when d > 0, the remainder should be larger than i * d instead of i;
2. when d == 0, when analyzing the remainder, we need avoid taking numbers with "heading zero" like 
   0xxxx into the total count.

class Solution {
    public int digitsCount(int d, int low, int high) {
        return countDigit(high, d) - countDigit(low-1, d);
    }
    
    int countDigit(int n, int d) {
        if(n < 0 || n < d) {
            return 0;
        }
        
        int count = 0;
        for(long i = 1; i <= n; i*= 10) {
            long divider = i * 10;
            count += (n / divider) * i;
            
            if (d > 0) {
            	// comment1: tailing number need to be large than d *  i to qualify.
                count += Math.min(Math.max(n % divider - d * i + 1, 0), i); 
            } else {
            	// comment2: when d == 0, we need avoid to take numbers like 0xxxx into account.
                if(n / divider > 0) {
                    if(i > 1) {  
                        count -= i;
                        count += Math.min(n % divider + 1, i);  
                    }
                }
            }
        }
        
        return count;
    }
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def digitsCount(self, d, low, high):
        def count(N):
            if N == 0: return 0
            if d == 0 and N <= 10: return 0
            res = 0
            if N % 10 > d: res += 1
            if N / 10 > 0: res += str(N / 10).count(str(d)) * (N % 10)
            res += N / 10 if d else N / 10 - 1
            res += count(N / 10) * 10
            return res
        return count(high + 1) - count(low)

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










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

