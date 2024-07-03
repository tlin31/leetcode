289. Game of Life - Medium


According to the Wikipedias article: "The Game of Life, also known simply as Life, is a cellular
automaton devised by the British mathematician John Horton Conway in 1970."

Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell 
interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four 
rules (taken from the above Wikipedia article):

	1. Any live cell with fewer than two live neighbors dies, as if caused by under-population.
	2. Any live cell with two or three live neighbors lives on to the next generation.
	3. Any live cell with more than three live neighbors dies, as if by over-population..
	4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

Write a function to compute the next state (after one update) of the board given its current state. 
The next state is created by applying the above rules simultaneously to every cell in the current 
state, where births and deaths occur simultaneously.

Example:

Input: 
[
  [0,1,0],
  [0,0,1],
  [1,1,1],
  [0,0,0]
]
Output: 
[
  [0,0,0],
  [1,0,1],
  [0,1,1],
  [0,1,0]
]

Follow up:

Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot
update some cells first and then use their updated values to update other cells.

In this question, we represent the board using a 2D array. In principle, the board is infinite, which
would cause problems when the active area encroaches the border of the array. How would you address 
these problems?



******************************************************
key:
	- use 0 - dead, 1 - live, 2 - go die, 3 - will live. and update board by %2
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

	-	0 - dead, 1 - live, 2 - go die, 3 - will live.

	-	

public class Solution {
	int[][] dir ={{1,-1},{1,0},{1,1},{0,-1},{0,1},{-1,-1},{-1,0},{-1,1}};

	public void gameOfLife(int[][] board) {
	    for (int i = 0; i < board.length; i++){
	        for (int j = 0; j < board[0].length; j++){
	            int live=0;
	            for(int[] d:dir){

	            	// d[0]+i = neighbor x coordinate, d[1]+j = neighbor y coordinate, 
	            	// neighbor needs to be on board! if not, skip this round
	                if(d[0]+i<0 || d[0]+i>=board.length || d[1]+j<0 || d[1]+j>=board[0].length) 
	                	continue;

	                // if neighbor is 1: already alive
	                // or is 2: is alive this round, will die next round
	                // can't include 3 because 3 means dead this current round
	                if(board[d[0]+i][d[1]+j]==1 || board[d[0]+i][d[1]+j]==2) 
	                	live++;
	            }

	            // only if has neighbors of exactly 3, it'll stay alive
	            if(board[i][j]==0 && live==3) 
	            	board[i][j]=3;

	            // will die in the next step
	            if(board[i][j]==1 && (live<2 || live>3)) 
	            	board[i][j]=2;
	        }
	    }

	    // update board
	    for(int i=0;i<board.length;i++){
	        for(int j=0;j<board[0].length;j++){
	            board[i][j] %=2;
	        }
	    }
	}
}

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
classSolution(object):
    def gameOfLife(self, board):
        ""
        :type board: List[List[int]], int = 0 dead,1 live
        " 2 dead -> live ; 3 live -> dead
        :rtype: void Do not return anything, modify board in-place instead.

        m = len(board)
        n = len(board[0])
        if m == 0 or n == 0:
            return 
        for iM in range(m):
            for iN in range(n):
                numNeighbor = sum([board[i][j]%2 for i in range(iM-1,iM+2) for j in range(iN-1,iN+2) 
                					if 0 <= i < m and 0<= j < n]) - board[iM][iN]
                if board[iM][iN] == 0 and numNeighbor == 3:
                    board[iM][iN] = 2
                if board[iM][iN] == 1 and ( numNeighbor < 2 or numNeighbor >  3):
                    board[iM][iN] = 3
        for iM in range(m):
            for iN in range(n):
                if board[iM][iN] == 2:
                    board[iM][iN] = 1
                if board[iM][iN] == 3:
                    board[iM][iN] = 0

=======================================================================================================
method 2: Follow up

Stats:

	- 
	- 


Method:

	-	
	-	If extremely sparse matrix --> only save the location of the live cells and then apply the 
	    4 rules accordingly using only these live cells.
	-	problem: when the entire board cannot fit into memory. If that is indeed the case, then we 
	    would have to approach this problem in a different way. For that scenario, we assume that the
	    contents of the matrix are stored in a file, one row at a time.

		Solution: have max 3 rows in memmory
			b/c to update a particular cell, we only have to look at its 8 neighbors which essentially 
			lie in the row above and below it. So, for updating the cells of a row, we just need the 
			row above and the row below. Thus, we read one row at a time from the file and at max we 
			will have 3 rows in memory. We will keep discarding rows that are processed and then we will 
			keepreading new rows from the file, one at a time.


def gameOfLifeInfinite(self, live):
    ctr = collections.Counter((I, J)
                              for i, j in live
                              for I in range(i-1, i+2)
                              for J in range(j-1, j+2)
                              if I != i or J != j)
    return {ij
            for ij in ctr
            if ctr[ij] == 3 or ctr[ij] == 2 and ij in live}

def gameOfLife(self, board):
    live = {(i, j) for i, row in enumerate(board) for j, live in enumerate(row) if live}
    live = self.gameOfLifeInfinite(live)
    for i, row in enumerate(board):
        for j in range(len(row)):
            row[j] = int((i, j) in live)




=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

