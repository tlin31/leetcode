1547. Minimum Cost to Cut a Stick - Hard

Given a wooden stick of length n units. The stick is labelled from 0 to n. For example, a stick of length 6 is labelled as follows:


Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.

You should perform the cuts in order, you can change the order of the cuts as you wish.

The cost of one cut is the length of the stick to be cut, the total cost is the sum of costs of all cuts. When you cut a stick, it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick before the cut). Please refer to the first example for a better explanation.

Return the minimum total cost of the cuts.

 

Example 1:


Input: n = 7, cuts = [1,3,4,5]
Output: 16
Explanation: Using cuts order = [1, 3, 4, 5] as in the input leads to the following scenario:

The first cut is done to a rod of length 7 so the cost is 7. The second cut is done to a rod of length 6 (i.e. the second part of the first cut), the third is done to a rod of length 4 and the last cut is to a rod of length 3. The total cost is 7 + 6 + 4 + 3 = 20.
Rearranging the cuts to be [3, 5, 1, 4] for example will lead to a scenario with total cost = 16 (as shown in the example photo 7 + 4 + 3 + 2 = 16).
Example 2:

Input: n = 9, cuts = [5,6,1,4,2]
Output: 22
Explanation: If you try the given cuts ordering the cost will be 25.
There are much ordering with total cost <= 25, for example, the order [4, 6, 5, 2, 1] has total cost = 22 which is the minimum possible.
 

Constraints:

2 <= n <= 106
1 <= cuts.length <= min(n - 1, 100)
1 <= cuts[i] <= n - 1
All the integers in cuts array are distinct.


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

Range DP
1. Sort and Extend: 
    We add the endpoints of the stick (0 and N) to the cuts array and sort it. Let this new array be A.

2. State Definition: 
    Let dp[i][j] be the minimum cost to perform all cuts between index i and index j in the sorted array A.

3. Transition: 
    To calculate dp[i][j], we try making the first cut at every possible position k between i and j:
        The cost of the current cut is the length of the segment: A[j] - A[i].
        The total cost is (A[j] - A[i]) + dp[i][k] + dp[k][j].
        We pick the k that minimizes this value.

4. Base Case: If j=i+1, there are no cuts possible between them, so dp[i][j] = 0.



Stats:
	Time Complexity: O（M^3）, where M is the number of cuts. There are M^2 states in the DP table, and each state takes O(M) to compute by iterating through possible cut positions k.
	Space Complexity: O(M^2) to store the memoization table.	

	

	
class Solution {
    public int minCost(int n, int[] cuts) {
        int m=cuts.length;
        int[] array=new int[m+2];
        array[0]=0;
        array[m+1]=n;
        for(int i=0;i<m;i++){
            array[i+1]=cuts[i];
        }
        Arrays.sort(array);

        int[][] dp=new int[m+2][m+2];
        for(int i=m;i>=1;i--){
            for(int j=i;j<=m;j++){
                int min=Integer.MAX_VALUE;

                for(int k=i;k<=j;k++){
                    int cost=array[j+1]-array[i-1]+dp[i][k-1]+dp[k+1][j];
                    min=Math.min(min,cost);
                }
                dp[i][j]=min;
            }
        }
        return dp[1][m];
    }
}




 recursive 写法：
 import java.util.*;

class Solution {
    int[][] memo;
    int[] A;

    public int minCost(int n, int[] cuts) {
        int m = cuts.length;
        A = new int[m + 2];
        System.arraycopy(cuts, 0, A, 1, m);
        A[0] = 0;
        A[m + 1] = n;
        Arrays.sort(A);
        
        memo = new int[m + 2][m + 2];
        for (int[] row : memo) Arrays.fill(row, -1);
        
        return solve(0, m + 1);
    }

    private int solve(int i, int j) {
        if (i + 1 == j) return 0; // No space to cut
        if (memo[i][j] != -1) return memo[i][j];
        
        int min = Integer.MAX_VALUE;
        for (int k = i + 1; k < j; k++) {
            min = Math.min(min, (A[j] - A[i]) + solve(i, k) + solve(k, j));
        }
        
        return memo[i][j] = min;
    }
}

 




