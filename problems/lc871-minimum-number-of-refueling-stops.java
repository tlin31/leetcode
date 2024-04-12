871. Minimum Number of Refueling Stops - Hard


A car travels from a starting position to a destination which is target miles east of the starting
position.

Along the way, there are gas stations.  Each station[i] represents a gas station that is station[i][0]
miles east of the starting position, and has station[i][1] liters of gas.

The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.  
It uses 1 liter of gas per 1 mile that it drives.

When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station 
into the car.

What is the least number of refueling stops the car must make in order to reach its destination?  If it 
cannot reach the destination, return -1.

Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.  If the car 
reaches the destination with 0 fuel left, it is still considered to have arrived.

 

Example 1:

Input: target = 1, startFuel = 1, stations = []
Output: 0
Explanation: We can reach the target without refueling.


Example 2:

Input: target = 100, startFuel = 1, stations = [[10,100]]
Output: -1
Explanation: We cant reach the target (or even the first gas station).


Example 3:

Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
Output: 2
Explanation: 
We start with 10 liters of fuel.
We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
We made 2 refueling stops along the way, so we return 2.
 

Note:

1 <= target, startFuel, stations[i][1] <= 10^9
0 <= stations.length <= 500
0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target


******************************************************
key:
	- Greedy, DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Priority queue + Greedy


Stats:

	- O(n* log n)
	- 




Method:

	- When driving past a gas station, let's remember the amount of fuel it contained. We don't need 
	  to decide yet whether to fuel up here or not - for example, there could be a bigger gas station 
	  up ahead that we would rather refuel at.

	  When we run out of fuel before reaching the next station, we will retroactively fuel up: greedily
	  choosing the largest gas stations first.

	  This is guaranteed to succeed because we drive the largest distance possible before each 
	  refueling stop, and therefore have the largest choice of gas stations to (retroactively) stop at.

Algorithm

	i is the index of next stops to refuel.
	res is the times that we have refeuled.
	pq is a priority queue that we store all available gas.

	We initial res = 0 and in every loop:

	We add all reachable stop to priority queue.
	We pop out the largest gas from pq and refeul once.
	If we cant refuel, means that we can not go forward and return -1

class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {

    	// max PQ
        Queue<Integer> queue = new PriorityQueue<>((a,b) -> b-a);
        long dist = startFuel;
        int res = 0;
        int idx = 0;
        while (true) {
            while (idx < stations.length && stations[idx][0] <= dist) {
                queue.offer(stations[idx][1]);
                idx++;
            }
            
            if (dist >= target) 
            	return res;
            if (queue.isEmpty()) 
            	return -1;
            dist += queue.poll();
            res++;
        }
        
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- O(N^2)
	- 


Method:

	-	dp[t] means the furthest distance that we can get with t times of refueling.

		So for every station s[i],
		if the current distance dp[t] >= s[i][0], we can refuel:
		dp[t + 1] = max(dp[t + 1], dp[t] + s[i][1])

		In the end, we will return the first t with dp[i] >= target,
		otherwise we will return -1.
	-	



class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int len = stations.length;
        long[] dp = new long[len + 1];
        dp[0] = startFuel;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j > 0 && dp[j - 1] >= stations[i][0]; j--) {
                dp[j] = Math.max(dp[j], dp[j - 1] + stations[i][1]);
            }
        }
        for (int i = 0; i <= len; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
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

