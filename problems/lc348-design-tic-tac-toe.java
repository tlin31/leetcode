348. Design Tic-Tac-Toe - Medium

Design a Tic-tac-toe game that is played between two players on a n x n grid.

You may assume the following rules:

	1. A move is guaranteed to be valid and is placed on an empty block.
	2. Once a winning condition is reached, no more moves is allowed.
	3. A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal 
	   row wins the game.
Example:
Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.

TicTacToe toe = new TicTacToe(3);

toe.move(0, 0, 1); -> Returns 0 (no one wins)
|X| | |
| | | |    // Player 1 makes a move at (0, 0).
| | | |

toe.move(0, 2, 2); -> Returns 0 (no one wins)
|X| |O|
| | | |    // Player 2 makes a move at (0, 2).
| | | |

toe.move(2, 2, 1); -> Returns 0 (no one wins)
|X| |O|
| | | |    // Player 1 makes a move at (2, 2).
| | |X|

toe.move(1, 1, 2); -> Returns 0 (no one wins)
|X| |O|
| |O| |    // Player 2 makes a move at (1, 1).
| | |X|

toe.move(2, 0, 1); -> Returns 0 (no one wins)
|X| |O|
| |O| |    // Player 1 makes a move at (2, 0).
|X| |X|

toe.move(1, 0, 2); -> Returns 0 (no one wins)
|X| |O|
|O|O| |    // Player 2 makes a move at (1, 0).
|X| |X|

toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
|X| |O|
|O|O| |    // Player 1 makes a move at (2, 1).
|X|X|X|


Follow up:
Could you do better than O(n^2) per move() operation?


******************************************************
key:
	- easier check condition --> 一共8条可能赢得line，每次一个move，在那个line的count++，

	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- keep a count for each row and column. 
	- If at any time a row or column matches the size of the board then that player has won.
	- To keep track of which player, I add one for Player1 and -1 for Player2. There are two 
	  additional variables to keep track of the count of the diagonals. 
	- Each time a player places a piece we just need to check the count of that row, column, 
	  diagonal and anti-diagonal.
	- Note: is in anti-diagonal if (row+col == size-1)


stats:

	- 
	- 



public class TicTacToe {
	private int[] rows;
	private int[] cols;
	private int diagonal;
	private int antiDiagonal;

	public TicTacToe(int n) {
	    rows = new int[n];
	    cols = new int[n];
	}

	public int move(int row, int col, int player) {
	    int toAdd = player == 1 ? 1 : -1;
	    
	    rows[row] += toAdd;
	    cols[col] += toAdd;

	    if (row == col){
	        diagonal += toAdd;
	    }
	    
	    if (row+col == size-1){
	        antiDiagonal += toAdd;
	    }
	    
	    int size = rows.length;

	    if (Math.abs(rows[row]) == size ||
	        Math.abs(cols[col]) == size ||
	        Math.abs(diagonal) == size  ||
	        Math.abs(antiDiagonal) == size){
	    	
	        	return player;
	    }
	    
	    return 0;
	}
}
=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



