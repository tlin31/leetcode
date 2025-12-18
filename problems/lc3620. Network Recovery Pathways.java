3620. Network Recovery Pathways - Hard

You are given a directed acyclic graph of n nodes numbered from 0 to n − 1. This is represented by a 2D array edges of length m, where edges[i] = [ui, vi, costi] indicates a one‑way communication from node ui to node vi with a recovery cost of costi.

Some nodes may be offline. You are given a boolean array online where online[i] = true means node i is online. Nodes 0 and n − 1 are always online.

A path from 0 to n − 1 is valid if:

All intermediate nodes on the path are online.
The total recovery cost of all edges on the path does not exceed k.
For each valid path, define its score as the minimum edge‑cost along that path.

Return the maximum path score (i.e., the largest minimum-edge cost) among all valid paths. If no valid path exists, return -1.

 

Example 1:

Input: edges = [[0,1,5],[1,3,10],[0,2,3],[2,3,4]], online = [true,true,true,true], k = 10

Output: 3

Explanation:



The graph has two possible routes from node 0 to node 3:

Path 0 → 1 → 3

Total cost = 5 + 10 = 15, which exceeds k (15 > 10), so this path is invalid.

Path 0 → 2 → 3

Total cost = 3 + 4 = 7 <= k, so this path is valid.

The minimum edge‐cost along this path is min(3, 4) = 3.

There are no other valid paths. Hence, the maximum among all valid path‐scores is 3.

Example 2:

Input: edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12

Output: 6

Explanation:



Node 3 is offline, so any path passing through 3 is invalid.

Consider the remaining routes from 0 to 4:

Path 0 → 1 → 4

Total cost = 7 + 5 = 12 <= k, so this path is valid.

The minimum edge‐cost along this path is min(7, 5) = 5.

Path 0 → 2 → 3 → 4

Node 3 is offline, so this path is invalid regardless of cost.

Path 0 → 2 → 4

Total cost = 6 + 6 = 12 <= k, so this path is valid.

The minimum edge‐cost along this path is min(6, 6) = 6.

Among the two valid paths, their scores are 5 and 6. Therefore, the answer is 6.

 

Constraints:

n == online.length
2 <= n <= 5 * 104
0 <= m == edges.length <= min(105, n * (n - 1) / 2)
edges[i] = [ui, vi, costi]
0 <= ui, vi < n
ui != vi
0 <= costi <= 109
0 <= k <= 5 * 1013
online[i] is either true or false, and both online[0] and online[n − 1] are true.
The given graph is a directed acyclic graph.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

目标：

最大化路径中的最小边权（max-min path）

👉 这是典型的 最大化最小值问题
👉 标准套路：Binary Search on Answer



Stats:

	- 
	- 
class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        //建图
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // find min cost i & max cost j along the way
        int i = Integer.MAX_VALUE;
        int j = Integer.MIN_VALUE;
        for (int[] ed : edges) {
            i = Math.min(i, ed[2]);
            j = Math.max(j, ed[2]);
            int u = ed[0];
            int v = ed[1];
            int w = ed[2];

            // 只保留在线节点之间的边
			// 离线节点直接从图中“删除”
            if (!online[u] || !online[v])
                continue;
            adj.get(u).add(new int[] {v, w});
        }

        int res = -1;
        while (i <= j) {
            int mid = (i + j) >> 1;
            if (isPossible(adj, mid, online, k)) {
                res = mid;
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }

        return res;
    }

    private boolean isPossible(List<List<int[]>> adj, int cost, boolean[] online, long k) {
        int n = online.length;
        Queue<long[]> q = new ArrayDeque<>(); // [node,sum,nodecost]

        //c[node]：到达该节点的最小路径和
        long[] c = new long[n]; 	
        Arrays.fill(c, Long.MAX_VALUE);

        //初始化（从 0 的邻居开始）
        for (int[] nodes : adj.get(0)) {
            int node = nodes[0];
            int nodeCost = nodes[1];
            if (nodeCost >= cost) {
                q.offer(new long[] {node, nodeCost, nodeCost});
                c[node] = nodeCost;
            }
        }

        // bfs
        while (!q.isEmpty()) {
            long[] curr = q.poll();
            int node = (int) curr[0];
            long sum = curr[1];

            //👉 一旦到达终点且路径合法，立即结束
            if (node == n - 1 && sum <= k)
                return true;

            //最短路径剪枝
            if (c[node] < sum)
                continue;

            //三层过滤：边权不达标、路径和超预算、不是更优路径
            for (int[] nei : adj.get(node)) {
                int neiNode = nei[0], neiCost = nei[1];
                if (neiCost < cost)
                    continue;
                long newCost = sum + neiCost;
                if (newCost > k || c[neiNode] < newCost)
                    continue;
                q.offer(new long[] {neiNode, newCost, neiCost});
                c[neiNode] = newCost;
            }
        }

        return false;
    }
}


