354. Russian Doll Envelopes - Hard

You have a number of envelopes with widths and heights given as a pair of integers (w, h). One 
envelope can fit into another if and only if both the width and height of one envelope is greater 
than the width and height of the other envelope.

What is the maximum number of envelopes can you Russian doll? (put one inside other)

Note:
Rotation is not allowed.

Example:

Input: [[5,4],[6,4],[6,7],[2,3]]
Output: 3 
Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).


******************************************************
key:
	- 2d longest increasing array: 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Sort + Longest Increasing Subsequence

Method:

	-	Consider an input [[1, 3], [1, 4], [1, 5], [2, 3]]. If we simply sort and extract the 
	    second dimension we get [3, 4, 5, 3], which implies that we can fit three envelopes (3, 4, 5).
	    The problem is that we can only fit one envelope, since envelopes that are equal in the first
	    dimension can't be put into each other.

	- In order fix this, we don't just sort increasing in the first dimension - also sorting height in
	  descending order when there is a tie prevents such a sequence to be included in the solution.

	-	Now when we sort and extract the second element from the input we get [5, 4, 3, 3], which
	    correctly reflects an LIS of one.


Stats:

	- O(n * logn)
	- O(n)

class Solution {

	// main function
    public int maxEnvelopes(int[][] envelopes) {

        // sort on increasing in first dimension and decreasing in second
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[0] == arr2[0]) {
                    return arr2[1] - arr1[1];
                } else {
                    return arr1[0] - arr2[0];
                }
           }
        });

        // extract the second dimension and run LIS
        int[] secondDim = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; ++i) 
        	secondDim[i] = envelopes[i][1];

        return lengthOfLIS(secondDim);
    }

        public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }
}


=======================================================================================================
method 2: DP

Method:

	- 

Stats:

	-


=======================================================================================================
method 3: DP with binary search

Method:

	-	
	-	


Stats:

	- O(n logn)
	- 

public class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int i = Arrays.binarySearch(dp, 0, len, num);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            if (i == len) {
                len++;
            }
        }
        return len;
    }
}


input: [0, 8, 4, 12, 2]

dp: [0]

dp: [0, 8]

dp: [0, 4]

dp: [0, 4, 12]

dp: [0 , 2, 12] which is not the longest increasing subsequence, but length of dpdp array results in length of Longest Increasing Subsequence.

