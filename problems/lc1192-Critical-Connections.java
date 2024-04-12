1192. Critical Connections in a Network - Hard

There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections 
forming a network where connections[i] = [a, b] represents a connection between servers a and b. 
Any server can reach any other server directly or indirectly through the network.

A critical connection is a connection that, if removed, will make some server unable to reach some 
other server.

Return all critical connections in the network in any order.

 

Example 1:


Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
Output: [[1,3]]
Explanation: [[3,1]] is also accepted.
 

Constraints:

1 <= n <= 10^5
n-1 <= connections.length <= 10^5
connections[i][0] != connections[i][1]
There are no repeated connections.

******************************************************
key:
	- An edge is a critical connection, if and only if it is not in a cycle.
	- 1) find cycles 2) discard all edges in the cycles 
						--> remaining connections are a complete collection of critical connections.
	- dfs
	- edge case:
		1) empty graph
		2)

******************************************************



=======================================================================================================
method 1:

method:
	- dfs
	- We record the timestamp that we visit each node. For each node, we check every neighbor except its 
	  parent and return a smallest timestamp in all its neighbors. 
	- If this timestamp is strictly less than the node timestamp, we know that this node is somehow in 
	  a cycle. Otherwise, this edge from the parent to this node is a critical connection.
	- 

stats:

	- Time = O(graph) + O(DFS) = O(|E| + |V|) + O(|E| + |V|) = O(|E| + |V|)
	- Space = O(graph) + O(visitedAt) + O(DFS) = O(|E| + |V|) + O(|V|) + O(|V|) = O(|V| + |E|)

class Solution {
    int T = 1;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // use a timestamp, for each node, check the samllest timestamp that can reach from the node
        List<List<Integer>> criticalConns = new ArrayList<>();

        // construct the graph 
        List[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for (List<Integer> conn : connections) {
            graph[conn.get(0)].add(conn.get(1));
            graph[conn.get(1)].add(conn.get(0));
        }
        
        int[] timestamp = new int[n]; // an array to save the timestamp that we meet a certain node
        
        // for each node, we need to run dfs for it, and return the smallest timestamp in all its 
        // children except its parent
        dfs(n, graph, timestamp, 0, -1, criticalConns);
        return criticalConns;
    }
    
    // return the minimum timestamp it ever visited in all the neighbors
    private int dfs(int n, List[] graph, int[] timestamp, int i, int parent, List<List<Integer>> criticalConns) {
        
    	// if already visited
        if (timestamp[i] != 0) 
        	return timestamp[i];
        
        timestamp[i] = T++;

        int minTimestamp = Integer.MAX_VALUE;
        for (int neighbor : (List<Integer>) graph[i]) {
            if (neighbor == parent) continue; // no need to check the parent
            int neighborTimestamp = dfs(n, graph, timestamp, neighbor, i, criticalConns);
            minTimestamp = Math.min(minTimestamp, neighborTimestamp);
        }
        
        if (minTimestamp >= timestamp[i]) {
            if (parent >= 0) criticalConns.add(Arrays.asList(parent, i));
        }
        return Math.min(timestamp[i], minTimestamp);
    }
}



=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



