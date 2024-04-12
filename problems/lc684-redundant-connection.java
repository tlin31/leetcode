684. Redundant Connection - Medium

In this problem, a tree is an undirected graph that is connected and has no cycles.

The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), 
with one additional edge added. The added edge has two different vertices chosen from 1 to N, and was 
not an edge that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, 
that represents an undirected edge connecting nodes u and v.

Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple
answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in 
the same format, with u < v.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given undirected graph will be like this:
  1
 / \
2 - 3


Example 2:
Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
Output: [1,4]
Explanation: The given undirected graph will be like this:
5 - 1 - 2
    |   |
    4 - 3
Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.

Update (2017-09-26):
We have overhauled the problem description + test cases and specified clearly the graph is an undirected 
graph. For the directed graph follow up please see Redundant Connection II)

******************************************************
key:
	- DFS, union find
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- dfs
	- 

stats:

	- Time O(N^2), where N is the number of vertices (and also the number of edges) in the graph. 
	   In the worst case, for every edge we include, we have to search every previously-occurring edge of the graph.
	- Space Complexity: O(N). The current construction of the graph has at most N nodes.



We build adjList progressevily as we go on adding edges. Say we are trying to add the edge [u,v] and want to know if that will form a cyle. We do not add the edge yet but we do dfs on the existing graph to see if we can reach v from u. If we can, then adding [u,v] will form a cycle. But we need the last possible edge that will form a cycle, so we can just set it to ret and move on without adding it.

Also since it is a dfs on an undirected graph, we have v in u's children and u in v's. So to avoid exploring the same edge from both the ends, we can pass in the current parent pre down the stack calls.

public int[] findRedundantConnection(int[][] edges) {
        int[] ret = null;
        int n = edges.length;
        List<Set<Integer>> adjList = new ArrayList<>(1001);
        for(int i=0; i < 1001; i++)
            adjList.add(new HashSet<>());
        
        for(int[] edge : edges){
            int u = edge[0], v = edge[1];
            if(dfs(u, v, 0, adjList)){
                ret = edge;
            }else{
                adjList.get(u).add(v);
                adjList.get(v).add(u);
            }
        }
        return ret;
    }
    
    private boolean dfs(int u, int v, int pre, List<Set<Integer>> adjList){
        if(u == v)
            return true;
        for(int w : adjList.get(u)){
            if(w == pre) continue;
            boolean ret = dfs(w, v, u, adjList);
            if(ret) return true;
        }
        return false;
    }



=======================================================================================================
method 2:

method:

	- union find
	- An edge will connect two nodes into one connected component.

When we count an edge in, if two nodes have already been in the same connected component, the edge will result in a cycle. That is, the edge is redundant.

We can make use of Disjoint Sets (Union Find).
If we regard a node as an element, a connected component is actually a disjoint set.

For example,

Given edges [1, 2], [1, 3], [2, 3],
  1
 / \
2 - 3
Initially, there are 3 disjoint sets: 1, 2, 3.
Edge [1,2] connects 1 to 2, i.e., 1 and 2 are winthin the same connected component.
Edge [1,3] connects 1 to 3, i.e., 1 and 3 are winthin the same connected component.
Edge [2,3] connects 2 to 3, but 2 and 3 have been winthin the same connected component already, so [2, 3] is redundant.

stats:
	- Union by Rank and Path Compression can optimize the time complexity from O(n) to O(logn)

	- Runtime: 0 ms, faster than 100.00% of Java online submissions for Redundant Connection.
	- Memory Usage: 39.7 MB, less than 66.67%


class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[2001];
        for (int i = 0; i < parent.length; i++) 
        	parent[i] = i;
        
        for (int[] edge: edges){
            int f = edge[0], 
                t = edge[1];
            if (find(parent, f) == find(parent, t)) 
            	return edge;

            else 
            	parent[find(parent, f)] = find(parent, t);
        }
        
        return new int[2];
    }
    
    private int find(int[] parent, int f) {
        if (f != parent[f]) {
          parent[f] = find(parent, parent[f]);  
        }
        return parent[f];
    }
}




class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        DisjointSet disjointSet = new DisjointSet(edges.length);
        
        for (int[] edge : edges) {
            if (!disjointSet.union(edge[0] - 1, edge[1] - 1)) return edge;
        }
        
        throw new IllegalArgumentException();
    }
    
    static class DisjointSet {
        
        private int[] parent;
        private byte[] rank;
        
        public DisjointSet(int n) {
            if (n < 0) throw new IllegalArgumentException();
            parent = new int[n];
            rank = new byte[n];
        }
        
        public int find(int x) {
            if (parent[x] == 0) return x;
            return parent[x] = find(parent[x]); // Path compression by halving.
        }
        
        // Return false if x, y are connected.
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return false;
            
            // Make root of smaller rank point to root of larger rank.
            if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else {
                parent[rootX] = rootY;
                rank[rootY]++;
            }
            
            return true;
        }
    }
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



