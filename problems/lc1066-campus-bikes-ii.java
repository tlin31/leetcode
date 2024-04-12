1066. Campus Bikes II - Medium



On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and 
bike is a 2D coordinate on this grid.

We assign one unique bike to each worker so that the sum of the Manhattan distances between each 
worker and their assigned bike is minimized.

The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.

Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.

 

Example 1:

Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
Output: 6
Explanation: 
We assign bike 0 to worker 0, bike 1 to worker 1. The Manhattan distance of both assignments is 3, 
so the output is 6.


Example 2:

Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
Output: 4
Explanation: 
We first assign bike 0 to worker 0, then assign bike 1 to worker 1 or worker 2, bike 2 to worker 2 
or worker 1. Both assignments lead to sum of the Manhattan distances as 4.
 

Note:

0 <= workers[i][0], workers[i][1], bikes[i][0], bikes[i][1] < 1000
All worker and bike locations are distinct.
1 <= workers.length <= bikes.length <= 10


******************************************************
key:
	- Dijakstra & DFS
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:  Priority queue & dijkstra


Stats:

	- 
	- 


Method:

	- use hashset to store seen combinations --> can skip if seen this mask
      b/c this is a PQ - and lower cost has already been seen with this exact mask 
      (i.e., those bikes used in some order), then there is no point to consider a higher cost one 
	-	




class Solution {

    static class Node {
        int worker;
        int mask;
        int cost;
        public Node(int w,int m,int cost){
            this.worker = w;
            this.mask = m;
            this.cost = cost;
        }
    }

    public int assignBikes(int[][] workers, int[][] bikes) {

    	// want lowest cost first
        Queue<Node> pq = new PriorityQueue<>(1,(a,b)->(a.cost-b.cost));
        Set<String> seen = new HashSet<>();
        pq.offer(new Node(0,0,0));
        while (!pq.isEmpty()){
            Node curr = pq.poll();
            String key = "$"+curr.worker+"$"+curr.mask;

            if (seen.contains(key))
                continue;

            seen.add(key);

            // all workers have a bike if this is true
            if (curr.worker == workers.length)
                return curr.cost;

            // scan all bikes - and create new nodes into the PQ for next worker.
            for(int j = 0; j < bikes.length; j++){

            	// this bike is not processed
                if ( (curr.mask & (1<<j)) == 0){
                    pq.offer( new Node(curr.worker+1, curr.mask | (1 << j), 
                                       curr.cost + getDist(bikes[j], workers[curr.worker]) ));
                }
            }
        }
        return -1;
    }
    private int getDist(int[] bikePosition,int[] workerPosition){
        return Math.abs(bikePosition[0]-workerPosition[0]) + Math.abs(bikePosition[1]-workerPosition[1]);
    }

}





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

   def assignBikes(self, workers, bikes):
        def dis(i, j):
            return abs(workers[i][0] - bikes[j][0]) + abs(workers[i][1] - bikes[j][1])
        
        h = [[0, 0, 0]]
        seen = set()
        while True:
            cost, i, taken = heapq.heappop(h)
            if (i, taken) in seen: 
            	continue
            seen.add((i, taken))
            if i == len(workers):
                return cost
            for j in xrange(len(bikes)):
                if taken & (1 << j) == 0:
                    heapq.heappush(h, [cost + dis(i, j), i + 1, taken | (1 << j)])


=======================================================================================================
method 2: DFS

Stats:

	- 
	- 


Method:

	-	
	-	





class Solution {
    public int assignBikes(int[][] workers, int[][] bikes) {
        int[] dp = new int[1 << bikes.length];
        int res = dfs(workers, bikes, 0, 0, dp);
        return res;
    }
    
    private int dfs(int[][] workers, int[][] bikes, int used, int count, int[] dp) {
        if (count == workers.length) return 0;
        if (dp[used] > 0) return dp[used];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < bikes.length; i++) {
            if ((used & (1 << i)) == 0) {
                used |= (1 << i); //set i th bit
                min = Math.min(min, dist(workers[count], bikes[i]) 
                	 				+ dfs(workers, bikes, used, count+1, dp));
                used &= ~(1 << i); //unset i th bit
            }
            
        }
        return dp[used] = min;
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

