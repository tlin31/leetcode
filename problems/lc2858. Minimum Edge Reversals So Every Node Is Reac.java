2858. Minimum Edge Reversals So Every Node Is Reachable - Hard

There is a simple directed graph with n nodes labeled from 0 to n - 1. The graph would form a tree if its edges were bi-directional.

You are given an integer n and a 2D integer array edges, where edges[i] = [ui, vi] represents a directed edge going from node ui to node vi.

An edge reversal changes the direction of an edge, i.e., a directed edge going from node ui to node vi becomes a directed edge going from node vi to node ui.

For every node i in the range [0, n - 1], your task is to independently calculate the minimum number of edge reversals required so it is possible to reach any other node starting from node i through a sequence of directed edges.

Return an integer array answer, where answer[i] is the minimum number of edge reversals required so it is possible to reach any other node starting from node i through a sequence of directed edges.

 

Example 1:



Input: n = 4, edges = [[2,0],[2,1],[1,3]]
Output: [1,1,0,2]
Explanation: The image above shows the graph formed by the edges.
For node 0: after reversing the edge [2,0], it is possible to reach any other node starting from node 0.
So, answer[0] = 1.
For node 1: after reversing the edge [2,1], it is possible to reach any other node starting from node 1.
So, answer[1] = 1.
For node 2: it is already possible to reach any other node starting from node 2.
So, answer[2] = 0.
For node 3: after reversing the edges [1,3] and [2,1], it is possible to reach any other node starting from node 3.
So, answer[3] = 2.
Example 2:



Input: n = 3, edges = [[1,2],[2,0]]
Output: [2,0,1]
Explanation: The image above shows the graph formed by the edges.
For node 0: after reversing the edges [2,0] and [1,2], it is possible to reach any other node starting from node 0.
So, answer[0] = 2.
For node 1: it is already possible to reach any other node starting from node 1.
So, answer[1] = 0.
For node 2: after reversing the edge [1, 2], it is possible to reach any other node starting from node 2.
So, answer[2] = 1.
 

Constraints:

2 <= n <= 105
edges.length == n - 1
edges[i].length == 2
0 <= ui == edges[i][0] < n
0 <= vi == edges[i][1] < n
ui != vi
The input is generated such that if the edges were bi-directional, the graph would be a tree.


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

The key insight is that since the graph is a tree, there is exactly one path between any two nodes. Reversing an edge to make a node reachable affects all nodes on one side of that edge.

Algorithm: Tree Rerooting (DP)

1. Treat as Undirected: Store the graph as an undirected tree, but keep track of the original direction. For an original edge u → u, assign it a weight of O. Add a "virtual" reverse edge u → u with a weight of 1.

2. First DFS (Root O): Calculate the total number of reversals needed if node O is the root. This is simply the sum of weights of all edges used to traverse from 0 to all other nodes.

Second DFS (Rerooting): For a neighbor v of u, the total reversals for v can be derived from u in O(1).

1. If the original edge was u → v (cost O), moving the root from u to v means we now need to traverse v → u, which is a reversal (+1).

2. If the original edge was v → u (cost 1), moving the root from u to v means we no longer need that reversal (-1).

Stats:

	- 
	- 

import java.util.*;

class Solution {
    List<int[]>[] adj;
    int[] ans;

    public int[] minEdgeReversals(int n, int[][] edges) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        
        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], 0}); // Original: 0 cost to traverse
            adj[e[1]].add(new int[]{e[0], 1}); // Reverse: 1 cost to traverse
        }

        ans = new int[n];
        // Phase 1: Total reversals for root 0
        ans[0] = dfs1(0, -1);
        // Phase 2: Compute others using rerooting logic
        dfs2(0, -1);
        
        return ans;
    }

    private int dfs1(int u, int p) {
        int res = 0;
        for (int[] edge : adj[u]) {
            if (edge[0] == p) continue;
            res += edge[1] + dfs1(edge[0], u);
        }
        return res;
    }

    private void dfs2(int u, int p) {
        for (int[] edge : adj[u]) {
            int v = edge[0], cost = edge[1];
            if (v == p) continue;
            // If cost was 0 (u->v), moving root to v adds a reversal (+1)
            // If cost was 1 (v->u), moving root to v removes a reversal (-1)
            ans[v] = ans[u] + (cost == 0 ? 1 : -1);
            dfs2(v, u);
        }
    }
}





class Solution:
    def minEdgeReversals(self, n: int, edges: list[list[int]]) -> list[int]:
        adj = [[] for _ in range(n)]
        for u, v in edges:
            adj[u].append((v, 0)) # Original
            adj[v].append((u, 1)) # Reversed
            
        ans = [0] * n
        
        # DFS 1: Initial cost for node 0
        def get_root_cost(u, p):
            cost = 0
            for v, c in adj[u]:
                if v != p:
                    cost += c + get_root_cost(v, u)
            return cost
        
        ans[0] = get_root_cost(0, -1)
        
        # DFS 2: Rerooting logic
        def reroot(u, p):
            for v, c in adj[u]:
                if v != p:
                    # If original was u->v (c=0), v needs +1 to reach u
                    # If original was v->u (c=1), v needs -1 to reach u
                    ans[v] = ans[u] + (1 if c == 0 else -1)
                    reroot(v, u)
                    
        reroot(0, -1)
        return ans







