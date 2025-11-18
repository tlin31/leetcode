2940. Find Building Where Alice and Bob Can Meet
Hard

You are given a 0-indexed array heights of positive integers, where heights[i] represents the 
height of the ith building.

If a person is in building i, they can move to any other building j if and only if i < j and 
heights[i] < heights[j].

You are also given another array queries where queries[i] = [ai, bi]. On the ith query, 
Alice is in building ai while Bob is in building bi.

Return an array ans where ans[i] is the index of the leftmost building where Alice and Bob 
can meet on the ith query. If Alice and Bob cannot move to a common building on query i, 
set ans[i] to -1.

 

Example 1:

Input: heights = [6,4,8,5,2,7], queries = [[0,1],[0,3],[2,4],[3,4],[2,2]]
Output: [2,5,-1,5,2]
Explanation: In the first query, Alice and Bob can move to building 2 since heights[0] < heights[2] and heights[1] < heights[2]. 
In the second query, Alice and Bob can move to building 5 since heights[0] < heights[5] and heights[3] < heights[5]. 
In the third query, Alice cannot meet Bob since Alice cannot move to any other building.
In the fourth query, Alice and Bob can move to building 5 since heights[3] < heights[5] and heights[4] < heights[5].
In the fifth query, Alice and Bob are already in the same building.  
For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.

Example 2:

Input: heights = [5,3,8,2,6,1,4,6], queries = [[0,7],[3,5],[5,2],[3,0],[1,6]]
Output: [7,6,-1,4,6]
Explanation: In the first query, Alice can directly move to Bob's building since heights[0] < heights[7].
In the second query, Alice and Bob can move to building 6 since heights[3] < heights[6] and heights[5] < heights[6].
In the third query, Alice cannot meet Bob since Bob cannot move to any other building.
In the fourth query, Alice and Bob can move to building 4 since heights[3] < heights[4] and heights[0] < heights[4].
In the fifth query, Alice can directly move to Bob's building since heights[1] < heights[6].
For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.

 

Constraints:

1 <= heights.length <= 5 * 104
1 <= heights[i] <= 109
1 <= queries.length <= 5 * 104
queries[i] = [ai, bi]
0 <= ai, bi <= heights.length - 1


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:


We are given an integer array heights and an array of pairs queries, where each pair is of the 
form [ai,bi]representing the positions of Alice and Bob at indices i and j, respectively. 
For each query, the task is to find the closest value to the right in the heights array that 
is greater than the heights at both the given positions.

In other words, given indices i and j, we need to find the first value in the heights array 
that is greater than the values at heights[i] and heights[j]. If no such value exists, return -1.


Step 1
For each query [i,j],
if i < j and A[i] < A[j], then Alice and Bob meet at j.
if i > j and A[i] > A[j], then Alice and Bob meet at i.
if i == j, then Alice and Bob already meet at i.

Step 2
For the remaining queries,
we arrange them by position,
a query [i,j] can be answered after index max(i,j),

We will(not now) put a query in a minimum priority queue,
sorted by key max(A[i], A[j]),
so we can answer the query with minimum height first.

Step 3
Then we iterate heights A[i] from left to right,
and check if we can answer any not answered queries.
After we answer all queries we can answer with A[i],
we will push new queries that we can answer from now,
new queries are arranged from step 2.

Step 4
Finally return the answer res.


Complexity
	Time O(q*log q)
	Space O(q)
	where q = queries.size




    public int[] leftmostBuildingQueries(int[] A, int[][] queries) {
        int n = A.length, qn = queries.length;
        List<int[]>[] que = new ArrayList[n];
        for (int i = 0; i < n; i++)
            que[i] = new ArrayList();
        PriorityQueue<int[]> h = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] res = new int[qn];
        Arrays.fill(res, -1);
        // Step 1
        for (int qi = 0; qi < qn; qi++) {
            int i = queries[qi][0], j = queries[qi][1];
            if (i < j && A[i] < A[j]) {
                res[qi] = j;
            } else if (i > j && A[i] > A[j]) {
                res[qi] = i;
            } else if (i == j) {
                res[qi] = i;
            } else { // Step 2
                que[Math.max(i, j)].add(new int[]{Math.max(A[i], A[j]), qi});
            }
        }
        // Step 3
        for (int i = 0; i < n; i++) {
            while (!h.isEmpty() && h.peek()[0] < A[i]) {
                res[h.poll()[1]] = i;
            }
            for (int[] q : que[i]) {
                h.add(q);
            }
        }

        return res;
    }





