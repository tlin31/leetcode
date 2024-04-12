1027. Longest Arithmetic Sequence - Medium

Given an array A of integers, return the length of the longest arithmetic subsequence in A.

Recall that a subsequence of A is a list A[i_1], A[i_2], ..., A[i_k] with 0 <= i_1 < i_2 < ... < i_k 
<= A.length - 1, and that a sequence B is arithmetic if B[i+1] - B[i] are all the same value (for 
0 <= i < B.length - 1).

 

Example 1:

Input: [3,6,9,12]
Output: 4
Explanation: 
The whole array is an arithmetic sequence with steps of length = 3.

Example 2:
Input: [9,4,7,2,10]
Output: 3
Explanation: 
The longest arithmetic subsequence is [4,7,10].

Example 3:
Input: [20,1,15,3,10,5,8]
Output: 4
Explanation: 
The longest arithmetic subsequence is [20,15,10,5].
 

Note:

2 <= A.length <= 2000
0 <= A[i] <= 10000

******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- dp[diff][index] equals to the length of arithmetic sequence at index with difference diff.
	- for each index, create a hashmap
	- 

stats:

	- Runtime: 360 ms, faster than 44.02% of Java online submissions for Longest Arithmetic Sequence.
	- Memory Usage: 178.7 MB, less than 6.67%


 public int longestArithSeqLength(int[] A) {

 		// minimum case, res = 2 because [3,6,7]中(3,6) or (6,7)都为等差数列
        int res = 2, 
        	n = A.length;
        HashMap<Integer, Integer>[] dp = new HashMap[n];
        for (int j = 0; j < A.length; j++) {
            dp[j] = new HashMap<>();
            for (int i = 0; i < j; i++) {
                int d = A[j] - A[i];
                dp[j].put(d, dp[i].getOrDefault(d, 1) + 1);
                res = Math.max(res, dp[j].get(d));
            }
        }
        return res;
    }
    
=======================================================================================================
method 2:

method:

	- The main idea is to maintain a 2D array that store diffs that are seen before.
	- The trick without using a HashMap is from the note: 0 <= a[i] <= 10000. So we can initiate 
	  dp[a.length][diff] where 0 <= diff <= 20001 because Java array does not support negative index.
	- Definition: dp[i][j]= longest arithmetic sequence from a[0] to a[i] that has j - 10000 
	  differentiate.
	  base case: dp[0][diff] = 0
	  induction rule: dp[i][diff] = Math.max(dp[i][diff], dp[j][diff] + 1) where 0 <= j < i.

stats:

	- Runtime: 85 ms, faster than 87.28% of Java online submissions for Longest Arithmetic Sequence.
	- Memory Usage: 246.3 MB, less than 6.67% of Java online submissions for Longest Arithmetic Sequence.
 

 public int longestArithSeqLength(int[] a) {
        if (a == null) return 0;
        int[][] dp = new int[a.length][20001];
        
        int res = 1;
        for (int i = 1; i < a.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int diff = a[i] - a[j] + 10000;
                dp[i][diff] = Math.max(dp[i][diff], dp[j][diff] + 1);   // DON'T forget to compare
                
                res = Math.max(res, dp[i][diff]);
            }
        }

        return res + 1;
    }

