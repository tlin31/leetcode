1301. Number of Paths with Max Score - Hard


You are given a square board of characters. You can move on the board starting at the bottom 
right square marked with the character 'S'.

You need to reach the top left square marked with the character 'E'. The rest of the squares are 
labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go 
up, left or up-left (diagonally) only if there is no obstacle there.

Return a list of two integers: the first integer is the maximum sum of numeric characters you can 
collect, and the second is the number of such paths that you can take to get that maximum sum, taken 
modulo 10^9 + 7.

In case there is no path, return [0, 0].

 

Example 1:

Input: board = ["E23","2X2","12S"]
Output: [7,1]
Example 2:

Input: board = ["E12","1X1","21S"]
Output: [4,2]
Example 3:

Input: board = ["E11","XXX","11S"]
Output: [0,0]
 

Constraints:

2 <= board.length == board[i].length <= 100


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	

class Pair{
    int val;
    long paths;
    Pair(int val, long paths){
        this.val = val;
        this.paths = paths;
    }
}

class Solution {
    static int mod = (int)Math.pow(10, 9) + 7;

    // main function
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        Pair[][] dp = new Pair[n][n];
        Pair ans = dfs(board, n - 1, n - 1, dp);

        // didn't find
        if(ans.val == Integer.MIN_VALUE) 
        	return new int[2];


        int[] final_ans = new int[2];
        final_ans[0] = ans.val;
        final_ans[1] = (int)ans.paths;
        return final_ans;
    }


    public static Pair dfs(List<String> arr, int i, int j, Pair[][] dp){
        // reach top left
        if(i == 0 && j == 0) 
        	return new Pair(0, 1);

        if(i < 0 || j < 0 || arr.get(i).charAt(j) == 'X') 
        	return new Pair(Integer.MIN_VALUE, 0);

        if(dp[i][j] != null) 
        	return dp[i][j];

        // 3 possibilities
        Pair x1 = dfs(arr,i - 1, j, dp);
        Pair x2 = dfs(arr, i, j - 1, dp);
        Pair x3 = dfs(arr, i - 1, j - 1, dp);

        Pair max = new Pair(Integer.MIN_VALUE, 0);

        // check and update
        if(x1.val != Integer.MIN_VALUE) 
        	max = new Pair(x1.val, x1.paths); 

        if(x2.val != Integer.MIN_VALUE){
            if(x2.val > max.val) 
            	max = new Pair(x2.val, x2.paths);
            else if(x2.val == max.val) 
            	max = new Pair(max.val, (max.paths + x2.paths) % mod);
        }

        if(x3.val != Integer.MIN_VALUE){
            if(x3.val > max.val) 
            	max = new Pair(x3.val, x3.paths);
            else if(x3.val == max.val) 
            	max = new Pair(max.val, (max.paths + x3.paths) % mod);
        } 

        // add current
        int curr = 0;
        if(arr.get(i).charAt(j) != 'S') 
        	curr = (arr.get(i).charAt(j)) - '0';

        if(max.val != Integer.MIN_VALUE) 
        	max.val += curr;

        dp[i][j] = max;
        return max;
    }
    

}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def pathsWithMaxScore(self, board: List[str]) -> List[int]:
        # `mem[(i, j)]` records `(the maximum scores, the number of paths)` starting from (i, j)
        # the answer should be mem[(len(board)-1, len(board[0])-1)]
        mem = {}

        def dfs(i, j):
            if (i, j) in mem:
                return mem[(i, j)]

            # invalid state
            if board[i][j] == 'X' or not (0 <= i < len(board) and 0 <= j < len(board[0])): 
                return [0, 0]

            # reach end state.
            if board[i][j] == 'E': 
                return [0, 1]

            scores_up, paths_up = dfs(i - 1, j)
            scores_left, paths_left = dfs(i, j - 1)
            score_upleft, paths_upleft = dfs(i - 1, j - 1)

            max_scores = max(scores_up, scores_left, score_upleft)
            paths = 0

            for s, p in ((scores_up, paths_up), (scores_left, paths_left), (score_upleft, paths_upleft)):
                paths += p if s == max_scores else 0

            # if paths == 0, we have no path to move.
            if board[i][j] != 'S':
                max_scores = max_scores + int(board[i][j]) if paths else 0 
            
            mem[(i, j)] = [max_scores, paths]
            return mem[(i, j)]

        scores, paths = dfs(len(board) - 1, len(board[0]) - 1)
        return [scores, paths % (10 ** 9 + 7)]

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	dp[i][j] := max score when reach (j, i)
		count[i][j] := path to reach (j, i) with max score

		m = max(dp[i + 1][j], dp[i][j+1], dp[i+1][j+1])
		dp[i][j] = board[i][j] + m
		count[i][j] += count[i+1][j] if dp[i+1][j] == m
		count[i][j] += count[i][j+1] if dp[i][j+1] == m
		count[i][j] += count[i+1][j+1] if dp[i+1][j+1] == m
			

class Solution {
    private static final int[][] DIRS = new int[][]{{0, -1}, {-1, 0}, {-1, -1}};

    public int[] pathsWithMaxScore(List<String> board) {
        int m = board.size(), n = board.get(0).length();
        int[][] dpSum = new int[m][n];
        int[][] dpCnt = new int[m][n];

        // start at the bottom right square
        dpCnt[m - 1][n - 1] = 1; 

        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {

            	// can't reach to this square
                if (dpCnt[r][c] == 0) 
                	continue; 

                for (int[] dir : DIRS) {
                    int nr = r + dir[0], nc = c + dir[1];
                    if (nr >= 0 && nc >= 0 && board.get(nr).charAt(nc) != 'X') {
                        int nsum = dpSum[r][c];
                        if (board.get(nr).charAt(nc) != 'E')
                            nsum += board.get(nr).charAt(nc) - '0';

                        // update
                        if (nsum > dpSum[nr][nc]) {
                            dpCnt[nr][nc] = dpCnt[r][c];
                            dpSum[nr][nc] = nsum;

                        } 
                        else if (nsum == dpSum[nr][nc]) {
                            dpCnt[nr][nc] = (dpCnt[nr][nc] + dpCnt[r][c]) % 1000000007;
                        }
                    }
                }
            }
        }
        return new int[]{dpSum[0][0], dpCnt[0][0]};
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

Explanation
We create 3D dp[x][y], where
dp[x][y][0] is the maximum value to this cell,
dp[x][y][1] is the number of paths.


Complexity
Time O(N^2)
Space O(N^2)

    def pathsWithMaxScore(self, A):
        n, mod = len(A), 10**9 + 7
        dp = [[[-10**5, 0] for j in xrange(n + 1)] for i in xrange(n + 1)]
        dp[n - 1][n - 1] = [0, 1]
        
        # loop bottom-to-up, right-to-left
        for x in range(n)[::-1]:
            for y in range(n)[::-1]:
                if A[x][y] in 'XS': continue
                for dx, dy in [[0, 1], [1, 0], [1, 1]]:
                    inheritsum = dp[x + dx][y + dy][0]
                    if dp[x][y][0] < inheritsum:
                        dp[x][y] = [inheritsum, 0]
                    if dp[x][y][0] == inheritsum:
                        dp[x][y][1] += dp[x + dx][y + dy][1]
                dp[x][y][0] += int(A[x][y]) if x or y else 0
        # return 0 as sum if the cell is not reachable
        return [dp[0][0][0] if dp[0][0][1] else 0, dp[0][0][1] % mod]

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

