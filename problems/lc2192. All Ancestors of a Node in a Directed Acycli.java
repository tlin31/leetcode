2192. All Ancestors of a Node in a Directed Acyclic Graph - Medium

You are given a positive integer n representing the number of nodes of a Directed Acyclic Graph (DAG). The nodes are numbered from 0 to n - 1 (inclusive).

You are also given a 2D integer array edges, where edges[i] = [fromi, toi] denotes that there is a unidirectional edge from fromi to toi in the graph.

Return a list answer, where answer[i] is the list of ancestors of the ith node, sorted in ascending order.

A node u is an ancestor of another node v if u can reach v via a set of edges.

 

Example 1:


Input: n = 8, edgeList = [[0,3],[0,4],[1,3],[2,4],[2,7],[3,5],[3,6],[3,7],[4,6]]
Output: [[],[],[],[0,1],[0,2],[0,1,3],[0,1,2,3,4],[0,1,2,3]]
Explanation:
The above diagram represents the input graph.
- Nodes 0, 1, and 2 do not have any ancestors.
- Node 3 has two ancestors 0 and 1.
- Node 4 has two ancestors 0 and 2.
- Node 5 has three ancestors 0, 1, and 3.
- Node 6 has five ancestors 0, 1, 2, 3, and 4.
- Node 7 has four ancestors 0, 1, 2, and 3.
Example 2:


Input: n = 5, edgeList = [[0,1],[0,2],[0,3],[0,4],[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
Output: [[],[0],[0,1],[0,1,2],[0,1,2,3]]
Explanation:
The above diagram represents the input graph.
- Node 0 does not have any ancestor.
- Node 1 has one ancestor 0.
- Node 2 has two ancestors 0 and 1.
- Node 3 has three ancestors 0, 1, and 2.
- Node 4 has four ancestors 0, 1, 2, and 3.
 

Constraints:

1 <= n <= 1000
0 <= edges.length <= min(2000, n * (n - 1) / 2)
edges[i].length == 2
0 <= fromi, toi <= n - 1
fromi != toi
There are no duplicate edges.
The graph is directed and acyclic.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

拓扑排序 + DP（最优、最清晰）

	按拓扑序遍历节点，
	对每条边 u -> v，
	把 u 以及 u 的所有祖先，加入到 v 的祖先集合中。

2️、 数据结构设计
	graph[u]       : u 的出边
	indegree[v]    : 入度（用于拓扑排序）
	ancestors[v]   : v 的所有祖先（用 Set 去重）

3️、 算法流程

	1）建图 + 统计入度

	2）BFS 拓扑排序

	3）对每条边 u -> v：

		ancestors[v] += u

		ancestors[v] += ancestors[u]

	4）最后对每个集合排序输出

Stats:
	时间：O(n^2 + m)
	空间：O(n^2)（最坏情况下每个节点有 n 个祖先）



class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        int[] indegree = new int[n];
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            indegree[e[1]]++;
        }

        List<Set<Integer>> ancestors = new ArrayList<>();
        for (int i = 0; i < n; i++) ancestors.add(new HashSet<>());

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        // 拓扑排序
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph.get(u)) {
                // u 是 v 的祖先
                ancestors.get(v).add(u);
                // u 的所有祖先也是 v 的祖先
                ancestors.get(v).addAll(ancestors.get(u));

                indegree[v]--;
                if (indegree[v] == 0) q.offer(v);
            }
        }

        // 输出结果（排序）
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> list = new ArrayList<>(ancestors.get(i));
            Collections.sort(list);
            res.add(list);
        }
        return res;
    }
}




Follow-up（高频）

❓1. 如果图中有环怎么办？

	先检测环（DFS + onPath）

	或拓扑排序失败

❓2. 如何优化空间？

	用 BitSet 替代 Set<Integer>

	并集操作更快，省内存

❓3. 如果只问某一个节点的 ancestors？

	从该节点反向 DFS 即可

❓4. 如果 n = 10^5？

	Set 会炸

	必须 BitSet / 压缩表示 / 离线查询


