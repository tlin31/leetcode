694. Number of Distinct Islands - Medium

Given a non-empty 2D array grid of 0s and 1s, an island is a group of 1s (representing land) 
connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid 
are surrounded by water.

Count the number of distinct islands. An island is considered to be the same as another if and 
only if one island can be translated (and not rotated or reflected) to equal the other.

Example 1:
11000
11000
00011
00011
Given the above grid map, return 1.

Example 2:
11011
10000
00001
11011
Given the above grid map, return 3.

Notice that:
11
1
and
 1
11
are considered different island shapes, because we do not consider reflection / rotation.
Note: The length of each dimension in the given grid does not exceed 50.


******************************************************
key:
	- dfs & backtrack
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- dfs/backtrack
	- The key to the solution is to find a way to represent a distinct shape. To describe the shape, 
	  is to describe its moving directions assuming we start at the first 1 we meet o - start, and 
	  move 0 - right, 1 - down, 2 - left, 3 - up.
	- We need to count backtracking as a moving direction by shape.append("_").Take the gird below 
	  as an example,

                {1, 1, 0},
                {1, 0, 0},
                {0, 0, 0},
                {1, 1, 0},
                {0, 1, 0}
	   With shape.append("_"); one with shape o0_1__, another with shape o01___, and they will be 
	   regarded as different shapes. Or else, they will be regarded as the same shape o01.
	- note: to change a 1 to an 0 once we see it!

stats:

	- Time Complexity: O(R*C), where R is the number of rows in the given grid, and C is the 
	     			   number of columns. We visit every square once.

	- Space complexity: O(R*C), the space used by seen to keep track of visited squares, and shapes.

Solution:
	private static int rows, cols;
    private static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int numDistinctIslands(int[][] grid) {
        cols = grid[0].length;
        rows = grid.length;   
        Set<String> uniqueShapes = new HashSet<>(); // Unique shpes.        
        StringBuilder shape;
        
        // a complete dfs that go through every cell!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {               
                if (grid[i][j] == 1) {
                    grid[i][j] = 0;
                    shape = new StringBuilder("o");
                    dfsTraversal(i, j, grid, shape);      
                    uniqueShapes.add(shape.toString());    
                }
            }
        }
        
        return uniqueShapes.size();
    }
    
    
    private static void dfsTraversal(int x, int y, int[][] matrix, StringBuilder shape) {        
        
        for (int i = 0; i < directions.length; i++) {
            int nx = x + directions[i][0];
            int ny = y + directions[i][1];   
            if (nx >= 0 && ny >= 0 && nx < rows && ny < cols) {
                if (matrix[nx][ny] == 1) {
                    matrix[nx][ny] = 0;
                    shape.append(i);
                    dfsTraversal(nx, ny, matrix, shape);
                }
            }
        }
        shape.append("_");
        
    }


=======================================================================================================
method 2:

method:

	- Hash By Path Signature [Accepted]
	- When we start a depth-first search on the top-left square of some island, the path taken 
	  by our dfs will be the same if and only if the shape is the same. We can exploit this by 
	  recording the path we take as our shape - keeping in mind to record both when we enter 
	  and when we exit the function. The rest of the code remains as in Approach #1.


	- 

stats:

	- 
	- 
class Solution {
    int[][] grid;
    boolean[][] seen;
    ArrayList<Integer> shape;

    public void explore(int r, int c, int di) {
        if (0 <= r && r < grid.length && 0 <= c && c < grid[0].length &&
                grid[r][c] == 1 && !seen[r][c]) {
            seen[r][c] = true;
            shape.add(di);
            explore(r+1, c, 1);
            explore(r-1, c, 2);
            explore(r, c+1, 3);
            explore(r, c-1, 4);
            shape.add(0);
        }
    }
    public int numDistinctIslands(int[][] grid) {
        this.grid = grid;
        seen = new boolean[grid.length][grid[0].length];
        Set shapes = new HashSet<ArrayList<Integer>>();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                shape = new ArrayList<Integer>();
                explore(r, c, 0);
                if (!shape.isEmpty()) {
                    shapes.add(shape);
                }
            }
        }

        return shapes.size();
    }
}

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



