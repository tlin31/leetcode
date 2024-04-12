70. Climbing Stairs - Easy

You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:
Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps

Example 2:
Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step

******************************************************
key:
	- stack / DP
	- backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- recursion
	- 

stats:

	- 时间复杂度：是一个树状图，O(2^n)
	- 

	public int climbStairs(int n) {
	    return climbStairsN(n);
	}

	private int climbStairsN(int n) {
	    if (n == 1) {
	        return 1;
	    }
	    if (n == 2) {
	        return 2;
	    }
	    return climbStairsN(n - 1) + climbStairsN(n - 2);
	}



=======================================================================================================
method 2:

method:
	- DP
stats:
	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Climbing Stairs.
	- Memory Usage: 33.1 MB, less than 5.26% 

	class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n];
        
        if (n == 1) return 1;
        if (n == 2) return 2;
        dp[0] = 1;
        dp[1] = 2;
        
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
            
        }
        return dp[n-1];
    }
}


=======================================================================================================
method 3:

method:

	- 当然递归可以解决，我们可以直接迭代，省去递归压栈的过程。初始值 f ( 1 ) 和 f ( 2 )，然后可以求出 f ( 3 )，
		然后求出 f ( 4 ) ... 直到 f ( n )，一个循环就够了。其实就是动态规划的思想了。
	- 

stats:

	- 时间复杂度：O（n）。
	- 空间复杂度：O（1）。


public int climbStairs(int n) {
    int n1 = 1;
    int n2 = 2;
    if (n == 1) {
        return n1;
    }
    if (n == 2) {
        return n2;
    }
    
    //n1、n2 都后移一个位置
    for (int i = 3; i <= n; i++) {
        int temp = n2;
        n2 = n1 + n2;
        n1 = temp;
    }
    return n2;
}


==================
Solution5叫做 Binets Method，它利用数学归纳法证明了一下，这里就直接用了，至于怎么想出来的，我也不清楚了。

定义一个矩阵 Q = \begin{matrix} 1 & 1 \\ 1 & 0 \end{matrix} Q=
​1
​1
​​ 
​1
​0
​​  ，然后求 f ( n ) 话，我们先让 Q 矩阵求幂，然后取第一行第一列的元素就可以了，也就是 f(n)=Q^n[0][0]f(n)=Q
​n
​​ [0][0]。

至于怎么更快的求幂，可以看 50 题的解法三。




