311. Sparse Matrix Multiplication - Medium

Given two sparse matrices mat1 of size m x k and mat2 of size k x n, return the result of mat1 x mat2. You may assume that multiplication is always possible.
 

Example 1:


Input: mat1 = [[1,0,0],[-1,0,3]], mat2 = [[7,0,0],[0,0,0],[0,0,1]]
Output: [[7,0,0],[-7,0,3]]
Example 2:

Input: mat1 = [[0]], mat2 = [[0]]
Output: [[0]]
 

Constraints:

m == mat1.length
k == mat1[i].length == mat2.length
n == mat2[i].length
1 <= m, n, k <= 100
-100 <= mat1[i][j], mat2[i][j] <= 100

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1: Pre-indexing Non-Zeros

Method:

you should pre-index the non-zero elements or rearrange your loops to skip zeros early.

The most efficient way for larger matrices is to convert the matrices into a Compressed Sparse Row (CSR)-like format or a lookup table. By storing only the (column, value) pairs for each row, you ensure that the inner multiplication loop only runs for values that actually contribute to the result.

Stats:

	- 
	- 

class Solution {
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int m = mat1.length, k = mat1[0].length, n = mat2[0].length;
        int[][] res = new int[m][n];

        // Step 1: Pre-index non-zero elements of mat2
        List<Integer>[] mat2NonZero = new List[k];
        for (int i = 0; i < k; i++) {
            mat2NonZero[i] = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (mat2[i][j] != 0) mat2NonZero[i].add(j);
            }
        }

        // Step 2: Multiply only non-zero entries
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                if (mat1[i][j] == 0) continue; // Skip if mat1 element is 0
                for (int col : mat2NonZero[j]) {
                    res[i][col] += mat1[i][j] * mat2[j][col];
                }
            }
        }
        return res;
    }
}
