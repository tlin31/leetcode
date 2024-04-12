Geeks - Number of Triangles in Directed and Undirected Graphs

https://www.geeksforgeeks.org/number-of-triangles-in-directed-and-undirected-graphs/

Question:
Given a Graph, count number of triangles in it. The graph is can be directed or undirected.

Note:
(using matrix multiplication --> https://www.geeksforgeeks.org/number-of-triangles-in-a-undirected-graph/)



Algorithm: 
	- 3 nested loops to consider every triplet (i, j, k) and check for the above condition 
	  (there is an edge from i to j, j to k and k to i)

	- in an undirected graph, the triplet (i, j, k) can be permuted to give six combination 
	  Hence we divide the total count by 6 to get the actual number of triangles.
	- In case of directed graph, the number of permutation would be 3 (as order of nodes becomes relevant)
	  Hence in this case the total number of triangles will be obtained by dividing total count by 3. 


import java.io.*; 

class GFG { 

	// Number of vertices in the graph 
	int V = 4; 


	int countTriangle(int graph[][], boolean isDirected) { 
		// Initialize result 
		int count_Triangle = 0; 

		// Consider every possible triplet of edges in graph 
		for (int i = 0; i < V; i++)  {
			for (int j = 0; j < V; j++) { 
				for (int k=0; k<V; k++) { 
					// check the triplet if it satisfies the condition 
					if (graph[i][j] == 1 && 
						graph[j][k] == 1 && 
						graph[k][i] == 1) 
						count_Triangle++; 
				} 
			} 
		} 

		// if graph is directed , division is done by 3 
		// else division by 6 is done 
		if(isDirected == true)  
			count_Triangle /= 3; 
		else  
			count_Triangle /= 6; 
		 
		return count_Triangle; 
	} 

	// driver code 
		public static void main(String args[]) 
	{ 
			
		// Create adjacency matrix 
		// of an undirected graph 
		int graph[][] = {{0, 1, 1, 0}, 
							{1, 0, 1, 1}, 
							{1, 1, 0, 1}, 
							{0, 1, 1, 0} 
						}; 
		
		// Create adjacency matrix 
		// of a directed graph 
		int digraph[][] = { {0, 0, 1, 0}, 
							{1, 0, 0, 1}, 
							{0, 1, 0, 0}, 
							{0, 0, 1, 0} 
							}; 

		System.out.println("The Number of triangles "+ 
						"in undirected graph : " + 
							countTriangle(graph, false)); 

		System.out.println("\n\nThe Number of triangles"+ 
						" in directed graph : "+ 
						countTriangle(digraph, true)); 

	} 
} 

Output:
The Number of triangles in undirected graph : 2
The Number of triangles in directed graph : 2
Comparison of this approach with previous approach:
Advantages:

No need to calculate Trace.
Matrix- multiplication is not required.
Auxiliary matrices are not required hence optimized in space.
Works for directed graphs.
Disadvantages:

The time complexity is O(n3) and can’t be reduced any further.