787. Cheapest Flights Within K Stops - Medium

There are n cities connected by some number of flights. You are given an array flights where 
flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with 
cost pricei.
You are also given three integers src, dst, and k, return the cheapest price from src to dst with 
at most k stops. If there is no such route, return -1.




0 [-1][100][500]
1 [-1][-1][100]
2 [-1][-1][-1]

Shortest
0 0
1 100
2 200
count 
0 0
1 1
2 2

2
K = 1


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	






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


===================================================================================================
class Solution {
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
HashMap<Integer, List<int[]>> fMap = new HashMap<>();

    for (int[] f : flights) {
        fMap.putIfAbsent(f[0], new ArrayList<>());
        fMap.get(f[0]).add(new int[]{f[1], f[2]});
    }
    
    PriorityQueue<int []> queue = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
    queue.offer(new int[]{0, src, k + 1});
    HashMap<Integer, Integer> visited = new HashMap<>();
    
    while (!queue.isEmpty()) {
        int[] e = queue.poll();
        int cost = e[0], steps = e[2], cur = e[1];
        if(visited.containsKey(cur) && visited.get(cur)>=steps) // avoid TLE
            continue;
        visited.put(cur, steps);
        
        if (cur == dst) return e[0];
        if (steps > 0) {
            List<int[]> list = fMap.get(cur);
            if (list == null || list.isEmpty()) continue;
            for (int[] f : list) {
                queue.offer(new int[]{cost + f[1], f[0], steps - 1});
            }
        }
    }
    return -1;
}



===================================================================================================


Public class Graph {
	Int[][] adjacencyList;
	Public Graph(int V) {
		adjacencyList = new int[V][V];
		For (int i = 0; i < V; i++) {
			Arrays.fill(adjacencyList[i], -1);
		}
	}

	Public void addEdge(int source, int destination, int dist) {
		this.adjacencyList[source][destination] = dist;
	}
}

Public int getDist(int[][] flights, int source, int dest, int k, int n) {
	If (source == dest) {
		Return 0;
	}

	If (flights == null | flights.length == 0 || flights[0].length == 0 || k < 0 || n == 0) {
		Return -1;
	}

	Graph g = new Graph(n);
	For (int i = 0; i < flights.length; i++) {
		g.addEdge(flights[i][0], flights[i][1], flights[i][2]);
	}

	Map<Integer, Integer> shortest = new HashMap<>();
	Map<Integer, Integer> stops = new HashMap<>();
	Queue<Integer> queue = new LinkedList<>();
	shortest.put(source, 0);
	stops.put(source, 0);
	queue.add(source);
	While (!q.isEmpty()) {
		Int curr = queue.remove();
		Int stop = stops.get(curr);
		Int cost = shortest.get(curr);
		If (stop < k) {
			For (int i = 0; i < n; i++) {
				If (g.adjacencyList[i] >= 0) {
					//neighbor found
					Int Shortest_dist；
					If (shortest.containsKey(i)) {
						Shortest_dist = Math.min(shortest.get(i), cost + g.adjacencyList[i]);
						If (shortest.get(i) > cost + g.adjacencyList[i]) {
							queue.add(i);
						}
					} else {
						Shortest_dist = cost + g.adjacencyList[i];
						queue.add(i);
					}
					shortest.put(i, Shortest_dist);
					stop.put(i, stop + 1);
				}
			}
		}
	}
	Return shortest.containsKey(dest) ? shortest.get(dest) : -1;
}



