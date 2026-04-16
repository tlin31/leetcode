296. Best Meeting Point- Hard

Given an m x n binary grid grid where each 1 marks the home of one friend, return the minimal total travel distance.

The total travel distance is the sum of the distances between the houses of the friends and the meeting point.

The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

 

Example 1:


Input: grid = [[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]
Output: 6
Explanation: Given three friends living at (0,0), (0,4), and (2,2).
The point (0,2) is an ideal meeting point, as the total travel distance of 2 + 2 + 2 = 6 is minimal.
So return 6.
Example 2:

Input: grid = [[1,1]]
Output: 1
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 200
grid[i][j] is either 0 or 1.
There will be at least two friends in the grid.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


class Solution {
    public int minTotalDistance(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1){
                    rows.add(i);
                }
            }
        }

        for(int j = 0; j < n; j++){
            for(int i = 0; i < m; i++){
                if(grid[i][j] == 1){
                    cols.add(j);
                }
            }
        }

        int midX = rows.get(rows.size() / 2);
        int midY = cols.get(cols.size() / 2);

        int res = 0;
        
        // Optimization: Iterate over the lists, not the whole grid
        for (int x : rows) res += Math.abs(midX - x);
        for (int y : cols) res += Math.abs(midY - y);

        /** 
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1){
                    res += Math.abs(midX - i) + Math.abs(midY - j);
                }
            }
        }
        */

        return res;
    }
}


===================================================================================================
Method 1:

Method: use 2 pointers 

0(M x N)
Because the dimensions are independent, minimizing the total distance in 2D is equivalent to solving two separate 1D problems: finding the point that minimizes the sum of absolute distances to a set of coordinates. 

The Median Property:  optimal 2D meeting point is simply (median_row, median_col)

1. Collect Rows: 
    Iterate through the grid row-by-row. Any 1 you find will contribute its row index to a list in non-decreasing order.

2. Collect Columns: 
    Iterate through the grid column-by-column. Any 1 you find will contribute its column index to a list in non-decreasing order.

3. Calculate Distance: 
    The total minimum distance is the sum of |row_i - median row| + |col_i-meaidn col| for all friends


Two-Pointer Method:
Pros: 
	Faster. You don't need to re-scan the grid. You only iterate through the list of coordinates (O(number of people)).

Logic: 
	The distance from any point P between two points A and B to both A and B is always exactly |A-B|By pairing the outermost points and moving inward, you calculate the total distance without ever explicitly "choosing" the median.



Stats:

	- 
	- 


	public int minTotalDistance(int[][] grid) {
	    List<Integer> rows = collectRows(grid);
	    List<Integer> cols = collectCols(grid);
	    return minDistance1D(rows) + minDistance1D(cols);
	}

	private List<Integer> collectRows(int[][] grid) {
	    List<Integer> rows = new ArrayList<>();
	    for (int r = 0; r < grid.length; r++) {
	        for (int c = 0; c < grid[0].length; c++) {
	            if (grid[r][c] == 1) rows.add(r);
	        }
	    }
	    return rows;
	}

	private List<Integer> collectCols(int[][] grid) {
	    List<Integer> cols = new ArrayList<>();
	    for (int c = 0; c < grid[0].length; c++) { // Column-first to get sorted order
	        for (int r = 0; r < grid.length; r++) {
	            if (grid[r][c] == 1) cols.add(c);
	        }
	    }
	    return cols;
	}

	private int minDistance1D(List<Integer> points) {
	    int distance = 0;
	    int i = 0, j = points.size() - 1;
	    while (i < j) {
	        // Distance from two points to any point between them is constant: points[j] - points[i]
	        distance += points.get(j--) - points.get(i++);
	    }
	    return distance;
	}



python

class Solution:
    def minTotalDistance(self, grid: List[List[int]]) -> int:

        rows = []
        cols = []

        for r in range(len(grid)):
            for c in range(len(grid[0])):
                if grid[r][c] == 1:
                    rows.append(r)

        for c in range(len(grid[0])):
            for r in range(len(grid)):
                if grid[r][c] == 1:
                    cols.append(c)

        row_med = rows[len(rows)//2]
        col_med = cols[len(cols)//2]

        dist = 0

        for r in rows:
            dist += abs(r - row_med)
        for c in cols:
            dist += abs(c - col_med)

        return dist
        
        
