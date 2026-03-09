723. Candy Crush - Medium

This question is about implementing a basic elimination algorithm for Candy Crush.

Given an m x n integer array board representing the grid of candy where board[i][j] represents the type of candy. A value of board[i][j] == 0 represents that the cell is empty.

The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:

If three or more candies of the same type are adjacent vertically or horizontally, crush them all at the same time - these positions become empty.
After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. No new candies will drop outside the top boundary.
After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
If there does not exist more candies that can be crushed (i.e., the board is stable), then return the current board.
You need to perform the above rules until the board becomes stable, then return the stable board.

 

Example 1:


Input: board = [[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
Output: [[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
Example 2:

Input: board = [[1,3,5,5,2],[3,4,3,3,1],[3,2,4,5,2],[2,4,4,5,5],[1,4,4,1,1]]
Output: [[1,3,0,0,0],[3,4,0,5,2],[3,2,0,3,1],[2,4,0,5,2],[1,4,3,1,1]]
 

Constraints:

m == board.length
n == board[i].length
3 <= m, n <= 50
1 <= board[i][j] <= 2000


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

This approach uses negative marking to identify crushable candies simultaneously without using 
extra space, followed by a two-pointer technique to simulate gravity.


Simultaneous Crushing: 
	Use negative marking (e.g., -val) to mark candies for deletion. 
	This allows you to identify all horizontal and vertical matches in one pass without 
	prematurely removing candies that might part of a cross-match.

Gravity Simulation: 
	Use a two-pointer approach (often called an anchor or store-index) per column to 
	shift non-zero values to the bottom, then fill the top with zeros.


Stats:

	- 
	- 


class Solution {
    public int[][] candyCrush(int[][] board) {
        int R = board.length, C = board[0].length;
        boolean found = false;

        // 1. Identify candies to crush (Horizontal)
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C - 2; c++) {
                int val = Math.abs(board[r][c]);
                if (val != 0 && val == Math.abs(board[r][c+1]) && val == Math.abs(board[r][c+2])) {
                    board[r][c] = board[r][c+1] = board[r][c+2] = -val;
                    found = true;
                }
            }
        }

        // 2. Identify candies to crush (Vertical)
        for (int r = 0; r < R - 2; r++) {
            for (int c = 0; c < C; c++) {
                int val = Math.abs(board[r][c]);
                if (val != 0 && val == Math.abs(board[r+1][c]) && val == Math.abs(board[r+2][c])) {
                    board[r][c] = board[r+1][c] = board[r+2][c] = -val;
                    found = true;
                }
            }
        }

        // 3. Gravity: Move candies down
        if (found) {
            for (int c = 0; c < C; c++) {
                int storeRow = R - 1;
                for (int r = R - 1; r >= 0; r--) {
                    if (board[r][c] > 0) { //shift non zero values to bottom
                        board[storeRow--][c] = board[r][c];
                    }
                }
                while (storeRow >= 0) {
                    board[storeRow--][c] = 0;
                }
            }
            return candyCrush(board); // Repeat until stable
        }

        return board;
    }
}








