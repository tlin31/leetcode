2402. Meeting Rooms III - Hard

You are given an integer n. There are n rooms numbered from 0 to n - 1.

You are given a 2D integer array meetings where meetings[i] = [starti, endi] means that a meeting will be held during the half-closed time interval [starti, endi). All the values of starti are unique.

Meetings are allocated to rooms in the following manner:

Each meeting will take place in the unused room with the lowest number.
If there are no available rooms, the meeting will be delayed until a room becomes free. The delayed meeting should have the same duration as the original meeting.
When a room becomes unused, meetings that have an earlier original start time should be given the room.
Return the number of the room that held the most meetings. If there are multiple rooms, return the room with the lowest number.

A half-closed interval [a, b) is the interval between a and b including a and not including b.

 

Example 1:

Input: n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]
Output: 0
Explanation:
- At time 0, both rooms are not being used. The first meeting starts in room 0.
- At time 1, only room 1 is not being used. The second meeting starts in room 1.
- At time 2, both rooms are being used. The third meeting is delayed.
- At time 3, both rooms are being used. The fourth meeting is delayed.
- At time 5, the meeting in room 1 finishes. The third meeting starts in room 1 for the time period [5,10).
- At time 10, the meetings in both rooms finish. The fourth meeting starts in room 0 for the time period [10,11).
Both rooms 0 and 1 held 2 meetings, so we return 0. 
Example 2:

Input: n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]]
Output: 1
Explanation:
- At time 1, all three rooms are not being used. The first meeting starts in room 0.
- At time 2, rooms 1 and 2 are not being used. The second meeting starts in room 1.
- At time 3, only room 2 is not being used. The third meeting starts in room 2.
- At time 4, all three rooms are being used. The fourth meeting is delayed.
- At time 5, the meeting in room 2 finishes. The fourth meeting starts in room 2 for the time period [5,10).
- At time 6, all three rooms are being used. The fifth meeting is delayed.
- At time 10, the meetings in rooms 1 and 2 finish. The fifth meeting starts in room 1 for the time period [10,12).
Room 0 held 1 meeting while rooms 1 and 2 each held 2 meetings, so we return 1. 
 

Constraints:

1 <= n <= 100
1 <= meetings.length <= 105
meetings[i].length == 2
0 <= starti < endi <= 5 * 105
All the values of starti are unique.



******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************


现实场景 🌍（为什么很真实）

	公司会议系统（会议延迟但不能取消）

	任务调度系统（任务排队执行）

	Kubernetes Pod 调度

	Kafka Consumer Group 分区重平衡

	CPU 核心任务分配

👉 本质：有限资源 + 排队 + 负载统计


和 Kafka Consumer 分配非常像：

room = consumer

meeting = partition/task

顺延 = backpressure



===================================================================================================
Method 1:

Method:

	-	



Stats:

排序：O(m log m)

每个会议堆操作：O(log n)

总体：O(m log n)

空间：O(n)



1️、 空闲会议室（按编号最小）
PriorityQueue<Integer> freeRooms

2️、 使用中的会议室（按结束时间最早）
PriorityQueue<long[]> usedRooms
// [endTime, roomId]


	public int mostBooked(int n, int[][] meetings) {
	    // 按会议开始时间排序
	    Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

	    // 空闲房间：按编号
	    PriorityQueue<Integer> freeRooms = new PriorityQueue<>();
	    for (int i = 0; i < n; i++) {
	        freeRooms.offer(i);
	    }

	    // 使用中房间：按结束时间 + 房间编号
	    PriorityQueue<long[]> usedRooms = new PriorityQueue<>(
	        (a, b) -> a[0] == b[0] ? Long.compare(a[1], b[1]) : Long.compare(a[0], b[0])
	    );

	    int[] count = new int[n];

	    for (int[] meeting : meetings) {
	        long start = meeting[0], end = meeting[1];

	        // 释放已经结束的会议室
	        while (!usedRooms.isEmpty() && usedRooms.peek()[0] <= start) {
	            freeRooms.offer((int) usedRooms.poll()[1]);
	        }

	        if (!freeRooms.isEmpty()) {
	            // 有空闲房间
	            int room = freeRooms.poll();
	            usedRooms.offer(new long[]{end, room});
	            count[room]++;
	        } else {
	            // 没有空闲房间，顺延
	            long[] earliest = usedRooms.poll();
	            long newEnd = earliest[0] + (end - start);
	            int room = (int) earliest[1];
	            usedRooms.offer(new long[]{newEnd, room});
	            count[room]++;
	        }
	    }

	    // 找使用次数最多的房间
	    int res = 0;
	    for (int i = 1; i < n; i++) {
	        if (count[i] > count[res]) {
	            res = i;
	        }
	    }
	    return res;
	}


