# Graph & Algorithms

- isomorphic graph: 2 graph’s nodes & edges are bijection
- use dictionary of edges (hashtable) of adj list to quickly answer edge queries problem O(e) 

## Representation of graph

1. Matrix

2. Adjacency List

    - An array of lists is used. Size of the array is equal to the number of vertices. 
      Let the array be array[]. An entry array[i] represents the list of vertices adjacent to the ith vertex. This representation can also be used to represent a weighted graph. The weights of edges can be represented as lists of pairs. Following is adjacency list representation of the above graph.

    - Pros: Saves space O(|V|+|E|) . In the worst case, there can be C(V, 2) number of edges in a graph thus consuming O(V^2) space. Adding a vertex is easier.

    - Cons: Queries like whether there is an edge from vertex u to vertex v are not efficient and can be done O(V).

    - tricky: if undirected graph, remember to add edge for both verticies


    - Use java's linked list: 
```java

import java.util.LinkedList; 

public class GFG 
{ 

    static class Graph 
    { 
        int V; 
        LinkedList<Integer> adjListArray[]; 
        
        // constructor, V = # of verticies
        Graph(int V) 
        { 
            this.V = V; 
            
            // define the size of array as number of vertices 
            adjListArray = new LinkedList[V]; 
            
            // Create a new list for each vertex such that adjacent nodes can be stored 
            for(int i = 0; i < V ; i++){ 
                adjListArray[i] = new LinkedList<>(); 
            } 
        } 
    } 
    
    // Adds an edge to an undirected graph 
    static void addEdge(Graph graph, int src, int dest) 
    { 
        // Add an edge from src to dest. 
        graph.adjListArray[src].add(dest); 
        
        // Since graph is undirected, add an edge from dest to src also 
        graph.adjListArray[dest].add(src); 
    } 
    
    // A utility function to print the adjacency list representation of graph 
    static void printGraph(Graph graph) 
    {    
        for(int v = 0; v < graph.V; v++) 
        { 
            System.out.println("Adjacency list of vertex "+ v); 
            System.out.print("head"); 
            for(Integer pCrawl: graph.adjListArray[v]){ 
                System.out.print(" -> "+pCrawl); 
            } 
            System.out.println("\n"); 
        } 
    } 
    
    // Driver program to test above functions 
    public static void main(String args[]) 
    { 
        // create the graph given in above figure 
        int V = 5; 
        Graph graph = new Graph(V); 
        addEdge(graph, 0, 1); 
        addEdge(graph, 0, 4); 
        addEdge(graph, 1, 2); 
        addEdge(graph, 1, 3); 
        addEdge(graph, 1, 4); 
        addEdge(graph, 2, 3); 
        addEdge(graph, 3, 4); 
    
        // print the adjacency list representation of 
        // the above graph 
        printGraph(graph); 
    } 
} 

```


Adjacency list of vertex 0
 head -> 1-> 4

 Adjacency list of vertex 1
 head -> 0-> 2-> 3-> 4

 Adjacency list of vertex 2
 head -> 1-> 3

 Adjacency list of vertex 3
 head -> 1-> 2-> 4

 Adjacency list of vertex 4
 head -> 0-> 1-> 3


- use java's arraylist of arraylist


```java 

// Java code to demonstrate Graph representation 
// using ArrayList in Java 
import java.util.*; 

class Graph { 
    ArrayList<ArrayList<Integer> > adj; 
    int V; 
    Graph(int v) 
    { 
        V = v; 
        adj = new ArrayList<ArrayList<Integer> >(V); 
        for (int i = 0; i < V; i++) 
            adj.add(new ArrayList<Integer>()); 
    } 

    void addEdge(int u, int v) 
    { 
        adj.get(u).add(v); 
        adj.get(v).add(u); 
    } 

    void printAdjacencyList() 
    { 
        for (int i = 0; i < adj.size(); i++) { 
            System.out.println("Adjacency list of " + i); 
            for (int j = 0; j < adj.get(i).size(); j++) { 
                System.out.print(adj.get(i).get(j) + " "); 
            } 
            System.out.println(); 
        } 
    } 
} 

class Test { 

    public static void main(String[] args) 
    { 
        // Creating a graph with 5 vertices 
        int V = 5; 

        Graph g = new Graph(V); 

        // Adding edges one by one. 
        g.addEdge(0, 1); 
        g.addEdge(0, 4); 
        g.addEdge(1, 2); 
        g.addEdge(1, 3); 
        g.addEdge(1, 4); 
        g.addEdge(2, 3); 
        g.addEdge(3, 4); 
        g.printAdjacencyList(); 
    } 
}

```

## BFS

### 总结

- unlike trees, graphs may contain cycles, --> use a boolean visited array. 

- Time Complexity: O(V+E) where V is number of vertices in the graph and E is number of edges in the graph.

- Auxiliary Space: O(V)




### Implementation (use adjacency list)

1. Iterative approach:
    - use a queue
    - indegre-endegree-check whether a node is explored before adding it to the queue

```java
import java.io.*; 
import java.util.*; 

class Graph 
{ 
    // No. of vertices
    private int V;  

     //Adjacency Lists 
    private LinkedList<Integer> adj[];

    // Constructor 
    Graph(int v) 
    { 
        V = v; 
        adj = new LinkedList[v]; 
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList(); 
    } 

    // Function to add an edge into the graph 
    void addEdge(int v,int w) 
    { 
        adj[v].add(w); 
    } 

    // prints BFS traversal from a given source s 
    void BFS(int s) 
    { 
        // Mark all the vertices as not visited(By default set as false) 
        boolean visited[] = new boolean[V]; 

        // Create a queue for BFS 
        LinkedList<Integer> queue = new LinkedList<Integer>(); 

        // Mark the current node as visited and enqueue it 
        visited[s]=true; 
        queue.add(s); 

        while (queue.size() != 0) 
        { 
            // Dequeue a vertex from queue and print it 
            s = queue.poll(); 
            System.out.print(s+" "); 

            // Get all adjacent vertices of the dequeued vertex s 
            // If a adjacent has not been visited, then mark it visited and enqueue it 
            Iterator<Integer> i = adj[s].listIterator(); 
            while (i.hasNext()) 
            { 
                int n = i.next(); 
                if (!visited[n]) 
                { 
                    visited[n] = true; 
                    queue.add(n); 
                } 
            } 
        } 
    } 

    // Driver method to 
    public static void main(String args[]) 
    { 
        Graph g = new Graph(4); 

        g.addEdge(0, 1); 
        g.addEdge(0, 2); 
        g.addEdge(1, 2); 
        g.addEdge(2, 0); 
        g.addEdge(2, 3); 
        g.addEdge(3, 3); 

        System.out.println("Following is Breadth First Traversal "+ 
                        "(starting from vertex 2)"); 

        g.BFS(2); 
    } 
} 

```


2. Recursive

```java

import java.util.*;

// data structure to store graph edges
class Edge
{
    int source, dest;

    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
    }
};

// class to represent a graph object
class Graph
{
    // A List of Lists to represent an adjacency list
    List<List<Integer>> adjList = null;

    // Constructor
    Graph(List<Edge> edges, int N) {
        adjList = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            adjList.add(i, new ArrayList<>());
        }

        // add edges to the undirected graph
        for (int i = 0; i < edges.size(); i++)
        {
            int src = edges.get(i).source;
            int dest = edges.get(i).dest;

            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
    }
}

class BFS
{
    // Perform BFS recursively on graph
    public static void recursiveBFS(Graph graph, Queue<Integer> q, boolean[] discovered)
    {
        if (q.isEmpty())
            return;

        // pop front node from queue and print it
        int v = q.poll();
        System.out.print(v + " ");

        // do for every edge (v -> u)
        for (int u : graph.adjList.get(v))
        {
            if (!discovered[u])
            {
                // mark it discovered and push it into queue
                discovered[u] = true;
                q.add(u);
            }
        }

        recursiveBFS(graph, q, discovered);
    }

    public static void main(String[] args)
    {
        // List of graph edges as per above diagram
        List<Edge> edges = Arrays.asList(
                new Edge(1, 2), new Edge(1, 3), new Edge(1, 4),
                new Edge(2, 5), new Edge(2, 6), new Edge(5, 9),
                new Edge(5, 10), new Edge(4, 7), new Edge(4, 8),
                new Edge(7, 11), new Edge(7, 12)
                // vertex 0, 13 and 14 are single nodes
        );

        // Set number of vertices in the graph
        final int N = 15;

        // create a graph from edges
        Graph graph = new Graph(edges, N);

        // stores vertex is discovered or not
        boolean[] discovered = new boolean[N];

        // create a queue used to do BFS
        Queue<Integer> q = new ArrayDeque<>();

        // Do BFS traversal from all undiscovered nodes to
        // cover all unconnected components of graph
        for (int i = 0; i < N; i++) {
            if (discovered[i] == false)
            {
                // mark source vertex as discovered
                discovered[i] = true;

                // push source vertex into the queue
                q.add(i);

                // start BFS traversal from vertex i
                recursiveBFS(graph, q, discovered);
            }
        }
    }
}

```

### bfs queue template in matrix

example: lc 200 number of islands

1. set boolean matrix visited to avoid going in loops
2. can use queue to store indicies int[]
3. in double for loop: if (condition met && !visited[i][j]), then queue offer current, set visited matrix to true, and call bfs
4. in bfs method: 
    while (!q.isEmpty()) 

```java
class Solution {
    int[][] dirs = {{0,1}, {1,0}, {0, -1}, {-1, 0}};


    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        
        int m = grid.length, n = grid[0].length;
        
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        int count = 0;

        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {
                if(grid[i][j] == '1' && !visited[i][j]) {
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                    bfs(grid, queue, visited);
                    count++;
                }
            }
        }
        
        return count;
    }
    
    private void bfs(char[][] grid, Queue<int[]> queue, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;
        
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                
                // if not valid/not island, keep going
                if (x < 0 || x >= m || y < 0 || y >=n || visited[x][y] || grid[x][y] == '0') 
                    continue;
                
                //else add the next 'island land'
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
    }
}
```
## DFS

### 总结

- Time Complexity: O(V+E) where V is number of vertices in the graph and E is number of edges in the graph.



### Implementation
1. Use recursion:
- algorithm: 
    1) start from a given node
    2) Mark current node as visited
    3) Visit current node
    4) Traverse unvisited adjacent vertices

```java
    public void dfs(int start) {
        boolean[] isVisited = new boolean[adjVertices.size()];
        dfsRecursive(start, isVisited);
    }
     
    private void dfsRecursive(int current, boolean[] isVisited) {
        isVisited[current] = true;
        visit(current);
        for (int dest : adjVertices.get(current)) {
            if (!isVisited[dest])
                dfsRecursive(dest, isVisited);
        }
    }
```

template for lc200
```java
    public int numIslands(char[][] A) {
        int m = A.length, n = A[0].length, res = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                res += dfs(A, i, j);
        return res;
    }

    private int dfs(char[][] A, int i, int j) {
        int m = A.length, n = A[0].length;
        if (i < 0 || i == m || j < 0 || j == n || A[i][j] == '0') return 0;
        A[i][j] = '0';
        dfs(A, i - 1, j);
        dfs(A, i + 1, j);
        dfs(A, i, j - 1);
        dfs(A, i, j + 1);
        return 1;
    }
```


2. Use stack
- algorithm:
    start from a given node
    Push start node into stack
    While Stack not empty
    Mark current node as visited
    Visit current node
    Push unvisited adjacent vertices


```java
public void dfsWithoutRecursion(int start) {
    Stack<Integer> stack = new Stack<Integer>();
    boolean[] isVisited = new boolean[adjVertices.size()];
    stack.push(start);
    while (!stack.isEmpty()) {
        int current = stack.pop();
        isVisited[current] = true;

        visit(current);
        
        for (int dest : adjVertices.get(current)) {
            if (!isVisited[dest])
                stack.push(dest);
        }
    }
}
```


------------ use Iterator --------------
1. fully connected, iterative implementation: 

```java
// Java program to print DFS traversal from a given given graph 
import java.io.*; 
import java.util.*; 

// This class represents a directed graph using adjacency list 
// representation 
class Graph 
{ 
    private int V; // No. of vertices 

    // Array of lists for Adjacency List Representation 
    private LinkedList<Integer> adj[]; 

    // Constructor 
    Graph(int v) 
    { 
        V = v; 
        adj = new LinkedList[v]; 
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList(); 
    } 

    //Function to add an edge into the graph 
    void addEdge(int v, int w) 
    { 
        adj[v].add(w); // Add w to v's list. 
    } 

    // A function used by DFS 
    void DFSUtil(int v,boolean visited[]) 
    { 
        // Mark the current node as visited and print it 
        visited[v] = true; 
        System.out.print(v+" "); 

        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
            if (!visited[n]) 
                DFSUtil(n, visited); 
        } 
    } 

    // The function to do DFS traversal. It uses recursive DFSUtil() 
    void DFS(int v) 
    { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        boolean visited[] = new boolean[V]; 

        // Call the recursive helper function to print DFS traversal 
        DFSUtil(v, visited); 
    } 

    public static void main(String args[]) 
    { 
        Graph g = new Graph(4); 

        g.addEdge(0, 1); 
        g.addEdge(0, 2); 
        g.addEdge(1, 2); 
        g.addEdge(2, 0); 
        g.addEdge(2, 3); 
        g.addEdge(3, 3); 

        System.out.println("Following is Depth First Traversal "+ 
                        "(starting from vertex 2)"); 

        g.DFS(2); 
    } 
} 

```


2. Note!!!! for disconnected graph
    - the code below for BFS & DFS only the vertices reachable from a given source vertex. All the vertices may not be reachable from a given vertex (example Disconnected graph). 
    - Solution:  call DFSUtil() for every vertex. Also, before calling DFSUtil(), we should check if it is already printed by some other call of DFSUtil(). 


```java

 // A function used by DFS 
    void DFSUtil(int v,boolean visited[]) 
    { 
        // Mark the current node as visited and print it 
        visited[v] = true; 
        System.out.print(v+" "); 
  
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> i = adj[v].listIterator(); 
        while (i.hasNext()) 
        { 
            int n = i.next(); 
            if (!visited[n]) 
                DFSUtil(n,visited); 
        } 
    } 
  
    // The function to do DFS traversal. It uses recursive DFSUtil() 
    void DFS() 
    { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        boolean visited[] = new boolean[V]; 
  
        // Call the recursive helper function to print DFS traversal 
        // starting from all vertices one by one 
        for (int i=0; i<V; ++i) 
            if (visited[i] == false) 
                DFSUtil(i, visited); 
    } 
```
## Topological Sorting

- - key: in-degrees, dfs, stack

- Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed edge uv, vertex u comes before v in the ordering. 

- not possible if the graph is not a DAG!!!

- For example, a topological sorting of the following graph is “5 4 2 3 1 0”. There can be more than one topological sorting for a graph. For example, another topological sorting of the following graph is “4 5 2 3 1 0”. The first vertex in topological sorting is always a vertex with in-degree as 0 (a vertex with no incoming edges).

- Theorem : A DAG G has at least one vertex with in-degree 0 and one vertex with out-degree 0.
	- Proof: There’s a simple proof to the above fact is that a DAG does not contain a cycle which means that all paths will be of finite length. Now let S be the longest path from u(source) to v(destination). Since S is the longest path there can be no incoming edge to u and no outgoing edge from v, if this situation had occurred then S would not have been the longest path => indegree(u) = 0 and outdegree(v) = 0

- Algo:
```python
	L ← Empty list that will contain the sorted elements
	S ← Set of all nodes with no incoming edge
	while S is non-empty do
	    remove a node n from S
	    add n to tail of L
	    for each node m with an edge e from n to m do
	        remove edge e from the graph
	        if m has no other incoming edges then
	            insert m into S
	if graph has edges then
	    return error   (graph has at least one cycle)
	else 
	    return L   (a topologically sorted order)
```

### Algorithm 1: modify DFS
1. create a temporary stack.
2. Mark all the vertices as not visited
3. recursively call topological sorting for all its adjacent vertices, then push it to a stack. 
	 3.1 Mark the current node as visited. 
     3.2 Recur for all the unvisited vertices adjacent to this vertex 
     3.3 Push current vertex to stack which stores result 
4. print contents of stack. 

- no in-degree involved 


- Note that a vertex is pushed to stack only when all of its adjacent vertices (and their adjacent vertices and so on) are already in stack.

![Example](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20190704153933/TopologicalSorting1.png)

Easier ver:

```java

public List<Integer> topologicalSort(int start) {
    LinkedList<Integer> result = new LinkedList<Integer>();
    boolean[] isVisited = new boolean[adjVertices.size()];
    topologicalSortRecursive(start, isVisited, result);
    return result;
}
 
private void topologicalSortRecursive(int current, boolean[] isVisited, LinkedList<Integer> result{
    isVisited[current] = true;
    for (int dest : adjVertices.get(current)) {
        if (!isVisited[dest])
            topologicalSortRecursive(dest, isVisited, result);
    }

    // add at the begining of the list --> decreasing finish time
    // early it finish, later it is in the list
    result.addFirst(current);
}

```




```java
class Graph 
{ 
    // No. of vertices 
    private int V;   

    // Adjacency List of all edges
    private LinkedList<Integer> adj[];
  
    //Constructor 
    Graph(int v) { 
        V = v; 
        adj = new LinkedList[v]; 
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList(); 
    } 
  
    // Function to add an edge into the graph 
    void addEdge(int v,int w) { 
        adj[v].add(w); 
    } 
  
    // The function to do Topological Sort. It uses recursive topologicalSortUtil() 
    void topologicalSort() { 
        Stack stack = new Stack(); 
  
        // Mark all the vertices as not visited 
        boolean visited[] = new boolean[V]; 
        for (int i = 0; i < V; i++) 
            visited[i] = false; 
  
        // Call the recursive helper function for all unvisited vertices 
        for (int i = 0; i < V; i++) 
            if (visited[i] == false) 
                topoRecursive(i, visited, stack); 
  
        // Print contents of stack 
        while (stack.empty()==false) 
            System.out.print(stack.pop() + " "); 
    } 

    // A recursive function used by topologicalSort 
    void topoRecursive(int v, boolean visited[], Stack stack) 
    { 
        // Mark the current node as visited. 
        visited[v] = true; 
        Integer i; 
  
        // Recur for all the vertices adjacent to this vertex 
        Iterator<Integer> it = adj[v].iterator(); 
        while (it.hasNext()) 
        { 
            i = it.next(); 
            if (!visited[i]) 
                topoRecursive(i, visited, stack); 
        } 
  
        // Push current vertex to stack which stores result 
        stack.push(new Integer(v)); 
    } 

// driver method 
    public static void main(String args[]) 
    { 
        // Create a graph given in the above diagram 
        Graph g = new Graph(6); 
        g.addEdge(5, 2); 
        g.addEdge(5, 0); 
        g.addEdge(4, 0); 
        g.addEdge(4, 1); 
        g.addEdge(2, 3); 
        g.addEdge(3, 1); 
  
        System.out.println("Following is a Topological " + 
                           "sort of the given graph"); 
        g.topologicalSort(); 
    } 

```
Example lc 207 course schedule
```java
    - dfs
    - 也需要建立有向图，还是用二维数组来建立，和 BFS 不同的是，我们像现在需要一个一维数组 visit 来记录访问状态
    - 三种状态:
        0表示还未访问过，
        1表示已经访问了
        -1 表示有冲突
    - 大体思路是，先建立好有向图，然后从第一门课开始，找其可构成哪门课，暂时将当前课程标记为已访问，然后对新得到的课程
        调用 DFS 递归，直到出现新的课程已经访问过了，则返回 false，没有冲突的话返回 true，然后把标记为已访问的课程
        改为未访问

stats:

    - Runtime: 4 ms, faster than 75.85% of Java online submissions for Course Schedule.
    - Memory Usage: 44.3 MB, less than 96.15%


public boolean canFinish(int numCourses, int[][] prerequisites) {
    if(prerequisites == null){
        throw new IllegalArgumentException("illegal prerequisites array");
    }
 
    int len = prerequisites.length;
 
    if(numCourses == 0 || len == 0){
        return true;
    }
 
    //track visited courses
    int[] visit = new int[numCourses];
 
    // use the map to store what courses depend on a course 
    HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
    for(int[] a: prerequisites){
        if(map.containsKey(a[1])){
            map.get(a[1]).add(a[0]);
        }else{
            // new class never seen before
            ArrayList<Integer> l = new ArrayList<Integer>();
            l.add(a[0]);
            map.put(a[1], l);
        }
    }
 
    for(int i=0; i<numCourses; i++){
        if(!canFinishDFS(map, visit, i))
            return false;
    }
 
    return true;
}
 
private boolean canFinishDFS(HashMap<Integer,ArrayList<Integer>> map, int[] visit, int i){
    if(visit[i]==-1) 
        return false;
    if(visit[i]==1) 
        return true;
 
    visit[i]=-1;
    if(map.containsKey(i)){

        // check if there's clash
        for(int j: map.get(i)){
            if(!canFinishDFS(map, visit, j)) 
                return false;
        }
    }
 
    visit[i]=1;
 
    return true;
}
```


#### Time complexity

- Time Complexity: The above algorithm is simply DFS with an extra stack. So time complexity is the same as DFS which is O(V+E).

- Note : can use vector instead of stack. If the vector is used then print the elements in reverse order to get the topological sorting.


#### Use DFS's finish time
- run DFS(G) & output node in decreasing finishing time


### Algorithm 2: modify bfs (use queue), aka Kahn's algorithm

		Map<Integer, List<Integer>> map = new HashMap<>();


1. Compute in-degree (number of incoming edges) for each of the vertex present in the DAG and initialize the count of visited nodes as 0.

2. Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)

3. Remove a vertex from the queue (Dequeue operation) and then:
	- Increment count of visited nodes by 1.
	- Decrease in-degree by 1 for all its neighboring nodes.
	- If in-degree of a neighboring nodes is reduced to zero, then add that to the queue.

4. Repeat Step 3 until the queue is empty.

5. If count of visited nodes is not equal to the number of nodes in the graph then the topological sort is not possible for the given graph.

#### How to find in-degree of each node?

1. Take an in-degree array，traverse the array of edges and simply increase the counter of the destination node by 1. --> Time Complexity: O(V+E)

```java
for each node in Nodes
    indegree[node] = 0;

for each edge(src,dest) in Edges
    indegree[dest]++
```

2) Traverse the list for every node and then increment the in-degree of all the nodes connected to it by 1.

    for each node in Nodes
        If (list[node].size()!=0) then
        for each dest in list
            indegree[dest]++;

```java
class Graph 
{ 
	// No. of vertices 
    int V;
      
    //An Array of List which contains references to the Adjacency List of each vertex 
    List <Integer> adj[]; 

    // Constructor 
    public Graph(int V)
    { 
        this.V = V; 
        adj = new ArrayList[V]; 
        for(int i = 0; i < V; i++) 
            adj[i]=new ArrayList<Integer>(); 
    } 
      
    // function to add an edge to graph 
    public void addEdge(int u,int v) 
    { 
        adj[u].add(v); 
    } 

    // prints a Topological Sort of the complete graph     
    public void topologicalSort() 
    { 
        // Create a array to store indegrees of all vertices. Initialize all indegrees as 0. 
        int indegree[] = new int[V]; 
          
        // Traverse adjacency lists to fill indegrees of vertices. This step takes O(V+E) time         
        for(int i = 0; i < V; i++) 
        { 
            ArrayList<Integer> temp = (ArrayList<Integer>) adj[i]; 
            for(int node : temp) 
            { 
                indegree[node]++; 
            } 
        } 
          
        // Create a queue and enqueue all vertices with indegree 0 
        Queue<Integer> q = new LinkedList<Integer>(); 

        for(int i = 0;i < V; i++) 
        { 
            if(indegree[i]==0) 
                q.add(i); 
        } 
          
        // Initialize count of visited vertices 
        int cnt = 0; 
          
        // Create a vector to store result (A topological ordering of the vertices) 
        Vector <Integer> topOrder = new Vector<Integer>(); 
        while(!q.isEmpty()) 
        { 
            // Extract front of queue (or perform dequeue) and add it to topological order 
            int u = q.poll(); 
            topOrder.add(u); 
              
            // Iterate through all its neighbouring nodes of dequeued node u and decrease their 
            // in-degree by 1 
            for(int node : adj[u]) 
            { 
                // If in-degree becomes zero, add it to queue 
                if(--indegree[node] == 0) 
                    q.add(node); 
            } 
            cnt++; 
        } 
          
        // Check if there was a cycle         
        if(cnt != V) 
        { 
            System.out.println("There exists a cycle in the graph"); 
            return ; 
        } 
          
        // Print topological order             
        for(int i : topOrder) 
        { 
            System.out.print(i+" "); 
        } 
    } 
} 
```

#### Time Complexity
- The outer for loop will be executed V number of times and the inner for loop will be executed E number of times, Thus overall time complexity is O(V+E).

### Applications:
1. scheduling jobs from the given dependencies among jobs
2. instruction scheduling, ordering of formula cell evaluation when recomputing formula values in spreadsheets, logic synthesis, determining the order of compilation tasks to perform in makefiles, data serialization, and resolving symbol dependencies in linkers

### Sample questions

1. lc269. Alien Dictionary - hard
2. lc207. Course Schedule - Medium
3. lc210. Course Schedule II - Medium  
4. lc329.  Longest Increasing Path in a Matrix - Hard  (not done)  
5. lc444. Sequence Reconstruction -Medium  (not done)  



## Dijkstra’s shortest path algorithm 

Given a graph and a source vertex in the graph, find shortest paths from source to all vertices in the given graph.
Dijkstra’s algorithm is very similar to Prim’s algorithm for minimum spanning tree. Like Prim’s MST, we generate a SPT (shortest path tree) with given source as root. We maintain two sets, one set contains vertices included in shortest path tree, other set includes vertices not yet included in shortest path tree. At every step of the algorithm, we find a vertex which is in the other set (set of not yet included) and has a minimum distance from the source.

Below are the detailed steps used in Dijkstra’s algorithm to find the shortest path from a single source vertex to all other vertices in the given graph.

Algorithm
- 1. Create a set sptSet (shortest path tree set) that keeps track of vertices included in shortest path tree, i.e., whose minimum distance from source is calculated and finalized. Initially, this set is empty

- 2. Assign a distance value to all vertices in the input graph. Initialize all distance values as INFINITE. Assign distance value as 0 for the source vertex so that it is picked first.

- 3. While sptSet doesn’t include all vertices
    - Pick a vertex u which is not there in sptSet and has minimum distance value.
    - Include u to sptSet.
    - Update distance value of all adjacent vertices of u. 
        - iterate through all adjacent vertices, for every adjacent vertex v, if distance value of u (from source) + weight of edge u-v < distance value of v, then update the distance value of v.



## Union Find (disjoint set)

### General:
- A disjoint-set data structure is a data structure that keeps track of a set of elements partitioned into a number of disjoint (non-overlapping) subsets. A union-find algorithm is an algorithm that performs two useful operations on such a data structure:

    1. Find: Determine which subset a particular element is in. This can be used for determining if 2 elements are in the same subset.
    2. Union: Join two subsets into a single subset.


- check by edge

- Data Structures used:
  1. Array : An array of integers, called parent[]. If we are dealing with n items, i’th element of the array represents the i’th item. More precisely, the i’th element of the array is the parent of the i’th item. These relationships create one, or more, virtual trees.

  2. Tree : It is a disjoint set. If two elements are in the same tree, then they are in the same disjoint set. The root node (or the topmost node) of each tree is called the representative of the set. There is always a single unique representative of each set. A simple rule to identify representative is, if i is the representative of a set, then parent[i] = i. If i is not the representative of his set, then it can be found by traveling up the tree until we find the representative.

 
- Operations :

  1. Find : Finds the representative of the set that i is an element of, it can be implemented by recursively traversing the parent array until we hit a node who is parent of itself.

```java
        int find(int x) {
            // // If i is the parent of itself
            // if (parent[i] == i) {
            //     // i is the representative of this set
            //     return i;
            // }             
            if (x != parent[x]) {
                parent[x] = find(parent[x]);  // find root with path compression
            }
            return parent[x];
        }
        

 

```

  2. Union: It takes in two elements. And finds the representatives of their sets using the find operation, and finally puts either one of the trees (representing the set) under the root node of the other tree, effectively merging the trees and the sets.

```java
    void union(int i, int j) {
        // Find the representatives (or the root nodes) for the set that includes i
        int irep = this.Find(i),

        // And do the same for the set that includes j    
        int jrep = this.Find(j);

        // Make the parent of i’s representative be j’s representative effectively 
        // moving all of i’s set into j’s set)
        this.Parent[irep] = jrep;
    }

```

### Improvements - Union by Rank & Path Compression

- The efficiency depends heavily on the height of the tree. We need to minimize the height of tree in order improve the efficiency. 

1.  Union by Rank:
    - always attach smaller depth tree under the root of the deeper tree. This technique is called union   by rank. 
    
    - rank is preferred instead of height because if path compression technique (we have discussed it below) is used, then rank is not always equal to height. Also, size (in place of height) of trees can also be used as rank. Using size as rank also yields worst case time complexity as O(Logn) 

    - The above operations can be optimized to O(Log n) in worst case. 

    Initially, all elements are single element subsets --> 0 1 2 3 
    
    Do Union(0, 1)
       1   2   3  
      /
     0

    Do Union(1, 2)
       1    3
     /  \
    0    2

    Do Union(2, 3)
        1    
     /  |  \
    0   2   3


```java
    void Union(subset [] subsets, int x , int y ) { 
        int xroot = find(subsets, x); 
        int yroot = find(subsets, y); 
      
        if (subsets[xroot].rank < subsets[yroot].rank) 
            subsets[xroot].parent = yroot; 

        else if (subsets[yroot].rank < subsets[xroot].rank) 
            subsets[yroot].parent = xroot; 
        
        else{ 
            subsets[xroot].parent = yroot; 
            subsets[yroot].rank++; 
        } 
    } 

```

2. Path Compression (Modifications to find()) : 
    - It speeds up the data structure by compressing the height of the trees. It can be achieved by inserting a small caching mechanism into the Find operation. When find() is called for an element x, root of the tree is returned. To make the found root as parent of x so that we don’t have to traverse all intermediate nodes again. If x is root of a subtree, then path (to root) from all nodes under x also compresses.

    - indegreendegreeTime compexity of find: O(Logn)

    Let the subset {0, 1, .. 9} be represented as below and find() is called
    for element 3.
                  9
             /    |    \  
            4     5      6
         /     \        /  \
        0        3     7    8
                /  \
               1    2  

    When find() is called for 3, we traverse up and find 9 as representative
    of this subset. With path compression, we also make 3 as the child of 9 so 
    that when find() is called next time for 1, 2 or 3, the path to root is reduced.

                   9
              /   /  \    \
            4    5    6      3 
          /          /  \   /  \
        0           7    8  1   2          



```java
    int find(int i) {
        // If i is the parent of itself --> it's the representative
        if (Parent[i] == i) {
            return i;
        } else { 
            // Recursively find the representative.
            int result = find(Parent[i]);

            // We cache the result by moving i’s node directly under the representative of 
            // this set
            Parent[i] = result;
           
            return result;
         }
    }
```




### Find whether a graph contains cycle:

```java

class Graph { 
    int V, E; 
    Edge[] edge; 
      
    Graph(int nV, int nE) { 
        V = nV; 
        E = nE; 
        edge = new Edge[E]; 
        for (int i = 0; i < E; i++) { 
            edge[i] = new Edge(); 
        } 
    } 
      
    // class to represent edge  
    class Edge 
    { 
        int src, dest; 
    } 
      
    // class to represent Subset 
    class subset 
    { 
        int parent; 
        int rank; 
    } 
      
  
    // path compression technique 
    int find(subset [] subsets , int i) 
    { 
    if (subsets[i].parent != i) 
        subsets[i].parent = find(subsets, subsets[i].parent); 
        return subsets[i].parent; 
    } 
      
    // uses union by rank) 
    void Union(subset [] subsets,  
               int x , int y ) 
    { 
        int xroot = find(subsets, x); 
            int yroot = find(subsets, y); 
      
        if (subsets[xroot].rank < subsets[yroot].rank) 
            subsets[xroot].parent = yroot; 
        else if (subsets[yroot].rank < subsets[xroot].rank) 
            subsets[yroot].parent = xroot; 
        else
        { 
            subsets[xroot].parent = yroot; 
            subsets[yroot].rank++; 
        } 
    } 
              
    // The main function to check whether a given graph contains cycle or not 
    int isCycle(Graph graph) 
    { 
        int V = graph.V; 
        int E = graph.E; 
      
        subset[] subsets = new subset[V]; 
        for (int v = 0; v < V; v++) 
        { 
      
            subsets[v] = new subset(); 
            subsets[v].parent = v; 
            subsets[v].rank = 0; 
        } 
      
        for (int e = 0; e < E; e++) 
        { 
            int x = find(subsets, graph.edge[e].src); 
            int y = find(subsets, graph.edge[e].dest); 
            if(x == y) 
                return 1; 
            Union(subsets, x, y); 
        } 
    return 0; 
    } 
      
    // Driver Code 
    public static void main(String [] args) 
    { 
    /* Let us create the following graph 
        0 
        | \ 
        | \ 
        1-----2 */
      
    int V = 3, E = 3; 
    Graph graph = new Graph(V, E); 
      
    // add edge 0-1 
    graph.edge[0].src = 0; 
    graph.edge[0].dest = 1; 
      
    // add edge 1-2 
    graph.edge[1].src = 1; 
    graph.edge[1].dest = 2; 
      
    // add edge 0-2 
    graph.edge[2].src = 0; 
    graph.edge[2].dest = 2; 
      
    if (graph.isCycle(graph) == 1) 
        System.out.println("Graph contains cycle"); 
    else
        System.out.println("Graph doesn't contain cycle"); 
    } 
}
```

### union find applied in lc 200 number of islands

```java
class Solution {
    
    // store the parent
    int[] par;
    
    public int numIslands(char[][] a) {
        if(a.length==0) 
            return 0;
        
        int n = a.length, m=a[0].length;
        par = new int[m*n];
        Arrays.fill(par, -1); 
        
        // go from bottom left corner to up.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                
                if(a[i][j]=='1'){
                    par[i*m+j]= i*m+j; // note, that `par` was filled witn -1 values

                    if(i>0 && a[i-1][j]=='1') 
                        union(i*m+j, (i-1)*m+j); // union current+top

                    if(j>0 && a[i][j-1]=='1') 
                        union(i*m+j, i*m+(j-1)); // union current+left
                }
                
            }
        }
        
        // since all connected 1's are grouped with the same par value
        Set<Integer> set = new HashSet<>();
        for(int k=0;k<par.length;k++){
            if(par[k]!=-1) 
                set.add(find(k));
        }
        return set.size();
    }
    
    int find(int x){
        if(par[x]==x) 
            return x;
        par[x]=find(par[x]);
        return par[x];
    }    
    
    void union(int x, int y){
        int px = find(x);
        int py = find(y);
        par[px]= par[py];
    }
    
}
```

### union find in examples
1. lc130 surrounded regions


## Dijkstra’s Algorithm finding Shortest Path

- single source
- positive edges only
- Time Complexity: O(V^2 )
- Auxiliary Space: O(V)

### algo

1. Mark the source node with a current distance of 0 and the rest with infinity.

2. Set the non-visited node with the smallest current distance as the current node.

3. For each neighbor, N of the current node adds the current distance of the adjacent node with the weight of the edge connecting 0->1. If it is smaller than the current distance of Node, set it as the new current distance of N.

4. Mark the current node 1 as visited.

5. Go to step 2 if there are any nodes are unvisited.


```java
import java.io.*;
import java.lang.*;
import java.util.*;

class ShortestPath {
    // A utility function to find the vertex with minimum
    // distance value, from the set of vertices not yet
    // included in shortest path tree

    static final int V = 9;
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed distance
    // array
    void printSolution(int dist[])
    {
        System.out.println(
            "Vertex \t\t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }


    void dijkstra(int graph[][], int src)
    {
        int dist[] = new int[V]; // The output array.
                                 // dist[i] will hold the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in
        // shortest path tree or shortest distance from src
        // to i is finalized
        Boolean sptSet[] = new Boolean[V];

        // Initialize all distances as INFINITE and stpSet[]
        // as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set
            // of vertices not yet processed. u is always
            // equal to src in first iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet,
                // there is an edge from u to v, and total
                // weight of path from src to v through u is
                // smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v] != 0
                    && dist[u] != Integer.MAX_VALUE
                    && dist[u] + graph[u][v] < dist[v])

                    dist[v] = dist[u] + graph[u][v];
        }

        printSolution(dist);
    }

    public static void main(String[] args) {
        int graph[][]
            = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                            { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                            { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                            { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                            { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                            { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                            { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
                            { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                            { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        ShortestPath t = new ShortestPath();

        t.dijkstra(graph, 0);
    }
}
```

### Limitation of Dijkstra’s Algorithm:

Since, we need to find the single source shortest path, we might initially think of using Dijkstra’s algorithm. However, Dijkstra is not suitable when the graph consists of negative edges. The reason is, it doesn’t revisit those nodes which have already been marked as visited. If a shorter path exists through a longer route with negative edges, Dijkstra’s algorithm will fail to handle it.


## Bellman–Ford Algorithm
- work for finding shortest paths in a graph
- work with weighted graph with negative edges!
- able to detect negative cycles
- complexity:  O(V*E) Time and O(V) Space
- 

### Algorithm: 

Relaxation means updating the shortest distance to a node if a shorter path is found through another node. For an edge (u, v) with weight w:

If going through u gives a shorter path to v from the source node (i.e., distance[v] > distance[u] + w), we update the distance[v] as distance[u] + w.

In the bellman-ford algorithm, this process is repeated (V – 1) times for all the edges.


### Detection of a Negative Weight Cycle

After running the Bellman-Ford algorithm, checking all edges in a graph V-1 times, all the shortest distances are found.

But, if the graph contains negative cycles, and we go one more round checking all edges, we will find at least one shorter distance in this last round, right?

So to detect negative cycles in the Bellman-Ford algorithm, after checking all edges V-1
times, we just need to check all edges **one more time**, and if we find a shorter distance this last time, we can conclude that a negative cycle must exist.


```java
// Java program to find single source shortest path using 
// Bellman-Ford algorithm

import java.util.Arrays;

class GfG {
    static int[] bellmanFord(int V, int[][] edges, int src) {
        
        // Initially distance from source to all other vertices 
        // is not known(Infinite).
        int[] dist = new int[V];
        Arrays.fill(dist, (int)1e8);
        dist[src] = 0;

        // Relaxation of all the edges V times, not (V - 1) as we  
        // need one additional relaxation to detect negative cycle
        for (int i = 0; i < V; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];
                if (dist[u] != 1e8 && dist[u] + wt < dist[v]) {
                    
                    // If this is the Vth relaxation, then there is
                    // a negative cycle
                    if (i == V - 1)
                        return new int[]{-1};
                    
                    // Update shortest distance to node v
                    dist[v] = dist[u] + wt;
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int V = 5;
        int[][] edges = new int[][] {
            {1, 3, 2},
            {4, 3, -1},
            {2, 4, 1},
            {1, 2, 1},
            {0, 1, 5}
        };

        int src = 0;
        int[] ans = bellmanFord(V, edges, src);
        for (int dist : ans) 
            System.out.print(dist + " ");
    }
}
```








