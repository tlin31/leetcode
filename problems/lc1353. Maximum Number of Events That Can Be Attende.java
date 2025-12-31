1353. Maximum Number of Events That Can Be Attended - Medium

You are given an array of events where events[i] = [startDayi, endDayi]. Every event i starts at startDayi and ends at endDayi.

You can attend an event i at any day d where startDayi <= d <= endDayi. You can only attend one event at any time d.

Return the maximum number of events you can attend.

 

Example 1:


Input: events = [[1,2],[2,3],[3,4]]
Output: 3
Explanation: You can attend all the three events.
One way to attend them all is as shown.
Attend the first event on day 1.
Attend the second event on day 2.
Attend the third event on day 3.
Example 2:

Input: events= [[1,2],[2,3],[3,4],[1,2]]
Output: 4
 

Constraints:

1 <= events.length <= 105
events[i].length == 2
1 <= startDayi <= endDayi <= 105



******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


// iterate through days

class Solution {

    public int maxEvents(int[][] events) {
        int n = events.length;
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        int ans = 0;

        //i是天数，j是事件index
        for (int i = 1, j = 0; i <= maxDay; i++) {

        	// 把今天开始的事件加入堆
            while (j < n && events[j][0] <= i) {
                pq.offer(events[j][1]);
                j++;
            }
			
			// 移除已经过期的事件
            while (!pq.isEmpty() && pq.peek() < i) {
                pq.poll();
            }

            // 参加一个结束最早的事件
            if (!pq.isEmpty()) {
                pq.poll();
                ans++;
            }
        }

        return ans;
    }
}


===================================================================================================
Method 1:

Method:


1、按 开始时间排序事件

2、用 最小堆存当前可选事件的 结束时间

3、从第 1 天开始遍历：

	把所有 start == today 的事件加入堆

	移除所有已经过期的事件（end < today）

	选一个 结束最早的参加


Stats:

	- 
	- 

	public int maxEvents(int[][] events) {
	    // 按开始时间排序
	    Arrays.sort(events, (a, b) -> a[0] - b[0]);

	    PriorityQueue<Integer> pq = new PriorityQueue<>(); // end times
	    int day = 0, 
	    	i = 0, 
	    	res = 0;
	    int n = events.length;

	    while (i < n || !pq.isEmpty()) {
	        // 如果当前没有可选事件，直接跳到下一个事件的开始日
	        if (pq.isEmpty()) {
	            day = events[i][0];
	        }

	        // 把今天开始的事件加入堆
	        while (i < n && events[i][0] <= day) {
	            pq.offer(events[i][1]);
	            i++;
	        }

	        // 移除已经过期的事件
	        while (!pq.isEmpty() && pq.peek() < day) {
	            pq.poll();
	        }

	        // 参加一个结束最早的事件
	        if (!pq.isEmpty()) {
	            pq.poll();
	            res++;
	            day++;
	        }
	    }

	    return res;
	}

