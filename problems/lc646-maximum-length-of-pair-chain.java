646. Maximum Length of Pair Chain - Medium

You are given n pairs of numbers. In every pair, the first number is always smaller than the second 
number.

Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can 
be formed in this fashion.

Given a set of pairs, find the length longest chain which can be formed. You neednt use up all the 
given pairs. You can select pairs in any order.

Example 1:
Input: [[1,2], [2,3], [3,4]]
Output: 2
Explanation: The longest chain is [1,2] -> [3,4]


******************************************************
key:
	- sort by first number, greedy
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Time Complexity: O(NlogN) where N is the length of S. The complexity comes from the sorting step,
	                   but the rest of the solution does linear work.

	- Space Complexity: O(N).



Method:

	-	
	-	


class Solution {
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
        int cur = Integer.MIN_VALUE, ans = 0;
        for (int[] pair: pairs) {
        	if (cur < pair[0]) {
	            cur = pair[1];
	            ans++;
            }
        }
        return ans;
    }
}

=======================================================================================================
method 2:

Stats:

	- Time Complexity: O(N^2) where N is the length of pairs. There are two for loops, and N^2 
	 				   dominates the sorting step.
	- Space Complexity: O(N) for sorting and to store dp.



Method:

	-	If a chain of length k ends at some pairs[i], and pairs[i][1] < pairs[j][0], we can extend 
	    this chain to a chain of length k+1.

	-	Sort the pairs by first coordinate, and let dp[i] be the length of the longest chain ending 
	    at pairs[i]. 
	    When i < j and pairs[i][1] < pairs[j][0], we can extend the chain, 
	    		dp[j] = max(dp[j], dp[i] + 1).


class Solution {
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        int N = pairs.length;
        int[] dp = new int[N];
        Arrays.fill(dp, 1);

        for (int j = 1; j < N; ++j) {
            for (int i = 0; i < j; ++i) {
                if (pairs[i][1] < pairs[j][0])
                    dp[j] = Math.max(dp[j], dp[i] + 1);
            }
        }

        int ans = 0;
        for (int x: dp) if (x > ans) ans = x;
        return ans;
    }
}
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



