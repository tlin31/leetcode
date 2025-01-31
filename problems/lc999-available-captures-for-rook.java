999. Available Captures for Rook - Easy


On an 8 x 8 chessboard, there is one white rook.  
There also may be empty squares, white bishops, and black pawns.  These are given as characters 'R', 
'.', 'B', and 'p' respectively. Uppercase characters represent white pieces, and lowercase characters 
represent black pieces.

The rook moves as in the rules of Chess: it chooses one of four cardinal directions (north, east, west, 
and south), then moves in that direction until it chooses to stop, reaches the edge of the board, or 
captures an opposite colored pawn by moving to the same square it occupies.  Also, rooks cannot move 
into the same square as other friendly bishops.

Return the number of pawns the rook can capture in one move.

 

Example 1:



Input: [[".",".",".",".",".",".",".","."],
        [".",".",".","p",".",".",".","."],
        [".",".",".","R",".",".",".","p"],
        [".",".",".",".",".",".",".","."],
        [".",".",".",".",".",".",".","."],
        [".",".",".","p",".",".",".","."],
        [".",".",".",".",".",".",".","."],
        [".",".",".",".",".",".",".","."]]
Output: 3
Explanation: 
In this example the rook is able to capture all the pawns.
Example 2:



Input: [[".",".",".",".",".",".",".","."],
		[".","p","p","p","p","p",".","."],
		[".","p","p","B","p","p",".","."],
		[".","p","B","R","B","p",".","."],
		[".","p","p","B","p","p",".","."],
		[".","p","p","p","p","p",".","."],
		[".",".",".",".",".",".",".","."],
		[".",".",".",".",".",".",".","."]]
Output: 0
Explanation: 
Bishops are blocking the rook to capture any pawn.


Example 3:



Input: [[".",".",".",".",".",".",".","."],
		[".",".",".","p",".",".",".","."],
		[".",".",".","p",".",".",".","."],
		["p","p",".","R",".","p","B","."],
		[".",".",".",".",".",".",".","."],
		[".",".",".","B",".",".",".","."],
		[".",".",".","p",".",".",".","."],
		[".",".",".",".",".",".",".","."]]
Output: 3
Explanation: 
The rook can capture the pawns at positions b5, d6 and f5.
 

Note:

board.length == board[i].length == 8
board[i][j] is either 'R', '.', 'B', or 'p'
There is exactly one cell with board[i][j] == 'R'


******************************************************
key:
	- just for loopss...
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Runtime: O(n), where n is the total number of cells. 
	           In the worst case, we need to check all cells to find the rock, and then check one 
	           row and one column.
	- Memory: O(1).



Method:

	-	
	-	



    public int numRookCaptures(char[][] board) {
        int x0 = 0, y0 = 0, res = 0;
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                if (board[i][j] == 'R') {
                    x0 = i;
                    y0 = j;
                }

        for (int[] d : new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}}) {
            for (int x = x0 + d[0], y = y0 + d[1]; 0 <= x && x < 8 && 0 <= y && y < 8; 
                 x += d[0], y += d[1]) {

                if (board[x][y] == 'p') res++;
                if (board[x][y] != '.') break;
            }
        }
        return res;
    }






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

 def numRookCaptures(self, board):
        for i in range(8):
            for j in range(8):
                if board[i][j] == 'R':
                    x0, y0 = i, j
        res = 0
        for i, j in [[1, 0], [0, 1], [-1, 0], [0, -1]]:
            x, y = x0 + i, y0 + j
            while 0 <= x < 8 and 0 <= y < 8:
                if board[x][y] == 'p': res += 1
                if board[x][y] != '.': break
                x, y = x + i, y + j
        return res


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

