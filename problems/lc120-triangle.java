120. Triangle - Medium

Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent
numbers on the row below.

For example, given the following triangle

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

Note:

Bonus point if you are able to do this using only O(n) extra space, where n is the total number 
of rows in the triangle.


******************************************************
key:
	- Greedy/ DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	
	-	

Stats:

	- 
	- 

public int minimumTotal(List<List<Integer>> triangle) {
    int[] A = new int[triangle.size()+1];
    for(int i = triangle.size()-1; i >= 0; i--){
        for(int j=0; j<triangle.get(i).size(); j++){
            A[j] = Math.min(A[j],A[j+1]) + triangle.get(i).get(j);
            System.out.println("i = " + i + ", j = " + j + ". A[j] = " + A[j]);

        }
    }
    return A[0];
}

[[2],[3,4],[6,5,7],[4,1,8,3]]
stdout
i = 3, j = 0. A[j] = 4
i = 3, j = 1. A[j] = 1
i = 3, j = 2. A[j] = 8
i = 3, j = 3. A[j] = 3
i = 2, j = 0. A[j] = 7
i = 2, j = 1. A[j] = 6
i = 2, j = 2. A[j] = 10
i = 1, j = 0. A[j] = 9
i = 1, j = 1. A[j] = 10
i = 0, j = 0. A[j] = 11

=======================================================================================================
method 2:

Method:

	-	Go from bottom to top.We start form the row above the bottom row [size()-2].
	-	Each number add the smaller number of two numbers that below it.
	- 	And finally we get to the top we the smallest sum.


Stats:

	- 
	- 


public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        for(int i = triangle.size() - 2; i >= 0; i--)
            for(int j = 0; j <= i; j++)
                triangle.get(i).set(j, triangle.get(i).get(j) + Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1)));
        return triangle.get(0).get(0);
    }
}


=======================================================================================================
method 3:

Method:

	-	suppose x and y are 'children' of k. Once minimum paths from x and y to the bottom are known,
        the minimum path starting from k can be decided in O(1), that is optimal substructure.

    -   What I like about this problem even more is that the difference between 'top-down' and 
        'bottom-up' DP can be 'literally' pictured in the input triangle. For 'top-down' DP, starting 
        from the node on the very top, we recursively find the minimum path sum of each node. 

        When a path sum is calculated, we store it in an array (memoization); the next time we need 
        to calculate the path sum of the same node, just retrieve it from the array. 

    - 'Bottom-up' DP: we start from the nodes on the bottom row; the min pathsums for these nodes 
       are the values of the nodes themselves. From there, the min pathsum at the ith node on the 
       kth row would be the lesser of the pathsums of its two children plus the value of itself, 
       i.e.:
            minpath[k][i] = min( minpath[k+1][i], minpath[k+1][i+1]) + triangle[k][i];

        Or even better, since the row minpath[k+1] would be useless after minpath[k] is computed, 
        we can simply set minpath as a 1D array, and iteratively update itself:

            For the kth level:
                minpath[i] = min( minpath[i], minpath[i+1]) + triangle[k][i]; 
	-	


Stats:

	- 
	- 

int minimumTotal(vector<vector<int> > &triangle) {
    int n = triangle.size();
    vector<int> minlen(triangle.back());
    for (int layer = n-2; layer >= 0; layer--) // For each layer
    {
        for (int i = 0; i <= layer; i++) // Check its every 'node'
        {
            // Find the lesser of its two children, and sum the current value in the triangle with it.
            minlen[i] = min(minlen[i], minlen[i+1]) + triangle[layer][i]; 
        }
    }
    return minlen[0];
}


