1001. Grid Illumination - Hard

There is a 2D grid of size n x n where each cell of this grid has a lamp that is initially turned off.

You are given a 2D array of lamp positions lamps, where lamps[i] = [rowi, coli] indicates that the lamp at grid[rowi][coli] is turned on. Even if the same lamp is listed more than once, it is turned on.

When a lamp is turned on, it illuminates its cell and all other cells in the same row, column, or diagonal.

You are also given another 2D array queries, where queries[j] = [rowj, colj]. For the jth query, determine whether grid[rowj][colj] is illuminated or not. After answering the jth query, turn off the lamp at grid[rowj][colj] and its 8 adjacent lamps if they exist. A lamp is adjacent if its cell shares either a side or corner with grid[rowj][colj].

Return an array of integers ans, where ans[j] should be 1 if the cell in the jth query was illuminated, or 0 if the lamp was not.

 

Example 1:


Input: n = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
Output: [1,0]
Explanation: We have the initial grid with all lamps turned off. In the above picture we see the grid after turning on the lamp at grid[0][0] then turning on the lamp at grid[4][4].
The 0th query asks if the lamp at grid[1][1] is illuminated or not (the blue square). It is illuminated, so set ans[0] = 1. Then, we turn off all lamps in the red square.

The 1st query asks if the lamp at grid[1][0] is illuminated or not (the blue square). It is not illuminated, so set ans[1] = 0. Then, we turn off all lamps in the red rectangle.

Example 2:

Input: n = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,1]]
Output: [1,1]
Example 3:

Input: n = 5, lamps = [[0,0],[0,4]], queries = [[0,4],[0,1],[1,4]]
Output: [1,1,0]
 

Constraints:

1 <= n <= 109
0 <= lamps.length <= 20000
0 <= queries.length <= 20000
lamps[i].length == 2
0 <= rowi, coli < n
queries[j].length == 2
0 <= rowj, colj < n


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

关键观察

是否被照亮 ≠ 是否有灯在视野里
而是： 是否存在灯在同一 row / col / diag / anti-diag


我们维护 计数 Map：

	方向		Key		Map
	行		row		Map<Integer, Integer>
	列		col		Map<Integer, Integer>
	主对角线	r - c	Map<Integer, Integer>
	副对角线	r + c	Map<Integer, Integer>
	灯集合	(r,c)	Set<Long>

坐标压缩（关键技巧）
	long key = ((long) r << 32) | c;

	① (long) r

	把 r 从 int（32 位）转换成 long（64 位），如果不转，左移会溢出

	② (long) r << 32


	把 r 移动到 long 的高 32 位， 低 32 位补 0

	结果结构： |   r (32 bits)   |   0 (32 bits)   |

	③ | c（按位或）
	 把 c 放到低 32 位， c 是 int，正好占 32 位

	最终结构：|   r (32 bits)   |   c (32 bits)   |


Stats:

	初始化：O(#lamps)

	每个 query：O(1)（固定 9 个位置）

	总体：O(L + Q)

	空间：O(L)


class Solution {
    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {

    	//store count
        Map<Integer, Integer> row = new HashMap<>(); 
        Map<Integer, Integer> col = new HashMap<>();
        Map<Integer, Integer> diag = new HashMap<>();
        Map<Integer, Integer> anti = new HashMap<>();
        Set<Long> lampSet = new HashSet<>();

        // 初始化灯
        for (int[] l : lamps) {
            int r = l[0], c = l[1];
            long key = ((long) r << 32) | c;
            if (lampSet.contains(key)) continue;

            lampSet.add(key);
            row.put(r, row.getOrDefault(r, 0) + 1);
            col.put(c, col.getOrDefault(c, 0) + 1);
            diag.put(r - c, diag.getOrDefault(r - c, 0) + 1);
            anti.put(r + c, anti.getOrDefault(r + c, 0) + 1);
        }

        int[] res = new int[queries.length];
        int idx = 0;

        int[][] dirs = {
            {0,0},{1,0},{-1,0},{0,1},{0,-1},
            {1,1},{1,-1},{-1,1},{-1,-1}
        };

        for (int[] q : queries) {
            int r = q[0], c = q[1];

            // 判断是否被照亮
            if (row.getOrDefault(r,0) > 0 ||
                col.getOrDefault(c,0) > 0 ||
                diag.getOrDefault(r - c,0) > 0 ||
                anti.getOrDefault(r + c,0) > 0) {
                res[idx++] = 1;
            } else {
                res[idx++] = 0;
            }

            // 关闭周围 9 个灯
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nc < 0 || nr >= n || nc >= n) continue;

                long key = ((long) nr << 32) | nc;
                if (!lampSet.contains(key)) continue; //

                lampSet.remove(key);
                row.put(nr, row.get(nr) - 1);
                col.put(nc, col.get(nc) - 1);
                diag.put(nr - nc, diag.get(nr - nc) - 1);
                anti.put(nr + nc, anti.get(nr + nc) - 1);
            }
        }
        return res;
    }
}


常见 Follow-Up
❓1. 如果灯不会被关闭？

	→ 直接只用 4 个 Map，不需要 Set

❓2. 如果 query 要关闭 k 步范围？

	→ dirs 从 9 个 → (2k+1)^2
	→ 仍是常数（k 小）

❓3. 如果灯是动态增加的？

	→ 同样维护 Map + Set
	→ 支持 add/remove

❓4. 能用 TreeMap / Segment Tree 吗？

	→ 可以，但 HashMap 更优
	→ 因为这里只关心是否 > 0，不关心区间


