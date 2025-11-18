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

	- 
	- 





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

import java.util.*;

class Solution {
    public int maxTwoEvents(int[][] events) {
        int n = events.length;
        
        // Step 1: Sort the events by their start time
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        
        // Step 2: Create the suffix array for the maximum event value from each event onward
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = events[n - 1][2];  // Initialize the last event's value
        
        // Populate the suffixMax array
        for (int i = n - 2; i >= 0; --i) {
            suffixMax[i] = Math.max(events[i][2], suffixMax[i + 1]);
        }
        
        // Step 3: For each event, find the next event that starts after it ends
        int maxSum = 0;
        
        for (int i = 0; i < n; ++i) {
            int left = i + 1, right = n - 1;
            int nextEventIndex = -1;
            
            // Perform binary search to find the next non-overlapping event
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (events[mid][0] > events[i][1]) {
                    nextEventIndex = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            
            // If a valid next event is found, update the max sum
            if (nextEventIndex != -1) {
                maxSum = Math.max(maxSum, events[i][2] + suffixMax[nextEventIndex]);
            }
            
            // Also consider the case where we take only the current event
            maxSum = Math.max(maxSum, events[i][2]);
        }
        
        return maxSum;
    }
}
