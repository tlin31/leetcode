793. Preimage Size of Factorial Zeroes Function - Hard

Let f(x) be the number of zeroes at the end of x!. (Recall that x! = 1 * 2 * 3 * ... * x, and by 
convention, 0! = 1.)

For example, f(3) = 0 because 3! = 6 has no zeroes at the end, while f(11) = 2 because 11! = 39916800 
has 2 zeroes at the end. 

Given K, find how many non-negative integers x have the property that f(x) = K.

Example 1:
Input: K = 0
Output: 5
Explanation: 0!, 1!, 2!, 3!, and 4! end with K = 0 zeroes.

Example 2:
Input: K = 5
Output: 0
Explanation: There is no x such that x! ends in K = 5 zeroes.
Note:

K will be an integer in the range [0, 10^9].



******************************************************
key:
	- binary search + find patterns. --> exactly K = (trailing zero < K) - (trailing zero < K-1) 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- The number of trailing zeros of the factorial x! is given by the minimum of num_2 and num_5, 
	  where the former is the total number of factor 2 of integers in the range [1, x], while the latter
	  is the total number of factor 5 of these integers. 

	  Since we always have more factors of 2 than 5, the number of trailing zeros of x! will always 
	  be num_5.

	  num_5 of x! can be computed by summing up the count of integers in the range [1, x] that are 
	  integer multiples of 5, 25, 125, ..., 5^k, ..., etc. use the helper funciton numOfTrailingZeros


	- Given two non-negative integers x and y, if x <= y, then numOfTrailingZeros(x) <= numOfTrailingZeros(
	   y), which implies numOfTrailingZeros is a non-decreasing function of x.

	- do binary solution on the range of integers such that the numOfTrailingZeros function is 
	  evaluated to K

	- use binary search to find the largest integers x and y such that numOfTrailingZeros(x) <= K 
	  and numOfTrailingZeros(y) <= K-1, respectively. 

	Then the factorial of all integers in the range (y, x] (left exclusive, right inclusive) will have
	K trailing zeros. Therefore the total number of non-negative integers whose factorials have K 
	trailing zeros will be given by x - y.

	Note that x! will always have at least x/5 trailing zeros, therefore, if x is the largest 
	integer such that x! has no more than K trailing zeros, then we have x <= 5 * (K + 1), 
	which can serve as the upper bound of our binary search.


stats:

	- Time Complexity: O(log^2 K) Our binary search is O(logK), and in each step of that binary search we 
					   do O(logK) work to evaluate the function zeta.

	- Space Complexity: O(1), the size of our recursive call stack when calling zeta.



public int preimageSizeFZF(int K) {
    return (int)(binarySearch(K) - binarySearch(K - 1));
}
    
private long binarySearch(int K) {
    long l = 0, r =  5L * (K + 1);
    
    while (l <= r) {
        long m = l + (r - l) / 2;
        long numTrailing = numOfTrailingZeros(m);
        
        if (numTrailing <= K) {
            l = m + 1;
        } else {
            r = m - 1;
        }
    }
    
    return r;
}

private long numOfTrailingZeros(long x) {
    long res = 0;
		
    for (; x > 0; x /= 5) {
        res += x/5;
    }
		
    return res;
}


ex. k = 3, then l = 0, r = 20. 

round 1: mid = 10, 
 		 res = 0 + 2 = 2.
		 since 2 < 3, l = 11
round 2: l = 11, r = 20, mid = 15
		 res = 0 + 3 = 3
		 since 3<=3, l = 16


K = 2 l = 11 r = 20
K = 3 l = 16 r = 20
K = 3 l = 19 r = 20
K = 3 l = 20 r = 20
K = 4 l = 20 r = 19


K = 1 l = 8 r = 15
K = 2 l = 12 r = 15
K = 2 l = 14 r = 15
K = 2 l = 15 r = 15
K = 3 l = 15 r = 14

=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 


class Solution {
    public int preimageSizeFZF(int K) {
        long lo = K, hi = 5*(long)K;
        while (lo < hi) {
            long mi = lo + (hi - lo) / 2;
            long zmi = zeta(mi);
            if (zmi == K) return 5;
            else if (zmi < K) lo = mi + 1;
            else hi = mi;
        }
        if (zeta(lo) == (long)K) return 5;
        return 0;
    }

    public long zeta(long x) {
        if (x == 0) return 0;
        return x/5 + zeta(x/5);
    }
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



