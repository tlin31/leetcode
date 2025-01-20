1905. Count Sub Islands - Medium

You are given two m x n binary matrices grid1 and grid2 containing only 0s 
(representing water) and 1s (representing land). An island is a group of 1s connected 
4-directionally (horizontal or vertical). Any cells outside of the grid are considered water cells.

An island in grid2 is considered a sub-island if there is an island in grid1 that contains 
all the cells that make up this island in grid2.

Return the number of islands in grid2 that are considered sub-islands.

image：https://leetcode.com/problems/count-sub-islands/description/

Example 1:

Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], 
grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]

Output: 3
Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
The 1s colored red in grid2 are those considered to be part of a sub-island. There are three 
sub-islands.


Example 2:


Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], 
grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
Output: 2 
Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
The 1s colored red in grid2 are those considered to be part of a sub-island. There are two 
sub-islands.
 

Constraints:

m == grid1.length == grid2.length
n == grid1[i].length == grid2[i].length
1 <= m, n <= 500
grid1[i][j] and grid2[i][j] are either 0 or 1.

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

	-	



Stats:

	- 
	- 
return 0 if and only if we find it's not a sub island.


Complexity
Time O(mn)
Space O(mn)




	public int countSubIslands(int[][] B, int[][] A) {
        int m = A.length, n = A[0].length, res = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (A[i][j] == 1)
                    res += dfs(B, A, i, j);
        return res;
    }


    private int dfs(int[][] B, int[][] A, int i, int j) {
        int m = A.length, n = A[0].length, res = 1;

        if (i < 0 || i == m || j < 0 || j == n || A[i][j] == 0) return 1;

        A[i][j] = 0;
        res &= dfs(B, A, i - 1, j);
        res &= dfs(B, A, i + 1, j);
        res &= dfs(B, A, i, j - 1);
        res &= dfs(B, A, i, j + 1);

        return res & B[i][j];
    }

===================================
class Solution {
    private int[] islandRoot;
    private byte[] islandStatus; // 0: unvisited, 1: valid sub-island, 2: invalid sub-island
    private int numRows, numCols;

    public int countSubIslands(int[][] grid1, int[][] grid2) {
        numRows = grid2.length;
        numCols = grid2[0].length;
        int totalCells = numRows * numCols;
        islandRoot = new int[totalCells];
        islandStatus = new byte[totalCells];

        // Initialize islandRoot array and perform union for grid2
        // initialize as union find's parent
        for (int i = 0; i < totalCells; i++) {
            islandRoot[i] = i;
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid2[row][col] == 1) {
                    int currentIndex = row * numCols + col;

                    // for loop从双0开始，所以后续dfs只需要横纵坐标各加1
                    if (col + 1 < numCols && grid2[row][col + 1] == 1) {
                        unionIslands(currentIndex, currentIndex + 1);
                    }
                    if (row + 1 < numRows && grid2[row + 1][col] == 1) {
                        unionIslands(currentIndex, currentIndex + numCols);
                    }
                }
            }
        }

        // Mark invalid sub-islands
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid2[row][col] == 1 && grid1[row][col] == 0) {
                    int rootIndex = findIslandRoot(row * numCols + col);
                    islandStatus[rootIndex] = 2; // Mark as invalid sub-island
                }
            }
        }

        // Count valid sub-islands
        int res = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid2[row][col] == 1) {
                    int rootIndex = findIslandRoot(row * numCols + col);
                    if (islandStatus[rootIndex] == 0) {
                        res++;
                        islandStatus[rootIndex] = 1; // Mark as counted
                    }
                }
            }
        }

        return res;
    }

    private int findIslandRoot(int x) {
        if (islandRoot[x] != x) {
            islandRoot[x] = findIslandRoot(islandRoot[x]); // Path compression
        }
        return islandRoot[x];
    }

    private void unionIslands(int x, int y) {
        int rootX = findIslandRoot(x);
        int rootY = findIslandRoot(y);
        if (rootX != rootY) {
            islandRoot[rootY] = rootX;
        }
    }
}







