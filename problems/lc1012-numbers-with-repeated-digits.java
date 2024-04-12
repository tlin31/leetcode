1012. Numbers With Repeated Digits - Hard


Given a positive integer N, return the number of positive integers less than or equal to N that have 
at least 1 repeated digit.

 

Example 1:

Input: 20
Output: 1
Explanation: The only positive number (<= 20) with at least 1 repeated digit is 11.

Example 2:
Input: 100
Output: 10
Explanation: The positive numbers (<= 100) with atleast 1 repeated digit are 11, 22, 33, 44, 55, 66, 
77, 88, 99, and 100.


Example 3:
Input: 1000
Output: 262
 

Note:

1 <= N <= 10^9


******************************************************
key:
	- Math, DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:
	Time: the number of permutations A(m,n) is O(1), We count digit by digit, so its O(logN)


Method:

	-	Count res the Number Without Repeated Digit, then the number with repeated digits = N - res
		
Algo:
	Transform N + 1 to arrayList
	Count the number with digits < n
	Count the number with same prefix

ex.
	if N = 8765, L = [8,7,6,6],
	the number without repeated digit can the the following format:
	XXX
	XX
	X

	1XXX ~ 7XXX
	80XX ~ 86XX
	870X ~ 875X
	8760 ~ 8765


select m out of n
	Permutation = P(m, n) =  n! / (n-m)! = (n * n-1 * n-2 * ... * n-m+1)

Algorithm:

lets say N has k digits.






class Solution {
	public int numDupDigitsAtMostN(int N) {
        List<Integer> nums = new ArrayList<>();
        
        // create the number arraylist
        int tempN = N + 1;
        while(tempN != 0){
            nums.add(0,tempN % 10);
            tempN/=10;
        }

        //1) count total number less than k digits
		// with i digits, first digit 1 ~ 9, following option is 0 ~ 9 without first digit
		// count = 9 * P(i-1,9)
        int countInvalid = 0;
        for(int i = 0; i < nums.size()-1; i++){
            countInvalid += 9 * permutation(9,i);
            System.out.println("i = " + i + ", countInvalid = " + countInvalid);
        }

        int[] isOccupied = new int[10];
        Arrays.fill(isOccupied,-1);
        
        for(int i = 0; i<nums.size(); i++){
            int digit = nums.get(i);
            
            int upper = 0;
            if (i == 0) upper = 1;
                    
    //         2) count number has k digits. 
				// Calculate digits with same prefix. 
				// Prefix cannot has duplicate digits.
				// for case like 77xxx, we should stop the calculation.

            for(int j = upper; j < digit; j++){
                if(isOccupied[j] != -1) 
                	continue;
                else 
                	countInvalid += permutation(10-(i+1), nums.size()-i-1);
            }
            
            if(isOccupied[digit] != -1) 
            	break;
            	
            isOccupied[digit] = 1;
        }
        return N - countInvalid;
    }

    private int permutation(int n, int c){
        int ans = 1;

        // computes c-digits, ex. (9,2) -> 99: 9 * 8 
        for(int i = 0; i<c; i++,n--) 
        	ans *= n;
        return ans;
    }
}


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:
2.1 Analysis
	The problem is to return

	T(N) = the number of positive integers less than or equal to N that have at least 1 repeated digit.

	S(N) = the number of positive integers less than or equal to N that have NO repeated digits.

	Answer = T(N) = N - S(N).
	Later, the calculation of S(N) will be focused on.

2.2 Find the rules
From 1 to 9, there are 9 positive integers that have NO repeated digits.

From 10 to 99:

	 10 to 19, there are 9 positive integers that have NO repeated digits. (Only 11 has repeated digits)
	 20 to 29, there are 9 positive integers that have NO repeated digits. (Only 22 has repeated digits)
	 30 to 39, there are 9 positive integers that have NO repeated digits. (Only 33 has repeated digits)
	 40 to 49, there are 9 positive integers that have NO repeated digits. (Only 44 has repeated digits)
	 50 to 59, there are 9 positive integers that have NO repeated digits. (Only 55 has repeated digits)
	 60 to 69, there are 9 positive integers that have NO repeated digits. (Only 66 has repeated digits)
	 70 to 79, there are 9 positive integers that have NO repeated digits. (Only 77 has repeated digits)
	 80 to 89, there are 9 positive integers that have NO repeated digits. (Only 88 has repeated digits)
	 90 to 99, there are 9 positive integers that have NO repeated digits. (Only 99 has repeated digits)
	there are 9 × 9 = 81 positive integers that have NO repeated digits.

From 100 to 999:

From 100 to 199,
100 to 109, there are 8 positive integers that have NO repeated digits. (100 and 101 have repeated digits)
110 to 119, there are 0 positive integers that have NO repeated digits. (ALL numbers have repeated digits because of the prefix 11)
120 to 129, there are 8 positive integers that have NO repeated digits. (121 and 122 have repeated digits)
130 to 139, there are 8 positive integers that have NO repeated digits. (131 and 133 have repeated digits)
140 to 149, there are 8 positive integers that have NO repeated digits. (141 and 144 have repeated digits)
150 to 159, there are 8 positive integers that have NO repeated digits. (151 and 155 have repeated digits)
160 to 169, there are 8 positive integers that have NO repeated digits. (161 and 166 have repeated digits)
170 to 179, there are 8 positive integers that have NO repeated digits. (171 and 177 have repeated digits)
180 to 189, there are 8 positive integers that have NO repeated digits. (181 and 188 have repeated digits)
190 to 199, there are 8 positive integers that have NO repeated digits. (191 and 199 have repeated digits)

there are 9 × 8 = 72 positive integers that have NO repeated digits.
.....
Lets think about all positive integers from 100 to 199.

They can be generated by adding a new digit from 0 to 9 to the end of all positive integers from 10 to 19.
In order to generate a new positive integer that has NO repeated digits,

To 10: 10 has NO repeated digits. There are 8 choices (0 and 1 can NOT be chosen).
To 11: 11 has repeated digits. There are 0 choices (0 to 9 can NOT be chosen).
To 12: 12 has NO repeated digits. There are 8 choices (1 and 2 can NOT be chosen).
To 13: 13 has NO repeated digits. There are 8 choices (1 and 3 can NOT be chosen).
To 14: 14 has NO repeated digits. There are 8 choices (1 and 4 can NOT be chosen).
To 15: 15 has NO repeated digits. There are 8 choices (1 and 5 can NOT be chosen).
To 16: 16 has NO repeated digits. There are 8 choices (1 and 6 can NOT be chosen).
To 17: 17 has NO repeated digits. There are 8 choices (1 and 7 can NOT be chosen).
To 18: 18 has NO repeated digits. There are 8 choices (1 and 8 can NOT be chosen).
To 19: 19 has NO repeated digits. There are 8 choices (1 and 9 can NOT be chosen).

== Pattern ===

	1. A k-digit positive integer with NO repeated digits can ONLY be generated from (k - 1)-digit 
	   positive integers with NO repeated digits (k > 1).

	2. To generate a k-digit positive integer with NO repeated digits, there are (10 - (k - 1)) digits 
	   that can be added to (k - 1)-digit positive integers with NO repeated digits (k > 1).

== Recursion ==

	f(i, j, k) =  The number of i-digit positive integers with NO repeated digits in the interval 
	              [j, k]. (i > 0, j ≤ k, j and k are i-digit positive integers).

	f(i, j, k) = k - j + 1  						if i = 1.
	f(i + 1, 10j, 10k + 9) = f(i, j, k) × (10 - i)  others.

ex.
	If N = 26334,
	From 1 to 9, f(1, 1, 9) = 9.
	From 10 to 99, f(2, 10, 99) = f(1, 1, 9) × (10 - 1) = 9 × 9 = 81.
	From 100 to 999, f(3, 100, 999) = f(2, 10, 99) × (10 - 2) = 81 × 8 = 648.
	From 1000 to 9999, f(4, 1000, 9999) = f(3, 100, 999) × (10 - 3) = 648 × 7 = 4536.


	sum = S(9999) = f(1, 1, 9) + f(2, 10, 99) + f(3, 100, 999) + f(4, 1000, 9999) 
	              = 9 + 81 + 648 + 4536 
	              = 5274.
				  = number of positive integers with NO repeated digits less than or equal to 9999 


	Now find the number of positive integers with NO repeated digits in interval [10000, 26334]
	, which is = f(5, 10000, 26334).

	- f(5, 10000, 26334) the calculation series is:
		f(1, 1, 2), f(2, 10, 26), f(3, 100, 263), f(4, 1000, 2633), f(5, 10000, 26334).

		From 1 to 2, f(1, 1, 2) = 2.
		From 10 to 26: 
			f(2, 10, 26) = f(2, 10, 29) - f(2, 27, 29) = f(1, 1, 2) × (10 - 1) = 2 × 9 - 3 = 15

		From 100 to 263:
			f(3, 100, 263) = f(3, 100, 269) - f(3, 264, 269) = 120 - 5 = 115.

		
		From 1000 to 2639:
			f(4, 1000, 2639) = f(3, 100, 263) × (10 - 3) = 115 × 7 = 805.

		From 2634 to 2639, there are 5 positive integers with NO repeated digits (except 2636), which means f(4, 2634, 2639) = 5.

			f(4, 1000, 2633) = f(4, 1000, 2639) - f(4, 2634, 2639) = 805 - 5 = 800.

		From 10000 to 26339, 
			f(4, 10000, 26339) = f(4, 1000, 2633) × (10 - 4) = 800 × 6 = 4800.

		From 26335 to 26339, there are NO positive integers with NO repeated digits (due to the prefix "2633"), which means 
			f(5, 26335, 26339) = 0.

		Thus, f(5, 10000, 26334) = f(4, 10000, 26339) - f(5, 26335, 26339) = 4800 - 0 = 4800.

		S(26334) = S(9999) + f(5, 10000, 26334) = 5274 + 4800 = 10074.

		T(26334) = 26334 - S(26334) = 26334 - 10074 = 16260.

https://leetcode.com/problems/numbers-with-repeated-digits/discuss/258212/Share-my-O(logN)-C%2B%2B-DP-solution-with-proof-and-explanation



=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	

Let's first consider about the cases where N=10^k
N=10
the free digits are marked as *, so we only need to consider about * and 1*

*: obviously all 1-digit numbers are non-repeated, so non-repeated number = 9
1*: we only need to consider about 1* <= 10, so non-repeated number = 1
Thus, the result for N=10 is:
N - #non_repeat(*) - #non_repeat(1*) = 10 - 9 - 1 = 0

N=100
the free digits are marked as *, so we only need to consider about *, **, and 1**

*: obviously all 1-digit numbers are non-repeated, so non-repeated number = 9
**: this can be calculated with permutation: leading digit has 9 options(1-9) and the last 1 digit has 10-1 options, thus the total permuation is 9 * permutation(9, 1)=81. i.e: non-repeated number = 81
1**: we only need to consider about 1**<=100, so non-repeated number =0
Thus, the result for N=100 is:
N - #non_repeat(*) - #non_repeat(**) - #non_repeat(1**) = 100 - 9 - 81 = 10

N=1000
#non_repeat(***) = 9 * permutation(9, 2) = 9 * 9 * 8 = 648
similarly, we can get:
N - #non_repeat(*) - #non_repeat(**) - #non_repeat(***) - #non_repeat(1***) = 1000 - 9 - 81 - 648 = 282

Now, let's consider a more general case:
N=12345
actually, we can get the count of non-repeated numbers by counting all non-repeated numbers in following patterns:

    *
   **
  ***
 ****
10***
11*** (prefix repeated, skip)
120**
121** (prefix repeated, skip)
122** (prefix repeated, skip)
1230*
1231* (prefix repeated, skip)
1232* (prefix repeated, skip)
1233* (prefix repeated, skip)
12340
12341 (prefix repeated, skip)
12342
12343
12344 (prefix repeated, skip)
12345

