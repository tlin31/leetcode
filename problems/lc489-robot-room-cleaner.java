489. Robot Room Cleaner - Hard

You are controlling a robot that is located somewhere in a room. The room is modeled as 
an m x n binary grid where 0 represents a wall and 1 represents an empty slot.

The robot starts at an unknown location in the room that is guaranteed to be empty, and 
you do not have access to the grid, but you can move the robot using the given API Robot.

You are tasked to use the robot to clean the entire room (i.e., clean every empty cell 
	in the room). The robot with the four given APIs can move forward, turn left, or turn 
right. Each turn is 90 degrees.

When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, 
and it stays on the current cell.

Design an algorithm to clean the entire room using the following APIs:

interface Robot {
  // returns true if next cell is open and robot moves into the cell.
  // returns false if next cell is obstacle and robot stays on the current cell.
  boolean move();

  // Robot will stay on the same cell after calling turnLeft/turnRight.
  // Each turn will be 90 degrees.
  void turnLeft();
  void turnRight();

  // Clean the current cell.
  void clean();
}
Note that the initial direction of the robot will be facing up. You can assume all four 
edges of the grid are all surrounded by a wall.

 

Custom testing:

The input is only given to initialize the room and the robot's position internally. 
You must solve this problem "blindfolded". In other words, you must control the robot using 
only the four mentioned APIs without knowing the room layout and the initial robot's position.

 

Example 1:


Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]], row = 1, col = 3
Output: Robot cleaned all rooms.
Explanation: All grids in the room are marked by either 0 or 1.
0 means the cell is blocked, while 1 means the cell is accessible.
The robot initially starts at the position of row=1, col=3.
From the top left corner, its position is one row below and three columns right.

Example 2:

Input: room = [[1]], row = 0, col = 0
Output: Robot cleaned all rooms.
 

Constraints:

m == room.length
n == room[i].length
1 <= m <= 100
1 <= n <= 200
room[i][j] is either 0 or 1.
0 <= row < m
0 <= col < n
room[row][col] == 1
All the empty cells can be visited from the starting position.
******************************************************
key:
  - dfs
  - edge case:
	1) empty map
	2)

******************************************************



=======================================================================================================
method 1:

method:

  - if found obstacle, stop and try to turn left or right. 
  - if surrounded by all blocks, go back to the previous cell and from there we go to a different 
	direciton.
  - We may go back on a cell where have visited and cleaned before, a good way to record that is 
    to use a Set to store the coordinates.
  - 4 directions, use an array to store the UP{-1,0}, LEFT{0,-1}, DOWN{1,0}, and RIGHT{0,1}.
  - In the backtracking func:
		if the coordinate we are visiting has already been visited
			skip and return 
		if not
			clean it and record it in the visited set
		move the robot along its original direction, if it cannot, there are still 3 other directions 
		  we can go. We will stick with turning right as our only choice.  

stats:

  - 
  - 

class Solution {
	private Set<String> visited = new HashSet<>();
	private final int[][] dirs= {{-1,0},{0,-1},{1,0},{0,1}};

	public void cleanRoom(Robot robot) {
		dfs(robot,0,0,0);    
	}

	private void dfs(Robot robot, int x, int y, int dir){
		// 4 directions that the robot can go: up, left, down, right. 
		// Each increment will make the robot the turnLeft. 
		
		//if this cell is already visited(= cleaned), we will skip and return
		if(visited.contains(x+","+ y)){
			return;
		}

		//o.w. we will clean this cell and mark this cell in the visited 
		robot.clean();
		visited.add(x+","+y);

		// backtracking: at current cell, the robot can go 4 direction and we will try at its current 
		// direction, if the robot cannot move, we will turn and increment the dir by 1 until we can 
		// move or go back to the last recursion.
		for(int i =0;i<4;i++){
			
			if(robot.move()){
				//if the robot can move(meaning there's no physical obstacle)
				dfs(robot, x+dirs[dir][0], y+dirs[dir][1], dir);

				// go back to starting position when stuck
				robot.turnRight();
				robot.turnRight();
				robot.move();
				robot.turnRight();
				robot.turnRight();
			}
			
			robot.turnRight();
			dir = (dir+1)%4;
		}
	}
}


=======================================================================================================
method 2:

method:

  - 
  - 

stats:

  - 
  - 
class Solution {
	
	private static final int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	
	public void cleanRoom(Robot robot) {
		dfs(0, 0, 0, new HashSet<>(), robot);
	}
	
	private void dfs(int r, int c, int dir, Set<String> visited, Robot robot) {
		String pos = r + "|" + c;
		if(!visited.contains(pos)) {
			visited.add(pos);
			robot.clean();
			for(int i = dir; i < dir + 4; i++) {
				if(robot.move()) {
					dfs(r + dirs[i % 4][0], c + dirs[i % 4][1], i % 4, visited, robot);
					robot.turnRight();
					robot.turnRight();
					robot.move();
					robot.turnRight();
					robot.turnRight();
				}
				robot.turnRight();
			}
		}
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



