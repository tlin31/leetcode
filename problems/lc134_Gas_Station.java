134. Gas Station- Medium

There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith 
station to its next (i + 1)th station. You begin the journey with an empty tank at one of the 
gas stations.

Given two integer arrays gas and cost, return the starting gas station's index if you can travel 
around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, 
it is guaranteed to be unique

 

Example 1:

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.

Example 2:

Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
 

Constraints:

n == gas.length == cost.length
1 <= n <= 105
0 <= gas[i], cost[i] <= 104

******************************************************
key:
	- optimization: see in total/big picture
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	

Brute force: O(N^2)

		{
        int n = gas.length;
        for(int i = 0; i < n; i++){
            int totalFuel = 0;
            int stopCount = 0, j = i;
            
            while(stopCount < n){
            	//use mod because we maybe going around the circle now
                totalFuel += gas[j % n] - cost[j % n];
                if(totalFuel < 0) 
                	break; // whenever we reach -ve
                stopCount++;
                j++;
            }
            if(stopCount == n && totalFuel >= 0) return i; 
            // cover all the stops & our fuel left is 0 or more than that
        }
        return -1;


//IF we run out of fuel say at some i-th gas station. All the gas station between ith and starting 
//point are bad starting point as well.

//check 2 things: 1. whether at the end there're still positive oil left, if's negative, then no way
// 2. within each loop, if current balance is less than 0, can only start at next one.

Firstly we can start at index i, only when gas[i] - cost[i]>=0 because otherwise, we won't be able 
to reach the next station.

Also, we will try to have the maximum value of gas in our tank at a given index because having 
greater fuel maximizes chances of getting success(here success means completing one circular trip) 
for future.

So if we try to start at index i where gas[i]-cost[i]>=0 and we get to know that we are not able to
reach at index j (starting from i) , so it is not beneficial to check at indices (i+1,i+2,...,j-1) 
because if we start at indices (i+1,i+2,...j-1) it will result in less amount of gas in our car at 
station j which will definitely be a failure.

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int total_surplus = 0;
        int surplus = 0;
        int start = 0;
        
        for(int i = 0; i < n; i++){
            total_surplus += gas[i] - cost[i];
            surplus += gas[i] - cost[i];
            // if can't start at index i, then all past i-1, i-2,..0 is not working, so set
            // starting point to the next index
            if(surplus < 0){
                surplus = 0;
                start = i + 1;
            }
        }
        return (total_surplus < 0) ? -1 : start;
    }
}



