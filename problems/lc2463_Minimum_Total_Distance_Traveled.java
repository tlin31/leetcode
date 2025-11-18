2463. Minimum Total Distance Traveled - Hard


There are some robots and factories on the X-axis. You are given an integer array robot 
where robot[i] is the position of the ith robot. You are also given a 2D integer array 
factory where factory[j] = [positionj, limitj] indicates that positionj is the position 
of the jth factory and that the jth factory can repair at most limitj robots.

The positions of each robot are unique. The positions of each factory are also unique. 
Note that a robot can be in the same position as a factory initially.

All the robots are initially broken; they keep moving in one direction. The direction 
could be the negative or the positive direction of the X-axis. When a robot reaches a 
factory that did not reach its limit, the factory repairs the robot, and it stops moving.

At any moment, you can set the initial direction of moving for some robot. Your target 
is to minimize the total distance traveled by all the robots.

Return the minimum total distance traveled by all the robots. The test cases are generated 
such that all the robots can be repaired.

Note that

All robots move at the same speed.
If two robots move in the same direction, they will never collide.
If two robots move in opposite directions and they meet at some point, they do not collide. They cross each other.
If a robot passes by a factory that reached its limits, it crosses it as if it does not exist.
If the robot moved from a position x to a position y, the distance it moved is |y - x|.

Input: robot = [0,4,6], factory = [[2,2],[6,2]]
Output: 4
Explanation: As shown in the figure:
- The first robot at position 0 moves in the positive direction. It will be repaired at the first factory.
- The second robot at position 4 moves in the negative direction. It will be repaired at the first factory.
- The third robot at position 6 will be repaired at the second factory. It does not need to move.
The limit of the first factory is 2, and it fixed 2 robots.
The limit of the second factory is 2, and it fixed 1 robot.
The total distance is |2 - 0| + |2 - 4| + |6 - 6| = 4. It can be shown that we cannot achieve a better total distance than 4.
Example 2:


Input: robot = [1,-1], factory = [[-2,1],[2,1]]
Output: 2
Explanation: As shown in the figure:
- The first robot at position 1 moves in the positive direction. It will be repaired at the second factory.
- The second robot at position -1 moves in the negative direction. It will be repaired at the first factory.
The limit of the first factory is 1, and it fixed 1 robot.
The limit of the second factory is 1, and it fixed 1 robot.
The total distance is |2 - 1| + |(-2) - (-1)| = 2. It can be shown that we cannot achieve a better total distance than 2.

有一些 机器人（robots[i] 表示位置），需要被分配到工厂（factories[j] 表示工厂位置 + 容量 capacity）。

每个机器人必须去某个工厂，代价 = 距离（绝对值）。

每个工厂最多可接收 capacity 个机器人。

要求：最小化所有机器人总的移动距离。

******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:



📌 思路分析
1. 排序

将机器人位置数组 robots 升序排序。

将工厂数组 factories 按位置升序排序。

这样可以保证“左边的机器人倾向于去左边的工厂”，有助于 DP。

2. 动态规划定义

	n = robots.length

	m = factories.length

	dp[i][j] = 前 i 个机器人被分配到前 j 个工厂的最小总距离。


3. 状态转移

	对于第 j 个工厂，它有 c = factories[j-1].capacity 容量，可以分配给 0 ~ c 个机器人。

	所以：

	dp[i][j] = min over k ( dp[i-k][j-1] + cost(k robots → factory j) )


	其中 k = 分给第 j 个工厂的机器人数量（0 ≤ k ≤ c 且 k ≤ i）。

	cost(k robots → factory j) = 把最近的 k 个机器人分配到该工厂的总距离。

	因为 robots 和 factories 排序后，可以直接计算：

	cost = sum( abs(robots[i-k+p] - factories[j-1].pos) )   for p=1..k


4. 边界条件

	dp[0][j] = 0（0 个机器人，代价为 0）。

	dp[i][0] = ∞（没有工厂但有机器人，不可能）。

5. 答案

	最终结果：dp[n][m]。


Stats:

	时间复杂度：O(n * m * C)，其中 C = 最大工厂容量。

	空间复杂度：O(n * m)，可优化为 O(n)。
	

import java.util.*;

class Solution {
    public long minimumTotalDistance(List<Integer> robotList, int[][] factoryArr) {
        int n = robotList.size();
        int m = factoryArr.length;

        // 排序
        Collections.sort(robotList);
        Arrays.sort(factoryArr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] robots = robotList.stream().mapToInt(i -> i).toArray();
        int[] factoryPos = new int[m];
        int[] factoryCap = new int[m];
        for (int i = 0; i < m; i++) {
            factoryPos[i] = factoryArr[i][0];
            factoryCap[i] = factoryArr[i][1];
        }

        long[][] dp = new long[n+1][m+1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE / 2);
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = 0; // 0个机器人时，代价为0
        }

        for (int j = 1; j <= m; j++) {
            for (int i = 0; i <= n; i++) {
                long cost = 0;
                // 分配给第 j 个工厂的机器人数量 k
                for (int k = 0; k <= factoryCap[j-1] && k <= i; k++) {
                    if (k > 0) {
                    	// position of robot [i-k] & position of factory [j-1]
                        cost += Math.abs(robots[i-k] - factoryPos[j-1]);
                    }
                    dp[i][j] = Math.min(dp[i][j], dp[i-k][j-1] + cost);
                }
            }
        }

        return dp[n][m];
    }
}



