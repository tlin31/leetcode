1102. Path With Maximum Minimum Value - Medium

Given a matrix of integers A with R rows and C columns, find the maximum score of a path 
starting at [0,0] and ending at [R-1,C-1].

The score of a path is the minimum value in that path.  For example, the value of the path 
8 →  4 →  5 →  9 is 4.

A path moves some number of times from one visited cell to any neighbouring unvisited cell in 
one of the 4 cardinal directions (north, east, west, south).

 

Example 1:

5	4	5
1	2	6
7	4	6

Input: [[5,4,5],[1,2,6],[7,4,6]]
Output: 4
Explanation: 
The path with the maximum score is highlighted in yellow. 

Example 2:

2	2	1	2	2	2	
1	2	2	2	1	2

Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
Output: 2

Example 3:

Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
Output: 3
 

Note:

1 <= R, C <= 100
0 <= A[i][j] <= 10^9


******************************************************
key:
	- kind of mini-max, BFS with priority Queue
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- BFS Dijkstra algorithm: use a priority queue to choose the next step with the maximum value. Keep track of the mininum value along the path.


	- Queue stores array list of: {value of this position, new_i, new_j}
	  PQ is decreasingly ordered

stats:

	- 
	- 

 public int maximumMinimumPath(int[][] A) {
        final int[][] DIRS = {{0,1},{1,0},{0,-1},{-1,0}};
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        pq.add(new int[] {A[0][0], 0, 0});
        int maxscore = A[0][0];

        // mark as visited
        A[0][0] = -1; 
        int n = A.length, 
         	m = A[0].length;
        while(!pq.isEmpty()) {
            int[] top = pq.poll();
            int i = top[1], 
            	j = top[2];

            maxscore = Math.min(maxscore, top[0]);

            // reach the end
            if(i == n - 1 && j == m - 1)
                break;

            for(int[] d : DIRS) {
                int newi = d[0] + i, newj = d[1] + j;

                // since we put visited to -1, use A[newi][newj]>=0 to go through 
                // only unvisited ones
                if(newi >= 0 && newi < n && newj >= 0 && newj < m && A[newi][newj]>=0){
                    pq.add(new int[] {A[newi][newj], newi, newj});
                    A[newi][newj] = -1;
                }
            }

        }
        return maxscore;
    }

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



