673. Number of Longest Increasing Subsequence - Medium


Given an unsorted array of integers, find the number of longest increasing subsequence.

Example 1:
Input: [1,3,5,4,7]
Output: 2
Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].

Example 2:
Input: [2,2,2,2,2]
Output: 5
Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 
subsequences length is 1, so output 5.


Note: Length of the given array will be not exceed 2000 and the answer is guaranteed to be fit in 
32-bit signed int.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- 
	- 


Method:

	-  use two arrays len[n] and cnt[n] to record the maximum length of Increasing Subsequence and 
	   the coresponding number of these sequence which ends with nums[i], respectively. 

		len[i]: the length of the Longest Increasing Subsequence which ends with nums[i].
		cnt[i]: the number of the Longest Increasing Subsequence which ends with nums[i].

	-  Then, the result is the sum of each cnt[i] while its corresponding len[i] is the maximum length.

	-  For every i < j with A[i] < A[j], we might append A[j] to a longest subsequence ending at A[i]. 
	   It means that we have demonstrated count[i] subsequences of length length[i] + 1.

	-  Now, if those sequences are longer than length[j], then we know we have count[i] sequences of 
	   this length. If these sequences are equal in length to length[j], then we know that there are 
	   now count[i] additional sequences to be counted of that length (ie. count[j] += count[i]).


	public int findNumberOfLIS(int[] nums) {
        int n = nums.length, 
        	res = 0, 
        	max_len = 0;

        int[] len =  new int[n], 
        	  cnt = new int[n];

        for(int i = 0; i<n; i++){

            len[i] = cnt[i] = 1;

            for(int j = 0; j <i ; j++){
                if(nums[i] > nums[j]){

                	// i & j are 4, 3
                	// means have another way to reach the same max length increasing sequence
                    if(len[i] == len[j] + 1)
                    	cnt[i] += cnt[j];

                    // find an increasing substring
                    else if(len[i] < len[j] + 1){
                        len[i] = len[j] + 1;
                        cnt[i] = cnt[j];
                    }
                }
            }
            if(max_len == len[i])
            	res += cnt[i];

            if(max_len < len[i]){
                max_len = len[i];
                res = cnt[i];
            }
        }
        return res;
    }

input: [1,3,5,4,7]


len:[1, 2, 3, 3, 4]

max:[1, 1, 1, 1, 2]


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




