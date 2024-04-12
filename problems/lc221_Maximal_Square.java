221. Maximal square - Hard

Given a 2D binary matrix filled with 0s and 1s, find the largest square containing only 1s and 
return its area.

Example:
Input: 

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4

******************************************************
key:
    - DP
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
Method 1:


Stats:

    - 
    - 


Method:

    -   
    -   

public int maximalSquare(char[][] a) {
    if(a.length == 0) return 0;
    int m = a.length, n = a[0].length, result = 0;
    int[][] b = new int[m+1][n+1];		// create new 2d map for dp

    for (int i = 1 ; i <= m; i++) {
        for (int j = 1; j <= n; j++) {

            // if matrix[i][j] = '0', then M[i][j] = 0
            // if '1', then M[i][j] = min(M[i][j - 1], M[i - 1][j - 1], M[i - 1][j]) + 1
            // since we take the min of all its neighbors, if all others = 1, this min = 1, 
            // thus the width of this square expands
            if(a[i-1][j-1] == '1') {
                // add 1 b/c even is 1  0
                //                   1  1
                // it has at least 1 with area = 1
                b[i][j] = Math.min(Math.min(b[i][j-1], b[i-1][j-1]), b[i-1][j]) + 1;
                result = Math.max (b[i][j], result); // update result
            }
        }
   	}
    	return result*result;
}