486. Predict the Winner - Medium

Given an array of scores that are non-negative integers. Player 1 picks one of the numbers from 
either end of the array followed by the player 2 and then player 1 and so on. Each time a player 
picks a number, that number will not be available for the next player. This continues until all 
the scores have been chosen. The player with the maximum score wins.

Given an array of scores, predict whether player 1 is the winner. You can assume each player plays 
to maximize his score.

Example 1:
Input: [1, 5, 2]
Output: False
Explanation: Initially, player 1 can choose between 1 and 2. 
If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then 
player 1 will be left with 1 (or 2). 
So, final score of player 1 is 1 + 2 = 3, and player 2 is 5. 
Hence, player 1 will never be the winner and you need to return False.

Example 2:
Input: [1, 5, 233, 7]
Output: True
Explanation: Player 1 first chooses 1. Then player 2 have to choose between 5 and 7. No matter which 
number player 2 choose, player 1 can choose 233.
Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing 
player1 can win.

Note:
1 <= length of the array <= 20.
Any scores in the given array are non-negative integers and will not exceed 10,000,000.
If the scores of both players are equal, then player 1 is still the winner.

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

	- The dp[i][j] = difference, 
		saves how much more scores that the first-in-action player will get from i to j 
		than the second player. First-in-action means whomever moves first.

	- 1. The first step is to break the question into the sub-problems. From the question, the winning 
		goal is that "The player with the maximum score wins". So one way to approach it is that we 
		may want to find a way to maximize player 1s sum and check if it is greater than player 2s 
		sum (or more than half of the sum of all numbers). 

		Another way, after noting that the sum of all numbers is fixed, I realized that it doesnt 
		matter how much player 1s total sum is as long as the sum is no less than player 2s sum. 
		No matter how, I think we can easily recognize that it is a recursive problem where we may
		use the status on one step to calculate the answer for the next step.

	- 2.redundant calculation. For example, "player 1 picks left, then player 2 picks left, then 
		player 1 picks right, then player 2 picks right" will end up the same as "player 1 picks 
		right, then player 2 picks right, then player 1 picks left, then player 2 picks left". 
		So, we may want to use dynamic programming to save intermediate states.

	- 3. I think it will be easy to think about using a two dimensional array dp[i][j] to save all 
		the intermediate states. From step 1, we may see at least two ways of doing it. It just 
		turned out that if we choose to save how much more scores that the first-in-action player 
		will earn from position i to j in the array (as I did), the code will be better in a couple 
		of ways.

	- 4. Suppose they are picking up numbers from position i to j in the array and it is player A
	     turn to pick the number now. 

		If player A picks position i, player A will earn nums[i] score instantly. Then player B will 
		choose from i + 1 to j. Please note that dp[i + 1][j] already saves how much more score that 
		the first-in-action player will get from i + 1 to j than the second player. 

		So it means that player B will eventually earn dp[i + 1][j] more score from i + 1 to j than 
		player A. So if player A picks position i, eventually player A will get 
		nums[i] - dp[i + 1][j] more score than player B after they pick up all numbers. 

		Similarly, if player A picks position j, player A will earn nums[j] - dp[i][j - 1] more score 
		than player B after they pick up all numbers. Since A is smart, A will always choose the max 
		in those two options, so:

				dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);

	- 5. Now we have the recursive formula, the next step is to decide where it all starts. This step
		is easy because we can easily recognize that we can start from dp[i][i], where 
		dp[i][i] = nums[i]. Then the process becomes a very commonly seen process to update the 
		dp table. 

		Using a 5 x 5 dp table as an example, where i is the row number and j is the column number. 
		Each dp[i][j] corresponds to a block at row i, column j on the table. We may start from 
		filling dp[i][i], which are all the diagonal blocks. I marked them as 1. Then we can see 
		that each dp[i][j] depends only on dp[i + 1][j] and dp[i][j - 1]. 

		On the table, it means each block (i, j) only depends on the block to its left (i, j - 1) 
		and to its down (i + 1, j). So after filling all the blocks marked as 1, we can start to 
		calculate those blocks marked as 2. After that, all blocks marked as 3 and so on.

		use len to denote how far the block is away from the diagonal. So len ranges from 1 to n - 1. 
		Remember this is the outer loop. The inner loop is all valid i positions. After filling all 
		the upper side of the table, we will get our answer at dp[0][n - 1] (marked as 5). This is 
		the end of my code.


stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Predict the Winner.
	- Memory Usage: 33.9 MB, less than 100.00% 


	public boolean PredictTheWinner(int[] nums) {
	    int n = nums.length;
	    int[][] dp = new int[n][n];
	    for (int i = 0; i < n; i++) 
	    	dp[i][i] = nums[i]; 

	    for (int len = 1; len < n; len++) {
	        for (int i = 0; i < n - len; i++) {
	            int j = i + len;
	            dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
	        }
	    }

	    return dp[0][n - 1] >= 0;
	}


--------------------------------------------------
method 1.5: optimize space ot O(n)
method：
	- That can be done by changing our way of filling the table. We may use only one dimensional 
		dp[i] and we start to fill the table at the bottom right corner where dp[4] = nums[4]. 
		On the next step, we start to fill the second to the last line, where it starts from 
		dp[3] = nums[3]. Then dp[4] = Math.max(nums[4] - dp[3], nums[3] - dp[4]). 

		Then we fill the third to the last line where dp[2] = nums[2] and so on... 

		Eventually after we fill the first line and after the filling, dp[4] will be the answer.

	- On a related note, whenever we do sum, subtract, multiply or divide of integers, we might 
		need to think about overflow. It does not seem to be a point to check for this question. 
		However, we may want to consider using long instead of int for some cases. Further, 
		in my way of code dp[i][j] roughly varies around zero or at least it does not always increases 
		with approaching the upper right corner. So it will be less likely to overflow.


public boolean PredictTheWinner(int[] nums) {
	int n = nums.length;

    if (nums == null) 
    	return true; 
    if ((n & 1) == 0)
    	return true;

    int[] dp = new int[n];
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            if (i == j) {
                dp[i] = nums[i];
            } else {
                dp[j] = Math.max(nums[i] - dp[j], nums[j] - dp[j - 1]);
            }
        }
    }
    return dp[n - 1] >= 0;
}

=======================================================================================================
method 2:

method:

	- minimax
	- maximize the amount of money we can get assuming we move first.
	- dp[i][j] means the max value we can get if it is our turn and only coins between i and j 
		remain.(Inclusively). So dp[i][i] means there is only one coin left, we just pick it, 
		dp[i][i+1] means there are only two left, we then pick the max one.

	- general case. dp[i][j] = max( something + v[i], something + v[j]), since we either will pick 
		the i or j coin. The problem now turns to what "something" here will be.

	- dp[i][j] = max( dp[i + 1][j] + v[i], dp[i][j - 1] + v[j]), but here dp[i + 1][j] and 
		dp[i][j - 1] are not the values directly available for us, it depends on the move that 
		our opponent make.

	- so the correct dp formula would be 
		dp[i][j] = max( min (dp[i + 1][j - 1], dp[i + 2][ j]) + v[i], min (dp[i][j - 2], dp[i + 1][ j - 1]) + v[j]}) .

	- If we pick i, then our opponent need to deal with subproblem dp[i + 1][j], it either pick from 
		i + 2 or j - 1. Similarly, If we pick j, then our opponent need to deal with subproblem 
		dp[i][j - 1] , it either pick from i + 1 or j - 2. 

		We take the worse case into consideration so use min() here.

stats:

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Predict the Winner.
	- Memory Usage: 34.3 MB, less than 100.00%


public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;

        if (num %2 == 0) return true;

        int[][] memo = new int[n][n];
        
        for (int[] memoRow : memo)
            Arrays.fill(memoRow, -1);
        
        // Final score of the first player.
        int scoreFirst = predictTheWinnerInSituation(nums, 0, n - 1, memo); 

        int scoreTotal = 0;
        for (int num : nums) {
            scoreTotal += num;
        }

        // Compare final scores of two players.
        return scoreFirst >= scoreTotal - scoreFirst;
    }
    
    private int predictTheWinnerInSituation(int[] nums, int i, int j, int[][] memo) {
        // Base case.
        if (i > j) {
            return 0;
        }

        if (i == j) {
            return nums[i];
        }

        // already put in number, -1 is only when initialization
        if (memo[i][j] != -1)
            return memo[i][j];
        
        // Recursive case.
        int curScore = Math.max(
        	//take the first one
            nums[i] + Math.min(predictTheWinnerInSituation(nums, i + 2, j, memo), 
            	predictTheWinnerInSituation(nums, i + 1, j - 1, memo)),

            // take the last one
            nums[j] + Math.min(predictTheWinnerInSituation(nums, i, j - 2, memo), 
            	predictTheWinnerInSituation(nums, i + 1, j - 1, memo)));  
            	 
        memo[i][j] = curScore;
        
        return curScore;
    } 
    


