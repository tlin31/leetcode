905. Sort Array By Parity - Easy


Given an array A of non-negative integers, return an array consisting of all the even elements of A, 
followed by all the odd elements of A.

You may return any answer array that satisfies this condition.

 

Example 1:

Input: [3,1,2,4]
Output: [2,4,3,1]
The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
 

Note:

1 <= A.length <= 5000
0 <= A[i] <= 5000



******************************************************
key:
	- 2 pointers
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: sort


Stats:

	- 
	- 


Method:

	-	
	-	


class Solution {
    public int[] sortArrayByParity(int[] A) {
        Integer[] B = new Integer[A.length];
        for (int t = 0; t < A.length; ++t)
            B[t] = A[t];

        Arrays.sort(B, (a, b) -> Integer.compare(a%2, b%2));

        for (int t = 0; t < A.length; ++t)
            A[t] = B[t];
        return A;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
class Solution(object):
    def sortArrayByParity(self, A):
        A.sort(key = lambda x: x % 2)
        return A

=======================================================================================================
method 2: in-place ~ 2 pointers

Stats:

	- 
	- 


Method:

	-  We'll maintain two pointers i and j. 
	   The loop invariant is everything below i has parity 0 (ie. A[k] % 2 == 0 when k < i), 
	   and everything above j has parity 1.

	Then, there are 4 cases for (A[i] % 2, A[j] % 2):

		If it is (0, 1), then everything is correct: i++ and j--.

		If it is (1, 0), we swap them so they are correct, then continue.

		If it is (0, 0), only the i place is correct, so we i++ and continue.

		If it is (1, 1), only the j place is correct, so we j-- and continue.

	-  Throughout all 4 cases, the loop invariant is maintained, and j-i is getting smaller. 
	   So eventually we will be done with the array sorted as desired.


class Solution {
    public int[] sortArrayByParity(int[] A) {
        int i = 0, j = A.length - 1;
        while (i < j) {
            if (A[i] % 2 > A[j] % 2) {
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }

            if (A[i] % 2 == 0) i++;
            if (A[j] % 2 == 1) j--;
        }

        return A;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution(object):
    def sortArrayByParity(self, A):
        i, j = 0, len(A) - 1

        while i < j:

        	# swap
            if A[i] % 2 > A[j] % 2:
                A[i], A[j] = A[j], A[i]

            if A[i] % 2 == 0: i += 1
            if A[j] % 2 == 1: j -= 1

        return A


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

