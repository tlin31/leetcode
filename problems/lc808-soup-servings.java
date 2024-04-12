808. Soup Servings - Medium


There are two types of soup: type A and type B. Initially we have N ml of each type of soup. 

There are four kinds of operations:

	Serve 100 ml of soup A and 0 ml of soup B
	Serve 75 ml of soup A and 25 ml of soup B
	Serve 50 ml of soup A and 50 ml of soup B
	Serve 25 ml of soup A and 75 ml of soup B

When we serve some soup, we give it to someone and we no longer have it.  Each turn, we will choose from 
the four operations with equal probability 0.25. If the remaining volume of soup is not enough to 
complete the operation, we will serve as much as we can.  We stop once we no longer have some quantity 
of both types of soup.

Note that we do not have the operation where all 100 mls of soup B are used first.  

Return the probability that soup A will be empty first, plus half the probability that A and B 
become empty at the same time.

 

Example:
Input: N = 50
Output: 0.625

Explanation: 
If we choose the first two operations, A will become empty first. For the third operation, A and B will become empty at the same time. For the fourth operation, B will become empty first. So the total probability of A becoming empty first plus half the probability that A and B become empty at the same time, is 0.25 * (1 + 1 + 0.5 + 0) = 0.625.

Notes:

0 <= N <= 10^9. 
Answers within 10^-6 of the true value will be accepted as correct.


******************************************************
key:
	- DP / dfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- Without this conversion,
		it needs O(200 * 200 * 25) time & space if A == B,
		it needs O(5000 * 5000) time & space if A != B, (which sounds like 250mb)

		But in our solution above, we use only O(200 * 200) time & space.
	- 


Method:

	- I consider only how many servings left in A and B. 1 serving = 25ml.
	  	If the left part is less than 25ml, it is still considered as one serving.
	- f(a,b) means the result probability for a ml of soup A and b ml of soup B.
		f(a-4,b) means that we take the first operation: Serve 100 ml of soup A and 0 ml of soup B. 
		f(a-3,b-1), f(a-2,b-2), f(a-1,b-3) are other 3 operations.
		The condition a <= 0 and b <= 0 means that we run out of soup A and B at the same time, 
		so we should return a probability of 0.5, which is half of 1.0.




	double[][] memo = new double[200][200];
    public double soupServings(int N) {
    	if (N>= 4800)
    		return 1;
    	else
    		// add 24 because even 1ml will still count as 1 serving, if we pass in integer
    		// 1/25 = 0 which is not what we want
    		return f((N + 24) / 25, (N + 24) / 25);

    }

    public double f(int a, int b) {
        if (a <= 0 && b <= 0) 
        	return 0.5;

        if (a <= 0) 
        	return 1;

        if (b <= 0) 
        	return 0;
        
        if (memo[a][b] > 0) 
        	return memo[a][b];

        memo[a][b] = 0.25 * (f(a - 4, b) + f(a - 3, b - 1) + f(a - 2, b - 2) + f(a - 1, b - 3));
        return memo[a][b];
    }







~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 2:	recursion + memo

Stats:

	- 
	- 


Method:

	-	
	-	

class Solution {
    public double soupServings(int N) {
        if (N > 5000) {  // trick
            return 1.0;
        }
        return helper(N, N, new Double[N + 1][N + 1]);
    }
    
    public double helper(int A, int B, Double[][] memo) {
        if (A <= 0 && B <= 0) return 0.5;     // base case 1
        if (A <= 0) return 1.0;               // base case 2
        if (B <= 0) return 0.0;               // base case 3
        if (memo[A][B] != null) {
            return memo[A][B];
        }
        int[] serveA = {100, 75, 50, 25};
        int[] serveB = {0, 25, 50, 75};
        memo[A][B] = 0.0;
        for (int i = 0; i < 4; i++) {
            memo[A][B] += helper(A - serveA[i], B - serveB[i], memo);
        }
        return memo[A][B] *= 0.25;
    }
}








~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

class Solution(object):
    def soupServings(self, N):
        if N>=5551: return 1
        self.dd=collections.defaultdict(float)
        return self.sub(N,N)
        
    def sub(self, an, bn):
        if an<=0 and bn<=0: 
        	return 0.5
        if an<=0: 
        	return 1
        if bn<=0: 
        	return 0
        if (an,bn) in self.dd: 
        	return self.dd[(an,bn)]

        c1=self.sub(an-100, bn)
        c2=self.sub(an-75, bn-25)
        c3=self.sub(an-50, bn-50)
        c4=self.sub(an-25, bn-75)

        self.dd[(an,bn)] = 0.25 * sum([c1,c2,c3,c4])

        return self.dd[(an,bn)]

=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

