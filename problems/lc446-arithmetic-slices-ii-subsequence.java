446. Arithmetic Slices II - Subsequence - Hard


A sequence of numbers is called arithmetic if it consists of at least three elements and if the 
difference between any two consecutive elements is the same.

For example, these are arithmetic sequences:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9

The following sequence is not arithmetic.

1, 1, 2, 5, 7
 
A zero-indexed array A consisting of N numbers is given. A subsequence slice of that array is any sequence of integers (P0, P1, ..., Pk) such that 0 ≤ P0 < P1 < ... < Pk < N.

A subsequence slice (P0, P1, ..., Pk) of array A is called arithmetic if the sequence A[P0], A[P1], ..., A[Pk-1], A[Pk] is arithmetic. In particular, this means that k ≥ 2.

The function should return the number of arithmetic subsequence slices in the array A.

The input contains N integers. Every integer is in the range of -231 and 231-1 and 0 ≤ N ≤ 1000. The output is guaranteed to be less than 231-1.

 
Example:

Input: [2, 4, 6, 8, 10]

Output: 7

Explanation:
All arithmetic subsequence slices are:
[2,4,6]
[4,6,8]
[6,8,10]
[2,4,6,8]
[4,6,8,10]
[2,4,6,8,10]
[2,6,10]




******************************************************
key:
	- dp + hashmap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: dp + hashmap

Stats:

	- fastest
	- 


Method:
	-   dp[i][j] = stores num of arithmetic series with 倒数第二 element at i, last element at j
	-	store in map: key = num in A, value = index in A
	-	target = 2 * start - end --> find the first element
	- 	find a series with {k,j,i}

	public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        int ans = 0;
        int[][] dp = new int[n][n];

        // use list because may have input [2,2,4,6,8] --> {2, {0,1}}
        Map<Long, List<Integer>> map = new HashMap<>();


        for (int i = 0; i < n; i++) {
            if (!map.containsKey((long)A[i])) {
                map.put((long)A[i], new ArrayList<>());
            }
            map.get((long)A[i]).add(i);
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {

                long target = 2 * (long)A[j] - A[i];

                if (map.containsKey(target)) {
                    for (int k: map.get(target)) {
                        if (k < j) {
                            dp[j][i] += (dp[k][j] + 1);
                        }
                    }
                }
                ans += dp[j][i];
            }
        }
        return ans;
    }


ex. input: [2,	4,	6,	8,	10] --> output = 7

map: [(2,0), (4,1), (6,2), (8,3), (10,4)]

i = 0, j = 0: skip 
i = 1, j = 0: target = 2*2-4 = 0, done
i = 2, j = 0: target = 2*2-6 = -2, done
i = 2, j = 1: target = 2*4-6 = 2, map contains, k = 0
				since k < j, find a series (k, j, i)
					update dp[j][i] = dp[1][2] += dp[0][1] + 1 = 1			--{0,1,2}
				ans += 1 = 1
i = 3, j = 0: target = 2*2 - 8 = -4, done
i = 3, j = 1: target = 2*4-8 = 0, done
i = 3, j = 2: target = 2*6-8 = 4, map contains, k = 1
				since k < j, 
					update dp[j][i] = dp[2][3] += dp[1][2] + 1 = 1 + 1 = 2	--{1,2,3} & {0,1,2,3}
				ans += 2 --> ans = 3
i = 4, j = 0：target = 2*2 - 10 = -6, done
i = 4, j = 1：target = 2*4 - 10 = -2, done
i = 4, j = 2：target = 2*6 - 10 = 2, map contains, k = 0
				since k < j, 
					update dp[j][i] = dp[2][4] += dp[0][2] + 1 = 0 + 1 = 1	--{0,2,4}  
				ans += 1 --> ans = 4
i = 4, j = 3: target = 2*8 - 10 = 6, map contains, k = 2
				since k < j, 
					update dp[j][i] = dp[3][4] += dp[2][3] + 1 = 2 + 1 = 3	--{2,3,4}, 
																			  {1,2,3,4},{0,1,2,3,4}
				ans += 3 --> ans = 7

=======================================================================================================
method 2: dfs

Stats:

	- 
	- 


Method:

	-	
	-	



public class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0;
        
        Map<Integer, List<Integer>> m = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            m.putIfAbsent(A[i], new ArrayList<>());
            m.get(A[i]).add(i);
        }
        for (Map.Entry<Integer, List<Integer>> e : m.entrySet()) {
            if (e.getValue().size() > 2) {
                int n = e.getValue().size();
                res += (1 << n) - 1 - n - n * (n - 1) / 2;
            }
        }
        
        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (A[j] == A[i] || (long) A[j] - A[i] > Integer.MAX_VALUE || (long) A[j] - A[i] < Integer.MIN_VALUE) {
                    continue; 
                }
                
                res += helper(A, A[j], A[j] - A[i], j, m);
            }
        }
        
        return res;
    }
    
    private int helper(int[] A, int curr, int d, int idx, Map<Integer, List<Integer>> m) {
        if ((long) curr + d > Integer.MAX_VALUE || (long) curr + d < Integer.MIN_VALUE 
        		|| !m.containsKey(curr + d)) {
            return 0;
        }
        
        int res = 0;
        curr += d;
        List<Integer> list = m.get(curr);
        for (int i : list) {
            if (i > idx) {
                res += helper(A, curr, d, i, m) + 1;
            }
        }
        return res;
    }
}