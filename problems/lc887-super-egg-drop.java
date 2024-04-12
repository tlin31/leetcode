887. Super Egg Drop - Hard

You are given K eggs, and you have access to a building with N floors from 1 to N. 

Each egg is identical in function, and if an egg breaks, you cannot drop it again.

You know that there exists a floor F with 0 <= F <= N such that any egg dropped at a floor higher 
than F will break, and any egg dropped at or below floor F will not break.

Each move, you may take an egg (if you have an unbroken one) and drop it from any floor X 
(with 1 <= X <= N). 

Your goal is to know with certainty what the value of F is.

What is the minimum number of moves that you need to know with certainty what F is, regardless of the
initial value of F?

 

Example 1:

Input: K = 1, N = 2
Output: 2
Explanation: 
Drop the egg from floor 1.  If it breaks, we know with certainty that F = 0.
Otherwise, drop the egg from floor 2.  If it breaks, we know with certainty that F = 1.
If it didnt break, then we know with certainty F = 2.
Hence, we needed 2 moves in the worst case to know what F is with certainty.


Example 2:

Input: K = 2, N = 6
Output: 3

Example 3:
Input: K = 3, N = 14
Output: 4
 

Note:

1 <= K <= 100
1 <= N <= 10000


******************************************************
key:
	- reduce window size
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1: DP

Method:

	-	dp[M][K] = given K eggs and M moves, what is the maximum number of floor that we can check.

	- 	the dp equation is:
				dp[m][k] = dp[m - 1][k - 1] + dp[m - 1][k] + 1,

		which means we take 1 move to a floor,
			if egg breaks --> can check a lower floor dp[m - 1][k - 1] floors.
			if egg doesnt breaks --> check this or higher floor dp[m - 1][k] floors.

	-	dp[m][k] is the number of combinations and it increase exponentially to N


Stats:

	- For time, O(NK) decalre the space, O(KlogN) running,
	- For space, O(NK).


    public int superEggDrop(int K, int N) {
        int[][] dp = new int[N + 1][K + 1];
        int m = 0;
        while (dp[m][K] < N) {
            ++m;
            for (int k = 1; k <= K; ++k)
                dp[m][k] = dp[m - 1][k - 1] + dp[m - 1][k] + 1;
        }
        
        for (int i = 0; i < N; i++) System.out.println(Arrays.toString(dp[i]));

        return m;
    }

Example:
row: m moves, column: k egggs

[0, 0, 0]
[0, 1, 1]
[0, 2, 3]
[0, 3, 6]
[0, 0, 0]
[0, 0, 0]


----------------------------------------------------------------------------------------
Optimized to 1D DP
Complexity: O(KlogN) Time, O(K) Space


    public int superEggDrop(int K, int N) {
        int dp[] = new int[K + 1], 
               m = 0;

        for (m = 0; dp[K] < N; ++m)
            for (int k = K; k > 0; --k)
                dp[k] = dp[k] + dp[k - 1] + 1;
        return m;
    }

at m = 0, k = 2: [0, 0, 1]
at m = 0, k = 1: [0, 1, 1]
at m = 1, k = 2: [0, 1, 3]
at m = 1, k = 1: [0, 2, 3]
at m = 2, k = 2: [0, 2, 6]
at m = 2, k = 1: [0, 3, 6]

=======================================================================================================
method 2:

Method:

	-	
	-	


Stats:

	- 
	- 
Then for int left = helper(K - 1, i - 1, memo); int right = helper(K, N - i, memo); when i goes up, left goes up and right goes down.
We can use Binary Search here to get the minimum Math.max(left, right) + 1, when left and right are as close as possible.
We come to this O(KNlogN) solution:

class Solution {
    public int superEggDrop(int K, int N) {
        int[][] memo = new int[K + 1][N + 1];
        return helper(K, N, memo);
    }
    private int helper(int K, int N, int[][] memo) {
        if (N <= 1) {
            return N;
        }
        if (K == 1) {
            return N;
        }
        if (memo[K][N] > 0) {
            return memo[K][N];
        }
        
        int low = 1, high = N, result = N;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int left = helper(K - 1, mid - 1, memo);
            int right = helper(K, N - mid, memo);
            result = Math.min(result, Math.max(left, right) + 1);
            if (left == right) {
                break;
            } else if (left < right) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        memo[K][N] = result;
        return result;
    }
}
=======================================================================================================
method 3: math

Method:

	-	
	-	


Stats:

	- 
	- 

Intuition:
	The most naïve solution to finding F would be just simply taking an egg, and making a trial at every
	floor level from bottom to top, which would take F trials to find the answer. This, in fact, is the 
	optimal solution when K = 1.

	Now consider K = k eggs. Obviously we can only afford breaking k - 1 eggs before we have to switch to 
	the aforementioned naïve solution. Now let's try building a Strategy Tree out of the trial actions 
	and see if there's a pattern.

	Take each trial as a tree node, which has 2 possible outcomes: either the egg breaks or not. 
	This gives us 2 branches at each node, yielding a binary tree. Let the left branch be the outcome 
	where the egg breaks. 

	ex： 
		Say we have K = 2 and N = 6, each node has the floor number where the trial took place, 

		   3
		 /   \
		1     5
		 \   / \
		  2 4   6


		This Strategy Tree can be interpreted as:

		Try at level 3. If egg breaks, go to #2, else go to #3.
		Only 1 egg left. Try at level 1. If egg breaks, F = 0, otherwise go to #4.
		Still 2 eggs left. Try at level 5. If egg breaks, go to #5, else go to #6.
		Only 1 egg left. Try at level 2. If egg breaks, F = 1, otherwise F = 2.
		Only 1 egg left. Try at level 4. If egg breaks, F = 3, otherwise F = 4.
		Still 2 eggs left. Try at level 6. If egg breaks, F = 5, otherwise F = 6.

		Notice that every single floor from 1 to N has to appear in this tree. 
		BUT optimal pivot is not in the middle, as you can probably tell from the tree having more
		nodes on the right side. 

		If we can build a Strategy Tree for the given arguments K and N, then result = [min depth] of
		the Tree that contains at least N nodes.

Building:
	shape of the tree depends on K = num of eggs given. 
	So: at most k - 1 left edges on any path from the root to a leaf in the binary tree (otherwise we
	will be left no eggs to try). Therefore, it makes sense to categorize all possible tree 
	constructions by their respective K. Let a tree with K = k eggs be called a k-Strategy Tree.

	The 1-Strategy Tree is basically a linked list, with each node only having a right child. 
	An l-level full k-Strategy Tree can be defined recursively as follows: 
		left subtree of the root is an (l - 1)-level full (k - 1)-Strategy Tree, 
		right subtree is an (l - 1) level full k-Strategy Tree. 

		This is because the left subtree represents the case where an egg has been broken, leaving 
		us l - 1 more moves and k - 1 eggs. 

		Similarly, if no egg is broken, we continue with l - 1 moves left and k eggs. 

	Thus, we can find out the answer by finding out the minimum l such that the number of nodes in an l-level full K-Strategy Tree is no less than N.

	Observe:
		# of nodes on level l in a k-Strategy Tree = sum of the number of nodes on level l - 1 in a 
		k-Strategy Tree and the number of nodes on level l - 1 in a (k - 1)-Strategy Tree. 

		When K = 1, the number of nodes on each level is 1, 1, 1, 1, 1, 1, 1, ...
		When K = 2, the number of nodes on each level is 1, 2, 3, 4, 5, 6, 7, ...
		When K = 3, the number of nodes on each level is 1, 2, 4, 7, 11, 16, 22, ...
		When K = 4, the number of nodes on each level is 1, 2, 4, 8, 15, 26, 42, ...

DP:
	use DP table to calculate the number of nodes at each level for a k-Strategy Tree where k ranges
	from 1 to K. 

	We can calculate level-by-level, from k = 1 to k = K at each level.

class Solution {
    public int superEggDrop(int K, int N) {
        if (N <= 0) {
            return 1;
        }
        if (K <= 1) {
            return N;
        }
        List<List<Integer>> nodes = new ArrayList<>();

        // Initialize DP table for K = 1
        for (int k = 0; k < K - 1; k++) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            nodes.add(list);
        }

        int level = 0; // Current level
        int sum = 1; // Total number of nodes in the K-Tree up to current level

        while (sum < N) {
            nodes.get(0).add(nodes.get(0).get(level) + 1);
            for (int k = 1; k < K - 1; k++) {
                nodes.get(k).add(nodes.get(k - 1).get(level) + nodes.get(k).get(level));
            }
            level++;
            sum += nodes.get(K - 2).get(level);
        }
        return level + 1;
    }
}

