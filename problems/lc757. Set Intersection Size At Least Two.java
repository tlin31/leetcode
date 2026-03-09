757. Set Intersection Size At Least Two - Hard

You are given a 2D integer array intervals where intervals[i] = [starti, endi] represents all the integers from starti to endi inclusively.

A containing set is an array nums where each interval from intervals has at least two integers in nums.

For example, if intervals = [[1,3], [3,7], [8,9]], then [1,2,4,7,8,9] and [2,3,4,8,9] are containing sets.
Return the minimum possible size of a containing set.

 

Example 1:

Input: intervals = [[1,3],[3,7],[8,9]]
Output: 5
Explanation: let nums = [2, 3, 4, 8, 9].
It can be shown that there cannot be any containing array of size 4.
Example 2:

Input: intervals = [[1,3],[1,4],[2,5],[3,5]]
Output: 3
Explanation: let nums = [2, 3, 4].
It can be shown that there cannot be any containing array of size 2.
Example 3:

Input: intervals = [[1,2],[2,3],[2,4],[4,5]]
Output: 5
Explanation: let nums = [1, 2, 3, 4, 5].
It can be shown that there cannot be any containing array of size 4.
 

Constraints:

1 <= intervals.length <= 3000
intervals[i].length == 2
0 <= starti < endi <= 108


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

贪心算法 (Greedy Strategy)
为了让集合 尽可能小，我们需要让 S 中的数字尽可能地被多个区间复用 (Reuse)。
核心策略：
1. 排序 (Sorting)：
  优先按区间的右端点 (End) 升序排列。
  如果右端点相同，按左端点 (Start) 降序排列（这是一个关键技巧：右端点相同时，短区间在前，长区间在后）。

2. 贪心放置 (Greedy Placement)：
我们总是尝试在当前区间的末尾放置数字，因为越靠后的数字越容易被后续的区间覆盖。

3. 状态维护：
维护当前集合中最大的两个数 p1, p2


public int intersectionSizeTwo(int[][] intervals) {
    // 1. 排序：右端点升序，左端点降序 (Sort: end asc, start desc)
    Arrays.sort(intervals, (a, b) -> {
        if (a[1] != b[1]) return a[1] - b[1];
        return b[0] - a[0];
    });

    int count = 0;
    int p1 = -1; // 集合中倒数第二大的数
    int p2 = -1; // 集合中最大的数

    for (int[] interval : intervals) {
        int s = interval[0];
        int e = interval[1];

        if (s > p2) {
            // 情况 A：无覆盖，放最后两个 (No coverage, add two)
            count += 2;
            p1 = e - 1;
            p2 = e;
        } else if (s > p1) {
            // 情况 B：覆盖一个（即 p2），再补一个 (One covered, add one)
            count += 1;
            p1 = p2;
            p2 = e;
        }
        // 情况 C：s <= p1，说明 p1 和 p2 都在区间内，无需操作
    }
    return count;
}


Step 1: 排序 (Sorting)
规则：右端点升序；若相同，左端点降序。
排序后：[[1, 3], [1, 4], [3, 5], [2, 5]]
注意：[3, 5] 排在 [2, 5] 前面，因为右端点相同，左端点大的（短区间）优先。

Step 2: 遍历与放置 (Traversal & Placement)
初始化：count = 0, p1 = -1, p2 = -1（p2 是最大的数，p1 是次大的）。

轮次	当前区间 逻辑判断 (Logic)			操作与更新 (Action & Update)			集合状态
1	[1, 3]	s(1) > p2(-1)：无交集		count += 2，放 e-1,e。p1=2, p2=3		{2, 3}
2	[1, 4]	s(1) <= p1(2)：已有两点	无操作 (Already covered by 2, 3)		{2, 3}
3	[3, 5]	s(3) > p1(2) 
		且 <= p2(3)：仅覆盖一点		count += 1，补个 e。p1=3, p2=5		{2, 3, 5}
4	[2, 5]	s(2) <= p1(3)：已有两点	无操作 (Already covered by 3, 5)		{2, 3, 5}

最终结果 (Final Result): count = 3 (集合为 {2, 3, 5})。


class Solution:
    def intersectionSizeTwo(self, intervals: List[List[int]]) -> int:
        # 排序：右端点升序，左端点降序 (Sort by end asc, then start desc)
        intervals.sort(key=lambda x: (x[1], -x[0]))
        
        count = 0
        p1, p2 = -1, -1  # p1 < p2
        
        for s, e in intervals:
            if s > p2:
                # 情况 A: 区间在已知点右侧，无覆盖 (No intersection)
                count += 2
                p1, p2 = e - 1, e
            elif s > p1:
                # 情况 B: 仅覆盖了 p2 一个点 (One point covered)
                count += 1
                p1, p2 = p2, e
            # 情况 C: s <= p1，说明 p1, p2 都在 [s, e] 内，无需操作
                
        return count





