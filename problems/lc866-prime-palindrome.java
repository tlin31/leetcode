866. Prime Palindrome - Medium

Find the smallest prime palindrome greater than or equal to N.

Recall that a number is prime if it is only divisors are 1 and itself, and it is greater than 1. 

For example, 2,3,5,7,11 and 13 are primes.

Recall that a number is a palindrome if it reads the same from left to right as it does from right 
to left. 

For example, 12321 is a palindrome.


Example 1:

Input: 6
Output: 7


Example 2:

Input: 8
Output: 11

Example 3:
Input: 13
Output: 101
 

Note:

1 <= N <= 10^8
The answer is guaranteed to exist and be less than 2 * 10^8.


******************************************************
key:
	- math trick
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Write some example, you find all even length palindromes are divisible by 11.
	- So we need to search only palindrome with odd length

		All palindrome with even length is multiple of 11.
		So among them, 11 is the only one prime
		if (8 <= N <= 11) return 11

	- For other cases, we consider only palindrome with odd dights. 
	  Store the reverse in r, and then append from the 2nd char to last char to new int y.
	   	int y = Integer.parseInt(s + r.substring(1));

	- y here is 101, 111, 121, 131,...202, 212, 222, ... 909, 919, ...999, 10001 (all odd length)


stats:
	- Time: O(10000) to check all numbers 1 - 10000, isPrime function is O(sqrt(x)) worst case.
		    But only sqrt(N) worst cases for 1 <= x <= N
	        In general it is O(logx)
	- Runtime: 37 ms, faster than 64.35% of Java online submissions for Prime Palindrome.
	- Memory Usage: 41 MB, less than 50.00% of Java online submissions for Prime Palindrome.



 public int primePalindrome(int N) {
        if (8 <= N && N <= 11) return 11;
        for (int x = 1; x < 100000; x++) {
            String s = Integer.toString(x), 
                   r = new StringBuilder(s).reverse().toString();
            int y = Integer.parseInt(s + r.substring(1));
            if (y >= N && isPrime(y)) 
            	return y;
        }
        return -1;
    }

    public Boolean isPrime(int x) {
        if (x < 2 || x % 2 == 0) 
        	return x == 2;
        for (int i = 3; i * i <= x; i += 2)
            if (x % i == 0) 
            	return false;

        return true;
    }

=======================================================================================================
method 2:


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



