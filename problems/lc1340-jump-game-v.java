1340. Jump Game V - Hard


Given an array of integers arr and an integer d. In one step you can jump from index i to index:

	i + x where: i + x < arr.length and 0 < x <= d.
	i - x where: i - x >= 0 and 0 < x <= d.

In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for 
all indices k between i and j (More formally min(i, j) < k < max(i, j)).

You can choose any index of the array and start jumping. Return the maximum number of indices 
you can visit.

Notice that you can not jump outside of the array at any time.

 

Example 1:


Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
Output: 4
Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 
because 13 > 9. You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
Similarly You cannot jump from index 3 to index 2 or index 1.


Example 2:

Input: arr = [3,3,3,3,3], d = 3
Output: 1
Explanation: You can start at any index. You always cannot jump to any index.


Example 3:

Input: arr = [7,6,5,4,3,2,1], d = 1
Output: 7
Explanation: Start at index 0. You can visit all the indicies. 

Example 4:

Input: arr = [7,1,7,1,7,1], d = 2
Output: 2

Example 5:

Input: arr = [66], d = 1
Output: 1
 

Constraints:
	1 <= arr.length <= 1000
	1 <= arr[i] <= 10^5
	1 <= d <= arr.length


******************************************************
key:
	- DFS + memo
	- edge case:
		1) 
		2)

******************************************************



=======================================================================================================
Method 1: dfs + memo (sample run through)


Stats:

	- 数组需要遍历，遍历每个元素的时候，需要访问2d个元素 ， 计算的时候，每个元素没有重复计算， 所以整体复杂度是O(N*d)

	- Space: O(N)


Method:

	-  You can jump from a[i] to a[j] if a[i] > a[j]. 
		This also implies you can not jump from a[j] to a[i]. 
		--> can recurse in one direction till you reach a base case, and then recurse back up. 
		Also, for j < k < i, a[i] > a[k] for you to be able to jump.






class Solution {
    public int maxJumps(int[] a, int d) {
        if( a==null || a.length==0 ) return 0;
        int n = a.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        int max = 0;

        //  check all element as starting index
        for(int i=0;i<n;i++){
            max = Math.max(max, solve(i, a, d , dp));
        }
        return max+1;
    }
    
    // i = index of elem in a[]
    private int solve(int i, int[] a, int d, int[] dp) {
        int n = a.length;
        if( dp[i] != -1 ) 
        	return dp[i];
        int max=0;

        // jump forward
        for(int j=i+1; j <= Math.min(i+d, n-1); j++){
            if(a[j] >= a[i]) 
            	break;
            max = Math.max(max, 1 + solve(j,a,d,dp));
        }

        // backward
        for(int j=i-1; j >= Math.max(i-d, 0); j--){
            if(a[j] >= a[i]) 
            	break;
            max = Math.max(max, 1 + solve(j,a,d,dp));
        }

        dp[i]=max;
        return max;
    }
}



~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	Time complexity: O(nlogn + n*d)
Space complexity: O(n)


  int maxJumps(vector<int>& a, int d) {
    const int n = a.size();
    vector<pair<int, int>> m(n); // <height, index>
    for (int i = 0; i < n; ++i)
      m[i] = {a[i], i};    
    // Solve from the lowest bar.
    sort(begin(m), end(m));    
 
    vector<int> dp(n, 1);
    for (auto [v, i] : m) {      
      for (int j = i + 1; j <= min(n - 1, i + d) && a[i] > a[j]; ++j) 
        dp[i] = max(dp[i], dp[j] + 1);
      for (int j = i - 1; j >= max(0, i - d) && a[i] > a[j]; --j)
        dp[i] = max(dp[i], dp[j] + 1);
    }
    
    return *max_element(begin(dp), end(dp));
  }

  int maxJumps(int[] a, int d) {
    int n = a.length;
    int[][] m = new int[n][2];

    //vector<pair<int, int>> m(n); // <height, index>
    for (int i = 0; i < n; ++i)
      m[i] = new int [a[i], i]; 

    // Solve from the lowest bar.
    sort(begin(m), end(m));    
 
    vector<int> dp(n, 1);
    for (auto [v, i] : m) {      
      for (int j = i + 1; j <= min(n - 1, i + d) && a[i] > a[j]; ++j) 
        dp[i] = max(dp[i], dp[j] + 1);
      for (int j = i - 1; j >= max(0, i - d) && a[i] > a[j]; --j)
        dp[i] = max(dp[i], dp[j] + 1);
    }
    
    return *max_element(begin(dp), end(dp));
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

