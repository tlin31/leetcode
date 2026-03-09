3531. Count Covered Buildings - Medium

You are given a positive integer n, representing an n x n city. You are also given a 2D grid buildings, where buildings[i] = [x, y] denotes a unique building located at coordinates [x, y].

A building is covered if there is at least one building in all four directions: left, right, above, and below.

Return the number of covered buildings.

 

Example 1:



Input: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]

Output: 1

Explanation:

Only building [2,2] is covered as it has at least one building:
above ([1,2])
below ([3,2])
left ([2,1])
right ([2,3])
Thus, the count of covered buildings is 1.
Example 2:



Input: n = 3, buildings = [[1,1],[1,2],[2,1],[2,2]]

Output: 0

Explanation:

No building has at least one building in all four directions.
Example 3:



Input: n = 5, buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]

Output: 1

Explanation:

Only building [3,3] is covered as it has at least one building:
above ([1,3])
below ([5,3])
left ([3,2])
right ([3,5])
Thus, the count of covered buildings is 1.
 

Constraints:

2 <= n <= 105
1 <= buildings.length <= 105 
buildings[i] = [x, y]
1 <= x, y <= n
All coordinates of buildings are unique.


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
The key insight is that all the “middle” buildings in a row or column don't matter individually.

Only the extreme values matter!
	In a row y, only the minimum x (topmost) and maximum x (bottommost) matter.
	In a column x, only the minimum y (leftmost) and maximum y (rightmost) matter.


So once we know:

	rmin[y], rmax[y]   — the topmost and bottommost buildings of row y
	cmin[x], cmax[x]   — the leftmost and rightmost buildings of column x

Checking whether (x, y) is covered becomes a constant-time condition:

	x is not at the row edge       → rmin[y] < x < rmax[y]
	y is not at the column edge    → cmin[x] < y < cmax[x]

class Solution:
    def countCoveredBuildings(self, n: int, b: List[List[int]]) -> int:
        rmax = [0] * (n + 1)
        rmin = [n + 1] * (n + 1)
        cmax = [0] * (n + 1)
        cmin = [n + 1] * (n + 1)

        # Track extreme buildings for each row and column
        for x, y in b:
            rmax[y] = max(rmax[y], x)
            rmin[y] = min(rmin[y], x)
            cmax[x] = max(cmax[x], y)
            cmin[x] = min(cmin[x], y)

        ans = 0

        # A building is covered only if it's strictly inside both extremes
        for x, y in b:
            if rmin[y] < x < rmax[y] and cmin[x] < y < cmax[x]:
                ans += 1

        return ans



        
逻辑步骤 (Logic Steps)：

1. 离散化/坐标压缩 (Coordinate Compression)：由于建筑物的坐标可能非常大（如 10^9），
直接开数组会 OOM (Out of Memory)。我们需要提取所有相关的坐标点（建筑物位置、区间起点和终点）。

2. 排序 (Sorting)：对所有感兴趣的坐标点进行排序。

3. 扫描线/差分 (Scanning Line / Difference)：
将区间转化为事件：start 时刻覆盖数 +1，end 时刻覆盖数 -1。
遍历排序后的坐标点，维护当前的“覆盖计数”。只要计数 >0，该段区域即为被覆盖。

4. 二分查找 (Binary Search)：统计落在这些“被覆盖区域”内的建筑物数量。



import java.util.*;

class Solution {
    public int countCoveredBuildings(int[][] buildings, int[][] SystemRanges) {
        // 1. 提取所有区间端点并排序 (Extract and sort interval endpoints)
        List<int[]> events = new ArrayList<>();
        for (int[] range : SystemRanges) {
            events.add(new int[]{range[0], 1});  // 起点：+1
            events.add(new int[]{range[1] + 1, -1}); // 终点后一位置：-1
        }
        Collections.sort(events, (a, b) -> a[0] - b[0]);

        // 2. 合并区间，得到所有被覆盖的 [start, end] 范围
        List<int[]> coveredIntervals = new ArrayList<>();
        int activeRanges = 0;
        int lastPos = -1;
        for (int[] event : events) {
            int pos = event[0];
            if (activeRanges > 0 && pos > lastPos) {
                // 如果当前有激活区间，记录覆盖段
                if (!coveredIntervals.isEmpty() && coveredIntervals.get(coveredIntervals.size()-1)[1] == lastPos) {
                    coveredIntervals.get(coveredIntervals.size()-1)[1] = pos - 1;
                } else {
                    coveredIntervals.add(new int[]{lastPos, pos - 1});
                }
            }
            activeRanges += event[1];
            lastPos = pos;
        }

        // 3. 统计落在这些区间内的建筑物数量 (Count buildings in ranges)
        int count = 0;
        int[] bPos = new int[buildings.length];
        for(int i=0; i<buildings.length; i++) bPos[i] = buildings[i][0];
        Arrays.sort(bPos);

        for (int[] interval : coveredIntervals) {
            int left = Arrays.binarySearch(bPos, interval[0]);
            if (left < 0) left = -(left + 1);
            int right = Arrays.binarySearch(bPos, interval[1]);
            if (right < 0) right = -(right + 2);
            if (right >= left) count += (right - left + 1);
        }
        return count;
    }
}


