51. N-Queens - Hard

The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that 
no two queens attack each other.


Question: Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens placement, where 
'Q' and '.' both indicate a queen and an empty space respectively.

Example:

Input: n = 4
Output: [
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.

******************************************************
key:
	- recursion, at each pos, try Q, then proceed
	- backtrack
	- ！！ 检查对角线的情况--> 用绝对值

	- edge case:
		1) n = 0, return [], n = 1, return [Q]
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- 比较经典的回溯问题了，我们需要做的就是先在第一行放一个皇后，然后进入回溯，放下一行皇后的位置，一直走下去，
		如果已经放的皇后的数目等	于 n 了，就加到最后的结果中。然后再回到上一行，变化皇后的位置，然后去找其他的解。
		期间如果遇到当前行所有的位置都不能放皇后了，就再回到上一行，然后变化皇后的位置。再返回到下一行。
	- currentQueen 里面存 queen 在每一行的位置， ex.[0,1,2,3] --> queens在对角线
	- currentQueen 保证每一行只有一个queen，剩下的就是看列和对角线

stats:

	- 
	- Runtime: 4 ms, faster than 54.04% of Java online submissions for N-Queens.
	- Memory Usage: 37.7 MB, less than 91.89% 

	public List<List<String>> solveNQueens(int n) {
	    List<List<String>> ans = new ArrayList<>();
	    backtrack(new ArrayList<Integer>(), ans, n);
	    return ans;
	}

	private void backtrack(List<Integer> currentQueen, List<List<String>> ans, int n) {

	    // 当前皇后的个数是否等于 n 了，等于的话就加到结果中
	    if (currentQueen.size() == n) {
	        List<String> temp = new ArrayList<>();
	        for (int i = 0; i < n; i++) {
	            char[] t = new char[n];
	            Arrays.fill(t, '.');
	            t[currentQueen.get(i)] = 'Q';
	            temp.add(new String(t));
	        }
	        ans.add(temp);
	        return;
	    }

	    //尝试每一列
	    for (int col = 0; col < n; col++) {
	        //当前列是否冲突
	        if (!currentQueen.contains(col)) {
	            //判断对角线是否冲突
	            if (isDiagonalAttack(currentQueen, col)) {
	                continue;
	            }
	            //将当前列的皇后加入
	            currentQueen.add(col);
	            //去考虑下一行的情况
	            backtrack(currentQueen, ans, n);

	            //将当前列的皇后移除，去判断下一列
	            //进入这一步就是两种情况，下边的行走不通了回到这里或者就是已经拿到了一个解回到这里
	            currentQueen.remove(currentQueen.size() - 1);
	        }

	    }

	}

	private boolean isDiagonalAttack(List<Integer> currentQueen, int i) {
	    // TODO Auto-generated method stub
	    int current_row = currentQueen.size();
	    int current_col = i;
	    //判断每一行的皇后的情况
	    for (int row = 0; row < currentQueen.size(); row++) {
	        //左上角的对角线和右上角的对角线，差要么相等，要么互为相反数，直接写成了绝对值
	        if (Math.abs(current_row - row) == Math.abs(current_col - currentQueen.get(row))) {
	            return true;
	        }
	    }
	    return false;
	}



=======================================================================================================
method 2:

method:

	- I use three boolean[] array to keep tracking of the position the Queen take in the helper method.
	- boolean[] cols is for check if the certain column is taken.
	- I use two boolean[2*n] array to keep tracking of two diagonals.
		for the diagonal in the \ direction (from left up corner to right down corner) 
			the col - row will always be same e.g. (0,1), (1,2), (2,3) are on the same diagonal, 
		    the range of col - row can be (0-(n-1)) ~ ((n-1)-0), to make sure we can store the 
		    value in one array,(needs to be positive) we will add n to this, it will become to keep tracking of 
		    (col - row + n).

		for the diagonal in the / direction (from right up corner to left down corner) 
			the col + row will always be same e.g. (0,4), (1,3), (2,2), (3,1), (4,0) are on the 
			same diagonal, the range of row + col can be 0 ~ (2*n-2)
	- 

stats:

	- 
	- Runtime: 2 ms, faster than 95.15% of Java online submissions for N-Queens.
	- Memory Usage: 37.3 MB, less than 100.00% 

	 public List<List<String>> solveNQueens(int n) {
	    List<List<String>> result = new ArrayList<List<String>>();
	    helper(result, new ArrayList<String>(), 0, new boolean[n], new boolean[2*n], new boolean[2*n], n);
	    return result;
	}

	private void helper(List<List<String>> result, List<String> board, int row, 
		boolean[] cols, boolean[] d1, boolean[] d2, int n){

	    if (row == n) {
	        result.add(new ArrayList<String>(board));
	    }
	    for (int col=0; col<n; col++){
	        int id1 = col - row + n;
	        int id2 = col + row;

	        // if valid to place it here
	        if (!cols[col] && !d1[id1] && !d2[id2]){
	            char[] r = new char[n];
	            Arrays.fill(r, '.');
	            r[col] = 'Q';
	            board.add(new String(r));
	            //take up the space accordingly
	            cols[col] = true;
	            d1[id1] = true;
	            d2[id2] = true;

	            //back track --> condition is row+1 --> move to next row
	            helper(result, board, row+1, cols, d1, d2, n);

	            //reset
	            board.remove(board.size()-1);
	            cols[col] = false;
	            d1[id1] = false;
	            d2[id2] = false;
	        }
	    }
	}

