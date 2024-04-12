667. Beautiful Arrangement II - Medium


Given two integers n and k, you need to construct a list which contains n different positive 
integers ranging from 1 to n and obeys the following requirement:
	Suppose this list is [a1, a2, a3, ... , an], then the list [|a1 - a2|, |a2 - a3|, |a3 - a4|, 
	... , |an-1 - an|] has exactly k distinct integers.

If there are multiple answers, print any of them.

Example 1:
Input: n = 3, k = 1
Output: [1, 2, 3]
Explanation: The [1, 2, 3] has three different positive integers ranging from 1 to 3, and the [1, 1] 
has exactly 1 distinct integer: 1.


Example 2:
Input: n = 3, k = 2
Output: [1, 3, 2]
Explanation: The [1, 3, 2] has three different positive integers ranging from 1 to 3, and the [2, 1] 
has exactly 2 distinct integers: 1 and 2.


Note:
The n and k are in the range 1 <= k < n <= 104.

******************************************************
key:
	- Array
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- 
	- 


Method:

	-	
	-	if you have n number, the maximum k can be n - 1;
		if n is 9, max k is 8.
		This can be done by picking numbers interleavingly from head and tail,

		// start from i = 1, j = n;
		// i++, j--, i++, j--, i++, j--

		i: 1   2   3   4   5
		j:   9   8   7   6

		out: 1 9 2 8 3 7 4 6 5
		dif:  8 7 6 5 4 3 2 1

		Above is a case where k is exactly n - 1
		When k is less than that, simply lay out the rest (i, j) in incremental
		order(all diff is 1). Say if k is 5:

		     i++ j-- i++ j--  i++ i++ i++ ...
		out: 1   9   2   8    3   4   5   6   7
		dif:   8   7   6   5    1   1   1   1 


Algorithm

	As before, write[1, 2, ..., n-k-1] first. The remaining k+1 elements to be written are 
	[n-k, n-k+1, ..., n], and we will write them in alternating head and tail order.

	When we are writing the i-th element from the remaining k+1, every even i is going to be chosen 
	from the head, and will have value n-k + i/2. Every odd i is going to be chosen from the tail, 
	and will have value n - i/2.



class Solution {
    public int[] constructArray(int n, int k) {
        int[] ans = new int[n];
        int c = 0;
        for (int v = 1; v < n-k; v++) {
            ans[c++] = v;
        }
        for (int i = 0; i <= k; i++) {
            ans[c++] = (i%2 == 0) ? (n-k + i/2) : (n - i/2);
        }
        return ans;
    }
}

ex. n = 9, k = 5
[1, 2, 3, 0, 0, 0, 0, 0, 0]
[1, 2, 3, 4, 9, 5, 8, 6, 7]





~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

    def constructArray(self, n, k):
        ans = list(range(1, n - k))
        for i in range(k+1):
            if i % 2 == 0:
                ans.append(n-k + i//2)
            else:
                ans.append(n - i//2)

        return ans

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

