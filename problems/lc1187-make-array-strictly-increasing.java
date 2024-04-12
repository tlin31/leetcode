1187. Make Array Strictly Increasing - Hard


Given two integer arrays arr1 and arr2, return the minimum number of operations (possibly zero) 
needed to make arr1 strictly increasing.

In one operation, you can choose two indices 0 <= i < arr1.length and 0 <= j < arr2.length and do 
the assignment arr1[i] = arr2[j].

If there is no way to make arr1 strictly increasing, return -1.

 

Example 1:

Input: arr1 = [1,5,3,6,7], arr2 = [1,3,2,4]
Output: 1
Explanation: Replace 5 with 2, then arr1 = [1, 2, 3, 6, 7].


Example 2:

Input: arr1 = [1,5,3,6,7], arr2 = [4,3,1]
Output: 2
Explanation: Replace 5 with 3 and then replace 3 with 4. arr1 = [1, 3, 4, 6, 7].


Example 3:

Input: arr1 = [1,5,3,6,7], arr2 = [1,6,3,3]
Output: -1
Explanation: You can't make arr1 strictly increasing.
 

Constraints:

1 <= arr1.length, arr2.length <= 2000
0 <= arr1[i], arr2[i] <= 10^9


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

	- Time complexity: O(m * n)
	- Space complexity: O(m * n) -> O(m + n)



Method:

	-  need to track a[i] = b[j], since element in b can be used multiple times (it's not a swap, just
	   assignment!) --> sort b[]

	-  dp: 2 cases, we want to know the state of a[i], and it can be determined by either keeping a[i] &
		   add it to a[i-1], or swap a[i] with b[j]. Therefore, we have 2 cases:

		   	1. swap[i][j] = min cost to make a[0~i] valid by setting a[i] = b[j] 
		   	2. keep[i]    = min cost to make a[0~i] valid by keeping a[i]

	    Base case:
			keep[0] = 0			# a[0] is always valid, no cost
			swap[0][*] = 1		# 1 op to assign b[j] to a[0]

		Try all pairs (a[i], b[j])
			case 1: a[i] > a[i-1], keep[i] = keep[i-1]
			case 2: b[j] > a[i-1], swap[i][j] = keep[i-1] + 1
			case 3: a[i] > b[j],   keep[i] = min(swap[i-1][k]), k <= j  --> a[0], a[1], ... b[k]...a[i]
			case 4: swap[i][j] = min(swap[i-1][k] + 1), k < j  			--> always true, a[0]...b[k],b[j]


		Ans:
			min( min(swap[m-1], keep[m-1]) )


  int makeArrayIncreasing(int[] a, int[] c) {
    int kInf = 1e9;
    int m = a.size();    
    // Sort b and make it only containing unique numbers.
    sort(begin(c), end(c));
    vector<int> b;
    for (int i = 0; i < c.size(); ++i) {
      if (!b.empty() && c[i] == b.back()) continue;
      b.push_back(c[i]);
    }    
    int n = b.size();
    
    // min steps to make a[0~i] valid by keeping a[i]
    vector<int> keep(m, kInf);
    keep[0] = 0;
    // swap[i][j] := min steps to make a[0~i] valid by a[i] = b[j]
    vector<int> swap(n, 1);
    
    for (int i = 1; i < m; ++i) {
      int min_keep = kInf;
      int min_swap = kInf;
      vector<int> temp(n, kInf);
      for (int j = 0; j < n; ++j) {
        if (j > 0) min_swap = min(min_swap, swap[j - 1] + 1);
        if (a[i] > b[j]) min_keep = min(min_keep, swap[j]);        
        if (a[i] > a[i - 1]) keep[i] = keep[i - 1];
        if (b[j] > a[i - 1]) temp[j] = keep[i - 1] + 1;        
        temp[j] = min(temp[j], min_swap);
        keep[i] = min(keep[i], min_keep);
      }
      temp.swap(swap);
    }
    
    int s = *min_element(begin(swap), end(swap));
    int k = keep.back();
    int ans = min(s, k);
    return ans >= kInf ? -1 : ans;
  }





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

Similar to Longest-increasing-sub-sequence problem.
Record the possible states of each position and number of operations to get this state.
When we check i-th element in the arr1, dp record the possible values we can place at this position, and the number of operations to get to this state.
Now, we need to build dp for (i+1)-th position, so for (i+1)-th element,
if it's larger than the possible state from i-th state, we have two choices:
1, keep it so no operation needs to be made.
2, choose from arr2 a smaller element that is larger than i-th element and add one operation.
If it's not larger than the i-th state, we definitely need to make a possible operation.


class Solution:
    def makeArrayIncreasing(self, arr1: List[int], arr2: List[int]) -> int:
        dp = {-1:0}
        arr2.sort()
        for i in arr1:
            tmp = collections.defaultdict(lambda: float('inf'))
            for key in dp:
                if i > key:
                    tmp[i] = min(tmp[i],dp[key])
                loc = bisect.bisect_right(arr2,key)
                if loc < len(arr2):
                    tmp[arr2[loc]] = min(tmp[arr2[loc]],dp[key]+1)
            dp = tmp
        if dp:
            return min(dp.values())
        return -1

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	
	-	










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

