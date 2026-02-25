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

1. 核心思路：逆向 DP
  状态定义：dp[i][j] 表示从坐标 (i, j) 到达终点 (m-1, n-1) 所需要的最小初始生命值。
  目标：求 dp[0][0]。
  生命值规则：在任何时刻，骑士的生命值都不能低于 1。

2. 状态转移方程
  为了进入格子 (i, j)，骑士需要的生命值取决于他下一步要去哪里（右边或下边）。
  骑士可以从 (i, j) 走向 (i+1, j) 或 (i, j+1)。
  他在 (i, j) 时所需的生命值加上该格子的奖励/惩罚 dungeon[i][j]，必须至少等于下一步所需的最小生命值。

  公式：dp[i][j] = min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]

  修正：如果计算出的 dp[i][j] 小于等于 0，说明该格子的奖励非常大，但骑士进入该格前至少仍需 1 点生命值。
       因此 dp[i][j] = max(1, dp[i][j])。


class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        
        // dp[i][j] 表示从 (i, j) 出发到达终点所需的最小生命值
        int[][] dp = new int[m][n];
        
        // 1. 初始化终点 (右下角)
        // 进入终点后至少剩 1 点血，扣除终点本身的得失
        dp[m - 1][n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);
        
        // 2. 初始化最后一行 (只能向右走)
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - dungeon[m - 1][j]);
        }
        
        // 3. 初始化最后一列 (只能向下走)
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        }
        
        // 4. 填充剩余网格 (逆向推导)
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                // 从右边和下边选一个需求较小的
                int minNextPlan = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(1, minNextPlan - dungeon[i][j]);
            }
        }
        
        return dp[0][0];
    }
}


4. 为什么不能正向 DP？
  正向 DP 的痛点：如果你从 (0, 0) 开始，你需要记录两个变量：当前路径积攒的“最小血量需求”和“当前剩余血量”。
               一个路径可能剩余血量很多，但中途某一瞬间对血量需求极高。这种两个变量互相制约的情况，
               无法通过简单的正向 max/min 做出局部最优决策。

  逆向 DP 的优势：从终点反推，我们只关心“为了活到终点，我现在最少需要多少血”。逻辑链路变得单一且清晰。


5. 2026 年面试点评
复杂度分析：时间复杂度 O(m * n)，空间复杂度 O(m * n)（可进一步优化为 O(n) 滚动数组）。



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



