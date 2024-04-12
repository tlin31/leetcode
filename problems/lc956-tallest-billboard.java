956. Tallest Billboard - Hard

You are installing a billboard and want it to have the largest height.  The billboard will have two
steel supports, one on each side.  Each steel support must be an equal height.

You have a collection of rods which can be welded together.  For example, if you have rods of 
lengths 1, 2, and 3, you can weld them together to make a support of length 6.

Return the largest possible height of your billboard installation.  

If you cannot support the billboard, return 0.

 

Example 1:

Input: [1,2,3,6]
Output: 6
Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
Example 2:

Input: [1,2,3,4,5,6]
Output: 10
Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
Example 3:

Input: [1,2]
Output: 0
Explanation: The billboard cannot be supported, so we return 0.
 

Note:

0 <= rods.length <= 20
1 <= rods[i] <= 1000
The sum of rods is at most 5000.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: Memorization

Method:

	-  Lets assign rods to the left and right buckets that will represent final support beams once
	   joined together. In order to do so, imagine all rods in one single array. To represent that 
	   rod is in the left bucket, rod length will remain positive. To represent that rod is in the right bucket, we will store length as negative number. To represent that rod is discarded, it will be set to 0. 

	   sum of all elements in the array will represent the difference between left and right steel
	   supports. For example given rods = [1,2,3,6] one way to represent them is [1, -2, -3, 0] that 
	   means there is one rod in the left bucket (1) , and two rods in the right bucket (-2, -3). 
	   Difference between left and right steel supports in this case is 1-2-3+0 = -4 (right support 
	   beam is longer)

	   We need to find combination that gives us difference equal to zero. In other words, sum of all
	   elements in such representation is 0, that means length of left beam (positive lengths) is 
	   equal to length of the right beam (negative lengths)

Now, for each rod in initial array, we need to consider three cases:

		1. it is kept in left bucket, length represented as positive number
		2. it is moved to the right bucket, length represented as negative number
		3. it is discarded. Length is 0
	
	-  In other words, we need to keep track of all possible sums until now, and update all of them
	   with new rod. 
	   actual length associated with a given sum, use dictionary to look it up. 

	   		lookup[sum_until_now] = maximum_length_of_left_support


Stats:

	- Time: O(N * S), N is the length of rods, and S is the maximum of ∑rods[i].
	- Space: O(N * S)

class Solution {
    int NINF = Integer.MIN_VALUE / 3;
    Integer[][] memo;

    public int tallestBillboard(int[] rods) {
        int N = rods.length;
        // "memo[n][x]" will be stored at memo[n][5000+x]
        // Using Integer for default value null
        memo = new Integer[N][10001];
        return (int) dp(rods, 0, 5000);
    }

    public int dp(int[] rods, int i, int s) {
        if (i == rods.length) {
            return s == 5000 ? 0 : NINF;
        } else if (memo[i][s] != null) {
            return memo[i][s];
        } else {

        	// discard
            int ans = dp(rods, i+1, s);

            // in right bucket
            ans = Math.max(ans, dp(rods, i+1, s-rods[i]));

            // in left bucket
            ans = Math.max(ans, rods[i] + dp(rods, i+1, s+rods[i]));
            memo[i][s] = ans;
            return ans;
        }
    }
}


=======================================================================================================
method 2:


Stats:

	- O(NM), where we have N = rod.length <= 20, S = sum(rods) <= 5000, 
	  M = all possible sum = min(3^N, S)

Method:

	-	
	-	

dp[d] mean the maximum pair of sum we can get with pair difference d

If have a pair of sum (a, b) with a > b, then dp[a - b] = b

If we have dp[diff] = a,
it means we have a pair of sum (a, a + diff).
And this is the biggest pair with difference diff

Ex.
	dp is initializes with dp[0] = 0;

	Assume we have an init state like this
	------- y ------|----- d -----|
	------- y ------|

	case 1:
		If put x to tall side
		------- y ------|----- d -----|----- x -----|
		------- y ------|

		We update dp[d + x] = max(dp[d + x], y )

	case 2.1
		Put x to low side and d >= x:
		-------y------|----- d -----|
		-------y------|--- x ---|

		We update dp[d-x] = max(dp[d - x], y + x)

	case 2.2
		Put x to low side and d < x:
		------- y ------|----- d -----|
		------- y ------|------- x -------|
		We update dp[x - d] = max(dp[x - d], y + d)

		case 2.1 and case2.2 can merge into dp[abs(x - d)] = max(dp[abs(x - d)], y + min(d, x))


There are 3 ways to arrange a number: in the first group, in the second, not used.
	The number of difference will be less than 3^N.
	The only case to reach 3^N is when rod = [1,3,9,27,81...]


    public int tallestBillboard(int[] rods) {
        int[] dp = new int[5001];
        for (int d = 1; d < 5001; d++) dp[d] = -10000;
        for (int x : rods) {
            int[] cur = dp.clone();
            for (int d = 0; d + x < 5001; d++) {
                dp[d + x] = Math.max(dp[d + x], cur[d]);
                dp[Math.abs(d - x)] = Math.max(dp[Math.abs(d - x)], cur[d] + Math.min(d, x));
            }
        }
        return dp[0];
    }


Java, using HashMap:

    public int tallestBillboard(int[] rods) {
        Map<Integer, Integer> dp = new HashMap<>(), cur;
        dp.put(0, 0);
        for (int x : rods) {
            cur = new HashMap<>(dp);
            for (int d : cur.keySet()) {
                dp.put(d + x, Math.max(cur.get(d), dp.getOrDefault(x + d, 0)));
                dp.put(Math.abs(d - x), Math.max(cur.get(d) + Math.min(d, x), dp.getOrDefault(Math.abs(d - x), 0)));
            }
        }
        return dp.get(0);
    }

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 


public int tallestBillboard(int[] rods) {

    // dp's key = sum of left billboard - the sum of right billboard, 
    // the value represents the largest sum of left billboard
    Map<Integer, Integer> dp = new HashMap<>();
    
    //for difference between sides of 0, initialize largest height of left side to be 0
    dp.put(0, 0);
    
    //for each rod height
    for(int ele:rods){

        //create copy of current map so we can modify it while iterating over it
        Map<Integer, Integer> temp = new HashMap<>(dp);

        //for each difference in the map
        for(int dif:dp.keySet()){
            //add to left or right side
            //new max sum for that left billboard = :
            	//the max height for (current difference + rod height)
            	//OR the current difference's max + rod height
            temp.put(dif+ele, Math.max(temp.getOrDefault(dif+ele,0), dp.get(dif)+ele));
            temp.put(dif-ele, Math.max(temp.getOrDefault(dif-ele, 0), dp.get(dif)));
        }

        dp = temp;
    }

    //answer is the max height where the difference between and left and right heights is 0
    return dp.get(0); 
}
