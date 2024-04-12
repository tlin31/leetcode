931. Minimum Falling Path Sum - Medium


Given a square array of integers A, we want the minimum sum of a falling path through A.


A falling path starts at any element in the first row, and chooses one element from each row.  
The next rows choice must be in a column that is different from the previous rows column by at most one.

 

Example 1:

Input: [[1,2,3],[4,5,6],[7,8,9]]
Output: 12
Explanation: 
The possible falling paths are:
[1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
[2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
[3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
The falling path with the smallest sum is [1,4,7], so the answer is 12.

 

Note:

1 <= A.length == A[0].length <= 100
-100 <= A[i][j] <= 100


******************************************************
key:
	- backtrack, traverse every path, DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- time complexity: O(m*n)
	- space complexity: O(m*n)



Method:

	-	The minimum path to get to element A[i][j] is the minimum of A[i - 1][j - 1], A[i - 1][j] and 
	    A[i - 1][j + 1].
	-  	Starting from row 1, we add the minumum path to each element. The smallest number in the last 
	    row is the miminum path sum.
	ex:
		input： 	[1,2,3]
				[6,5,4]
				[7,8,9]

		M：		[1,  2,  3]
				[7,  6,  6]
				[13, 14, 15]

		

class Solution {
    public int minFallingPathSum(int[][] A) {

        // corner case
        if(A == null || A.length == 0 || A[0].length == 0) return 0;
        
        int m = A.length;
        int n = A[0].length;

        // M[i][j] represents the min sum from top to A[i][j]
        // M[0][j] stays the same
        // M[i][j] = min(M[i - 1][j - 1], M[i - 1][j], M[i - 1][j + 1]) + A[i][j]
        int[][] M = new int[m][n]; 

        
        // copy the 1st row to M[0]
        for(int j = 0; j < n; j++){
            M[0][j] = A[0][j];    
        }
                    System.out.println(Arrays.toString(M[0]));

        for(int i = 1; i < m; i++){
            for(int j = 0; j < n; j++){
                if(j == 0){
                	// can't have M[i - 1][j - 1]
                    M[i][j] = Math.min(M[i - 1][j], M[i - 1][j + 1]);
                
                }else if(j == n - 1){
                	// can't have M[i - 1][j + 1]
                    M[i][j] = Math.min(M[i - 1][j - 1], M[i - 1][j]);
                
                }else{
                    M[i][j] = Math.min(M[i - 1][j - 1], M[i - 1][j]);
                    M[i][j] = Math.min(M[i][j], M[i - 1][j + 1]);
                }

                M[i][j] += A[i][j];
            }
            System.out.println(Arrays.toString(M[i]));
        }
        
        int min = Integer.MAX_VALUE;

        // loop through the last row
        for(int num : M[m - 1]){
            min = Math.min(min, num);
        }
        
        return min; 
    }
}
=======================================================================================================
method 2: Recursion

Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    int[][] memo;
    public int minFallingPathSum(int[][] A) {
        int minPath = Integer.MAX_VALUE;
        memo = new int[A.length][A.length];
        for (int i = 0; i < A.length; i++){
            minPath = Math.min(minPath, findMinPath(A, 0, i));
        }
        return minPath;    
    }
    
    private int findMinPath(int[][] A, int row, int column){
        if (row == A.length - 1){
            return A[row][column];
        } 
        if (memo[row][column] != 0) 
        	return memo[row][column];

        int minPath = Integer.MAX_VALUE; 
        int value = A[row][column];
        if (column-1 >= 0)
            minPath = Math.min(minPath, findMinPath(A, row + 1, column - 1) + value);
        if (column+1 < A.length)
            minPath = Math.min(minPath, findMinPath(A, row + 1, column + 1) + value);

        minPath = Math.min(minPath, findMinPath(A, row + 1, column) + value);
        memo[row][column] = minPath;
        
        return minPath;
            
    }
}

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



