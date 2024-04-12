1039. Minimum Score Triangulation of Polygon - Medium


Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise
order.

Suppose you triangulate the polygon into N-2 triangles.  For each triangle, the value of that triangle is
the product of the labels of the vertices, and the total score of the triangulation is the sum of these 
values over all N-2 triangles in the triangulation.

Return the smallest possible total score that you can achieve with some triangulation of the polygon.

 

Example 1:

Input: [1,2,3]
Output: 6
Explanation: The polygon is already triangulated, and the score of the only triangle is 6.


Example 2:

3 ---- 7
|	   |
|      |
|      |
5 ---- 4
Input: [3,7,4,5]
Output: 144
Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The minimum score is 144.
Example 3:

Input: [1,3,1,4,1,5]
Output: 13
Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
 

Note:

3 <= A.length <= 50
1 <= A[i] <= 100


******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

https://zxi.mytechroad.com/blog/wp-content/uploads/2019/05/1039.png

Stats:

	- O(n^3)
	- O(n^2)


Method:

	-  similar to leetcode 312, burst ballons
	-  First observe that we only stop drawing inner edges, when we cannot form any more triangels. 
	   Obviously every such triangle has 1 edge from the given polygon or 2 edges. That means, the 
	   stop condition is all edges from the polygon are covered.

	-  Also observe that it doesnt matter which triangle you are trying first. Ultimately, if it is 
	   part of optimal configuration, it will end up in the final solution (i.e. doesnt matter which
	   subproblem formed that triangle, this will be more clear with the below pic).

	-  we can fix the edge connecting first and last point and see how many triangles can this edge
	   possible be part of (to begin with). And each of the possibilties divide the problem in 
	   two parts (left part and right part) and they can be solved recusrively. 

	-  the fixed edge will always settle with the optimal triangle irrespective of which edge you 
	   fix first but fixing other edges will not play well with array indicies.


DP:
	-  dp[i][j]: the min score to triangulate A[i] ~ A[j], while there is edge connect A[i] and A[j].
	-  The connected two points in polygon shares one common edge, these two points must be one and 
	   only one triangles.

	We enumerate all points A[k] with i < k < j to form a triangle.

	The score of this triangulation is dp[i][j], dp[i][k] + dp[k][j] + A[i] * A[j] * A[k]


    public int minScoreTriangulation(int[] A) {
        int n = A.length;
        int[][] dp = new int[n][n];

        // j is ending index, i < j is the start
        for (int j = 2; j < n; ++j) {
            for (int i = j - 2; i >= 0; --i) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; ++k)
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j] + A[i] * A[j] * A[k]);
            }
        }
        return dp[0][n - 1];
    }

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	





