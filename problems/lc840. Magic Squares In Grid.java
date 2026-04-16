840. Magic Squares In Grid- Medium

A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.

Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

Note: while a magic square can only contain numbers from 1 to 9, grid may contain numbers up to 15.

 

Example 1:


Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
Output: 1
Explanation: 
The following subgrid is a 3 x 3 magic square:

while this one is not:

In total, there is only one magic square inside the given grid.
Example 2:

Input: grid = [[8]]
Output: 0
 

Constraints:

row == grid.length
col == grid[i].length
1 <= row, col <= 10
0 <= grid[i][j] <= 15


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:


Algorithm: Sliding Window with Mathematical Optimization

A 3x3 magic square using numbers 1–9 has strict mathematical properties that allow for efficient filtering: 

	1.Constant Sum: The sum of all numbers 1–9 is 45. Since there are three rows with equal sums, each row, column, and diagonal must sum to exactly 15.

	2. Central Element: The middle element of any 3x3 magic square with numbers 1–9 must be 5.

	3. Range and Uniqueness: Every number from 1 to 9 must appear exactly once. 


Steps:

	1. Iterate through Subgrids: Loop through every possible 3x3 starting position [i,j] in the grid	
	2. Early Exit (Center Check): If the center element grid[i+1][j+1] is not 5, skip the check.
	3. Validate Contents: Use a frequency array or set to ensure all nine cells contain distinct numbers from 1 to 9.
	4. Validate Sums: Check if all three rows, three columns, and two diagonals sum to 15. 



Stats: time - O(Row x Col)


class Solution {
	//main function
    public int numMagicSquaresInside(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int count = 0;
        for (int r = 0; r <= rows - 3; r++) {
            for (int c = 0; c <= cols - 3; c++) {
                if (grid[r+1][c+1] == 5 && isMagic(grid, r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isMagic(int[][] grid, int r, int c) {
        int[] counts = new int[16]; // Input can be up to 15
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = grid[r+i][c+j];
                if (val < 1 || val > 9 || counts[val] > 0) return false;
                counts[val]++;
            }
        }
        
        // Rows
        for (int i = 0; i < 3; i++) {
            if (grid[r+i][c] + grid[r+i][c+1] + grid[r+i][c+2] != 15) return false;
        }
        // Cols
        for (int j = 0; j < 3; j++) {
            if (grid[r][c+j] + grid[r+1][c+j] + grid[r+2][c+j] != 15) return false;
        }
        // Diagonals
        if (grid[r][c] + grid[r+1][c+1] + grid[r+2][c+2] != 15) return false;
        if (grid[r][c+2] + grid[r+1][c+1] + grid[r+2][c] != 15) return false;
        
        return true;
    }
}



