502. IPO - Hard

Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.

Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.

The answer is guaranteed to fit in a 32-bit signed integer.

 

Example 1:

Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
Output: 4
Explanation: Since your initial capital is 0, you can only start the project indexed 0.
After finishing it you will obtain profit 1 and your capital becomes 1.
With capital 1, you can either start the project indexed 1 or the project indexed 2.
Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
Example 2:

Input: k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
Output: 6
 

Constraints:

1 <= k <= 105
0 <= w <= 109
n == profits.length
n == capital.length
1 <= n <= 105
0 <= profits[i] <= 104
0 <= capital[i] <= 109

******************************************************
key:
	- greedy + PQ/heap
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

1、把所有项目按所需 capital 升序排序
2、维护两个堆：一个指针按 capital 把当前能做的项目加入大根堆（按 profit），每次从大根堆取利润最大的做掉（W += profit），重复 k 次或直到没有可做项目。


先把项目按 capital 排序，然后用一个索引 i 线性推进，将 capital <= W 的项目 push 到 max-heap（按 profit）。

每轮从 max-heap pop 最大 profit 并加到 W。

如果 heap 为空 —— 说明当前资金做不了任何项目，直接结束。

注意边界：若某些项目 profit ≤ 0（没有收益），在 pop 到非正利润时可以提前停止（因为再做不会增加 W）。

若 k 很大（> n），算法仍有效，最多做 n 个项目（或到没有正 profit 为止）。


Stats:

	排序：O(n log n)

	每个项目最多入堆出堆一次：O(n log n)

	最多 k 次 pop：O(k log n)（通常 k ≤ n）
	总体：O((n + k) log n)。
	空间 O(n)（堆与排序数据）。



import java.util.*;

class Solution {
    public int findMaximizedCapital(int k, int W, int[] profits, int[] capital) {
        int n = profits.length;
        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = capital[i];
            projects[i][1] = profits[i];
        }
        Arrays.sort(projects, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int idx = 0;
        for (int i = 0; i < k; i++) {
            while (idx < n && projects[idx][0] <= W) {
                maxHeap.offer(projects[idx][1]);
                idx++;
            }
            if (maxHeap.isEmpty()) break;
            int p = maxHeap.poll();
            if (p <= 0) break; //如果是负的profit，就不加到最后profit里面！
            W += p;
        }
        return W;
    }
}




