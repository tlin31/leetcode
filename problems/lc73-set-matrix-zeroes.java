73. Set Matrix Zeroes - Medium

Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.

Example 1:

Input: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]

Example 2:
Input: 
[
  [0,1,2,0],
  [3,4,5,2],
  [1,3,1,5]
]
Output: 
[
  [0,0,0,0],
  [0,4,5,0],
  [0,3,1,0]
]

Follow up:
A straight forward solution using O(mn) space is probably a bad idea.
A simple improvement uses O(m + n) space, but still not the best solution.
Could you devise a constant space solution?


******************************************************
key:
  - trick, set the first 
  - edge case:
    1) empty string, return empty
    2)

******************************************************



=======================================================================================================
Method 1:

Method:
  - store states of each row in the first of that row, and store states of each column in the first 
    of that column. Because the state of row0 and the state of column0 would occupy the same cell, 
    I let it be the state of row0, and use another variable "col0" for column0. 

    In the first phase, use matrix elements to set states in a top-down way. 
    In the second phase, use states to set matrix elements in a bottom-up way.



Stats:

  - Runtime: 1 ms, faster than 90.15% 
  - Memory Usage: 42.4 MB, less than 97.14% 


class Solution {
    void setZeroes(int[][] matrix) {
        boolean isZeroCol = false;
        boolean isZeroRow = false;

        //check the first column
        for (int i = 0; i < matrix.length; i++) { 
            if (matrix[i][0] == 0) {
                isZeroCol = true;
                break;
            } 
        }

        //check the first row
        for (int i = 0; i < matrix[0].length; i++) { 
            if (matrix[0][i] == 0) {
                isZeroRow = true;
                break;
            } 
        }

        //check except the first row and column
        for (int i = 1; i < matrix.length; i++) { 
            for (int j = 1; j < matrix[0].length; j++) 
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
        }

        //set others to 0 except the first row and column
        for (int i = 1; i < matrix.length; i++) { 
           for (int j = 1; j < matrix[0].length; j++) 
               if (matrix[i][0] == 0 || matrix[0][j] == 0)
                   matrix[i][j] = 0;
        }

        //handle the first column
        if (isZeroCol) { 
            for (int i = 0; i < matrix.length; i++) 
                matrix[i][0] = 0;
        }

        //handle the first row
        if (isZeroRow) { 
            for (int i = 0; i < matrix[0].length; i++) 
                matrix[0][i] = 0;
        }
  }
}

=======================================================================================================
method 2 (straight forward, no extra space):

Method:

  1. Iterate over the original array and if we find an entry, say cell[i][j] to be 0, then we 
     iterate over row i and column j separately and set all the non zero elements to some high 
     negative dummy value (say -1000000). Note, choosing the right dummy value for your solution 
     is dependent on the constraints of the problem. 

     Any value outside the range of permissible values in the matrix will work as a dummy value.

  2. Finally, we iterate over the original matrix and if we find an entry to be equal to the high
     negative value (constant defined initially in the code as MODIFIED), then we set the value in 
     the cell to 0.

Stats:

  - Time Complexity : O((M×N)×(M+N)) where M and N are the number of rows and columns respectively. 
                      Even though this solution avoids using space, but is very inefficient since 
                      in worst case for every cell we might have to zero out its corresponding row 
                      and column. Thus for all (M×N) cells zeroing out (M + N) cells.
  - Space Complexity : O(1)


class Solution {
  public void setZeroes(int[][] matrix) {
    int MODIFIED = -1000000;
    int R = matrix.length;
    int C = matrix[0].length;

    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (matrix[r][c] == 0) {
          // We modify the corresponding rows and column elements in place.
          // Note, we only change the non zeroes to MODIFIED
          for (int k = 0; k < C; k++) {
            if (matrix[r][k] != 0) {
              matrix[r][k] = MODIFIED;
            }
          }
          for (int k = 0; k < R; k++) {
            if (matrix[k][c] != 0) {
              matrix[k][c] = MODIFIED;
            }
          }
        }
      }
    }

    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        // Make a second pass and change all MODIFIED elements to 0 """
        if (matrix[r][c] == MODIFIED) {
          matrix[r][c] = 0;
        }
      }
    }
  }
}


=======================================================================================================
method 3 (straight forward):

Method:
  
    If any cell of the matrix has a zero we can record its row and column number. All the cells 
    of this recorded row and column can be marked zero in the next iteration.

Algorithm

    We make a pass over our original array and look for zero entries.
    If we find that an entry at [i, j] is 0, then we need to record somewhere the row i and column j.
    So, we use two sets, one for the rows and one for the columns.
         if cell[i][j] == 0 {
             row_set.add(i)
             column_set.add(j)
         }

    Finally, we iterate over the original matrix. For every cell we check if the row r or column c 
    had been marked earlier. If any of them was marked, we set the value in the cell to 0.

         if r in row_set or c in column_set {
             cell[r][c] = 0
         }

Stats:

  - Time Complexity: O(M×N) where M and N are the number of rows and columns respectively.
  - Space Complexity: O(M + N).


class Solution {
  public void setZeroes(int[][] matrix) {
    int R = matrix.length;
    int C = matrix[0].length;
    Set<Integer> rows = new HashSet<Integer>();
    Set<Integer> cols = new HashSet<Integer>();

    // Essentially, we mark the rows and columns that are to be made zero
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        if (matrix[i][j] == 0) {
          rows.add(i);
          cols.add(j);
        }
      }
    }

    // Iterate over the array once again and using the rows and cols sets, update the elements.
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        if (rows.contains(i) || cols.contains(j)) {
          matrix[i][j] = 0;
        }
      }
    }
  }
}

