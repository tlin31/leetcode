564. Find the Closest Palindrome - Hard


Given an integer n, find the closest integer (not including itself), which is a palindrome.

The 'closest' is defined as absolute difference minimized between two integers.

Example 1:
Input: "123"
Output: "121"
Note:
The input n is a positive integer represented by string, whose length will not exceed 18.
If there is a tie, return the smaller one as answer.



******************************************************
key:
	- math, pattern, string
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

	-  get first half, then compare 5 cases: +0(itself not palindrome), +1 / -1 / 9...9 / 10...01 
	   (itself palindrome)
	-	

class Solution {
    public String nearestPalindromic(String n) {
        // edge case 1: leading 0s
        int len = n.length();

        int start = 0;
        while (start < len && n.charAt(start) == '0')
            start ++;
        
        n = n.substring(start);
        System.out.println(n);
        len = n.length();

        int i = len % 2 == 0 ? len / 2 - 1: len / 2;
        long left = Long.parseLong(n.substring(0, i+1));
        
        // input: n 12345, left = 123
        List<Long> candidate = new ArrayList<>();
        candidate.add(getPalindrome(left, len % 2 == 0)); // 12321
        candidate.add(getPalindrome(left+1, len % 2 == 0)); // 12421
        candidate.add(getPalindrome(left-1, len % 2 == 0)); // 12221
        candidate.add((long)Math.pow(10, len-1) - 1); // 9999
        candidate.add((long)Math.pow(10, len) + 1); // 100001
        
        long diff = Long.MAX_VALUE, 
             res = 0, 
             nl = Long.parseLong(n);

        for (long cand : candidate) {
            if (cand == nl) continue;
            if (Math.abs(cand - nl) < diff) {
                diff = Math.abs(cand - nl);
                res = cand;
            } else if (Math.abs(cand - nl) == diff) {
                res = Math.min(res, cand);
            }
        }
        
        return String.valueOf(res);
    }
    
    private long getPalindrome(long left, boolean even) {
        long res = left;

        // 123 --> 12, so only add '21' to left = 12321
        if (!even) 
        	left = left / 10;
        while (left > 0) {
            res = res * 10 + left % 10;
            left /= 10;
        }
        return res;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


candidates.add(i + [i, i[:-1]][l & 1][::-1])

1. [i, i[:-1]] --> generates [123,12] 
2. [l&1] --> if l is odd, then l&1 = 1; if l is even, then l&1 = 0
   [i, i[:-1]][l&1] --> if l is odd, take the 1st element, which is 12, and forms 12321
   						if l is even, takes the 0th element, which is 123, and forms 123321


class Solution(object):
    def nearestPalindromic(self, n):
        """
        :type n: str
        :rtype: str
        """
        # remove leading zeros
        n = str(int (n))
        l = len(n)

        # create candidates set to store all possible candidates
        # with different digits width, it must be either 10...01 or 9...9
        candidates = set((str(10 ** l + 1), str(10 ** (l - 1) - 1)))

        # the closest must be in middle digit +1, 0, -1, then flip left to right
        # prefix = 123
        prefix = int(n[:(l + 1)/2])
        for i in map(str, (prefix - 1, prefix, prefix + 1)):

            candidates.add(i + [i, i[:-1]][l & 1][::-1])
        candidates.discard(n)
        return min(candidates, key=lambda x: (abs(int(x) - int(n)), int(x)))

        
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

