375. Guess Number Higher or Lower II - Medium

We are playing the Guess Game. The game is as follows:

I pick a number from 1 to n. You have to guess which number I picked.

Every time you guess wrong, I will tell you whether the number I picked is higher or lower.

However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game 
when you guess the number I picked.

Example:

n = 10, I pick 8.

First round:  You guess 5, I tell you that it is higher. You pay $5.
Second round: You guess 7, I tell you that it is higher. You pay $7.
Third round:  You guess 9, I tell you that it is lower. You pay $9.

Game over. 8 is the number I picked.

You end up paying $5 + $7 + $9 = $21.
Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.


******************************************************
key:
	- stack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 这题要求我们在猜测数字y未知的情况下（1~n任意一个数），要我们在最坏情况下我们支付最少的钱。也就是说要考虑所有y的情况。
	- 我们假定选择了一个错误的数x，（1<=x<=n && x!=y ）那么就知道接下来应该从[1,x-1 ] 或者[x+1,n]中进行查找。 
		假如我们已经解决了[1,x-1] 和 [x+1,n]计算问题，我们将其表示为solve(L,x-1) 和solve(x+1,n)，

		那么我们应该选择 max(solve(L,x-1),solve(x+1,n)) = 最坏情况下的损失。 

		总的损失 = f(x) = x + max(solve(L,x-1),solve(x+1,n))

	- 那么将x从1~n进行遍历，取使得 f(x) 达到最小，来确定最坏情况下最小的损失，也就是我们初始应该选择哪个数。
	- 上面的说法其实是一个自顶向下的过程（Top-down），可以用递归来解决。很容易得到如下的代码（这里用了记忆化搜索）：
	   	For each number x in range[i~j]:
			we do: result_when_pick_x = x + max{DP([i,x-1]), DP([x+1, j])}
				--> the max means whenever you choose a number, the feedback is always bad and 
					therefore leads you to a worse branch.
		
			   DP([i~j]) = min{xi, ... ,xj}
				--> // this min makes sure that you are minimizing your cost.

stats:
	- Time complexity : O(n^3) We traverse the complete dp matrix once (O(n^2))
	  					For every entry we take atmost nn numbers as pivot.

	- Space complexity : O(n^2) dp matrix of size n^2 is used.



bottom up:

public class Solution {
    public int getMoneyAmount(int n) {
        int[][] table = new int[n+1][n+1];
        for(int j=2; j<=n; j++){
            for(int i=j-1; i>0; i--){
                int globalMin = Integer.MAX_VALUE;

                // all possible partition, if pick k in the range [i,j]
                for(int k=i+1; k<j; k++){
                    int localMax = k + Math.max(table[i][k-1], table[k+1][j]);
                    globalMin = Math.min(globalMin, localMax);
                }

                // store the current min
                table[i][j] = i+1==j?i:globalMin;
            }
        }
        return table[1][n];
    }
}





=======================================================================================================
method 2:

method:

	- minimax
	- 

stats:

	- 
	- Runtime: 6 ms, faster than 65.87% of Java online submissions for Guess Number Higher or Lower II.
Memory Usage: 33.1 MB, less than 16.67% 

class solution{

	int[][] dp;

	// main function
	public int getMoneyAmount(int n) {
	    dp = new int[n+1][n+1];
	    return getMoneyAmount(1, n);
	}

	private int getMoneyAmount(int lower, int upper) {
	    if (lower >= upper) {
	        return 0;
	    }
	    if (dp[lower][upper] != 0) {
	        return dp[lower][upper];
	    }
	    
	    int maximum = Integer.MAX_VALUE;

	    for (int i = lower; i <= upper; i++) {
	        maximum = Math.min(maximum, Math.max(getMoneyAmount(lower, i-1), 
	        									 getMoneyAmount(i+1, upper)) + i);
	    }
	    dp[lower][upper] = maximum;
	    
	    return maximum;
	}
}

=======================================================================================================
method 3:

method:
	- binary search
	- corner case: if the range is less or equal to 3 (start >= end - 2), the cost will be the 
		upper boundary minus 1 (end - 1).
	- When selecting the first guess, the loop starts from one index left to the middle 
		((start + end) / 2 - 1), and the loop break when the cost of left part is higher than the 
		cost of right part.

stats:
	- Runtime: 1 ms, faster than 99.44% of Java online submissions for Guess Number Higher or Lower II.
	- Memory Usage: 33.4 MB, less than 16.67% 

	public class Solution {
	    int[][] dp;
	    public int getMoneyAmount(int n) {
	        dp = new int[n + 1][n + 1];
	        return helper(1, n);
	    }
	    
	    private int helper(int start, int end) {
	        if (dp[start][end] != 0) {
	            return dp[start][end];
	        }
	        if (start >= end) {
	            return 0;
	        }
	        if (start >= end - 2) {
	            return dp[start][end] = end - 1;
	        }
	        int mid = (start + end) / 2 - 1, 
	        	min = Integer.MAX_VALUE;

	        while (mid < end) {
	            int left = helper(start, mid - 1);
	            int right = helper(mid + 1, end);
	            min = Math.min(min, mid + Math.max(left, right));
	            if (right <= left) break;
	            mid++;
	        }
	        return dp[start][end] = min;
	    }
	}
 