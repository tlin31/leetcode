265. Paint House II - Hard


There are a row of n houses, each house can be painted with one of the k colors. The cost of painting 
each house with a certain color is different. You have to paint all the houses such that no two 
adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. For example,
costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 
with color 2, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Example:

Input: [[1,5,3],[2,9,4]]
Output: 5
Explanation: Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5; 
             Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5. 


Follow up:
Could you solve it in O(nk) runtime?



******************************************************
key:
	- DP, store last result min & second min
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP


Stats:

	- Time complexity : O(n * k ^ 2)
					   We iterate over each of the n⋅k cells. For each of the cells, we are finding 
					   the min of the k values in the row above, excluding the one that is in the 
					   same column. This operation is O(k). 
	- Space complexity : O(1) if done in-place, O(n⋅k) if input is copied.



Method:

	-  pick exactly one number from each row such that the sum of those numbers is minimized. 
	   Because 2 adjacent houses cannot be the same color, adjacent rows must be picked from 
	   different columns. 

	- iterate over the cells and determine what the cheapest way of getting to that cell is. 
	  We will work from top to bottom.



class Solution {

    public int minCostII(int[][] costs) {

        if (costs.length == 0) 
        	return 0;
        int k = costs[0].length;
        int n = costs.length;

        for (int house = 1; house < n; house++) {
            for (int color = 0; color < k; color++) {
                int min = Integer.MAX_VALUE;
                for (int previousColor = 0; previousColor < k; previousColor++) {
                   	// can't have 2 same color
                    if (color == previousColor) 
                    	continue;
                    min = Math.min(min, costs[house - 1][previousColor]);
                }
                costs[houseNumber][color] += min;
            }
        }

        // Find the minimum in the last row.
        int min = Integer.MAX_VALUE;
        for (int c : costs[n - 1]) {
            min = Math.min(min, c);
        }
        return min;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2: DP w/ optimized time --> observe min & 2nd minimum

Stats:

	- O(nk)
	- O(1) if modified input is ok


Method:

	-	When we are calculating the values for the second row, we're adding the minimum from the 
	    first row onto them. The only cell we can't do this for is the one that was directly below 
	    the minimum, as this would break the adjacency rule. 
	    For this one, it makes sense to add the second minimum.
	-	

class Solution {

    public int minCostII(int[][] costs) {

        if (costs.length == 0) return 0;
        int k = costs[0].length;
        int n = costs.length;


        // we need to determine the 2 lowest costs of the first row.
        // & the color of the lowest. 
        int prevMin = -1; 
        int prevSecondMin = -1; 
        int prevMinColor = -1;

        // loop on the first row to initiate the 3 var above
        for (int color = 0; color < k; color++) {
            int cost = costs[0][color];
            if (prevMin == -1 || cost < prevMin) {
                prevSecondMin = prevMin;
                prevMinColor = color;
                prevMin = cost;
            } else if (prevSecondMin == -1 || cost < prevSecondMin) {
                prevSecondMin = cost;
            }
        }

        // loop for each house
        for (int house = 1; house < n; house++) {
            int min = -1; int secondMin = -1; int minColor = -1;
            for (int color = 0; color < k; color++) {
                // Determine the cost for this cell (without writing it in).
                int cost = costs[house][color];
                if (color == prevMinColor) {
                    cost += prevSecondMin;
                } else {
                    cost += prevMin;
                }

                // Determine whether or not this current cost is also a minimum.
                if (min == -1 || cost < min) {
                    secondMin = min;
                    minColor = color;
                    min = cost;
                } else if (secondMin == -1 || cost < secondMin) {
                    secondMin = cost;
                }
            }

            // Transfer current mins to be previous mins.
            prevMin = min;
            prevSecondMin = secondMin;
            prevMinColor = minColor;
        }

        return prevMin;
    }
}






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def minFallingPathSum(self, A):
        for i in xrange(1, len(A)):
            r = heapq.nsmallest(2, A[i - 1])
            for j in xrange(len(A[0])):
                A[i][j] += r[1] if A[i - 1][j] == r[0] else r[0]
        return min(A[-1])

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

