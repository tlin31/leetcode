1139. Largest 1-Bordered Square - Medium


Given a 2D grid of 0s and 1s, return the number of elements in the largest square subgrid that has 
all 1s on its border, or 0 if such a subgrid doesnt exist in the grid.

 

Example 1:

Input: grid = [[1,1,1],
			   [1,0,1],
			   [1,1,1]]
Output: 9


Example 2:

Input: grid = [[1,1,0,0]]
Output: 1
 

Constraints:

1 <= grid.length <= 100
1 <= grid[0].length <= 100
grid[i][j] is 0 or 1



******************************************************
key:
	- prefix sum, then check all 4 edges
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


graph: https://leetcode.com/problems/largest-1-bordered-square/discuss/345265/c%2B%2B-beats-100-(both-time-and-memory)-concise-with-algorithm-and-image

Stats:

	- Time O(N^3)
	- Space O(N^2)



Method:
	-  compute presum arrays
	-  Then starting from bottom right,for every i,j ; we find small=min (ver[i][j], hor[i][j]) 
	   (marked in orange) , then look at all distances in [1,small] vertically in hor array and 
	   horizontally in ver array. 
	   If values(shown in blue) are greater than small and if small is greater than curr result, 
	   then we update result
	-  preSumLeft[i+1][j] and preSumLeft[i+1][j+1+len-1] indicate the count of consecutive 1s 
	   ending at j on column i and column i + 1 - len, respectively;




class Solution {
public int largest1BorderedSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] preSumUp = new int[m+1][n+1];
        int[][] preSumLeft = new int[m+1][n+1];
        for(int j = 0;j<n;j++){
            for(int i = 0;i<m;i++){
                preSumUp[i+1][j+1] = preSumUp[i][j+1] + grid[i][j];
                
            }
        }
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){
                preSumLeft[i+1][j+1] = preSumLeft[i+1][j] + grid[i][j];
            }
        }
        
        int len = Math.min(m,n);
        while(len > 0){
            for(int i = 0;i<=m-len;i++){
                for(int j = 0;j<=n-len;j++){

                	// check top side --> if the difference is not == length, 
                	// just decrement
                    if(preSumLeft[i+1][j+1+len-1] - preSumLeft[i+1][j] != len) 
                    	continue;

                    // check bottom
                    if(preSumLeft[i+1+len-1][j+1+len-1] - preSumLeft[i+1+len-1][j] != len) 
                    	continue;

                    // check left
                    if(preSumUp[i+1+len-1][j+1] - preSumUp[i][j+1] != len) 
                    	continue;

                    // check right
                    if(preSumUp[i+1+len-1][j+1+len-1] - preSumUp[i][j+1+len-1] != len) 
                    	continue;

                    return len*len;
                }
            }
            len--;
        }
        return 0;
    }
}

ex.
[[1,1,1],
 [1,0,1],
 [1,1,1]], result = 9


preSumLeft
[0, 0, 0, 0]	// first if condition, check diff of (1,3) & (1,0)
[0, 1, 2, 3]    // second if condition, check diff of (3,3) & (3,1)
[0, 1, 1, 2]
[0, 1, 2, 3]


preSumUp:
[0, 0, 0, 0]	// third if condition, check diff of (3,3) & (3,0)
[0, 1, 1, 0]	// last if condition, check diff of (3,1) & (0,1)
[0, 2, 1, 2]
[0, 3, 2, 3]






~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	




class Solution {
    public int largest1BorderedSquare(int[][] grid) {
        if (grid.length==0) return 0;
        int[][] dpr = new int[grid.length+1][grid[0].length+1];
        int[][] dpc = new int[grid.length+1][grid[0].length+1];
        int dist, max=0;
        for (int r=1;r<=grid.length;r++){
            for (int c=1;c<=grid[0].length;c++){
                if (grid[r-1][c-1]==0) 
                	continue;
                dpr[r][c] = dpr[r-1][c]+1;
                dpc[r][c] = dpc[r][c-1]+1;
                dist = Math.min(dpr[r][c],dpc[r][c]);
                for (int i=dist;i>=1;i--){
                    if (dpr[r][c-i+1]>=i && dpc[r-i+1][c]>=i){
                        max = Math.max(max, i*i);
                        break;
                    }
                }
            }
        }
        return max;
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

