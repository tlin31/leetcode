1041. Robot Bounded In Circle - Medium

On an infinite plane, a robot initially stands at (0, 0) and faces north.  The robot can receive one of
three instructions:

"G": go straight 1 unit;
"L": turn 90 degrees to the left;
"R": turn 90 degress to the right.
The robot performs the instructions given in order, and repeats them forever.

Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.

 

Example 1:

Input: "GGLLGG"
Output: true
Explanation: 
The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
Example 2:

Input: "GG"
Output: false
Explanation: 
The robot moves north indefinitely.
Example 3:

Input: "GL"
Output: true
Explanation: 
The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
 

Note:

1 <= instructions.length <= 100
instructions[i] is in {'G', 'L', 'R'}


******************************************************
key:
	- find pattern
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:
	Starting at the origin and face north (0,1),after one sequence of instructions,
		1. if chopper return to the origin, he is obvious in an circle.
		2. if chopper finishes with face not towards north, it will get back to the initial status in
		   another one or three sequences.

Math:
	Let us say the robot starts with facing up. It moves [dx, dy] by executing the instructions once.
	If the robot starts with facing right, it moves [dy, -dx];
	left, it moves [-dy, dx];
	down, it moves [-dx, -dy]

	If the robot faces right (clockwise 90 degree) after executing the instructions once,
	the direction sequence of executing the instructions repeatedly: up -> right -> down -> left -> up
	The resulting move is [dx, dy] + [dy, -dx] + [-dx, -dy] + [-dy, dx] = [0, 0] (back to original)

	All other possible direction sequences:
	up -> left -> down -> right -> up. The resulting move is [dx, dy] + [-dy, dx] + [-dx, -dy] + [dy, -dx] = [0, 0]
	up -> down -> up. The resulting move is [dx, dy] + [-dx, -dy] = [0, 0]
	up -> up. The resulting move is [dx, dy]

Explanation
	(x,y) is the location of chopper.
	d[i] is the direction he is facing.
	i = (i + 1) % 4 will turn right
	i = (i + 3) % 4 will turn left
	Check the final status after instructions.


Stats:

	- 
	- 


    public boolean isRobotBounded(String ins) {
        int x = 0, y = 0, 
        	// i indicates the direction
         	i = 0, 
         	d[][] = {{0, 1}, {1, 0}, {0, -1}, { -1, 0}};

        for (int j = 0; j < ins.length(); ++j)
            if (ins.charAt(j) == 'R')
                i = (i + 1) % 4;
            else if (ins.charAt(j) == 'L')
                i = (i + 3) % 4;
            else {
                x += d[i][0]; 
                y += d[i][1];
            }
        return x == 0 && y == 0 || i > 0;
    }

=======================================================================================================
method 2:

Method:



Stats:

	- 
	- 

class Solution {
    public boolean isRobotBounded(String instructions) {
        // position (x, y), direction (vx, vy). 
        int x = 0, y = 0, vx = 0, vy = 1; 


        for(char c : instructions.toCharArray()){

        	// go 1 step along the direction (vx, vy), new positon = old position + 1*(vx, vy). 
            if(c == 'G'){ 
                x += vx; 
                y += vy;
            }
            // turn left is (vx, vy) ->(-vy, vx)
            else if(c == 'L'){ 
                int tmp = vx;
                vx = -vy;
                vy = tmp;
            }
            // trun right is (vx, vy) -> (vy, -vx)
            else{ 
                int tmp = vx;
                vx = vy;
                vy = -tmp;
            }
        }

        // if we travel some distance, in the meanwhile facing the north, sky is the limit!
        if(x*x + y*y > 0 && vx == 0 && vy == 1) 
        	return false; 
        else 
        	// stay the same position or travel an angle, with many steps you will be back, stuck in limbo. 
        	return true; 
    }
}

=======================================================================================================
method 3:

Method:



Stats:

	- 
	- 




