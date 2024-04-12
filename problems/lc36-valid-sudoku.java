36. Valid Sudoku - Medium

Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according 
to the following rules:

Each row must contain the digits 1-9 without repetition.
Each column must contain the digits 1-9 without repetition.
Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.

A partially filled sudoku which is valid.

The Sudoku board could be partially filled, where empty cells are filled with the character '.'.

Example 1:

Input:
[
  ["5","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: true

Note:

A Sudoku board (partially filled) could be valid but is not necessarily solvable.
Only the filled cells need to be validated according to the mentioned rules.
The given board contain only digits 1-9 and the character '.'.
The given board size is always 9x9.


******************************************************
key:
	- use 3 functions --> row, column, small box
	- use hash map for quick search whether a number is in a row?
	- optimize: end for loop ahead of time
	- edge case: none?
	- use of hash map --> !singleSet.add(board[i][j]) or getOrDefault

******************************************************



=======================================================================================================
method 1:

method:

	- 暴力解法， check all 3 conditions
	- 

stats:
	- 时间复杂度：整个棋盘访问了三次，如果棋盘大小是 n，那么就是 3n。也就是 O（n）。
	- 空间复杂度：O（1）


	public boolean isValidSudoku(char[][] board) {
	    //判断每一行
	    for (int i = 0; i < 9; i++) {
	        if (!isValidRows(board[i])) {
	            return false;
	        }
	    }
	    //判断每一列
	    for (int i = 0; i < 9; i++) {
	        if (!isValidCols(i, board)) {
	            return false;
	        }
	    }
	    //判断每个小棋盘
	    for (int i = 0; i < 9; i = i + 3) {
	        for (int j = 0; j < 9; j = j + 3) {
	            if (!isValidSmall(i, j, board)) {
	                return false;
	            }
	        }

	    }
	    return true;
	}

	public boolean isValidRows(char[] board) {
	    HashMap<Character, Integer> hashMap = new HashMap<>();
	    for (char c : board) {
	        if (c != '.') {

	        	// if there already exists this key
	            if (hashMap.getOrDefault(c, 0) != 0) {
	                return false;
	            } else {
	                hashMap.put(c, 1);
	            }
	        }
	    }
	    return true;
	}

public boolean isValidCols(int col, char[][] board) {
    HashMap<Character, Integer> hashMap = new HashMap<>();
    for (int i = 0; i < 9; i++) {
        char c = board[i][col];
        if (c != '.') {
            if (hashMap.getOrDefault(c, 0) != 0) {
                return false;
            } else {
                hashMap.put(c, 1);
            }
        }
    }
    return true;
}

public boolean isValidSmall(int row, int col, char[][] board) {
    HashMap<Character, Integer> hashMap = new HashMap<>();

    //remember how to write this
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            char c = board[row + i][col + j];
            if (c != '.') {
                if (hashMap.getOrDefault(c, 0) != 0) {
                    return false;
                } else {
                    hashMap.put(c, 1);
                }
            }
        }
    }
    return true;
}





=======================================================================================================
method 2:

method:

	- optimize of method 1, check all 3 conditions using 1 function
	- 

stats:
	- Runtime: 3 ms, faster than 70.85% of Java online submissions for Valid Sudoku.
	- Memory Usage: 42.9 MB, less than 97.10% 

	public boolean isValidSudoku(char[][] board) {
	    for (int i = 0; i < 9; i++) {
	        if (!isParticallyValid(board, i, 0, i, 8)) return false;
	        if (!isParticallyValid(board, 0, i, 8, i)) return false;
	    }
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {

	        	// optimize/reduce # of counts using mod
	            if (!isParticallyValid(board, (i/3)*3,(i/3)*3+2,(i%3)*3,(i%3)*3+2)) return false;
	        }
	    }
	    return true;
	}
	private boolean isParticallyValid(char[][] board, int x1, int y1, int x2, int y2) {
	    Set singleSet = new HashSet();
	    for (int i = x1; i <= x2; i++) {
	        for (int j = y1; j <= y2; j++) {
	            if (board[i][j] != '.')
	                if (!singleSet.add(board[i][j])) return false;
	        }
	    }
	    return true;
	}




=======================================================================================================
method 3:

method:
	- 只遍历一遍。
	- 我们用 HashSet 实现放在一起的作用，但是这样的话总共就是 9 行，9 列，9 个小棋盘，27 个 HashSet 了。
	  我们其实可以在放的时候标志一下，例如
		如果第 4 行有一个数字 8，我们就 (8)4，把 "(8)4"放进去。
		如果第 5 行有一个数字 6，我们就 5(6)，把 "5(6)"放进去。
		小棋盘看成一个整体，总共是 9 个，3 行 3 列，如果第 2 行第 1 列的小棋盘里有个数字 3，我们就把 "2(3)1" 放进去。
		这样 1 个 HashSet 就够了。

stats:
	- 时间复杂度：如果棋盘大小总共是 n，那么只遍历了一次，就是 O（n）。
	- 空间复杂度：如果棋盘大小总共是 n，最坏的情况就是每个地方都有数字，就需要存三次，O（n）。
	- Runtime: 5 ms, faster than 21.57% of Java online submissions for Valid Sudoku.
	- Memory Usage: 43.3 MB, less than 95.65% of

public boolean isValidSudoku(char[][] board) {
    Set seen = new HashSet();
    for (int i=0; i<9; ++i) {
        for (int j=0; j<9; ++j) {
            char number = board[i][j];
            if (number != '.')
                if (!seen.add(number + " in row " + i) ||
                    !seen.add(number + " in column " + j) ||
                    !seen.add(number + " in block " + i/3 + "-" + j/3))
                    return false;
        }
    }
    return true;
}



=======================================================================================================
method 4:

method:
	- use bit operation
	- keep track of each row, column and 3x3 block (bucket). each 1 bit of integer says: 
	  number which corresponds to this position has been found in this row/column/bucket. 
	  the formula (i / 3) * 3 + j / 3 produces index of bucket given row and column.
	- Than you just check row, column, bucket for the current number. If you seen it before - reject board.

stats:
	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Valid Sudoku.
	- Memory Usage: 43.3 MB, less than 95.65% 

public boolean isValidSudoku(char[][] board) {
    int [] vset = new int [9];
    int [] hset = new int [9];
    int [] bckt = new int [9];
    int idx = 0;
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] != '.') {

            	//put the num in bucket
                idx = 1 << (board[i][j] - '0') ;
                if ((hset[i] & idx) > 0 ||
                    (vset[j] & idx) > 0 ||
                    (bckt[(i / 3) * 3 + j / 3] & idx) > 0) return false;
                hset[i] |= idx;
                vset[j] |= idx;
                bckt[(i / 3) * 3 + j / 3] |= idx;
            }
        }
    }
    return true;
}







