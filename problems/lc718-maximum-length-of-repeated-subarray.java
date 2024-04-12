718. Maximum Length of Repeated Subarray - Medium

Given two integer arrays A and B, return the maximum length of an subarray that appears in both arrays.

Example 1:

Input:
A: [1,2,3,2,1]
B: [3,2,1,4,7]
Output: 3
Explanation: 
The repeated subarray with maximum length is [3, 2, 1].
 

Note:

1 <= len(A), len(B) <= 1000
0 <= A[i], B[i] < 100
 

******************************************************
key:
	- DP
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:

Method:

	-	dp[i][j] : max lenth of common subarray start at a[i] & b[j];
		start from the end of the array, compare char by char

		if (a[i] == b[j]): 
			dp[i][j] = 1 + dp[i + 1][j + 1]

		else:
			dp[i][j] = 0
	
	-	

Stats:

	- 
	- 

class Solution {
    public int findLength(int[] a, int[] b) {
        int m = a.length, 
            n = b.length;
        if (m == 0 || n == 0) 
        	return 0;

        int[][] dp = new int[m + 1][n + 1];
        int max = 0;
        for (int i = m - 1; i >= 0; i--)
            for (int j = n - 1; j >= 0; j--)
                max = Math.max(max, dp[i][j] = a[i] == b[j] ? 1 + dp[i + 1][j + 1] : 0);
        
        return max;        
    }
}


Java - DP array

class Solution {
    public int findLength(int[] a, int[] b) {
        int m = a.length, n = b.length;
        if (m == 0 || n == 0) return 0;
        int[] dp = new int[n + 1];
        int max = 0;
        for (int i = m - 1; i >= 0; i--)
            for (int j = 0; j < n; j++)
                max = Math.max(max, dp[j] = a[i] == b[j] ? 1 + dp[j + 1] : 0);
        return max;        
    }
}

ex. input:[1,2,3,2,1], [3,2,1,4,7]

dp:
[0, 0, 1, 0, 0, 0]
[0, 2, 0, 0, 0, 0]
[3, 0, 0, 0, 0, 0]
[0, 1, 0, 0, 0, 0]
[0, 0, 1, 0, 0, 0]



So maxLen[i][j] only depends on maxLen[i-1][j-1], so you only need to keep maxLen[i-1][j-1] 
which is one element.

In below code we use maxLenEnding to keep maxLen[i-1][j-1] and traverse the matrix diagonally

public int findLength(int[] A, int[] B) {
        
        int maxLen = 0;
        
        
        for (int j = 0; j < B.length; j++) {
            int maxLenEnding = 0;
            for (int i = 0, k = j; i < A.length && k < B.length; i++, k++) {
                if (A[i] != B[k]) 
                	maxLenEnding = 0;
                else {
                    maxLenEnding++;
                    maxLen = Math.max(maxLen, maxLenEnding);
                }
            }
        }
        
        for (int i =1; i < A.length; i++) {
            int maxLenEnding = 0;
            for (int j = 0, k = i; k < A.length && j < B.length; j++, k++) {
                if (A[k] != B[j]) maxLenEnding = 0;
                else {
                    maxLenEnding++;
                    maxLen = Math.max(maxLen, maxLenEnding);
                }
            }
        }
        
        return maxLen;    
    }

=======================================================================================================
method 2:

Method:

	-	Binary Search + Rabin Karp + Hash Table
	-	For every i >= length - 1, we will want to record the hash of A[i-length+1], A[i-length+2],
		..., A[i]. After, we will truncate the first element by h = (h - A[i - (length - 1)]) * 
		Pinv % MOD to get ready to add the next element.


Stats:

	- O(N log N)
	- Time Complexity: O((M+N)∗log(min(M,N))), where M, NM,N are the lengths of A, B. The log factor 
	  is contributed by the binary search, while creating the rolling hashes is O(M+N). The checks 
	  for duplicate hashes are O(1). If we perform a naive check to make sure our answer is correct, 
	  it adds a factor of O(min(M,N)) to our cost of check, which keeps the complexity the same.

	- Space Complexity: O(M), the space used to store hashes and the subarrays in our final naive check.

import java.math.BigInteger;

class Solution {
    int P = 113;
    int MOD = 1_000_000_007;
    int Pinv = BigInteger.valueOf(P).modInverse(BigInteger.valueOf(MOD)).intValue();

    private int[] rolling(int[] source, int length) {
        int[] ans = new int[source.length - length + 1];
        long h = 0, power = 1;
        if (length == 0) return ans;
        for (int i = 0; i < source.length; ++i) {
            h = (h + source[i] * power) % MOD;
            if (i < length - 1) {
                power = (power * P) % MOD;
            } else {
                ans[i - (length - 1)] = (int) h;
                h = (h - source[i - (length - 1)]) * Pinv % MOD;
                if (h < 0) h += MOD;
            }
        }
        return ans;
    }

    private boolean check(int guess, int[] A, int[] B) {
        Map<Integer, List<Integer>> hashes = new HashMap();
        int k = 0;
        for (int x: rolling(A, guess)) {
            hashes.computeIfAbsent(x, z -> new ArrayList()).add(k++);
        }
        int j = 0;
        for (int x: rolling(B, guess)) {
            for (int i: hashes.getOrDefault(x, new ArrayList<Integer>()))
                if (Arrays.equals(Arrays.copyOfRange(A, i, i+guess),
                                  Arrays.copyOfRange(B, j, j+guess))) {
                    return true;
                }
            j++;
        }
        return false;
    }

    public int findLength(int[] A, int[] B) {
        int lo = 0, hi = Math.min(A.length, B.length) + 1;
        while (lo < hi) {
            int mi = (lo + hi) / 2;
            if (check(mi, A, B)) lo = mi + 1;
            else hi = mi;
        }
        return lo - 1;
    }
}

=======================================================================================================
method 3:

Method:

	-	
	-	


Stats:

	- 
	- 



