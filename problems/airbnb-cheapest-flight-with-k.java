Minimum Cost with At Most K Stops

Given a flight itinerary consisting of starting city, destination city, and ticket price (2d list) 
- find the optimal price flight path to get from start to destination. (A variation of Dynamic 
Programming Shortest Path) Output list of strings representing a page of hostings given a list of 
CSV strings.


这道题给了我们一些航班信息，包括出发地，目的地，和价格，然后又给了我们起始位置和终止位置，说是最多能转K次机，
让我们求出最便宜的航班价格。

首先我们要建立这个图，选取的数据结构就是邻接链表的形式，具体来说就是建立每个结点和其所有能到达的结点的集合之间的映射，
然后就是用DFS来遍历这个图了，用变量cur表示当前遍历到的结点序号，还是当前剩余的转机次数K，访问过的结点集合visited，
当前累计的价格out，已经全局的最便宜价格res。

在递归函数中，首先判断如果当前cur为目标结点dst，那么结果res赋值为out，并直接返回。你可能会纳闷为啥不是取二者中较小
值更新结果res，而是直接赋值呢？原因是我们之后做了剪枝处理，使得out一定会小于结果res。然后判断如果K小于0，说明超过转
机次数了，直接返回。然后就是遍历当前结点cur能到达的所有结点了，对于遍历到的结点，首先判断如果当前结点已经访问过了，
直接跳过。或者是当前价格out加上到达这个结点需要的价格之和大于结果res的话，那么直接跳过。

这个剪枝能极大的提高效率，是压线过OJ的首要功臣。之后就是标记结点访问，调用递归函数，以及还原结点状态的常规操作了，


/*
Assumption:
1. The given input is a list of list, each list contains one flight
information as ["A", "B", "100"]
   Also we know the start position and the end position given as String
   Given a integer represents the number of stop we can have
2. return the minimum cost from start to end, if can not reach, return -1
3. The cost >= 0, that is no negative cost
4. k < 1000

Approach: DFS
So, based on our assumption and we know k should not be too big in real
life. The structure of the graph will looks more wide than tall.
So, rather than BFS, we can choose DFS to solve this problem.
Let me go through an example here, let's say we have a graph like this:
Graph:
      A
     / \
    B ->C -> D

Recursion Tree:
0        A
       / |
1     B  C
     /  |
2   C   D
    |
3   D

Also, to reduce the unnecessary search, we can do some memorization during
DFS. We can use a map to store the min cost and min stop for a node so far.
If later path comes, if both cost and stop greater than the value in maps,
we should not go further.

Time: O(V^(k+1))
	  In the worst case, each level we have n flight, we can go k levels

Space: O(V + E + k + 1)
	   O(k) for the call stack, O(V + E) to store the path information
=======================
follow up:
1. DFS vs. BFS, for time complexity, BFS is similar with DFS with proper
pruning. But the space complexity, BFS is much larger, the size of queue
can grow very fast
 */

import java.util.*;

public class ACheapestFlight {

    public static void main(String[] args) {
        DFS sol = new DFS();
        List<List<String>> flights = new ArrayList<>();
        flights.add(Arrays.asList("A", "C", "300"));
        flights.add(Arrays.asList("A", "B", "100"));
        flights.add(Arrays.asList("B", "C", "100"));
        flights.add(Arrays.asList("A", "D", "700"));
        flights.add(Arrays.asList("C", "D", "200"));
        flights.add(Arrays.asList("D", "B", "2"));
        System.out.print(sol.findMinCost(flights, "A", "C", 1));
        System.out.println(sol.getPath());
    }
}

class DFS {

    private int minCost = Integer.MAX_VALUE;
    private List<String> minPath;
    private Map<String, Integer> costMap;  // this two memorization hash map must work together
    private Map<String, Integer> stopMap;
    private Map<String, Map<String, Integer>> map;

    public int findMinCost(List<List<String>> flights, String from, String to, int k) {

    	// constructor, initialize new.
        this.map = new HashMap<>();
        this.costMap = new HashMap<>();
        this.stopMap = new HashMap<>();
        for (List<String> flight : flights) {
            map.putIfAbsent(flight.get(0), new HashMap<>());
            map.putIfAbsent(flight.get(1), new HashMap<>());
            map.get(flight.get(0)).put(flight.get(1), Integer.parseInt(flight.get(2)));
        }

        for (String city : map.keySet()) {
            this.costMap.put(city, Integer.MAX_VALUE);
            this.stopMap.put(city, Integer.MAX_VALUE);
        }

        dfs(new HashSet<>(), new ArrayList<>(), from, to, k,-1, 0);
        return minCost == Integer.MAX_VALUE ? -1 : this.minCost;
    }

    private void dfs(Set<String> visited, List<String> curPath, String cur, String to, 
    	int k, int stop,  int cost) {

        if (stop > k || visited.contains(cur)) {
            return;
        }
        visited.add(cur);
        curPath.add(cur);

        //if found path
        if (cur.equals(to)) {
            if (cost < minCost) {
                this.minPath = new ArrayList<>(curPath);
                this.minCost = cost;
            }
            curPath.remove(curPath.size() - 1);
            visited.remove(cur);
            return;
        }

        for (Map.Entry<String, Integer> entry : map.get(cur).entrySet()) {
            int newCost = cost + entry.getValue();
            int newStop = stop + 1;
            String next = entry.getKey();
            if (newCost >= costMap.get(next) && newStop >= stopMap.get(next)) continue;
            if (newCost < costMap.get(next) && newStop < stopMap.get(next)) {
                costMap.put(next, newCost);
                stopMap.put(next, newStop);
            }
            dfs(visited, curPath, next, to, k, newStop, newCost);
        }
        curPath.remove(curPath.size() - 1);
        visited.remove(cur);
    }

    public List<String> getPath() {
        return this.minPath;
    }
}

=======================================================================================================

DFS version 2：

	- in map, store: (key = 0, value = Arraylist{[1,100], [2,500]})
	[0,1,100], [0,2,500]


	int ans_dfs;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K)
    {
        ans_dfs = Integer.MAX_VALUE;
        Map<Integer,List<int[]>> map = new HashMap<>();
        for(int[] i:flights)
        {
            map.putIfAbsent(i[0],new ArrayList<>());
            map.get(i[0]).add(new int[]{i[1],i[2]});
        }

        // note!!! here is k+1, because we add source to it!
        dfs(map,src,dst,K+1,0);

        return ans_dfs == Integer.MAX_VALUE?-1:ans_dfs;
    }

    public void dfs(Map<Integer,List<int[]>> map, int src, int dst, int k, int cost)
    {
    	//edge case
        if(k < 0)
            return;

        // update ans_dfs
        if(src == dst){
            ans_dfs = cost;
            return;
        }

        if(!map.containsKey(src))
            return;

        for(int[] i : map.get(src)){
        	//Pruning, check the sum of current price and next cost. If it's greater then the 
        	// ans so far, skip this round
            if(cost+i[1] > ans_dfs)               
                continue;

            // move further down a node, so now the source = end state
            dfs(map,i[0],dst,k-1,cost+i[1]);
        }
    }

====================================================================================================
Dijk version 2

Method:

 - The key difference with the classic Dijkstra algo is, we do not maintain the global optimal 
 	distance to each node, i.e. ignore below optimization:
		alt ← dist[u] + length(u, v)
if alt < dist[v]:
- Because there could be routes which their length is shorter but pass more stops, and those 
  routes do not necessarily constitute the best route in the end. To deal with this, rather than 
  maintain the optimal routes with 0..K stops for each node, the solution simply put all possible 
  routes into the priority queue, so that all of them has a chance to be processed. 

- And the solution simply returns the first qualified route, it is easy to prove this must be 
  the best route.


 public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

 		// prices store : (key = city, value = (destination, cost))
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            if (!prices.containsKey(f[0])) 
            	prices.put(f[0], new HashMap<>());
            prices.get(f[0]).put(f[1], f[2]);
        }

        // PQ that compares cost
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        pq.add(new int[] {0, src, k + 1});

        while (!pq.isEmpty()) {
            int[] top = pq.remove();
            int price = top[0];
            int city = top[1];
            int stops = top[2];

            if (city == dst) return price;

            if (stops > 0) {
            	// store all possible destinations from current node
                Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());

                for (int a : adj.keySet()) {
                    pq.add(new int[] {price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }
=======================================================================================================

BFS :

public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K)
    {
        Map<Integer,List<int[]>> map=new HashMap<>();
        for(int[] i:flights)
        {
            map.putIfAbsent(i[0],new ArrayList<>());
            map.get(i[0]).add(new int[]{i[1],i[2]});
        }

        // record # of stops
        int step=0;
        Queue<int[]> q=new LinkedList<>();
        q.offer(new int[]{src,0});

        int ans=Integer.MAX_VALUE;

        while(!q.isEmpty())
        {
        	// go by layer
            int size=q.size();
            for(int i=0;i<size;i++){
                int[] curr = q.poll();
                if(curr[0] == dst)
                    ans = Math.min(ans,curr[1]);

                if(!map.containsKey(curr[0]))
                    continue;

                for(int[] f : map.get(curr[0])){
                	//Pruning
                    if(curr[1]+f[1]>ans)      
                        continue;

                    q.offer(new int[]{f[0],curr[1]+f[1]});
                }
            }

            if(step++ > K)
                break;
        }
        return ans==Integer.MAX_VALUE?-1:ans;
    }


BellmanFord
 public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K)
    {
        int[] cost=new int[n];
        Arrays.fill(cost,Integer.MAX_VALUE);
        cost[src]=0;
        for(int i=0;i<=K;i++)
        {
            int[] temp= Arrays.copyOf(cost,n);
            for(int[] f: flights)
            {
                int curr=f[0],next=f[1],price=f[2];
                if(cost[curr]==Integer.MAX_VALUE)
                    continue;
                temp[next]=Math.min(temp[next],cost[curr]+price);
            }
            cost=temp;
        }
        return cost[dst]==Integer.MAX_VALUE?-1:cost[dst];
    }


//class BellmanFord {
//    Map<Integer, Map<String, String>> prev;
//
//        flights.add(Arrays.asList("S", "A", "8"));
//        flights.add(Arrays.asList("A", "B", "20"));
//        flights.add(Arrays.asList("S", "C", "5"));
//        flights.add(Arrays.asList("C", "D", "10"));
//        flights.add(Arrays.asList("D", "B", "3"));
//        flights.add(Arrays.asList("B", "E", "100"));
//        flights.add(Arrays.asList("E", "F", "20"));
//
//    public int findCheapestPrice(List<List<String>> flights, String src, String dst, int k) {
//        //follow up
//        prev = new HashMap<>();
//
//        //dist from src to each vertex
//        Map<String, Integer> costs = new HashMap<>();
//        for (List<String> flight : flights) {
//            costs.putIfAbsent(flight.get(0), Integer.MAX_VALUE);
//            costs.putIfAbsent(flight.get(1), Integer.MAX_VALUE);
//        }
//        if (!costs.containsKey(src) || !costs.containsKey(dst)) {
//            return -1;
//        }
//
//        costs.put(src, 0);
//
//        //do k + 1 iterations
//        for (int i = 0; i < k + 1; i++) {
//            //follow up
//            prev.put(i, new HashMap<>());
//
//            Map<String, Integer> curr = new HashMap<>();
//            for (String key : costs.keySet()) {
//                curr.put(key, costs.get(key));
//            }
//
//            for (List<String> flight : flights) {
//                String from = flight.get(0);
//                String to = flight.get(1);
//                int cost = Integer.parseInt(flight.get(2));
//                if (costs.get(from) == Integer.MAX_VALUE) {
//                    continue;
//                }
//                if (costs.get(from) + cost < curr.get(to)) {
//                    curr.put(to, costs.get(from) + cost);
//
//                    //follow up
//                    prev.get(i).put(to, from);
//                }
//            }
//            costs = curr;
//        }
//
//        if (costs.get(dst) != Integer.MAX_VALUE) {
//            //follow up
//            printPath(src, dst, k);
//
//            return costs.get(dst);
//        }
//        return -1;
//    }
//
//    public void printPath(String src, String dst, int k) {
//        List<String> path = new ArrayList<>();
//        while (k >= 0) {
//            if (prev.get(k).containsKey(dst)) {
//                path.add(dst);
//                dst = prev.get(k).get(dst);
//            }
//            k--;
//        }
//        path.add(src);
//        Collections.reverse(path);
//        System.out.print("Path is : ");
//        for (String s : path) {
//            System.out.print(s + " -> ");
//        }
//        System.out.println("done");
//
//    }
//} 

DP：

stats:	
	- Time Complexity : O(KN), k is stop and N is the number of cities



public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        //dp[i][j] denotes the cheapest price within i-1 stops, stop in j city
        long[][] dp = new long[K+2][n];

        //initialize dp
        for (long[] d : dp) 
        	Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][src] = 0;
        for (int i = 1; i < K+2; i++) {
            dp[i][src] = 0;
            for (int[] f : flights) {
                dp[i][f[1]] = Math.min(dp[i][f[1]], dp[i-1][f[0]] + f[2]);
            }
        }
        return dp[K+1][dst] == Integer.MAX_VALUE ? -1 : (int)dp[K+1][dst];
    }

