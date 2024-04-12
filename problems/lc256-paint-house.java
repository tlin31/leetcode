256. Paint House - Easy

There are a row of n houses, each house can be painted with one of the three colors: red, blue 
or green. The cost of painting each house with a certain color is different. You have to paint 
all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. 
For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the 
cost of painting house 1 with color green, and so on... 

Q: Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Example:

Input: [[17,2,17],
		[16,16,5],
		[14,3,19]]

Output: 10
Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue. 
             Minimum cost: 2 + 5 + 3 = 10.


******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP,

Method:

	-	modify on original input
	-	bottom up, don't change the bottom row's value/cost
	- 	paintCurrentRed = min(paintPreviousGreen,paintPreviousBlue) + costs[i+1][0]
		Same for the green and blue situation. 
	-	And the initialization is set to costs[0]


Stats:

	- Time: O(n)
	- space: O(1)

class Solution {
    public int minCost(int[][] costs) {

        for (int n = costs.length - 2; n >= 0; n--) {

            // Total cost of painting the nth house red = the row beneath it
            costs[n][0] += Math.min(costs[n + 1][1], costs[n + 1][2]);

            // Total cost of painting the nth house green.
            costs[n][1] += Math.min(costs[n + 1][0], costs[n + 1][2]);

            // Total cost of painting the nth house blue.
            costs[n][2] += Math.min(costs[n + 1][0], costs[n + 1][1]);
        }

        if (costs.length == 0) 
        	return 0;   

        return Math.min(Math.min(costs[0][0], costs[0][1]), costs[0][2]);
    }
}

ex. [17,2,17],
	[16,16,5],
	[14,3,19]

start from the bottom:
	Modify row = 2: dont change
	Modify row = 1 --> [16 + min(3,19)		16 + min(14,19)		5 + min(14,3)]

=======================================================================================================
method 2:

Method:

	-	optimize space
	-	


Stats:

	- 
	- 




public class Solution {
	public int minCost(int[][] costs) {
	    if(costs.length==0) return 0;
	    int lastR = costs[0][0];
	    int lastG = costs[0][1];
	    int lastB = costs[0][2];
	    for(int i=1; i<costs.length; i++){
	        int curR = Math.min(lastG,lastB)+costs[i][0];
	        int curG = Math.min(lastR,lastB)+costs[i][1];
	        int curB = Math.min(lastR,lastG)+costs[i][2];
	        lastR = curR;
	        lastG = curG;
	        lastB = curB;
	    }
	    return Math.min(Math.min(lastR,lastG),lastB);
	}
}

=======================================================================================================
method 3:

Method:

	-	The code can handle n colors rather than 3.
	-	I am a guy don't like to mess up original input, so I create a new array called dp.


Stats:

	- 
	- 

public int minCost(int[][] costs) {
    if(costs.length == 0 || costs[0].length == 0)
        return 0;
    int m = costs.length, n = costs[0].length;
    
    //initialize dp matrix
    int[][] dp = new int[m][n];
    for(int j = 0; j < n; j++) 
        dp[0][j] = costs[0][j];
        
    //fill the dp matrix
    for(int i = 1; i < m; i++) {
        for(int j = 0; j < n; j++)
            dp[i][j] = costs[i][j] + findMinInOneRow(i - 1, j, dp);
    }
    
    return findMinInOneRow(m - 1, -1, dp);
}

private int findMinInOneRow(int i, int j, int[][] matrix) {
    int min = Integer.MAX_VALUE;
    for(int k = 0; k < matrix[0].length; k++) {
        if(k == j)
            continue;
        min = Math.min(min, matrix[i][k]);
    }
    return min;
}



