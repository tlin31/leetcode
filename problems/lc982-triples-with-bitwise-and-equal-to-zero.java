982. Triples with Bitwise AND Equal To Zero - Hard


Given an array of integers A, find the number of triples of indices (i, j, k) such that:

0 <= i < A.length
0 <= j < A.length
0 <= k < A.length
A[i] & A[j] & A[k] == 0, where & represents the bitwise-AND operator.
 

Example 1:

Input: [2,1,3]
Output: 12
Explanation: We could choose the following i, j, k triples:
(i=0, j=0, k=1) : 2 & 2 & 1
(i=0, j=1, k=0) : 2 & 1 & 2
(i=0, j=1, k=1) : 2 & 1 & 1
(i=0, j=1, k=2) : 2 & 1 & 3
(i=0, j=2, k=1) : 2 & 3 & 1
(i=1, j=0, k=0) : 1 & 2 & 2
(i=1, j=0, k=1) : 1 & 2 & 1
(i=1, j=0, k=2) : 1 & 2 & 3
(i=1, j=1, k=0) : 1 & 1 & 2
(i=1, j=2, k=0) : 1 & 3 & 2
(i=2, j=0, k=1) : 3 & 2 & 1
(i=2, j=1, k=0) : 3 & 1 & 2
 

Note:

1 <= A.length <= 1000
0 <= A[i] < 2^16



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

	- 
	- 


Method:

	-	
	-	

class Solution {
    public int countTriplets(int[] A) {
        Map<Integer, Integer> map = new HashMap<>();

        // all combinations of 2 
        for(int i : A) {
            for(int j : A) {
                map.put(i&j, map.getOrDefault(i&j, 0) + 1);
            }
        }

        int res = 0;

        for(int k : A) {
            for(int key : map.keySet()) {
                if((key & k) == 0) {
                    res+= map.get(key);
                }
            }
        }

        return res;
    }
}

~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~
def countTriplets(self, A):
	combo = collections.Counter(x&y for x in A for y in A)
	return sum(combo[k] for z in A for k in combo if z&k == 0)

=======================================================================================================
method 2:

Stats:
	Time complexity: O(3 * 2^16 * n)
	Space: O(2^16)
	

Method:

	-  For each i, assume that if we pick i numbers from A array(allow duplicates), the AND of these 
	   i numbers is k.
	-  dp[k] represents the number of combinations for such i and k. We update this dp array for 3 times.

Example:
	i=2 means we pick 2 numbers.
	dp[10] = 5 means when we pick 2 numbers, the count of combinations is 5, where the AND result 
	           of such numbers is 10.

Trick:
	We initialize the dp[(1<<16) - 1] to 1 because the AND result of every number with (1<<16) - 1 
	is the number itself.



class Solution {
    public int countTriplets(int[] A) {
        int N = 1 << 16;
        int[] dp = new int[N];
        dp[N - 1] = 1;
        for (int i = 0; i < 3; i++) {
            int[] temp = new int[N];
            for (int k = 0; k < N; k++) {
                for (int a : A) {
                    temp[k & a] += dp[k];
                }
            }
            dp = temp;
        }
        return dp[0];
    }
}


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

Run throught arrays with two nested loops A[i] and A[j]
Store A[i]&A[j] into dict alongside with number of occurrence.
Run through array one more time and check if dict key and array element gives result of 0

    def countTriplets2(self, A):
        """
        :type A: List[int]
        :rtype: int
        """        
        d={}
        res=0
        for a in A:
            for b in A:
                t=a&b
                if t in d:
                    d[t]+=1
                else:
                    d[t]=1
        for a in A:
            for k,v in d.items():
                if a&k==0:
                    res+=v
        return res
=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	


~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

