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

public class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] visited = new boolean[m][n];
        boolean result = false;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    result = backtrack(board, word, visited, i, j, 0);
                    if (result) return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean backtrack(char[][] board, String word, boolean[][] visited, int i, int j, int index) {
        if (index == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] || board[i][j] != word.charAt(index)) {
            return false;
        }
        
        visited[i][j] = true;
        
        if (backtrack(board, word, visited, i + 1, j, index + 1) ||
            backtrack(board, word, visited, i - 1, j, index + 1) ||
            backtrack(board, word, visited, i, j + 1, index + 1) ||
            backtrack(board, word, visited, i, j - 1, index + 1)) {
            return true;
        }
        
        visited[i][j] = false;
        return false;
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



