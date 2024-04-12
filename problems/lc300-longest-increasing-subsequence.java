300. Longest Increasing Subsequence - Medium

Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.

******************************************************
key:
	- DP.
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- DP
	- Let arr[0..n-1] be the input array and L(i) be the length of the LIS ending at index i such 
	  that arr[i] is the last element of the LIS.

	Then, L(i) can be recursively written as:
			L(i) = 1 + max( L(j) ) where 0 < j < i and arr[j] < arr[i]; or
			L(i) = 1, if no such j exists.

	To find the LIS for a given array, we need to return max(L(i)) where 0 < i < n.

stats:

	- O(n^2)
	- 

class Solution {
    public int lengthOfLIS(int[] nums) {
        int length = nums.length;
        int max = 0;
        
        //memo[i] = longest subseq ending at i
        int[] memo = new int[length];
        for (int i = 0; i < length; i++) {
            memo[i] = 1;
        }
        
        // i = ending index 
        for (int i = 1; i < length; i++) {
            
            // j = start index
            for(int j = 0; j<i; j++) {

            	// increasing seq AND generate longer sequence
                if (nums[i] > nums[j] && memo[i] < memo[j] + 1) {
                    memo[i] = memo[j]+1; 
                }
            }
            
        }
        
        for ( int i = 0; i < length; i++ ) 
              if ( max < memo[i] ) 
                 max = memo[i]; 
  
        
        return max;
    }
}


=======================================================================================================
method 2:

method:
    - kind of greedy --> always want to store the smallest ones
	- tails is an array storing the smallest tail of all increasing subsequences with length i+1 in 
	  tails[i].
	- Each time we only do one of the two:

		(1) if x is larger than all tails, append it, increase the size by 1
		(2) if tails[i-1] < x <= tails[i], update tails[i], because we always want a smaller num

stats:

	- O(n logn )
	- 

https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/


public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];

    // size of the tail table
    int size = 0;
    for (int x : nums) {
        int i = 0, 
        	j = size;

        // binary search on where x should be in the tail table
        while (i != j) {
            int m = (i + j) / 2;
            if (tails[m] < x)
                i = m + 1;
            else
                j = m;
        }
        tails[i] = x;

        if (i == size) 
        	++size;
    }
    return size;
}

input: [10,9,2,5,3,7,101,18]

run 0
updates at 0 with value: 10
tail table = [10, 0, 0, 0, 0, 0, 0, 0]

run 1
updates at 0 with value: 9
tail table = [9, 0, 0, 0, 0, 0, 0, 0]
run 1
updates at 0 with value: 2
tail table = [2, 0, 0, 0, 0, 0, 0, 0]
run 1
updates at 1 with value: 5
tail table = [2, 5, 0, 0, 0, 0, 0, 0]


run 2
updates at 1 with value: 3
tail table = [2, 3, 0, 0, 0, 0, 0, 0]
run 2
updates at 2 with value: 7
tail table = [2, 3, 7, 0, 0, 0, 0, 0]

run 3
updates at 3 with value: 101
tail table = [2, 3, 7, 101, 0, 0, 0, 0]

run 4
updates at 3 with value: 18
tail table = [2, 3, 7, 18, 0, 0, 0, 0]



=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 

public class Solution {
    public int lengthOfLIS(int[] nums) {            
        int[] dp = new int[nums.length];
        int len = 0;

        for(int x : nums) {
            int i = Arrays.binarySearch(dp, 0, len, x);
            if(i < 0) 
            	i = -(i + 1);
            dp[i] = x;
            if(i == len) 
            	len++;
        }

        return len;
    }
}

