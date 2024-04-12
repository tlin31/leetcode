1004. Max Consecutive Ones III Medium

Given an array A of 0s and 1s, we may change up to K values from 0 to 1.

Return the length of the longest (contiguous) subarray that contains only 1s. 

 

Example 1:

Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
Output: 6
Explanation: 
[1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.


Example 2:
Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
Output: 10
Explanation: 
[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.
 

Note:

1 <= A.length <= 20000
0 <= K <= A.length
A[i] is 0 or 1 
******************************************************
key:
	- sliding window
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- same as Find the longest subarray with at most K zeros.
	- For each A[j], try to find the longest subarray.
		If A[i] ~ A[j] has zeros <= K, we continue to increment j.
		If A[i] ~ A[j] has zeros > K, we increment i.

stats:

	- Runtime: 3 ms, faster than 99.67% of Java online submissions for Max Consecutive Ones III.
	- Memory Usage: 40.5 MB, less than 100.00%



    public int longestOnes(int[] A, int K) {
        int i = 0, j;
        for (j = 0; j < A.length; ++j) {
            if (A[j] == 0) K--;
            if (K < 0 && A[i++] == 0) K++;
        }
        return j - i;
    }


=======================================================================================================
method 2:

method:

	- count number of zeros, if the end == 0, then we move to the next element
	- when zeroCound > k, we do not have enough k to convert that 0 to 1, so we check the start
		of the window and move start to its right. If start points to 0, then we need to -1 from 
		zero count.

stats:

	- 
	- 

	public int longestOnes(int[] A, int K) {
        int zeroCount=0,start=0,res=0;

        for(int end=0; end<A.length; end++){
            if(A[end] == 0) zeroCount++;

            // can't be flipped, so move the start 
            while(zeroCount > K){
                if(A[start] == 0) zeroCount--;
                start++;
            }

            res = Math.max(res,end-start+1);
        }
        return res;
    }





