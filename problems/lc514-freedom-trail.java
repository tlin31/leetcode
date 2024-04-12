514. Freedom Trail - Hard


In the video game Fallout 4, the quest "Road to Freedom" requires players to reach a metal dial 
called the "Freedom Trail Ring", and use the dial to spell a specific keyword in order to open 
the door.

Given a string ring, which represents the code engraved on the outer ring and another string key, 
which represents the keyword needs to be spelled. 

Q: find the minimum number of steps in order to spell all the characters in the keyword.

Initially, the first character of the ring is aligned at 12:00 direction. You need to spell all 
the characters in the string key one by one by rotating the ring clockwise or anticlockwise to 
make each character of the string key aligned at 12:00 direction and then by pressing the center 
button.

At the stage of rotating the ring to spell the key character key[i]:

	- You can rotate the ring clockwise or anticlockwise one place, which counts as 1 step. 
	  The final purpose of the rotation is to align one of the string ring's characters at the 
	  12:00 direction, where this character must equal to the character key[i].
	- If the character key[i] has been aligned at the 12:00 direction, you need to press the center 
	  button to spell, which also counts as 1 step. After the pressing, you could begin to spell 
	  the next character in the key (next stage), otherwise, you've finished all the spelling.



Example:
 
Input: ring = "godding", key = "gd"
Output: 4
Explanation:
For the first key character 'g', since it is already in place, we just need 1 step to spell this character. 
For the second key character 'd', we need to rotate the ring "godding" anticlockwise by two steps to make it become "ddinggo".
Also, we need 1 more step for spelling.
So the final output is 4.


Note:

Length of both ring and key will be in range 1 to 100.
There are only lowercase letters in both strings and might be some duplcate characters in both strings.
It is guaranteed that string key could always be spelled by rotating the string ring.


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
	-	

	- 	The dp is a 2D integer array, with height = the length of ring,  width = the length of key. 
	    DP[i][j] = spell the next character key[j], and at the same time the 12:00 aligns with the 
	               ring[i], then what is the minimum steps to spell the whole key start at key[j]. 

	    If we finish the DP array, then the answer is just DP[0][0], which means the minimum steps 
	    to spell the whole key start at key[0], if currently 12:00 aligns with the ring[0], and this 
	    is exactly the original problem. 

	    And dont forget to plus the length of key, which is the steps we need to push the button.


class Solution {
    public int findRotateSteps(String ring, String key) {
        int ringLen = ring.length();
        int keyLen = key.length();
        
        int[][] dp = new int[keyLen+1][ringLen];

        //Start by solving key[i:keyLen-1] -> smallest instance is key[keyLen-1:keyLen-1]
        for (int i = keyLen-1; i>= 0; i--){

            //Solve the problem for key[i:keyLen-1] and when the ring arrow points at index j.
            for (int j = 0; j < ringLen; j++){
                dp[i][j] = Integer.MAX_VALUE;

                //Try out every type of spin (by 0, 1, 2, 3, and choose the best choice)
                for (int k = 0; k < ringLen; k++){
                    if (ring.charAt(k) == key.charAt(i)){

                        //Using greedy logic that we should just use the min spin 
                        int diff = Math.abs(j-k);

                        //Choose the min of moving clockwise or counterclockwise
                        int step = Math.min(diff, ringLen-diff);

                        //dp[i+1][k] = Solve the subproblem from key[i+1: keyLen-1] 
                        // and while our pointer points to k now since we have rotated the dial there.
                        dp[i][j] = Math.min(dp[i][j], step + dp[i+1][k]);
                    }
                }
            }
        }
        
        //We will press the dial button m times in total no matter what.
        return dp[0][0] + keyLen; 
    }
}




~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

def findRotateSteps(self, ring, key):
        # the distance between two points (i, j) on the ring
        def dist(i, j):
            return min(abs(i - j), len(ring) - abs(i - j))
        # build the position list for each character in ring
        pos = {}
        for i, c in enumerate(ring):
            if c in pos: pos[c].append(i)
            else: pos[c] = [i]
        # the current possible state: {position of the ring: the cost}
        state = {0: 0}
        for c in key:
            next_state = {}
            for j in pos[c]:  # every possible target position
                next_state[j] = float('inf')
                for i in state:  # every possible start position
                    next_state[j] = min(next_state[j], dist(i, j) + state[i])
            state = next_state
        return min(state.values()) + len(key)

=======================================================================================================
method 2: DFS + memo

Stats:

	- 
	- 


Method:

	-  key: whether to move clockwise or anticlockwise. 
	-  If apply brute force, then for each position in key we have two options,
		Search for the character clockwise
		Search for the character anti-clockwise
	-  To find optimal answer we need to try both options and get minimum of them. 
	   Thus, we obtain dfs solution for the problem. 
	   The state is defined by position of the ring and the index of character in the key. 
	- map stores <ring+index, number of steps>


public class Solution {
    public int findRotateSteps(String ring, String key) {
           Map<String,Integer> map = new HashMap();
           return dfs(ring, key, 0, map);
    }
    
    public int dfs(String ring, String key, int index, Map<String,Integer> map){
        if(index == key.length()){
            return 0;
        }
    
        char c = key.charAt(index);
        String hashKey = ring + index;
        if(map.containsKey(hashKey)) 
        	return map.get(hashKey);
        
        int minSteps = Integer.MAX_VALUE;
        for(int i = 0; i < ring.length(); i ++){
            if(ring.charAt(i) == c){
                String s = ring.substring(i, ring.length()) + ring.substring(0, i);
                int steps = 1 + Math.min(i, ring.length() - i);

                // pass in new string s
                steps += dfs(s, key, index + 1, map);
                minSteps = Math.min(minSteps, steps);
            }
        }
        
        map.put(hashKey, minSteps);
        
        return minSteps;
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

