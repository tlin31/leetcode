174. Dungeon Game - Hard

The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially
positioned in the top-left room and must fight his way through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health
point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering 
these rooms; other rooms are either empty (0s) or contain magic orbs that increase the knights health 
(positive integers).

In order to reach the princess as quickly as possible, the knight decides to move only rightward or
downward in each step.

 

Write a function to determine the knights minimum initial health so that he is able to rescue the
princess.

For example, given the dungeon below, the initial health of the knight must be at least 7 if he 
follows the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.

-2 (K)	-3		3
-5		-10		1
10		30		-5 (P)
 

Note:

The knights health has no upper bound.
Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right
room where the princess is imprisoned.


******************************************************
key:
	- dfs/backtrack --> traverse all path, keep a local maximum
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

https://leetcode.com/problems/dungeon-game/solution/

Method:

	-	Though the down-right corner is the final destination of the knight, we could start from 
	    the destination and deduce backwards the minimal health point that the knight would need 
	    at each step along the way.
	-	in dp[row][col] = minimal health points that the knight would need, starting from the
	    corresponding dungeon cell dungeon[row][col], in order to reach the destination.

	-	start from the down-right corner of the dungeon, and walk following the orders of
	    from-right-to-left and from-down-to-up. 

	-	dp[row][col] = :

			- If possible, RIGHT step from the current cell, the knight need right_health points.
			- If possible, DOWN step from the current cell, the knight need down_health  points.
			- If either of the above two alternatives exists, we then take the minimal value of them 
			  as the value for dp[row][col].
			- If none of the above alternatives exists, i.e. we are at the destination cell, have 2 
			  sub-cases:
				1) If the current cell is of magic orb, then 1 health point would suffice.
				2) If the current cell is of daemon, then the knight should possess one health 
				   point plus the damage points that would be caused by the daemon.
Stats:

	- Time Complexity: O(M⋅N) where M⋅N is the size of the dungeon. We iterate through the entire
					   dungeon once and only once.
	- Space Complexity: O(M⋅N) where M⋅N is the size of the dungeon. In the algorithm, we keep a 
	                   dp matrix that is of the same size as the dungeon.

 class Solution {
    public int calculateMinimumHP(int[][] dungeon) {

        int m = dungeon.length;
        int n = dungeon[0].length;
        
        int[][] health = new int[m][n];
        
        // from bottom to origin
        for (int i=m-1;i>=0;i--) {
            for (int j=n-1;j>=0;j--) {

                if (i == m-1 && j == n-1) {
                    // in destination
                    // 1. health >= 1, 2. health + dungeon >=1
                    health[i][j] = Math.max(1, 1 - dungeon[i][j]);

                } else if (i == m -1) {
                    // can only go to right, can't go down
                    //1. health >= 1, 2. health + dungeon >= health_on_right
                    health[i][j] = Math.max(1, health[i][j+1] - dungeon[i][j]);

                } else if (j == n-1) {
                	// can only go down, can't go right
                    health[i][j] = Math.max(1, health[i+1][j] - dungeon[i][j]);
                } else {
                    // 1.health >=1, 2. get min by use: health + dungeon >= min(right, down)
                    health[i][j] = Math.max(1, Math.min(health[i+1][j], health[i][j+1]) - dungeon[i][j]);
                }
            }
        }
        
        return health[0][0];
    }
}

ex. start from bottom right corner, then the bottom row, then the middle row (from right to left)

-2		3		3	
-5		-10		1
10		30		6

becomes:
-2						3									3
-5						-10/11 =max(1,min(1,5)- -10)		1/5= max(1,6-1)
10/1 =max(1,1-10)		30/1 =max(1,6-30)					-5/6 = max(1,1- -5)

=======================================================================================================
method 2: reduce space 

Method:

	-	Indeed, we could use the CircularQueue to calculate the dp array, as we show in the above
	    graph. At any moment, the size of the CircularQueue would not exceed the predefined capacity,
	    which would be N in our case. As a result, we reduce the overall space complexity of the
	    algorithm to O(n)
	-	


Stats:

	- 
	- 

class MyCircularQueue {
  protected int capacity;
  protected int tailIndex;
  public int[] queue;

  public MyCircularQueue(int capacity) {
    this.queue = new int[capacity];
    this.tailIndex = 0;
    this.capacity = capacity;
  }

  public void enQueue(int value) {
    this.queue[this.tailIndex] = value;
    this.tailIndex = (this.tailIndex + 1) % this.capacity;
  }

  public int get(int index) {
    return this.queue[index % this.capacity];
  }
}


class Solution {
  int inf = Integer.MAX_VALUE;
  MyCircularQueue dp;
  int rows, cols;

  public int getMinHealth(int currCell, int nextRow, int nextCol) {
    if (nextRow < 0 || nextCol < 0)
      return inf;

    int index = cols * nextRow + nextCol;
    int nextCell = this.dp.get(index);
    // hero needs at least 1 point to survive
    return Math.max(1, nextCell - currCell);
  }

  public int calculateMinimumHP(int[][] dungeon) {
    this.rows = dungeon.length;
    this.cols = dungeon[0].length;
    this.dp = new MyCircularQueue(this.cols);

    int currCell, rightHealth, downHealth, nextHealth, minHealth;
    for (int row = 0; row < this.rows; ++row) {
      for (int col = 0; col < this.cols; ++col) {
        currCell = dungeon[rows - row - 1][cols - col - 1];

        rightHealth = getMinHealth(currCell, row, col - 1);
        downHealth = getMinHealth(currCell, row - 1, col);
        nextHealth = Math.min(rightHealth, downHealth);

        if (nextHealth != inf) {
          minHealth = nextHealth;
        } else {
          minHealth = currCell >= 0 ? 1 : 1 - currCell;
        }
        this.dp.enQueue(minHealth);
      }
    }

    // retrieve the last element in the queue
    return this.dp.get(this.cols - 1);
  }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



