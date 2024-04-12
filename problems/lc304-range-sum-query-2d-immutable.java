304. Range Sum Query 2D - Immutable - Medium

Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper 
left corner (row1, col1) and lower right corner (row2, col2).


Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12

Note:
You may assume that the matrix does not change.
There are many calls to sumRegion function.
You may assume that row1 ≤ row2 and col1 ≤ col2.


******************************************************
key:
	- prefix sum for each row
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: prefix sum row

Method:

	-	see the 2D matrix as m rows of 1D arrays. To find the region sum, we just accumulate the 
	    sum in the region row by row.

	-	

Stats:

	- Time complexity : O(row) time per query, O(mn) time pre-computation. 

	- Space complexity : O(mn). The algorithm uses O(mn) space to store the cumulative sum of all rows.



class NumMatrix {

	private int[][] dp;
	public NumMatrix(int[][] matrix) {
	    if (matrix.length == 0 || matrix[0].length == 0) 
	    	return;

	    dp = new int[matrix.length][matrix[0].length + 1];
	    for (int r = 0; r < matrix.length; r++) {
	        for (int c = 0; c < matrix[0].length; c++) {
	            dp[r][c + 1] = dp[r][c] + matrix[r][c];
	        }
	    }
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
	    int sum = 0;
	    for (int row = row1; row <= row2; row++) {
	        sum += dp[row][col2 + 1] - dp[row][col1];
	    }
	    return sum;
	}
}


ex.
[0, 3, 3, 4, 8, 10]
[0, 5, 11, 14, 16, 17]
[0, 1, 3, 3, 4, 9]
[0, 4, 5, 5, 6, 13]
[0, 1, 1, 4, 4, 9]


=======================================================================================================
method 2: prefix from (0,0)

Method:

	-	Extending this analogy to the 2D case, we could pre-compute a cumulative region sum 
	    with respect to the origin at (0, 0)
	-	Sum(ABCD) = Sum(OD) - Sum(OB) - Sum(OC) + Sum(OA)


Stats:

	- Time complexity : O(1) time per query, O(mn) time pre-computation. 
	- Space complexity : O(mn)


	private int[][] dp;

	public NumMatrix(int[][] matrix) {
	    if (matrix.length == 0 || matrix[0].length == 0) return;
	    dp = new int[matrix.length + 1][matrix[0].length + 1];
	    for (int r = 0; r < matrix.length; r++) {
	        for (int c = 0; c < matrix[0].length; c++) {
	            dp[r + 1][c + 1] = dp[r + 1][c] + dp[r][c + 1] + matrix[r][c] - dp[r][c];
	        }
	        System.out.println(Arrays.toString(dp[r]));

	    }
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
	    return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
	}


ex.

[0, 0, 0, 0, 0, 0]
[0, 3, 3, 4, 8, 10]
[0, 8, 14, 18, 24, 27]
[0, 9, 17, 21, 28, 36]
[0, 13, 22, 26, 34, 49]

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



