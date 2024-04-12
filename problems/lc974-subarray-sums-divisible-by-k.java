974. Subarray Sums Divisible by K - Medium


Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum 
divisible by K.

 

Example 1:

Input: A = [4,5,0,-2,-3,1], K = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by K = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 

Note:

1 <= A.length <= 30000
-10000 <= A[i] <= 10000
2 <= K <= 10000


******************************************************
key:
	- prefix sum, mod array
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

	- if sum[0, i] % K == sum[0, j] % K --> sum[i + 1, j] is divisible by by K.
	 So for current index j, we need to find out how many index i (i < j) exit that has the same 
	 mod of K.

    public int subarraysDivByK(int[] A, int K) {
        int[] mods = new int[K];//O(K) space complexity
        mods[0] = 1;
        int sum = 0, count = 0;
        for(int i = 0;i< A.length;i++){
            sum += A[i];
            sum = sum % K;          // if sum A[i:j] = sum A[0:j] - sum[0:i-1] has the same remainder
            if(sum < 0) sum += K;   // Because -1 % 5 = -1, but we need the positive mod 4
            count += mods[sum];
            mods[sum] = mods[sum]+1;
        }
        return count;
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
    def subarraysDivByK(self, A, K):
        res = 0
        prefix = 0
        count = [1] + [0] * K
        for a in A:
            prefix = (prefix + a) % K
            res += count[prefix]
            count[prefix] += 1
        return res

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

