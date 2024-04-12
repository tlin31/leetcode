1074. Number of Submatrices That Sum to Target - Hard

Given a matrix, and a target, return the number of non-empty submatrices that sum to target.

A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.

Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate 
that is different: for example, if x1 != x1'.
'
 

Example 1:

Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
Output: 4
Explanation: The four 1x1 submatrices that only contain 0.


Example 2:
Input: matrix = [[1,-1],[-1,1]], target = 0
Output: 5
Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.
 

Note:

1 <= matrix.length <= 300
1 <= matrix[0].length <= 300
-1000 <= matrix[i] <= 1000
-10^8 <= target <= 10^8


******************************************************
key:
	- sliding window
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- For each row, calculate the prefix sum.
	  For each pair of columns,calculate the accumulated sum of rows.
	  Now this problem is same to, "Find the Subarray with Target Sum".
	- 

stats:

	- Time O(M*N^2)
	- Space O(N)
	- Runtime: 727 ms, faster than 92.11% 
	- Memory Usage: 45.4 MB, less than 100.00% 

public int numSubmatrixSumTarget(int[][] A, int target) {
        int res = 0, 
         	m = A.length, 
         	n = A[0].length;

        // 1. calculate prefix sum for each row
        for (int i = 0; i < m; i++)
            for (int j = 1; j < n; j++)
                A[i][j] += A[i][j - 1];

		// key = unique value of all possible prefix sum that we've seen so far
		// value = the count (number of appearances) of each prefix sum value we've seen so far
		// an empty submatrix trivially has a sum of 0
        Map<Integer, Integer> counter = new HashMap<>();

        //2. for every possible range between two columns, accumulate the prefix sum of submatrices 
        // that can be formed between these two columns by adding up the sum of values between 
        // these two columns for every row
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                counter.clear();
                counter.put(0, 1);
                int cur = 0;

                // the rows
                // we are actually calculating the prefix sum of submatrices which has column 
                // 1, 2, and 3,... by adding up the sum of matrix[0][1...3], matrix[1][1...3] ... 
                // matrix[m-1][1...3] row by row, starting from the first row (row 0). 

                for (int k = 0; k < m; k++) {
                    cur += A[k][j] - (i > 0 ? A[k][i - 1] : 0);
                    res += counter.getOrDefault(cur - target, 0);
                    counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                }
            }
        }
        return res;
    }


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

Method:



    Compute 2D prefix sum ps. To simplify the code, we allocate one more row and one more column, 
        reserving row 0 and column 0 for zero values. 

    Iterate over the rows: r1 from 1 to r, and r2 from r1 to r:

        Inside this double loop, the left and right row boundaries are fixed. Now it is time to 
          initialize a hashmap: 1D prefix sum -> number of matrices which use [r1, r2] rows and sum to 
          this prefix sum. Sum of empty matrix is equal to zero: h[0] = 1.

        Iterate over the columns from 1 to c + 1. At each step:

            Compute current 1D prefix sum curr_sum using previously computed 2D prefix sum ps: 
               curr_sum = ps[r2][col] - ps[r1 - 1][col].

            The number of times the sum curr_sum - target occurred, defines the number of matrices 
              which use r1 ... r2 rows and sum to target. Increment the count: count += h[curr_sum - 
              target].

            Add the current 1D prefix sum into hashmap.

    Return count.




class Solution {
  public int numSubmatrixSumTarget(int[][] matrix, int target) {
    
    int r = matrix.length, 
        c = matrix[0].length;

    // compute 2D prefix sum
    int[][] ps = new int[r + 1][c + 1];
    for (int i = 1; i < r + 1; ++i) {
      for (int j = 1; j < c + 1; ++j) {
        ps[i][j] = ps[i - 1][j] + ps[i][j - 1] - ps[i - 1][j - 1] + matrix[i - 1][j - 1];
      }
    }

    int count = 0, currSum;
    Map<Integer, Integer> h = new HashMap();
    
    // reduce 2D problem to 1D by fixing two rows r1 and r2 and 
    // computing 1D prefix sum for all matrices using [r1..r2] rows
    for (int r1 = 1; r1 < r + 1; ++r1) {
      for (int r2 = r1; r2 < r + 1; ++r2) {
        h.clear();
        h.put(0, 1);
        for (int col = 1; col < c + 1; ++col) {
          // current 1D prefix sum
          currSum = ps[r2][col] - ps[r1 - 1][col];

          // add subarrays which sum up to (currSum - target)
          count += h.getOrDefault(currSum - target, 0);

          // save current prefix sum
          h.put(currSum, h.getOrDefault(currSum, 0) + 1);
        }
      }
    }

    return count;
  }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def numSubmatrixSumTarget(self, A, target):
        m, n = len(A), len(A[0])
        for row in A:
            for i in xrange(n - 1):
                row[i + 1] += row[i]
        res = 0
        for i in xrange(n):
            for j in xrange(i, n):
                c = collections.defaultdict(int)
                cur, c[0] = 0, 1
                for k in xrange(m):
                    cur += A[k][j] - (A[k][i - 1] if i > 0 else 0)
                    res += c[cur - target]
                    c[cur] += 1
        return res
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



