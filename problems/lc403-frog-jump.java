403. Frog Jump - Hard

A frog is crossing a river. The river is divided into x units and at each unit there may or may not
exist a stone. The frog can jump on a stone, but it must not jump into the water.

Given a list of stones positions (in units) in sorted ascending order, determine if the frog is 
able to cross the river by landing on the last stone. Initially, the frog is on the first stone 
and assume the first jump must be 1 unit.

If the frogs last jump was k units, then its next jump must be either k - 1, k, or k + 1 units. 

Note that the frog can only jump in the forward direction.

Note:

The number of stones is ≥ 2 and is < 1,100.
Each stone's position will be a non-negative integer < 231.
The first stone's position is always 0.


Example 1:
[0,1,3,5,6,8,12,17]
0  1 2 3 4 5 6  7  (index)

There are a total of 8 stones.
The first stone at the 0th unit, second stone at the 1st unit,
third stone at the 3rd unit, and so on...
The last stone at the 17th unit.

Return true. The frog can jump to the last stone by jumping 
1 unit to the 2nd stone, then 2 units to the 3rd stone, then 
2 units to the 4th stone, then 3 units to the 6th stone, 
4 units to the 7th stone, and 5 units to the 8th stone.

Example 2:

[0,1,2,3,4,8,9,11]

Return false. There is no way to jump to the last stone as 
the gap between the 5th and 6th stone is too large.


******************************************************
key:
	- dfs or DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	- 1) Using a HashSet to store all the positions of the stones. So when you are trying to jump 
	     to a position, you will know whether there is a stone at that position or not.
	- 2) Starting from the first valid position (the second stone if it is 1), you try to jump as 
	     far as possible. At each position, if you jumped x steps to this position, the next possible positions are (position + x - 1, position + x, position + x + 1). 
	     If any of them is the last stones position, then you can return true. 
	     If not, use DFS from the longest jump first.
	- 3) This path finding process can be terminated much earlier if there are two stones that are 
	     too far away.

Stats:

	- Time complexity : O(n^3)
	- Space complexity : O(n^2)



	// main function
 	public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) 
        	return false;

        int n = stones.length;
        if (n == 1) {return true;}
        if (stones[1] != 1) {return false;}
        if (n == 2) {return true;}

        int last = stones[n - 1];

        // add ONLY reachable stones, filter once here
        HashSet<Integer> hs = new HashSet();
        for (int i = 0; i < n; i++) {

        	// the jump from i-1 to i is at most (stones[i-1] - 1) + 1
            if (i >= 3 && stones[i] - stones[i-1] > stones[i-1]) 
            	return false;

            hs.add(stones[i]);
        }

        return canReach(hs, last, 1, 1);
    }
    
    private boolean canReach(HashSet<Integer> hs, int last, int pos, int jump) {
    	// if next jump from current pos can reach the last stone
        if (pos + jump - 1 == last || pos + jump == last || pos + jump + 1 == last) {
            return true;
        }

        // start checking from the furthest jump, because most likely
        if (hs.contains(pos + jump + 1)) {
            if (canReach(hs, last, pos + jump + 1, jump + 1)) 
            	return true;
        }

        if (hs.contains(pos + jump)) {
            if (canReach(hs, last, pos + jump, jump)) 
            	return true;
        }

        if (jump > 1 && hs.contains(pos + jump - 1)) {
            if (canReach(hs, last, pos + jump - 1, jump - 1)) 
            	return true;
        }
        return false;
    }

=======================================================================================================
method 2: DP

Method:

	                +----+    +----+        +----+     +----+       
	stone:          | S1 |    | S2 |        | S3 |     | S4 | 
	            ____|____|____|____|________|____|_____|____|____________
	           ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
	position:"         0         1             3          5             "        

	jump size:         1     [0, 1, 2]     [1, 2, 3]

	- Suppose we want to know if the frog can reach stone 2 (S2),& the frog must come from S1, 
			dist(S1->S2) = 1 - 0 = 1, 
	  and we already know the frog is able to make a jump of size 1 at S1.
	  Hence, the frog is able to reach S2, and the next jump would be 0, 1 or 2 units.

	  Next, we want to know if the frog can reach stone 3 (S3),frog must be at  S1 or S2 before
		1) If the frog comes from S1: 
			we know dist(S1->S3) = 3 - 0 = 3, and we know frog cant make a jump of size 3 at S1.
			So it is not possible the frog can jump from S1 to S3.

		2) If the frog comes from S2: 
			we know dist(S2->S3) = 3 - 1 = 2, and we know frog could make a jump of size 2 at S2.
			Hence, the frog is able to reach S3, and the next jump would be 1, 2 or 3 units.

Exapme 1:
            
	index:        0   1   2   3   4   5   6   7 
	            +---+---+---+---+---+---+---+---+
	stone pos:  | 0 | 1 | 3 | 5 | 6 | 8 | 12| 17|
	            +---+---+---+---+---+---+---+---+
	k:          | 1 | 0 | 1 | 1 | 0 | 1 | 3 | 5 |
	            |   | 1 | 2 | 2 | 1 | 2 | 4 | 6 |
	            |   | 2 | 3 | 3 | 2 | 3 | 5 | 7 |
	            |   |   |   |   | 3 | 4 |   |   |
	            |   |   |   |   | 4 |   |   |   |
	            |   |   |   |   |   |   |   |   |

Method:
	- let dp(i) denote a set containing all next jump size at stone i
	- Recurrence:
		for any j < i,
		dist = stones[i] - stones[j];
		if dist is in dp(j):
		    put dist - 1, dist, dist + 1 into dp(i). 

Optimization:
	Given: 
	1. The number of stones is ≥ 2 and is < 1,100. 
	2. The frog is on the first stone and assume the first jump must be 1 unit.
	3. If the frogs last jump was k units, then its next jump must be either k - 1, k, or k + 1 units,

	The maximum jump size the frog can make at each stone if possible is shown as followings: 
	stone:      0, 1, 2, 3, 4, 5
	jump size:  1, 2, 3, 4, 5, 6 (suppose frog made jump with size k + 1 at each stone)

	So instead of creating a HashSet for lookup for each stone, we can create a boolean array with 
	  size of N + 1 (N is the number of stones). Like in the given example, at stone 2 the next jump 
	  could be 1, 2, 3, use a bool array to represent this like

		index:    0  1  2  3  4  5  6  7  ...
		         [0, 1, 1, 1, 0, 0, 0, 0, ...]
		index is jump size, boolean value represents if the frog can make this jump.

	Then, the 2D array will be something like below.

index:        0   1   2   3   4   5   6   7 
            +---+---+---+---+---+---+---+---+
stone pos:  | 0 | 1 | 3 | 5 | 6 | 8 | 12| 17|
            +---+---+---+---+---+---+---+---+
k:        0 | 0 | 1 | 0 | 0 | 1 | 0 | 0 | 0 |
          1 | 1 | 1 | 1 | 1 | 1 | 1 | 0 | 0 |
          2 | 0 | 1 | 1 | 1 | 1 | 1 | 0 | 0 |
          3 | 0 | 0 | 1 | 1 | 1 | 1 | 1 | 0 |
          4 | 0 | 0 | 0 | 0 | 1 | 1 | 1 | 0 |
          5 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 1 |
          6 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 |
          7 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 |

Algorithm:
	// Sub-problem and state:
	let dp[i][j] denote at stone i, the frog can or cannot make jump of size j

	// Recurrence relation:
	for any j < i,
		dist = stones[i] - stones[j];
		if dp[j][dist]:
		    dp[i][dist - 1] = ture
		    dp[i][dist] = ture
		    dp[i][dist + 1] = ture


Stats:

	- O(n^2)
	- O(n^2)

class Solution {
    public boolean canCross(int[] stones) {
        int N = stones.length;
        boolean[][] dp = new boolean[N][N + 1];
        dp[0][1] = true;
        
        for(int i = 1; i < N; ++i){
        	// previous stones
            for(int j = 0; j < i; ++j){
                int diff = stones[i] - stones[j];

                // dp[j][diff] means stone j is reachable!
                if(diff < 0 || diff > N || !dp[j][diff]) 
                	continue;

                dp[i][diff] = true;
                if(diff - 1 >= 0) 
                	dp[i][diff - 1] = true;
                if(diff + 1 <= N) 
                	dp[i][diff + 1] = true;

                // reach the last stone!
                if(i == N - 1) 
                	return true;
            }
        }

        return false;
    }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



