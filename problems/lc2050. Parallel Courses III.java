2050. Parallel Courses III - Hard

You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given a 2D integer array relations where relations[j] = [prevCoursej, nextCoursej] denotes that course prevCoursej has to be completed before course nextCoursej (prerequisite relationship). Furthermore, you are given a 0-indexed integer array time where time[i] denotes how many months it takes to complete the (i+1)th course.

You must find the minimum number of months needed to complete all the courses following these rules:

You may start taking a course at any time if the prerequisites are met.
Any number of courses can be taken at the same time.
Return the minimum number of months needed to complete all the courses.

Note: The test cases are generated such that it is possible to complete every course (i.e., the graph is a directed acyclic graph).

 

Example 1:


Input: n = 3, relations = [[1,3],[2,3]], time = [3,2,5]
Output: 8
Explanation: The figure above represents the given graph and the time required to complete each course. 
We start course 1 and course 2 simultaneously at month 0.
Course 1 takes 3 months and course 2 takes 2 months to complete respectively.
Thus, the earliest time we can start course 3 is at month 3, and the total time required is 3 + 5 = 8 months.
Example 2:


Input: n = 5, relations = [[1,5],[2,5],[3,5],[3,4],[4,5]], time = [1,2,3,4,5]
Output: 12
Explanation: The figure above represents the given graph and the time required to complete each course.
You can start courses 1, 2, and 3 at month 0.
You can complete them after 1, 2, and 3 months respectively.
Course 4 can be taken only after course 3 is completed, i.e., after 3 months. It is completed after 3 + 4 = 7 months.
Course 5 can be taken only after courses 1, 2, 3, and 4 have been completed, i.e., after max(1,2,3,7) = 7 months.
Thus, the minimum time needed to complete all the courses is 7 + 5 = 12 months.
 

Constraints:

1 <= n <= 5 * 104
0 <= relations.length <= min(n * (n - 1) / 2, 5 * 104)
relations[j].length == 2
1 <= prevCoursej, nextCoursej <= n
prevCoursej != nextCoursej
All the pairs [prevCoursej, nextCoursej] are unique.
time.length == n
1 <= time[i] <= 104
The given graph is a directed acyclic graph.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


n 门课（编号 1～n）

relations[i] = [u, v]：上完 u 才能上 v（有向边）

time[i]：完成第 i 门课所需时间

可以 并行上课（只要前置课完成）


===================================================================================================
Method 1:拓扑排序 + DP

Method:

dp[i]：完成课程 i 的最早时间


建图 + 入度数组

初始化队列：

	入度为 0 的课程

	dp[i] = time[i]

BFS 拓扑遍历：

	对每条边 u → v

	dp[v] = max(dp[v], dp[u] + time[v])

	入度减 1

所有课程处理完

返回 max(dp[i])


Stats:

	时间复杂度：O(n + m)

	n：课程数

	m：关系数

	空间复杂度：O(n + m)


class Solution {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n];
        
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // build graph
        for (int[] r : relations) {
            int u = r[0] - 1;
            int v = r[1] - 1;
            graph.get(u).add(v);
            indegree[v]++;
        }

        // dp[i] = earliest finish time of course i
        int[] dp = new int[n];
        Queue<Integer> q = new ArrayDeque<>();

        // initialize
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                dp[i] = time[i];
                q.offer(i);
            }
        }

        // topo bfs
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph.get(u)) {
                dp[v] = Math.max(dp[v], dp[u] + time[v]);
                if (--indegree[v] == 0) {
                    q.offer(v);
                }
            }
        }

        int res = 0;
        for (int t : dp) res = Math.max(res, t);
        return res;
    }
}




