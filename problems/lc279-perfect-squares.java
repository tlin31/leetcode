279. Perfect Squares - Medium

Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...)
 which sum to n.

Example 1:

Input: n = 12
Output: 3 
Explanation: 12 = 4 + 4 + 4.
Example 2:

Input: n = 13
Output: 2
Explanation: 13 = 4 + 9.

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

	- DP
	- 

stats:

	- Time complexity: O(n⋅ sqrt(n)). In main step, we have a nested loop, where the outer loop is of n 
	   iterations and in the inner loop it takes at maximum sqrt{n} iterations.

	- Space Complexity: O(n). We keep all the intermediate sub-solutions in the array dp[].

public class Solution {
	public int numSquares(int n) {
	    int[] dp = new int[n + 1];

	    for (int i = 1; i <= n; i++) {
	        dp[i] = Integer.MAX_VALUE;
	    }
	    
	    for (int i = 1; i <= n; i++) {
	        int sqrt = (int)Math.sqrt(i);
	        
	        // If the number is already a perfect square, then dp[number] can be 1 directly. 
	        if (sqrt * sqrt == i) {
	            dp[i] = 1;
	            continue;                
	        }
	        
	        // To get the value of dp[n], we should choose the min value from:
	        //     dp[n - 1] + 1,
	        //     dp[n - 4] + 1,
	        //     dp[n - 9] + 1,
	        //     dp[n - 16] + 1
	        //     and so on...
	        for (int j = 1; j <= sqrt; j++) {
	            int dif = i - j * j;
	            dp[i] = Math.min(dp[i], (dp[dif] + 1));
	        }
	    }
	    
	    return dp[n];
	}
}


=======================================================================================================
method 2:

method:

	- BFS
	- 

stats:

	- 
	- 

public int numSquares(int n) {
    Queue<Integer> q = new LinkedList();
    int step = 0;
    Set<Integer> visited = new HashSet();
    q.add(0);
    visited.add(0);
    
    while(!q.isEmpty()){
        int size = q.size();
        ++step;
        for(int i = 0; i < size; ++i){
            int remian = n - q.remove();
            for(int j = 1; j <= Math.sqrt(remian); ++j){
                int v = n - remian + j * j;
                if(v == n) return step;
                if(!visited.add(v)) continue;
                q.add(v);
            }
        }
    }
 
    return n;
}


=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



