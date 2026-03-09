2054. Two Best Non-Overlapping Events - Medium

You are given a 0-indexed 2D integer array of events where events[i] = [startTimei, endTimei, valuei]. 
The ith event starts at startTimei and ends at endTimei, and if you attend this event, you will 
receive a value of valuei. You can choose at most two non-overlapping events to attend such that 
the sum of their values is maximized.

Return this maximum sum.

Note that the start time and end time is inclusive: that is, you cannot attend two events where 
one of them starts and the other ends at the same time. More specifically, if you attend an event 
with end time t, the next event must start at or after t + 1.

 

Example 1:


Input: events = [[1,3,2],[4,5,2],[2,4,3]]
Output: 4
Explanation: Choose the green events, 0 and 1 for a sum of 2 + 2 = 4.
Example 2:

Example 1 Diagram
Input: events = [[1,3,2],[4,5,2],[1,5,5]]
Output: 5
Explanation: Choose event 2 for a sum of 5.
Example 3:


Input: events = [[1,5,3],[1,5,1],[6,6,5]]
Output: 8
Explanation: Choose events 0 and 2 for a sum of 3 + 5 = 8.
 

Constraints:

2 <= events.length <= 105
events[i].length == 3
1 <= startTimei <= endTimei <= 109
1 <= valuei <= 106



******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:greedy

Method:

	First sort all events by start time. If start time of two events are equal, sort them by end time.
	
	Then take a priority queue that takes an array containing [endtime, value]. Priority queue will 
		sort elements on the basis of end time.
	
	Iterate through events, for each event e, calculate maximum value from all events that ends 
	before e[0] (i.e. start time). Let's store this value in maxVal variable.
	
	Now answer will be ans = max(ans, e[2] + maxVal).



Stats:
    Time Complexity: O(n⋅logn)

    The algorithm sorts the events by their start times, which takes O(n⋅logn). While iterating through the event list, the algorithm performs operations related to the priority queue (min-heap) for each event. Popping from the heap and pushing a new event both take O(logn), leading to a total of O(n⋅logn) for all these operations.

    Combining all steps, the overall time complexity is given by O(n⋅logn).

    Space complexity: O(n)

class Solution {
    public int maxTwoEvents(int[][] events) {
        int n = events.length;
        Arrays.sort(events, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        
        int maxVal = 0, ans = 0;
        for(int[] event : events){            
            int start = event[0];
            while(!queue.isEmpty()){
                if(queue.peek()[0] >= start)
                    break;
                int[] eve = queue.remove();
                maxVal = Math.max(maxVal, eve[1]);
            }
            ans = Math.max(ans, event[2] + maxVal);
            queue.add(new int[]{event[1], event[2]});
        }
        
        return ans;
    }
}


===================================================================================================
method 2： binary search

关键点 (Key Idea)：
1、 排序 (Sorting)：按开始时间 (Start Time) 升序排列。
2、后缀最大值 (Suffix Max)：预处理一个数组 maxVal，记录从索引 i 到 n-1 之间的所有活动中，单个活动能提供的最大 max value。
3. 二分查找 (Binary Search)：对于每一个活动 i，我们尝试寻找在它结束时间 i.end 之后开始的第一个活动 j（即 
j.start>i.end）。通过二分查找快速定位索引 j ，然后取 maxVal[j] 与当前 value_i 相加。




import java.util.*;

class Solution {
    public int maxTwoEvents(int[][] events) {
        int n = events.length;
        // 1. 按开始时间排序 (Sort by start time)
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. 预处理后缀最大值 (Precompute Suffix Max)
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = events[n - 1][2];
        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(events[i][2], suffixMax[i + 1]);
        }

        int maxTotalValue = 0;

        for (int i = 0; i < n; i++) {
            // 情况 A: 只选当前的这一个活动 (Option 1: Pick only the current event)
            maxTotalValue = Math.max(maxTotalValue, events[i][2]);

            // 情况 B: 选当前活动 + 后面不冲突的最高价值活动 (Option 2: Current + best compatible one)
            int nextEventIdx = findNext(events, events[i][1], i + 1);
            if (nextEventIdx != -1) {
                maxTotalValue = Math.max(maxTotalValue, events[i][2] + suffixMax[nextEventIdx]);
            }
        }

        return maxTotalValue;
    }

    // 二分查找第一个开始时间 > endTime 的活动 (Binary Search for first start > target)
    private int findNext(int[][] events, int endTime, int left) {
        int right = events.length - 1;
        int res = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events[mid][0] > endTime) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return res;
    }
}


import bisect

class Solution:
    def maxTwoEvents(self, events: List[List[int]]) -> int:
        # 1. 按开始时间排序 (Sort by start time)
        events.sort()
        n = len(events)
        
        # 2. 预处理后缀最大值 (Suffix Max Array)
        suffix_max = [0] * (n + 1)
        for i in range(n - 1, -1, -1):
            suffix_max[i] = max(events[i][2], suffix_max[i + 1])
            
        max_total = 0
        
        # 3. 提取所有开始时间用于二分 (Extract start times for bisect)
        starts = [e[0] for e in events]
        
        for s, e, v in events:
            # 选一个的情况 (One event case)
            max_total = max(max_total, v)
            
            # 寻找第一个开始时间 > 当前结束时间 e 的索引
            # Using bisect_right to find the first element > e
            idx = bisect.bisect_right(starts, e)
            
            if idx < n:
                # 选两个的情况 (Two events case)
                max_total = max(max_total, v + suffix_max[idx])
                
        return max_total


