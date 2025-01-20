419. Battleships in a Board - Medium

Given an m x n matrix board where each cell is a battleship 'X' or empty '.', return the 
number of the battleships on board.

Battleships can only be placed horizontally or vertically on board. In other words, they can 
only be made of the shape 1 x k (1 row, k columns) or k x 1 (k rows, 1 column), where k can 
be of any size. At least one horizontal or vertical cell separates between two battleships 
(i.e., there are no adjacent battleships).

 

Example 1:


Input: board = [["X",".",".","X"],
				[".",".",".","X"],
				[".",".",".","X"]]

Output: 2:左边一个1x1的，右边 一个1x3的

Example 2:

Input: board = [["."]]
Output: 0
 

Constraints:

m == board.length
n == board[i].length
1 <= m, n <= 200
board[i][j] is either '.' or 'X'.
 

Follow up: Could you do it in one-pass, using only O(1) extra memory and without 
modifying the values board?



******************************************************
key:
	- union find
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:
Going over all cells, we can count only those that are the "first" cell of the battleship. 
First cell will be defined as the most top-left cell. We can check for first cells by only 
counting cells that do not have an 'X' to the left and do not have an 'X' above them.


    public int countBattleships(char[][] board) {
        int m = board.length;
        if (m==0) return 0;
        int n = board[0].length;
        
        int count=0;
        
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] == '.') continue;
                if (i > 0 && board[i-1][j] == 'X') continue;
                if (j > 0 && board[i][j-1] == 'X') continue;
                count++;
            }
        }
        
        return count;
    }



