74. Search a 2D Matrix - Medium

Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following 
properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.
Example 1:

Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 3
Output: true

Example 2:

Input:
matrix = [
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
target = 13
Output: false


******************************************************
key:
	- binary search
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Use binary search, treat it as a sorted array (flat the matrix to 1d)
	- n * m matrix convert to an array => matrix[x][y] => a[x * m + y]
	- an array convert to n * m matrix => a[x] =>matrix[x / m][x % m];
	- Sorted array is a perfect candidate for the binary search because the element index in this 
	  virtual array could be easily transformed into the row and column in the initial matrix

	  row = idx // n and col = idx % n.



stats:

	- Time complexity : O(log(mn)) since it is a standard binary search.
	- Space complexity : O(1).


class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
                return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        // binary search
        int left = 0, right = m * n - 1;
        int pivotIdx, pivotElement;
        while (left <= right) {
            pivotIdx = (left + right) / 2;
            pivotElement = matrix[pivotIdx / n][pivotIdx % n];
            if (target == pivotElement) 
                return true;
            else if (target < pivotElement) 
                right = pivotIdx - 1;
            else 
                left = pivotIdx + 1;
            }
        }
        return false;
    }




=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

// not binary search！
    
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return false;
    }
    int row = 0;
    int col = matrix[0].length - 1;

    while (row < matrix.length && col >= 0) {

        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] < target) {
            row++;
        } else {
            col--;
        }
    }
    return false;
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



