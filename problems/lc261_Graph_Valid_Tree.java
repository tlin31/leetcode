261. Graph Valid Tree - Medium

You have a graph of n nodes labeled from 0 to n - 1. You are given an integer n and a list of 
edges where edges[i] = [ai, bi] indicates that there is an undirected edge between nodes ai and 
bi in the graph.

Return true if the edges of the given graph make up a valid tree, and false otherwise.

 

Example 1:


Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true
Example 2:


Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
 

Constraints:

1 <= n <= 2000
0 <= edges.length <= 5000
edges[i].length == 2
0 <= ai, bi < n
ai != bi
There are no self-loops or repeated edges.

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

a graph, G, is a tree iff the following two conditions are met:

G is fully connected. In other words, for every pair of nodes in G, there is a path between them.
G contains no cycles. In other words, there is exactly one path between each pair of nodes in G.

对于一棵含 n 个节点的无向树：
边的数量 = n - 1


✅ 方法 1：Union-Find（并查集）
思路：

	初始时每个节点是一个独立集合。

	遍历每条边：如果两个节点已经在同一集合里 → 出现环 → ❌不是树。否则合并这两个集合。

	遍历完后，若边数 = n - 1 且只有一个连通分量 → ✅是树。

✅ 方法 2：DFS / BFS
	思路：

	从任意一个节点开始遍历；检查是否有环；



Stats:

	- 
	- 


public class Solution {
    
    class UnionFind {
        
        int[] parent;
        int[] rank;
        int count;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;  // number of components
            for (int i=0; i<n; ++i) { parent[i] = i; }  // initially, each node's parent is itself.
        }
        
        int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);  // find root with path compression
            }
            return parent[x];
        }
        
        boolean union(int x, int y) {
            int X = find(x), Y = find(y);

            if (X == Y) { return false; }
            
            if (rank[X] > rank[Y]) { parent[Y] = X; }  // tree Y is lower
            else if (rank[X] < rank[Y]) { parent[X] = Y; }  // tree X is lower
            else {  // same height
                parent[Y] = X;
                ++rank[X];
            }
            --count;
            return true;
        }
    }
    
    public boolean validTree(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for (int[] edge: edges) {
            int x = edge[0], y = edge[1];
            if (!uf.union(x, y)) { return false; }  // loop detected
        }
        return uf.count == 1;
    }
}



===================================================================================================

recursive dfs

public class Solution {
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        boolean[] visited = new boolean[n];
        if (hasCycle(graph, 0, -1, visited)) return false;

        // 检查是否连通
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    private boolean hasCycle(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
        visited[node] = true;
        for (int nei : graph.get(node)) {
            if (!visited[nei]) {
                if (hasCycle(graph, nei, node, visited)) return true;
            } else if (nei != parent) {
                return true; // 遇到不是父节点的已访问节点 => 有环
            }
        }
        return false;
    }
}



class Solution {
    
    private List<List<Integer>> adjacencyList = new ArrayList<>();
    private Set<Integer> seen = new HashSet<>();
    
    
    public boolean validTree(int n, int[][] edges) {
        
        if (edges.length != n - 1) return false;
        
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            adjacencyList.get(edge[0]).add(edge[1]);
            adjacencyList.get(edge[1]).add(edge[0]);
        }
        
        // We return true iff no cycles were detected,
        // AND the entire graph has been reached.
        return dfs(0, -1) && seen.size() == n;   
    }
    
    public boolean dfs(int node, int parent) {
        if (seen.contains(node)) return false;
        seen.add(node);
        for (int neighbour : adjacencyList.get(node)) {
            if (parent != neighbour) {
                boolean result = dfs(neighbour, node);
                if (!result) return false;
            }
        }
        return true;
    }
}

===================================================================================================

 iterative breadth-first search
public boolean validTree(int n, int[][] edges) {
            
    List<List<Integer>> adjacencyList = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        adjacencyList.add(new ArrayList<>());
    }
    for (int[] edge : edges) {
        adjacencyList.get(edge[0]).add(edge[1]);
        adjacencyList.get(edge[1]).add(edge[0]);
    }
    
    Map<Integer, Integer> parent = new HashMap<>();
    parent.put(0, -1);
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(0);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbour : adjacencyList.get(node)) {
            if (parent.get(node) == neighbour) {
                continue;
            }
            if (parent.containsKey(neighbour)) {
                return false;
            }
            queue.offer(neighbour);
            parent.put(neighbour, node);
        }
    }
    
    return parent.size() == n;   
}

