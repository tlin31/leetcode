978. Longest Turbulent Subarray - Medium



A subarray A[i], A[i+1], ..., A[j] of A is said to be turbulent if and only if:

	- For i <= k < j, A[k] > A[k+1] when k is odd, and A[k] < A[k+1] when k is even;
	- OR, for i <= k < j, A[k] > A[k+1] when k is even, and A[k] < A[k+1] when k is odd.

That is, the subarray is turbulent if the comparison sign flips between each adjacent pair of elements 
in the subarray.

Return the length of a maximum size turbulent subarray of A.

 

Example 1:

Input: [9,4,2,10,7,8,8,1,9]
Output: 5
Explanation: (A[1] > A[2] < A[3] > A[4] < A[5])


Example 2:

Input: [4,8,12,16]
Output: 2


Example 3:

Input: [100]
Output: 1
 

Note:

1 <= A.length <= 40000
0 <= A[i] <= 10^9
Accepted


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- O(N) time
	- O(1) space


Method:

	For each A[i]
	inc: The length of current valid sequence which ends with two increasing numbers
	dec: The length of current valid sequence which ends with two decreasing numbers




	public int maxTurbulenceSize(int[] A) {
        if(A == null || A.length == 0){
            return 0;
        }

        int[] incre = new int[A.length];
        int[] decre = new int[A.length];
        int maxLen = 1;
        Arrays.fill(incre, 1); 
        Arrays.fill(decre, 1);

        for(int i = 1; i < A.length; i++){
            if(A[i] > A[i - 1]){
                incre[i] = decre[i - 1] + 1;
            }else if(A[i] < A[i - 1]){
                decre[i] = incre[i - 1] + 1;
            }
            maxLen = Math.max(maxLen, Math.max(incre[i], decre[i]));
        }
        return maxLen;
    }


class Solution {
    public int maxTurbulenceSize(int[] A) {
        int inc = 1, 
        	dec = 1, 
        	result = 1;

        for (int i = 1; i < A.length; i++) {
            if (A[i] < A[i - 1]) {
                dec = inc + 1;
                inc = 1;
            } 

            else if (A[i] > A[i - 1]) {
                inc = dec + 1;
                dec = 1;
            } 

            else {
                inc = 1;
                dec = 1;
            }

            result = Math.max(result, Math.max(dec, inc));
        }
        return result;
    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
def maxTurbulenceSize(self, A):
    best = clen = 0

    for i in range(len(A)):
        if i >= 2 and (A[i-2] > A[i-1] < A[i] or A[i-2] < A[i-1] > A[i]):
            clen += 1
        elif i >= 1 and A[i-1] != A[i]:
            clen = 2
        else:
            clen = 1
        best = max(best, clen)
    return best

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

