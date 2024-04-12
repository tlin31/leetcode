847. Shortest Path Visiting All Nodes - Hard

An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.

graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j 
are connected.

Return the length of the shortest path that visits every node. You may start and stop at any node, you
may revisit nodes multiple times, and you may reuse edges.

 

Example 1:

Input: [[1,2,3],[0],[0],[0]]
Output: 4
Explanation: One possible path is [1,0,2,0,3]
Example 2:

Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
Output: 4
Explanation: One possible path is [0,1,4,2,3]
 

Note:

1 <= graph.length <= 12
0 <= graph[i].length < graph.length


******************************************************
key:
	- BFS, use bit mask
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-  Idea is to use BFS to traverse the graph. Since all edges are weighted 1, we can use a Queue. 
	   Since all edges are weighted 1, then closer nodes will always be evaluated before farther ones.

	- In order to represent a path:

		1） int bitMask: represent visited nodes: 0 -> not visited, 1 -> visited. 
						 use Set<Integer> of all nodes we visited so far will TLE and N <= 12, 
						 so we use a bitMask - 32 bits total in an Integer can cover all the possibilities. 
		2） int curr: current node we are on

	    When node 1,2,4 has been visited, we use 10110 to represent. (node 0 and 3 are not visited)
	    When visitedNodeBit = 11111....111 (however many nodes/bit). We find the ans.

	- We initialize our queue to contain N possible paths each starting from [0,N-1] b/c we can start
	  at any of N possible Nodes.

	- Each step:
		we remove element from the queue and see if we have covered all 12 nodes in our bitMask. 
			If we cover all nodes:
				- return the cost of the path immediately. b/c BFS, guranteed to be path with the 
				 lowest cost.

			Otherwise:
				- we get all the neighbors of current node, and for each neighbor, set the Node to
				  visited in bitMask, and then add it back into the queue.

	- In order to prevent duplicate paths from being visited, we use a Set<Tuple> to store the 
	  Set<Path> that we have visited before. 


Stats:

	- O(2^N * N)
	- 

	class Node {
        int id;
        int mask;
        
        Node(int id, int mask){
            this.id = id; 
            this.mask = mask;
        }
        
        public String toString() {
            return id + " " + mask;
        }
    }

    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int fullMask = (1 << n) - 1;
        
        Set<String> visited = new HashSet<>();
        Queue<Node> que = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            Node node = new Node(i, 1<<i);
            que.offer(node);
            visited.add(node.toString());
        }
        
        int level = 0;
        while (!que.isEmpty()) {
            int size = que.size();
            for (int i = 0; i < size; i++) {
                Node node = que.poll();
                if (node.mask == fullMask) 
                	return level;

                for (int next : graph[node.id]) {
                	// set this next node as visited in the mask
                    Node nextNode = new Node(next, node.mask | (1 << next));
                    if (visited.contains(nextNode.toString())) 
                    	continue;
                    que.offer(nextNode);
                    visited.add(nextNode.toString());
                }
            }
            level++;
        }
        
        return level;
    }
    
    

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



