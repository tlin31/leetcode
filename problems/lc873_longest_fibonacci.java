Leetcode 873

problem description: 
A sequence X_1, X_2, ..., X_n is fibonacci-like if:
    n >= 3
    X_i + X_{i+1} = X_{i+2} for all i + 2 <= n

Given a strictly increasing array A of positive integers forming a sequence, find the length of the 
longest fibonacci-like subsequence of A.  If one does not exist, return 0.

Example 1:
Input: [1,2,3,4,5,6,7,8]
Output: 5
Explanation:
The longest subsequence that is fibonacci-like: [1,2,3,5,8].



Example 2:
Input: [1,3,7,11,12,14,18]
Output: 3
Explanation:
The longest subsequence that is fibonacci-like:
[1,11,12], [3,11,14] or [7,11,18].
 

Note:

3 <= A.length <= 1000
1 <= A[0] < A[1] < ... < A[A.length - 1] <= 10^9
(The time limit has been reduced by 50% for submissions in Java, C, and C++.)

******************************************************
key:
    - 
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:
    - 2 sum + pointers?
    - Save array A to a hash set s.
      Start from base (A[i], A[j]) as the first two element in the sequence, try to find the Fibonacci 
      like subsequence as long as possible

    - base case: (a, b) = (A[i], A[j])
      While the set s contains a + b, we update (a, b) = (b, a + b).
      In the end we update the longest length we find.


stats:

    - O(N^2 logM), where M is the max(A).

    - Since the values grow exponentially,the amount of numbers needed to accommodate a sequence
      that ends in a number M is at most log(M).


    public int lenLongestFibSubseq(int[] A) {
            Set<Integer> s = new HashSet<Integer>();
            for (int x : A) 
                s.add(x);

            int res = 2;

            for (int i = 0; i < A.length; ++i)
                for (int j = i + 1; j < A.length; ++j) {
                    int a = A[i], 
                        b = A[j], 
                        l = 2;

                    while (s.contains(a + b)) {
                        b = a + b;  // third becomes new second num in list
                        a = b - a;  // second becomes new first num in list
                        l++;
                    }

                    // keep record for max.
                    res = Math.max(res, l);
                }
            return res > 2 ? res : 0;
        }

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
classSolution(object):
    def lenLongestFibSubseq(self, A):
        S = set(A)
        ans = 0
        for i in xrange(len(A)):
            for j in xrange(i+1, len(A)):
                """
                With the starting pair (A[i], A[j]),
                y represents the future expected value in
                the fibonacci subsequence, and x represents
                the most current value found.
                """
                x, y = A[j], A[i] + A[j]
                length = 2
                while y in S:
                    x, y = y, x + y
                    length += 1
                ans = max(ans, length)

        return ans if ans >= 3 else 0
=======================================================================================================
method 2:

method:

    - dp.
    - dp[a, b] --> length of fibo sequence ends up with (a, b)
      base case: length = 2
      formula: dp[a, b] = (dp[b - a, a] + 1 ) or 2

stats:

    - O(N^2)
    - 

    public int lenLongestFibSubseq(int[] A) {
        int res = 0;
        int[][] dp = new int[A.length][A.length];
        Map<Integer, Integer> index = new HashMap<>();

        // find fibo sequence (k, i, j)
        for (int j = 0; j < A.length; j++) {
            index.put(A[j], j);
            for (int i = 0; i < j; i++) {
                int k = index.getOrDefault(A[j] - A[i], -1);

                if (k >= 0 && k < i){
                    dp[i][j] = dp[k][i] + 1
                } else {
                    dp[i][j] = 2
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res > 2 ? res : 0;
    }

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

classSolution(object):
    def lenLongestFibSubseq(self, A):
        index = {x: i for i, x in enumerate(A)}
        longest = collections.defaultdict(lambda: 2)

        ans = 0
        for k, z in enumerate(A):
            for j in xrange(k):
                i = index.get(z - A[j], None)
                if i is not None and i < j:
                    cand = longest[j, k] = longest[i, j] + 1
                    ans = max(ans, cand)

        return ans if ans >= 3 else 0
=======================================================================================================
method 3: dp + binary search, do not need hashmap

method:

    - 
    - 

stats:

    - fastest
    - 


class Solution {
    public int lenLongestFibSubseq(int[] A) {
        int n = A.length;
        int max = 0;
        int[][] dp = new int[n][n];
        for (int i = 1; i < n; i++) {
            int l = 0, r = i - 1;
            while (l < r) {
                int sum = A[l] + A[r];
                if (sum > A[i]) {
                    r--;  
                } else if (sum < A[i]) {
                    l++;
                } else {

                    // sum == A[i], we find a seq (l, r, i)
                    dp[r][i] = dp[l][r] + 1;
                    max = Math.max(max, dp[r][i]);
                    r--;
                    l++;
                }
            }
        }
        return max == 0 ? 0 : max + 2;
    }
}




