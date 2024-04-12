306. Additive Number - Medium

Additive number is a string whose digits can form additive sequence.

A valid additive sequence should contain at least three numbers. Except for the first two 
numbers, each subsequent number in the sequence must be the sum of the preceding two.

Given a string containing only digits '0'-'9', write a function to determine if it is an additive number.

Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 
1, 02, 3 is invalid.

 

Example 1:

Input: "112358"
Output: true
Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8. 
             1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
Example 2:

Input: "199100199"
Output: true
Explanation: The additive sequence is: 1, 99, 100, 199. 
             1 + 99 = 100, 99 + 100 = 199
 

Constraints:

num consists only of digits '0'-'9'.
1 <= num.length <= 35

Follow up:
How would you handle overflow for very large input integers?

******************************************************
key:
	- Key: determine the range of the first possible number, then try recursively
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	generate the first and second of the sequence, check if the rest of the string match the 
	    sum recursively. 
	-   i and j are length of the first and second number. i should in the range of [0, n/2]. 
	    The length of their sum should >= max(i,j)
	    
	    	Math.max(i, j) <= n - i - j

	-	Note: If no overflow, instead of BigInteger we can consider to use Long which is a lot faster.


Stats:

	- Runtime: 3 ms, faster than 27.40% of Java online submissions for Additive Number.
	- Memory Usage: 38.4 MB, less than 14.29%



import java.math.BigInteger;

public class Solution {
    
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        
        /**
         * Iterate over possible first numbers.
         * 
         * The first number is defined as num.substring(0, i),
         * inclusive of 0, and exclusive of i.
         * 
         * Note that the first number, when added to a second number of any
         * length, must produce a sum that is at least the length of the first
         * number.
         * 
         * Accordingly, the first number cannot be more than half the length
         * of the given String num.
         * 
         * So we can stop iterating when i reaches (n / 2) + 1.
         * 
         * Note also that when num.length < 2, this loop exits, thus handling
         * some of the invalid-length inputs.
         */
        for (int i = 1; i <= n / 2; i++) {
            
            /**
             * Iterate over possible second numbers.
             * 
             * The second number is defined as num.substring(i, i + j),
             * inclusive of i, and exclusive of i + j.
             * 
             * Note that the second number, when added to a first number of any
             * length, must produce a sum whose length is at least
             * the maximum of either i or j.
             * 
             * Accordingly, if n is the length of the given num, and i of those
             * digits belong to the first number, and j of those digits belong
             * to the second number, then (n - i - j) digits are left for the
             * sum.
             * 
             * Since the length of the sum is at least max(i, j), we can stop
             * iterating when max(i, j) exceeds n - i - j.
             * 
             * Note also that when num.length == 2, this loop exits, thus
             * handling the remaining invalid-length input case.
             */
            for (int j = 1; Math.max(i, j) <= n - i - j; j++) {
                if (isValid(i, j, num)) 
                	return true;
            }
        }
        return false;
    }
    
    private boolean isValid(int i, int j, String num) {
        
        // Check for leading zero in first number.
        // it is ok to be zero, but can't be more than one digit long with zero as the first digit.
        if (num.charAt(0) == '0' && i > 1) 
        	return false;
        
        // Check for leading zero in second number.
        if (num.charAt(i) == '0' && j > 1) 
        	return false;
        
        String sum;
        BigInteger b1 = new BigInteger(num.substring(0, i));
        BigInteger b2 = new BigInteger(num.substring(i, i + j));
        
        /**
         * Check whether num obeys the Additive pattern described in the 
         * problem statement.
         * 
         * (1) Compute their sum,
         * (2) Update the second number to be this sum (for the next iteration),
         * (3) Update the first number to be the previous second number,
         * (4) Check whether the next number in the given num is sum,
         * (5) Repeat until the start of the next sum exceeds the last index
         *     of the given num.
         * 
         * Note that offset below is our "offset" for where the next sum
         * comparison should occur in the given num.
         * 
         * Thus, offset initializes to the lengths of the first and second
         * numbers (due to zero-indexing, offset = 2 starts sum at the third
         * digit of num).
         * 
         * Note also that offset increments by the length of the previous sum,
         * which cleverly handles the sum's tendency to grow in length.
         */
        for (int offset = i + j; offset < num.length(); offset += sum.length()) {
            
            // The second number becomes the sum of the first and second numbers
            b2 = b2.add(b1);
            
            // The first number becomes the second number
            b1 = b2.subtract(b1);
            
            sum = b2.toString();
            
            // Check whether the next digits of num, at an offset of offset, are sum
            if (!num.startsWith(sum, offset)) 
            	return false;
        }
        return true;
    }
}

recursive:

import java.math.BigInteger;

public class Solution {
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        for (int i = 1; i <= n / 2; ++i) {
            if (num.charAt(0) == '0' && i > 1) 
            	return false;
            BigInteger x1 = new BigInteger(num.substring(0, i));
            for (int j = 1; Math.max(j, i) <= n - i - j; ++j) {
                if (num.charAt(i) == '0' && j > 1) 
                	break;

                BigInteger x2 = new BigInteger(num.substring(i, i + j));

                if (isValid(x1, x2, j + i, num)) 
                	return true;
            }
        }
        return false;
    }
    private boolean isValid(BigInteger x1, BigInteger x2, int start, String num) {
        if (start == num.length()) return true;
        x2 = x2.add(x1);
        x1 = x2.subtract(x1);
        String sum = x2.toString();
        return num.startsWith(sum, start) && isValid(x1, x2, start + sum.length(), num);
    }
}
// Runtime: 8ms

=======================================================================================================
method 2:

Method:

	-	DFS
	-	


Stats:

	- 
	- 
public class Solution {
    public boolean isAdditiveNumber(String s) {
        int n = s.length();
        for (int i=1; i<n; i++) {
            for (int j=i+1; j<n; j++) {
                long a = parse(s.substring(0, i));
                long b = parse(s.substring(i, j));
                if (a == -1 || b == -1) continue;
                if (dfs(s.substring(j), a, b))   return true;
            }
        }
        return false;
    }
    
    boolean dfs(String s, long a, long b) {
        if (s.length() == 0)    return true;
        
        for (int i=1; i<=s.length(); i++) {
            long c = parse(s.substring(0, i));
            if (c == -1)    continue;
            if (c-a == b && dfs(s.substring(i), b, c)) {
                return true;
            }
        }
        return false;
    }
    
    long parse(String s) {
        if (!s.equals("0") && s.startsWith("0"))    
        	return -1;
        long result = 0;
        try {
            result = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return -1;
        }
        return result;
    }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



