52. N-Queens II - Hard

The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that 
no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example:

Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]

******************************************************
key:
	- back track
	- set
	- edge case:
		1) empty matrix, return 0
		2)

******************************************************


=======================================================================================================
method 1:

method:

	- back track
	- 我们直接把上道题的 ans 的 size 返回就可以了，此外 currentQueen.size ( ) == n 的时候，也不用去生成一个解，
		直接加一个数字占位。


stats:

	- 
	- Runtime: 3 ms, faster than 36.18% of Java online submissions for N-Queens II.
	- Memory Usage: 34 MB, less than 8.70% 

	public int totalNQueens(int n) {

		// just store the answer
	    List<Integer> ans = new ArrayList<>();
	    backtrack(new ArrayList<Integer>(), ans, n);
	    return ans.size();
	}

	private void backtrack(List<Integer> currentQueen, List<Integer> ans, int n) {
	    if (currentQueen.size() == n) {
	        ans.add(1);
	        return;
	    }
	    for (int col = 0; col < n; col++) {
	        if (!currentQueen.contains(col)) {
	            if (isDiagonalAttack(currentQueen, col)) {
	                continue;
	            }
	            currentQueen.add(col);
	            backtrack(currentQueen, ans, n);
	            currentQueen.remove(currentQueen.size() - 1);
	        }

	    }

	}

	private boolean isDiagonalAttack(List<Integer> currentQueen, int i) {
	    int current_row = currentQueen.size();
	    int current_col = i;
	    for (int row = 0; row < currentQueen.size(); row++) {
	        if (Math.abs(current_row - row) == Math.abs(current_col - currentQueen.get(row))) {
	            return true;
	        }
	    }
	    return false;
	}



=======================================================================================================
method 2:

method:

	- 既然不用返回所有解，那么我们就不需要 currentQueen 来保存当前已加入皇后的位置。只需要一个 bool 型数组，
		来标记列是否被占有就可以了。
	- 由于没有了 currentQueen，所有不能再用之前 isDiagonalAttack 判断对角线冲突的方法了
		对角线元素的情况 --> 同一条副对角线，row + col 的值是相等的。
		对于同一条主对角线，row - col 的值是相等的。
		我们同样可以用一个 bool 型数组，来保存当前对角线是否有元素，把它们相加相减的值作为下标。
		对于 row - col ，由于出现了负数，所以可以加 1 个 n，由 [ - 3, 3 ] 转换为 [ 1 , 7 ] 。
	- 

stats:

	- 
	- Runtime: 1 ms, faster than 95.78% of Java online submissions for N-Queens II.
	- Memory Usage: 32.8 MB, less than 8.70%



public int totalNQueens(int n) {
    List<Integer> ans = new ArrayList<>();
    boolean[] cols = new boolean[n]; // 列
    boolean[] d1 = new boolean[2 * n]; // 主对角线 
    boolean[] d2 = new boolean[2 * n]; // 副对角线
    return backtrack(0, cols, d1, d2, n, 0);
}

private int backtrack(int row, boolean[] cols, boolean[] d1, boolean[] d2, int n, int count) { 
    if (row == n) {
        count++;
    } else {
        for (int col = 0; col < n; col++) {
            int id1 = row - col + n; //主对角线加 n
            int id2 = row + col;
            if (cols[col] || d1[id1] || d2[id2])
                continue;
            
            cols[col] = true;
            d1[id1] = true;
            d2[id2] = true;
            
            count = backtrack(row + 1, cols, d1, d2, n, count);
            cols[col] = false;
            d1[id1] = false;
            d2[id2] = false;
        }

    }
    return count;
}



=======================================================================================================
method 3:

method:

	- use set
	- 

stats:

	- 
	- Runtime: 5 ms, faster than 19.81% of Java online submissions for N-Queens II.
Memory Usage: 33.9 MB, less than 8.70% o




private final Set < Integer > occupiedCols = new HashSet < Integer > ();
private final Set < Integer > occupiedDiag1s = new HashSet < Integer > ();
private final Set < Integer > occupiedDiag2s = new HashSet < Integer > ();
public int totalNQueens(int n) {
    return totalNQueensHelper(0, 0, n);
}
private int totalNQueensHelper(int row, int count, int n) {
    for (int col = 0; col < n; col++) {
        if (occupiedCols.contains(col))
            continue;
        int diag1 = row - col;
        if (occupiedDiag1s.contains(diag1))
            continue;
        int diag2 = row + col;
        if (occupiedDiag2s.contains(diag2))
            continue;
        // we can now place a queen here
        if (row == n - 1)
            count++;
        else {
            occupiedCols.add(col);
            occupiedDiag1s.add(diag1);
            occupiedDiag2s.add(diag2);
            count = totalNQueensHelper(row + 1, count, n);
            // recover
            occupiedCols.remove(col);
            occupiedDiag1s.remove(diag1);
            occupiedDiag2s.remove(diag2);
        }
    }
    return count;
}