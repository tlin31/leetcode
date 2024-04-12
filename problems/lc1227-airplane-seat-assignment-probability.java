1227. Airplane Seat Assignment Probability - Medium

n passengers board an airplane with exactly n seats. The first passenger has lost the ticket and 
picks a seat randomly. But after that, the rest of passengers will:

	1. Take their own seat if it is still available, 
	2. Pick other seats randomly when they find their seat occupied 

What is the probability that the n-th person can get his own seat?


Example 1:

Input: n = 1
Output: 1.00000
Explanation: The first person can only get the first seat.

Example 2:
Input: n = 2
Output: 0.50000
Explanation: The second person has a probability of 0.5 to get the second seat (when first person 
gets the first seat).
 

Constraints:

1 <= n <= 10^5


******************************************************
key:
	- Math
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

	-  For any case, we can get rid of those sitting on own seats (except the first passenger) and 
	   get a problem of n' (= n - k, where k is the number of passengers sitting on own seats), 
	   then re-number (without changing the relative order) them as passenger 1, 2, ..., n', hence 
	   the result is in same form, the only difference is to change n to n'.

	-  Except n' = 1, results for n' of other values are independent on n'. In short, changing from 
	   n to n' will not influence the result.

Part 1: 
	- For the 1st passenger, there are 2 cases that the nth passenger could take the right seat:

		1. Take his own seat, the probability is 1 / n;
		2. Take a seat neither his own nor the one of the nth passenger, and the corresponding
		   probability is (n - 2) / n; 

		   In addition, other passengers (except the nth one) should not occupy the nth seat;

Now there are n - 1 passengers and n - 1 seats remaining, and the 2nd passenger, like the 1st one, 
have 2 options to make it possible the nth passenger take the right seat:
	
	a) take the 1st passenger's seat, the probability is 1 / (n - 1);
	b) Take a seat that is neither the 1st passenger's nor the nth passenger's, and the corresponding
	   probability is ((n - 1) - 2) /( n - 1);


Obviouly, we recurse to subproblem of (n - 1) .

Code:

    public double nthPersonGetsNthSeat(int n) {
        if (n == 1) return 1.0d;
        return 1d / n + (n - 2d) / n * nthPersonGetsNthSeat(n - 1);
    }

Based on the code in part 1, we have the following formula:

f(n) = 1 / n + (n - 2) / n * f(n - 1)



Part2: Proof when n > 1, the f(n) is 1/2
n = 2, we have f(2) = 1/2; the assumption holds;
Suppose n = k we have f(k) = 1/2, when n = k + 1,
f(k + 1) = 1 / (k + 1) + (k + 1 - 2) / (k + 1) * f(k)
         = 2 / (2 * (k + 1)) + (k - 1) / (k + 1) * 1/2
         = 1 / 2
That is, f(k + 1) = 1 / 2 also holds.


    public double nthPersonGetsNthSeat(int n) {
        return n == 1 ? 1.0d : .5d;
    }

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



