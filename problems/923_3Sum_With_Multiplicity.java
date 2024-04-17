923. 3Sum With Multiplicity
Medium

Given an integer array arr, and an integer target, return the number of tuples i, j, k 
such that i < j < k and arr[i] + arr[j] + arr[k] == target.

As the answer can be very large, return it modulo 109 + 7.

 

Example 1:

Input: arr = [1,1,2,2,3,3,4,4,5,5], target = 8
Output: 20
Explanation: 
Enumerating by the values (arr[i], arr[j], arr[k]):
(1, 2, 5) occurs 8 times;
(1, 3, 4) occurs 8 times;
(2, 2, 4) occurs 2 times;
(2, 3, 3) occurs 2 times.

Example 2:

Input: arr = [1,1,2,2,2,2], target = 5
Output: 12
Explanation: 
arr[i] = 1, arr[j] = arr[k] = 2 occurs 12 times:
We choose one 1 from [1,1] in 2 ways,
and two 2s from [2,2,2,2] in 6 ways.

Example 3:

Input: arr = [2,1,3], target = 6
Output: 1
Explanation: (1, 2, 3) occured one time in the array so we return 1.
 

Constraints:

3 <= arr.length <= 3000
0 <= arr[i] <= 100
0 <= target <= 300


******************************************************
key:
	- is the array sorted
	
	- possible combinations: Cnk = n!/(n-k)!*k!
	- edge case:
		1) empty array, return 0
		2) array of size <3, return 0

******************************************************


=======================================================================================================
Method 2: count occurance of each NumberFormatException


Count the occurrence of each number. using hashmap or array up to you.

Loop i on all numbers,
loop j on all numbers,
check if k = target - i - j is valid.

Add the number of this combination to result.
3 cases covers all possible combination:

	i == j == k
	i == j != k
	i < j && j < k

Math:
	        # n choose k 
            # -> n! / (k! * (n-k)!) 
            # k is always 3 in this problem. Substitute it in. 
            # -> n! / (3! * (n-3)!)

            # -> (n * (n-1) * (n-2)) / 6 

Time Complexity:
	3 <= A.length <= 3000, so N = 3000
	But 0 <= A[i] <= 100
	So my solution is O(N + 101 * 101)



    public int threeSumMulti(int[] A, int target) {
        long[] c = new long[101];
        for (int a : A) 
        	c[a]++;

        long res = 0;
        for (int i = 0; i <= 100; i++)
            for (int j = i; j <= 100; j++) {
                int k = target - i - j;

                if (k > 100 || k < 0) 
                	continue; //escape from the loop

                if (i == j && j == k) //3 same numbers， number of count of i chooses 3
                    res += c[i] * (c[i] - 1) * (c[i] - 2) / 6;

                else if (i == j && j != k) // number of count i choose 2 * count k
                    res += c[i] * (c[i] - 1) / 2 * c[k];

                else if (j < k)			  //ensure i<j<k, so that we don't double count any combo
                    res += c[i] * c[j] * c[k];
            }

        return (int)(res % (1e9 + 7));
    }






class Solution {
    public int threeSumMulti(int[] A, int target) {
        int MOD = 1_000_000_007;
        long[] count = new long[101];
        for (int x: A)
            count[x]++;

        long ans = 0;

        // All different
        for (int x = 0; x <= 100; ++x)
            for (int y = x+1; y <= 100; ++y) {
                int z = target - x - y;
                if (y < z && z <= 100) {
                    ans += count[x] * count[y] * count[z];
                    ans %= MOD;
                }
            }

        // x == y != z
        for (int x = 0; x <= 100; ++x) {
            int z = target - 2*x;
            if (x < z && z <= 100) {
                ans += count[x] * (count[x] - 1) / 2 * count[z];
                ans %= MOD;
            }
        }

        // x != y == z
        for (int x = 0; x <= 100; ++x) {
            if (target % 2 == x % 2) {
                int y = (target - x) / 2;
                if (x < y && y <= 100) {
                    ans += count[x] * count[y] * (count[y] - 1) / 2;
                    ans %= MOD;
                }
            }
        }

        // x == y == z
        if (target % 3 == 0) {
            int x = target / 3;
            if (0 <= x && x <= 100) {
                ans += count[x] * (count[x] - 1) * (count[x] - 2) / 6;
                ans %= MOD;
            }
        }

        return (int) ans;
    }
}

=======================================================================================================
Method 1:


Stats:

	- Time Complexity: O(N^2) where N is the length of A.
	  Space Complexity: O(1)
	


Method:

	-	

class Solution {
    public int threeSumMulti(int[] A, int target) {
        int MOD = 1_000_000_007;
        long ans = 0;
        Arrays.sort(A);

        for (int i = 0; i < A.length; ++i) {
            // We'll try to find the number of i < j < k
            // with A[j] + A[k] == T, where T = target - A[i].

            // The below is a "two sum with multiplicity".
            int T = target - A[i];
            int j = i+1, k = A.length - 1;

            while (j < k) {
                // These steps proceed as in a typical two-sum.
                if (A[j] + A[k] < T)
                    j++;
                else if (A[j] + A[k] > T)
                    k--;
                else if (A[j] != A[k]) {  // We have A[j] + A[k] == T.

                    // Let's count "left": the number of A[j] == A[j+1] == A[j+2] == ...
                    // And similarly for "right".
                    int left = 1, right = 1;
                    while (j+1 < k && A[j] == A[j+1]) {
                        left++;
                        j++;
                    }
                    while (k-1 > j && A[k] == A[k-1]) {
                        right++;
                        k--;
                    }

                    ans += left * right;
                    ans %= MOD;
                    j++;
                    k--;
                } else {
                    // M = k - j + 1
                    // We contributed M * (M-1) / 2 pairs.
                    ans += (k-j+1) * (k-j) / 2;
                    ans %= MOD;
                    break;
                }
            }
        }

        return (int) ans;
    }
}





