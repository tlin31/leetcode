994. Rotting Oranges - Easy

In a given grid, each cell can have one of three values:

the value 0 representing an empty cell;
the value 1 representing a fresh orange;
the value 2 representing a rotten orange.
Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this 
is impossible, return -1 instead.

 

Example 1:

Input: [[2,1,1],[1,1,0],[0,1,1]]
Output: 4


Example 2:
Input: [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because 
rotting only happens 4-directionally.


Example 3:
Input: [[0,2]]
Output: 0
Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 

Note:

1 <= grid.length <= 10
1 <= grid[0].length <= 10
grid[i][j] is only 0, 1, or 2.



******************************************************
key:
	- bfs, or keep updating 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- time stamp， use min to record the minutes
	- every minute we update the rotten orage to be min+3 and in the next iteration increase 
	  the min (next min) check for next rotten min+2.

	- when see a rotten, change it to 0 --> we already processed this
	  					 change the cell around it to min +2, because = next rotten

	- when there are still rotten ones, min ++
	- another case, when there is no rotten, and all are fresh, then return -1

stats:

	- Runtime: 1 ms, faster than 100.00% of Java online submissions for Rotting Oranges.
	- Memory Usage: 39.1 MB, less than 81.25%

class Solution {
    int[][] dirs = new int[][] {{-1,0}, {0,-1}, {1,0}, {0,1}};
    
    public int orangesRotting(int[][] grid) {
        int min = 0;
        int r = grid.length, 
            c = grid[0].length;
        
        // go as many rounds as needed
        while(true) {
            boolean foundRot = false;
            boolean foundFresh = false;
            for(int i =0; i<r;i++){
                for(int j=0; j<c; j++){
                	// found rot
                    if(grid[i][j] == min+2) {
                        foundRot = true;

                        //change its status
                        grid[i][j] = 0;
                        for(int[] a: dirs){
                            int newX = i+a[0];
                            int newY = j+a[1];
                            // check within boundry & check next move is a fresh orange
                            if(newX>=0 && newX<r && newY>=0 && newY<c
                               && grid[newX][newY] == 1) {

                                //mark it as 3
                                grid[newX][newY] = min+3;
                            }
                        }
                    }

                    //if is fresh
                    if(grid[i][j]==1){
                        foundFresh = true;
                    }
                }
            }

            // after going through the entire grid
            // check for globally at last, if no change in find rot, all oranges are fresh
            if (!foundRot){
                if(foundFresh){
                    return -1;
                }
                // break only if no more rot/can't infect 
                break;
            }
            min++;
        }
        return min==0? min : min-1;
    }
}



=======================================================================================================
method 2:

method:

	- bfs
	- First, we traverse the whole grid to find out the positions of rotten oranges and compute the 
	  total num of orange as well, add the positions of rotten oranges into a queue;

	- Secondly, dequeue to get the position of a rotten orange. Due to the infection, the fresh 
	  oranges near the rotten orange become rotten. So we check the cell in the left/right/top/down 
	  of the rotten orange, if it is a fresh orange, enqueue it. 

	  Continue to dequeue until all rotten oranges of last round are removed from the queue

stats:

	- Runtime: 2 ms, faster than 86.83% of Java online submissions for Rotting Oranges.
	- Memory Usage: 42.5 MB, less than 6.25%

	class Solution {
	    public int orangesRotting(int[][] grid) {
	        if(grid == null || grid.length == 0) return 0;
	        int rows = grid.length;
	        int cols = grid[0].length;
	        Queue<int[]> queue = new LinkedList<>();

	        int count_fresh = 0;

	        //Put the position of all rotten oranges in queue & count the number of fresh oranges
	        for(int i = 0 ; i < rows ; i++) {
	            for(int j = 0 ; j < cols ; j++) {
	                if(grid[i][j] == 2) {
	                    queue.offer(new int[]{i , j});
	                }
	                else if(grid[i][j] == 1) {
	                    count_fresh++;
	                }
	            }
	        }

	        // early exit!
	        //if count of fresh oranges is zero --> return 0 
	        if(count_fresh == 0) return 0;

	        // count of minutes/steps
	        int count = 0;
	        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};


	        //bfs starting from initially rotten oranges
	        while(!queue.isEmpty()) {
	            ++count;
	            int size = queue.size();
	            for(int i = 0 ; i < size ; i++) {
	                int[] point = queue.poll();

	                for(int dir[] : dirs) {
	                    int x = point[0] + dir[0];
	                    int y = point[1] + dir[1];

	                    //if out of bound || already rotten || (x , y) is empty, ski[]
	                    if(x < 0 || y < 0 || x >= rows || y >= cols || grid[x][y] == 0 || 
	                    	grid[x][y] == 2) 
	                    	continue;

	                    
	                    //mark the orange at (x , y) as rotten
	                    grid[x][y] = 2;
	                    //put the new rotten orange at (x , y) in queue
	                    queue.offer(new int[]{x , y});
	                    
	                    //decrease the count of fresh oranges by 1
	                    count_fresh--;
	                }
	            }
	        }
	        return count_fresh == 0 ? count-1 : -1;
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



