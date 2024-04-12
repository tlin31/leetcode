1289. Minimum Falling Path Sum II - Hard


Given a square grid of integers arr, a falling path with non-zero shifts is a choice of exactly 
one element from each row of arr, such that no two elements chosen in adjacent rows are in the 
same column.

Return the minimum sum of a falling path with non-zero shifts.

 

Example 1:

Input: arr = [[1,2,3],[4,5,6],[7,8,9]]
Output: 13
Explanation: 
The possible falling paths are:
[1,5,9], [1,5,7], [1,6,7], [1,6,8],
[2,4,8], [2,4,9], [2,6,7], [2,6,8],
[3,4,8], [3,4,9], [3,5,7], [3,5,9]
The falling path with the smallest sum is [1,5,7], so the answer is 13.
 

Constraints:

1 <= arr.length == arr[i].length <= 200
-99 <= arr[i][j] <= 99


******************************************************
key:
	- DP, similar to 265
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


For every element in every column starting from second last row,
count minimum on the left & minimum on the right.

public int minFallingPathSum(int[][] arr) {
        int n = arr.length;
        for (int row=n-2;row>=0;row--) {

            for (int col=0;col<n;col++) {
                int min = Integer.MAX_VALUE;
                // Values to the left.
                for(int k=0;k<col;k++) {
                    min = Math.min(arr[row+1][k],min);
                }
                // Values to the right.
                for (int k=col+1;k<n;k++) {
                    min = Math.min(arr[row+1][k],min);
                }
                arr[row][col]+= min;
            }

        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            result = Math.min(result, arr[0][i]);
        }
        return result;
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

