743. Network Delay Time - Medium

There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v 
is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? 

If it is impossible, return -1.


Example 1:

Input: times = [[2,1,1],[2,3,1],[3,4,1]], N = 4, K = 2
Output: 2
 

Note:

N will be in the range [1, 100].
K will be in the range [1, N].
The length of times will be in the range [1, 6000].
All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.

******************************************************
key:
	- Dijkstra --> 
	- Bellman-ford
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Dijkstra with priority queue and hashmap
	- hashmap stores with key = source node, value = (map <destination node, weight>)
	- PQ: int[a,b] = [distance, node]

stats:

	- Time complexity: O(Nlog(N) + E), Space complexity: O(N + E)
	- 

class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, Map<Integer,Integer>> map = new HashMap<>();

        for(int[] time : times){
            map.putIfAbsent(time[0], new HashMap<>());
            map.get(time[0]).put(time[1], time[2]);
        }
        
        //distance, node into pq
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> (a[0] - b[0]));
        
        pq.add(new int[]{0, K});
        
        boolean[] visited = new boolean[N+1];
        int res = 0;
        
        while(!pq.isEmpty()){
            int[] cur = pq.remove();
            int curNode = cur[1];
            int curDist = cur[0];
            if(visited[curNode]) 
            	continue;

            visited[curNode] = true;
            res = curDist;
            N--;
            if(N == 0) return res; // terminate here

            // map contains current source node
            if(map.containsKey(curNode)){

            	// next = curNode的neighbor
                for(int next : map.get(curNode).keySet()){

                	// relaxation
                	int newDist = curDist + map.get(curNode).get(next);
                    pq.add(new int[]{newDist, next});
                }
            }
        }
        return N == 0 ? res : -1;
            
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

Bellman-Ford algorithm
Time complexity: O(N*E), Space complexity: O(N)
public int networkDelayTime_BF(int[][] times, int N, int K) {
    double[] disTo = new double[N];
    Arrays.fill(disTo, Double.POSITIVE_INFINITY);
    disTo[K - 1] = 0;
    for (int i = 1; i < N; i++) {
        for (int[] edge : times) {
            int u = edge[0] - 1, v = edge[1] - 1, w = edge[2];
            disTo[v] = Math.min(disTo[v], disTo[u] + w);
        }
    }
    double res = Double.MIN_VALUE;
    for (double i: disTo) {
        res = Math.max(i, res);
    }
    return res == Double.POSITIVE_INFINITY ? -1 : (int) res;
}
=======================================================================================================
method 3:

method:

	- 
	- 

stats:

	- 
	- 



