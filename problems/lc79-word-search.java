79. Word Search - Medium

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells 
are those horizontally or vertically neighboring. 

The same letter cell may not be used more than once.

Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.


******************************************************
key:
	- backtrack, all cell dfs?
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- backtrack
	- 

stats:

	- Time Complexity: O(N * 4^L), where N = number of cells in the board and L = length of the 
					   word to be matched.
					   For the backtracking function, its execution trace would be visualized as a 
					   4-ary tree, each of the branches represent a potential exploration in the 
					   corresponding direction. 

					   Therefore, in the worst case, the total number of invocation would be the number 
					   of nodes in a full 4-nary tree, which is about 4^L.
					   We iterate through the board for backtracking, i.e. there could be N times invocation for the backtracking function in the worst case.

	- Space Complexity: O(L)




class Solution {
  private char[][] board;
  private int ROWS;
  private int COLS;

  // main function
  public boolean exist(char[][] board, String word) {
    this.board = board;
    this.ROWS = board.length;
    this.COLS = board[0].length;

    for (int row = 0; row < this.ROWS; ++row)
      for (int col = 0; col < this.COLS; ++col)
        if (this.backtrack(row, col, word, 0))
          return true;

    return false;
  }

  protected boolean backtrack(int row, int col, String word, int index) {
    // Step 1). check the bottom case. 
    if (index >= word.length())
      return true;

    // Step 2). Check the boundaries. 
    if (row < 0 || row == this.ROWS || col < 0 || col == this.COLS
        || this.board[row][col] != word.charAt(index))
      return false;

    // Step 3). explore the neighbors in DFS 
    boolean ret = false;

    // mark the path before the next exploration
    this.board[row][col] = '#';

    int[] rowOffsets = {0, 1, 0, -1};
    int[] colOffsets = {1, 0, -1, 0};
    for (int d = 0; d < 4; ++d) {
      ret = this.backtrack(row + rowOffsets[d], col + colOffsets[d], word, index + 1);
      if (ret)
        break;
    }

    // Step 4). recover the result
    this.board[row][col] = word.charAt(index);
    return ret;
  }
}


=======================================================================================================
method 2:

method:

	- dfs
	- 

stats:

	- 
	- 

public boolean exist(char[][] board, String word) {
	for (int i = 0; i < board.length; i++)
		for (int j = 0; j < board[0].length; j++)
			if ((board[i][j] == word.charAt(0)) && dfs(board, i, j, 0, word))
				return true;
	return false;
}

private boolean dfs(char[][] board, int i, int j, int count, String word) {
	if (count == word.length())
		return true;
	if (i == -1 || i == board.length || j == -1 || j == board[0].length || board[i][j] != word.charAt(count))
		return false;
	char temp = board[i][j];
	board[i][j] = ' ';
	boolean found = dfs(board, i + 1, j, count + 1, word) ||
			        dfs(board, i - 1, j, count + 1, word) ||
					dfs(board, i, j + 1, count + 1, word) ||
					dfs(board, i, j - 1, count + 1, word);
	board[i][j] = temp;
	return found;
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



