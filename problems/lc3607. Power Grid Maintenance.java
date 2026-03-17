3607. Power Grid Maintenance - Medium

You are given an integer c representing c power stations, each with a unique identifier id from 1 to c (1‑based indexing).

These stations are interconnected via n bidirectional cables, represented by a 2D array connections, where each element connections[i] = [ui, vi] indicates a connection between station ui and station vi. Stations that are directly or indirectly connected form a power grid.

Initially, all stations are online (operational).

You are also given a 2D array queries, where each query is one of the following two types:

[1, x]: A maintenance check is requested for station x. If station x is online, it resolves the check by itself. If station x is offline, the check is resolved by the operational station with the smallest id in the same power grid as x. If no operational station exists in that grid, return -1.

[2, x]: Station x goes offline (i.e., it becomes non-operational).

Return an array of integers representing the results of each query of type [1, x] in the order they appear.

Note: The power grid preserves its structure; an offline (non‑operational) node remains part of its grid and taking it offline does not alter connectivity.

 

Example 1:

Input: c = 5, connections = [[1,2],[2,3],[3,4],[4,5]], queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]

Output: [3,2,3]

Explanation:



Initially, all stations {1, 2, 3, 4, 5} are online and form a single power grid.
Query [1,3]: Station 3 is online, so the maintenance check is resolved by station 3.
Query [2,1]: Station 1 goes offline. The remaining online stations are {2, 3, 4, 5}.
Query [1,1]: Station 1 is offline, so the check is resolved by the operational station with the smallest id among {2, 3, 4, 5}, which is station 2.
Query [2,2]: Station 2 goes offline. The remaining online stations are {3, 4, 5}.
Query [1,2]: Station 2 is offline, so the check is resolved by the operational station with the smallest id among {3, 4, 5}, which is station 3.
Example 2:

Input: c = 3, connections = [], queries = [[1,1],[2,1],[1,1]]

Output: [1,-1]

Explanation:

There are no connections, so each station is its own isolated grid.
Query [1,1]: Station 1 is online in its isolated grid, so the maintenance check is resolved by station 1.
Query [2,1]: Station 1 goes offline.
Query [1,1]: Station 1 is offline and there are no other stations in its grid, so the result is -1.
 

Constraints:

1 <= c <= 105
0 <= n == connections.length <= min(105, c * (c - 1) / 2)
connections[i].length == 2
1 <= ui, vi <= c
ui != vi
1 <= queries.length <= 2 * 105
queries[i].length == 2
queries[i][0] is either 1 or 2.
1 <= queries[i][1] <= c


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

1. Group Components: Use Union-Find (DSU) or BFS/DFS to group all n power stations into 
						their respective "grids" (connected components) based on the connections.


2. Manage Operational IDs: For each component, maintain a data structure that tracks the 
		current "online" station IDs. A Sorted Set or Lazy Heap is ideal for retrieving the minimum ID efficiently.


Stats:

Time Complexity: O（N+E+QlogC） where N is stations, E is connections, 
and Q is queries. Building components takes O（N+E. Each query involves a set 
operation (removal or finding min) taking O(log c)

Space Complexity: O(N+E) to store the graph and component sets


import java.util.*;

class Solution {
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        // 1. Build Adjacency List for Component Finding
        List<Integer>[] adj = new ArrayList[c + 1];
        for (int i = 1; i <= c; i++) adj[i] = new ArrayList<>();
        for (int[] conn : connections) {
            adj[conn[0]].add(conn[1]);
            adj[conn[1]].add(conn[0]);
        }

        // 2. Identify Components and their Online IDs
        int[] componentId = new int[c + 1];
        List<TreeSet<Integer>> componentOnlineIds = new ArrayList<>();
        boolean[] isOnline = new boolean[c + 1];
        Arrays.fill(isOnline, true);
        
        int currentId = 0; //index for the component
        for (int i = 1; i <= c; i++) {
            if (componentId[i] == 0) {
                TreeSet<Integer> set = new TreeSet<>();
                bfs(i, ++currentId, componentId, adj, set);
                componentOnlineIds.add(set);
            }
        }

        // 3. Process Queries
        List<Integer> type1Results = new ArrayList<>();
        for (int[] q : queries) {
            int type = q[0], x = q[1];
            int compIdx = componentId[x] - 1;
            
            if (type == 1) {
                if (isOnline[x]) {
                    type1Results.add(x);
                } else {
                    TreeSet<Integer> set = componentOnlineIds.get(compIdx);
                    type1Results.add(set.isEmpty() ? -1 : set.first());
                }
            } else {
                if (isOnline[x]) {
                    isOnline[x] = false;
                    componentOnlineIds.get(compIdx).remove(x);
                }
            }
        }

        return type1Results.stream().mapToInt(i -> i).toArray();
    }

    private void bfs(int start, int id, int[] componentId, List<Integer>[] adj, TreeSet<Integer> set) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        componentId[start] = id;
        while (!q.isEmpty()) {
            int u = q.poll();
            set.add(u);
            for (int v : adj[u]) {
                if (componentId[v] == 0) {
                    componentId[v] = id;
                    q.offer(v);
                }
            }
        }
    }
}



