688. Knight Probability in Chessboard - Medium


On an NxN chessboard, a knight starts at the r-th row and c-th column and attempts to make exactly K
moves. The rows and columns are 0 indexed, so the top-left square is (0, 0), and the bottom-right 
square is (N-1, N-1).

A chess knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a
cardinal direction, then one square in an orthogonal direction.

 

Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if 
the piece would go off the chessboard) and moves there.

The knight continues moving until it has made exactly K moves or has moved off the chessboard. Return 
the probability that the knight remains on the board after it has stopped moving.

 

Example:

Input: 3, 2, 0, 0
Output: 0.0625
Explanation: There are two moves (to (1,2), (2,1)) that will keep the knight on the board.
From each of those positions, there are also two moves that will keep the knight on the board.
The total probability the knight stays on the board is 0.0625.
 

Note:

N will be between 1 and 25.
K will be between 0 and 100.
The knight always initially starts on the board.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- 
	- 


Method:

	-  At every k and position i， j we store the probability that the knight landed at position 
	   i j at step k. We know that this probability is the sum of probabilities of the 8 directions 
	   in the previous step k-1 because in the previous step all 8 of those knights have a chance of
	   moving here. 

	   For example since one of the directions is 2, 1 we take the current i-2 and j-1 and add that
	   probability/8.0 (because if the knight is currently at i-2, j-1 the chance is only /8.0 that 
	   he will choose this direction out of his 7 other choices).

	-  We initialize the r , c index of the k==0 board to 1, because at step 0, we already have the
	   knight at position r, c so the chance it lands there in 0 steps is 100%.
	
		The result is the sum of probabilities in all areas of the board in the Kth index Board.

class Solution {
    int [][] direction =new int[][]{{2,1},{-2,1},{2,-1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
   
    public double knightProbability(int N, int K, int r, int c) {
        double [][][] ways = new double[K+1][N][N];
        ways[0][r][c]=1;

        // k steps
        for(int k=1; k<=K;++k){
            for(int i=0; i<N;++i){
                for(int j=0; j<N;++j){
                    for(int [] dir: direction){
                        int oldR = i-dir[0];
                        int oldC = j-dir[1];

                        // check if the last step is within range
                        if(oldR>=0 && oldC>=0 && oldR<N && oldC<N){
                            ways[k][i][j]+=(ways[k-1][oldR][oldC]/8.0);
                        }
                    }
                }
            }
        }
        double res = 0;
        for(int i=0; i<N;++i){
            for(int j=0; j<N;++j){
                res+=ways[K][i][j];
            }
        }
        return res;
    }
}


because we only use the k and k-1 at every step we can just make the new Kth board be a variable and 
set the previous one to a previous variable, reducing the space to O(n^2):

class Solution {
    int [][] direction =new int[][]{{2,1},{-2,1},{2,-1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
   
    public double knightProbability(int N, int K, int r, int c) {
        double [][] prevWays = new double[N][N];
        prevWays[r][c]=1;
        double res = 0;
        for(int k=1; k<=K;++k){
        	// the new board
            double [][] ways = new double[N][N];
            for(int i=0; i<N;++i){
                for(int j=0; j<N;++j){
                    for(int [] dir: direction){
                        int oldR = i-dir[0];
                        int oldC = j-dir[1];
                        if(oldR>=0 && oldC>=0 && oldR<N && oldC<N){
                            ways[i][j]+=(prevWays[oldR][oldC]/8.0);
                        }
                    }
                }
            }
            prevWays=ways;
        }

        for(int i=0; i<N;++i){
            for(int j=0; j<N;++j){
                res+=prevWays[i][j];
            }
        }
        return res;
    }
}



=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



