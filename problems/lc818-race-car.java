818. Race Car - Hard


Your car starts at position 0 and speed +1 on an infinite number line.  (Your car can go into negative
positions.)

Your car drives automatically according to a sequence of instructions A (accelerate) and R (reverse).

When you get an instruction "A", your car does the following: position += speed, speed *= 2.

When you get an instruction "R", your car does the following: if your speed is positive then speed = -1,
otherwise speed = 1.  (Your position stays the same.)

For example, after commands "AAR", your car goes to positions 0->1->3->3, and your speed goes to 1->2->4->-1.

Now for some target position, say the length of the shortest sequence of instructions to get there.


Example 1:

Input: 
target = 3
Output: 2
Explanation: 
The shortest instruction sequence is "AA".
Your position goes from 0->1->3.



Example 2:

Input: 
target = 6
Output: 5
Explanation: 
The shortest instruction sequence is "AAARA".
Your position goes from 0->1->3->7->7->6.
 

Note:

1 <= target <= 10000.




******************************************************
key:
	- BFS or DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:BFS (knapsack)


Stats:

	- 
	- 


Method:

	-	
	-  we can keep track of all the possible positions of the racecar after n instructions 
	   (n = 0, 1, 2, 3, 4, ...) and return the smallest n such that the target position is reached. 
	-  Naive BFS will run at O(2^n) since for each position we have 2 choices: 
		either accelerate or reverse. 
	-  use memo to remember each state, which is characterized by two integers: position & car speed. 
	-  However, the total number of unique states still blows up for large target positions (because 
	   the position and speed can grow unbounded), so we need further pruning of the search space.

public int racecar(int target) {
    Deque<int[]> queue = new LinkedList<>();

    // starts from position 0 with speed 1
    queue.offerLast(new int[] {0, 1}); 
    
    Set<String> visited = new HashSet<>();
    visited.add(0 + " " + 1);
    
    for (int level = 0; !queue.isEmpty(); level++) {
        for(int k = queue.size(); k > 0; k--) {

        	// cur[0] is position; cur[1] is speed
            int[] cur = queue.pollFirst();  
            
            if (cur[0] == target) {
                return level;
            }
            
             // accelerate instruction, update position, speed * 2
            int[] nxt = new int[] {cur[0] + cur[1], cur[1] << 1}; 
            String key = (nxt[0] + " " + nxt[1]);
            
            // if next position < target * 2, this is a valid next step
            if (!visited.contains(key) && nxt[0] > 0 && nxt[0] < (target << 1)) {
                queue.offerLast(nxt);
                visited.add(key);
            }
            

            // reverse
            if (cur[1] > 0)
            	int newSpeed = -1;
            else
            	int newSpeed = 1;

            nxt = new int[] {cur[0], newSpeed};  
            key = (nxt[0] + " " + nxt[1]);
            
            // add this in queue as well
            if (!visited.contains(key) && 0 < nxt[0] && nxt[0] < (target << 1)) {
                queue.offerLast(nxt);
                visited.add(key);
            }
        }
    }
    
    return -1;
}



=======================================================================================================
method 2: DP

Stats:

	- O(TlogT)
	- space: O(T + log T)


Method:
	- Note: 1 + 2 + 4 + ... 2^(n-1) = 2^n - 1

	-  3 possibilities:

		1. target = 2^n - 1, then we just keep accelerating, and it will take n steps --> A^n

		2. accelerate for n steps, pass the traget & trun around:

			ex. # = target, so it is in the range of last 2^(n-1):

			|----	2^n - 1 = n steps			----|

			1 | 2 | .... | 2^(n-2) | 	2^ (n-1) 	|
								   		 ...| 2 | 1 |

								   		| --- left--|		// turn around & come back

			|----- target --------------|	
							   		

			dp(target) = n + 1 + dp (left)
					   = Accelerate n times + 1 step to reverse + dp (left)

			left = 2^n - 1 - target

		3. accelerate n-1 steps, turn around and go backwards, then turn around and go forward again

			ex. 

			|----	2^(n-1) - 1 = n-1 steps		----|

			1 | 2 | .... | 2^(n-2) | 	2^ (n-2) 	| 		// turn around & go back
							 | 2^(m-1) | ...| 2 | 1 |		// turn around & go forward
							 | 1 | 2 | ...................|

							 | --- left-------------------|	

			| ---- target --------------------------------|


			Key: find the minimum m in {0, n-1}

			dp (target) = min{ (n+m+1) + dp(left)}
					 	= min{ accelerate n-1 steps + 1 step to turn + m steps backward + 1 step turn}
			left = target - (2^(n-1) - 1) + 2^m - 1

    
class Solution {
  	int[] dp = new int[10001];

    public int racecar(int t) {
        if (dp[t] > 0) 
        	return dp[t];

        int n = (int)(Math.log(t) / Math.log(2)) + 1;


	    // AA...A (nA) best case --> target = 2^n - 1
        if (1 << n == t + 1) 
        	dp[t] = n;
        
        else {
        	// AA...AR (nA + 1R) + dp(left) 
            dp[t] = racecar((1 << n) - 1 - t) + n + 1;
            
            // AA...ARA...AR (n-1A + 1R + mA + 1R) + dp(left) 
            for (int m = 0; m < n - 1; ++m){
                 int cur = (1 << (n - 1)) - (1 << m);
                 dp[t] = Math.min(dp[t], racecar(t - cur) + n + m + 1);
        	}

        }
        return dp[t];
    }
} 


=======================================================================================================
method 3:

Stats:

	- 

Time Complexity: O(n^2)

Space complexity: O(n)


	- 


Method:

	-	m[t][d] : min steps to reach t and facing d (0 = right, 1 = left)
	-	

class Solution {
  private static int[][] m;
  public int racecar(int target) {
    if (m == null) {
      final int kMaxT = 10000;
      m = new int[kMaxT + 1][2];
      for (int t = 1; t <= kMaxT; ++t) {
        int n = (int)Math.ceil(Math.log(t + 1) / Math.log(2));
        if (1 << n == t + 1) {
          m[t][0] = n;
          m[t][1] = n + 1;
          continue;
        }
        int l = (1 << n) - 1 - t;
        m[t][0] = n + 1 + Math.min(m[l][1], m[l][0] + 1);
        m[t][1] = n + 1 + Math.min(m[l][0], m[l][1] + 1);
        for (int i = 1; i < t; ++i) 
          for (int d = 0; d <= 1; d++)
            m[t][d] = Math.min(m[t][d], Math.min(
                m[i][0] + 2 + m[t - i][d],
                m[i][1] + 1 + m[t - i][d]));
      }
    } 
    return Math.min(m[target][0], m[target][1]);
  }
}

