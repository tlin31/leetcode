656. Coin Path - Hard


Given an array A (index starts at 1) consisting of N integers: A1, A2, ..., AN and an integer B. 
The integer B denotes that from any place (suppose the index is i) in the array A, you can jump 
to any one of the place in the array A indexed i+1, i+2, …, i+B if this place can be jumped to. 
Also, if you step on the index i, you have to pay Ai coins. If Ai is -1, it means you can’t jump 
to the place indexed i in the array.

Now, you start from the place indexed 1 in the array A, and your aim is to reach the place indexed 
N using the minimum coins. You need to return the path of indexes (starting from 1 to N) in the 
array you should take to get to the place indexed N using minimum coins.

If there are multiple paths with the same cost, return the lexicographically smallest such path.

If it is not possible to reach the place indexed N then you need to return an empty array.

Example 1:

Input: [1,2,4,-1,2], 2
Output: [1,3,5]
 

Example 2:

Input: [1,2,4,-1,2], 1
Output: []
 

Note:

Path Pa1, Pa2, ..., Pan is lexicographically smaller than Pb1, Pb2, ..., Pbm, if and only if at 
the first i where Pai and Pbi differ, Pai < Pbi; when no such i exists, then n < m.

A1 >= 0. A2, ..., AN (if exist) will in the range of [-1, 100].

Length of A is in the range of [1, 1000].
B is in the range of [1, 100].
 


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************

1️、 DP 状态定义
    dp[i] = 从 i 走到终点的最小花费


    如果 A[i] == -1，dp[i] = INF

    目标是求 dp[0]

2️、 状态转移（从后往前）
    dp[i] = A[i] + min(dp[j]), j ∈ [i+1, i+B]


    ⚠️ 前提：A[i] != -1 && dp[j] != INF

3️、 字典序最小如何保证？

    当 dp 相等时，选择更小的 j

    因为路径是从前往后，先选小 index → 字典序更小


时间 & 空间复杂度
    时间：O(n * B)
    空间：O(n)
    若 n=1000, B=1000，完全可接受

class Solution {
    public List<Integer> cheapestJump(int[] coins, int maxJump) {
        int n = coins.length;
        int INF = Integer.MAX_VALUE / 2;

        int[] dp = new int[n];
        int[] next = new int[n]; // 记录下一跳位置

        Arrays.fill(dp, INF);
        Arrays.fill(next, -1);

        // 终点
        if (coins[n - 1] != -1) {
            dp[n - 1] = coins[n - 1];
        }

        // 从后往前 DP
        for (int i = n - 2; i >= 0; i--) {
            if (coins[i] == -1) continue;

            for (int j = i + 1; j <= Math.min(n - 1, i + maxJump); j++) {
                if (dp[j] == INF) continue;

                int cost = coins[i] + dp[j];
                if (cost < dp[i]) {
                    dp[i] = cost;
                    next[i] = j;
                }
                // cost == dp[i] 不更新 → 保证字典序最小
            }
        }

        // 无法到达
        if (dp[0] == INF) return new ArrayList<>();

        // 还原路径（1-indexed）
        List<Integer> res = new ArrayList<>();
        int cur = 0;
        while (cur != -1) {
            res.add(cur + 1);
            cur = next[cur];
        }

        return res;
    }
}


现实生活类比（你要求的）
🎯 1️⃣ 路径规划 / 成本最优跳跃

每个站点有成本

有最大跳跃距离

选最省钱路线

🎯 2️⃣ 分布式系统任务调度

节点不可用（-1）

跳转有最大 hops

优先选成本最小且顺序最稳定的路径

🎯 3️⃣ 游戏设计

地图跳跃点

有些点损坏

选最低代价通关路径

七、面试 Follow-Up（高频）
❓1. 如何优化到 O(n)？

👉 单调队列 / 最小值滑动窗口

维护 dp[j] 的最小值

类似 LeetCode 239 / 1438

❓2. 如果 B 很大（1e5）？

DP 会 TLE

必须用 deque

❓3. 如果要返回所有最优路径？

dp 存 list

或 DAG + DFS（复杂度高）

❓4. 如果不要求字典序？

任意最小即可

next 更新更自由


=======================================================================================================

class Solution {
    public List<Integer> cheapestJump(int[] coins, int maxJump) {
        int n = coins.length;

        long[] dp = new long[n];
        int[] next = new int[n];

        Arrays.fill(next, -1);
        for (int i = n - 2; i >= 0; i--) {

            long minCost = Integer.MAX_VALUE;
            for (int j = i + 1; j <= i + maxJump && j < n; j++) {

                if (coins[j] >= 0) {
                    long cost = coins[i] + dp[j];
                    if (cost < minCost) {
                        minCost = cost;
                        next[i] = j;
                    }
                }
            }

            dp[i] = minCost;
        }
        
        List<Integer> result = new ArrayList();
        int i = 0;
        while (i < n && next[i] > 0){
            result.add(i + 1);
            i = next[i];
        }

        if (i == n - 1 && coins[i] >= 0)
            result.add(n);
        else 
            return new ArrayList<Integer>();
        
        return result;        
    }
}

=======================================================================================================


Method 1: memo


Stats:

	- Time complexity : O(nB). memomemo array of size nn is filled only once. We also do a traversal over the nextnext array, which will go upto BB steps. Here, nn refers to the number of nodes in the given tree.

	- Space complexity： O(n). The depth of the recursive tree can grow upto nn. nextnext array of size nn is used.



Method:

	-	
	- memo[i] = min cost of jumps to reach the end of the array A from index i

	  Whenever the value for any index is calculated once, it is stored in its appropriate location. 
	  Thus, next time whenever the same function call is made, we can return the result directly from 
	  this memo array, pruning the search space to a great extent.
	
	- when we step on ( i ), we have B choices: 
	            i + 1, d(i) = A[i] + d(i + 1)
	            i + 2, d(i) = A[i] + d(i + 2)
	            i + 3, d(i) = A[i] + d(i + 3)
	            ...
	            i + B, d(i) = A[i] + d(i + B)
	  We choose the minimum one among them. That is,

			d(i) = A[i] + min(d(i + 1), d(i + 2), ..., d(i + B));

	- To avoid duplicate calculations on subproblems, we use memorization technique, i.e., memo[].
	- To trace back and print out the solution pattern in the end, we use next[].

public class Solution {

	// main function
    public List < Integer > cheapestJump(int[] A, int step) {
        int[] next = new int[A.length];
        Arrays.fill(next, -1);
        long[] memo = new long[A.length];

        jump(A, step, 0, next, memo);

        // restore the steps
        List < Integer > res = new ArrayList();
        int i;
        for (i = 0; i < A.length && next[i] > 0; i = next[i])
            res.add(i + 1);

        if (i == A.length - 1 && A[i] >= 0)
            res.add(A.length);
        else
            return new ArrayList < Integer > ();

        return res;
    }

    public long jump(int[] A, int step, int i, int[] next, long[] memo) {
        if (memo[i] > 0)
            return memo[i];

        // reach the end, then add this one last cost
        if (i == A.length - 1 && A[i] >= 0)
            return A[i];

        long min_cost = Integer.MAX_VALUE;
        for (int next_elem = i + 1; next_elem <= i + step && next_elem < A.length; next_elem++) {
            
            // if not == -1, then it's possible to reach
            if (A[next_elem] >= 0) {
                long cost = A[i] + jump(A, step, next_elem, next, memo);
                if (cost < min_cost) {
                    min_cost = cost;
                    next[i] = next_elem;
                }
            }
        }
        memo[i] = min_cost;
        return min_cost;
    }
}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP

Stats:

	- same as method 1
	- 


Method:

	-	
	-  From the solutions discussed above, we can observe that the cost of jumping till the end of 
	   the array A starting from the index i is only dependent on the elements following the index 
	   i and not the ones before it. This inspires us to make use of Dynamic Programming to solve 
	   the current problem.

	- We again make use of a next array to store the next jump locations. We also make use of a dp 
	  with the same size as that of the given A array. 

	  dp[i] = min cost of jumping till the end of the array A, starting from the index i. 

	  We start with the last index as the current index and proceed backwards 

	- With i as the current index, we consider all the next possible positions from i+1, i+2,..., 
	  i+B, and determine the position, j, which leads to a minimum cost of reaching the end of A, 
	  which is given by A[i]+dp[j]. We update next[i] with this corresponding index. We also update 
	  dp[i] with the minimum cost, to be used by the previous indices cost calculations.

	- At the end, we again jump over the indices as per the nextnext array and put these indices in 
	  the resres array to be returned.



public class Solution {
    public List < Integer > cheapestJump(int[] A, int B) {
        int[] next = new int[A.length];
        long[] dp = new long[A.length];
        Arrays.fill(next, -1);
        List < Integer > res = new ArrayList();
        for (int i = A.length - 2; i >= 0; i--) {
            long min_cost = Integer.MAX_VALUE;
            for (int j = i + 1; j <= i + B && j < A.length; j++) {
                if (A[j] >= 0) {
                    long cost = A[i] + dp[j];
                    if (cost < min_cost) {
                        min_cost = cost;
                        next[i] = j;
                    }
                }
            }
            dp[i] = min_cost;
        }

        int i;
        for (i = 0; i < A.length && next[i] > 0; i = next[i])
            res.add(i + 1);
        if (i == A.length - 1 && A[i] >= 0)
            res.add(A.length);
        else
            return new ArrayList < Integer > ();
        return res;
    }
}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

I used DP. dp[i] represent the best path found to get to the place indexed i + 1 and dp[i][0] is the cost of the path.
dp[0] is initialized as [A[0], 1] and the others are initialized as [infinity].

def cheapestJump(self, A, B):
        if not A or A[0] == -1: return []
        dp = [[float('inf')] for _ in A]
        dp[0] = [A[0], 1]
        for j in range(1, len(A)):
            if A[j] == -1: continue
            dp[j] = min([dp[i][0] + A[j]] + dp[i][1:] + [j + 1] for i in range(max(0, j - B), j))
        return dp[-1][1:] if dp[-1][0] < float('inf') else []
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

