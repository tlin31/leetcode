1031. Maximum Sum of Two Non-Overlapping Subarrays - Medium


Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping 
(contiguous) subarrays, which have lengths L and M.  (For clarification, the L-length subarray 
could occur before or after the M-length subarray.)

Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... 
+ A[j+M-1]) and either:

0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
0 <= j < j + M - 1 < i < i + L - 1 < A.length.
 

Example 1:

Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
Output: 20
Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.


Example 2:

Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
Output: 29
Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.


Example 3:

Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
Output: 31
Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.
 

Note:

L >= 1
M >= 1
L + M <= A.length <= 1000
0 <= A[i] <= 1000



******************************************************
key:
	- calculate prefix sum array + sliding window
	- edge case:
		1) empty array, return 0
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Two pass, O(N) time, O(1) extra space.
	- 


Method:
	- if L is always before M, we maintain a Lmax to keep track of the max sum of L subarray, and 
	  sliding the window of M from left to right to cover all the M subarray.

	- 2 cases: 
		1) L array appearing first and then M array
		2) M array appearing first and then L array

	Therefore for a given index i in the loop:
		1) find max sum for L length before index i and add it with every M length sum right to it

		2) find max sum for M length before index i and add it with every L length sum right to it
		
		3) now in every loop do res = max(res , max( equation 1, equation 2))
			lMax + preSum[i] - preSum[i - M] --> L sum from lmax + the left M  


class Solution {
	public int maxSumTwoNoOverlap(int[] A, int L, int M) {
	    if (A == null || A.length == 0) {
	        return 0;
	    }

	    int n = A.length;
	    int[] preSum = new int[n + 1];
	    for (int i = 0; i < n; i++) {
	        preSum[i + 1] = A[i] + preSum[i];
	    }

	    int lMax = preSum[L], mMax = preSum[M];
	    int res = preSum[L + M];

	    for (int i = L + M; i <= n; i++) {
	        //case 1: L subarray is always before M subarray
	        // p[i - M] - p[i - M - L] is L-length subarray from index i - M - L to i - M - 1
	        lMax = Math.max(lMax, preSum[i - M] - preSum[i - M - L]);

	        //case 2: M subarray is always before L subarray
	        mMax = Math.max(mMax, preSum[i - L] - preSum[i - M - L]);

	        //compare two cases and update res
	        // preSum[i] - preSum[i - M] = subarray of length M
	        res = Math.max(res, Math.max(lMax + preSum[i] - preSum[i - M], 
	                                     mMax + preSum[i] - preSum[i - L]));
	    }
	    return res;
	}
}







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def maxSumTwoNoOverlap(self, A, L, M):
        for i in xrange(1, len(A)):
            A[i] += A[i - 1]
        res, Lmax, Mmax = A[L + M - 1], A[L - 1], A[M - 1]
        for i in xrange(L + M, len(A)):
            Lmax = max(Lmax, A[i - M] - A[i - L - M])
            Mmax = max(Mmax, A[i - L] - A[i - L - M])
            res = max(res, Lmax + A[i] - A[i - M], Mmax + A[i] - A[i - L])
        return res


=======================================================================================================
method 2: sliding window

Stats:

	- Time: O(n), space: O(1), where n = A.length.
	- 


Method:

	-	


 public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        return Math.max(maxSum(A, L, M), maxSum(A, M, L));
    }
    private int maxSum(int[] A, int L, int M) {
        int sumL = 0, sumM = 0;
        for (int i = 0; i < L + M; ++i) { // compute the initial values of L & M length subarrays.
            if (i < L) sumL += A[i];
            else sumM += A[i];
        }
        int ans = sumM + sumL; // sum of sumL and sumM.
        for (int i = L + M, maxL = sumL; i < A.length; ++i) {
            sumM += A[i] - A[i - M]; // update sumM.
            sumL += A[i - M] - A[i - L - M]; // update sumL.
            maxL = Math.max(maxL, sumL); // update max value of L-length subarray.
            ans = Math.max(ans, maxL + sumM); // update max value of sum of L & M-length subarrays.
        }
        return ans;
    }
    








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
def maxSumTwoNoOverlap(self, A: List[int], L: int, M: int) -> int:
        
        def maxSum(L:int, M:int) -> int:
            sumL = sumM = 0
            for i in range(0, L + M):
                if i < L:
                    sumL += A[i]
                else:
                    sumM += A[i]    
            maxL, ans = sumL, sumL + sumM
            for i in range(L + M, len(A)):
                sumL += A[i - M] - A[i - L - M]
                maxL = max(maxL, sumL)
                sumM += A[i] - A[i - M]
                ans = max(ans, maxL + sumM)
            return ans
        
        return max(maxSum(L, M), maxSum(M, L)) 

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

