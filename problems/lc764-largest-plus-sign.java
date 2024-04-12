764. Largest Plus Sign - Medium



In a 2D grid from (0, 0) to (N-1, N-1), every cell contains a 1, except those cells in the given 
list mines which are 0. 

What is the largest axis-aligned plus sign of 1s contained in the grid? 
Return the order of the plus sign. If there is none, return 0.

An "axis-aligned plus sign of 1s of order k" has some center grid[x][y] = 1 along with 4 arms of 
length k-1 going up, down, left, and right, and made of 1s. This is demonstrated in the diagrams 
below. 

Note that there could be 0s or 1s beyond the arms of the plus sign, only the relevant area of 
the plus sign is checked for 1s.

Examples of Axis-Aligned Plus Signs of Order k:

Order 1:
000
010
000

Order 2:
00000
00100
01110
00100
00000

Order 3:
0000000
0001000
0001000
0111110
0001000
0001000
0000000

Example 1:

Input: N = 5, mines = [[4, 2]]
Output: 2
Explanation:
1 1 111
1 1 111
1 1 111
1(1)111
1 1 011
In the above grid, the largest plus sign can only be order 2.  One of them is marked in bold.


Example 2:

Input: N = 2, mines = []
Output: 1
Explanation:
There is no plus sign of order 2, but there is of order 1.


Example 3:

Input: N = 1, mines = [[0, 0]]
Output: 0
Explanation:
There is no plus sign, so return 0.

N will be an integer in the range [1, 500].
mines will have length at most 5000.
mines[i] will be length 2 and consist of integers in the range [0, N-1].
(Additionally, programs submitted in C, C++, or C# will be judged with a slightly smaller time limit.)


******************************************************
key:
	- 
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
	-  gridp[i][j] = max axis-aligned plus sign centered at position(i,j)
	-  For each position (i, j) of the grid matrix, we try to extend in each of the 4
	   directions (left, right, up, down) as long as possible, then take the minimum length of 1s 
	   out of the four directions as the order of the largest axis-aligned plus sign centered at 
	   position (i, j).

	-	Optimizations: 
			- For each position (i, j), we are only concerned with the minimum length of 1s out 
			  of the four directions. This implies we may combine the four matrices into one by only 
			  keeping track of the minimum length.

			- For each position (i, j), the order of the largest axis-aligned plus sign centered 
			  at it will be 0 if and only if grid[i][j] == 0. This implies we may further combine 
			  the grid matrix with the one obtained above.

	-  Algo:
		1. Create an N-by-N matrix grid, with all elements initialized with value N.
		2. Reset those elements to 0 whose positions are in the mines list.
		3. For each position (i, j), find the maximum length of 1s in each of the four directions 
		   and set grid[i][j] to the minimum of these four lengths. Note that there is a simple
		   recurrence relation relating the maximum length of 1s at current position with previous
		   position for each of the four directions (labeled as l, r, u, d).
		4. Loop through the grid matrix and choose the maximum element which will be the largest
		   axis-aligned plus sign of 1s contained in the grid.


public int orderOfLargestPlusSign(int N, int[][] mines) {
    
    int[][] grid = new int[N][N];
        
    for (int i = 0; i < N; i++) {
        Arrays.fill(grid[i], N);
    }
        
    // set mines position to 0
    for (int[] m : mines) {
        grid[m[0]][m[1]] = 0;
    }
        
	for (int i = 0; i < N; i++) {

		// LEFT: j is a column index,  check how far left it can reach.
		// if grid[i][j] is 0, l needs to start over from 0 again, otherwise increment
        for (int j=0; j < N; j++) {

 			int left = (grid[i][j] == 0 ? 0 : left + 1);
        	grid[i][j] = Math.min(grid[i][j], left);
        }

        // RIGHT: k is a column index, iterate from right to left, check how far right it can reach.
        for (int k = N-1, r=0; k >= 0; k--) {
            grid[i][k] = Math.min(grid[i][k], r = (grid[i][k] == 0 ? 0 : r + 1));
        }

        // UP: j is a row index, iterate from top to bottom
        for (int j = 0, u=0; j < N; j++) {
            grid[j][i] = Math.min(grid[j][i], u = (grid[j][i] == 0 ? 0 : u + 1));
        }

        // DOWN: k is a row index, iterate from bottom to top
        for (int k = N-1, d=0; k >= 0; k--) {

            grid[k][i] = Math.min(grid[k][i], d = (grid[k][i] == 0 ? 0 : d + 1));
        }
                
    }
        
    int res = 0;
        
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            res = Math.max(res, grid[i][j]);
        }
    }
        
    return res;
}


ex. for N = 5, mine = [4,2]

i = 0:
after LEFT:  1 2 3 4 5
after RIGHT: 5 4 3 2 1 ---> since we take min of this and prev answer from LEFT --> 1 2 3 2 1
after UP: 	 1
			 2
			 3
			 4
			 5
after Down:  1
			 2
			 3
			 2
			 1
i = 0
[1, 2, 3, 2, 1]
[2, 5, 5, 5, 5]
[3, 5, 5, 5, 5]
[2, 5, 5, 5, 5]
[1, 5, 0, 5, 5]

i = 1
[1, 1, 3, 2, 1]
[1, 2, 3, 2, 1]
[3, 3, 5, 5, 5]
[2, 2, 5, 5, 5]
[1, 1, 0, 5, 5]

i = 2
[1, 1, 1, 2, 1]
[1, 2, 2, 2, 1]
[1, 2, 2, 2, 1]
[2, 2, 1, 5, 5]
[1, 1, 0, 5, 5]

i = 3
[1, 1, 1, 1, 1]
[1, 2, 2, 2, 1]
[1, 2, 2, 2, 1]
[1, 2, 1, 2, 1]
[1, 1, 0, 1, 5]

i = 4
[1, 1, 1, 1, 1]
[1, 2, 2, 2, 1]
[1, 2, 2, 2, 1]
[1, 2, 1, 2, 1]
[1, 1, 0, 1, 1]


final grid:
[1, 1, 1, 1, 1]
[1, 2, 2, 2, 1]
[1, 2, 2, 2, 1]
[1, 2, 1, 2, 1]
[1, 1, 0, 1, 1]




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
class Solution:
    def orderOfLargestPlusSign(self, N, mines):
        #up, left, down, right
        dp, res, mines = [[[0, 0, 0, 0] for j in range(N)] for i in range(N)], 0, {(i, j) for i, j in mines}
        for i in range(N):
            for j in range(N):
                if (i, j) not in mines:
                    try:
                        dp[i][j][0] = dp[i - 1][j][0] + 1
                    except:
                        dp[i][j][0] = 1
                    try:
                        dp[i][j][1] = dp[i][j - 1][1] + 1
                    except:
                        dp[i][j][1] = 1
        for i in range(N - 1, -1, -1):
            for j in range(N - 1, -1, -1):
                if (i, j) not in mines:
                    try:
                        dp[i][j][2] = dp[i + 1][j][2] + 1
                    except:
                        dp[i][j][2] = 1
                    try:
                        dp[i][j][3] = dp[i][j + 1][3] + 1
                    except:
                        dp[i][j][3] = 1
                    res = max(res, min(dp[i][j]))
        return res

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

