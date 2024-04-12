240. Search a 2D Matrix II - Medium

Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

1. Integers in each row are sorted in ascending from left to right.

2. Integers in each column are sorted in ascending from top to bottom.


Example:

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.


******************************************************
key:
	- similar to 378, binary search or Split into 4 areas or special trick (O(m+n))
	- edge case:
		1) empty matrix, return empty
		2) this submatrix has no height or no width.
		3) target is already larger than the largest element or smaller than the smallest element 
           in this submatrix.


******************************************************



=======================================================================================================
method 1:

method:

	- 1) initialize a (row, col) pointer to the bottom-left of the matrix
	  2) until we find target & return true (or the pointer points to a (row, col) that lies outside of the dimensions of the matrix)
	  	2.1) if the current value > target:
	  		move one row "up". 
	  	2.2) else if the current value < target:
	  		move one column "right".
	- 

stats:

	- Time complexity : O(n+m)
	- Space complexity : O(1)


class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // start our "pointer" in the bottom-left
        int row = matrix.length-1;
        int col = 0;

        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] > target) {
                row--;
            } else if (matrix[row][col] < target) {
                col++;
            } else { // found it
                return true;
            }
        }

        return false;
    }
}

=======================================================================================================
method 2:

method:

	- Divide and Conquer
	- partition a sorted two-dimensional matrix into 4 sorted submatrices, two of which might contain 
	  target and two of which definitely do not.
	- Recursive Case: If the base case conditions have not been met, then the array has positive area 
	  and target could potentially be present. Therefore, we seek along the matrix middle column for 
	  an index row such that 
	  	
	  				matrix[row-1][mid] < target < matrix[row][mid] 

	  (obviously, if we find target during this process, we immediately return true). 
	- The existing matrix can be partitioned into 4 submatrice around this index; the top-left and 
	  bottom-right submatrice cannot contain target (via the argument outlined in Base Case section), 
	  so we can prune them from the search space. 
	- Additionally, the bottom-left and top-right submatrice are sorted two-dimensional matrices, 
	  so we can recursively apply this algorithm to them.



stats:

	- Time complexity : O(nlgn)
	- Space complexity :O(lgn) --> memory proportional to the height of its recursion tree. 
		Because this approach discards half of matrix on each level of recursion (and makes two 
		recursive calls), the height of the tree is bounded by lgn

class Solution {
    private int[][] matrix;
    private int target;

    // main function
    public boolean searchMatrix(int[][] mat, int targ) {
        // cache input values in object to avoid passing them unnecessarily
        // to `searchRec`
        matrix = mat;
        target = targ;

        // an empty matrix obviously does not contain `target`
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        return searchRec(0, 0, matrix[0].length-1, matrix.length-1);
    }

    // helper function
    private boolean searchRec(int left, int up, int right, int down) {
        // this submatrix has no height or no width.
        if (left > right || up > down) {
            return false;
        // `target` is already larger than the largest element or smaller
        // than the smallest element in this submatrix.
        } else if (target < matrix[up][left] || target > matrix[down][right]) {
            return false;
        }

        int mid = left + (right-left)/2;

        // Locate `row` such that matrix[row-1][mid] < target < matrix[row][mid]
        int row = up;
        while (row <= down && matrix[row][mid] <= target) {
            if (matrix[row][mid] == target) {
                return true;
            }
            row++;
        }

        return searchRec(left, row, mid-1, down) || searchRec(mid+1, up, right, row-1);
    }


} 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



