Geeks- Unique paths covering every non-obstacle block exactly once in a grid


Given a grid grid[][] with 4 types of blocks:

1 represents the starting block. There is exactly one starting block.
2 represents the ending block. There is exactly one ending block.
0 represents empty block we can walk over.
-1 represents obstacles that we cannot walk over.


Question:
	The task is to count the number of paths from the starting block to the ending block such that 
	every non-obstacle block is covered exactly once.

Examples:

Input: grid[][] = {
{1, 0, 0, 0},
{0, 0, 0, 0},
{0, 0, 2, -1} }
Output: 2
Following are the only paths covering all the non-obstacle blocks:


Input: grid[][] = {
{1, 0, 0, 0},
{0, 0, 0, 0},
{0, 0, 0, 2} }
Output: 4

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

	- DFS + backtracking. 
	- a particular path covered all the non-obstacle blocks by counting all the blocks encountered in the
	  way & comparing it with the total number of blocks 
	- walk-condition --> not out of bound, the next block is not visited, it is not blocked

stats:

	- 
	- 


// C++ implementation of the approach 
#include <bits/stdc++.h> 
using namespace std; 

// Function for dfs. 
// i, j ==> Current cell indexes 
// vis ==> To mark visited cells 
// ans ==> Result 
// z ==> Current count 0s visited 
// z_count ==> Total 0s present 
void dfs(int i, int j, vector<vector<int> >& grid, vector<vector<bool> >& vis, int& ans, 
	int curCount, int totalCount){

	int n = grid.size(), 
		m = grid[0].size(); 

	// Mark the block as visited 
	vis[i][j] = 1; 

	// reached an unvisited block, update curCount
	if (grid[i][j] == 0) 
		curCount++; 

	// If end block reached 
	if (grid[i][j] == 2) { 

		// If path covered all the non-obstacle blocks 
		if (curCount == totalCount) 
			ans++; 

		vis[i][j] = 0; 
		return; 
	} 

	// Up 
	if (i >= 1 && !vis[i - 1][j] && grid[i - 1][j] != -1) 
		dfs(i - 1, j, grid, vis, ans, curCount, totalCount); 

	// Down 
	if (i < n - 1 && !vis[i + 1][j] && grid[i + 1][j] != -1) 
		dfs(i + 1, j, grid, vis, ans, curCount, totalCount); 

	// Left 
	if (j >= 1 && !vis[i][j - 1] && grid[i][j - 1] != -1) 
		dfs(i, j - 1, grid, vis, ans, curCount, totalCount); 

	// Right 
	if (j < m - 1 && !vis[i][j + 1] && grid[i][j + 1] != -1) 
		dfs(i, j + 1, grid, vis, ans, curCount, totalCount); 

	// Unmark the block (unvisited) 
	vis[i][j] = 0; 
} 

// Function to return the count of the unique paths 
int uniquePaths(vector<vector<int> >& grid) 
{ 
	// Total 0s present 
	int numEmptyCell = 0; 
	int n = grid.size(), 
		m = grid[0].size(); 
	int ans = 0; 
	int startX, startY; 

	// store whether this block has been visited 
	vector<vector<bool> > vis(n, vector<bool>(m, 0)); 

	// go through the matrix, count empty blocks, find the start position
	for (int i = 0; i < n; ++i) { 
		for (int j = 0; j < m; ++j) { 

			// Count non-obstacle blocks 
			if (grid[i][j] == 0) 
				numEmptyCell++; 

			else if (grid[i][j] == 1) { 

				// Starting position 
				startX = i, 
				startY = j; 
			} 
		} 
	} 
	dfs(startX, startY, grid, vis, ans, 0, numEmptyCell); 
	return ans; 
} 

// Driver code 
int main() 
{ 
	vector<vector<int> > grid{ { 1, 0, 0, 0 }, 
							{ 0, 0, 0, 0 }, 
							{ 0, 0, 2, -1 } }; 

	cout << uniquePaths(grid); 
	return 0; 
} 







