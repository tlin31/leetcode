1277. Count Square Submatrices with All Ones - Medium

Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.

 

Example 1:

Input: matrix =
[
  [0,1,1,1],
  [1,1,1,1],
  [0,1,1,1]
]
Output: 15

Explanation: 
  [0,1,1,1],
  [1,1,2,2],
  [0,1,2,3]

There are 10 squares of side 1.
There are 4 squares of side 2.
There is  1 square of side 3.
Total number of squares = 10 + 4 + 1 = 15.



Example 2:
Input: matrix = 
[
  [1,0,1],
  [1,1,0],
  [1,1,0]
]
Output: 7
Explanation: 
There are 6 squares of side 1.  
There is 1 square of side 2. 
Total number of squares = 6 + 1 = 7.
 

Constraints:

1 <= arr.length <= 300
1 <= arr[0].length <= 300
0 <= arr[i][j] <= 1


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	-  dp[i][j] means the biggest square size with A[i][j] as bottom-right corner, & there are dp[i][j] 
     squares whose lower right corner element are matrix[i][j]. 

     The answer of count-square-submatrices-with-all-ones is sum of all dp[i][j].

		If A[i][j] == 0, no square.

		If A[i][j] == 1, we compare the size of square dp[i-1][j-1], dp[i-1][j] and dp[i][j-1].
		
		min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1 is the maximum size of square that we can find.

	- 

stats:

	- Time O(M*N)
	- Space O(1)


  public int countSquares(int[][] A) {
        int res = 0;
        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < A[0].length; ++j) {
                if (A[i][j] > 0 && i > 0 && j > 0) {
                    A[i][j] = Math.min(A[i - 1][j - 1], Math.min(A[i - 1][j], A[i][j - 1])) + 1;
                }
                res += A[i][j];
            }
        }
        return res;
    }


For example:
1, 1, 1
1, 1, 1
1, 1, 1

dp[0][0] = 1
dp[1][1] = 2 (the square including A[1][1] can be square with element A[1][1] with size: 2 * 2, 1 * 1)
dp[2][2] = 3 (the square including A[2][2] can be square with element A[2][2] with size: 3 * 3, 2 * 2, 1 * 1)


=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



