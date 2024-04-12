Google-interview: num of steps back to origin

You start at index 0 in an array with length 'h'. At each step, you can move to the left, move to 
the right, or stay in the same place

(Note! Stay in the same place also takes one step). 

Q: How many possible ways are you still at index 0 after you have walked 'n' step?

Example： n = 3
1. right->left->stay
2. right->stay->left
3. stay->right->left
4. stay->stay->stay

=======================================================================================================
Method 1:


Stats:

    - O(n^2), as we do not need to consider the case when h > n / 2. If you go that far, you can not 
      come back to the origin, as you need to have the same number of left and right moves.
    

Method:
	- since the last one step to index 0. we have traveled n-1 steps and current at index either 
	  index 0 or index 1(we could not travel to -1);

        final step --> f(n,0) = f(n-1,0) + f(n-1,1)

    - let s as first s steps. j as current index;
         f(s,j) = f(s-1,j)+f(s-1,j-1)+f(s-1,j+1): 
    
    - base case: dp[1][0]=1 and dp[1][1]=1, but if we have dp[0][0]=1 we could achieve it by using 
                 the general function; 

 public static int solve(int n, int h) {

        int[][] dp = new int[n + 1][h]; 
        dp[0][0] = 1;  

        // steps
        for (int s = 1; s <= n; s++) {

        	// j = index, can go as far as (n-s) = total step - step already taken
            for (int j = 0; j <= n - s; j++) {

                // stay
                dp[s][j] = dp[s - 1][j];

                // previous step can be index to its left.
                if (j - 1 >= 0) 
                	dp[s][j] += dp[s - 1][j - 1];

                // previous step can be index to its right
                if (j + 1 < h) 
                	dp[s][j] += dp[s - 1][j + 1];
            }

        }
        return dp[n][0];
    }