305. Number of Islands II - Hard

You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

 

Example 1:


Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
Output: [1,1,2,3]
Explanation:
Initially, the 2d grid is filled with water.
- Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
- Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
- Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
- Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
Example 2:

Input: m = 1, n = 1, positions = [[0,0]]
Output: [1]
 

Constraints:

1 <= m, n, positions.length <= 104
1 <= m * n <= 104
positions[i].length == 2
0 <= ri < m
0 <= ci < n


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

Union-Find (Disjoint Set Union)

This problem is a "dynamic connectivity" challenge. Since we add land one by one, we need to efficiently merge separate components and track the total number of connected sets. 

Map 2D to 1D: Convert a cell (x,y)to a unique index using x*n+y.

Initialize: Start with an empty parent array (all set to -1) and a counter count = 0.

Add Land: 
	For each position (x,y) in the input:
		If it’s already land, add the current count to the result and move on.
		Increment count and set parent[index] = index.

Check Neighbors: Look at the four neighbors (Up, Down, Left, Right).

Union: If a neighbor is already land, find its root. If its root is different from the current cell’s root, merge them and decrement count.

Store: After checking all neighbors, add the updated count to the result list. 



Stats:

	- 
	- 


class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> result = new ArrayList<>();
        int[] parent = new int[m * n];
        Arrays.fill(parent, -1);
        int count = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] pos : positions) {
            int r = pos[0], c = pos[1];
            int idx = r * n + c;

            if (parent[idx] != -1) {
                result.add(count);
                continue;
            }

            parent[idx] = idx;
            count++;

            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                int nIdx = nr * n + nc;
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && parent[nIdx] != -1) {
                    int root1 = find(parent, idx);
                    int root2 = find(parent, nIdx);
                    if (root1 != root2) {
                        parent[root1] = root2;
                        count--;
                    }
                }
            }
            result.add(count);
        }
        return result;
    }

    private int find(int[] parent, int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent, parent[i]); // Path compression
    }
}



class Solution:
    def numIslands2(self, m: int, n: int, positions: List[List[int]]) -> List[int]:
        parent = [-1] * (m * n)
        count = 0
        res = []
        
        def find(i):
            if parent[i] == i:
                return i
            parent[i] = find(parent[i]) # Path compression
            return parent[i]

        def union(i, j):
            root_i, root_j = find(i), find(j)
            if root_i != root_j:
                parent[root_i] = root_j
                return True
            return False

        for r, c in positions:
            idx = r * n + c
            if parent[idx] == -1:
                parent[idx] = idx
                count += 1
                for dr, dc in [(0,1), (0,-1), (1,0), (-1,0)]:
                    nr, nc = r + dr, c + dc
                    n_idx = nr * n + nc
                    if 0 <= nr < m and 0 <= nc < n and parent[n_idx] != -1:
                        if union(idx, n_idx):
                            count -= 1
            res.append(count)
        return res


