463. Island Perimeter - Easy

You are given a map in form of a 2-d integer grid where 1 represents land and 0 represents water.

Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded 
by water, and there is exactly one island (i.e., one or more connected land cells).

The island doesn't have "lakes" (water inside that isn't connected to the water around the island). 
One cell is a square with side length 1. The grid is rectangular, width and height dont exceed 100. 

Q: Determine the perimeter of the island.

 

Example:

Input:
[[0,1,0,0],
 [1,1,1,0],
 [0,1,0,0],
 [1,1,0,0]]

Output: 16

Explanation: The perimeter is the 16 yellow stripes in the image below:

******************************************************
key:
	- bfs, or math
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- loop over the matrix and count the number of islands;
	  if the current dot is an island, count if it has any right neighbour or down neighbour;
	  the result is islands * 4 - neighbours * 2
	- since every adjacent islands made two sides disappeared(as picture below).
			+--+     +--+                   +--+--+
			|  |  +  |  |          ->       |     |
			+--+     +--+                   +--+--+

stats:
	- 
	- 


public class Solution {
    public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbours = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    islands++; 

                    // count down neighbours
                    if (i < grid.length - 1 && grid[i + 1][j] == 1) 
                    	neighbours++; 

                    // count right neighbours
                    if (j < grid[i].length - 1 && grid[i][j + 1] == 1) 
                    	neighbours++; 
                }
            }
        }

        return islands * 4 - neighbours * 2;
    }
}


=======================================================================================================
method 2:



=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



